<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Viết bài kêu gọi từ thiện</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<form action="${urlAccount}/dang-bai-viet" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="title">Tiêu đề</label>
					<form:errors path="postError.title" cssStyle="color: red; font-style: italic"></form:errors>
					<input type="text" class="form-control" name="title" value="${post.title}" id="title" placeholder="Nhập tiêu đề bài viết">
				</div>
				<div class="form-group">
			        <label for="quantity">Số lượng nhà hảo tâm</label>
			        <form:errors path="postError.quantity" cssStyle="color: red; font-style: italic"></form:errors>
			        <select id="quantity" name="quantity" class="form-control">
			        	<option value="0">-- Chọn số lượng nhà hảo tâm giúp đỡ cho hoàn cảnh --</option>
			        	<option value="1">Một nhà hảo tâm</option>
			        	<option value="2">Nhiều nhà hảo tâm</option>
			        </select>
			    </div>
				<div class="form-group">
					<label for="image">Hình ảnh</label>
					<form:errors path="postError.picture" cssStyle="color: red; font-style: italic"></form:errors>
					<input type="file" name="image" id="image" class="form-control-file">
					<p class="help-block">
						<em>Định dạng: jpg, png, jpeg</em>
					</p>
				</div>
				<div class="form-group">
					<label for="description">Mô tả</label>
					<form:errors path="postError.description" cssStyle="color: red; font-style: italic"></form:errors>
					<textarea class="form-control" name="description" id="description" placeholder="Mô tả bài viết" rows="6">${post.description}</textarea>
				</div>
				<div class="form-group">
					<label for="detail">Nội dung chi tiết</label>
					<form:errors path="postError.detail" cssStyle="color: red; font-style: italic"></form:errors>
					<textarea class="form-control" name="detail" id="detail" placeholder="Nhập nội dung chi tiết của bài viết" rows="30">${post.detail}</textarea>
				</div>
				<div>
					<button type="submit" class="btn btn-success">Đăng</button>
					<button type="button" class="btn btn-primary" onclick="back()">Trở về</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	var ckeditor = CKEDITOR.replace('detail');
	CKFinder.setupCKEditor(ckeditor, '${pageContext.request.contextPath}/resources/admin/ckfinder/');
</script>