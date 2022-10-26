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
<title>Chỉnh Sửa TT Khách Hàng</title>
<base href="${pageContext.servletContext.contextPath}/">

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

					<tg:warningModal />

					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<!-- Column -->
									<form:form modelAttribute="khachHang"
										action="customer/updateCustomer.htm" method="post"
										class="table-responsive update-KH" style="overflow-x: unset">
										<h4 class="mb-4 text-dark font-weight-medium">Chỉnh sửa
											thông tin khách hàng</h4>
										<div class="mt-4">
											<div class="row">
												<div class="col-lg-3 col-md-6">
													<label for="ho">Họ</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="ho" class="error-message" />
													</h6>
													<form:input class="form-control" path="ho" maxlength="30"/>
												</div>

												<div class="col d-none">
													<label for="">Đã giao dịch</label>
													<form:input class="form-control" path="daGiaoDich" />
												</div>

												<div class="col d-none">
													<label for=""></label>
													<form:input class="form-control" path="id" />
												</div>

												<div class="col-lg-3 col-md-6">
													<label for="ten">Tên</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="ten" class="error-message" />
													</h6>
													<form:input class="form-control" path="ten" maxlength="20"/>
												</div>

												<div class="col-lg-3 col-md-6">
													<h6 class="text-danger font-weight-medium">
														<form:errors path="ngaySinh"
															class="error-message date-err" />
													</h6>
													<label for="ngaySinh">Ngày sinh</label>
													<form:input type="date" class="form-control"
														path="ngaySinh" placeholder="Nhập ngày sinh dd/mm/yyyy" />
												</div>
												<div class="col-lg-3 col-md-6 d-flex align-items-center">
													<p class="m-0">Giới tính</p>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="gioiTinh" class="error-message" />
													</h6>
													<div class="form-check form-check-inline">
														<form:radiobutton class="form-check-input ml-3"
															path="gioiTinh" id="inlineRadio1" value="Nam" />
														<label class="form-check-label" for="inlineRadio1">Nam</label>
														<form:radiobutton class="form-check-input ml-3"
															path="gioiTinh" id="inlineRadio2" value="Nữ" />
														<label class="form-check-label" for="inlineRadio2">Nữ</label>
													</div>
												</div>
											</div>

											<div class="row mt-2 d-flex justify-content-center">
												<div class="col-lg-3">
													<label for="sdt">Số điện thoại</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="sdt" class="error-message" />
													</h6>
													<form:input class="form-control only-number" path="sdt" maxlength="10"/>
												</div>
												<div class="col-lg-6">
													<label for="diaChi">Địa chỉ</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="diaChi" class="error-message" />
													</h6>
													<form:input class="form-control" path="diaChi" />
												</div>
											</div>
											<div style="margin-top: 20px">
												<div
													class="col row align-items-center justify-content-center">
													<!-- 
													<button type="button" class="btn btn-primary mr-4"
														data-toggle="modal" data-target="#exampleModal">
														<i class="fas fa-plus"></i> Thêm loại văn phòng phẩm
													</button> -->
													<div class="row d-flex justify-content-center"
														style="height: 50px">
														<button type="submit" name="btnUpdateCustomer"
															class="btn waves-effect waves-light btn-success">
															Chỉnh sửa khách hàng</button>
													</div>
												</div>
											</div>
										</div>
									</form:form>
									<!-- Column -->
								</div>
							</div>
						</div>
					</div>

					<c:if test="${khachHang.getDaGiaoDich() == true}">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<h4 class="text-dark font-weight-medium">Lịch sử mua hàng</h4>
										<!-- Column -->
										<div style="max-height: 500px; overflow: auto; width: 100%">
											<table class="table table-bordered">
												<thead class="bg-primary text-white"
													style="position: sticky; top: 0; z-index: 5">
													<tr>
														<th class="text-center align-middle" scope="col">Mã
															HĐ</th>
														<th class="text-center align-middle" scope="col">Nhân
															viên nhập</th>
														<th class="text-center align-middle" scope="col">Ngày
															lập</th>
														<th class="text-center align-middle" scope="col">Tổng
															Tiền</th>
														<th class="text-center align-middle" scope="col">VAT</th>
														<th class="text-center align-middle" scope="col">Tình
															trạng</th>
														<th class="text-center align-middle" scope="col">Actions</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${hoaDon}" var="hoaDon">
														<tr>
															<td class="text-center align-middle">${hoaDon.getId()}</td>
															<td class="text-center align-middle">
																<div class="text-1-line-140">${hoaDon.getNhanVien().getHoTen()}</div>
															</td>
															<td class="text-center align-middle">${hoaDon.getNgayLap()}</td>
															<td class="text-center align-middle"><fmt:formatNumber
																	value="${hoaDon.getTongTien()}" type="currency"
																	currencySymbol="" maxFractionDigits="0" /></td>
															<td class="text-center align-middle"><fmt:formatNumber
																	value="${hoaDon.getTienVAT()}" type="currency"
																	currencySymbol="" maxFractionDigits="0" /></td>

															<td class="text-center align-middle">
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

															<td class="text-center align-middle"><a
																href="invoice/index/${hoaDon.getId()}.htm?linkEdit"
																class="edit-product"> <i class="fas fa-share-square"
																	style="color: #01caf1"></i>
															</a> <c:if
																	test="${hoaDon.getTinhTrang() != 3 && hoaDon.getTinhTrang() != 1}">
																	<a style="cursor: pointer"
																		onclick="showWarningModal('invoice/index/${hoaDon.getId()}.htm?linkCancel', 'Hóa đơn ${hoaDon.getId()}')"
																		class="edit-product ml-3"> <i
																		class="fas fa-window-close" style="color: #fdc16a"></i>
																	</a>
																</c:if> <c:if test="${hoaDon.getTinhTrang() == 1}">
																	<a style="cursor: pointer"
																		onclick="showWarningModal('invoice/index/${hoaDon.getId()}.htm?linkDelete', 'Hóa đơn ${hoaDon.getId()}')"
																		class="edit-product ml-3"> <i class="fas fa-trash"
																		style="color: #ff4f70"></i>
																	</a>
																</c:if></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>

			<footer class="footer text-center text-muted w-100">
				All Rights Reserved by Group 23 D19CQCN01-N <span
					style="display: none" class="xacdinh-sidebar">${activeMapping}</span>
			</footer>
			<!-- ============================================================== -->
			<!-- End footer -->
		</div>
	</div>
</body>

<!-- Scripts -->
<%@ include file="/WEB-INF/views/include/scripts.jsp"%>
<!--My Custom Jquery Main-->
<script src="<c:url value='/resources/customer.js'></c:url>"></script>
<script>
	$(function() {
		//xuat hien modal va hien thi error message
		if (+$('.errors-count').html().trim() > 0) {
			if ($('.date-err').html().trim()) {
				$('.date-err').html('Hãy chọn một ngày');
			}
		}
	})
</script>
</html>