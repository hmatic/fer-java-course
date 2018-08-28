package hr.fer.zemris.java.hw16.trazilica.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw16.trazilica.ConsoleStatus;
import hr.fer.zemris.java.hw16.trazilica.environment.Environment;
import hr.fer.zemris.java.hw16.trazilica.environment.SearchResult;

/**
 * Command which sorts and prints top 10 search results to console.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class ResultsCommand implements ConsoleCommand {

	@Override
	public ConsoleStatus execute(Environment env, String arguments) {
		int i = 0;
		List<SearchResult> results = env.getResults();
		Collections.sort(results);
		if(results.size()==0) {
			env.writeln("No results for given query.");
			return ConsoleStatus.CONTINUE;
		}
		env.writeln("Najboljih 10 rezultata:");
		for(SearchResult result : results) {
			if(i==10) break;
			env.writeln(String.format("[ %d] (%.4f) %s",
					i++, result.getSimilarity(), 
					result.getDocument().getDocPath().toAbsolutePath().toString()));
		}
		
		return ConsoleStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "results";
	}

}
