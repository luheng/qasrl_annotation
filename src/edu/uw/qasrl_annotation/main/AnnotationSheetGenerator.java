package edu.uw.qasrl_annotation.main;

import edu.uw.qasrl_annotation.config.GenerationConfig;
import edu.uw.qasrl_annotation.data.Corpus;
import edu.uw.qasrl_annotation.data.Sentence;
import edu.uw.qasrl_annotation.data.TargetPredicate;
import edu.uw.qasrl_annotation.data.VerbInflectionDictionary;
import edu.uw.qasrl_annotation.io.XSSFDataRetriever;
import edu.uw.qasrl_annotation.io.XSSFOutputHelper;

import java.io.IOException;
import java.util.ArrayList;

public class AnnotationSheetGenerator {
	static GenerationConfig config = null;
	static Corpus corpus = null;
	static VerbInflectionDictionary inflDict = null;
	static ArrayList<Sentence> sentences = null;
	static ArrayList<ArrayList<TargetPredicate>> predicates = null;

	private static void loadData() throws IOException {
		corpus = new Corpus("qa");
		corpus.loadSentenceWithPredicates(config.inputFilePath);
		inflDict = new VerbInflectionDictionary(corpus);
		inflDict.loadDictionaryFromFile(config.verbInflPath);
	}

	private static void generateAnnotationSheet() throws IOException {
		XSSFOutputHelper.outputXlsx(corpus.sentences, corpus.predicates,
				inflDict, config.outputFilePath);
	}


	public static void main(String[] args) {
		config = new GenerationConfig(args);
		config.print(System.out);
		try {
			loadData();
			XSSFOutputHelper.maxNumSheetsPerFile = config.maxNumSheetsPerFile;
			XSSFOutputHelper.maxNumSentsPerSheet = config.maxNumSentsPerSheet;
			XSSFOutputHelper.maxNumQAs = config.maxNumQAs;
			generateAnnotationSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
