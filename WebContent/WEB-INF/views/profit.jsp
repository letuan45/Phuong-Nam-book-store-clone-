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
				<div class="row">
					<c:choose>
						<c:when test="${webMessage.isSuccess()}">
							<div
								class="thongbao alert alert-success alert-dismissible bg-success text-white border-0 
                    			fade show position-absolute"
								role="alert" style="z-index: 2; right: 35px;">
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
								role="alert" style="z-index: 2; right: 35px;">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>${webMessage.getMessageType()} -</strong>
								${webMessage.getMessage()}
							</div>
						</c:when>
					</c:choose>

					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="col-12">
										<h2 class="text-dark text-center">Tổng lợi nhuận của cửa
											hàng trong năm <span style="color: #ff4f70">${year}</span></h2>
										<div class="d-flex">
											<form action="/NhaSachPN/profit/search.htm">	
												<div class="form-group d-flex">
												 	<div>
												 		<label for="yearpicker">Chọn năm</label>
														<select name="yearpicker" id="yearpicker" class="form-control"></select>
												 	</div>	
													<div class="d-flex align-items-end ml-3">
														<button type="submit" 
														class="btn btn-cyan float-right" style="height: 40px">Tra cứu</button>
													</div>							
												</div>
												
											</form>
										</div>	
										
										<div>
											<canvas id="myChart"></canvas>
										</div>

										<div class="row mt-5 d-none" style="font-size: 15px">
 											<c:forEach items="${profitOnYear}" varStatus="loop"
												var="profit">
												<div class="month-item col-1">
													<span class="month-lable">Tháng ${loop.index+1}</span> <span
														class="data-profit">${profit}</span>
												</div>
											</c:forEach>
												
										</div>

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