<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="col-md-10">
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">BÀI VIẾT</div>
		  			</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-4"></div>
                	<div class="col-md-8">
                		<form action="${urlAdminPost}/tim-kiem" method="get">
		                 	<div class="input-group form">
		                       <input type="text" name="title" value="${title}" class="form-control" placeholder="Tiêu đề bài viết" style="width: 45%; float: right">
		                       <input type="text" name="username" value="${username}" class="form-control" placeholder="Username người đăng" style="width: 25%; float: right; margin: 0px 10px">
		                       <select class="form-control" name="censored" style="width: 27%; float: right">
		                       		<option value="2">Trạng thái kiểm duyệt</option>
		                       		<option <c:if test='${censored == 1}'>selected="selected"</c:if> value="1">Đã duyệt</option>
		                       		<option <c:if test='${censored == 0}'>selected="selected"</c:if> value="0">Chưa duyệt</option>
		                       </select>
		                       <div style="clear: both"></div>
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
	  					<c:if test="${not empty msg}">
							<div class="alert alert-info" role="alert">
							    ${msg}
							</div>
						</c:if>
		  				<c:choose>
							<c:when test="${not empty listPost}">
			  					<table class="table table-striped table-bordered" id="example">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th>Tiêu đề</th>
											<th width="12%">Người đăng</th>
											<th width="7%">Lượt xem</th>
											<th width="20%">Hình ảnh</th>
											<th width="8%">Kiểm duyệt</th>
											<th width="7%">Chi tiết</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listPost}" var="post">
											<tr <c:if test='${postUpdate == post.id}'>style="color: red; font-weight: bold"</c:if> class="odd gradeX">
												<td>${post.id}</td>
												<td>${post.title}</td>
												<td>${post.user.username}</td>
												<td align="center">${post.views}</td>
												<td align="center">
													<img width="200px" height="120px" src="${pictureContextPath}/images/${post.picture}" title="${post.title}" />
												</td>
												<td align="center">
													<c:if test="${post.censored == 1}">Đã duyệt</c:if>
													<c:if test="${post.censored == 0}">Chưa duyệt</c:if>
												</td>
												<td class="center text-center">
													<a href="${urlAdminPost}/chi-tiet/${post.id}" title="Xem chi tiết bài viết" class="btn btn-primary">Xem</a>
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
									  	<a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${pagePrevious}" aria-label="Previous" >
									  		<span aria-hidden="true">«</span>
									  	</a>
									  </li>
																			      
								      <c:choose>
									      <c:when test="${totalPage > 5}">
									      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>">Đầu</a></li>
											      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage <= 3}">
											      <c:forEach begin="1" end="5" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage >= (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>">Đầu</a></li>
											      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
										      </c:if>
									      </c:when>
									      <c:otherwise>
									      	  <c:forEach begin="1" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:otherwise>
								      </c:choose>
								      
								      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
								      <c:if test="${currentPage == totalPage}">
								      	<c:set value="${currentPage}" var="pageNext"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
									  	<a href="${urlAdminPost}${search}/title=<c:if test='${not empty title}'>${stringUtil.spaceToDash(title)}</c:if>/username=<c:if test='${not empty username}'>${username}</c:if>/censored=<c:if test='${not empty censored}'>${censored}</c:if>/trang-${pageNext}" aria-label="Next">
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
	document.getElementById("post_management").className = "current";
</script>