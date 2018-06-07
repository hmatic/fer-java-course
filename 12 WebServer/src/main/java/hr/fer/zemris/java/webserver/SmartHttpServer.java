package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngineException;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * IMPORTANT:
 * In order to run server properly, please configure paths in server.properties!
 * 
 * A Web server is a program that uses HTTP (Hypertext Transfer Protocol) 
 * to serve the files that form Web pages to users, in response to their requests, 
 * which are forwarded by their computers' HTTP clients. Dedicated computers and 
 * appliances may be referred to as Web servers as well.
 * 
 * SmartHttpServer provides basic server functionalities. It also supports SmartScript files,
 * both for execution or rendering other HTML pages.
 * Server also supports cookies and sessions.
 * SmartHttpServer provides features of MVC design: You can create WebWorker to process data and
 * then you can dispatch processed data to SmartScript to render it to HTML page. 
 * 
 * Some examples of server functionalities can be found on following paths:
 * "/index2.html"
 * "/scripts/fibonaccih.smscr"
 * "/ext/EchoParams?param1=abc&param2=def"
 * "/cw"
 * "/hello"
 * "/calc?a=3&b=34"
 * "/fruits.png"
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class SmartHttpServer {
	/**
	 * Program entry point.
	 * Takes 1 argument which is path to server config file.
	 * @param args list of arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Must provide a single argument: path to server.properties.");
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}
	
	/**
	 * Server address.
	 */
	private String address;
	/**
	 * Server domain.
	 */
	private String domainName;
	/**
	 * Server port.
	 */
	private int port;
	/**
	 * Number of threads in pool.
	 */
	private int workerThreads;
	/**
	 * Session timeout duration.
	 */
	private int sessionTimeout;
	/**
	 * Map of mime types.
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Map of WebWorkers.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	/**
	 * Main server thread.
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool.
	 */
	private ExecutorService threadPool;
	/**
	 * Path to document root(web root)
	 */
	private Path documentRoot;
	/**
	 * Map of existing sessions.
	 */
	private Map<String, SessionMapEntry> sessions =
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Randomizer for SID generation.
	 */
	private Random sessionRandom = new Random();
	/**
	 * Collector of expired sessions.
	 */
	private SessionCollector sessionCollector = new SessionCollector();
	
	/**
	 * SID length.
	 */
	private static final int SID_LENGTH = 20;
	/**
	 * Page not found(404) message.
	 */
	public static final String PAGE_NOT_FOUND_MSG = "Page not found";
	/**
	 * Forbidden(403) message.
	 */
	public static final String FORBIDDEN_MSG = "Forbidden";
	/**
	 * Bad request(400) message.
	 */
	public static final String BAD_REQUEST_MSG = "Bad request";
	
	/**
	 * Default constructor for SmartHttpServer.
	 * Takes all values from properties files and configures server.
	 * @param configFileName path to server config file
	 */
	public SmartHttpServer(String configFileName) {
		Properties config = new Properties();
		Properties mimeConfig = new Properties();
		Properties workerConfig = new Properties();
		try {
			config.load(new FileInputStream(configFileName));
			mimeConfig.load(new FileInputStream(config.getProperty("server.mimeConfig")));
			workerConfig.load(new FileInputStream(config.getProperty("server.workers")));
		} catch (IOException e) {
			Logger.getLogger().log("Exception while loading properties files.", e);
		}
		this.address = config.getProperty("server.address").trim();
		this.domainName = config.getProperty("server.domainName");
		this.port = Integer.valueOf(config.getProperty("server.port").trim());
		this.workerThreads = Integer.valueOf(config.getProperty("server.workerThreads").trim());
		this.sessionTimeout = Integer.valueOf(config.getProperty("session.timeout").trim());
		this.documentRoot = Paths.get(config.getProperty("server.documentRoot").trim());
		
		for(Entry<Object, Object> entry : mimeConfig.entrySet()) {
			mimeTypes.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}
		
		for(Entry<Object, Object> entry : workerConfig.entrySet()) {
			Class<?> referenceToClass;
			Object newObject = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(String.valueOf(entry.getValue()));
				newObject = referenceToClass.getDeclaredConstructor().newInstance();
			} catch(Exception e) {
				Logger.getLogger().log("Exception creating worker objects.", e);
			}
			IWebWorker iww = (IWebWorker)newObject;
			workersMap.put(String.valueOf(entry.getKey()), iww);
		}
	}

	/**
	 * Start running server.
	 */
	protected synchronized void start() {
		this.threadPool = Executors.newFixedThreadPool(workerThreads);
		if (serverThread == null) {
			serverThread = new ServerThread();
			sessionCollector.setDaemon(true);
		}
		if(!serverThread.isAlive()) {
			serverThread.start();
			System.out.println("Server started...");
			
			sessionCollector.start();
		}
	}

	/**
	 * Stop running server.
	 */
	protected synchronized void stop() {
		serverThread.stopRunning();
		threadPool.shutdown();
	}

	/**
	 * Server main thread.
	 * @author Hrvoje Matic
	 */
	protected class ServerThread extends Thread {
		/**
		 * Server status flag.
		 */
		private volatile boolean running = true;
		
		@Override
		public void run() {	
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				while (running) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Stop running server. Take down status flag to false.
		 */
		public void stopRunning() {
			running = false;
		}
	}

	/**
	 * Worker that handles all requests given to server.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Start letter in SID generation.
		 */
		private static final int START_LETTER_CODE = 65;
		/**
		 * Number of possible letters in SID generation.
		 */
		private static final int LETTERS_COUNT = 25;
		/**
		 * Client socket.
		 */
		private Socket csocket;
		/**
		 * Request context.
		 */
		private RequestContext context = null;
		/**
		 * Input stream.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream.
		 */
		private OutputStream ostream;
		/**
		 * HTTP version.
		 */
		private String version;
		/**
		 * HTTP Request method.
		 */
		private String method;
		/**
		 * Request host.
		 */
		private String host;
		/**
		 * Map of parameters.
		 */
		private Map<String, String> params = new HashMap<>();
		/**
		 * Map of temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<>();
		/**
		 * Map of persistent parameters.
		 */
		private Map<String, String> permParams = new HashMap<>();
		/**
		 * List of output cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID.
		 */
		private String SID;

		/**
		 * Default ClientWorker constructor.
		 * @param csocket client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				this.istream = new PushbackInputStream(csocket.getInputStream());
				this.ostream = csocket.getOutputStream();
			
				List<String> request = readRequest();
				if(request==null) return;
				System.out.println("req:" + request);
				if(request.size()<1) {
					sendError(400, BAD_REQUEST_MSG);
				}
				String firstLine = request.get(0);
				
				String[] firstLineParts = firstLine.split(" ");
				this.method = firstLineParts[0].toUpperCase();
				String requestedPath = firstLineParts[1];
				this.version = firstLineParts[2].toUpperCase();
				
				if(!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
					sendError(400, BAD_REQUEST_MSG);
				}

				String[] requestedPathParts = requestedPath.split("\\?");
				
				String path = requestedPathParts[0];
				
				determineHost(request);
				
				checkSession(request);
				
				if(requestedPathParts.length==2) {
					String paramString = requestedPathParts[1];
					try {
						parseParameters(paramString);
					} catch(IllegalArgumentException ex) {
						sendError(400, BAD_REQUEST_MSG);
						Logger.getLogger().log("Exception while parsing parameters: " 
								+ ex.getMessage(), ex);
						return;
					}
				}
			
				internalDispatchRequest(path, true);
			
			} catch (Exception e) {
				Logger.getLogger().log("Exception in SmartHttpServer(ClientWorker run()).", e);
			} finally {
				try { csocket.close(); } catch (IOException ignorable) { }
			}
		}

		/**
		 * Determine host from HTTP request. If no host is given, set host to domainName.
		 * @param request http request
		 */
		private void determineHost(List<String> request) {
			for(String requestLine : request) {
				if(requestLine.startsWith("Host:")) {
					requestLine = requestLine.substring("Host:".length()).trim();
					if(requestLine.contains(":")) {
						int index = requestLine.indexOf(":");
						this.host = requestLine.substring(0, index);
						return;
					}
				}
			}
			this.host = domainName;
		}

		/**
		 * Method which handles sessions.
		 * Finds "sid" cookies and determines if sid exists in session map.
		 * Executes extra validations for cookie host and session length.
		 * @param request HTTP request lines
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = null;
			String[] cookieParts;
			for(String requestLine : request) {
				if(requestLine.startsWith("Cookie:")) {
					requestLine = requestLine.substring("Cookie:".length());
					String[] cookies = requestLine.split(";");
					for(String cookie : cookies) {
						cookie = cookie.trim();
						cookieParts = cookie.split("=");
						if(cookieParts[0].equals("sid")) {
							sidCandidate = cookieParts[1].replaceAll("\"", "");
							break;
						}
					}
				}
			}

			if(sessions.containsKey(sidCandidate)) {
				SessionMapEntry sessionEntry = sessions.get(sidCandidate);
			
				if(sessionEntry.host.equals(this.host) && 
						(System.currentTimeMillis()/1000) <= sessionEntry.validUntil) {
					sessionEntry.setValidUntil((System.currentTimeMillis()/1000)+sessionTimeout);
					this.permParams = sessionEntry.map;	
					return;
				} else if((System.currentTimeMillis()/1000) > sessionEntry.validUntil) {
					sessions.remove(sidCandidate);
				}
			}
			
			SID = generateSID();
			SessionMapEntry entry = new SessionMapEntry(SID, this.host, 
					(System.currentTimeMillis()/1000)+sessionTimeout, 
					new ConcurrentHashMap<String, String>());
			sessions.put(SID, entry);
			this.permParams = entry.map;
			outputCookies.add(new RCCookie("sid", SID, null, this.host, "/"));
		}

		/**
		 * Generate session ID. SID consists of SID_LENGTH characters which are between
		 * START_LETTER_CODE and (START_LETTER_CODE+LETTERS_COUNT).
		 * @return session ID
		 */
		private String generateSID() {
			StringBuilder sid = new StringBuilder();
			for(int i=0; i<SID_LENGTH; i++) {
				char c = (char)(sessionRandom.nextInt(LETTERS_COUNT)+START_LETTER_CODE);
				sid.append(c);
			}
			return sid.toString();
		}

		/**
		 * Parse parameters from argument and put them in parameters map.
		 * @param paramString parameters string
		 * @throw IllegalArgumentException if parameters format is invalid
		 */
		private void parseParameters(String paramString) {
			for(String parameter : paramString.split("&")) {
				if(parameter.startsWith("=")) {
					throw new IllegalArgumentException("Illegal parameter.");
				}
				String[] parameterParts = parameter.split("=");
				
				if(parameterParts.length==1 && parameter.contains("=")) { 
					params.put(parameterParts[0], "");
				} else if(parameterParts.length==1) {
					params.put(parameterParts[0], null);
				} else {
					params.put(parameterParts[0], parameterParts[1]);
				}
			}
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Method with all server dispatch logic.
		 * It has flag in second argument which is true if method is called 
		 * directly or false if method is called through some dispatcher. 
		 * It means dispatchers can access /private directory while clients can not.
		 * @param urlPath request URL
		 * @param directCall determines if method is called directly or not
		 * @throws Exception if error occurs
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall)
				 throws Exception {
			
			if(urlPath.startsWith("/private")) {
				if(directCall) {
					sendError(404, PAGE_NOT_FOUND_MSG);
					return;
				}
			}
			if(urlPath.endsWith(".smscr")) {
				Path requestedFile = documentRoot.toAbsolutePath().resolve(urlPath.substring(1)).toAbsolutePath();
								
				if(!checkFile(requestedFile)) {
					return;
				}
				
				String file = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
				if(context==null) {
					context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
					context.setMimeType("text/html");
				}
				try {
					new SmartScriptEngine(new SmartScriptParser(file).getDocumentNode(),
							context).execute();
				} catch (SmartScriptEngineException e) {
					Logger.getLogger().log("Error with SmartScriptEngine!", e);
					sendError(500, "SmartScriptEngine failed");
				}
			
			} else if(urlPath.startsWith("/ext/")) {
				Class<?> referenceToClass;
				Object newObject = null;
				try {
					referenceToClass = this.getClass()
							.getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers." + 
							urlPath.substring("/ext/".length()));
					newObject = referenceToClass.getDeclaredConstructor().newInstance();
				} catch(Exception e) {
					Logger.getLogger().log("Exception while creating worker via /ext/.", e);
					sendError(400, BAD_REQUEST_MSG);
					return;
				}
				IWebWorker iww = (IWebWorker)newObject;
				if(context==null) {
					context = new RequestContext(ostream, params, permParams, outputCookies);
					context.setMimeType("text/html");
				}
				if(iww!=null) {
					iww.processRequest(context);
				} else {
					sendError(404, PAGE_NOT_FOUND_MSG);
				}
			} else if(workersMap.containsKey(urlPath)) {
				if(context==null) {
					context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
				}
				workersMap.get(urlPath).processRequest(context);
			
			} else {
				Path requestedFile = documentRoot.toAbsolutePath().resolve(urlPath.substring(1)).toAbsolutePath();
				if(!checkFile(requestedFile)) {
					return;
				}
				String fileName = requestedFile.getFileName().toString();
				String fileExtension = fileName.substring(fileName.lastIndexOf('.')+1);
				String mimeType = mimeTypes.get(fileExtension)==null ? 
						"application/octet-stream" : mimeTypes.get(fileExtension);
				if(context==null) {
					context = new RequestContext(ostream, params, permParams, outputCookies);
					context.setMimeType(mimeType);
					context.setContentLength(Files.size(requestedFile));
				}
				try(InputStream is = Files.newInputStream(requestedFile)) {
					byte[] buf = new byte[1024];
					while(true) {
						int r = is.read(buf);
						if(r<1) break;
						context.write(buf, 0, r);
					}
				}
			}
			ostream.flush();
		}
		
		/**
		 * Checks if file path is valid.
		 * Sends 403 error if path does not start with document root.
		 * Send 404 error if given path is not existing, readable or regular file.
		 * @param requestedFile file path to be checked
		 * @return true if file is valid, false otherwise
		 */
		private boolean checkFile(Path requestedFile) {
			try {
				if(!requestedFile.startsWith(documentRoot)) {
					sendError(403, FORBIDDEN_MSG);
					return false;
				}
			
				if(!Files.exists(requestedFile) || 
						!Files.isReadable(requestedFile) || 
						!Files.isRegularFile(requestedFile)) {
					sendError(404, PAGE_NOT_FOUND_MSG);
					return false;
				}
			} catch (IOException e) {
				Logger.getLogger().log("IOException in checkFile(SmartHttpServer)", e);
			}
			return true;
		}

		/**
		 * Read request headers and return list of header lines.
		 * @return list of header lines
		 * @throws IOException if error on IO operation
		 */
		private List<String> readRequest() throws IOException {
			List<String> headers = new ArrayList<>();
			byte[] request = readRequest(istream);
			if(request==null) return null;
			String requestHeader = new String(request, StandardCharsets.US_ASCII);
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
		
		/**
		 * Send error message response to output stream.
		 * @param statusCode error status code
		 * @param statusText error message text
		 * @throws IOException if error on IO operation
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + 
					"\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + 
					"Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			
			ostream.flush();
		}
		
		/**
		 * Simple automate that reads header of HTTP request and returns array of bytes
		 * @param is input stream
		 * @return array of bytes containing HTTP request
		 * @throws IOException if error on IO operation
		 */
		private byte[] readRequest(PushbackInputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
l:			while (true) {
				int b = is.read();
				if(b == -1) return null;
				if(b != 13){
					bos.write(b);
				}
				switch (state) {
				case 0:
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1:
					if(b==10) { state=2; } else state=0;
					break;
				case 2:
					if(b==13) { state=3; } else state=0;
					break;
				case 3:
					if(b==10) { break l; } else state=0;
					break;
				case 4:
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}
		
	}
	
	/**
	 * Models map entry in sessions map.
	 * @author Hrvoje Matic
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID.
		 */
		String sid;
		/**
		 * Session host.
		 */
		String host;
		/**
		 * Session expiration time.
		 */
		long validUntil;
		/**
		 * Map of session parameters.
		 */
		Map<String,String> map;
		
		/**
		 * Default constructor for SessionMapEntry
		 * @param sid session id
		 * @param host session host
		 * @param validUntil session expiration time
		 * @param map session map
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
		
		/**
		 * Setter for expiration time.
		 * @param validUntil expiration time
		 */
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}

		@Override
		public String toString() {
			return sid + " "  + host + " " + String.valueOf(validUntil);
		}
	}

	/**
	 * Thread that collects expired sessions and deletes them from sessions map.
	 * @author Hrvoje Matic
	 */
	private class SessionCollector extends Thread {
		/**
		 * Collecting period. Currently 5 minutes.
		 */
		private static final long COLLECTING_PERIOD = 300000;

		@Override
		public void run() {
			while(true) {
				synchronized(sessions) {
					Iterator<Map.Entry<String,SmartHttpServer.SessionMapEntry>> iterator = 
							sessions.entrySet().iterator();
					while(iterator.hasNext()) {
						Map.Entry<String,SmartHttpServer.SessionMapEntry> entry = iterator.next();
						if ((System.currentTimeMillis()/1000) > entry.getValue().validUntil) {
							iterator.remove();
						}
					}
				}
				try {
					Thread.sleep(COLLECTING_PERIOD);
				} catch (InterruptedException ignorable) { }
			}
		}
	}
	
}
