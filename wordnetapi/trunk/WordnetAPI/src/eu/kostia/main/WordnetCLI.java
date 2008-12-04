package eu.kostia.main;

import java.io.*;
import java.util.*;

import eu.kostia.entity.*;

public class WordnetCLI {

	private String term;

	public WordnetCLI(String term) {
		this.term = term;
	}

	public void printResult(PrintStream out) {
		List<Word> words = getResult();
		if (words == null || words.isEmpty()) {
			out.println("Your search did not return any results.");
			return;
		}

		for (Word word : words) {
			out.print("LEMMA: " + word.getLemma() + " ("
					+ word.getLexicalType() + "): ");
			out.println(word.getDefinition());
			out.println("SEMANTIC SET:");
			Relation[] synset = word.getSynset().toArray(new Relation[0]);
			Arrays.sort(synset);

			SemanticRelationType lastRelationType = null;
			for (Relation relation : synset) {
				SemanticRelationType relationType = relation
						.getSemanticRelationType();
				if (relationType != lastRelationType) {
					out.println(" " + relationType + ":");
				}
				lastRelationType = relationType;

				Word target = relation.getTarget();
				out.print("   ");
				out.print(target.getLemma() + " (" + target.getLexicalType()
						+ "): ");
				out.println(target.getDefinition());
			}
			out.println();
		}
	}

	List<Word> getResult() {
		WordnetDictionary dictionary = new WordnetDictionary();
		return dictionary.lookup(term);
	}

	public static void main(String... args) {
		if (args.length != 1) {
			printUsage();
			return;
		}
		String lemma = args[0];
		if (lemma == null) {
			printUsage();
			return;
		}
		if (lemma.isEmpty()) {
			printUsage();
			return;
		}

		WordnetCLI cli = new WordnetCLI(lemma);
		cli.printResult(System.out);

	}

	private static void printUsage() {
		System.out.println("Usage: wn <lemma>");
	}
}
