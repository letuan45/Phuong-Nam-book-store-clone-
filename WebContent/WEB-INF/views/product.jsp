<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Quản lí Văn Phòng Phẩm</title>
<base href="${pageContext.servletContext.contextPath}/">

<!-- Links -->
<%@ include file="/WEB-INF/views/include/links.jsp"%>

</head>
<body>
	<!-- 	<div class="preloader">
		<div class="lds-ripple">
			<div class="lds-pos"></div>
			<div class="lds-pos"></div>
	</div> -->

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
									<div class="table-responsive" style="overflow-x: unset">
										<h4 class="mb-4 text-dark font-weight-medium">
											<div class="btn btn-info btn-circle mb-2 btn-item">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-box feather-icon">
                                				<path d="M12.89 1.45l8 4A2 2 0 0 1 22 7.24v9.53a2 2 0 0 1-1.11 1.79l-8 4a2 2 0 0 1-1.79 0l-8-4a2 2 0 0 1-1.1-1.8V7.24a2 2 0 0 1 1.11-1.79l8-4a2 2 0 0 1 1.78 0z"></path>
                                				<polyline points="2.32 6.16 12 11 21.68 6.16"></polyline>
                                				<line x1="12" y1="22.76" x2="12" y2="11"></line></svg>
                                            </div>
											Thông tin
											văn phòng phẩm</h4>
										<form:form class="mt-4" modelAttribute="VPP"
											action="product/actions.htm" enctype="multipart/form-data">
											<div class="row">
												<div class="col-lg-3 col-md-6 col-sm-6">
													<form:input class="form-control d-none" path="ID_VPP"
														placeholder="ID_VPP" />
													<h6
														class="text-danger position-absolute font-weight-medium"
														style="top: -12px;">
														<form:errors path="tenVPP" class="error-message" />
													</h6>
													<label for="tenvpp">Tên Văn phòng phẩm</label>
													<form:input class="form-control" path="tenVPP"
														placeholder="Nhập tên" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6">
													<label for="hinhanh">Hình ảnh</label>
													<div class="input-group">
														<div class="custom-file">
															<input type="file" class="custom-file-input" id="hinhanh"
																name="file"> <label class="custom-file-label"
																for="inputGroupFile01">Upload</label>
														</div>
													</div>
												</div>
												<div class="col-lg-3 col-md-6 col-sm-6">
													<label for="loai">Loại sản phẩm</label>
													<form:select path="loai.ID_Loai"
														class="custom-select mr-sm-2" items="${loaisp}"
														itemValue="ID_Loai" itemLabel="fullDisplay">
													</form:select>
												</div>
												<div class="col-lg-3 col-md-6 col-sm-6">
													<label for="thuonghieu">Thương hiệu</label>
													<form:select path="thuongHieu.ID_TH"
														class="custom-select mr-sm-2" items="${thuonghieu}"
														itemValue="ID_TH" itemLabel="fullDisplay">
													</form:select>
												</div>
											</div>

											<div class="row mt-4 justify-content-center">
												<div class="col-lg-3 col-md-6 col-sm-6">
													<label for="tinhtrang">Tình trạng (Ngừng kinh doanh
														?)</label>
													<div class="form-check">
														<form:checkbox class="form-check-input tinhTrangCheck"
															name="exampleRadios" id="exampleRadios1" value="3"
															path="tinhTrang" />
														<label class="form-check-label" for="exampleRadios1">
															Ngừng kinh doanh </label>
													</div>
												</div>
												<div class="col-lg-3 col-md-6 col-sm-6">
													<h6
														class="text-danger position-absolute font-weight-medium"
														style="top: -12px;">
														<form:errors path="giaBan" class="error-message" />
													</h6>
													<label for="giaban">Giá bán</label>
													<form:input class="form-control only-number" path="giaBan"
														placeholder="Nhập giá bán" />
												</div>
												<div class="col-lg-3 col-md-6 col-sm-6">
													<h6
														class="text-danger position-absolute font-weight-medium"
														style="top: -12px;">
														<form:errors path="donViTinh" class="error-message" />
													</h6>
													<label for="donViTinh">Đơn vị tính</label>
													<form:input class="form-control mr-4" path="donViTinh"
														placeholder="Nhập đơn vị tính"
														style="display: inline-block;" />
												</div>
											</div>
											<div style="margin-top: 20px">
												<div
													class="col row align-items-center justify-content-center">
													<button type="button" class="btn btn-primary mr-4"
														data-toggle="modal" data-target="#exampleModal">
														<i class="fas fa-plus"></i> Thêm loại văn phòng phẩm
													</button>
													<div class="row d-flex justify-content-center"
														style="height: 50px">
														<c:choose>
															<c:when
																test="${loaisp.size() > 0 && thuonghieu.size() > 0}">
																<button name="${btnStatus}"
																	class="btn ${btnStatus} waves-effect ${daThem} waves-light btn-success mb-1 ${disableButton}">${btnContent}</button>
															</c:when>
															<c:otherwise>
																<button name="${btnStatus}"
																	class="btn ${btnStatus} waves-effect ${daThem} waves-light btn-success mb-1 ${disableButton}"
																	disabled=true>${btnContent}</button>
															</c:otherwise>
														</c:choose>
													</div>
													<button type="button" class="btn btn-primary"
														style="margin-left: 25px;" data-toggle="modal"
														data-target="#exampleModal2">
														<i class="fas fa-plus"></i> Thêm TH văn phòng phẩm
													</button>
												</div>
											</div>
										</form:form>
										<div class="row align-items-center justify-content-center">
											<div class="modal fade" id="exampleModal" tabindex="-1"
												role="dialog" aria-labelledby="exampleModalLabel"
												aria-hidden="true">
												<div class="modal-dialog" role="document"
													style="max-width: 650px">
													<div class="modal-content" style="border-radius: 6px">
														<div class="modal-header">
															<h4 class="modal-title" id="exampleModalLabel">Thêm
																loại Văn Phòng Phẩm</h4>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<form:form modelAttribute="loai"
																action="product/addType.htm" method="POST">
																<div class="form-group">
																	<label for="tenLoai">Tên loại sản phẩm</label>
																	<form:input type="text" class="form-control"
																		path="tenLoai"
																		placeholder="Nhập tên loại sản phẩm không trùng" />
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
											<div class="modal fade" id="exampleModal2" tabindex="-1"
												role="dialog" aria-labelledby="exampleModalLabel2"
												aria-hidden="true">
												<div class="modal-dialog" role="document"
													style="max-width: 650px">
													<div class="modal-content" style="border-radius: 6px">
														<div class="modal-header">
															<h4 class="modal-title" id="exampleModalLabel2">Thêm
																thương hiệu mới</h4>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<form:form modelAttribute="thuongHieu"
																action="product/addBrand.htm" method="POST">
																<div class="form-group">
																	<label for="tenTH">Tên thương hiệu</label>
																	<form:input type="text" class="form-control"
																		path="tenTH"
																		placeholder="Nhập tên thương hiệu không trùng" />
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
									<!-- Column -->
								</div>
							</div>
						</div>
					</div>
					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<div class="row">
									<div class="table-responsive">
										<!--title-->
										<div class="d-flex justify-content-between">
											<h4 class="mb-4 text-dark font-weight-medium">Thống kê</h4>
											<div class="d-flex">
												<span> Chỉnh sửa: <i class="fas fa-pencil-alt"
													style="color: #01caf1"></i>
												</span> <span class="ml-2 mr-"> Xóa: <i class="fas fa-trash"
													style="color: #ff4f70"></i>
												</span>

											</div>
										</div>


										<!--Search form-->
										<form action="product/index.htm" class="mb-3" method="get">
											<h5 class="text-dark font-weight-medium">Tìm kiếm</h5>
											<div class="row w-100">
												<div class="col-1">
													<input type="text" class="form-control only-number"
														name="idVPP" placeholder="ID">
												</div>
												<div class="col-3">
													<input type="text" class="form-control" name="tenVPP"
														placeholder="Nhập tên VPP">
												</div>
												<div class="col-3">
													<select class="custom-select" name="loai">
														<option selected value="">Loại VPP ...</option>
														<c:forEach items="${loaisp}" var="loai">
															<option value="${loai.getID_Loai()}">${loai.getTenLoai()}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-3">
													<select class="custom-select" name="tinhtrang">
														<option selected value="">Tình trạng...</option>
														<c:forEach items="${tinhtrang}" var="tinhtrang">
															<option value="${tinhtrang.maTT}">${tinhtrang.getTinhTrang()}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-2">
													<button name="btnSearch"
														class="btn waves-effect waves-light btn-primary bg-cyan border-0">Xác
														nhận</button>
												</div>
											</div>

										</form>

										<tg:warningModal />

										<!-- order by so luong and pagination -->
										<jsp:useBean id="pagedListHolder" scope="request"
											type="org.springframework.beans.support.PagedListHolder" />
										<c:url value="/product/index.htm" var="pagedLink">
											<c:param name="idVPP" value="${idVPPInput}" />
											<c:param name="tenVPP" value="${tenVPPInput}" />
											<c:param name="loaiID" value="${loaiInput}" />
											<c:param name="tinhtrangID" value="${tinhtrangInput}" />
											<c:param name="sortByQuantity" value="${sortByQuantity}" />
											<c:param name="p" value="~" />
										</c:url>
										<c:choose>
											<c:when test="${sortByQuantity == 0}">
												<c:set var="string2"
													value="${fn:replace(pagedLink, 'sortByQuantity=0&p=~', 'sortByQuantity=1&p=0')}" />
											</c:when>
											<c:otherwise>
												<c:set var="string2"
													value="${fn:replace(pagedLink, 'sortByQuantity=1&p=~', 'sortByQuantity=0&p=0')}" />
											</c:otherwise>
										</c:choose>
										<div class="d-flex justify-content-between mt-5">
											<div>
												<span>Sắp xếp theo: </span>
												<c:choose>
													<c:when test="${sortByQuantity == 0}">
														<a type="button" id="btn-order-quanity"
															class="btn-outline-warning mr-2 p-2" href="${string2}">Số
															lượng</a>
													</c:when>
													<c:otherwise>
														<a type="button" id="btn-order-quanity"
															class="btn-warning mr-2 p-2" href="${string2}">Số
															lượng</a>
													</c:otherwise>
												</c:choose>
											</div>
											<tg:paging pagedListHolder="${pagedListHolder}"
												pagedLink="${pagedLink}" pageCount="${totalPage}"
												pageAt="${pageAt}" />
										</div>
										<table class="table table-bordered">
											<thead class="bg-primary text-white">
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
													<th class="text-center align-middle" scope="col">Nhà
														cung cấp</th>
													<th class="text-center align-middle" scope="col">Thương
														hiệu</th>
													<th class="text-center align-middle" scope="col">Tình
														trạng</th>
													<th class="text-center align-middle" scope="col">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${pagedListHolder.pageList}" var="product">
													<tr>
														<th scope="row" class="text-center align-middle">${product.getID_VPP()}</th>
														<td class="text-center align-middle text-1-line-140">
															<div class="text-1-line-140">${product.getTenVPP()}</div>
														</td>
														<td class="text-center align-middle"><img
															src="${product.getHinhAnh()}" alt="ảnh sản phẩm"
															class="rounded mx-auto"
															style="max-width: 100px; max-height: 100px"></td>
														<td class="text-center align-middle">${product.getSoLuong()}</td>
														<td class="text-center align-middle">
															<fmt:formatNumber
																value="${product.getGiaBan()}" type="currency"
																currencySymbol="" maxFractionDigits="0" />
														</td>
														<td class="text-center align-middle">
															<div class="text-1-line-80">${product.getLoai().getTenLoai()}</div>
														</td>
														<td class="text-center align-middle">
															<div class="text-1-line-140">
																<c:choose>
																	<c:when test="${product.getNhaCungCap() != null}">
    																	${product.getNhaCungCap().getTenNCC()}
  																	</c:when>
																	<c:otherwise>
    																	Chưa có
  																	</c:otherwise>
																</c:choose>
															</div>
														</td>
														<td class="text-center align-middle">
															<div class="text-1-line-100">${product.getThuongHieu().getTenTH()}</div>
														</td>
														<td class="text-center align-middle"><c:choose>
																<c:when test="${product.getTinhTrang() == 1}">
    																Còn hàng
  																</c:when>
																<c:when test="${product.getTinhTrang() == 2}">
    																Hết hàng
  																</c:when>
																<c:otherwise>
    																Ngừng kinh doanh
  																</c:otherwise>
															</c:choose></td>
														<td class="text-center align-middle"><a
															href="product/index/${product.getID_VPP()}.htm?linkEdit"
															"
																class="edit-product"> <i
																class="fas fa-pencil-alt" style="color: #01caf1"></i>
														</a> <c:if test="${!product.getDaGiaoDich()}">
																<a class="edit-product ml-3" type="button"
																	onclick="showWarningModal('product/index/${product.getID_VPP()}.htm?linkDelete', '${product.getTenVPP()}')">
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
				</div>
				<footer class="footer text-center text-muted">
					All Rights Reserved by Group 23 D19CQCN01-N <span
						style="display: none" class="xacdinh-sidebar">${activeMapping}</span>
				</footer>
				<!-- ============================================================== -->
				<!-- End footer -->
			</div>
		</div>
	</div>
</body>

<!-- Scripts -->
<%@ include file="/WEB-INF/views/include/scripts.jsp"%>
<!--My Custom Jquery Main-->
<script src="<c:url value='/resources/product.js'></c:url>"></script>

</html>