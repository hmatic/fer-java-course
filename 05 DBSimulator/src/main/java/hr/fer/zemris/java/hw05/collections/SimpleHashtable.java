package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * This class implements a hash table, which maps keys to values. Any non-null object can be used as a key. Values can be null. <br>
 * Initial capacity of hash table is 16, unless specified otherwise. Capacity is always power of number 2.<br>
 * This hash table uses linked list for entries which end up in same table spot.<br>
 * Upon reaching 75% occupancy rate, hash table will be reallocated to twice its size, and entries will be rearranged.<br>
 * 
 * @author Hrvoje Matić
 * @version 1.0
 *
 * @param <K> data type of key
 * @param <V> data type of value
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	/**
	 * Initial capacity of created SimpleHashtable.
	 */
	private static final int INITIAL_CAPACITY = 16;
	/**
	 * Occupancy percentage before SimpleHashtable's capacity is increased.
	 */
	private static final double OCCUPANCY_PERCENTAGE = 0.75;
	
	/**
	 * Inner static class which models each entry inside SimpleHashtable.
	 * 
	 * @author Hrvoje Matić
	 *
	 * @param <K> data type of key
	 * @param <V> data type of value
	 */
	public static class TableEntry<K,V> {
		/**
		 * Entry key. Key can't be null.
		 */
		private K key;
		/**
		 * Entry value.
		 */
		private V value;
		/**
		 * Reference to next TableEntry in same SimpleHashtable field.
		 */
		private TableEntry<K,V> next;
		
		/**
		 * Default constructor for TableEntry.
		 * 
		 * @param key entry key
		 * @param value entry value
		 */
		public TableEntry(K key, V value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}

		/**
		 * Getter for entry key.
		 * 
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter for entry value.
		 * 
		 * @return entry value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for entry value.
		 * 
		 * @param value entry value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Returns String representation of TableEntry
		 * 
		 * @return TableEntry as string
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
	
	/**
	 * Table which holds all entries.
	 */
	private TableEntry<K,V>[] table;
	/**
	 * Current number of entries stored in SimpleHashtable.
	 */
	private int size;
	/**
	 * Modifications counter.
	 */
	private int modificationCount;
	
	/**
	 * Default constructor for SimpleHashtable. 
	 * It creates new SimpleHashtable with capacity of first higher of equal number which is power of 2.
	 *  
	 * @param capacity given capacity
	 * @throws IllegalArgumentException if gives capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity<1) throw new IllegalArgumentException("Capacity can't be less than 1");
		int newCapacity = (int) Math.pow(2, Math.ceil(Math.log(capacity)/Math.log(2)));
		table = (TableEntry<K,V>[]) new TableEntry[newCapacity];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Constructor which initializes SimpleHashtable with INITIAL_CAPACITY.
	 */
	public SimpleHashtable() {
		this(INITIAL_CAPACITY);
	}
	
	/**
	 * Returns number of current entries inside SimpleHashtable.
	 * 
	 * @return number of entries
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Finds entry with key given in argument and returns its value.
	 * Will return null if no such entry is found.
	 * 
	 * @param key key of searched entry
	 * @return found entry value, null otherwise
	 */
	public V get(K key) {
		if(key==null) return null;
		int pos = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> current = table[pos];
		while(current!=null) {
			if(current.getKey().equals(key) ) {
				return current.getValue();
			}
			current=current.next;
		}
		return null;
	}
	
	/**
	 * Puts entry with given key and value inside SimpleHashtable. 
	 * If entry with given key already exists, it overwrites its value to new one.
	 * If table exceeds OCCUPANCY_PERCENTAGE, it doubles capacity of table.
	 * 
	 * @param key key of input entry
	 * @param value value of input entry
	 * @throws NullPointerException if key is null
	 */
	public void put(K key, V value) {
		if(key==null) throw new NullPointerException("Key can't be null");
		
		boolean valueAdded = put(key, value, this.table);
		if(valueAdded) {
			size++;
			modificationCount++;
		}
		
		if(size>(OCCUPANCY_PERCENTAGE*table.length)) {
			increaseCapacity();
		}
	}
	
	/**
	 * Helper method used to put entry with given key and value into given array of table entries.
	 * 
	 * @param key input entry key
	 * @param value input entry value
	 * @param table array of TableEntries
	 * @return true if new entry is created, false if existing entry is only modified
	 */
	private boolean put(K key, V value, TableEntry<K,V>[] table) {
		int pos = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> entry = new TableEntry<K,V>(key, value);
		
		if(table[pos]==null) {
			table[pos] = entry;
		} else {
			TableEntry<K,V> root = table[pos];
			
			while(root.next!=null) {
				if(root.getKey().equals(key)) {
					root.setValue(value);
					return false;
				}
				root=root.next;
			}
			
			root.next=entry;
		}
		return true;
	}

	/**
	 * Helper method which increases capacity of current SimpleHashtable and reorders elements inside according to new table positions. 
	 */
	private void increaseCapacity() {
		@SuppressWarnings("unchecked")
		TableEntry<K,V>[] biggerTable = (TableEntry<K,V>[]) new TableEntry[table.length*2];
		
		for(TableEntry<K,V> current : table) {
			while(current!=null) {
				put(current.getKey(), current.getValue(), biggerTable);
				current=current.next;
			}
		}
		
		table = biggerTable;
	}

	/**
	 * Checks if entry with given key exists inside SimpleHashtable.
	 * 
	 * @param key entry key
	 * @return true if entry exists, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(key==null) return false;
		int pos = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> current = table[pos];
		while(current!=null) {
			if(current.getKey().equals(key) ) {
				return true;
			}
			current=current.next;
		}
		return false;
	}
	
	/**
	 * Checks if entry with given value exists inside SimpleHashtable.
	 * 
	 * @param value entry value
	 * @return true if entry exists, false otherwise
	 */
	public boolean containsValue(Object value) {
		for(TableEntry<K,V> entry : table) {
			TableEntry<K,V> current = entry;
			while(current!=null) {
				if(current.getValue().equals(value) ) {
					return true;
				}
				current=current.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes entry with given key from SimpleHashtable.
	 * 
	 * @param key key of entry that needs to be removed
	 */
	public void remove(Object key) {
		if(key==null) return;
		int pos = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> current = table[pos];
		TableEntry<K,V> previous = null;
		if(table[pos].getKey().equals(key)) {
			table[pos]=table[pos].next;
			size--;
			modificationCount++;
			return;
		}
		while(current!=null) {
			if(current.getKey().equals(key) ) {
				previous.next=current.next;
				size--;
				modificationCount++;
				return;
			}
			previous=current;
			current=current.next;
		}
	}
	
	/**
	 * Checks if SimpleHashtable is empty or not.
	 * 
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		if(size==0) return true;
		return false;
	}
	
	/**
	 * Returns String representation of SimpleHashtable.
	 * 
	 * @return SimpleHashtable as string
	 */
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[", "]");
		for(TableEntry<K,V> current : table) {
			while(current!=null) {
				sj.add(current.toString());
				current=current.next;
			}
		}
		return sj.toString();
	}

	/**
	 * Class which models iterator of SimpleHashtable.
	 * 
	 * @author Hrvoje Matić
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		/**
		 * Current index position inside table.
		 */
		private int pos;
		/**
		 * Current table entry.
		 */
		private TableEntry<K,V> entry;
		/**
		 * Iteration counter.
		 */
		private int counter;
		/**
		 * Iterator modification counter.
		 */
		private int iteratorModificationCount;
		/**
		 * Flag to determine multiple calls of remove method.
		 */
		private boolean elementRemoved;
		
		/**
		 * Default constructor for IteratorImpl which initializes all variables to default values.
		 */
		public IteratorImpl() {
			pos = 0;
			entry = table[0];
			counter = 0;
			iteratorModificationCount = modificationCount;
			elementRemoved = false;
		}
		
		/**
		 * Determines if Iterator has next entry or not.
		 * 
		 * @return true if iterator has next entry, false otherwise
		 * @throws ConcurrentModificationException if collection was modified during iteration
		 */
		public boolean hasNext() {
			if(iteratorModificationCount!=modificationCount) throw new ConcurrentModificationException();
			if(counter<size) return true;
			return false;
		}
		
		/**
		 * Returns next entry in iteration.
		 * 
		 * @return next table entry
		 * @throws ConcurrentModificationException if collection was modified during iteration
		 * @throws NoSuchElementException if called when no more next entries exist
		 */
		public TableEntry<K,V> next() {
			if(iteratorModificationCount!=modificationCount) throw new ConcurrentModificationException();
			if(entry!=null && entry.next!=null) {
				entry=entry.next;
				counter++;
				elementRemoved = false;
				return entry;
			} else if(pos<table.length-1) {
				do {
					pos++;
				} while(table[pos]==null && pos<table.length);
				entry=table[pos];
				counter++;
				elementRemoved = false;
				return entry;
			} else {
				throw new NoSuchElementException("No more elements");
			}
		}
		
		/**
		 * Controlled iterator remove method. Removes current Iterator entry. This method can't be called twice in same iteration.
		 * 
		 * @throws ConcurrentModificationException if collection was modified during iteration 
		 * @throws IllegalStateException if called more than once in same iteration
		 */
		public void remove() {
			if(iteratorModificationCount!=modificationCount) throw new ConcurrentModificationException();
			if(elementRemoved) throw new IllegalStateException();
			SimpleHashtable.this.remove(entry.getKey());
			elementRemoved = true;
			counter--;
			iteratorModificationCount++;
		}
	}

	/**
	 * Factory method that returns new iterator of SimpleHashtable.
	 * 
	 * @return iterator of SimpleHashtable
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
}
