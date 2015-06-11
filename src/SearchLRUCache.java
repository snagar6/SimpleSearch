/* By: Shreyas Nagaraj (Email: shreyas.n.d@gmail.com) */
/* This is more like a generic implementation, not particular for the music band search project only. 
 * 
 * References: https://code.google.com/p/concurrentlinkedhashmap/
 */

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SearchLRUCache <K,V> {
	
	private final int size; 
	private ConcurrentHashMap<K,V> map;
	private ConcurrentLinkedQueue<K> queue;	
	
	public SearchLRUCache (int size) {
		this.size = size;
		map = new ConcurrentHashMap<K,V> (size);
		queue = new ConcurrentLinkedQueue<K>();
	}

	public void put(K key, V value) {
		
		/* checking for null values */
		if (key == null || value == null) 
			throw new NullPointerException();	
		
		if (map.containsKey(key))
			queue.remove(key);
			
		if(queue.size() >= size) {
			K currKey = queue.poll();
			if (currKey != null) {
				map.remove(currKey);
			}
		}
		
		queue.add(key);
		map.put(key,value);
	}
	
	public V get(K key) {
		
		/* Add the entry back - to imply that is was used recently */
		queue.remove(key);
		queue.add(key);
		return map.get(key);
	}

}