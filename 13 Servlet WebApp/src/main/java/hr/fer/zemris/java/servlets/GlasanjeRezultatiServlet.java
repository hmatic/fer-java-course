package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which calculates results in voting part of application.
 * Three data structures are passed to JSP on rendering:
 * -List with VoteResult objects(id, voteCount)
 * -Map with ID as key and BandData(name, songLink) as value
 * -List with winner IDs
 * 
 * If no vote results file exists, all bands will have 0 votes by default.
 * 
 * @author Hrvoje Matic
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Relative path to voting definition storage file.
	 */
	private static final String VOTE_DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";
	/**
	 * Relative path to voting results storage file.
	 */
	private static final String VOTE_RESULTS_PATH = "/WEB-INF/glasanje-rezultati.txt";
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<VoteResult> results = new ArrayList<>();
		Map<String, BandData> bands = new HashMap<>();
		for(String line : AppUtils.loadLines(req, VOTE_DEFINITION_PATH)) {
			String[] lineParts = line.split("\t");
			bands.put(lineParts[0], new BandData(lineParts[1], lineParts[2]));
		}
		if(Files.exists(Paths.get(req.getServletContext().getRealPath(VOTE_RESULTS_PATH)))) {
			for(String line : AppUtils.loadLines(req, VOTE_RESULTS_PATH)) {
				String[] lineParts = line.split("\t");
				String resultID = lineParts[0];
				if(bands.containsKey(resultID)) {
					results.add(new VoteResult(resultID, Integer.parseInt(lineParts[1])));
				}
			}
		} else {
			for(String id : bands.keySet()) {
				results.add(new VoteResult(id, Integer.valueOf(0)));
			}
		}
		Collections.sort(results);
		Collections.reverse(results);
		
		List<String> winners = new ArrayList<>();
		int winnerVoteCount = results.get(0).getVoteCount();
		for(VoteResult result : results) {
			if(result.getVoteCount()==winnerVoteCount) {
				winners.add(result.getId());
			}
		}
		
		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * Models vote results.
	 * Contains ID of band and number of votes that given band received.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	public static class VoteResult implements Comparable<VoteResult> {
		/**
		 * Band ID.
		 */
		private String id;
		/**
		 * Number of votes received.
		 */
		private int voteCount;
		/**
		 * Default constructor.
		 * @param id band ID
		 * @param voteCount vote count
		 */
		public VoteResult(String id, int voteCount) {
			super();
			this.id = id;
			this.voteCount = voteCount;
		}
		/**
		 * Getter for ID.
		 * @return band ID
		 */
		public String getId() {
			return id;
		}
		/**
		 * Getter for vote count.
		 * @return vote count
		 */
		public int getVoteCount() {
			return voteCount;
		}
		@Override
		public int compareTo(VoteResult other) {
			return ((Integer)voteCount).compareTo(other.getVoteCount());
		}
	}
	
	/**
	 * Models band data.
	 * Contains name of band and their most famous song.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	public static class BandData {
		/**
		 * Band name.
		 */
		private String name;
		/**
		 * Link to song.
		 */
		private String songLink;
		/**
		 * Default constructor.
		 * @param name band name
		 * @param songLink link to song
		 */
		public BandData(String name, String songLink) {
			super();
			this.name = name;
			this.songLink = songLink;
		}
		/**
		 * Getter for band name.
		 * @return band name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Getter for song link.
		 * @return song link
		 */
		public String getSongLink() {
			return songLink;
		}	
	}
}
