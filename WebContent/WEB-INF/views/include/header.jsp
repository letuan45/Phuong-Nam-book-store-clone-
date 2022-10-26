<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<header class="topbar " data-navbarbg="skin6">
	<nav class="navbar top-navbar navbar-expand-md">
		<div class="navbar-header" data-logobg="skin6">
			<!-- This is for the sidebar toggle which is visible on mobile only -->
			<a class="nav-toggler waves-effect waves-light d-block d-md-none"
				href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
			<!-- ============================================================== -->
			<!-- Logo -->
			<!-- ============================================================== -->
			<div class="navbar-brand">
				<!-- Logo icon -->
				<a href="/NhaSachPN/mainPage.htm"> <span class="logo-text"> <span
						class="logo-text"> <!-- dark Logo text --> 
						<img
							src="<c:url value='/resources/assets/images/logo-vector.png'></c:url>" alt="homepage"
							style="width: 200px; height: auto;" />
					</span>
				</span>
				</a>
			</div>
			<!-- ============================================================== -->
			<!-- End Logo -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- Toggle which is visible on mobile only -->
			<!-- ============================================================== -->
			<a class="topbartoggler d-block d-md-none waves-effect waves-light"
				href="javascript:void(0)" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation"><i class="ti-more"></i></a>
		</div>
		<!-- ============================================================== -->
		<!-- End Logo -->
		<!-- ============================================================== -->
		<div class="navbar-collapse collapse" id="navbarSupportedContent"
			style="background-color: #e3f2fd;">
			<!-- ============================================================== -->
			<!-- toggle and nav items -->
			<!-- ============================================================== -->
			<div class="navbar-nav float-left mr-auto ml-3 pl-1"
				style="font-size: 20px; font-weight: bold">Ngày: ${dateNow}</div>
			<!-- ============================================================== -->
			<!-- Right side toggle and nav items -->
			<!-- ============================================================== -->
			<ul class="navbar-nav float-right">
				<!-- ============================================================== -->
				<!--Staff-->
				<c:choose>
					<c:when test="${tkNhanVien == 1}">
						<li class="nav-item d-flex align-items-center">
							<div class="p-2 bg-primary text-white"
								style="border-radius: 20px">
								<i class="fas fa-user text-dark"></i> Tài khoản Nhân viên
							</div>
						</li>
					</c:when>
					<c:otherwise>
						<!--Manager-->
                        <li class="nav-item d-flex align-items-center">
                            <div class="p-2 bg-cyan text-white" 
                            style="border-radius: 20px">
                                <i class="fas fa-briefcase text-dark"></i>
                                Tài khoản Quản lí
                            </div>
                        </li>
					</c:otherwise>
				</c:choose>

				<!-- ============================================================== -->
				<!-- User profile and search -->
				<!-- ============================================================== -->
				<li class="nav-item dropdown">
				<a
					class="nav-link dropdown-toggle" href="javascript:void(0)"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<img src='<c:url value="/resources/images/user-img.png"></c:url>' alt="user" class="rounded-circle" width="40">
						<span class="ml-2 d-none d-lg-inline-block"><span>Xin
								chào, </span> <span class="text-dark">${user.getTen()}</span> <i
							data-feather="chevron-down" class="svg-icon"></i></span>
				</a>
					<div
						class="dropdown-menu dropdown-menu-right user-dd animated flipInY">
						<a class="dropdown-item" href="javascript:void(0)"><i
							data-feather="credit-card" class="svg-icon mr-2 ml-1"></i> Thông
							báo</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="/NhaSachPN/useraccount/index.htm"><i
							data-feather="settings" class="svg-icon mr-2 ml-1"></i> Thay đổi mật khẩu</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/logout"><i
							data-feather="power" class="svg-icon mr-2 ml-1"></i> Đăng xuất</a>
						<div class="dropdown-divider"></div>
						<div class="pl-4 p-3">
							<a href="javascript:void(0)" class="btn btn-sm btn-info">Xem
								Profile</a>
						</div>
					</div></li>
			</ul>
		</div>
	</nav>
</header>