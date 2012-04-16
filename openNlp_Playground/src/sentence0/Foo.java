package sentence0;

import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Foo {

	public static void main(String[] args) {

		String paragraph = "This is a sentence.  This is another sentence.  This is a third sentence.";
		
		FileInputStream modelInpStream = null;
		try {
			modelInpStream = new FileInputStream("/Users/wwward/Downloads/en-sent.bin");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SentenceModel model = null;
		try {
			model = new SentenceModel(modelInpStream);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		String[] sents = sdetector.sentDetect(paragraph);
		
		InputStream tokmodelInpStream = null;
		try {
			tokmodelInpStream = new FileInputStream("/Users/wwward/Downloads/en-token.bin");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TokenizerModel tokmodel = null;
		try {
			tokmodel = new TokenizerModel(tokmodelInpStream);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Tokenizer tokenizer = new TokenizerME(tokmodel);
		
		String tokens[] = tokenizer.tokenize(sents[0]);
		System.out.println(tokens[1]);

	}
}
