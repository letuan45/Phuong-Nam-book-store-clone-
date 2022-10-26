<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Bán hàng | Xem phiếu</title>
<base href="${pageContext.servletContext.contextPath}/">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Links -->
<%@ include file="/WEB-INF/views/include/links.jsp"%>

</head>
<body>
	<div class="preloader">
		<div class="lds-ripple">
			<div class="lds-pos"></div>
			<div class="lds-pos"></div>
		</div>
	</div>

	<div id="main-wrapper" data-theme="light" data-layout="vertical"
		data-navbarbg="skin6" data-sidebartype="full"
		data-sidebar-position="fixed" data-header-position="fixed"
		data-boxed-layout="full">
		<!-- Header -->
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<!-- Side Menu -->
		<%@ include file="/WEB-INF/views/include/menu.jsp"%>

		<!-- Main Content -->
		<div class="page-wrapper">
			<div class="container-fluid">
				<div class="row align-items-center justify-content-center">
					<c:choose>
						<c:when test="${webMessage.isSuccess()}">
							<div
								class="thongbao alert alert-success alert-dismissible bg-success text-white border-0 
                    			fade show position-absolute"
								role="alert" style="z-index: 2; right: 35px; top: 100px">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>${webMessage.getMessageType()} -</strong>
								${webMessage.getMessage()}
							</div>
						</c:when>
						<c:when test="${webMessage.isFailed()}">
							<div
								class="thongbao alert alert-danger alert-dismissible bg-danger text-white border-0 
                    			fade show position-absolute"
								role="alert" style="z-index: 2; right: 35px; top: 100px">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>${webMessage.getMessageType()} -</strong>
								${webMessage.getMessage()}
							</div>
						</c:when>
					</c:choose>

					<div class="col-6 w-50">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="col-12">
										<div class="btn-circle btn-lg btn-cyan float-right text-white"
											style="position: absolute">
											<i class="fas fa-paper-plane"></i>
										</div>
										<h2 class="text-center">Thay đổi mật khẩu</h2>

										<form:form class="w-100"
											action="useraccount/submit-change.htm">
											<div class="mt-4 d-flex justify-content-center">
												<div class="row w-75">
													<div class="col-12">
														<label for="username" class="m-0">Tên tài khoản</label> <input
															class="form-control valid-char" name="username"
															placeholder="Nhập tên tài khoản" maxlength="30"
															value="${username}" readonly="true"/>
													</div>
												</div>
											</div>
											<div class="mt-1 d-flex justify-content-center">
												<div class="row w-75">
													<div class="col-12">
														<label for="old-password" class="m-0">Mật khẩu cũ</label> <input
															class="form-control valid-char" name="old-password"
															type="password" placeholder="Nhập mật khẩu cũ"
															value="${oldPassword}"
															maxlength="30" />
													</div>
												</div>
											</div>
											
											<div class="dropdown-divider mt-3"></div>
											
											<div class="mt-2 d-flex justify-content-center">
												<div class="row w-75">
													<div class="col-12">
														<label for="new-password" class="m-0">Mật khẩu mới</label> <input
															class="form-control valid-char" name="new-password"
															type="password" placeholder="Nhập mật khẩu mới"
															value="${newPassword}"
															maxlength="30" />
													</div>
												</div>
											</div>
											<div class="mt-3 d-flex justify-content-center">
												<div class="row w-75">
													<div class="col-12">
														<label for="new-password" class="m-0">Nhập lại mật
															khẩu mới</label> <input class="form-control valid-char"
															name="confirm-password" placeholder="Nhập lại mật khẩu mới"
															type="password" maxlength="30" 
															value="${confirmPassword}"
															/>
													</div>
												</div>
											</div>
											<div
												class="row w-100 align-items-center justify-content-center mt-3">
												<p class="font-italic text-center">*Lưu ý: sau khi thay đổi thông tin tài khoản thành công, bạn sẽ được đăng xuất !</p>
												<div class="col-6">
													<div class="row d-flex justify-content-center">
														<button type="submit"
															class="btn waves-effect waves-light btn-cyan">Xác
															nhận</button>
													</div>
												</div>
											</div>
										</form:form>

									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<footer class="footer text-center text-muted">
					All Rights Reserved by Group 23 D19CQCN01-N <span
						style="display: none" class="xacdinh-sidebar">${activeMapping}</span>
					<span class="d-none errors-count">${errorsCount}</span>
				</footer>
				<!-- ============================================================== -->
				<!-- End footer -->
			</div>
		</div>
</body>

<!-- Scripts -->
<%@ include file="/WEB-INF/views/include/scripts.jsp"%>
<script>
 $(function() {
	 for (i = new Date().getFullYear(); i > 1990; i--) {
	     $('#yearpicker').append($('<option />').val(i).html(i));
	 }
	 
	 const monthsArr = $('.month-lable').toArray().map(item => $(item).html().trim());
	 const dataArr = $('.data-profit').toArray().map(item => +$(item).html().trim());
	
	 let myChart = document.getElementById('myChart').getContext('2d');
	 let massPopChart = new Chart(myChart, {
	 	type : 'bar',
	 	data : {
	 		labels : monthsArr,
	 		datasets : [ {
	 			label : 'Lợi nhuận (VND)',
	 			data : dataArr,
	 			backgroundColor : '#ffd60a',
	 			hoverColor : '#ffc300'
	 		} ],
	 	},
	 	options : {}
	 });

});
</script>

</html>