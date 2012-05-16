package org.wwward;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
//		System.out.println("For context: " + predicates + "\n" + model.getAllOutcomes(ocs) + "\n");
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
		String dataFileName, modelFileName, outFileName;
		dataFileName = args[0];
		modelFileName = args[1];
		outFileName = args[2]; // output file with added tag
		
		FileWriter outFile = null;
		try {
			outFile = new FileWriter(new File(outFileName)); // open file for write
		} catch (Exception e) {
			System.err.println("main: ERROR unable to open output file for write.");
			e.printStackTrace();
		}
		
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
			//HashMap<String, Double> maxobs = new HashMap<String, Double>();
			while (ds.hasNext()) {
				String s = (String)ds.nextToken();
				obs.add(s);
				if (s.trim().isEmpty()) {
					HashMap<String, HashMap<String, Double>> V = new HashMap<String, HashMap<String, Double>>(); // regular Viterbi table of observations + probabilities
					HashMap<String, HashMap<String, Double>> maxValues = new HashMap<String, HashMap<String, Double>>(); //store highest valued B/I/O results per word
					LinkedList<String> path = new LinkedList<String>(); // tags will be added here and read out to a file later.
//					for (String observation : obs) {
//						double[] ocsresult = predictor.eval(observation, false);
//						
//					}
					for (String observation : obs) {
						if (!observation.trim().isEmpty()) {
							String[] temp = observation.split("\t");
							String substring = temp[0] + " " + temp[1]; // try adding the POS tag may14
							V.put(observation, predictor.eval(substring, false));
						}
					}
					for (String observation : obs) {
						if(!observation.trim().isEmpty()) {
							HashMap<String, Double> tempmap = V.get(observation);
							Map.Entry<String, Double> maxEntry = null;
							for (Map.Entry<String, Double> entry : tempmap.entrySet()) {
							    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
							        maxEntry = entry;
							    }
							}
							String tag = maxEntry.getKey();
							path.add(tag); // put the maximum scored tag in here - refactor this code
							Double tval = maxEntry.getValue(); //store max probability
							HashMap<String, Double> tempentry = new HashMap<String, Double>();
							tempentry.put(tag,tval); //just used to manipulate the loose key and value into a compatible type
							maxValues.put(observation, tempentry); // store maximum value for this observation
						}
					}
					System.out.println(obs);
					System.out.println(path);
					for (String observation : obs) {
						try {
							if (!observation.trim().isEmpty()) {
								String[] obsplit = observation.split("\t");
								String obsspace = obsplit[0] + " " + obsplit[1] + " " + obsplit[2]; //crude way of changing tabs to spaces for conlleval
								outFile.write(obsspace + " " + path.pop() + "\n");
							} else {
								outFile.write("\n"); // if space, just output newline
								outFile.flush();
							}
						} catch (IOException e) {
							System.err.println("main: ERROR Unable to write out in final output step.");
							e.printStackTrace();
						}
					}
					maxValues.clear(); // reset for next loop
					obs.clear(); //reset for next loop
					
				//} else {
				//String sub = s.substring(0, s.lastIndexOf('\t')); //not used calling viterbi
				//predictor.eval(sub); use this from within viterbi
				//add to sentence
				}
				//call to viterbi unless empty, then quit
			}
			try {
				outFile.close();
			} catch (IOException e) {
				System.err.println("main: ERROR unable to close output file.");
				e.printStackTrace();
			}
			return;
			
		} catch (FileNotFoundException e) {
			System.err.println("main: ERROR opening dataFileName");
			e.printStackTrace();
		}
	}
}
