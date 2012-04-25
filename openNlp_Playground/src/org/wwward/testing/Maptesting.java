package org.wwward.testing;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maptesting {
	
	//Bigram Frequency Counts
	// 2D hashmap to form lookup table
	// key1 = (String) POS "Column", (String) key2 = POS "Row", (Integer) Value = Frequency
	private class bigramFrequency {
		HashMap<String, HashMap<String, Integer>> columns;
		
		bigramFrequency() {
			columns = new HashMap<String, HashMap<String, Integer>>();
		}
		
		public void put(String key1, String key2, Integer value) {
			HashMap row = columns.get(key1);
			if (row == null) {
				row = new HashMap<String, Integer>();
				columns.put(key1, row);
			}
			row.put(key2, value);
		}
		
		public Integer get(String key1, String key2) {
			HashMap row = columns.get(key1);
			if (row == null) {
				return null;
			}
			return (Integer) row.get(key2);
		}
		
		public String outerKeys(){
			return columns.keySet();
		}
		
	}
	
	// Unigram Frequency Counts
	// Wrapper for a simple hashmap in case I want to add more later.
	// Key = (String) POS, Value = (Integer) Frequency
	private class unigramFrequency {
		HashMap<String, Integer> table;
		
		unigramFrequency() {
			table = new HashMap<String, Integer>();
		}
		
		public void put(String key1, Integer value) {
			table.put(key1, value);
		}
		
		public Integer get(String key1) {
			return table.get(key1);
		}
		
	}
	
	Maptesting(File file) {
		BufferedReader br = null;
		try {
			 br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Unable to open file.");
			e.printStackTrace();
		}
		
		bigramFrequency m = new bigramFrequency();
		this.doSomething(m, br);
	}
	
	
	
	void doSomething(bigramFrequency m, BufferedReader br) {
		String previousTag = "<s>";
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				String line0 = line.trim();
				if (line0.isEmpty()){
					System.out.println("Space!");
					previousTag = "<s>"; // reset to start symbol
				} else {
					String[] lineSplit = line0.split("\\s+");
					Integer data = m.get(lineSplit[1], previousTag); //m.get returns an Integer
				    if (data != null) {
				    	m.put(lineSplit[1], previousTag, (data += 1)); //increment frequency
				    } else {
				    	m.put(lineSplit[1], previousTag, 1); //initial
				    }
				    previousTag = lineSplit[1]; //advance the previous tag
				}
			}
		} catch (IOException e) {
			System.err.println("ERROR: Fail in read loop.");
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			System.err.println("ERROR: Can't close file?");
			e.printStackTrace();
		}
		
		System.out.println(m.columns.toString());
	}
	
	

	/**
	 * @param takes nothing on input
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		Maptesting maptest = new Maptesting(file);

	}

}
