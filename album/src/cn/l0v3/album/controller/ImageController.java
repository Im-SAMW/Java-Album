package cn.l0v3.album.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.l0v3.album.dao.*;
import cn.l0v3.album.vo.*;

@Controller
public class ImageController {
    
	@Resource(name="ImageDao")
	private ImageDao dao;
	
	@RequestMapping(value="listImage.do")
	protected String listImage(HttpServletRequest req)  {
				
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("user");
		List images = dao.list(user);
		
		req.setAttribute("images", images);
		req.getSession(true).setAttribute("photoName", "所有图片");
		return "image";
	}
	
	@RequestMapping(value="listPhotoImage.do")
	protected String listPhotoImage(HttpServletRequest req) throws UnsupportedEncodingException  {
		req.setCharacterEncoding("UTF-8");
		int photoID = Integer.parseInt(req.getParameter("photoID"));
		String photoName = req.getParameter("photoName");
		photoName = URLDecoder.decode(photoName,"utf-8");
		List images = dao.listPhotoImage(photoID);
				
		req.setAttribute("images", images);
		req.getSession(true).setAttribute("photoID", photoID);
		req.getSession(true).setAttribute("photoName", photoName);
        return "image";
	}
	
	@RequestMapping(value="addImageListPhotoImage.do")
	protected String addImageListPhotoImage(HttpServletRequest req)  {
		
		HttpSession session = req.getSession(true);
		int photoID = (int) session.getAttribute("photoID");
		String photoName = (String) session.getAttribute("photoName");

		List images = dao.listPhotoImage(photoID);
				
		req.setAttribute("images", images);
        return "image";
	}

	
	@RequestMapping(value="deleteImage.do")
	protected String DeleteImage(HttpServletRequest req) throws IOException {
		HttpSession session = req.getSession(true);
		String photoName = (String) session.getAttribute("photoName");
		int imageID = Integer.parseInt(req.getParameter("imageID"));
		String imageName = req.getParameter("imageName");
		
		boolean bool = dao.deleteImage(imageID);
		
		if(bool) {
			User user = (User) session.getAttribute("user");
			String filePath = req.getSession().getServletContext().getRealPath("/")+"images"+java.io.File.separator+user.getUserName()+java.io.File.separator;
			
			File image = new File(filePath+imageName);
			File imageMin = new File(filePath+java.io.File.separator+"min"+java.io.File.separator+imageName);
			File imageMax = new File(filePath+java.io.File.separator+"max"+java.io.File.separator+imageName);
			
			if(image.exists()) image.delete();
			if(imageMin.exists()) imageMin.delete();
			if(imageMax.exists()) imageMax.delete();
			
		}
		
		if(photoName.equals("所有图片")) {
			return "redirect:listImage.do";
		} else {
			return "redirect:addImageListPhotoImage.do";
		}
	}
}
