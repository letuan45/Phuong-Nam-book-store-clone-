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
<title>Bán hàng | Thao tác</title>
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

					<div class="col-lg-12 ${displayNone}">
						<div class="card">
							<div class="card-body">
								<div class="row flex-column">
									<!--Search form-->
									<form action="category/actions.htm" class="mb-3 w-100">
										<h5 class="text-dark font-weight-medium">Tìm kiếm</h5>
										<div class="row w-100">
											<!-- <div class="col-1">
												<input type="text" class="form-control" name="idVPP"
													placeholder="ID">
											</div> -->
											<div class="col-lg-3">
												<input type="text" class="form-control idAndNameP"
													name="tenVPP" placeholder="Nhập Id hoặc tên VPP">
											</div>
											<div class="col-lg-3">
												<select class="custom-select loaiSearch" name="loai">
													<option selected value="">Loại VPP ...</option>
													<c:forEach items="${loaisp}" var="loai">
														<option value="${loai.getTenLoai()}">${loai.getTenLoai()}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-lg-3">
												<select class="custom-select thuongHieuSearch" name="loai">
													<option selected value="">Thương hiệu ...</option>
													<c:forEach items="${thuonghieu}" var="th">
														<option value="${th.getTenTH()}">${th.getTenTH()}</option>
													</c:forEach>
												</select>
											</div>
											<!-- <div class="col-2">
												<button name="btnSearch"
													class="btn waves-effect waves-light btn-primary bg-cyan border-0">Xác
													nhận</button>
											</div> -->
										</div>
									</form>

									<!-- Column -->
									<div class="w-100"
										style="max-height: 400px; overflow: auto; border: 1px solid #e5e5e5">
										<table class="table table-bordered">
											<thead class="bg-primary text-white"
												style="position: sticky; top: 0; z-index: 5">
												<tr>
													<th class="text-center align-middle" scope="col">Mã
														VPP</th>
													<th class="text-center align-middle" scope="col">Tên
														VPP</th>
													<th class="text-center align-middle" scope="col">Hình
														ảnh</th>
													<th class="text-center align-middle" scope="col">Số
														lượng</th>
													<th class="text-center align-middle" scope="col">Giá
														bán (VND)</th>
													<th class="text-center align-middle" scope="col">Loại</th>
													<th class="text-center align-middle" scope="col">Thương
														hiệu</th>
													<th class="text-center align-middle" scope="col">Chọn</th>
												</tr>
											</thead>
											<tbody class="product-table-body">
												<c:forEach items="${products}" var="product">
													<tr data-set="${product.getID_VPP()}" class="table-item">
														<th scope="row"
															class="text-center align-middle product-id">${product.getID_VPP()}</th>
														<td class="text-center align-middle text-1-line-140">
															<div class="text-1-line-140 product-name">${product.getTenVPP()}</div>
														</td>
														<td class="text-center align-middle"><img
															src="${product.getHinhAnh()}" alt="ảnh sản phẩm"
															class="rounded mx-auto"
															style="max-width: 100px; max-height: 50px"></td>
														<td class="text-center align-middle product-quan">${product.getSoLuong()}</td>
														<td class="text-center align-middle product-price"><fmt:formatNumber
																value="${product.getGiaBan()}" type="currency"
																currencySymbol="" maxFractionDigits="0" /></td>
														<td class="text-center align-middle">
															${product.getLoai().getTenLoai()}</td>
														<td class="text-center align-middle">
															${product.getThuongHieu().getTenTH()}</td>
														<td class="text-center align-middle">
															<button class="choose-product"
																style="border-radius: 3px; background-color: #20c997;">
																<i class="fas fa-cart-arrow-down"></i>
															</button>
														</td>
														<td class="text-center align-middle d-none product-json">
															${product.toJSON()}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>


					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<h4 class="text-dark font-weight-medium">
									Phiếu xuất mã: <span class="phieuNhapId text-danger">${idHoaDon}</span>,
									Khách hàng: <span class="idNhaCungCap text-danger">${idKhachHang}</span>
									- <span class="tenNhaCungCap text-danger">${tenKhachHang}</span>
								</h4>
								<div class="row mt-4">
									<!-- Begin Tabs -->
									<div class="col-lg-12" style="overflow: auto;">
										<table class="table table-info">
											<thead class="bg-info text-white"
												style="position: sticky; top: 0">
												<tr>
													<th class="text-center align-middle" scope="col">Mã
														VPP</th>
													<th class="text-center align-middle" scope="col">Tên
														VPP</th>
													<th class="text-center align-middle" scope="col">Số
														lượng</th>
													<th class="text-center align-middle" scope="col">Đơn
														giá</th>
													<th class="text-center align-middle" scope="col">Thành
														tiền</th>
													<th class="text-center align-middle" scope="col">Xóa</th>
												</tr>
											</thead>
											<tbody class="choose-table">
												<c:if test="${cacCTHD.size() > 0}">
													<c:forEach items="${cacCTHD}" var="cthd" varStatus="status">
														<tr class="table-choosen">
															<td class="text-center align-middle choosen-id">${cthd.vanPhongPham.getID_VPP()}</td>
															<td class="text-center align-middle">
																<div class="choosen-pd-name">${cthd.vanPhongPham.tenVPP}</div>
															</td>
															<td
																class="text-center align-middle d-flex justify-content-center">
																<input style="max-width: 90px" id="soluong"
																type="number"
																class="form-control choosen-quan ${lockInput}" name="SL"
																value="${cthd.soLuong}" min="1" max="${soLuongTon.get(status.index)}"
																oninput="this.value = Math.abs(this.value)">
															</td>
															<td class="text-center align-middle calculated-price">														
																<fmt:formatNumber value="${cthd.getDonGia()}"
																	type="currency" currencySymbol="" maxFractionDigits="0" />
															</td>
															<td
																class="text-center align-middle calculated-total-price"
																data-set="${cthd.getDonGia() * cthd.soLuong}">
																<fmt:formatNumber
																	value="${cthd.getDonGia() * cthd.soLuong}"
																	type="currency" currencySymbol="" maxFractionDigits="0" />
															</td>
															<td class="text-center align-middle">
																<button class="remove-product btn-danger ${lockBtn}"
																	style="border-radius: 3px;">
																	<i class="fas fa-trash"></i>
																</button>
															</td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<div class="col-lg-12 d-flex justify-content-center"
										style="font-size: 20px">
										<div class="w-100">
											<form:form modelAttribute="hoaDonModel">
												<div class="form-group mt-3">
													<div class="row">
														<div class="col-lg-4">
															Tổng tiền: <span class="total-of-bill"></span>
														</div>
														<div class="col-lg-4 total-payment">
															Tổng tiền cần thanh toán: <span class="total-pay"></span>
														</div>
														<div class="col-lg-4 d-none">
															<label for="tongtien">Tổng tiền</label>
															<form:input path="tongTien"
																class="form-control total-bill" placeholder="Tổng tiền" />
														</div>
														<div class="col-lg-4 d-none">
															<label for="tongtien">id</label>
															<input name="idHoaDon"
																class="form-control idHoaDon" placeholder="" value="${idHoaDon}">
														</div>
														<div class="col-lg-4 d-flex">
															<label for="tienVAT">Tổng VAT</label>
															<form:input path="tienVAT"
																class="form-control vat-pay ${lockInput} only-number"
																placeholder="Thuế VAT" />
														</div>
						
													</div>
													<div class="row">
														<div class="col-lg-4">
															<label for="tienTra">Tiền khách trả</label> 
															<span class="not-enough" style="color: #FF4F70; display: none;
															font-weight: bold">- Số tiền không đủ</span>
															<form:input
																path="tienTra" class="form-control client-payment only-number ${lockInput}"
																placeholder="Nhập tiền khách trả"/>
														</div>
														<div class="col-lg-4">
															Tiền thừa: <span class="client-payment-format"></span>
														</div>
														<div class="col-lg-4">
															<button
																class="btn waves-effect waves-light 
																btn-primary border-0 
																btn-submit-product ${lockBtn}" style="margin-top: 38px; font-size: 18px"
																type="submit">Xác nhận ghi hóa đơn</button>
														</div>
													</div>
												</div>
											</form:form>
										</div>
									</div>
								</div>

								<!-- End Tabs -->
							</div>
						</div>
					</div>

				</div>
			</div>
			<footer class="footer text-center text-muted">
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
<script src="<c:url value='/resources/invoiceDT.js'></c:url>"></script>

<script>
	history.pushState(null, document.title, location.href);
	history.back();
	history.forward();
	window.onpopstate = function() {
		history.go(1);
	};
</script>
</html>