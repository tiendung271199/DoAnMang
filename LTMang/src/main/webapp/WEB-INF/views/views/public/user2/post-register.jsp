<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Danh sách bài viết đã đăng ký giúp đỡ</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<c:choose>
				<c:when test="${not empty listRegisterCharity}">
					<table class="table table-striped table-bordered">
					  <thead> 
					    <tr>
					      <th width="5%">ID</th>
					      <th>Tiêu đề bài viết</th>
					      <th width="15%">Hình ảnh</th>
					      <th width="15%">Người đăng</th>
					      <th width="13%">Đăng ký ngày</th>
					      <th width="18%">Xác nhận giúp đỡ</th>
					      <th width="13%">Đánh giá</th>
					    </tr>
					  </thead>
					  <tbody>
				  		<c:forEach items="${listRegisterCharity}" var="reg">
						    <tr>
						      <td>${reg.id}</td>
						      <td><a href="${urlDetail}/${stringUtil.makeSlug(reg.post.title)}-${reg.post.id}">${reg.post.title}</a></td>
						      <td align="center">
						      	  <img width="160px" height="100px" src="${pictureContextPath}/images/${reg.post.picture}" title="${reg.post.title}" />
						      </td>
						      <td><a href="${urlAccount}/thong-tin-nguoi-dung-${reg.post.user.id}">${reg.post.user.fullname}</a></td>
						      <td>${dateUtil.formatDate(reg.updateAt)}</td>
						      <td align="center">
						      	<c:if test="${reg.confirm == 1}">
						      		<button class="btn btn-success">Đã xác nhận</button>
						      	</c:if>
						      	<c:if test="${reg.confirm == 0}">
						      		<button class="btn btn-secondary">Chưa xác nhận</button>
						      	</c:if>
						      </td>
						      <td>
						      	<a href="${urlAccount}/danh-gia-nguoi-dung/${reg.id}" class="btn btn-primary">Đánh giá</a>
						      </td>
						    </tr>
					    </c:forEach>
					  </tbody>
					</table>
					
					<nav aria-label="Page navigation example" style="margin-top: 15px; float: right">
					  <ul class="pagination">
					  	  <c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
					   	  <c:if test="${currentPage == 1}">
					   	  	<c:set value="${currentPage}" var="pagePrevious"></c:set>
					      </c:if>
						  <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
						  	<a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${pagePrevious}">Trang trước</a>
						  </li>
																      
					      <c:choose>
						      <c:when test="${totalPage > 5}">
						      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
						      	  	  <li class="page-item"><a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do">Đầu</a></li>
								      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
								      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
								      	  	  <a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${page}">${page}</a>
								      	  </li>
								      </c:forEach>
								      <li class="page-item"><a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${totalPage}">Cuối</a></li>
							      </c:if>
						      	  <c:if test="${currentPage <= 3}">
								      <c:forEach begin="1" end="5" var="page">
								      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
								      	  	  <a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${page}">${page}</a>
								      	  </li>
								      </c:forEach>
								      <li class="page-item"><a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${totalPage}">Cuối</a></li>
							      </c:if>
						      	  <c:if test="${currentPage >= (totalPage - 2)}">
						      	  	  <li class="page-item"><a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do">Đầu</a></li>
								      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
								      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
								      	  	  <a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${page}">${page}</a>
								      	  </li>
								      </c:forEach>
							      </c:if>
						      </c:when>
						      <c:otherwise>
						      	  <c:forEach begin="1" end="${totalPage}" var="page">
							      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
							      	  	  <a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${page}">${page}</a>
							      	  </li>
							      </c:forEach>
						      </c:otherwise>
					      </c:choose>
					      
					      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
					      <c:if test="${currentPage == totalPage}">
					      	<c:set value="${currentPage}" var="pageNext"></c:set>
					      </c:if>
						  <li class="page-item <c:if test='${currentPage == totalPage}'>disabled</c:if>">
						  	<a class="page-link" href="${urlAccount}/danh-sach-bai-viet-da-dang-ky-giup-do/trang-${pageNext}">Trang tiếp</a>
						  </li>
					  </ul>
					</nav>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info" role="alert">Không có dữ liệu</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>