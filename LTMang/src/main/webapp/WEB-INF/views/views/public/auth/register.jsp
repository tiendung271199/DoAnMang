<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Đăng ký tài khoản</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<form action="${urlRegister}" method="post">
				<div class="form-group">
					<label for="fullname">Tên</label>
					<form:errors path="userError.fullname" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.fullname}" name="fullname" class="form-control" placeholder="Nhập họ tên" >										
				</div>
				
				<div class="form-group">
					<label for="email">Email</label>
					<form:errors path="userError.email" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.email}" name="email" class="form-control" placeholder="Nhập email" >										
				</div>
				
				<div class="form-group">
					<label for="phone">Số điện thoại</label>
					<form:errors path="userError.phone" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.phone}" name="phone" class="form-control" placeholder="Nhập số điện thoại" >										
				</div>
				
				<div class="form-group">
					<label for="province">Địa chỉ (Tỉnh/Thành phố)</label>
					<form:errors path="addressError.province" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="province.provinceId" class="form-control" id="province" onchange="getDistrictByProvinceId()">
						<option value="0">-- Tỉnh/Thành phố --</option>
						<c:if test="${not empty listProvince}">
							<c:forEach items="${listProvince}" var="province" >
								<option value="${province.provinceId}" <c:if test='${province.provinceId == address.province.provinceId}'>selected="selected"</c:if>>${province.provinceName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="district">Địa chỉ (Quận/Huyện)</label>
					<form:errors path="addressError.district" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="district.districtId" class="form-control" id="district" onchange="getWardByDistrictId()">
						<option value="0">-- Quận/Huyện --</option>
						<c:if test="${not empty listDistrict}">
							<c:forEach items="${listDistrict}" var="district" >
								<option value="${district.districtId}" <c:if test='${district.districtId == address.district.districtId}'>selected="selected"</c:if>>${district.districtName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="ward">Địa chỉ (Xã/Phường)</label>
					<form:errors path="addressError.ward" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="ward.wardId" class="form-control" id="ward">
						<option value="0">-- Xã/Phường --</option>
						<c:if test="${not empty listWard}">
							<c:forEach items="${listWard}" var="ward" >
								<option value="${ward.wardId}" <c:if test='${ward.wardId == address.ward.wardId}'>selected="selected"</c:if>>${ward.wardName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="detail">Địa chỉ (Số nhà, tên đường...)</label>
					<form:errors path="addressError.detail" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" name="detail" class="form-control" value="${address.detail}" placeholder="Nhập số nhà, tên đường...">
				</div>
				
				<div class="form-group">
					<label for="role">Vai trò</label>
					<form:errors path="userError.role" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="role.id" class="form-control selectpicker">
						<option value="0">-- Chọn vai trò --</option>
						<c:if test="${not empty listRole}">
							<c:forEach items="${listRole}" var="role">
								<option value="${role.id}" <c:if test='${role.id == user.role.id}'>selected="selected"</c:if>>${role.description}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="username">Tên đăng nhập</label>
					<form:errors path="userError.username" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.username}" name="username" class="form-control" placeholder="Nhập tên đăng nhập (Username)" >
					<p class="help-block"><em>Định dạng: Tên đăng nhập từ 6 - 20 ký tự, không có dấu, không được chứa ký tự đặc biệt và khoảng trắng</em></p>
				</div>
				
				<div class="form-group">
					<label for="password">Mật khẩu</label>
					<form:errors path="userError.password" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="password" value="" name="password" class="form-control" placeholder="Nhập mật khẩu (Password)" >
					<p class="help-block"><em>Định dạng: Mật khẩu từ 6 - 20 ký tự, phải chứa ít nhất 1 ký tự hoa, 1 ký tự thường, 1 chữ số và 1 ký tự đặc biệt</em></p>
				</div>
				
				<div class="form-group">
					<label for="confirmPassword">Xác nhận mật khẩu</label>
					<span style="color:red; font-style:italic">${confirmPasswordError}</span>
					<input type="password" value="" name="confirmPassword" class="form-control" placeholder="Xác nhận mật khẩu đã nhập ở trên" >										
				</div>
				<div>
					<button type="submit" class="btn btn-success">Đăng ký</button>
				</div>
			</form>
		</div>
	</div>
</div>