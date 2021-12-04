<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="col-md-10">
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">NGƯỜI DÙNG</div>
		  			</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-4">
						<a href="${urlAdminUser}/add" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>
					</div>
                	<div class="col-md-8">
                		<form action="${urlAdminUser}/tim-kiem" method="get">
		                 	<div class="input-group form">
		                       <input type="text" name="username" value="${username}" class="form-control" placeholder="Username" style="width: 40%; float: right">
		                       <select class="form-control" name="roleId" style="width: 25%; float: right; margin-right: 10px">
		                       		<option value="0">-- Vai trò user --</option>
		                       		<option <c:if test='${roleId == 1}'>selected="selected"</c:if> value="1">Admin</option>
		                       		<option <c:if test='${roleId == 4}'>selected="selected"</c:if> value="4">Mod</option>
		                       		<option <c:if test='${roleId == 2}'>selected="selected"</c:if> value="2">Người cần giúp đỡ</option>
		                       		<option <c:if test='${roleId == 3}'>selected="selected"</c:if> value="3">Người giúp đỡ</option>
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
							<c:when test="${not empty listUser}">
			  					<table class="table table-striped table-bordered" id="example">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th width="11%">Username</th>
											<th width="15%">Tên</th>
											<th width="11%">Email</th>
											<th width="9%">Số điện thoại</th>
											<th>Địa chỉ</th>
											<th width="9%">Vai trò</th>
											<th width="14%">Chức năng</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listUser}" var="user">
											<tr <c:if test='${userUpdate == user.id}'>style="color: red; font-weight: bold"</c:if> class="odd gradeX">
												<td>${user.id}</td>
												<td>${user.username}</td>
												<td>${user.fullname}</td>
												<td>${user.email}</td>
												<td align="center">${stringUtil.beautifulPhone(user.phone)}</td>
												<td>
													${user.address.detail} - ${user.address.ward.wardName} - ${user.address.district.districtName} - ${user.address.province.provinceName}
												</td>
												<td>${user.role.description}</td>
												<td class="center text-center">
													<a href="${urlAdminUser}/update/user-${user.id}" title="Cập nhật thông tin người dùng" class="btn btn-primary"><span class="glyphicon glyphicon-pencil"></span> Sửa</a>
				                                    <a href="${urlAdminUser}/del/user-${user.id}" onclick="return confirm('Bạn có chắc muốn xoá user \'${user.username}\' không?')" title="Xoá người dùng" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> Xóa</a>
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
									  	<a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${pagePrevious}" aria-label="Previous" >
									  		<span aria-hidden="true">«</span>
									  	</a>
									  </li>
																			      
								      <c:choose>
									      <c:when test="${totalPage > 5}">
									      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>">Đầu</a></li>
											      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage <= 3}">
											      <c:forEach begin="1" end="5" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage >= (totalPage - 2)}">
									      	  	  <li><a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>">Đầu</a></li>
											      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
										      </c:if>
									      </c:when>
									      <c:otherwise>
									      	  <c:forEach begin="1" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:otherwise>
								      </c:choose>
								      
								      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
								      <c:if test="${currentPage == totalPage}">
								      	<c:set value="${currentPage}" var="pageNext"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
									  	<a href="${urlAdminUser}${search}/username=<c:if test='${not empty username}'>${username}</c:if>/role=<c:if test='${not empty roleId}'>${roleId}</c:if>/trang-${pageNext}" aria-label="Next">
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
	document.getElementById("user_management").className = "current";
</script>