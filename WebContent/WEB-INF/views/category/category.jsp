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
<title>Nhập Kho | Xem phiếu</title>
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


					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="col-lg-12">
										<h3 class="mb-2 mt-2 text-dark font-weight-medium">
											<div class="btn btn-success btn-circle mb-2 btn-item">
												<i class="far fa-clipboard" style="font-size: 20px"></i>
											</div>
											Tạo phiếu nhập
										</h3>
										<form:form action="category/addCate.htm" class="mb-3"
											modelAttribute="pNhap">
											<div class="row w-100">
												<div class="col-lg-4">
													<label for="ncc">Nhà cung cấp</label>
													<form:select path="nhaCungCap.ID_NCC"
														class="custom-select mr-sm-2" items="${nhacungcap}"
														itemValue="ID_NCC" itemLabel="FullDisplay">
													</form:select>
												</div>
												<div class="col-lg-3">
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
													<div class="text-center">Tạo phiếu nhập tạm</div>

													<c:if test="">
														<button type="submit"
															class="btn mt-2 w-100 waves-effect waves-light btn-primary bg-cyan border-0">Tạo
															phiếu nhập</button>
													</c:if>
													<c:choose>
														<c:when test="${nhacungcap.size() > 0}">
															<button type="submit"
																class="btn mt-2 w-100 waves-effect waves-light btn-primary bg-cyan border-0">Tạo
																phiếu nhập</button>
														</c:when>
														<c:otherwise>
															<button type="submit"
																class="btn mt-2 w-100 waves-effect waves-light btn-primary border-0"
																disabled=true>Tạo phiếu nhập</button>
														</c:otherwise>
													</c:choose>

												</div>
											</div>
										</form:form>
										<div class="row w-100 mt-3 d-flex justify-content-center">
											<button type="button"
												class="btn btn-primary mr-4 add-ncc-btn" data-toggle="modal"
												data-target="#exampleModal">
												<i class="fas fa-plus"></i> Thêm nhà cung cấp
											</button>
											<div class="modal fade" id="exampleModal" tabindex="-1"
												role="dialog" aria-labelledby="exampleModalLabel"
												aria-hidden="true">
												<div class="modal-dialog" role="document"
													style="max-width: 650px">
													<div class="modal-content" style="border-radius: 6px">
														<div class="modal-header">
															<h4 class="modal-title" id="exampleModalLabel">Thêm
																nhà cung cấp sản phẩm</h4>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<form:form modelAttribute="nhaCungCap"
																action="category/addNCC.htm" method="POST">
																<div class="form-group">
																	<div>
																		<label for="tenLoai">Mã nhà cung cấp</label>
																		<h6 class="text-danger font-weight-medium">
																			<form:errors path="ID_NCC" class="error-message" />
																		</h6>
																		<form:input class="form-control" path="ID_NCC"
																			placeholder="Nhập chuỗi mã nhà cung cấp" />
																	</div>
																	<div class="mt-2">
																		<label for="tenNCC">Tên nhà cung cấp</label>
																		<h6 class="text-danger font-weight-medium">
																			<form:errors path="tenNCC" class="error-message" />
																		</h6>
																		<form:input class="form-control" path="tenNCC"
																			placeholder="Nhập tên nhà cung cấp" onkeydown="return /[a-z]/i.test(event.key)"/>
																	</div>
																	<div class="mt-3 d-flex align-items-center">
																		<label for="SDT" style="margin: 0 5px 0 0">SĐT</label>
																		<h6 class="text-danger font-weight-medium">
																			<form:errors path="SDT" class="error-message" />
																		</h6>
																		<form:input class="form-control only-number"
																			path="SDT" placeholder="Nhập số điện thoại" maxlength="10"/>
																		<label for="email" style="margin: 0 5px 0 5px">Email</label>
																		<h6 class="text-danger font-weight-medium">
																			<form:errors path="email" class="error-message" />
																		</h6>
																		<form:input class="form-control" path="email"
																			placeholder="Nhập email" />
																	</div>
																	<div class="mt-2">
																		<label for="diaChi">Địa chỉ nhà cung cấp</label>
																		<h6 class="text-danger font-weight-medium">
																			<form:errors path="diaChi" class="error-message" />
																		</h6>
																		<form:input class="form-control" path="diaChi"
																			placeholder="Nhập địa chỉ nhà cung cấp" />
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
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<tg:warningModal />

					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<!--title-->
									<div class="d-flex justify-content-between w-100">
										<h4 class="mb-4 text-dark font-weight-medium">Thống kê
											phiếu nhập</h4>
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
										<form action="category/actions.htm">
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
													<th class="text-center align-middle" scope="col">Mã PN</th>
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
												<c:forEach items="${phieuNhapAll}" var="phNhap">
													<tr>
														<td class="text-center align-middle">${phNhap.getID_PN()}</td>
														<td class="text-center align-middle">
															<div class="text-1-line-140">${phNhap.getNhanVien().getHoTen()}</div>
														</td>
														<td class="text-center align-middle">${phNhap.getNgayLap()}</td>
														<td class="text-center align-middle"><fmt:formatNumber
																value="${phNhap.getTongTien()}" type="currency"
																currencySymbol="" maxFractionDigits="0" /></td>
														<td class="text-center align-middle"><fmt:formatNumber
																value="${phNhap.getTienVAT()}" type="currency"
																currencySymbol="" maxFractionDigits="0" /></td>

														<td class="text-center align-middle">
															<div>
																<c:choose>
																	<c:when test="${phNhap.getTinhTrang() == 1}">
    																Phiếu tạm
  																</c:when>
																	<c:when test="${phNhap.getTinhTrang() == 2}">
    																Đã nhập hàng
  																</c:when>
																	<c:when test="${phNhap.getTinhTrang() == 3}">
    																Đã hủy
  																</c:when>
																	<c:when test="${phNhap.getTinhTrang() == 4}">
    																Đã trả hàng
  																</c:when>
																</c:choose>
															</div>
														</td>

														<td class="text-center align-middle"><a
															href="category/index/${phNhap.getID_PN()}.htm?linkEdit"
															class="edit-product"> <i class="fas fa-share-square"
																style="color: #01caf1"></i>
														</a> <c:if
																test="${phNhap.getTinhTrang() != 3 && phNhap.getTinhTrang() != 1}">
																<a style="cursor: pointer"
																	onclick="showWarningModal('category/index/${phNhap.getID_PN()}.htm?linkCancel', 'Phiếu nhập ${phNhap.getID_PN()}')"
																	class="edit-product ml-3"> <i
																	class="fas fa-window-close" style="color: #fdc16a"></i>
																</a>
															</c:if> 
														</td>
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
<script>
	$(function() {
		//xuat hien modal va hien thi error message
		if (+$('.errors-count').html().trim() > 0) {
			$('.add-ncc-btn').trigger("click");
		}
	})
</script>
</html>