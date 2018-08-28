package hr.fer.zemris.hw17.gallery.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.hw17.gallery.model.GalleryImage;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Provider class used by Jersey library. If you return resource as object of type
 * {@link GalleryImage} this provider will convert it in application/json.
 * Jersey scans all classes and uses this provider when needed.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GalleryWriter implements MessageBodyWriter<GalleryImage> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == GalleryImage.class;
	}
	
	@Override
	public long getSize(GalleryImage img, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return toData(img).length;
	}

	@Override
	public void writeTo(GalleryImage img, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		
		entityStream.write(toData(img));
	}
	
	/**
	 * Helper method which converts GalleryImage object to json.
	 * @param img gallery image object
	 * @return json representation of gallery image object as byte array
	 */
	private byte[] toData(GalleryImage img) {
		String text;
		if(img==null) {
			text = "null;";
		} else {
			JSONObject result = new JSONObject();
			result.put("name", img.getName());
			result.put("desc", img.getDesc());
			
			JSONArray tags = new JSONArray();
			for(String t : img.getTags()) {
				tags.put(t);
			}
			result.put("tags", tags);
			
			text = result.toString();
		}
		return text.getBytes(StandardCharsets.UTF_8);
	}
}
