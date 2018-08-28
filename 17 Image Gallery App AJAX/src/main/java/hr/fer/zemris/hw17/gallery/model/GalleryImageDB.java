package hr.fer.zemris.hw17.gallery.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Singleton class representing our simple database of gallery images.
 * Uses static class InstanceHolder for lazy-loading and thread-safe implementation.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class GalleryImageDB {
	/**
	 * List of gallery images.
	 */
	private List<GalleryImage> images;
	
	/**
	 * Private constructor. Can not be instantiated from outside of class.
	 */
	private GalleryImageDB() { }
	
	/**
	 * InstanceHolder holds instance of GalleryImageDB class.
	 * It will be loaded into memory first time it is needed, and that is
	 * first time method getInstance is called as that is only place where
	 * InstanceHolder is referenced.
	 * 
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private static class InstanceHolder {
		/**
		 * GalleryImageDB instance.
		 */
		private final static GalleryImageDB INSTANCE = new GalleryImageDB();
	}
	
	/**
	 * Get single and only instance of GalleryImageDB class.
	 * @return GalleryImageDB instance
	 */
	public static GalleryImageDB getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	/**
	 * Builds our simple database from gallery configuration text file.
	 * This method is not that robust and resistant to user errors because
	 * we are only ones who are modifying configuration file.
	 * One image representation in configuration file consists of:
	 * First line is name of image file.
	 * Second line is image description.
	 * Third line is image tags separated by commas.
	 * 
	 * @param path path to configuration file
	 */
	public void build(Path path) {
		List<GalleryImage> images = new ArrayList<>();
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
		}
		
		for(int i=0; i<lines.size(); i+=3) {
			String name = lines.get(i);
			String desc = lines.get(i+1);
			String tagString = lines.get(i+2).toLowerCase();
			String[] tags = tagString.replaceAll(" ", "").split(",");
			images.add(new GalleryImage(name, desc, tags));
		}
		
		this.images = images;
	}
	
	/**
	 * Get all unique tags from images as array of strings.
	 * @return all existing image tags
	 */
	public String[] getTags() {
		Set<String> tagSet = new HashSet<>();
		for(GalleryImage img : images) {
			for(String tag : img.getTags()) {
				tagSet.add(tag);
			}
		}
		return tagSet.toArray(new String[0]);
	}
	
	/**
	 * Get all images that contain tag given in argument.
	 * @param tag searched tag
	 * @return tag's images
	 */
	public String[] getTagImageNames(String tag) {
		List<String> tagImageNameList = new ArrayList<>();
		for(GalleryImage img : images) {
			
			if(Arrays.asList(img.getTags()).contains(tag)) {
				tagImageNameList.add(img.getName());
			}
		}
		return tagImageNameList.toArray(new String[0]);
	}

	/**
	 * Get single GalleryImage based on name given in argument.
	 * Returns null if no image with specified name was found.
	 * @param name image name
	 * @return gallery image
	 */
	public GalleryImage getImage(String name) {
		for(GalleryImage img : images) {
			if(img.getName().equals(name)) {
				return img;
			}
		}
		return null;
	}
	
}
