<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Thông tin người dùng</h3>
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
				<c:when test="${not empty user}">
					<table class="table table-striped table-bordered">
					  <thead> 
					    <tr>
					      <th width="23%">Tên</th>
					      <th width="23%">Email</th>
					      <th width="13%">Số điện thoại</th>
					      <th>Địa chỉ</th>
					      <c:if test="${user.role.id == 2}">
					      	  <th width="14%">Số bài viết</th>
					      </c:if>
					      <c:if test="${user.role.id == 3}">
					      	  <th width="14%">Số lượt giúp đỡ</th>
					      </c:if>
					    </tr>
					  </thead>
					  <tbody>
					    <tr>
					      <td>${user.fullname}</td>
					      <td>${user.email}</td>
					      <td>${stringUtil.beautifulPhone(user.phone)}</td>
					      <td>${user.address.detail}, ${user.address.ward.wardName}, ${user.address.district.districtName}, ${user.address.province.provinceName}</td>
					      <c:if test="${user.role.id == 2}">
					      	  <td align="center">${totalPost}</td>
					      </c:if>
					      <c:if test="${user.role.id == 3}">
					      	  <td align="center">Đã giúp: ${totalConfirmCharity}<br />Đã đăng ký: ${totalRegisterCharity}</td>
					      </c:if>
					    </tr>
					  </tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info" role="alert">Không có dữ liệu</div>
				</c:otherwise>
			</c:choose>
		</div>
		<hr>
        <div class="section" id="same-category-wp">
        	<div class="section-head">
               <h3 class="section-title">Đánh giá về người dùng</h3>
            </div>
            <hr>
            <div class="section-detail">
            	<c:choose>
            		<c:when test="${not empty listReviews}">
            			<c:forEach items="${listReviews}" var="reviews">
            				<div>
            					<p>${dateUtil.formatDate(reviews.createAt)} - Nhận xét từ người dùng <strong>${reviews.userReviews.fullname}</strong></p>
            					<c:forEach items="${reviews.list}" var="answer">
            						<p><span style="font-style: italic">${answer.question.question}</span> - ${answer.answer}</p>
            					</c:forEach>
            					<p><span style="font-style: italic">Nhận xét khác:</span> ${reviews.other}</p>
            				</div>
            				<hr>
            			</c:forEach>
            			
            			<nav aria-label="Page navigation example" style="float: right">
            				<ul class="pagination">
            					<c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
						   	    <c:if test="${currentPage == 1}">
						   	  		<c:set value="${currentPage}" var="pagePrevious"></c:set>
						        </c:if>
							    <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>">
							  		<a class="page-link" href="${urlAccount}/thong-tin-nguoi-dung-${user.id}/trang-${pagePrevious}">Trang trước</a>
							    </li>
							    
							    <c:forEach begin="1" end="${totalPage}" var="page">
						      	    <li class="page-item <c:if test='${page == currentPage}'>active</c:if>">
						      	  	    <a class="page-link" href="${urlAccount}/thong-tin-nguoi-dung-${user.id}/trang-${page}">${page}</a>
						      	    </li>
						        </c:forEach>
							    
							    <c:set value="${currentPage + 1}" var="pageNext"></c:set>
						        <c:if test="${currentPage == totalPage}">
						      		<c:set value="${currentPage}" var="pageNext"></c:set>
						        </c:if>
							    <li class="page-item <c:if test='${currentPage == totalPage}'>disabled</c:if>">
							  		<a class="page-link" href="${urlAccount}/thong-tin-nguoi-dung-${user.id}/trang-${pageNext}">Trang tiếp</a>
							    </li>
            				</ul>
            			</nav>
            		</c:when>
            		<c:otherwise>
            			<div class="alert alert-info" role="alert">Người dùng chưa có nhận xét</div>
            		</c:otherwise>
            	</c:choose>
            </div>
        </div>
	</div>
</div>