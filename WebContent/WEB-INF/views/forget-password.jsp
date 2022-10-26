<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Login</title>

<!-- Links -->
<%@ include file="/WEB-INF/views/include/links.jsp"%>

</head>
<body>
	<section class="vh-100" style="background-color: #e5e5e5;">
		<c:choose>
			<c:when test="${webMessage.isFailed()}">
				<div
					class="thongbao alert alert-danger alert-dismissible bg-danger text-white border-0 
                    			fade show position-absolute"
					role="alert" style="z-index: 2; right: 200px; margin-top: 100px">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<strong>${webMessage.getMessageType()} -</strong>
					${webMessage.getMessage()}
				</div>
			</c:when>
		</c:choose>
		<div
			class="container py-5 h-100 d-flex justify-content-center align-items-center">

			<div
				style="border-radius: 30px; background-color: white; overflow: hidden; box-shadow: 0px 0px 15px -7px rgba(0, 0, 0, 0.87);">
				<div
					class="row d-flex align-items-center justify-content-center h-100">
					<div class="col-md-8 col-lg-7 col-xl-6">
						<img src='<c:url value="resources/images/unnamed.jpg"></c:url>'
							"			
							class="img-fluid" alt="Phone image">
					</div>
					<div class="col-md-7 col-xl-5">
						<h1 style="color: #560bad" class="font-weight-medium">Quên mật khẩu</h1>

						<form name='f'
							action="${pageContext.request.contextPath}/j_spring_security_check"
							method='POST' id="myForm">
							<!-- Username input -->
							<div class="form-outline mb-2">
								<input type="text" id="form1Example13"
									placeholder="Nhập tên đăng nhập" name="username"
									value="${username}"
									class="form-control form-control-lg valid-char login-usn" /> <label
									class="form-label" for="form1Example13"
									style="color: #f72585; font-weight: 500; font-size: 20px">Tài
									khoản</label>
							</div>

							<!-- Password input -->
							<div class="form-outline mb-2">
								<input type="password" id="form1Example23"
									placeholder="Nhập mật khẩu" name="password" value="${password}"
									class="form-control form-control-lg valid-char login-pass" />
								<label class="form-label" for="form1Example23"
									style="color: #f72585; font-weight: 500; font-size: 20px">Mật
									khẩu</label>
							</div>

							<div
								class="d-flex justify-content-around align-items-center mb-4">
								<!-- Checkbox -->
								<div class="form-check">
									<input class="form-check-input" type="checkbox"
										id="form1Example3" checked /> <label class="form-check-label"
										for="form1Example3" style="font-size: 18px"> Ghi nhớ
										tài khoản </label>
								</div>
								<a href="forget-password.htm" style="font-size: 18px">Quên
									mật khẩu ?</a>
							</div>

							<!-- Submit button -->
							<div class="d-flex justify-content-center">
								<button type="submit" class="btn btn-primary btn-lg btn-block">
									Đăng nhập</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>

<!-- Scripts -->
<%@ include file="/WEB-INF/views/include/scripts.jsp"%>
<script type="text/javascript">
	$('#myForm').on('submit', function(e) {
		if ($('.login-usn').val() == '' && $('.login-pass').val() == '') {
			alert('Bạn không thể để trống tên tài khoản và mật khẩu');
			return false;
		}
		if ($('.login-usn').val() == '') {
			alert('Bạn không thể để trống tên tài khoản');
			return false;
		}
		if ($('.login-pass').val() == '') {
			alert('Bạn không thể để trống mật khẩu');
			return false;
		}

	});
</script>
</html>