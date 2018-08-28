package hr.fer.zemris.hw17.gallery.model;

/**
 * Model class representing single gallery image.
 * Every image has file name, description and list of tags.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class GalleryImage {
	/**
	 * Image name.
	 */
	private String name;
	/**
	 * Image description.
	 */
	private String desc;
	/**
	 * Image tags.
	 */
	private String[] tags;
	
	/**
	 * Default GalleryImage constructor.
	 * @param name name
	 * @param desc description
	 * @param tags tags
	 */
	public GalleryImage(String name, String desc, String[] tags) {
		super();
		this.name = name;
		this.desc = desc;
		this.tags = tags;
	}

	/**
	 * Getter for image name.
	 * @return image name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for image description.
	 * @return image description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Getter for image tags.
	 * @return image tags
	 */
	public String[] getTags() {
		return tags;
	}
	
}
