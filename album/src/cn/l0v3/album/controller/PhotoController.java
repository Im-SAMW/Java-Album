package cn.l0v3.album.controller;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.l0v3.album.dao.*;
import cn.l0v3.album.vo.*;

@Controller
public class PhotoController {

	@Resource(name="PhotoDao")
	private PhotoDao dao;
	
	@RequestMapping(value="listPhoto.do")
	protected String list(HttpServletRequest req)  {
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("user");
		List photos = dao.list(user);
		
		session.setAttribute("photos", photos);
        return "redirect:index.jsp";
	}
	
	@RequestMapping(value="addPhotos.do")
	public String AddPhotos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Photo photofrom = null;
		String photoStateLog = "新建失败！";
		boolean bool_addphoto = false;
		HttpSession session = req.getSession(true);
		
		User user = (User) session.getAttribute("user");
		String photoName = req.getParameter("photoname");
		photofrom = new Photo();
		photofrom.setPhotoName(photoName);
		photofrom.setUser(user);
		if(bool_addphoto = dao.addPhoto(photofrom))
			photoStateLog = "新建成功！";

		session.setAttribute("photoStateLog",null);
		if(bool_addphoto){
			return "redirect:listPhoto.do";
		} else {
			session.setAttribute("photoStateLog",photoStateLog);
			return "redirect:index.jsp";
		}
	}
	
	@RequestMapping(value="updatePhotoImage.do")
	public String updatePhotoImg(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		int photoID = (int) session.getAttribute("photoID");
		String photoName = (String) session.getAttribute("photoName");
		String fileName = req.getParameter("fileName");

		if(dao.updatePhotoImage(fileName,photoID)){
			session.setAttribute("result", "设置成功！");
		}else{
			session.setAttribute("result", "设置失败！");
		}
			
		if(photoName.equals("所有图片")) {
			return "redirect:listImage.do";
		} else {
			return "redirect:addImageListPhotoImage.do";
		}
	}
}
