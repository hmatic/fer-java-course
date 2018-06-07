package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Models context of one request. Provides write methods that can write responses back to client.
 * HTTP Headers are usually generated from this class.
 * All request data is stored in this class.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class RequestContext implements IRequestContext {
	/**
	 * Default HTTP version.
	 */
	private static final String HTTP_VERSION = "HTTP/1.1";
	/**
	 * Charset used for header generation.
	 */
	private static final Charset HEADER_CHARSET = StandardCharsets.ISO_8859_1;
	/**
	 * Default encoding.
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * Default status code.
	 */
	private static final int DEFAULT_STATUS_CODE = 200;
	/**
	 * Default status message.
	 */
	private static final String DEFAULT_STATUS_MSG = "OK";
	/**
	 * Default mime type.
	 */
	private static final String DEFAULT_MIME_TYPE = "text/html";
	/**
	 * Default content length value.
	 */
	private static final Long DEFAULT_CONTENT_LENGTH = null;
	
	/**
	 * Output stream.
	 */
	private OutputStream outputStream;
	/**
	 * Current charset.
	 */
	private Charset charset;
	
	/**
	 * Current encoding.
	 */
	private String encoding = DEFAULT_ENCODING;
	/**
	 * Current status code.
	 */
	private int statusCode = DEFAULT_STATUS_CODE;
	/**
	 * Current status text.
	 */
	private String statusText = DEFAULT_STATUS_MSG;
	/**
	 * Current mime type.
	 */
	private String mimeType = DEFAULT_MIME_TYPE;
	/**
	 * Current content length.
	 */
	private Long contentLength = DEFAULT_CONTENT_LENGTH;
	
	/**
	 * Dispatcher.
	 */
	private IDispatcher dispatcher;
	/**
	 * Parameters map.
	 */
	private Map<String,String> parameters;
	/**
	 * Temporary parameters map.
	 */
	private Map<String,String> temporaryParameters;
	/**
	 * Persistent parameters map.
	 */
	private Map<String,String> persistentParameters;
	/**
	 * List of output cookies.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Flag determines if header is generated or not.
	 */
	private boolean headerGenerated = false;
	
	 /**
	  * Default constructor for RequestContext.
	  * 
	  * @param outputStream output stream
	  * @param parameters list of parameters
	  * @param persistentParameters list of persistent parameters
	  * @param outputCookies output cookies
	  */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		
		this.parameters = parameters==null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters==null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies==null ? new ArrayList<>() : outputCookies;
	}
	
	/**
	 * Extended constructor for RequestContext.
	 * @param outputStream output stream
	 * @param parameters list of parameters
	 * @param persistentParameters list of persistent parameters
	 * @param outputCookies output cookies
	 * @param temporaryParameters list of temporary parameters
	 * @param dispatcher dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, 
			Map<String,String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.dispatcher = dispatcher;
		this.temporaryParameters = temporaryParameters;
	}
	
	/**
	 * Getter for dispatcher.
	 * @return dispatcher.
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public Set<String> getParameterNames() {
		Set<String> parameterNames = new HashSet<>(parameters.keySet());
		parameterNames = Collections.unmodifiableSet(parameterNames);
		return parameterNames;
	}

	@Override
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	@Override
	public Set<String> getPersistentParameterNames() {
		Set<String> persistentParameterNames = new HashSet<>(persistentParameters.keySet());
		persistentParameterNames = Collections.unmodifiableSet(persistentParameterNames);
		return persistentParameterNames;
	}
	
	@Override
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	@Override
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	@Override
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	@Override
	public Set<String> getTemporaryParameterNames() {
		Set<String> temporaryParameterNames = new HashSet<>(temporaryParameters.keySet());
		temporaryParameterNames = Collections.unmodifiableSet(temporaryParameterNames);
		return temporaryParameterNames;
	}
	
	@Override
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	@Override
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	@Override
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}
	
	@Override
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}
	
	@Override
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}
	
	@Override
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		byte[] textAsByte = text.getBytes(charset);
		write(textAsByte);
		return this;
	}
	
	/**
	 * Generate HTTP header and write it to output stream.
	 * @throws IOException if error during writing to output stream
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder headerBuilder = new StringBuilder();
		headerBuilder.append(HTTP_VERSION);
		headerBuilder.append(" " + String.valueOf(statusCode));
		headerBuilder.append(" " + statusText);
		headerBuilder.append("\r\n");
		
		headerBuilder.append("Content-Type: ").append(mimeType);
		if(mimeType.startsWith("text/")) {
			headerBuilder.append("; charset=" + encoding);
		}
		headerBuilder.append("\r\n");
		
		if(contentLength!=null) {
			headerBuilder.append("Content-Length: " + String.valueOf(contentLength));
			headerBuilder.append("\r\n");
		}
		
		if(!outputCookies.isEmpty()) {
			for(RCCookie cookie : outputCookies) {
				headerBuilder.append("Set-Cookie: ");
				StringJoiner cookieAttributes = new StringJoiner(";");
				cookieAttributes.add(cookie.name + "=\"" + cookie.value + "\"");
				if(cookie.domain!=null) {
					cookieAttributes.add(" Domain=" + cookie.domain);
				}
				if(cookie.path!=null) {
					cookieAttributes.add(" Path=" + cookie.path);
				}
				if(cookie.maxAge!=null) {
					cookieAttributes.add(" Max-Age=" + cookie.maxAge);
				}
				cookieAttributes.add(" HttpOnly");
				headerBuilder.append(cookieAttributes.toString());
				headerBuilder.append("\r\n");
			}
		}
		headerBuilder.append("\r\n");
		
		outputStream.write(headerBuilder.toString().getBytes(HEADER_CHARSET));
		headerGenerated = true;
	}


	/**
	 * Setter for encoding.
	 * @param encoding encoding
	 * @throws RuntimeException if called when header is already generated
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Can not change "
					+ "this propery as header is already generated.");
		}
		this.encoding = encoding;
	}

	/**
	 * Setter for status code
	 * @param statusCode status code value
	 * @throws RuntimeException if called when header is already generated
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Can not change "
					+ "this propery as header is already generated.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text.
	 * @param statusText status text
	 * @throws RuntimeException if called when header is already generated
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Can not change "
					+ "this propery as header is already generated.");
		}
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type.
	 * @param mimeType mime type
	 * @throws RuntimeException if called when header is already generated
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Can not change "
					+ "this propery as header is already generated.");
		}
		this.mimeType = mimeType;
	}
	
	/**
	 * Setter for content length.
	 * @param contentLength content length in bytes
	 * @throws RuntimeException if called when header is already generated
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException("Can not change "
					+ "this propery as header is already generated.");
		}
		this.contentLength = contentLength;
	}

	/**
	 * Models cookie in request context.
	 * @author Hrvoje Matic
	 */
	public static class RCCookie {
		/**
		 * Cookie name.
		 */
		private final String name;
		/**
		 * Cookie value.
		 */
		private final String value;
		/**
		 * Cookie domain.
		 */
		private final String domain;
		/**
		 * Cookie maximum age.
		 */
		private final Integer maxAge;
		/**
		 * Cookie path.
		 */
		private final String path;
		
		/**
		 * Default constructor for RCCookie.
		 * @param name cookie name
		 * @param value cookie value
		 * @param maxAge max age of cookie
		 * @param domain cookie domain
		 * @param path cookie path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}

	}

}
