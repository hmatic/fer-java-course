package hr.fer.zemris.hw17.gallery.rest;

import hr.fer.zemris.hw17.gallery.model.GalleryImage;
import hr.fer.zemris.hw17.gallery.model.GalleryImageDB;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST API for gallery application using Jersey.
 * Uses org.json for JSON transformations.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@Path("/")
public class GalleryJSON {
	
	/**
	 * Method used to retrieve all existing unique tags.
	 * Mapped on GET method. Produces application/json.
	 * @return all existing unique tags
	 */
	@Path("/tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTagsList() {
		String[] tagArray = GalleryImageDB.getInstance().getTags();
		
		JSONArray tags = new JSONArray(Arrays.asList(tagArray));
		JSONObject object = new JSONObject();
		object.put("tags", tags);
		return Response.status(Status.OK).entity(object.toString()).build();
	}

	/**
	 * Method used to retrieve all images from given tag.
	 * Mapped on GET method. Produces application/json.
	 * @param tag tag name
	 * @return JSON response
	 */
	@Path("/tags/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTagImages(@PathParam("tag") String tag) {
		String[] imgNamesArray = GalleryImageDB.getInstance().getTagImageNames(tag);

		if(imgNamesArray.length==0) {
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity("No images found for tag: " + tag).build();
	    }
		
		JSONObject result = new JSONObject();
		
		JSONArray imgNames = new JSONArray();
		for(String t : imgNamesArray) {
			imgNames.put(t);
		}
		result.put("imgNames", imgNames);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * Method used to retrieve image specified by its name.
	 * Mapped on GET method. Produces application/json.
	 * @param name image name
	 * @return gallery image
	 */
	@Path("/image/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GalleryImage getImage(@PathParam("name") String name) {
		return GalleryImageDB.getInstance().getImage(name);
	}
	
}
