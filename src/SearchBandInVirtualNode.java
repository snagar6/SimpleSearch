/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */
import java.util.ArrayList;

/* Binary Search based technique to find a Musical Band in a Virtual Node containing the range of Index sorted */
public class SearchBandInVirtualNode {

	public static ArrayList<String> searchNode(EmulatedDistribitedDatabaseEntry[] a, int index) {
	    if (a.length == 0) {
	    	return new ArrayList<String>(); 
	    }
	    
	    int low = 0;
	    int high = a.length - 1;

	    while(low <= high) {
	    	
	    	int middle = (low + high)/2; 
	      
	    	if (index > a[middle].bandIndex) {
	    		low = middle +1;
	    	} 
	    	else if (index < a[middle].bandIndex) {
	    		high = middle -1;
	    	}
	    	else { 
	    		/* Match FOUND - now see if its sorted neighbors also have the same index */
	    		ArrayList<String> resultArr = new ArrayList<String>();
	    		resultArr.add(a[middle].bandID);
	    		
	    		/* Neighbors below the Found position */
	    		int below = middle - 1;
	    		while ( (below >= 0) && (a[below].bandIndex == index) ) {
	    			resultArr.add(a[below].bandID);
	    			below--;
	    		}
	    		
	    		/* Neighbors above the Found position */
	    		int above = middle + 1;
	    		while ( (above < a.length) && (a[above].bandIndex == index) ) {
	    			resultArr.add(a[above].bandID);
	    			above++;
	    		}
	    		
	    		return resultArr; 
	    	}
	    }
	  
	    /* Music band not found in this shard */
	    return new ArrayList<String>(); 
	  }	
}

