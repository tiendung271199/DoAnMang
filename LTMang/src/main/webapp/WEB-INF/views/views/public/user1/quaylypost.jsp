<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Quản lý bài viết - Danh sách bài viết</h3>
		</div>
		<hr />
		<div class="section-detail">
			<div class="row" style="margin-bottom: 15px">
				<div class="col-md-8">
					<a href="${urlAccount}/dang-bai-viet" class="btn btn-success"><i class="fa fa-plus"></i>&nbsp;Thêm</a>
				</div>
               	<div class="col-md-4"></div>
			</div>
		
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<c:choose>
				<c:when test="${not empty listPostUserLogin}">
					<table class="table table-striped table-bordered">
					  <thead> 
					    <tr>
					      <th width="5%">ID</th>
					      <th>Tiêu đề</th>
					      <th width="10%">Lượt xem</th>
					      <th width="15%">Hình ảnh</th>
					      <th width="11%">Kiểm duyệt</th>
					      <th width="10%">Chi tiết</th>
					      <th width="20%">Chức năng</th>
					    </tr>
					  </thead>
					  <tbody>
				  		<c:forEach items="${listPostUserLogin}" var="post">
						    <tr>
						      <td>${post.id}</td>
						      <td>${post.title}</td>
						      <td align="center">${post.views}</td>
						      <td align="center">
						      	  <img width="160px" height="100px" src="${pictureContextPath}/images/${post.picture}" title="${post.title}" />
						      </td>
						      <td align="center">
						      	  <c:if test="${post.censored == 1}">Đã duyệt</c:if>
								  <c:if test="${post.censored == 0}">Chưa duyệt</c:if>
						      </td>
						      <td align="center">
						      	<a href="${urlAccount}/quan-ly-bai-viet/chi-tiet/${stringUtil.makeSlug(post.title)}-${post.id}" title="Xem chi tiết bài viết" class="btn btn-secondary">Xem</a>
						      </td>
						      <td align="center">
						      	<a href="${urlAccount}/cap-nhat-bai-viet/${post.id}" title="Cập nhật thông tin bài viết" class="btn btn-primary"><i class="fa fa-pencil"></i> Sửa</a>
						      	<a href="${urlAccount}/xoa-bai-viet/${post.id}" onclick="return confirm('Bạn có chắc muốn xoá bài viết \'${post.title}\' không?')" title="Xoá bài viết" class="btn btn-danger"><i class="fa fa-trash"></i> Xoá</a>
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
						  	<a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${pagePrevious}">Trang trước</a>
						  </li>
																      
					      <c:choose>
						      <c:when test="${totalPage > 5}">
						      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
						      	  	  <li class="page-item"><a class="page-link" href="${urlAccount}/quan-ly-bai-viet">Đầu</a></li>
								      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
								      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
								      	  	  <a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${page}">${page}</a>
								      	  </li>
								      </c:forEach>
								      <li class="page-item"><a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${totalPage}">Cuối</a></li>
							      </c:if>
						      	  <c:if test="${currentPage <= 3}">
								      <c:forEach begin="1" end="5" var="page">
								      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
								      	  	  <a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${page}">${page}</a>
								      	  </li>
								      </c:forEach>
								      <li class="page-item"><a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${totalPage}">Cuối</a></li>
							      </c:if>
						      	  <c:if test="${currentPage >= (totalPage - 2)}">
						      	  	  <li class="page-item"><a class="page-link" href="${urlAccount}/quan-ly-bai-viet">Đầu</a></li>
								      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
								      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
								      	  	  <a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${page}">${page}</a>
								      	  </li>
								      </c:forEach>
							      </c:if>
						      </c:when>
						      <c:otherwise>
						      	  <c:forEach begin="1" end="${totalPage}" var="page">
							      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
							      	  	  <a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${page}">${page}</a>
							      	  </li>
							      </c:forEach>
						      </c:otherwise>
					      </c:choose>
					      
					      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
					      <c:if test="${currentPage == totalPage}">
					      	<c:set value="${currentPage}" var="pageNext"></c:set>
					      </c:if>
						  <li class="page-item <c:if test='${currentPage == totalPage}'>disabled</c:if>">
						  	<a class="page-link" href="${urlAccount}/quan-ly-bai-viet/trang-${pageNext}">Trang tiếp</a>
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