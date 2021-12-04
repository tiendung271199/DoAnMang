<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
		  <div class="col-md-10">
  			<div class="row">
  				<div class="col-md-12 panel-info">
		  			<div class="content-box-header panel-heading">
	  					<div class="panel-title ">Xem chi tiết bài viết</div>
		  			</div>
		  			<div class="content-box-large box-with-header">
			  			<div>
							<div class="row mb-10"></div>
							<div class="row">
								<div class="col-sm-12">
									<div>
										<h4 style="color: red">Tiêu đề</h4>
										<p style="font-size: 14px">${post.title}</p>
									</div>
									<hr>
									<div>
										<h4 style="color: red">Thông tin khác</h4>
										<p style="font-size: 14px">Đăng bởi: ${post.user.username} (${post.user.fullname}) - Đăng ngày: ${dateUtil.formatDate(post.createAt)} - Lần cập nhật gần nhất: ${dateUtil.formatDate(post.updateAt)}</p>
									</div>
									<hr>
									<div>
										<h4 style="color: red">Mô tả</h4>
										<div style="font-size: 14px">${post.description}</div>
									</div>
									<hr>
									<div>
										<h4 style="color: red">Nội dung</h4>
										<div style="font-size: 14px">${post.detail}</div>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="col-sm-12">
									<c:if test="${post.censored == 1}">
										<input type="button" value="Đã duyệt" class="btn btn-secondary" />
									</c:if>
									<c:if test="${post.censored == 0}">
										<input id="btn-censored" type="button" value="Duyệt" onclick="censored(${post.id})" class="btn btn-success" />
									</c:if>
									<input style="margin-left: 5px" type="button" onclick="back()" value="Quay lại" class="btn btn-primary" />
								</div>
							</div>
						</div>
					</div>
		  		</div>
  			  </div>
		  </div>

<script type="text/javascript">
	function censored(id) {
		var conf = confirm("Bạn có chắc muốn duyệt bài viết này?\nLưu ý: Khi duyệt sẽ không thể thay đổi trạng thái về chưa duyệt");
		if (conf == true) {
			confirmCensored(id);
		}
	}
	
	function confirmCensored(id) {
		$.ajax({
			url: '${urlAdminPost}/censored',
			type: 'POST',
			cache: false,
			data: {
				id: id
			},
			success: function(data){
				if (data.code == 0) {
					alert('Success: '+data.content);
					$("#btn-censored").val("Đã duyệt");
					$("#btn-censored").prop("onclick", null);
					$("#btn-censored").addClass("btn-secondary").removeClass("btn-success");
					location.reload();
				} else {
					alert('Error: '+data.content);
				}
			},
			error: function (e){
				console.log(e);
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
</script>		  
		  
<script type="text/javascript">
	document.getElementById("post_management").className = "current";
</script>