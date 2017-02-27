<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    	<title>
    		<c:choose>
    			<c:when test="${user != null}">${user.getUserName()} [网络相册系统]</c:when>
    			<c:otherwise>
    			<c:out value="网络相册系统-092213202"></c:out> 
    			</c:otherwise>
    		</c:choose>
    	</title>
    
     	<link href="css/bootstrap.min.css" rel="stylesheet">

		<!--[if lt IE 9]>
			<script src="js/bootstrap/html5shiv.min.js"></script>
			<script src="js/bootstrap/respond.min.js"></script>
		<![endif]-->
        
        <style>
            body {
	            padding-top: 50px;
	            padding-bottom: 50px;
                background-color:#E3E3E3; 
                font-size:14px; 
                color:#000; 
            }
            .panel-default {
                min-height:450px;
                padding:10px; 
                margin:20px 0px; 
            }
            
            .feature-divider {
	            margin: 40px 0;
	        }
	
	        .feature {
	            padding: 30px 0;
	        }
	
	        .feature-heading {
	            font-size: 50px;
	            color: #2a6496;
	        }
	
	        .feature-heading .text-muted {
	            font-size: 28px;
	        }
	        .row .form-group {
	        	padding-top: 15px;
	            padding-bottom: 15px;
	        }
	        .jumbotron {
	        	background-color: #fff;
	        }
        </style>
    </head>
    <body>
    	<!-- 导航栏 -->
    	<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span>
					</button>
					<c:choose>
					<c:when test="${user != null}">
						<a class="navbar-brand" href="listPhoto.do">网络相册系统</a>
					</c:when>
					<c:otherwise>
						<a class="navbar-brand" href="index.jsp">网络相册系统</a>
					</c:otherwise>
					</c:choose>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						
						<c:if test="${user != null}">
						<li><a href="listImage.do">所有图片</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-haspopup="true"
								aria-expanded="false">相册<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<c:if test="${photos != null}">
			 					<c:forEach items="${photos}" var="p">
			 					<c:if test="${p != null}">
									<li><a href="listPhotoImage.do?photoID=${p.getPhotoID()}&photoName=${p.getPhotoName()}" onclick="code(this)"> ${p.getPhotoName()} </a></li>
								</c:if>
								</c:forEach>
								</c:if>
								<li role="separator" class="divider"></li>
								<li><a href="#" data-toggle="modal" data-target="#addPhoto"> 新建相册 </a></li>
							</ul>
						</li>
						</c:if>
						
						<li><a href="#" data-toggle="modal" data-target="#about-modal"> 关于 </a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right hidden-xs hidden-sm">
						<c:choose>
						<c:when test="${user == null}">
							<li><a href="#" onclick="document.getElementById('inputText3').focus();"> 登陆 </a></li>
							<li><a href="register.jsp"> 注册 </a></li>
						</c:when>
						<c:otherwise>
							<li><a href="#">欢迎你，${user.getUserName()}</a></li>
							<li><a href="logout.do">退出登录</a></li>
						</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</nav>
    
    <!-- 主体 -->
    <c:choose>
    <c:when test="${user == null}">
    
    <div class="container">
    	<div class="panel panel-default">
           	<div class="row feature container-fluid">
                
                <!-- 左侧剧幕 -->
               	<div class="panel-heading hidden-sm hidden-md hidden-lg">
                	<h3 class="panel-title text-center">网络相册系统-登录</h3>
                </div>
                
                <div class="panel-body">
                <div class="col-sm-7 col-xs-0 hidden-xs">
                    <div class="jumbotron">
						<h1 class="hidden-xs hidden-sm">网络相册系统</h1>
						<p class="hidden-xs hidden-sm">
							网络相册系统能为用户免费提供个人相片展示、存放的网络空间。
						</p>
						<p><a class="btn btn-default btn-lg hidden-xs hidden-sm" href="register.jsp" role="button">开始使用</a></p>
						
						<h2 class="hidden-lg hidden-md">网络相册系统</h2>
						<p class="hidden-lg hidden-md" style="font-size: 18px;">
							网络相册系统能为用户免费提供个人相片展示、存放的网络空间。
						</p>
						<p><a class="btn btn-default btn-sm hidden-lg hidden-md" href="register.jsp" role="button">开始使用</a></p>
					</div>
                </div>
                
                <!-- 右侧登录 -->
                <div class="col-sm-5 col-xs-12">
                	<c:if test="${userStateLog != null}">
                	<div class="alert alert-danger alert-dismissible fade in" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${userStateLog}
					</div>
					</c:if>
                    <form action="login.do" method="post">
			        	<div class="form-group">
							<label for="inputEmail3" class="col-md-3 col-sm-0 col-xs-0 control-label hidden-xs hidden-sm">用户名</label>
							<div class="col-md-9 col-sm-12 col-xs-12">
								<input name="username" type="text" class="form-control" id="inputText3" placeholder="用户名" value="${userFromName}">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-md-3 col-sm-0 col-xs-0 control-label hidden-xs hidden-sm">密码</label>
							<div class="col-md-9 col-sm-12 col-xs-12">
								<input name="password" type="password" class="form-control" id="inputPassword3" placeholder="密码" value="${userFromPassWord}">
							</div>
						</div>
			        	<div class="form-group">
							<label for="inputEmail3" class="col-md-3 col-sm-0 col-xs-0 control-label hidden-xs hidden-sm">验证码</label>
							<div class="col-md-4 col-sm-6 col-xs-6">
								<input name="checkcode" type="text" class="form-control" id="inputEmail3" placeholder="验证码">
							</div>
							<div class="col-md-5 col-sm-6 col-xs-6">
								<a href="javascript:reloadCode();">
									<img class="img-responsive img-rounded" alt="验证码" id="imagecode" src="Image.do" />
								</a>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-offset-0 col-xs-12">
								<button type="submit" class="btn btn-default btn-block">登录</button>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-offset-0 col-xs-12">
								<a class="btn btn-default btn-block" href="register.jsp" role="button">注册</a>
							</div>
						</div>
			        </form>
                </div>
                </div>
            </div>
    	</div>
    </div>
    </c:when>
    <c:otherwise>
    <div class="container">
    	<div class="panel panel-default">
			<div class="panel-body">
 				<div class="row">
 					<c:if test="${photos != null}">
 					<c:forEach items="${photos}" var="p">
 					<c:if test="${p != null}">
					<div class="col-xs-6 col-sm-4 col-md-3">
						<div class="thumbnail">
							<a href="listPhotoImage.do?photoID=${p.getPhotoID()}&photoName=${p.getPhotoName()}" onclick="code(this)">
							<div style="width: 100%;height: 0;padding-bottom: 100%;">
							<c:choose>
								<c:when test="${p.getPhotoImage() == null}">
								<img style="width:100%;height:100%" src="sc/noImage.png">
								</c:when>
								<c:otherwise>
								<img style="width:100%;height:100%" src="images/${user.getUserName()}/min/${p.getPhotoImage()}">
								</c:otherwise>
							</c:choose>
							</div>
							</a>
							<div class="caption">
								<a href="listPhotoImage.do?photoID=${p.getPhotoID()}&photoName=${p.getPhotoName()}" onclick="code(this)">
								<h5> ${p.getPhotoName()}&nbsp; </h5>
								</a>
							</div>
							
						</div>
					</div>
					</c:if>
					</c:forEach>
					</c:if>
					
					<div class="col-xs-6 col-sm-4 col-md-3">
						<div class="thumbnail" data-toggle="modal" data-target="#addPhoto">
							
							<img class="img-responsive" src="sc/addPhoto.png">
							
							<div class="caption">
								<h5>新建相册</h5>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
    
    </div>
    </c:otherwise>
    
    </c:choose>
	
 	<!-- 新建相册 -->
	<div class="modal fade" id="addPhoto" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">新建相册</h4>
				</div>
				<div class="modal-body">
					<form action="addPhotos.do" method="post" class="form-inline text-center">
						<div class="form-group">
							<label for="exampleInputName2">相册名称：</label>
							<input name="photoname" type="text" class="form-control" id="exampleInputName2" placeholder="请输入相册名称">
						</div>
						
						<input type="submit" class="btn btn-default" value="确认">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					
					</form>
				</div>
			</div>
		</div>
	</div>
   
    <!-- 关于 -->
    <div class="modal fade" id="about-modal" tabindex="-1" role="dialog" aria-labelledby="modal-label" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
                    <h3 class="modal-title" id="modal-label">关于<span class="text-muted">课程设计</span></h3>
                </div>
                <div class="modal-body">
                    <h5>标题：<span class="text-muted">网络相册系统</span></h5>
                    <h5>王横&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="text-muted">092213202</span></h5>
                    <h5>王晨&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="text-muted">092213201</span></h5>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 页尾 -->
    <footer class="text-center">
        <p>&copy; 2016 &nbsp;&nbsp;092213202&nbsp;01&nbsp; <a href="#top">回到顶部</a></p>
    </footer>
        
        
        
        <script src="js/jquery/1.9.1/jquery.min.js"></script>
		<script src="js/bootstrap/bootstrap.min.js"></script>
        <script src="js/layer/layer.js"></script>
        <script type="text/javascript">
        
        function reloadCode(){
            var time = new Date().getTime();
            document.getElementById("imagecode").src="Image.do?d="+time;
        }
        
        </script>
        <script>
			function code(x){
				var url = $(x).attr('href');
				url = encodeURI(encodeURI(url));
				$(x).attr('href',url);
			}
					
		</script>

    </body>
</html>