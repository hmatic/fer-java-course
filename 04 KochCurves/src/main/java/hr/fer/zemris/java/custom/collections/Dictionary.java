package hr.fer.zemris.java.custom.collections;

/**
 * Represents dictionary collection (also known as map). Each dictionary entry has key and value.
 * Key can never be null, while value can. There can't be duplicate keys inside dictionary, while there can be duplicate values.
 * @author Hrvoje Matić
 * @version 1.0
 *
 */
public class Dictionary {
	/**
	 * Inner class that models dictionary entries.
	 * 
	 * @author Hrvoje Matić
	 * @version 1.0
	 */
	private class DictEntry {
		/**
		 * Entry key.
		 */
		private Object key;
		/**
		 * Entry value.
		 */
		private Object value;
		
		/**
		 * Default constructor for DictEntry.
		 * @param key entry key
		 * @param value entry value
		 */
		public DictEntry(Object key, Object value) {
			if(key==null) throw new NullPointerException("Key can't be null");
			this.key = key;
			this.value = value;
		}

		/**
		 * Getter for key.
		 * @return key of entry
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Getter for value.
		 * @return value of entry
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Setter for value.
		 * @param value new value
		 */
		public void setValue(Object value) {
			this.value = value;
		}	
		
		/**
		 * Equals determines if two DictEntries are same. <br>
		 * They will be same if they have same keys and if they're both objects of type DictEntry. Entry value has no influence on this method.
		 */
		@Override
		public boolean equals(Object arg0) {
			if(!(arg0 instanceof DictEntry)) return false;
			if(this.key==((DictEntry) arg0).getKey()) return true;
			return false;
		}
	}
	
	/**
	 * Storage of dictionary entries implemented with {@link ArrayIndexedCollection}.
	 */
	private ArrayIndexedCollection storage;

	/**
	 * Default constructor for dictionary. Initializes new empty dictionary.
	 */
	public Dictionary() {
		super();
		this.storage = new ArrayIndexedCollection();
	}
	
	/**
	 * Determines if dictionary is empty or not.
	 * @return true if dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}
	
	/** 
	 * Calculates and returns number of entries stored in dictionary
	 * @return size of dictionary
	 */
	public int size() {
		return storage.size();
	}
	
	/**
	 * Removes all entries from dictionary.
	 */
	public void clear() {
		storage.clear();
	}
	
	/**
	 * Stores new entry in dictionary. If entry with same key already exists, it overwrites its value, otherwise creates new entry.
	 * @param key entry key
	 * @param value entry value
	 */
	public void put(Object key, Object value) {
		DictEntry newEntry = new DictEntry(key,value);
		if(storage.contains(newEntry)) {
			DictEntry oldEntry = (DictEntry) storage.get(storage.indexOf(newEntry));
			oldEntry.setValue(value);
		} else {
			storage.add(newEntry);
		}
	}
	
	/**
	 * Retrieves entry value from dictionary using key to find it. Returns null if no entry is found.
	 * @param key key of entry
	 * @return entry value
	 */
	public Object get(Object key) {
		DictEntry searchForEntry = new DictEntry(key,null);
		if(storage.contains(searchForEntry)) {
			DictEntry foundEntry = (DictEntry) storage.get(storage.indexOf(searchForEntry));
			return foundEntry.getValue();
		} else {
			return null;
		}
	}
	
}
