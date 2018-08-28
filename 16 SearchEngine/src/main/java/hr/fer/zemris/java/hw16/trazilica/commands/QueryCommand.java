package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.ConsoleStatus;
import hr.fer.zemris.java.hw16.trazilica.environment.Document;
import hr.fer.zemris.java.hw16.trazilica.environment.Environment;
import hr.fer.zemris.java.hw16.trazilica.environment.SearchResult;
import hr.fer.zemris.java.hw16.trazilica.utils.VectorUtils;

/**
 * Query command updates results map with results of similarity computations.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class QueryCommand implements ConsoleCommand {

	@Override
	public ConsoleStatus execute(Environment env, String arguments) {
		double[] queryVector = new double[env.getVocabulary().size()];
		
		StringBuilder wordBuilder = new StringBuilder();
		for(Character letter : arguments.toCharArray()) {	
			if(!Character.isAlphabetic(letter)) {
				String word = wordBuilder.toString().toLowerCase().trim();
				int vectorIndex = env.getVocabulary().indexOf(word);
				if(vectorIndex>=0) {
					queryVector[vectorIndex] = queryVector[vectorIndex] + 1;
				}
				wordBuilder.setLength(0);
			} else {
				wordBuilder.append(letter);
			}
		}
		queryVector = VectorUtils.multiplyVectors(queryVector, env.getIdfVector());
		env.getResults().clear();
		for(Document document : env.getDocuments()) {
			double similarity = VectorUtils.calculateSimilarity(queryVector, document.getVector());
			if(similarity>0) {
				env.getResults().add(new SearchResult(document, similarity));
			}
		}
		
		return ConsoleStatus.CONTINUE;	
	}

	@Override
	public String getCommandName() {
		return "query";
	}
}
