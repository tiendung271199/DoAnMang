<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="row">
	<div class="col-md-4 col-md-offset-4">
		<div class="login-wrapper">
	        <div class="box">
	            <div class="content-wrap">
	            	<img width="100px" class="img-circle" src="${authContextPath}/images/logo.png">
	                <h6>Đăng nhập</h6>
					<form id="formLogin" action="" method="post">
						<c:if test="${not empty param.msg}">
							<div class="alert alert-danger" role="alert">
							    Tài khoản hoặc mật khẩu không chính xác
							</div>
						</c:if>
						
		                <div class="form-group">
		                	<label class="text-left pull-left" for="username">Tên đăng nhập</label>
		               		<input class="form-control" name="username" id="username" value="" type="text" placeholder="Tên đăng nhập" required="required">
		                </div>
		                
		                <div class="form-group">
		                	<label class="text-left pull-left" for="password">Mật khẩu</label>
		                	<input class="form-control" name="password" id="password" type="password" placeholder="Mật khẩu" required="required">
		                </div>
		                
		                <div class="row" style="margin-top: 30px">
							<div class="col-sm-12">
								<input type="submit" value="Đăng nhập" class="btn btn-primary signup btn-block" />
							</div>
						</div>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
</div>