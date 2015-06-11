/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */

/* Definition of each entry in the emulated distributed database */
public class EmulatedDistribitedDatabaseEntry {
	
	public String bandName;
	public String bandID;
	public int bandIndex;	
	
	EmulatedDistribitedDatabaseEntry() {
		bandName = bandID = null;	
		bandIndex = 0;
	}
	
	EmulatedDistribitedDatabaseEntry(String bandName, String bandID, int bandIndex) {
		this.bandName = bandName;
		this.bandID = bandID;		
		this.bandIndex = bandIndex;		
	}
}