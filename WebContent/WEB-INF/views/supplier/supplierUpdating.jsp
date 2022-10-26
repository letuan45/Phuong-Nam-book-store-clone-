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
<title>Chỉnh Sửa TT Nhà cung cấp</title>
<base href="${pageContext.servletContext.contextPath}/">

<!-- Links -->
<%@ include file="/WEB-INF/views/include/links.jsp"%>

</head>
<body>

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
									<!-- Column -->
									<form:form modelAttribute="supplier"
										action="supplier/updateSupplier.htm" method="post"
										class="table-responsive" style="overflow-x: unset">
										<h4 class="mb-4 text-dark font-weight-medium">Chỉnh sửa
											nhà cung cấp</h4>
										<div class="mt-4">
											<div class="row">
												<div class="col">
													<form:input class="form-control d-none" path="ID_NCC"
														placeholder="ID_NCC" />
													<label for="tenNCC">Tên nhà cung cấp</label>
													<form:input class="form-control" path="tenNCC"
														placeholder="Nhập tên NCC"/>
												</div>

												<div class="col">
													<label for="SDT">SDT</label>
													<form:input class="form-control only-number" path="SDT"
														placeholder="Nhập SDT" maxlength="10"/>
												</div>
											</div>

											<div class="row mt-3 d-flex justify-content-center">
												<div class="col-3">
													<label for="email">email</label>
													<form:input class="form-control" path="email"
														placeholder="Nhập email" />
												</div>

												<div class="col-lg-6">
													<label for="diaChi">Địa chỉ</label>
													<form:input class="form-control" path="diaChi"
														placeholder="Nhập địa chỉ" />
												</div>
											</div>
											<div style="margin-top: 20px">
												<div
													class="col row align-items-center justify-content-center">
													<div class="row d-flex justify-content-center"
														style="height: 50px">
														<button type="submit" name="btnUpdateSupplier"
															class="btn waves-effect waves-light btn-success add-KH">Chỉnh
															sửa</button>
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
<script>
	
</script>
</html>