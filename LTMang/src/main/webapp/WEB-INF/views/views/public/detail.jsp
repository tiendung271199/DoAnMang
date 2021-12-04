<%@page import="doan.model.User"%>
<%@page import="doan.model.Post"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
                  <div class="main-content fl-right">
                     <div class="section" id="detail-blog-wp">
                        <div class="section-head clearfix">
                           <h3 class="section-title">${post.title}</h3>
                        </div>
                        <div class="section-detail">
                        	<c:set value="" var="status"></c:set>
                        	<c:if test="${post.status == 1}"><c:set value="Vẫn còn nhận hỗ trợ" var="status"></c:set></c:if>
                        	<c:if test="${post.status == 0}"><c:set value="Không còn nhận hỗ trợ" var="status"></c:set></c:if>
                            <span class="create-date">Đăng ngày: ${dateUtil.formatDate(post.createAt)} - Đăng bởi: <a href="${urlAccount}/thong-tin-nguoi-dung-${post.user.id}">${post.user.fullname}</a> - Lượt xem: ${post.views} - ${status}</span>
                        	<img style="width: 400px; float: left" src="${pictureContextPath}/images/${post.picture}" />
                        	<p style="width: 430px; float: right">${post.description}</p>
                        	<div class="clr"></div>
                        	<div class="detail" style="margin-top: 15px">
                        		<h4>Nội dung bài viết</h4>
                        		<p>${post.detail}</p>
                        	</div>
                        </div>
                     </div>
                     <hr>
                     
                     <c:if test="${post.status == 0}">
                     	<div class="section" id="same-category-wp">
	                        <div class="section-head"></div>
	                        <div class="section-detail">
	                        	<div style="margin-top: 10px; font-style: italic">
									<p>Bài viết không còn nhận hỗ trợ nữa</p>
								</div>
	                        </div>
	                     </div>
	                     <hr>
                     </c:if>
                     <c:if test="${post.status == 1}">
	                     <c:choose>
		                     <c:when test="${not empty userLogin}">
		                     	 <c:if test="${userLogin.role.id == 3}">
		                     	 	 <div id="register-charity">
					                     <div class="section" id="same-category-wp">
					                        <div class="section-head">
					                           <h3 class="section-title">Nếu bạn muốn giúp đỡ cho hoàn cảnh này, hãy nhấn nút bên dưới</h3>
					                        </div>
					                        <div class="section-detail">
					                           <button title="Đăng ký giúp đỡ" onclick="register(${post.id})" class="btn btn-primary">Tôi muốn hỗ trợ</button>
					                        </div>
					                     </div>
					                     <hr>
				                     </div>
				                     <div id="cancel-register-charity">
					                     <div class="section" id="same-category-wp">
					                        <div class="section-head">
					                           <h3 class="section-title">Bạn đã đăng ký giúp đỡ cho hoàn cảnh này, nếu bạn muốn huỷ đăng ký hãy nhấn nút bên dưới</h3>
					                        </div>
					                        <div class="section-detail">
					                           <button title="Huỷ đăng ký giúp đỡ" onclick="cancelRegister(${post.id})" class="btn btn-secondary">Huỷ đăng ký</button>
					                        </div>
					                     </div>
					                     <hr>
				                     </div>
			                     </c:if>
		                     </c:when>
		                     <c:otherwise>
		                     	<div class="section" id="same-category-wp">
			                        <div class="section-head"></div>
			                        <div class="section-detail">
			                        	<div style="margin-top: 10px; font-style: italic">
											<p>Nếu bạn muốn giúp đỡ, hãy <a href="${urlLogin}" title="Đăng ký tài khoản">Đăng nhập</a> vào hệ thống</p>
										</div>
			                        </div>
			                     </div>
			                     <hr>
		                     </c:otherwise>
	                     </c:choose>
                     </c:if>
                     
                     <div class="section" id="same-category-wp">
                        <div class="section-head">
                           <h3 class="section-title">Bài viết khác</h3>
                        </div>
                        <div class="section-detail">
                           <c:choose>
                           		<c:when test="${not empty listPostOther}">
		                           <ul class="list-item">
		                           	  <c:forEach items="${listPostOther}" var="postOther">
			                              <li>
			                                 <a href="${urlDetail}/${stringUtil.makeSlug(postOther.title)}-${postOther.id}" title="${postOther.title}" class="thumb">
			                                 	<img src="${pictureContextPath}/images/${postOther.picture}">
			                                 </a>
			                                 <a href="${urlDetail}/${stringUtil.makeSlug(postOther.title)}-${postOther.id}" title="${postOther.title}" class="product-name">${postOther.title}</a>
			                              </li>
		                              </c:forEach>
		                           </ul>
                           		</c:when>
                           		<c:otherwise>
                           			<div class="alert alert-info" role="alert">Không có bài viết khác trong vòng 1 tuần qua</div>
                           		</c:otherwise>
                           </c:choose>
                        </div>
                     </div>
                  </div>

<script type="text/javascript">
	window.onload = myOnLoadFunction();
	
	function myOnLoadFunction() {
		var a = document.getElementById("register-charity");
		var b = document.getElementById("cancel-register-charity");
		if (a != null && b != null) {
			document.getElementById("register-charity").hidden = true;
			document.getElementById("cancel-register-charity").hidden = true;
			var url = window.location.href;
			var arrURL = url.split('/');
			var arrNameID = arrURL[arrURL.length-1].split('-');
			var id = arrNameID[arrNameID.length-1];
			xuLyDetail(id);
		}
	}
	
	function xuLyDetail(id) {
			$.ajax({
			url: '${urlIndex}check-register',
			type: 'POST',
			cache: false,
			data: {
				id: id
			},
			success: function(data){
				console.log(data);
				if (data == '1') {
					document.getElementById("cancel-register-charity").hidden = false;
				} else {
					document.getElementById("register-charity").hidden = false;
				}
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}

	function register(id) {
		var conf = confirm("Bạn có chắc muốn đăng ký hỗ trợ hoàn cảnh này?");
		if (conf == true) {
			confirmRegister(id);
		}
	}
	
	function confirmRegister(id) {
		$.ajax({
			url: '${urlIndex}dang-ky-giup-do',
			type: 'POST',
			cache: false,
			data: {
				id: id
			},
			success: function(data){
				if (data.code == 0) {
					alert('Success: '+data.content);
					location.reload();
				} else {
					alert('Error: '+data.content);
				}
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
	
	function cancelRegister(id) {
		var conf = confirm("Bạn có chắc muốn huỷ đăng ký hỗ trợ hoàn cảnh này?");
		if (conf == true) {
			confirmCancelRegister(id);
		}
	}
	
	function confirmCancelRegister(id) {
		$.ajax({
			url: '${urlIndex}huy-dang-ky-giup-do',
			type: 'POST',
			cache: false,
			data: {
				id: id
			},
			success: function(data){
				if (data.code == 0) {
					alert('Success: '+data.content);
					location.reload();
				} else {
					alert('Error: '+data.content);
				}
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
</script>