package org.wwward.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import opennlp.maxent.BasicContextGenerator;
import opennlp.maxent.ContextGenerator;
import opennlp.maxent.DataStream;
import opennlp.maxent.PlainTextByLineDataStream;
import opennlp.model.GenericModelReader;
import opennlp.model.MaxentModel;

public class Tagger {

	MaxentModel model = null;
	ContextGenerator cg = new BasicContextGenerator();
	
	public Tagger (MaxentModel m) {
		model = m;
	}
	
	private void eval (String predicates) {
		eval(predicates, false);
	}
	
	private void eval (String predicates, boolean real) {
		String[] contexts = predicates.split(" ");
		double[] ocs;
		ocs = model.eval(contexts);
		System.out.println("For context: " + predicates + "\n" + model.getAllOutcomes(ocs) + "\n");
	}
	
	
	
	/**
	 * @param args datafilename modelfilename
	 */
	public static void main(String[] args) {
		String dataFileName, modelFileName;
		dataFileName = args[0];
		modelFileName = args[1];
		
		Tagger predictor = null;
		
		try {
			MaxentModel m = new GenericModelReader(new File(modelFileName)).getModel();
			predictor = new Tagger(m);
		} catch (Exception e) {
			System.err.println("main: ERROR in GenericModelReader/Tagger.");
			e.printStackTrace();
		}
		
		try {
			DataStream ds = new PlainTextByLineDataStream(new FileReader(new File(dataFileName)));
			
			while (ds.hasNext()) {
				String s = (String)ds.nextToken();
				System.out.println(s);
				//String sub = s.substring(0, s.lastIndexOf('\t'));
				String[] split = s.split("\t");
				String sub = split[0];
				System.out.println(sub); //debug
				predictor.eval(sub);
			}
			return;
			
		} catch (FileNotFoundException e) {
			System.err.println("main: ERROR opening dataFileName");
			e.printStackTrace();
		}

	}

}
