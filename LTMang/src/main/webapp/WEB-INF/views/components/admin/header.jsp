<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="header">
	     <div class="container">
	        <div class="row">
	           <div class="col-md-5">
	              <div class="logo">
	                 <h1><a href="${urlAdminIndex}">Grab Từ Thiện</a></h1>
	              </div>
	           </div>
	           <div class="col-md-5">
	              <div class="row">
	                <div class="col-lg-12"></div>
	              </div>
	           </div>
	           <div class="col-md-2">
	              <div class="navbar navbar-inverse" role="banner">
	                  <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
	                    <ul class="nav navbar-nav">
	                      <li class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" style="width: 250px; text-align: right">
                        		<c:if test="${not empty adminUserLogin}">
                        			Chào, ${adminUserLogin.username}
                        		</c:if>
                        		<b class="caret"></b>
	                        </a>
	                        <ul class="dropdown-menu animated fadeInUp">
                          		<li><a href="${urlAdminLogout}">Đăng xuất</a></li>
	                        </ul>
	                      </li>
	                    </ul>
	                  </nav>
	              </div>
	           </div>
	        </div>
	     </div>
	</div>