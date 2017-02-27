package cn.l0v3.album.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Photo implements Serializable{
	private Integer photoID;
	private String photoName;
	private String photoImage;
	private String alt;
	private User user;
	private List images = new ArrayList();
	
	public Photo() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Photo(String photoName) {
		super();
		this.photoName = photoName;
	}

	public Photo(String photoName, String alt) {
		super();
		this.photoName = photoName;
		this.alt = alt;
	}

	public String getPhotoImage() {
		return photoImage;
	}

	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}

	public Integer getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Integer photoID) {
		this.photoID = photoID;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public List getImages() {
		return images;
	}

	public void setImages(List images) {
		this.images = images;
	}
	
	
}
