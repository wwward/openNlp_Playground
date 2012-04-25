package org.wwward.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Stripper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("/Users/wwward/Downloads/NounGroupsforNLPClass/train.np")));
			FileWriter fw = new FileWriter(new File("/Users/wwward/Downloads/NounGroupsforNLPClass/train.stripped.txt"));
			
			while (br.ready()) {
				String s = br.readLine();
				String[] split = s.split("\t");
				fw.write(split[0] + "\n");
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found error.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
