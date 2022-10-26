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
<title>Quản lí tài khoản</title>
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
			<div class="container-fluid"
				style="background: url(resources/images/auth-bg.jpg) no-repeat center center;">
				<div class="row justify-content-center">
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

					<c:if test="${hideRegister != null}">
						<div class="col-6">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<!-- Account form -->
										<form:form class="w-100" modelAttribute="taiKhoan"
											action="employee/action/${employee.getID_NV()}.htm">
											<h4 class="mb-4 text-dark font-weight-medium text-center">
												Quản lí tài khoản <span style="color: #ff4f70;">${employee.getHoTenVaID()}</span>
											</h4>
											<div class="mt-4 d-flex justify-content-center">
												<div class="row">
													<div class="col-6">
														<label for="username" class="m-0">Tên tài khoản</label>
														<h6 class="text-danger font-weight-medium">
															<form:errors path="username" class="error-message" />
														</h6>
														<form:input class="form-control valid-char readonly"
															path="username" placeholder="Nhập tên tài khoản"
															maxlength="30" />
													</div>
													<div class="col-6">
														<label for="password" class="m-0">Mật khẩu</label>
														<h6 class="text-danger font-weight-medium">
															<form:errors path="password" class="error-message" />
														</h6>
														<form:input class="form-control readonly" type="password"
															placeholder="Nhập mật khẩu" path="password" />
													</div>
													<div class="col-6 d-none">
														<label for="password" class="m-0">Mật khẩu</label>
														<h6 class="text-danger font-weight-medium">
															<form:errors path="confirmPassword" class="error-message" />
														</h6>
														<form:input class="form-control valid-char"
															type="password" placeholder="Nhập lại mật khẩu"
															path="confirmPassword" />
													</div>
													<div class="col-6 mt-2">
														<label for="USER_ROLE" class="m-0">Quyền tài khoản</label>
														<form:select path="role.role"
															class="custom-select mr-sm-2" items="${roles}"
															itemValue="role" itemLabel="description">
														</form:select>
													</div>

													<div class="col-6 d-flex flex-column align-items-center">
														<label for="enabled" class="mt-3">Có hiệu lực ?</label>
														<div class="form-check form-check-inline">
															<form:checkbox class="form-check-input" path="enabled"
																id="inlineRadio" value="true" />
															<label class="form-check-label" for="inlineRadio">Đang
																hiệu lực</label>
														</div>
													</div>

													<div
														class="row w-100 align-items-center justify-content-center mt-3">
														<div class="col-6">
															<div class="row d-flex justify-content-center">
																<button type="submit" name="btnEdit"
																	class="btn waves-effect waves-light btn-cyan ${disableBtn}" >Chỉnh
																	sửa</button>
															</div>
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
					</c:if>

					<c:if test="${hideAccountForm != null}">
						<div class="col-6">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<!-- Account form -->
										<form:form class="w-100" modelAttribute="taiKhoan"
											action="employee/action/${employee.getID_NV()}.htm">
											<h4 class="mb-4 text-dark font-weight-medium text-center">
												Quản lí tài khoản <span style="color: #ff4f70;">${employee.getHoTenVaID()}</span>
											</h4>
											<p class="font-italic">* Nhân viên hiện chưa có tài
												khoản, hãy đăng kí !</p>
											<p class="font-italic m-0">* Tài khoản sẽ hiệu lực sau
												khi đăng kí</p>
											<div class="mt-4 d-flex justify-content-center">
												<div class="row">
													<div class="col-6">
														<label for="username" class="m-0">Tên tài khoản</label>
														<h6 class="text-danger font-weight-medium">
															<form:errors path="username" class="error-message" />
														</h6>
														<form:input class="form-control valid-char"
															path="username" placeholder="Tối đa 30 kí tự"
															maxlength="30" />
													</div>
													<div class="col-6">
														<label for="password" class="m-0">Mật khẩu</label>
														<h6 class="text-danger font-weight-medium">
															<form:errors path="password" class="error-message" />
														</h6>
														<form:input class="form-control valid-char"
															type="password" placeholder="Tối đa 30 kí tự"
															path="password" />
													</div>
													<div class="col-6 mt-2">
														<label for="USER_ROLE" class="m-0">Quyền tài khoản</label>
														<form:select path="role.role"
															class="custom-select mr-sm-2" items="${roles}"
															itemValue="role" itemLabel="description">
														</form:select>
													</div>
													<div class="col-6">
														<label for="password" class="m-0">Mật khẩu</label>
														<h6 class="text-danger font-weight-medium">
															<form:errors path="confirmPassword" class="error-message" />
														</h6>
														<form:input class="form-control valid-char"
															type="password" placeholder="Nhập lại mật khẩu"
															path="confirmPassword" />
													</div>

													<div class="col-6 d-flex flex-column align-items-center">
														<label for="enabled" class="mt-3">Có hiệu lực ?</label>
														<div class="form-check form-check-inline">
															<form:checkbox class="form-check-input disabledInput"
																path="enabled" id="inlineRadio" value="true" />
															<label class="form-check-label" for="inlineRadio">Đang
																hiệu lực</label>
														</div>
													</div>

													<div
														class="row w-100 align-items-center justify-content-center mt-3">
														<div class="col-6">
															<div class="row d-flex justify-content-center">
																<button type="submit" name="btnAdd"
																	class="btn waves-effect waves-light btn-cyan ${disableBtn}">Đăng
																	kí</button>
															</div>
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
					</c:if>

					<c:if test="${hoaDon.size() > 0}">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<h4 class="text-dark font-weight-medium">Lịch sử bán hàng</h4>
										<!-- Column -->
										<div style="max-height: 500px; overflow: auto; width: 100%">
											<table class="table table-bordered">
												<thead class="bg-primary text-white"
													style="position: sticky; top: 0; z-index: 5">
													<tr>
														<th class="text-center align-middle" scope="col">Mã
															HĐ</th>
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

					<tg:warningModal />

					<c:if test="${phieuNhapAll.size() > 0}">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<div class="row">
										<h4 class="text-dark font-weight-medium">Lịch sử nhập
											hàng</h4>
										<!-- Column -->
										<div style="max-height: 500px; overflow: auto; width: 100%">
											<table class="table table-bordered">
												<thead class="bg-primary text-white"
													style="position: sticky; top: 0; z-index: 5">
													<tr>
														<th class="text-center align-middle" scope="col">Mã
															PN</th>
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
																</c:if> <c:if test="${phNhap.getTinhTrang() == 1}">
																	<a class="edit-product ml-3" style="cursor: pointer"
																		onclick="showWarningModal('category/index/${phNhap.getID_PN()}.htm?linkDelete', 'Phiếu nhập ${phNhap.getID_PN()}')">
																		<i class="fas fa-trash" style="color: #ff4f70"></i>
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
					</c:if>

					<div></div>
					<footer class="footer text-center text-dark w-100">
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
<!--My Custom Jquery Main-->
<script>
	$(function() {
		//xuat hien modal va hien thi error message
		if (+$('.errors-count').html().trim() > 0) {
			if ($('.date-err').html().trim()) {
				$('.date-err').html('Hãy chọn một ngày');
			}
		}

		$('.disabledInput').attr('disabled', true);
		$('.readonly').attr('readonly', true);
		$('.disabled-btn').attr('disabled', true);
	})
</script>
</html>