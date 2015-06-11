/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

/* Basic UNIT test cases for the Music Band ID searcher */
public class SeacherTester {
  
	Searcher testSearchInstance;
	private final String testFilePath = "C:\\Users\\snagar6\\Desktop\\Pandora\\musical_group.tsv";
	
	@Test
	public void testSearches1() {
		List<String> actual = testSearchInstance.search("Yearbook Committee");		
		List<String> expected = Arrays.asList("dykyh3");		
		assertTrue(actual.equals(expected));	
	}

	@Test
	public void testSearches2() {
		List<String> actual = testSearchInstance.search("Yearbook Committee");		
		List<String> expected = Arrays.asList("dyksdsccscsyh3");		
		assertFalse(actual.equals(expected));
	}
	
	@Test
	public void testSearches3() {
		List<String> actual = testSearchInstance.search("Overload");		
		List<String> expected = Arrays.asList("4jm18_", "gf13v3", "3j3d5k", "1twzd4", "3j35x4", "8ls5b", "3f05sj");		
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches4() {
		List<String> actual = testSearchInstance.search("Overload");		
		List<String> expected = Arrays.asList("4jm18_");		
		assertFalse(actual.equals(expected));
	}
	
	@Test
	public void testSearches5() {
		List<String> actual = testSearchInstance.search("hel");		
		List<String> expected = Arrays.asList("1sx0db",  "1mnzv9");
		assertTrue(actual.equals(expected));
	}
 
	@Test
	public void testSearches6() {
		List<String> actual = testSearchInstance.search("hhh                (ryu☆ &                                    dai)");		
		List<String> expected = Arrays.asList("1x0q6l");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches7() {
		List<String> actual = testSearchInstance.search("hhh (ryu☆ & dai)");		
		List<String> expected = Arrays.asList("1x0q6l");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches8() {
		List<String> actual = testSearchInstance.search("камерный оркестр ленинградской государственной филармонии");		
		List<String> expected = Arrays.asList("3xnm8x");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches9() {
		List<String> actual = testSearchInstance.search("камерный оркестр ленинградско");		
		List<String> expected = Arrays.asList("3xnm8x");
		assertFalse(actual.equals(expected));
	}
	
	@Test
	public void testSearches10() {
		List<String> actual = testSearchInstance.search("ਹਰਭਜਨ ਮਾਨ ਅਤੇ ਅਲਕਾ ਯਾਗਨਿਕ");		
		List<String> expected = Arrays.asList("1wxn7_");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches11() {
		List<String> actual = testSearchInstance.search("John Lennon & The Plastic Ono Nuclear Band with Little Big Horns & The Philharmanic Orchestrange");		
		List<String> expected = Arrays.asList("3j0d5y");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches12() {
		List<String> actual = testSearchInstance.search("a");		
		List<String> expected = Arrays.asList("2p6x0");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches13() {
		List<String> actual = testSearchInstance.search("הקוביות");		
		List<String> expected = Arrays.asList("dnjrnd");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches14() {
		List<String> actual = testSearchInstance.search("The Project");		
		List<String> expected = Arrays.asList("1tw_nf", "3h_4ft", "3h_5c5", "3h_6rw", "3h_5m3", "3h_617");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches15() {
		List<String> actual = testSearchInstance.search("The Project asdaadcacdnbcbsacsbcjsabjkbcajbdkjbakjbcdakjdbcak");		
		List<String> expected = Arrays.asList("1tw_nf", "3h_4ft", "3h_5c5", "3h_6rw", "3h_5m3", "3h_617");
		assertFalse(actual.equals(expected));
	}
	
	@Test
	public void testSearches16() {
		List<String> actual = testSearchInstance.search("asdaadcacdnbcbsacsbcjsabjkbcajbdkjbakjbcdakjdbcak");		
		List<String> expected = Arrays.asList();
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches17() {
		List<String> actual = testSearchInstance.search("(maps)");		
		List<String> expected = Arrays.asList("f1_dhg");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches18() {
		List<String> actual = testSearchInstance.search("depapepe meets ハチミツとクローバー");		
		List<String> expected = Arrays.asList("3f5bpc");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches19() {
		List<String> actual = testSearchInstance.search("depapepe");		
		List<String> expected = Arrays.asList("1rhnmt");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches20() {
		List<String> actual = testSearchInstance.search("depapepe");		
		List<String> expected = Arrays.asList();
		assertFalse(actual.equals(expected));
	}
	
	@Test
	public void testSearches21() {
		List<String> actual = testSearchInstance.search("2face4    2face4  2face444444");		
		List<String> expected = Arrays.asList("frnmmz");
		assertFalse(actual.equals(expected));
	}
		
	@Test
	public void testSearches22() {
		List<String> actual = testSearchInstance.search("phil collins; emilio cuervo; pablo perea; toni menguiano; miguel galindo; tomás álvarez");		
		List<String> expected = Arrays.asList("dtx59h");
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches23() {
		List<String> actual = testSearchInstance.search("phil collins; tomás álvarez");		
		List<String> expected = Arrays.asList();
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches24() {
		List<String> actual = testSearchInstance.search("      \\m\\0");		
		List<String> expected = Arrays.asList();
		assertTrue(actual.equals(expected));
	}
	
	@Test
	public void testSearches25() {
		List<String> actual = testSearchInstance.search("\\n");		
		List<String> expected = Arrays.asList();
		assertTrue(actual.equals(expected));
	}

	@Before
	public void setUp() throws Exception {
		/* Creating a Searcher Instance */
		this.testSearchInstance = new Searcher(testFilePath);
		
        /* Emulating a Database - By parsing the file containing the dataset onto the memory (emulated database as per the design) */
        testSearchInstance.parseFile(testSearchInstance.filePath);
           
    	/* Index Based Sort */
        SortIndex.inPlaceQuickSort (testSearchInstance.rowDataset, 0, testSearchInstance.count - 1);
		
		/* Dataset Sharding & assigning the shards to virtual nodes */
		testSearchInstance.shardingToNodes();
		
	  } 
} 
