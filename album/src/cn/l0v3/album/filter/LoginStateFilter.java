package cn.l0v3.album.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.l0v3.album.vo.User;

public class LoginStateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
 
     }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();

        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();
        //System.out.println(path);
         
        // 从session里取user
        User user = (User)session.getAttribute("user");
         
         // 登陆页面无需过滤
         if(path.indexOf("index.jsp") > -1 || path.indexOf("register.jsp") > -1 || 
        	path.indexOf("login.do") > -1  || path.indexOf("register.do") > -1  ||
        	path.indexOf("/Image.do") > -1 || path.indexOf("/js/") > -1 ||
        	path.indexOf("/css/") > -1 || path.indexOf("/sc/") > -1) {
             chain.doFilter(servletRequest, servletResponse);
             return;
         }
 
         // 判断如果没有取到员工信息,就跳转到登陆页面
         if (user == null || "".equals(user)) {
             // 跳转到登陆页面
             servletResponse.sendRedirect("index.jsp");
         } else {
             // 已经登陆,继续此次请求
             chain.doFilter(request, response);
         }
 
     }
 
     @Override
     public void destroy() {
         // TODO Auto-generated method stub
 
     }
    
}
