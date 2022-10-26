$(function() {
		$(".disable-button").attr("disabled", true);
		if($(".btnAdd").html()) {
			$(".tinhTrangCheck").attr("disabled", true);
		}
		
		if($(".btnEdit").html()) {
			//$("#ID_VPP").attr("disabled", true);
			$("#ID_VPP").unbind("click");
		}
		
		var sidebarContent = $(".xacdinh-sidebar").html();
		var sidebarElement = $("." + sidebarContent + "");
		if(sidebarElement) {
			sidebarElement.addClass('selected');
			sidebarElement.children().addClass('active');
		}
});