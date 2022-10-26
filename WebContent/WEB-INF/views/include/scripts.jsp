<!-- All Jquery -->
<!-- ============================================================== -->
<script
	src="<c:url value='/resources/assets/libs/jquery/dist/jquery.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/libs/popper.js/dist/umd/popper.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/libs/bootstrap/dist/js/bootstrap.min.js'></c:url>"></script>
<!-- apps -->
<!-- apps -->
<script
	src="<c:url value='/resources/dist/js/app-style-switcher.js'></c:url>"></script>
<script src="<c:url value='/resources/dist/js/feather.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js'></c:url>"></script>
<script src="<c:url value='/resources/dist/js/sidebarmenu.js'></c:url>"></script>
<!--Custom JavaScript -->
<script src="<c:url value='/resources/dist/js/custom.min.js'></c:url>"></script>
<!--This page JavaScript -->
<script
	src="<c:url value='/resources/assets/extra-libs/c3/d3.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/resources/assets/extra-libs/c3/c3.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/libs/chartist/dist/chartist.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/extra-libs/jvector/jquery-jvectormap-2.0.2.min.js'></c:url>"></script>
<script
	src="<c:url value='/resources/assets/extra-libs/jvector/jquery-jvectormap-world-mill-en.js'></c:url>"></script>
<!--My Custom Jquery-->
<!--Only number-->
<script src="<c:url value='/resources/OnlyNumber.js'></c:url>"></script>
<script charset="utf-8">
	function showWarningModal(link, name) {
		$('#item-name').text(name);
		$('#link-to').attr('href', link);
		$('#warningModal').modal('show');
	}

	$(function() {
		$('.sidebar-link').click(function(e) {
			$('.sidebar-item').removeClass('selected');
			$('.sidebar-link').removeClass('active');
			$('.alert').hide();

			const parent = $(this).parent('.sidebar-item');
			parent.addClass('selected');
			parent.children().addClass('active');
		});

		var sidebarContent = $(".xacdinh-sidebar").html();
		var sidebarElement = $("." + sidebarContent + "");
		if (sidebarElement) {
			sidebarElement.addClass('selected');
			sidebarElement.children().addClass('active');
		}

		setTimeout(function() {
			$('.alert').fadeOut('slow');
		}, 5000);
	});
</script>