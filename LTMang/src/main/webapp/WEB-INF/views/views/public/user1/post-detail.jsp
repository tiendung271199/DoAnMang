<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
                  <div class="main-content fl-right">
                     <div class="section" id="detail-blog-wp">
                        <div class="section-head clearfix">
                           <h3 class="section-title">Quản lý bài viết - ${post.title}</h3>
                        </div>
                        <hr />
                        <div class="section-detail">
                        	<c:set value="" var="censored"></c:set>
                        	<c:if test="${post.censored == 1}"><c:set value="Đã duyệt" var="censored"></c:set></c:if>
                        	<c:if test="${post.censored == 0}"><c:set value="Chưa duyệt" var="censored"></c:set></c:if>
                        	<c:set value="" var="status"></c:set>
                        	<c:if test="${post.status == 1}"><c:set value="Vẫn còn nhận hỗ trợ" var="status"></c:set></c:if>
                        	<c:if test="${post.status == 0}"><c:set value="Không còn nhận hỗ trợ" var="status"></c:set></c:if>
                            <span class="create-date">Đăng ngày: ${dateUtil.formatDate(post.createAt)} - Lượt xem: ${post.views} - ${censored} - ${status}</span>
                        	<img style="width: 400px; float: left" src="${pictureContextPath}/images/${post.picture}" />
                        	<p style="width: 430px; float: right">${post.description}</p>
                        	<div class="clr"></div>
                        	<div class="detail" style="margin-top: 15px">
                        		<h4>Nội dung bài viết</h4>
                        		<p>${post.detail}</p>
                        	</div>
                        	<div id="stop-charity" style="text-align: right">
                        		<button type="button" class="btn btn-primary" onclick="stop(${post.id})">Ngừng nhận hỗ trợ</button>
                        	</div>
                        </div>
                     </div>
                     <hr>
                     <div class="section" id="same-category-wp">
                        <div class="section-head">
                           <h3 class="section-title">Danh sách nhà hảo tâm đăng ký giúp đỡ</h3>
                        </div>
                        <div class="section-detail">
                        	<c:choose>
                        		<c:when test="${not empty listRegisterCharity}">
                        			<table class="table table-striped table-bordered">
                        				<thead> 
										    <tr>
										        <th width="5%">ID</th>
										        <th>Tên</th>
										        <th width="20%">Email</th>
										        <th width="13%">Số điện thoại</th>
										        <th width="18%">Chi tiết người giúp đỡ</th>
										        <th width="16%">Xác nhận giúp đỡ</th>
										        <th width="13%">Đánh giá</th>
										    </tr>
										</thead>
										<tbody>
											<c:forEach items="${listRegisterCharity}" var="reg">
												<tr>
													<td>${reg.id}</td>
													<td>${reg.user.fullname}</td>
													<td>${reg.user.email}</td>
													<td align="center">${stringUtil.beautifulPhone(reg.user.phone)}</td>
													<td align="center">
											      		<a href="${urlAccount}/thong-tin-nguoi-dung-${reg.user.id}" title="Xem chi tiết người giúp đỡ" class="btn btn-secondary">Xem</a>
											        </td>
											        <td align="center" id="confirm-charity-${reg.id}">
											        	<c:if test="${reg.confirm == 1}">
											        		<button class="btn btn-success">Đã xác nhận</button>
											        	</c:if>
											        	<c:if test="${reg.confirm == 0}">
											        		<button title="Xác nhận người sẽ giúp đỡ" onclick="charity(${reg.id})" class="btn btn-primary">Xác nhận</button>
											        	</c:if>
											        </td>
											        <td>
											      		<a href="${urlAccount}/danh-gia-nguoi-dung/${reg.id}" class="btn btn-primary">Đánh giá</a>
											        </td>
										        </tr>
											</c:forEach>
										</tbody>
	                        		</table>
                        		</c:when>
                        		<c:otherwise>
                        			<div class="alert alert-info" role="alert">Chưa có nhà hảo tâm đăng ký giúp đỡ</div>
                        		</c:otherwise>
                        	</c:choose>
                        </div>
                     </div>
                  </div>
                  
<script type="text/javascript">
	function charity(id) {
		var conf = confirm("Bạn có chắc muốn xác nhận nhà hảo tâm sẽ giúp đỡ?\nLưu ý: khi xác nhận sẽ không thể thay đổi");
		if (conf == true) {
			confirmCharity(id);
		}
	}
	
	function confirmCharity(id) {
		$.ajax({
			url: '${urlIndex}xac-nhan-dang-ky-giup-do',
			type: 'POST',
			cache: false,
			data: {
				id: id
			},
			success: function(data){
				if (data.code == 0) {
					alert('Success: '+data.content);
					$("#confirm-charity-"+id).html('<button class="btn btn-success">Đã xác nhận</button>');
				} else {
					alert('Error: '+data.content);
				}
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
	
	function stop(id) {
		var conf = confirm("Bạn có chắc muốn ngừng nhận hỗ trợ cho hoàn cảnh này không?\nLưu ý: khi xác nhận sẽ không thể thay đổi");
		if (conf == true) {
			confirmStop(id);
		}
	}
	
	function confirmStop(id) {
		$.ajax({
			url: '${urlAccount}/quan-ly-bai-viet/ngung-nhan-ho-tro',
			type: 'POST',
			cache: false,
			data: {
				id: id
			},
			success: function(data){
				if (data.code == 0) {
					alert('Success: '+data.content);
					$("#stop-charity").html('<button type="button" class="btn btn-secondary">Đã ngừng nhận hỗ trợ</button>');
				} else {
					alert('Error: '+data.content);
				}
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
</script>