package cn.l0v3.album.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class User implements Serializable{
	private Integer userID;
	private String userName;
	private String passWord;
	private List photos = new ArrayList();
	private List images = new ArrayList();
	
	
	public User() {
		super();
	}
	public User(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}
	public Integer getUserID() {
		return userID;
	}
	
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public List getImages() {
		return images;
	}
	
	public void setImages(List images) {
		this.images = images;
	}
	public List getPhotos() {
		return photos;
	}
	public void setPhotos(List photos) {
		this.photos = photos;
	}
	
}
