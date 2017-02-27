package cn.l0v3.album.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.l0v3.album.dao.*;
import cn.l0v3.album.vo.*;

@Controller
public class UserController {

	@Resource(name="UserDao")
	private UserDao dao;
	
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public String Login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		User user = null;
		User userfrom = null;
		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");
		String userStateLog = "<strong>登录失败！</strong>用户名或密码错误";
		
		session.setAttribute("userFromName", userName);
		session.setAttribute("userFromPassWord", passWord);
		
		if(userName.length() < 5 || passWord.length() < 6 || passWord.length() > 32){
			req.setAttribute("userStateLog","用户名或密码格式错误！");
			return "index";
		}
		
		
		try {
			String piccode = (String) req.getSession().getAttribute("piccode");
			String checkcode = req.getParameter("checkcode");
			checkcode = checkcode.toUpperCase();
			resp.setContentType("text/html;charset=utf-8");

			if(checkcode.equals(piccode)){
				userfrom = new User();
				userfrom.setUserName(userName);
				userfrom.setPassWord(passWord);
				user = dao.login(userfrom);
			} else {
				userStateLog = "<strong>验证码错误！</strong>";
			}               
		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("userStateLog",null);
		if(user != null){
			session.setAttribute("user", user);
			return "redirect:listPhoto.do";
		} else {
			req.setAttribute("userStateLog",userStateLog);
			return "index";
		}
	}

	@RequestMapping(value="register.do",method=RequestMethod.POST)
	public String Register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		User userfrom = null;
		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");
		String passWord2 = req.getParameter("password2");
		String userStateLog = "注册失败！";
		boolean reg = false;
		
		session.setAttribute("userFromName", userName);
		session.setAttribute("userFromPassWord", passWord);
		
		if(!passWord.equals(passWord2)){
			req.setAttribute("userStateLog","两次密码输入不同！");
			return "index";
		}
		if(userName.length() < 5 || passWord.length() < 6 || passWord.length() > 32){
			req.setAttribute("userStateLog","用户名或密码格式错误！");
			return "index";
		}
		
		try {
			String piccode = (String) req.getSession().getAttribute("piccode");
			String checkcode = req.getParameter("checkcode");
			checkcode = checkcode.toUpperCase();
			resp.setContentType("text/html;charset=utf-8");

			if(checkcode.equals(piccode)){
				userfrom = new User();
				userfrom.setUserName(userName);
				userfrom.setPassWord(passWord);
				if(reg = dao.register(userfrom))
					userStateLog = "注册成功！";
			} else {
				userStateLog = "验证码错误！";
			}               
		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("userStateLog",null);
		if(reg){
			return "redirect:index.jsp";
		} else {
			req.setAttribute("userStateLog",userStateLog);
			return "register";
		}
	}

	@RequestMapping(value="logout.do")
	public String Logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(true);
		session.removeAttribute("user");
		session.invalidate();
		return "redirect:index.jsp";
	}

}
