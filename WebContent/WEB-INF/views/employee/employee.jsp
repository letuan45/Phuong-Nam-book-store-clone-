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
<title>Quản lí nhân viên</title>
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
									<!-- Column -->
									<form:form modelAttribute="nhanVien"
										action="employee/actions.htm" method="post"
										class="table-responsive" style="overflow-x: unset">
										<h4 class="mb-4 text-dark font-weight-medium">
											<div class="btn btn-gray btn-circle mb-2 btn-item"
												style="background-color: #1c2d41;">
												<i class="far fa-user-circle text-light"
													style="font-size: 20px"></i>
											</div>
											Thông tin nhân viên
										</h4>
										<div class="mt-4">
											<div class="row">
												<div class="col-lg-3 col-md-6 col-sm-6">
													<label for="cmnd" class="m-0">CMND</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="cmnd" class="error-message" />
													</h6>
													<form:input class="form-control only-number ${disableInput}" path="cmnd"
														placeholder="Nhập CMND" maxlength="13" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6"">
													<form:input class="form-control d-none" path="ID_NV"
														placeholder="ID_NV" />
													<label for="Ho" class="m-0">Họ</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="Ho" class="error-message" />
													</h6>
													<form:input class="form-control ${disableInput}" path="Ho"
														placeholder="Nhập họ" maxlength="30" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6"">
													<label for="Ten" class="m-0">Tên</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="Ten" class="error-message" />
													</h6>
													<form:input class="form-control ${disableInput}" path="Ten"
														placeholder="Nhập tên" maxlength="20" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6"">
													<label for="sdt" class="m-0">Số điện thoại</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="sdt" class="error-message" />
													</h6>
													<form:input class="form-control only-number ${disableInput}" path="sdt"
														placeholder="Nhập SĐT" maxlength="10" />
												</div>
											</div>

											<div class="row mt-3">
												<div class="col-lg-3 col-md-6 col-sm-6"">
													<label for="email" class="m-0">Email</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="email" class="error-message" />
													</h6>
													<form:input class="form-control ${disableInput}" path="email" 
														placeholder="Nhập Email" maxlength="30" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6"">
													<label for="ngaySinh" class="m-0">Ngày sinh</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="ngaySinh"
															class="error-message date-err" />
													</h6>
													<form:input type="date" class="form-control ${disableInput}"
														path="ngaySinh" placeholder="Nhập ngày sinh dd/mm/yyyy" />
												</div>

												<div class="col-lg-6">
													<label for="diaChi" class="m-0">Địa chỉ</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="diaChi" class="error-message" />
													</h6>
													<form:input class="form-control ${disableInput}" path="diaChi"
														placeholder="Nhập địa chỉ" />
												</div>
											</div>

											<div class="row mt-3 justify-content-center">
												<div class="col-lg-3 col-md-6 col-sm-6"">
													<label for="chucVu" class="m-0">Chức vụ</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="chucVu" class="error-message" />
													</h6>
													<form:input class="form-control ${disableInput}"
														path="chucVu" placeholder="Nhập chức vụ" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6"">
													<label for="luong" class="m-0">Lương</label>
													<h6 class="text-danger font-weight-medium">
														<form:errors path="luong" class="error-message" />
													</h6>
													<form:input
														class="form-control only-number ${disableInput}"
														path="luong" placeholder="Nhập lương (VND)" />
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6" d-flex align-items-center">
													<p class="m-0">Giới tính</p>
													<div class="form-check form-check-inline">
														<h6 class="text-danger font-weight-medium">
															<form:errors path="gioiTinh" class="error-message" />
														</h6>
														<form:radiobutton class="form-check-input ml-3 ${disableInput}"
															path="gioiTinh" id="inlineRadioNam" value="Nam" />
														<label class="form-check-label" for="inlineRadioNam">Nam</label>
														<form:radiobutton class="form-check-input ml-3 ${disableInput}"
															path="gioiTinh" id="inlineRadioNu" value="Nữ" />
														<label class="form-check-label" for="inlineRadioNu">Nữ</label>
													</div>
												</div>

												<div class="col-lg-3 col-md-6 col-sm-6" d-flex align-items-center">
													<p class="m-0">Nghỉ việc ?</p>
													<div class="form-check form-check-inline">
														<form:checkbox class="form-check-input ml-3 ${disabled}"
															path="daNghi" id="inlineRadio" value="true" />
														<label class="form-check-label" for="inlineRadio">Nghỉ
															việc</label>
													</div>
												</div>

												<div class="col-lg-3 col-sm-6 d-none">
													<p class="m-0">DGG</p>
													<div class="form-check form-check-inline">
														<form:checkbox class="form-check-input ml-3"
															path="daGiaoDich" id="inlineRadio" value="true" />
														<label class="form-check-label" for="inlineRadio">Đã
															giao dịch</label>
													</div>
												</div>
											</div>

											<div style="margin-top: 20px">
												<div
													class="col row align-items-center justify-content-center">
													<div class="row d-flex justify-content-center"
														style="height: 50px">
														<button type="submit" name="${btnStatus}"
															class="btn waves-effect waves-light btn-success">${btnContent}</button>
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
												</span> <span class="ml-2 mr-"> QL tài khoản: <i
													class="fas fa-user-circle" style="color: #5f76e8"></i>
												</span>
											</div>
										</div>

										<!--Search form-->
										<form action="employee/index.htm" class="mb-3">
											<h5 class="text-dark font-weight-medium">Tìm kiếm</h5>
											<div class="row w-100">
												<div class="col-1">
													<input type="text" class="form-control only-number"
														name="idCustomerSearch" placeholder="ID">
												</div>
												<div class="col-3">
													<input type="text" class="form-control"
														name="inforCustomerSearch"
														placeholder="Nhập họ, tên, sdt,..">
												</div>
												<div class="col-3">
													<select class="custom-select" name="gioiTinhSearch">
														<option selected value="">Giới tính</option>
														<option value="0">Nam</option>
														<option value="1">Nữ</option>
													</select>
												</div>
												<div class="col-3">
													<select class="custom-select" name="daMuaHangSearch">
														<option selected value="">Đã giao dịch ?</option>
														<option value="0">Chưa giao dịch</option>
														<option value="1">Đã giao dịch</option>
													</select>
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
										<c:url value="/employee/index.htm" var="pagedLink">
											<c:param name="idCustomer" value="${idInput}" />
											<c:param name="inforCustomer" value="${inforInput}" />
											<c:param name="gioiTinh" value="${gioiTinhInput}" />
											<c:param name="daMuaHang" value="${daGiaoDichInput}" />
											<c:param name="p" value="~" />
										</c:url>

										<tg:paging pagedListHolder="${pagedListHolder}"
											pagedLink="${pagedLink}" pageCount="${totalPage}"
											pageAt="${pageAt}" />

										<table class="table table-bordered">
											<thead class="bg-primary text-white">
												<tr>
													<th class="text-center align-middle" scope="col">Mã NV</th>
													<th class="text-center align-middle" scope="col">Họ</th>
													<th class="text-center align-middle" scope="col">Tên</th>
													<th class="text-center align-middle" scope="col">Số
														điện thoại</th>
													<th class="text-center align-middle" scope="col">Ngày
														sinh</th>
													<th class="text-center align-middle" scope="col">Giới
														tính</th>
													<th class="text-center align-middle" scope="col">Địa
														chỉ</th>
													<th class="text-center align-middle" scope="col">Actions
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${pagedListHolder.pageList}"
													var="employee">
													<tr>
														<th scope="row" class="text-center align-middle">${employee.getID_NV()}</th>
														<td class="text-center align-middle text-1-line-140">
															<div class="text-1-line-140">${employee.getHo()}</div>
														</td>
														<td class="text-center align-middle">${employee.getTen()}</td>
														<td class="text-center align-middle">${employee.getSdt()}</td>
														<td class="text-center align-middle">
															<div class="text-1-line-100">${employee.getNgaySinh()}</div>
														</td>
														<td class="text-center align-middle">
															<div class="text-1-line-80">${employee.getGioiTinh()}</div>
														</td>
														<td class="text-center align-middle">
															<div class="text-1-line-140">${employee.getDiaChi()}</div>
														</td>
														<td class="text-center align-middle"><a
															href="employee/update/${employee.getID_NV()}.htm"
															class="edit-product"> <i class="fas fa-pencil-alt"
																style="color: #01caf1"></i>
														</a> <a href="employee/account/${employee.getID_NV()}.htm"
															class="edit-product ml-2"> <i
																class="fas fa-user-circle" style="color: #5f76e8"></i>
														</a> <c:if test="${!employee.getDaGiaoDich()}">
																<a class="edit-product ml-2" style="cursor: pointer"
																	onclick="showWarningModal('employee/delete/${employee.getID_NV()}.htm', 'Nhân viên ${employee.getID_NV()}')">
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
</body>

<!-- Scripts -->
<%@ include file="/WEB-INF/views/include/scripts.jsp"%>
<!--My Custom Jquery Main-->
<script>
	$(function() {
		$('.disable-input').attr("readonly", true);
		$('.disabledInputQuit').attr("disabled", true);
		$('.disabledInput').attr("disabled", true);
		$('.disable-button').attr("disabled", true);
		
		$('.disable-input').click(function(){
	         return false;
		 })

		//xuat hien modal va hien thi error message
		if (+$('.errors-count').html().trim() > 0) {
			if ($('.date-err').html().trim()) {
				$('.date-err').html('Hãy chọn một ngày');
			}
		}
	})
</script>
</html>