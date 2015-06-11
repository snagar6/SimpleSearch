/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */

/* Simple Search Engine design to locate a Musical group based on their ID on a database */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
 
/* The Main Searcher Class */
public class Searcher {
	
	public String filePath;
	public SearchLRUCache<Integer, ArrayList<String>> cacheLRU;
	public EmulatedDistribitedDatabaseEntry[] rowDataset;
	public VirtualNodeShard[] dataShards;
	public MusicBandsTrie Trie;    
	public final int INITIAL_EMUALTED_DATABASE_SIZE = 200000; /* Size of the Initial Emulated database, can extend it if we add more rows of data */
	public final int SHARDS = 2000; /* configurable number of shards/splits*/
	public final int LRU_CACHE_SIZE = 20; /* configurable LRU cache size */
	public int count;
        
	private void prompt() {
		System.out.print("search> ");
	}
 
	public Searcher(String filePath) {
   	 	this.filePath = filePath;
        	this.Trie = new MusicBandsTrie(); 
		this.rowDataset = new EmulatedDistribitedDatabaseEntry[INITIAL_EMUALTED_DATABASE_SIZE];
		this.dataShards = new VirtualNodeShard[SHARDS];
		this.cacheLRU = new SearchLRUCache<Integer, ArrayList<String>>(LRU_CACHE_SIZE);
		this.count = 0;
	} 
    
	/* Search/Query Method */
  	public List<String> search(String query) {    	
  		String queryString = "";
  		  		
    		ArrayList<String> recomendString = new ArrayList<String>(); /* Recommendation List, when search query fails */
    		VirtualNodeShard dataShard;
    	
     		/* Pre-process the input Query String to remove some extra while spaces */
    		String [] strs = query.split("\\s+");
       		for (int i = 0; i < strs.length; i++) {
    			queryString += strs[i].trim();
    			queryString += " ";
    		}
       	
       		/* Hash of the input string - which in this case is nothing but the Index */
       		int queryIndex = queryString.toLowerCase().trim().hashCode();
    
    		/* Searching the Query String in the Cache */
       		ArrayList<String> cacheResultIDs = this.cacheLRU.get(queryIndex);
      	 	if (cacheResultIDs != null) { /* Cache HIT */	
       			System.out.print("ID/IDs found in the LRU cache:");
       			return (List<String>)cacheResultIDs;
      	 	} 
    	
       		/* Searching the String in the Actual Distributed emulated database - Cache MISS case */
    		/* To check if the Index is in the valid range of any Virtual Node */ 
       	
    		dataShard = getNodeforIndex(queryIndex);    	
    		if (dataShard != null) {     		
    			EmulatedDistribitedDatabaseEntry[] searchSet = new EmulatedDistribitedDatabaseEntry[dataShard.noOfEntries];
   	 		System.arraycopy(this.rowDataset, (dataShard.nodeNum)*(this.count/this.SHARDS), searchSet, 0, dataShard.noOfEntries);
    			ArrayList<String> resultIDs = SearchBandInVirtualNode.searchNode(searchSet, queryIndex);
    		
    			if (resultIDs.size() > 0) {
    				this.cacheLRU.put(queryIndex, resultIDs); /* Adding the found entry onto the cache */
    				System.out.print("ID/IDs found in the database:");
    				return (List<String>)resultIDs;
    			}    		
   	 	}
    	
    		/* After Search query FAILURE */    	
       		/* In order to do a partial Look-up when the query search has failed - the query length needs at be least of 2 chars */
    		if (queryString.length() > 1) {    		
    			/* Figuring out all the possible music bands in the database with the same prefix */
    			recomendString = (ArrayList<String>) Trie.retCloseMatches(queryString.toLowerCase().trim());  
    			if (recomendString.size() != 0) {
    				Iterator<String> itr = recomendString.iterator();
    				if (itr.hasNext())
    					System.out.print("\nDid you mean?: ");
    				while (itr.hasNext()) 
					System.out.println(" '" + itr.next().trim() + "' ");
				System.out.println("<Above are your RECOMMEDATIONS>\n");
			} 
		}   	
    	
		return Collections.emptyList();
	}
 
	/* Parsing the dataset file and loading onto the emulated database & then building a prefix tree */
	public void parseFile (String filePath) throws IOException {
    	
    		BufferedReader br = new BufferedReader(new FileReader(filePath));
    		String line;           
  
	    	br.readLine(); /* Ignore header line */
    	
    		while ((line = br.readLine()) != null) {  /* process each line */   
    			String [] strs = line.split("/m/0"); 
       
    			/* Creating the emulated distributed database in the memory */
    			this.rowDataset[this.count] = new EmulatedDistribitedDatabaseEntry();
    			this.rowDataset[this.count].bandName = strs[0].toLowerCase().trim();
    			this.rowDataset[this.count].bandID = strs[1].trim();
    			this.rowDataset[this.count].bandIndex = strs[0].toLowerCase().trim().hashCode();
    			this.count++;
    		
    			/* Creating the TRIE data structure for partial look-ups */
    			Trie.insert(strs[0].toLowerCase().trim());     		
    		}
    		br.close();
	}    
    
    
	/* This is a VIRTUAL split of the data amongst virtual nodes. Dataset is NOT REALLY distributed across real and
	 * distributed file system. Here, its just being virtually divided on the emulated database by maintain a bunch of 
	 * virtual nodes which contain the START and END indices of the dataset rows it owns as a result of horizontal sharding
	 */
	public void shardingToNodes() {

		int noEntriesPerShard = this.count/this.SHARDS;
    	
		for (int i = 0; i < this.SHARDS; i++) {
	    		this.dataShards[i] = new VirtualNodeShard(); 
    			int start = i*noEntriesPerShard;
    			int last = (i*noEntriesPerShard) + noEntriesPerShard ;
    			this.dataShards[i].startIndex = this.rowDataset[start].bandIndex;
    			this.dataShards[i].nodeNum = i;
    		
    			if (i == this.SHARDS-1) {  /* The last Shard will be more */
    				this.dataShards[i].endIndex = this.rowDataset[this.count-1].bandIndex; 
    				this.dataShards[i].noOfEntries = this.count - start;
    			}
    			else {
    				this.dataShards[i].endIndex = this.rowDataset[last].bandIndex;
    				this.dataShards[i].noOfEntries = last - start;    			
    			}    		
    		}    	
	}
    
	/* Getting the Right Virtual Node for a given Musical Band based on Index */
	public VirtualNodeShard getNodeforIndex (int hashCode) {
		for (int i = 0; i < this.SHARDS; i++) {
    			if ((hashCode >= this.dataShards[i].startIndex) && (hashCode <= this.dataShards[i].endIndex))
				return this.dataShards[i];
		}
		return null;
	}
    

	/* Main Entry point for the Searcher utility */
	public static void main(String[] args) throws IOException {
    	
		if (args == null || args.length != 1) {
			System.err.println("Invalid input arguments: " + (args == null ? args : Arrays.asList(args)));
			System.exit(1);
		}
 
		/* Creating a Searcher instance */
		Searcher searchInstance = new Searcher(args[0]);
        
		/* Emulating a Database - By parsing the file containing the dataset onto the memory */
		System.out.println("Initialzing the Emulated distributed database");
		System.out.println("And, building a Prefix Tree for Partial Searches...");
		searchInstance.parseFile(searchInstance.filePath);
		System.out.println("Done!");
       
		/* Index Based Sort */
		System.out.println("Indexing dataset and sorting...");
		SortIndex.inPlaceQuickSort (searchInstance.rowDataset, 0, searchInstance.count - 1);
		System.out.print("No of dataset entries(rows): "+searchInstance.count+"   No of Horizontal Shards: "+searchInstance.SHARDS+"  Cache Size: "+searchInstance.LRU_CACHE_SIZE);
		System.out.println("\nDone!\n");
		
		/* Dataset Sharding & assigning the shards to virtual nodes */
		searchInstance.shardingToNodes();
				
		searchInstance.prompt();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			List<String> results = searchInstance.search(scanner.nextLine());
			if (results.isEmpty()) {
				System.out.println(" RESULT: ID FOR YOUR Input Query NOT FOUND !!!\n");
			} else {            	
				for (String result : results) {
					System.out.print("  "+result);
				}
				System.out.println();
			}
			searchInstance.prompt();
		}        
	}
}
