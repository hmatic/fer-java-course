package hr.fer.zemris.java.hw16.trazilica.environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.trazilica.ConsoleIOException;
import hr.fer.zemris.java.hw16.trazilica.utils.VectorUtils;

/**
 * Environment implementation.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class EnvironmentImpl implements Environment {
	/**
	 * Vocabulary.
	 */
	private Vocabulary vocabulary;
	/**
	 * List of search results.
	 */
	private List<SearchResult> results = new ArrayList<>();
	/**
	 * List of documents in search database.
	 */
	private List<Document> documents = new ArrayList<>();
	/**
	 * IDF vector.
	 */
	private double[] idfVector;
	
	/**
	 * Input stream.
	 */
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	/**
	 * Output stream.
	 */
	private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
	
	/**
	 * Default constructor.
	 * @param dir directory containing documents
	 */
	public EnvironmentImpl(Path dir) {
		vocabulary = new Vocabulary(dir);
		VectorBuilderVisitor builderVisitor = new VectorBuilderVisitor();
		try {
			Files.walkFileTree(dir, builderVisitor);
		} catch (IOException e) {
			throw new ConsoleIOException("Problem while building vocabulary.");
		}
		idfVector = new double[vocabulary.size()];
		generateIdfVector(builderVisitor.getWordOccurrence());
		
		for(Document document : documents) {
			document.setVector(VectorUtils.multiplyVectors(document.getVector(), idfVector));
		}
	}

	@Override
	public List<SearchResult> getResults() {
		return results;
	}
		
	@Override
	public List<Document> getDocuments() {
		return documents;
	}

	@Override
	public double[] getIdfVector() {
		return idfVector;
	}
	
	@Override
	public void write(String text) {
		try {
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			throw new ConsoleIOException("Error while writing to output.");
		}			
	}
	
	@Override
	public void writeln(String text) {		
		write(text + "\n");
	}
	
	@Override
	public Vocabulary getVocabulary() {
		return vocabulary;
	}


	@Override
	public String readLine() {
		String line;
		try {
			writer.write("Enter command > ");
			writer.flush();
				
			line = reader.readLine();
			
		} catch (IOException e) {
			throw new ConsoleIOException("Error while reading from input.");
		}
		
		return line;
	}

	/**
	 * File visitor which builds vectors of all documents. 
	 * Also builds wordOccurrence vector used to generate IDF vector.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class VectorBuilderVisitor extends SimpleFileVisitor<Path> {
		/**
		 * Number of documents word has occurred in.
		 */
		private int[] wordOccurrence = new int[vocabulary.size()];
		
		/**
		 * Getter for word occurrence.
		 * @return word occurrence
		 */
		public int[] getWordOccurrence() {
			return wordOccurrence;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
			double[] documentVector = new double[vocabulary.size()];
			StringBuilder wordBuilder = new StringBuilder();
			for(String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
				for(Character letter : (line + "\n").toCharArray()) {	
					if(!Character.isAlphabetic(letter)) {
						String word = wordBuilder.toString().toLowerCase().trim();
						int vectorIndex = vocabulary.indexOf(word);
						if(vectorIndex>=0) {
							documentVector[vectorIndex] = documentVector[vectorIndex] + 1;
						}
						wordBuilder.setLength(0);
					} else {
						wordBuilder.append(letter);
					}
				}
			}
			documents.add(new Document(file, documentVector));
			for(int i=0; i<documentVector.length; i++) {
				if(documentVector[i]>0) {
					wordOccurrence[i] = wordOccurrence[i] + 1;
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}
	
	/**
	 * Generate IDF vector.
	 * @param occurrences wordOccurrence vector
	 */
	private void generateIdfVector(int[] occurrences) {
		int numberOfDocuments = documents.size();
		for(int i=0, idfSize=idfVector.length; i<idfSize; i++) {
			idfVector[i] = Math.log(numberOfDocuments/occurrences[i]);
		}
	}
	
}
