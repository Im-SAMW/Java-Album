package cn.l0v3.album.controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import com.jspsmart.upload.SmartUpload;

import cn.l0v3.album.dao.*;
import cn.l0v3.album.vo.*;

@Controller
public class UploadController implements ServletConfigAware,ServletContextAware{
	
	private ServletContext servletContext; 
	private ServletConfig servletConfig;
	
	@Resource(name="ImageDao")
	private ImageDao dao;
	
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}
	
	@Override
	public void setServletConfig(ServletConfig arg0) {
		this.servletConfig = arg0;
	}
	
	public static void zoomImageScale(File imageFile, String suffix, String newPath, int newHeight) throws IOException {
		int newWidth;
		if(!imageFile.canRead())
            return;
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		if (null == bufferedImage) 
			return;
       
		int originalWidth = bufferedImage.getWidth();
		int originalHeight = bufferedImage.getHeight();
		
		double scale = (double)originalHeight / (double)newHeight;    // 缩放的比例
		newWidth =  (int)(originalWidth / scale);

		zoomImageUtils(imageFile, suffix, newPath, bufferedImage, newWidth, newHeight);
	}
	private static void zoomImageUtils(File imageFile, String suffix, String newPath, BufferedImage bufferedImage, int width, int height)
			throws IOException{

		// 处理 png 背景变黑的问题
		if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))) {
			BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			Graphics2D g2d = to.createGraphics(); 
			to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g2d.dispose(); 

			g2d = to.createGraphics(); 
			Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING); 
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose(); 

			ImageIO.write(to, suffix, new File(newPath));
		} else {

			BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
			Graphics g = newImage.getGraphics();
			g.drawImage(bufferedImage, 0, 0, width, height, null);
			g.dispose();
			ImageIO.write(newImage, suffix, new File(newPath));
		}
	}

   @RequestMapping(value="upload.do",method = RequestMethod.POST)
   public String Upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UUID uuid;
		int count = 0;
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("user");
		
		int photoID =  (int) session.getAttribute("photoID");
		
		String filePath = req.getSession().getServletContext().getRealPath("/")+"images"; 
		File file = new File(filePath);
		
		if(!file.exists()) { file.mkdir(); }
		filePath = filePath + java.io.File.separator+user.getUserName();
		String minFilePath = filePath+java.io.File.separator+"min";
		String maxFilePath = filePath+java.io.File.separator+"max";
		
		
		file = new File(filePath);
		File minFile = new File(minFilePath);
		File maxFile = new File(maxFilePath); 
		if(!file.exists()) { file.mkdir(); }
		if(!minFile.exists()) { minFile.mkdir(); }
		if(!maxFile.exists()) { maxFile.mkdir(); }
		
		
		SmartUpload su = new SmartUpload();
		//初始化上传组件
		su.initialize(servletConfig, req, resp);
		//设置允许上传图片的最大大小
		su.setMaxFileSize(1024*1024*10);
		//设置单次允许上传的总大小
		su.setTotalMaxFileSize(1024*1024*1000);
		//设置允许上传的图片类型
		su.setAllowedFilesList("jpg,JPG,jpeg,JPEG,bmp,BMP,png,PNG,gif,GIF");
		String result = "上传成功";
		
		try{
			su.upload();
			for(int i =0; i<su.getFiles().getCount(); i++) {
				String ext=su.getFiles().getFile(i).getFileExt();//此为得到文件的扩展名,getFile(0)为得到唯一的一个上传文件
				uuid = UUID.randomUUID();
				String fileName=uuid+"."+ext;
		        su.getFiles().getFile(i).saveAs(filePath + java.io.File.separator + fileName);
		        zoomImageScale(new File(filePath+java.io.File.separator+fileName), ext, 
       				minFilePath+java.io.File.separator+fileName, 300);
		        zoomImageScale(new File(filePath+java.io.File.separator+fileName), ext, 
		        		maxFilePath+java.io.File.separator+fileName, 640);

		        com.jspsmart.upload.File tempFile = su.getFiles().getFile(i);
		        cn.l0v3.album.vo.Image image = new cn.l0v3.album.vo.Image(tempFile.getFilePathName(), fileName);
		        image.setUser(user);
		        dao.image(image,photoID);
			}
			count = su.getFiles().getCount();
			result = result + count + "张图片！";
		}catch(Exception e) {
			result = "上传失败!";
			e.printStackTrace();
		}
		
		req.getSession(true).setAttribute("result", result);
		return "redirect:addImageListPhotoImage.do";
	}
}
