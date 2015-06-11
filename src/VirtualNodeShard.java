/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */

/* Definition of each entry in the emulated distributed database */
public class VirtualNodeShard{
	public int nodeNum;
	public int startIndex;
	public int endIndex;
	public int noOfEntries;	
	
	VirtualNodeShard() {
		startIndex = endIndex = noOfEntries = 0;	
		nodeNum = -1;
	}	
}
