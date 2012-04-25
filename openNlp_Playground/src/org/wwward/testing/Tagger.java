package org.wwward.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	private HashMap<String, Double> eval (String predicates, boolean real) {
		String[] contexts = predicates.split(" ");
		double[] ocs;
		HashMap<String, Double> outcomes = new HashMap<String, Double>();
		ocs = model.eval(contexts);
		System.out.println("For context: " + predicates + "\n" + model.getAllOutcomes(ocs) + "\n");
//		System.out.println("Best: " + model.getBestOutcome(ocs) + "\n");
		for (int i = 0; (i < ocs.length); i++) {
			outcomes.put(model.getOutcome(i), ocs[i]);
		}
		return outcomes;
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
			ArrayList<String> obs = new ArrayList<String>();
			HashMap<String, Double> maxobs = new HashMap<String, Double>();
			while (ds.hasNext()) {
				String s = (String)ds.nextToken();
				obs.add(s);
				if (s.trim().isEmpty()) {
					HashMap<String, HashMap<String, Double>> V = new HashMap<String, HashMap<String, Double>>();
//					for (String observation : obs) {
//						double[] ocsresult = predictor.eval(observation, false);
//						
//					}
					for (String observation : obs) {
						if (!observation.trim().isEmpty()) {
							String[] temp = observation.split("\t");
							String substring = temp[0];
							V.put(observation, predictor.eval(substring, false));
						}
					}
					for (String observation : obs) {
						if(!observation.trim().isEmpty()) {
							HashMap<String, Double> tempmap = V.get(observation);
							HashMap<String, Double> hashmap = 
						}
					}
					
					obs.clear(); //reset for next loop
					
				//} else {
				//String sub = s.substring(0, s.lastIndexOf('\t')); //not used calling viterbi
				//predictor.eval(sub); use this from within viterbi
				//add to sentence
				}
				//call to viterbi unless empty, then quit
			}
			return;
			
		} catch (FileNotFoundException e) {
			System.err.println("main: ERROR opening dataFileName");
			e.printStackTrace();
		}
	}
}
