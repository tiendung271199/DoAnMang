<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Đánh giá người dùng</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<table class="table table-striped table-bordered">
			  <thead> 
			    <tr>
			      <th>Bài viết</th>
			      <th width="23%">Người kêu gọi</th>
			      <th width="23%">Nhà hảo tâm</th>
			    </tr>
			  </thead>
			  <tbody>
			    <tr>
			      <td>${registerCharity.post.title}</td>
			      <c:if test="${userLogin.role.id == 3}">
			      	  <td><a href="${urlAccount}/thong-tin-nguoi-dung-${registerCharity.post.user.id}">${registerCharity.post.user.fullname}</a></td>
			      	  <td>${registerCharity.user.fullname}</td>
			      </c:if>
			      <c:if test="${userLogin.role.id == 2}">
			      	  <td>${registerCharity.post.user.fullname}</td>
			      	  <td><a href="${urlAccount}/thong-tin-nguoi-dung-${registerCharity.user.id}">${registerCharity.user.fullname}</a></td>
			      </c:if>
			    </tr>
			  </tbody>
			</table>
		</div>
		<hr>
        <div class="section" id="same-category-wp">
        	<div class="section-head">
               <h3 class="section-title">Thực hiện đánh giá - User ${user.fullname}</h3>
            </div>
            <div class="section-detail">
            	<form action="javascript:void(0)">
            		<c:forEach items="${listQuestion}" var="question">
					    <div class="form-group">
						    <label for="question${question.id}">${question.question}</label>
						    <input type="text" class="form-control" id="question${question.id}" placeholder="Trả lời">
					    </div>
				    </c:forEach>
				    <div class="form-group">
					    <label for="other">Nhận xét khác</label>
					    <textarea class="form-control" id="other" name="other" placeholder="Những nhận xét khác" rows="6"></textarea>
				    </div>
				    <div>
				  	    <button type="button" class="btn btn-primary" onclick="sendReviews()">Gửi đánh giá</button>
				    </div>
				</form>
            </div>
        </div>
	</div>
</div>

<script type="text/javascript">
	var url = window.location.href;
	var arrUrl = url.split("/");

	async function sendReviews() {
		let arr = await getUserLogin();
		let arrData = new Array();
		for (let item of arr) {
			var value = $("#question"+item).val();
			if (value == '') {
				alert('Vui lòng nhập đủ dữ liệu trước khi gửi đánh giá');
				return;
			}
			arrData.push(item + '-' + value);
		}
		arrData.push($("#other").val());
		send(arrData);
	}
	
	function send(arrData) {
		$.ajax({
			url: '${urlAccount}/xu-ly-danh-gia',
			type: 'POST',
			cache: false,
			data: {
				id: arrUrl[6],
				q1: arrData[0],
				q2: arrData[1],
				q3: arrData[2],
				other: arrData[3]
			},
			success: function(data){
				if (data.code == 0) {
					alert('Success: '+data.content);
					location.reload();
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

	async function getUserLogin() {
		let data = await fetch('http://localhost:8081/grabtuthien/api/user/login').then(response => response.json());
		if (data.role.id == 2) {
			return getQuestion(3);
		}
		return getQuestion(2);
	}
	
	async function getQuestion(obj) {
		let arr = new Array();
		let data = await fetch('http://localhost:8081/grabtuthien/api/question/'+obj).then(response => response.json());
	    for(let item of data){
	    	arr.push(item.id);
	    }
	    return arr;
	}
</script>