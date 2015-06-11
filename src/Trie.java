/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */

/* Use case: (This trie utility is generally called by the Searcher Instance)
 * I build a Trie when I initially parse through the dataset and store it separately.
 * A trie though an additional data structure, would not consume too much memory based
 * on the way the prefix tree is constructed. The partial string lookup will result in
 * a bunch of qualified musical bands having the same prefix.  This can be listed upon 
 * a failure of a band lookup after the query has been made. 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

/* Implementing the Trie (prefix tree) */
class MusicBandsTrie {

	public TrieNode base;
	
	public MusicBandsTrie () { 
    		base = new TrieNode((char)0);    
	}    

	public void insert(String str) {
    		TrieNode traverse = base;
           
		for (int i = 0; i < str.length(); i++) {
        		/* Getting an HashMap which contains all the existing children of the Trie Node */
			HashMap<Character,TrieNode> children = traverse.getChildren();            
			char ch = str.charAt(i);
          
			/* Finding if there is child node (character) under this node */
			if (children.containsKey(ch))
				traverse = children.get(ch);
			else { /* If child node is not found - grow the Trie by adding a new Trie Node for the parent node */
				TrieNode t = new TrieNode(ch);
				children.put(ch, t);
				traverse = t; /* New position for traversal would be the newly added child node */
			}
		}
		/* Mark the last trie node for that input string as the end of word */
		traverse.setIsWordEnd(true);
	}
    
	public ArrayList<String> retCloseMatches(String partialStr) {
    		TrieNode traverse = base;
   	 	int len = partialStr.length();
    		int i;
    	    	    
		for (i = 0; i < len; i++) {
			HashMap<Character,TrieNode> child = traverse.getChildren();            
			char ch = partialStr.charAt(i);            
            
			/* To check if we can find the next character of the prefix string in the Trie */
			if (child.containsKey(ch))          		         		
				traverse = child.get(ch);          
			else
				return (new ArrayList<String>()); /* Return an Empty List - because, there is no band with this prefix */
		}        
        
		/* The partial lookup is a valid prefix, but that is not Music Band by itself*/
		if ((i == len) && (traverse.isWordEnd() == false))        	
			return (getAllWords(traverse, partialStr));
    
		/* This means there was a match found, so returning empty list - shdnt reach here */     
		return (new ArrayList<String>());    	
	}
    
	/* Getting all the valid Music bands for the prefix */
	ArrayList<String> getAllWords(TrieNode start,  String prefixStr) {
    	HashMap<Character,TrieNode> childrenList; 
    	childrenList = start.getChildren();    	
    	
    	/* Leaf Node case */
    	if(childrenList.size() == 0) {
    		ArrayList<String> list = new ArrayList<String>();
			list.add(prefixStr);
			return list;
		} else {		
		/* Recursively traversing the Trie to get all the valid bands for the prefix */
			ArrayList<String> list = new ArrayList<String>();
			for(TrieNode entry : childrenList.values()) {				
				list.addAll(getAllWords(entry, prefixStr + entry.getVal()));
			}
			return list;
		}    	
	}
}

/* Single node in the Trie tree */
class TrieNode {        
	private char val;  
	private boolean isWordEnd;
	private HashMap<Character,TrieNode> children;  
    
	public TrieNode(char c)  {
		val = c;
		children = new HashMap<Character,TrieNode>();
		isWordEnd = false;
	}    
    
	public HashMap<Character,TrieNode> getChildren() {
		return children; 
	}    
    
	public char getVal() {   
		return val;  
	}    
    
	public void setIsWordEnd(boolean value) {
		isWordEnd = value;
	}
    
	public boolean isWordEnd() { 
		return isWordEnd; 
	}       
}
