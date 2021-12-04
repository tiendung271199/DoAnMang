<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="col-md-10">
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">ĐÁNH GIÁ NGƯỜI DÙNG</div>
		  			</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-8"></div>
                	<div class="col-md-4">
                		<form action="${urlAdminReviews}/tim-kiem" method="get">
		                 	<div class="input-group form">
		                       <input type="text" name="name" value="" class="form-control" placeholder="Tìm kiếm" >
		                       <span class="input-group-btn">
		                         <button class="btn btn-primary" type="submit">Tìm kiếm</button>
		                       </span>
		                  	</div>
                  		</form>
                  	</div>
				</div>

				<div class="row">
	  				<div class="panel-body">
	  					<c:if test="${not empty success}">
							<div class="alert alert-success" role="alert">
							    ${success}
							</div>
						</c:if>
	  					<c:if test="${not empty error}">
							<div class="alert alert-danger" role="alert">
							    ${error}
							</div>
						</c:if>
		  				<c:choose>
							<c:when test="${not empty listReviews}">
			  					<table class="table table-striped table-bordered" id="example">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th>Bài viết</th>
											<th width="18%">User đánh giá</th>
											<th width="18%">User được đánh giá</th>
											<th width="10%">Đánh giá ngày</th>
											<th width="8%">Chi tiết</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listReviews}" var="reviews">
											<tr class="odd gradeX">
												<td>${reviews.id}</td>
												<td>${reviews.post.title}</td>
												<td>${reviews.userReviews.username} (${reviews.userReviews.fullname})</td>
												<td>${reviews.userDuocReviews.username} (${reviews.userDuocReviews.fullname})</td>
												<td align="center">${dateUtil.formatDate(reviews.createAt)}</td>
												<td class="center text-center">
													<a href="${urlAdminReviews}/chi-tiet/${reviews.id}" title="Xem chi tiết đánh giá" class="btn btn-primary">Xem</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								
								<!-- pagination -->
								<nav class="text-center" aria-label="...">
								   <ul class="pagination">
								   	  <c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
								   	  <c:if test="${currentPage == 1}">
								   	  	<c:set value="${currentPage}" var="pagePrevious"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == 1}'>class="disabled"</c:if>>
									  	<a href="${urlAdminReviews}/trang-${pagePrevious}" aria-label="Previous" >
									  		<span aria-hidden="true">«</span>
									  	</a>
									  </li>
																			      
								      <c:choose>
									      <c:when test="${totalPage > 5}">
									      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminReviews}">Đầu</a></li>
											      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminReviews}/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage <= 3}">
											      <c:forEach begin="1" end="5" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminReviews}/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage >= (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminReviews}">Đầu</a></li>
											      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
										      </c:if>
									      </c:when>
									      <c:otherwise>
									      	  <c:forEach begin="1" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:otherwise>
								      </c:choose>
								      
								      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
								      <c:if test="${currentPage == totalPage}">
								      	<c:set value="${currentPage}" var="pageNext"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
									  	<a href="${urlAdminReviews}/trang-${pageNext}" aria-label="Next">
									  		<span aria-hidden="true">»</span>
									  	</a>
									  </li>
								   </ul>
								</nav>
								<!-- end pagination -->
							</c:when>
							<c:otherwise>
								<div class="alert alert-info" role="alert">
								  	Không có dữ liệu.
								</div>
							</c:otherwise>
						</c:choose>
	  				</div>
  				</div>
  			</div>
		  </div>
		  
<script type="text/javascript">
	document.getElementById("reviews_management").className = "current";
</script>