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
<title>Trang chủ</title>

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
			<!-- ============================================================== -->
			<!-- Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-7 align-self-center">
						<h3
							class="text-dark font-weight-medium mb-1">Chúc
							một ngày làm việc tốt lành</h3>
						<div class="d-flex align-items-center">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb m-0 p-0">
									<li class="breadcrumb-item"><a href="index.html">Thống
											kê của bạn</a></li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- End Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- Container fluid  -->
			<!-- ============================================================== -->
			<div class="container-fluid">
				<!-- *************************************************************** -->
				<!-- Start First Cards -->
				<!-- *************************************************************** -->
				<div class="card-group">
					<div class="card border-right">
						<div class="card-body">
							<div class="d-flex d-lg-flex d-md-block align-items-center">
								<div>
									<div class="d-inline-flex align-items-center">
										<h2 class="text-dark mb-1 font-weight-medium">${soVPP}</h2>
									</div>
									<h6 class="text-muted font-weight-normal mb-0 w-100 text-white">Tổng
										văn phòng phẩm</h6>
								</div>
								<div class="ml-auto mt-md-3 mt-lg-0">
									<span class="opacity-7"><i class="fas fa-book"
										style="font-size: 24px; color: #5f76e8"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="card border-right">
						<div class="card-body">
							<div class="d-flex d-lg-flex d-md-block align-items-center">
								<div>
									<h2
										class="text-dark mb-1 w-100 text-truncate font-weight-medium">${soPN}</h2>
									<h6 class="text-muted font-weight-normal mb-0 w-100 text-white">Hóa
										đơn nhập</h6>
								</div>
								<div class="ml-auto mt-md-3 mt-lg-0">
									<span class="opacity-7"><i data-feather="dollar-sign"
										style="font-size: 24px; color: #22ca80"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="card border-right">
						<div class="card-body">
							<div class="d-flex d-lg-flex d-md-block align-items-center">
								<div>
									<div class="d-inline-flex align-items-center">
										<h2 class="text-dark mb-1 font-weight-medium">${soHD}</h2>
									</div>
									<h6 class="text-muted font-weight-normal mb-0 w-100 text-white">Hóa
										đơn xuất</h6>
								</div>
								<div class="ml-auto mt-md-3 mt-lg-0">
									<span class="opacity-7"><i data-feather="file-plus"
										style="font-size: 24px; color: #ff4f70"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-body">
							<div class="d-flex d-lg-flex d-md-block align-items-center">
								<div>
									<h2 class="text-dark mb-1 font-weight-medium">${soKH}</h2>
									<h6 class="text-muted font-weight-normal mb-0 w-100 text-white">Khách
										hàng</h6>
								</div>
								<div class="ml-auto mt-md-3 mt-lg-0">
									<span class="opacity-7"><i data-feather="globe"
										style="font-size: 24px; color: #01caf1"></i></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- *************************************************************** -->
				<div class="col-12">
					<div class="card">
						<div class="card-body">
							<h4 class="card-title text-blue">Hóa đơn của bạn ngày hôm
								nay</h4>
						</div>
						<div class="table-responsive">
							<div style="max-height: 500px; overflow: auto; width: 100%">
								<table class="table">
									<thead class="thead-light" style="position: sticky; top: 0; z-index: 5">
										<tr>
											<th scope="col">#</th>
											<th scope="col">Khách hàng</th>
											<th scope="col">Tổng tiền</th>
											<th scope="col">VAT</th>
											<th scope="col">Tình trạng</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${hoaDon}" var="hoaDon">
											<tr>
												<th scope="row">${hoaDon.id}</th>
												<td>
													<div>
														<c:choose>
															<c:when test="${hoaDon.khachHang != null}">
    																${hoaDon.khachHang.getFullDisplay()}
  																</c:when>
															<c:otherwise>
															Khách lẻ
														</c:otherwise>
														</c:choose>
													</div>
												</td>
												<td><fmt:formatNumber value="${hoaDon.getTongTien()}"
														type="currency" currencySymbol="" maxFractionDigits="0" />
												</td>
												<td><fmt:formatNumber value="${hoaDon.tienVAT}"
														type="currency" currencySymbol="" maxFractionDigits="0" /></td>
												<td>
													<div>
														<c:choose>
															<c:when test="${hoaDon.getTinhTrang() == 1}">
    																Phiếu tạm
  																</c:when>
															<c:when test="${hoaDon.getTinhTrang() == 2}">
    																Đã thanh toán
  																</c:when>
															<c:when test="${hoaDon.getTinhTrang() == 3}">
    																Đã hủy
  																</c:when>
															<c:when test="${hoaDon.getTinhTrang() == 4}">
    																Đã trả hàng
  																</c:when>
														</c:choose>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- End Container fluid  -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- footer -->
			<!-- ============================================================== -->
			<footer class="footer text-center text-muted"> All Rights
				Reserved by Group 23 D19CQCN01-N </footer>
			<!-- ============================================================== -->
			<!-- End footer -->
			<!-- ============================================================== -->
		</div>
	</div>
</body>

<!-- Scripts -->
<%@ include file="/WEB-INF/views/include/scripts.jsp"%>
</html>