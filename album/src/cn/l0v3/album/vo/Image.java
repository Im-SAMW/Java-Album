package cn.l0v3.album.vo;

import java.io.Serializable;

public class Image implements Serializable{
	private Integer imageID;
	private String title;
	private String fileName;
	private String alt;
	
	private User user;
	private Photo photo;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Image(String title, String fileName) {
		this.title = title;
		this.fileName = fileName;
	}

	public Image() {
		super();
	}

	public Integer getImageID() {
		return imageID;
	}
	
	public void setImageID(Integer imageID) {
		this.imageID = imageID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getAlt() {
		return alt;
	}
	
	public void setAlt(String alt) {
		this.alt = alt;
	}
	
}
