package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getListOfPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		
		try(PreparedStatement pst = con.prepareStatement("SELECT id, title FROM Polls ORDER BY id")) {
			try(ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					Poll poll = new Poll(rs.getInt(1), rs.getString(2), null);
					polls.add(poll);
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while trying to get polls from database.", ex);
		}
		
		return polls;
	}
	
	
	@Override
	public Poll getPoll(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		
		try(PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls WHERE id=" + id)) {
			try(ResultSet rs = pst.executeQuery()) {
				if(rs!=null && rs.next()) {
					return new Poll(rs.getInt(1), rs.getString(2), rs.getString(3));
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while trying to get polls from database.", ex);
		}
		
		return null;
	}

	@Override
	public List<PollOption> getPollOptions(long id) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		
		try(PreparedStatement pst = con.prepareStatement("SELECT id, optionTitle FROM PollOptions "
				+ "WHERE pollID=" + id + " ORDER BY id")) {
			try(ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					PollOption pollOption = new PollOption(rs.getInt(1), rs.getString(2), null, 0);
					pollOptions.add(pollOption);
				}
			}
		
		} catch (Exception ex) {
			throw new DAOException("Error while trying to get polls from database.", ex);
		}
		return pollOptions;
	}

	
	@Override
	public List<PollOption> getPollResults(long id) throws DAOException {
		List<PollOption> pollResults = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		
		try(PreparedStatement pst = con.prepareStatement("SELECT id, optionTitle, optionLink, "
				+ "votesCount FROM PollOptions "
				+ "WHERE pollID=" + id + " ORDER BY id")) {
			try(ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					PollOption pollResult = new PollOption(rs.getInt(1), 
							rs.getString(2), rs.getString(3), rs.getInt(4));
					pollResults.add(pollResult);
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while trying to get polls from database.", ex);
		}
		return pollResults;
	}
	
	@Override
	public synchronized void incrementOptionVoteCount(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
	
		try(PreparedStatement pst = con.prepareStatement("UPDATE PollOptions "
				+ "SET VotesCount = VotesCount + 1 WHERE id=" + id)) {
			pst.executeUpdate();
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata anketa.", ex);
		}
	}
	
	@Override
	public int getPollID(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		int pollID;
		
		try(PreparedStatement pst = con.prepareStatement("SELECT PollID FROM PollOptions WHERE id=" + id)) {
			try(ResultSet rs = pst.executeQuery()) {
				if(rs!=null && rs.next()) {
					pollID = rs.getInt(1);
				} else {
					throw new DAOException("Option with requested ID does not exist in table.");
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while trying to get polls from database.", ex);
		}

		return pollID;
	}
	
	
	@Override
	public void createTables(DataSource cpds) throws DAOException {
		Connection con;
		try {
			con = cpds.getConnection();
		} catch (Exception ex) {
			throw new DAOException("Error while getting connection", ex);
		}
		try(ResultSet polls = con.getMetaData().getTables(null, null, "POLLS", null);
				ResultSet pollOptions = con.getMetaData().getTables(null, null, "POLLOPTIONS", null)) {
			if (!polls.next()) {
				try(PreparedStatement pst = con.prepareStatement("CREATE TABLE Polls" + 
						" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
						" title VARCHAR(150) NOT NULL," + 
						" message CLOB(2048) NOT NULL)")) {
					pst.executeUpdate();
				}
			}
			
			if (!pollOptions.next()) {
				try(PreparedStatement pst = con.prepareStatement("CREATE TABLE PollOptions" + 
						" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
						" optionTitle VARCHAR(100) NOT NULL," + 
						" optionLink VARCHAR(150) NOT NULL," + 
						" pollID BIGINT," + 
						" votesCount BIGINT," + 
						" FOREIGN KEY (pollID) REFERENCES Polls(id))")) {
					pst.executeUpdate();
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while trying to create tables.", ex);
		}
	}


	@Override
	public void fillTables(DataSource cpds) throws DAOException {
		long firstPollID = 1;
		long secondPollID = 2;
		Connection con;
		try {
			con = cpds.getConnection();
		} catch (Exception ex) {
			throw new DAOException("Error while getting connection", ex);
		}
		try(Statement st = con.createStatement()) {
			if(checkIfTableEmpty(cpds, "Polls")) {
				st.executeUpdate("INSERT INTO Polls(title, message) VALUES\r\n" + 
						"('Glasanje za omiljeni bend', 'Od sljedećih bendova, koji vam "
						+ "je bend najdraži? Kliknite na link kako biste glasali!')",
						Statement.RETURN_GENERATED_KEYS);
				ResultSet firstKey = st.getGeneratedKeys();
				if (firstKey != null && firstKey.next()) {
					firstPollID = firstKey.getLong(1);
				}
				
				st.executeUpdate("INSERT INTO Polls(title, message) VALUES\r\n" + 
						"('Pobjednik svjetskog prvenstva', 'Što mislite tko će osvojiti "
						+ "Svjetsko nogometno prvenstvo u Rusiji? Kliknite na link kako biste glasali!')",
						Statement.RETURN_GENERATED_KEYS);
				ResultSet secondKey = st.getGeneratedKeys();
				if (secondKey != null && secondKey.next()) {
					secondPollID = secondKey.getLong(1);
				}
				
			}
			if(checkIfTableEmpty(cpds, "PollOptions")) {
				st.executeUpdate("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES" + 
						"('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg', " + firstPollID + ", 0)," + 
						"('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', " + firstPollID + ", 0)," + 
						"('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', " + firstPollID + ", 0)," + 
						"('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', " + firstPollID + ", 0)," + 
						"('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', " + firstPollID + ", 0)," + 
						"('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', " + firstPollID + ", 0)," + 
						"('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', " + firstPollID + ", 0)");
				st.executeUpdate("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES" + 
						"('Brazil', 'https://www.fifa.com/worldcup/teams/team/43924/', " + secondPollID + ", 0)," + 
						"('Argentina', 'https://www.fifa.com/worldcup/teams/team/43922/', " + secondPollID + ", 0)," + 
						"('Njemačka', 'https://www.fifa.com/worldcup/teams/team/43948/', " + secondPollID + ", 0)," + 
						"('Francuska', 'https://www.fifa.com/worldcup/teams/team/43946/', " + secondPollID + ", 0)," + 
						"('Španjolska', 'https://www.fifa.com/worldcup/teams/team/43969/', " + secondPollID + ", 0)," + 
						"('Hrvatska', 'https://www.fifa.com/worldcup/teams/team/43938/', " + secondPollID + ", 0)," + 
						"('Rusija', 'https://www.fifa.com/worldcup/teams/team/43965/', " + secondPollID + ", 0)");
			}
					
		} catch (Exception ex) {
			throw new DAOException("Error while trying to fill tables.", ex);
		}
	}
	
	/**
	 * Check if database table from second argument is empty or not.
	 * @param cpds connection pool
	 * @param tableName name of table
	 * @return true of table is empty, false otherwise
	 * @throws SQLException
	 */
	private boolean checkIfTableEmpty(DataSource cpds, String tableName) throws SQLException {
		Connection con = cpds.getConnection();
		try(PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) as COUNTER FROM " + tableName)){
			ResultSet rset = pst.executeQuery();
			rset.next();
			int number = rset.getInt("COUNTER");
			return number==0;
		}
	}
	
}