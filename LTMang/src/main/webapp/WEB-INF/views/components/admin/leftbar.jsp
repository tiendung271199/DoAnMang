<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="sidebar content-box" style="display: block;">
	<ul class="nav">
	    <li id="admin-index"><a href="${urlAdminIndex}"><i class="glyphicon glyphicon-home"></i> Trang chủ</a></li>
	    <li id="post_management"><a href="${urlAdminPost}"><i class="glyphicon glyphicon-globe"></i> Bài viết</a></li>
	    <li id="user_management"><a href="${urlAdminUser}"><i class="glyphicon glyphicon-user"></i> Người dùng</a></li>
	    <li id="charity_management"><a href="${urlAdminCharity}"><i class="glyphicon glyphicon-heart"></i> Đăng ký giúp đỡ</a></li>
	    <li id="reviews_management"><a href="${urlAdminReviews}"><i class="glyphicon glyphicon-th-list"></i> Đánh giá người dùng</a></li>
	    <li class="submenu">
	         <a href="#">
	            <i class="glyphicon glyphicon-list"></i> Tài khoản
	            <span class="caret pull-right"></span>
	         </a>
	         <ul>	  			
	  			<li><a href="${urlAdminLogout}">Đăng xuất</a></li>
	        </ul>
	    </li>
	</ul>
</div>