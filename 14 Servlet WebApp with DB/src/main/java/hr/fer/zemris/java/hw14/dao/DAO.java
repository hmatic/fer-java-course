package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Interface to sub-system for data perisistence.
 * 
 * @author Hrvoje Matic
 *
 */
public interface DAO {
	/**
	 * Retrieves a list of all available polls.
	 * @return list of available polls
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	List<Poll> getListOfPolls() throws DAOException;
	
	/**
	 * Retrieves a list of all poll options in single poll defined by ID in argument. 
	 * Options contain only titles and IDs, for bigger option data check method getPollResults.
	 * @param id poll ID
	 * @return list of poll options
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	List<PollOption> getPollOptions(long id) throws DAOException;

	/**
	 * Retrieves a list of poll results in a single poll defined by ID in argument.
	 * Results contain all data from poll option database row.
	 * @param id poll ID
	 * @return list of poll results 
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	List<PollOption> getPollResults(long id) throws DAOException;

	/**
	 * Increments vote counter in poll option table by 1.
	 * @param id option ID
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	void incrementOptionVoteCount(long id) throws DAOException;
	
	/**
	 * Returns poll ID for poll option defined by ID from argument.
	 * @param id option ID
	 * @return poll ID
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	int getPollID(long id) throws DAOException;

	/**
	 * Returns poll data for poll ID specified in argument.
	 * @param id poll ID
	 * @return poll data
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	Poll getPoll(long id) throws DAOException;

	/**
	 * Creates tables Polls and PollOptions if they do not exist.
	 * @param cpds connection pool
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	void createTables(DataSource cpds) throws DAOException;

	/**
	 * Fills tables Polls and PollOptions if they are empty.
	 * @param cpds data connection pool
	 * @throws DAOException wrapper exception for all other exceptions
	 */
	void fillTables(DataSource cpds) throws DAOException;
	
}