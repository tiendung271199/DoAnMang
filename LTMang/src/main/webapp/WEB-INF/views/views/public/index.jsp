<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
                  <div class="main-content fl-right">
                  	 <c:choose>
	                  	 <c:when test="${not empty listPost}">
		                     <div class="section" id="list-blog-wp">
		                     	<c:if test="${not empty error}">
		                     		<div class="section-detail">
		                     			<div class="alert alert-danger" role="alert">${error}</div>
		                     		</div>
		                     	</c:if>
		                     	<c:if test="${not empty success}">
		                     		<div class="section-detail">
		                     			<div class="alert alert-success" role="alert">${success}</div>
		                     		</div>
		                     	</c:if>
		                        <div class="section-detail">
		                           <ul class="list-item">
		                           	  <c:forEach items="${listPost}" var="post">
			                              <li class="clearfix">
			                                 <a href="${urlDetail}/${stringUtil.makeSlug(post.title)}-${post.id}" title="${post.title}" class="thumb fl-left">
			                                 	<img src="${pictureContextPath}/images/${post.picture}" alt="Hình ảnh bài viết #ID:${post.id}">
			                                 </a>
			                                 <div class="info fl-right">
			                                    <a href="${urlDetail}/${stringUtil.makeSlug(post.title)}-${post.id}" title="${post.title}" class="title">${post.title}</a>
			                                    <span class="create-date">${dateUtil.formatDate(post.createAt)} - Đăng bởi: <a href="${urlAccount}/thong-tin-nguoi-dung-${post.user.id}">${post.user.fullname}</a> - Lượt đọc: ${post.views}</span>
			                                    <p class="desc">${post.description}</p>
			                                 </div>
			                              </li>
		                              </c:forEach>
		                           </ul>
		                        </div>
		                     </div>
		                     
		                     <div class="section" id="paging-wp">
		                        <nav aria-label="Page navigation example" style="margin-top: 15px; float: right">
								  <ul class="pagination">
								  	  <c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
								   	  <c:if test="${currentPage == 1}">
								   	  	<c:set value="${currentPage}" var="pagePrevious"></c:set>
								      </c:if>
									  <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
									  	<a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${pagePrevious}">Trang trước</a>
									  </li>
																			      
								      <c:choose>
									      <c:when test="${totalPage > 5}">
									      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
									      	  	  <li class="page-item"><a class="page-link" href="${urlIndex}trang-chu">Đầu</a></li>
											      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
											      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
											      	  	  <a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li class="page-item"><a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage <= 3}">
											      <c:forEach begin="1" end="5" var="page">
											      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
											      	  	  <a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li class="page-item"><a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage >= (totalPage - 2)}">
									      	  	  <li class="page-item"><a class="page-link" href="${urlIndex}trang-chu">Đầu</a></li>
											      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
											      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
											      	  	  <a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
										      </c:if>
									      </c:when>
									      <c:otherwise>
									      	  <c:forEach begin="1" end="${totalPage}" var="page">
										      	  <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
										      	  	  <a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:otherwise>
								      </c:choose>
								      
								      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
								      <c:if test="${currentPage == totalPage}">
								      	<c:set value="${currentPage}" var="pageNext"></c:set>
								      </c:if>
									  <li class="page-item <c:if test='${currentPage == totalPage}'>disabled</c:if>">
									  	<a class="page-link" href="${urlIndex}trang-chu<c:if test='${not empty keyword}'>/tim-kiem/${stringUtil.spaceToDash(keyword)}</c:if>/trang-${pageNext}">Trang tiếp</a>
									  </li>
								  </ul>
								</nav>
		                     </div>
	                     </c:when>
	                     <c:otherwise>
	                     	<div class="alert alert-info" role="alert">Không có dữ liệu</div>
	                     </c:otherwise>
                     </c:choose>
                  </div>