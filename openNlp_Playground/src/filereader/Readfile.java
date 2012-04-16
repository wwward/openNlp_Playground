package filereader;

import java.io.*;

public class Readfile {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		BufferedReader br = null;
		try {
			 br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Unable to open file.");
			e.printStackTrace();
		}
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				String line0 = line.trim();
				if (line0.isEmpty()){
					System.out.println("Space!");
				} else {
					String[] lineSplit = line0.split("\\s+");
					System.out.println(lineSplit[1]);
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
	}

}
