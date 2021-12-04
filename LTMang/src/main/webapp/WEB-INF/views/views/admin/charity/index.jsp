<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="col-md-10">
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">ĐĂNG KÝ GIÚP ĐỠ</div>
		  			</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-8"></div>
                	<div class="col-md-4">
                		<form action="#" method="get">
		                 	<div class="input-group form">
		                       <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm kiếm">
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
							<c:when test="${not empty listCharity}">
			  					<table class="table table-striped table-bordered" id="example">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th>Tiêu đề bài viết</th>
											<th width="18%">Người đăng</th>
											<th width="18%">Người giúp đỡ</th>
											<th width="10%">Đăng ký ngày</th>
											<th width="11%">Xác nhận</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listCharity}" var="charity">
											<tr class="odd gradeX">
												<td>${charity.id}</td>
												<td>${charity.post.title}</td>
												<td>${charity.post.user.username} (${charity.post.user.fullname})</td>
												<td>${charity.user.username} (${charity.user.fullname})</td>
												<td align="center">${dateUtil.formatDate(charity.updateAt)}</td>
												<td>
													<c:if test="${charity.confirm == 1}">Đã xác nhận</c:if>
													<c:if test="${charity.confirm == 0}">Chưa xác nhận</c:if>
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
									  	<a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${pagePrevious}" aria-label="Previous" >
									  		<span aria-hidden="true">«</span>
									  	</a>
									  </li>
																			      
								      <c:choose>
									      <c:when test="${totalPage > 5}">
									      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>">Đầu</a></li>
											      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage <= 3}">
											      <c:forEach begin="1" end="5" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage >= (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>">Đầu</a></li>
											      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
										      </c:if>
									      </c:when>
									      <c:otherwise>
									      	  <c:forEach begin="1" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:otherwise>
								      </c:choose>
								      
								      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
								      <c:if test="${currentPage == totalPage}">
								      	<c:set value="${currentPage}" var="pageNext"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
									  	<a href="${urlAdminCharity}<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${pageNext}" aria-label="Next">
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
	document.getElementById("charity_management").className = "current";
</script>