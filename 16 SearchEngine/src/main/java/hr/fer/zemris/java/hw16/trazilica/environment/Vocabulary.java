package hr.fer.zemris.java.hw16.trazilica.environment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import hr.fer.zemris.java.hw16.trazilica.ConsoleIOException;

/**
 * Models search engine vocabulary.
 * Vocabulary is list of all words appearing in all document excluding stop-words.
 * Vocabulary is case insensitive.
 * Words are determined by Character.isAlphabetic().
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Vocabulary {
	/**
	 * Path to file containing stop-words.
	 */
	private static final String STOP_WORDS_FILE_PATH = "src/main/resources/hrvatski_stoprijeci.txt";

	/**
	 * Unmodifiable list of vocabulary words.
	 */
	private List<String> vocabulary;

	/**
	 * Default constructor for Vocabulary.
	 * @param dir
	 */
	public Vocabulary(Path dir) {
		VocabularyBuilderVisitor visitor = new VocabularyBuilderVisitor(Paths.get(STOP_WORDS_FILE_PATH));
		try {
			Files.walkFileTree(dir, visitor);
		} catch (IOException e) {
			throw new ConsoleIOException("Error while building vocabulary.");
		}
		vocabulary = Collections.unmodifiableList(new ArrayList<>(visitor.getVocabularySet()));	
	}

	/**
	 * Get size of vocabulary.
	 * @return vocabulary size
	 */
	public int size() {
		return vocabulary.size();
	}
	
	/**
	 * Get index of word given in argument.
	 * @param word searched word
	 * @return index of word or -1 if word is not found
	 */
	public int indexOf(String word) {
		return vocabulary.indexOf(word);
	}
	
	/**
	 * Visitor used for building vocabulary.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class VocabularyBuilderVisitor extends SimpleFileVisitor<Path> {
		/**
		 * Set of stop words. Stop words are not included in vocabulary.
		 */
		private Set<String> stopWords;
		/**
		 * Set collection of vocabulary words.
		 */
		private SortedSet<String> vocabularySet = new TreeSet<>();

		/**
		 * Default visitor constructor.
		 * @param stopWordsFile path to file containing stop-words.
		 */
		public VocabularyBuilderVisitor(Path stopWordsFile) {
			try {
				stopWords = new HashSet<>(Files.readAllLines(stopWordsFile, StandardCharsets.UTF_8));
			} catch (IOException e) {
				throw new ConsoleIOException("Error while reading stop words configuration.");
			}
			
		}
		
		/**
		 * Getter for vocabulary set.
		 * @return vocabulary set
		 */
		public Set<String> getVocabularySet() {
			return vocabularySet;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			StringBuilder wordBuilder = new StringBuilder();
			for(String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
				for(Character letter : (line + "\n").toCharArray()) {	
					if(!Character.isAlphabetic(letter)) {
						String word = wordBuilder.toString().toLowerCase().trim();
						if(!stopWords.contains(word)) {
							vocabularySet.add(word);
						}
						wordBuilder.setLength(0);
					} else {
						wordBuilder.append(letter);
					}
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}
}
