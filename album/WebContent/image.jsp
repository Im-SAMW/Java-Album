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
    		${photoName}[网络相册系统]
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
                font-family:'微软雅黑'
            }
            a,a:hover { 
                text-decoration:none;
            }
            .photos-demo img{
            	width: 19.5%
            }
            .panel-default {
                min-height:450px;
                padding:10px; 
                margin:20px 0px; 
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
						<li><a href="#" data-toggle="modal" data-target="#about-modal">关于</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right hidden-xs hidden-sm">
						<c:choose>
						<c:when test="${user == null}">
							<li><a href="#">登陆</a></li>
							<li><a href="#">注册</a></li>
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
		
		<!-- 图片区域 -->
		<div class="container">
			<div class="panel panel-default">
			<div>
			<span style="font-size:25px; padding-bottom:20px;"><c:out value="${photoName}"></c:out></span>
			<c:if test="${photoName != '所有图片'}">
			<a href="#" class="btn btn-default pull-right" data-toggle="modal" data-target="#addImage"> 上传图片 </a>
			</c:if>
			</div>
			<hr />
			<!-- 上传成功提示框 -->
			<c:if test="${result != null}">
				<div class="alert alert-success alert-dismissible fade in" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					${result}
				</div>
				<% 
					if(session.getAttribute("result") != null)
						session.removeAttribute("result");
				%>
			</c:if>
			
			<!-- 显示照片 -->
			<div class="row" id="photos">
				<!-- layer-src表示大图  layer-pid表示图片id  src表示缩略图-->
				<c:forEach items="${images}" var="temp">
				<c:if test="${temp != null}">
					<div class="col-xs-6 col-sm-4 col-md-3">
					<div class="thumbnail">
						<div style="width: 100%;height: 0;padding-bottom: 100%;overflow: hidden;border: solid 1px red;">
							<img 	style="width:100%;height:100%"
									layer-src="images/${user.getUserName()}/max/${temp.getFileName()}"
									src="images/${user.getUserName()}/min/${temp.getFileName()}"
									alt="${temp.getTitle()}">
						</div>
							<div class="caption">
								<h6 style="text-align:center;width:90%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
									${temp.getTitle()}
								</h6>
								<div class="row">
								<div class="col-xs-4">
									<a href="images/${user.getUserName()}/${temp.getFileName()}" target="_blank">原图</a>
								</div>
								<div class="col-xs-4">
									<a href="updatePhotoImage.do?fileName=${temp.getFileName()}">封面</a>
								</div>
								<div class="col-xs-4">
									<a href="deleteImage.do?imageID=${temp.getImageID()}&imageName=${temp.getFileName()}">删除</a>
								</div>
								</div>
							</div>
							
					</div>
							
					
					</div>
				</c:if>
				</c:forEach>
			</div>
			
			</div>
		</div>
		
		<!-- 上传图片 -->
		<div class="modal fade" id="addImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">上传图片</h4>
				</div>
				<div class="modal-body">
					
					<form action="upload.do" method="post" enctype="multipart/form-data">
						<div class="form-group">
						<label for="exampleInputName2">请选择图片：</label>
						<!-- 
						<input id="files" name="file" type="file" accept="image/*" multiple/>
						 -->
						<input id="files" name="file" type="file" accept="image/*" multiple="multiple" style="display:none"/>
						<button class="btn btn-default" type="button" onclick="document.getElementById('files').click();" >选择图片</button>
						<input class="btn btn-default pull-right" type="submit" value="确认上传"  />${result}
						</div>
						
					</form>
					<div id="preview"></div>
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
		
		
		<!-- javascript -->
		<script src="js/jquery/1.9.1/jquery.min.js"></script>
		<script src="js/layer/layer.js"></script>
		<script src="js/bootstrap/bootstrap.min.js"></script>
        
        <!-- 图片查看 -->
        <script>
		;!function(){
			//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
			layer.ready(function(){
				//使用相册
				layer.photos({
					photos: '#photos'
				});
			});
		}();
		
		</script>
		
		<!-- 待上传图片预览 -->
		<script type="text/javascript">
		function fileSelect(e) {
		    e = e || window.event;
		    var files = this.files;
		    var p = document.getElementById('preview');
	
		    p.innerHTML = '';
		    for(var i = 0, f; f = files[i]; i++) {
		        var reader = new FileReader();
		        reader.onload = (function(file) {
		            return function(e) {
		                var span = document.createElement('span');
		                span.innerHTML = '<img style="padding: 10px 10px;" height="100px" src="'+ this.result +'" alt="'+ file.name +'" />';
	
		                p.insertBefore(span, null);
		            };
		        })(f);
		        //读取文件内容
		        reader.readAsDataURL(f);
		    }
		}
		document.getElementById('files').addEventListener('change', fileSelect, false);
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