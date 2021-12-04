<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
    			<div class="sidebar fl-left">
    				 <c:if test="${not empty userLogin}">
	    				 <div class="section" id="category-product-wp" style="margin-bottom: 30px">
	                        <div class="section-head">
	                           <h3 class="section-title">Tài khoản cá nhân</h3>
	                        </div>
	                        <div class="secion-detail">
	                           <ul class="list-item">
	                              <li>
	                                 <a href="${urlProfile}" title="Thông tin cá nhân">Hồ sơ cá nhân</a>
	                              </li>
	                              <c:if test="${userLogin.role.id == 2}">
		                              <li>
		                                 <a href="${urlAccount}/quan-ly-bai-viet" title="">Quản lý bài viết</a>
		                              </li>
	                              </c:if>
	                              <c:if test="${userLogin.role.id == 3}">
	                              	  <li>
		                                 <a href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do" title="">Bài viết đã đăng ký giúp đỡ</a>
		                              </li>
	                              </c:if>
	                           </ul>
	                        </div>
	                     </div>
    				 </c:if>
                     <div class="section" id="selling-wp" style="margin-bottom: 30px">
                        <c:if test="${not empty listNew}">
	                        <div class="section-head">
	                           <h3 class="section-title">Mới nhất</h3>
	                        </div>
	                        <div class="section-detail">
	                           <ul class="list-item">
	                              <c:forEach items="${listNew}" var="postNew">
		                              <li class="clearfix">
		                                 <a href="${urlDetail}/${stringUtil.makeSlug(postNew.title)}-${postNew.id}" title="${postNew.title}" class="thumb fl-left">
		                                 	<img width="72px" src="${pictureContextPath}/images/${postNew.picture}" alt="">
		                                 </a>
		                                 <div class="info fl-right">
		                                    <a href="${urlDetail}/${stringUtil.makeSlug(postNew.title)}-${postNew.id}" title="${postNew.title}" class="product-name">${postNew.title}</a>
		                                    <p>${stringUtil.setStringCompact2(postNew.description, 25)}</p>
		                                 </div>
		                              </li>
	                              </c:forEach>
	                           </ul>
	                        </div>
                        </c:if>
                     </div>
                     <div class="section" id="selling-wp">
                        <c:if test="${not empty listHighlight}">
	                        <div class="section-head">
	                           <h3 class="section-title">Nổi bật</h3>
	                        </div>
	                        <div class="section-detail">
	                           <ul class="list-item">
	                              <c:forEach items="${listHighlight}" var="postHighlight">
		                              <li class="clearfix">
		                                 <a href="${urlDetail}/${stringUtil.makeSlug(postHighlight.title)}-${postHighlight.id}" title="${postHighlight.title}" class="thumb fl-left">
		                                 	<img width="72px" src="${pictureContextPath}/images/${postHighlight.picture}" alt="">
		                                 </a>
		                                 <div class="info fl-right">
		                                    <a href="${urlDetail}/${stringUtil.makeSlug(postHighlight.title)}-${postHighlight.id}" title="${postHighlight.title}" class="product-name">${postHighlight.title}</a>
		                                    <p>${stringUtil.setStringCompact2(postHighlight.description, 25)}</p>
		                                 </div>
		                              </li>
	                              </c:forEach>
	                           </ul>
	                        </div>
                        </c:if>
                     </div>
                </div>