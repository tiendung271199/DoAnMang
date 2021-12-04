<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
		  <div class="col-md-10">
  			<div class="row">
  				<div class="col-md-12 panel-info">
		  			<div class="content-box-header panel-heading">
	  					<div class="panel-title ">Xem chi tiết đánh giá người dùng - ${reviews.userDuocReviews.username} (${reviews.userDuocReviews.fullname})</div>
		  			</div>
		  			<div class="content-box-large box-with-header">
			  			<div>
							<div class="row mb-10"></div>
							<div class="row">
								<div class="col-sm-12">
									<div>
										<h4 style="color: red">User thực hiện đánh giá</h4>
										<p style="font-size: 14px">${reviews.userReviews.username} (${reviews.userReviews.fullname})</p>
									</div>
									<hr>
									<div>
										<h4 style="color: red">Nội dung đánh giá</h4>
										<c:forEach items="${listAnswer}" var="answer">
		            						<p style="font-size: 14px">
		            							<span style="font-style: italic">${answer.question.question}</span> - ${answer.answer}
		            						</p>
		            					</c:forEach>
		            					<p style="font-size: 14px"><span style="font-style: italic">Nhận xét khác:</span> ${reviews.other}</p>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="col-sm-12">
									<input style="margin-left: 5px" type="button" onclick="back()" value="Quay lại" class="btn btn-primary" />
								</div>
							</div>
						</div>
					</div>
		  		</div>
  			  </div>
		  </div>
		  
<script type="text/javascript">
	document.getElementById("reviews_management").className = "current";
</script>