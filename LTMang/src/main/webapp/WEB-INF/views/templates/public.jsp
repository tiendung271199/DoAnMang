<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Grab Từ Thiện</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" type="image/ico" href="${contextPath}/images/logo.png" />
      
      <c:url value="/resources/public" var="contextPath" scope="application"></c:url>
      <c:url value="/resources/admin" var="adminContextPath" scope="application"></c:url>
      <c:url value="/upload" var="pictureContextPath" scope="application"/>
      
      <link href="${contextPath}/css/css/bootstrap/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/reset.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/carousel/owl.carousel.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/carousel/owl.theme.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/responsive.css" rel="stylesheet" type="text/css"/>
      
      <script src="${contextPath}/js/jquery-3.5.1.min.js" type="text/javascript"></script>
      
      <script src="${adminContextPath}/ckeditor/ckeditor.js"></script>
      <script src="${adminContextPath}/ckfinder/ckfinder.js"></script>
   </head>

	<style>
		.clr { clear: both; }
	</style>
	
	<jsp:useBean id="stringUtil" class="doan.util.StringUtil" scope="application"></jsp:useBean>
	<jsp:useBean id="dateUtil" class="doan.util.DateUtil" scope="application"></jsp:useBean>
	
	<c:url value="/" var="urlIndex" scope="application"></c:url>
	<c:url value="/chi-tiet" var="urlDetail" scope="application"></c:url>

	<c:url value="/dang-nhap" var="urlLogin" scope="application"></c:url>
	<c:url value="/dang-xuat" var="urlLogout" scope="application"></c:url>
	<c:url value="/tai-khoan" var="urlAccount" scope="application"></c:url>
	<c:url value="/tai-khoan/ho-so-ca-nhan" var="urlProfile" scope="application"></c:url>
	<c:url value="/dang-ky-tai-khoan" var="urlRegister" scope="application"></c:url>
	
	<c:url value="/address/district" var="urlDistrict" scope="application"></c:url>
  	<c:url value="/address/ward" var="urlWard" scope="application"></c:url>
	
   <body>
      <div id="site">
         <div id="container">
            <tiles:insertAttribute name="header"></tiles:insertAttribute>
            <div id="main-content-wp" class="clearfix blog-page">
               <div class="wp-inner">
                  <tiles:insertAttribute name="body"></tiles:insertAttribute>
                  <tiles:insertAttribute name="leftbar"></tiles:insertAttribute>
               </div>
            </div>
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
         </div>
         <div id="btn-top"><img class="img-fluid" src="${contextPath}/images/icon-to-top.png" alt=""/></div>
         <div id="fb-root"></div>
      </div>
      
      <script type="text/javascript">
      	  function back() {
      		  history.back();
      	  }
      </script>
      
      <script type="text/javascript">
			function getDistrictByProvinceId() {
				var ward = '<option value="0">-- Xã/Phường --</option>';
				$("#ward").html(ward);
				var provinceId = $("#province").val();
				$.ajax({
					url: '${urlDistrict}',
					type: 'POST',
					cache: false,
					dataType: 'json',
					data: {
						provinceId: provinceId
					},
					success: function(data){
						var rs = '<option value="0">-- Quận/Huyện --</option>';
						for (var i = 0; i < data.length; i++) {
							rs += '<option value="'+data[i].districtId+'">'+data[i].districtName+'</option>';
						}
						$("#district").html(rs);
					},
					error: function (){
						alert('Có lỗi xảy ra!');
					}
				});
				return false;
			};
			
			function getWardByDistrictId() {
				var districtId = $("#district").val();
				$.ajax({
					url: '${urlWard}',
					type: 'POST',
					cache: false,
					dataType: 'json',
					data: {
						districtId: districtId
					},
					success: function(data){
						var rs = '<option value="0">-- Xã/Phường --</option>';
						for (var i = 0; i < data.length; i++) {
							rs += '<option value="'+data[i].wardId+'">'+data[i].wardName+'</option>';
						}
						$("#ward").html(rs);
					},
					error: function (){
						alert('Có lỗi xảy ra!');
					}
				});
				return false;
			}
	  </script>
      
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
      <script type="text/javascript" src="https://cdn.rawgit.com/igorlino/elevatezoom-plus/1.1.6/src/jquery.ez-plus.js"></script>
      <script src="${contextPath}/js/jquery-3.5.1.min.js" type="text/javascript"></script>
      <script src="https://i-like-robots.github.io/EasyZoom/dist/easyzoom.js"></script>
      <script src="${contextPath}/js/popper.min.js" type="text/javascript"></script>
      <script src="${contextPath}/js/elevatezoom-master/jquery.elevatezoom.js" type="text/javascript"></script>
      <script src="${contextPath}/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
      <script src="${contextPath}/js/carousel/owl.carousel.js" type="text/javascript"></script>
      <script src="${contextPath}/js/main.js" type="text/javascript"></script>
   </body>
</html>