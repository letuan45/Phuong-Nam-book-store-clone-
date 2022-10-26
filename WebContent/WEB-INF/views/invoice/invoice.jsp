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


					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="col-lg-12">
										<h3 class="mb-2 mt-2 text-dark font-weight-medium">
											<div class="btn btn-danger btn-circle mb-2 btn-item">
												<i class="far fa-list-alt" style="font-size: 20px"></i>
											</div>
											Tạo hóa đơn
										</h3>
										<form action="invoice/addInvoice.htm" class="mb-3"
											modelAttribute="hoaDon">
											<div class="row w-100">
												<div class="col-lg-3">
													<label for="khachHang">Khách hàng</label> <input readonly
														id="khachHang" type="text" class="form-control"
														name="khachHang" placeholder="Mã khách hàng" value="">
												</div>
												<div class="col-lg-4">
													<label for="nhanvien">Nhân viên nhập</label> <input
														readonly id="nhanvien" type="text" class="form-control"
														name="nhanvien" placeholder="Tên nv"
														value="${user.getID_NV()} - ${user.getHo()} ${user.getTen()}">
												</div>

												<div class="col-lg-2">
													<label for="ngaylap">Ngày lập</label> <input readonly
														id="ngaylap" type="text" class="form-control"
														name="ngaylap" placeholder="Ngày lập" value="${dateNow}">
												</div>

												<div class="col-lg-3">
													<div class="text-center">Tạo hóa đơn tạm</div>

													<button type="submit"
														class="btn mt-2 w-100 waves-effect waves-light btn-primary bg-cyan border-0">Tạo
														hóa đơn</button>

												</div>
											</div>
										</form>
										<div class="row w-100 mt-3 d-flex justify-content-center">
											<button type="button" class="btn btn-primary mr-4 add-KH"
												data-toggle="modal" data-target="#exampleModal">
												<i class="fas fa-plus"></i> Thêm khách hàng
											</button>
											<div class="modal fade" id="exampleModal" tabindex="-1"
												role="dialog" aria-labelledby="exampleModalLabel"
												aria-hidden="true">
												<div class="modal-dialog" role="document"
													style="max-width: 650px">
													<div class="modal-content" style="border-radius: 6px">
														<div class="modal-header">
															<h4 class="modal-title" id="exampleModalLabel">Thêm
																khách hàng và thông tin khách hàng</h4>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<form:form modelAttribute="khachHang"
																action="invoice/addClient.htm" method="POST">
																<div class="form-group">

																	<div class="d-flex">
																		<div class="col-6 p-0">
																			<label for="ho">Họ khách hàng</label>
																			<h6 class="text-danger font-weight-medium">
																				<form:errors path="ho" class="error-message" />
																			</h6>
																			<form:input class="form-control" path="ho"
																				placeholder="Nhập họ khách hàng" onkeydown="return /[a-z]/i.test(event.key)"/>
																		</div>

																		<div class="col-6 p-0 ml-2">
																			<label for="ho">Tên khách hàng</label>
																			<h6 class="text-danger font-weight-medium">
																				<form:errors path="ten" class="error-message" />
																			</h6>
																			<form:input class="form-control" path="ten"
																				placeholder="Nhập tên khách hàng" onkeydown="return /[a-z]/i.test(event.key)"/>
																		</div>
																	</div>

																	<div class="d-flex mt-2">
																		<div class="col-6 p-0">
																			<label for="sdt">Số điện thoại</label>
																			<h6 class="text-danger font-weight-medium">
																				<form:errors path="sdt" class="error-message" />
																			</h6>
																			<form:input class="form-control only-number"
																				path="sdt" placeholder="Nhập SĐT khách hàng" />
																		</div>

																		<div class="col-6 p-0 ml-2">
																			<label for="ngaySinh">Ngày sinh</label>
																			<h6 class="text-danger font-weight-medium date-err">
																				<form:errors path="ngaySinh" class="error-message" />
																			</h6>
																			<form:input type="date" class="form-control"
																				path="ngaySinh" placeholder="Chọn ngày sinh" />
																		</div>
																	</div>

																	<div class="mt-2">
																		<label for="diaChi">Địa chỉ</label>
																		<h6 class="text-danger font-weight-medium">
																			<form:errors path="diaChi" class="error-message" />
																		</h6>
																		<form:input class="form-control" path="diaChi"
																			placeholder="Nhập địa chỉ" />
																	</div>

																	<div class="mt-2 d-flex justify-content-center">
																		<div class="form-check form-check-inline">
																			Giới tính
																			<h6 class="text-danger font-weight-medium mt-2">
																				<form:errors path="gioiTinh" class="error-message" />
																			</h6>
																			<form:radiobutton class="form-check-input ml-3"
																				path="gioiTinh" id="inlineRadio1" value="Nam" />
																			<label class="form-check-label" for="inlineRadio1">Nam</label>
																			<form:radiobutton class="form-check-input ml-3"
																				path="gioiTinh" id="inlineRadio2" value="Nữ" />
																			<label class="form-check-label" for="inlineRadio2">Nữ</label>
																		</div>
																	</div>
																</div>
																<button type="submit"
																	class="btn btn-primary float-right">Submit</button>
															</form:form>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-secondary"
																data-dismiss="modal">Close</button>
														</div>
													</div>
												</div>
											</div>
											<button type="button" class="btn btn-primary mr-4"
												data-toggle="modal" data-target="#exampleModal1">
												<i class="fas fa-search"></i> Chọn khách hàng
											</button>
											<div class="modal fade" id="exampleModal1" tabindex="-1"
												role="dialog" aria-labelledby="exampleModalLabel1"
												aria-hidden="true">
												<div class="modal-dialog" role="document"
													style="max-width: 950px">
													<div class="modal-content" style="border-radius: 6px">
														<div class="modal-header">
															<h4 class="modal-title" id="exampleModalLabel1">Danh
																sách khách hàng</h4>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body"
															style="max-height: 400px; overflow: auto;">
															<form class="mb-3">
																<h5 class="text-dark font-weight-medium">Tìm kiếm</h5>
																<div class="row w-100">
																	<div class="col-2">

																		<input type="text"
																			class="form-control idClient only-number"
																			name="tenVPP" placeholder="Nhập ID">
																	</div>
																	<div class="col-4">
																		<input type="text" class="form-control fnClient"
																			name="fnClient" placeholder="Nhập Họ">
																	</div>
																	<div class="col-3">
																		<input type="text" class="form-control lnClient"
																			name="lnClient" placeholder="Nhập Tên">
																	</div>
																	<div class="col-3">
																		<input type="text"
																			class="form-control phoneClient only-number"
																			name="phoneClient" placeholder="Nhập SĐT">
																	</div>
																</div>
															</form>

															<table class="table table-hover">
																<thead class="thead text-dark"
																	style="background-color: #01caf1; position: sticky; top: 0; z-index: 5;">
																	<tr>
																		<th scope="col" class="text-center w-25">#id</th>
																		<th scope="col" class="text-center w-25">Họ</th>
																		<th scope="col" class="text-center w-25">Tên</th>
																		<th scope="col" class="text-center w-25">SĐT</th>
																	</tr>
																</thead>
																<tbody class="khachHangTable">
																	<c:forEach items="${khachhang}" var="kh">
																		<tr class="client-item" data-set="${kh.id}">
																			<th scope="row" class="idKH text-center">${kh.id}</th>
																			<td class="hoKH text-center">${kh.ho}</td>
																			<td class="tenKH text-center">${kh.ten}</td>
																			<td class="sdtKH text-center">${kh.sdt}</td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
														<div class="modal-footer">
															<button type="button"
																class="btn btn-primary single-client">Khách lẻ</button>
															<button type="button"
																class="btn btn-secondary closeModal"
																data-dismiss="modal">Close</button>
														</div>
													</div>
												</div>
											</div>


										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<tg:warningModal />

					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<!--title-->
									<div class="d-flex justify-content-between w-100">
										<h4 class="mb-4 text-dark font-weight-medium">Thống kê
											hóa đơn</h4>
										<div>
											<span> Chỉnh sửa: <i class="fas fa-share-square"
												style="color: #01caf1"></i>
											</span> <span class="ml-2 mr-2"> Hủy phiếu: <i
												class="fas fa-window-close" style="color: #fdc16a"></i>
											</span> 
										</div>
									</div>
									<div class="d-flex w-100">
										<h4 class="mb-4 text-dark font-weight-medium">Tìm kiếm</h4>
										<form action="invoice/actions.htm">
											<div class="form-group">
												<div class="row">
													<div class="from-date ml-4">
														<label for="maPhieu">Mã phiếu</label> <input
															class="form-control only-number" id="maPhieu"
															name="maPhieu" placeholder="Nhập mã phiếu">
													</div>

													<div class="from-date ml-4">
														<label for="from-date">Từ ngày</label> <input type="date"
															class="form-control" id="from-date" name="from-date">
													</div>
													<div class="from-date ml-4">
														<label for="to-date">Đến ngày</label> <input type="date"
															class="form-control" id="to-date" name="to-date">
													</div>
													<div class="from-date ml-4 d-flex align-items-end">
														<button type="submit" name="btnSearch"
															class="btn btn-primary float-right">Tìm kiếm</button>
													</div>

												</div>
												<div class="ml-4 text-dark d-flex align-items-center mt-3">
													ID Phiếu: <span class="mr-1" style="color: #ff4f70;">${idPhieu}</span>
													Từ ngày: <span class="mr-2" style="color: #ff4f70;">${fromDate}</span>
													Đến ngày: <span style="color: #ff4f70;">${toDate}</span>
												</div>
											</div>
										</form>
									</div>

									<div style="max-height: 500px; overflow: auto; width: 100%">
										<table class="table table-bordered">
											<thead class="bg-primary text-white"
												style="position: sticky; top: 0; z-index: 5">
												<tr>
													<th class="text-center align-middle" scope="col">Mã HĐ</th>
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
												<c:forEach items="${hoaDonAll}" var="hoaDon">
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
															</c:if></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<!-- Column -->
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
<script src="<c:url value='/resources/invoice.js'></c:url>"></script>
<script>
	$(function() {
		//xuat hien modal va hien thi error message
		if (+$('.errors-count').html().trim() > 0) {
			$('.add-KH').trigger("click");
			if ($('.date-err').html().trim()) {
				$('.date-err').html('Hãy chọn một ngày');
			}
		}
	})
</script>

</html>