/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */

/* Sorting based on the indices */

/* In my solution, I have used an “In-place quicksort” technique to sort the indices.
 *  This would NOT be comparable to the real external sort, however by doing the sorting 
 *  in-place we at least can be memory efficient while sorting our dataset.  
 *    
 *  Note: 
 *  Since, we only have 3 columns for our dataset including the calculated 
 *  index – I don’t save the indices in a separate storage. Based, on the index which
 *  is being looked-up, I can also retrieve the entry dataset entry for that particular
 *  index. And, several such dataset entries referred by their indices would be put
 *  into each of the virtual shard of the emulated database. 
 */


public class SortIndex {

	public static void inPlaceQuickSort (EmulatedDistribitedDatabaseEntry[] arr, int i, int j) {
		if (i < j) {
			/* Partitioning the dataset */
			int q = partition (arr, i, j);

			/* Splitting the dataset across two equal partitions */
			inPlaceQuickSort (arr, i, q);
			inPlaceQuickSort (arr, q+1, j);
		}
	}

	private static int partition(EmulatedDistribitedDatabaseEntry[] a, int p, int r) {

		int x = a[p].bandIndex;
		int i = p - 1;
		int j = r + 1;

		while (true) {
			i++;
			while ( (i < r) && a[i].bandIndex < x)
				i++;

			j--;
			while ( (j > p) && a[j].bandIndex > x)
				j--;

			if (i < j)
				swap (a, i, j);
			else
				return j;
		}
	}

	/* Swapping two entries in the datasets */
	/* NOTE: In real world, We SHOULD JUST BE SORTING the COLUMN which is PICKED for INDEXING & WHAT it is POINTING to, not the entire DATASET */
	private static void swap(EmulatedDistribitedDatabaseEntry[] a, int i, int j) {     
		EmulatedDistribitedDatabaseEntry temp = new EmulatedDistribitedDatabaseEntry(a[i].bandName, a[i].bandID, a[i].bandIndex);
  
		a[i].bandName = a[j].bandName;
		a[i].bandID = a[j].bandID;
		a[i].bandIndex = a[j].bandIndex;
        
		a[j].bandName = temp.bandName;
		a[j].bandID = temp.bandID;
		a[j].bandIndex = temp.bandIndex;
	}
}
