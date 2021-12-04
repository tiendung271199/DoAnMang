<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div id="header-wp">
   <div id="head-top" class="clearfix">
      <div class="wp-inner clearfix">
         <div id="main-menu-wp" class="fl-right">
            <ul id="main-menu" class="clearfix">
               <li>
                  <a href="${urlIndex}" title="Trang chủ">Trang chủ</a>
               </li>
               <c:choose>
               	  <c:when test="${not empty userLogin}">
               	  	  <li>
		                  <a href="${urlProfile}" title="Thông tin cá nhân">Chào, ${userLogin.username}</a>
		              </li>
               	  	  <li>
		                  <a href="${urlLogout}" title="Đăng xuất khỏi hệ thống">Đăng xuất</a>
		              </li>
               	  </c:when>
               	  <c:otherwise>
               	  	  <li>
		                  <a href="${urlLogin}" title="Đăng nhập vào hệ thống">Đăng nhập</a>
		              </li>
               	  </c:otherwise>
               </c:choose>
            </ul>
         </div>
      </div>
   </div>
   <div id="head-body" class="clearfix">
      <div class="wp-inner clearfix">
         <a href="${urlIndex}" title="Trang chủ" id="logo" class="fl-left"><img style="width: 60px" src="${contextPath}/images/logo.png"/></a>
         <div id="search-wp" class="fl-left">
            <form method="get" action="${urlIndex}trang-chu" style="position: relative; z-index: 11;">
               <input type="text" name="keyword" id="s" value="${keyword}" placeholder="Nhập từ khóa tìm kiếm tại đây!">
               <button type="submit" id="sm-s">Tìm kiếm</button>
               <div id="countryList">
                  <div class="sremain">
                     <ul style="display:none">
                     </ul>
                  </div>
               </div>
            </form>
         </div>
         <div id="action-wp" class="fl-right">
            <div id="advisory-wp">
               <span class="title">Đường dây nóng</span>
               <span class="phone">0987.654.321</span>
            </div>
         </div>
      </div>
   </div>
</div>