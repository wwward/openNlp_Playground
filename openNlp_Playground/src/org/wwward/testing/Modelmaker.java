package org.wwward.testing;

import opennlp.maxent.BasicEventStream;
import opennlp.maxent.GIS;
import opennlp.maxent.PlainTextByLineDataStream;
import opennlp.maxent.io.GISModelWriter;
import opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.model.AbstractModel;
import opennlp.model.EventStream;

import java.io.*;

public class Modelmaker {
	
	/**
	 * @param args file
	 */
	public static void main(String[] args) {
		
		//Open File
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File("/Users/wwward/Downloads/NounGroupsforNLPClass/train.np"));
		} catch (FileNotFoundException e) {
			System.err.println("main: ERROR Unable to open file.");
			e.printStackTrace();
		}
		
		//Read step
		EventStream es = new BasicEventStream(new PlainTextByLineDataStream(fileReader),"\t");
		
		//Model step
		AbstractModel model = null;
		try {
			model = GIS.trainModel(es); //should I use smoothing here?
		} catch (IOException e) {
			System.err.println("main: ERROR in model = GIS step.");
			e.printStackTrace();
		} 
		
		//Output step
		File outputFile = new File("/Users/wwward/Downloads/NounGroupsforNLPClass/train.txt");
		try {
			GISModelWriter writer = new SuffixSensitiveGISModelWriter(model, outputFile);
			writer.persist();
		} catch (IOException e) {
			System.err.println("main: ERROR in writer step.");
			System.out.println(e);
		}
	}

}
