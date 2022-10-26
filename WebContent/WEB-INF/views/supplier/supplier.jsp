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
<title>Quản lí nhà cung cấp</title>
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
									<!-- Column -->
									<form:form modelAttribute="supplier"
										action="supplier/addSupplier.htm" method="post"
										class="table-responsive" style="overflow-x: unset">
										<h4 class="mb-4 text-dark font-weight-medium">
											<div class="btn btn-warning btn-circle mb-2 btn-item">
												<i class="far fa-clipboard text-light" style="font-size: 20px"></i>
											</div>
											Thông tin nhà cung cấp
										</h4>
										<div class="mt-4">
											<div class="row">
												<div class="col-6">
													<label for="ID_NCC">Mã nhà cung cấp</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="ID_NCC" class="error-message" />
													</h6>
													<form:input class="form-control valid-char" path="ID_NCC"
														placeholder="Mã nhà cung cấp" />
												</div>

												<div class="col">
													<label for="SDT">SDT</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="SDT" class="error-message" />
													</h6>
													<form:input class="form-control only-number" path="SDT"
														placeholder="Nhập SDT" maxlength="10"/>
												</div>
											</div>

											<div class="row">
												<div class="col-6 mt-2">
													<label for="tenNCC" class="m-0">Tên nhà cung cấp</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="tenNCC" class="error-message" />
													</h6>
													<form:input class="form-control valid-char" path="tenNCC"
														placeholder="Nhập tên NCC"/>
												</div>
												<div class="col-6 mt-2">
													<label for="email" class="m-0">Địa chỉ email</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="email" class="error-message" />
													</h6>
													<form:input class="form-control" path="email"
														placeholder="Nhập email" />
												</div>
											</div>

											<div class="row mt-3 d-flex justify-content-center">
												<div class="col-lg-6">
													<label for="diaChi" class="m-0">Địa chỉ</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="diaChi" class="error-message" />
													</h6>
													<form:input class="form-control" path="diaChi"
														placeholder="Nhập địa chỉ" />
												</div>
											</div>
											<div style="margin-top: 20px">
												<div
													class="col row align-items-center justify-content-center">
													<div class="row d-flex justify-content-center"
														style="height: 50px">
														<button type="submit" name="btnAddSupplier"
															class="btn waves-effect waves-light btn-success add-KH">Thêm
															nhà cung cấp</button>
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
										<form action="supplier/index.htm" class="mb-3">
											<h5 class="text-dark font-weight-medium">Tìm kiếm</h5>
											<div class="w-100 d-flex justify-content-between	">
												<div class="col-6 d-flex">
													<div class="col-2">
														<input type="text" class="form-control" name="idInput"
															placeholder="ID">
													</div>
													<input type="text" class="form-control" name="inforInput"
														placeholder="Nhập email, tên, sdt, địa chỉ,..">
												</div>
												<div class="col-2">
													<button name="btnSearch"
														class="btn waves-effect waves-light btn-primary bg-cyan border-0">Tìm
														kiếm</button>
												</div>
											</div>
										</form>

										<tg:warningModal />

										<!-- pagination -->
										<jsp:useBean id="pagedListHolder" scope="request"
											type="org.springframework.beans.support.PagedListHolder" />
										<c:url value="/supplier/index.htm" var="pagedLink">
											<c:param name="idInput" value="${idInput}" />
											<c:param name="inforInput" value="${inforInput}" />
											<c:param name="p" value="~" />
										</c:url>

										<tg:paging pagedListHolder="${pagedListHolder}"
											pagedLink="${pagedLink}" pageCount="${totalPage}"
											pageAt="${pageAt}" />

										<table class="table table-bordered">
											<thead class="bg-primary text-white">
												<tr>
													<th class="text-center align-middle" scope="col">Mã
														NCC</th>
													<th class="text-center align-middle" scope="col">Tên</th>
													<th class="text-center align-middle" scope="col">Số
														điện thoại</th>
													<th class="text-center align-middle" scope="col">Email</th>
													<th class="text-center align-middle" scope="col">Địa
														chỉ</th>
													<th class="text-center align-middle" scope="col">Action
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${pagedListHolder.pageList}"
													var="supplier">
													<tr>
														<th scope="row" class="text-center align-middle">${supplier.getID_NCC()}</th>
														<td class="text-center align-middle text-1-line-140">
															<div class="text-1-line-140">${supplier.getTenNCC()}</div>
														</td>
														<td class="text-center align-middle">${supplier.getSDT()}</td>
														<td class="text-center align-middle">${supplier.getEmail()}</td>
														<td class="text-center align-middle">
															<div class="text-1-line-140">${supplier.getDiaChi()}</div>
														</td>
														<td class="text-center align-middle"><a
															href="supplier/update/${supplier.getID_NCC()}.htm"
															class="edit-product"> <i class="fas fa-pencil-alt"
																style="color: #01caf1"></i>
														</a> <c:if test="${supplier.getDaGiaoDich() == false}">
																<a style="cursor: pointer"
																	onclick="showWarningModal('supplier/delete/${supplier.getID_NCC()}.htm', '${supplier.getTenNCC()}')"
																	class="edit-product ml-3 btn"> <i
																	class="fas fa-trash" style="color: #ff4f70"></i>
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

					<div></div>
					<footer class="footer text-center text-muted w-100">
						All Rights Reserved by Group 23 D19CQCN01-N <span
							style="display: none" class="xacdinh-sidebar">${activeMapping}</span>
						<span class="d-none errors-count">${errorsCount}</span>
					</footer>
					<!-- ============================================================== -->
					<!-- End footer -->
				</div>
			</div>
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
	})
</script>
</html>