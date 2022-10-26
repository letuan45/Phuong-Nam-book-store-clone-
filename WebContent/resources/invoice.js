$(function() {
	$(document).on('click', '.client-item', function(e) {
		var idChoosen = +($(this).closest('.client-item').attr('data-set'));
		$('.closeModal').trigger("click");
		$('#khachHang').val(idChoosen);
	});

	$('.single-client').on('click', function(e) {
		$('#khachHang').val('');
		$('.closeModal').trigger("click");
	});

	//Search Session
	var clientArr = [];
	var clientEle = $('.khachHangTable').children().toArray();
	if (clientEle.length > 0) {
		clientEle.forEach(item => clientArr.push({
			id: +$(item).find('.idKH').html(),
			ho: $(item).find('.hoKH').html(),
			ten: $(item).find('.tenKH').html(),
			sdt: $(item).find('.sdtKH').html()
		}))
	}

	function renderClient(clientArr) {
		$('.khachHangTable').empty();
		for (var i = 0; i < clientArr.length; i++) {
			$('.khachHangTable').append(`
				<tr class="client-item" data-set="${clientArr[i].id}">
					<th scope="row" class="idKH text-center">${clientArr[i].id}</th>
					<td class="hoKH text-center">${clientArr[i].ho}</td>
					<td class="tenKH text-center">${clientArr[i].ten}</td>
					<td class="sdtKH text-center">${clientArr[i].sdt}</td>
				</tr>
			`)
		}
	}

	//Tim kiem on event
	var idKH = '';
	var hoKH = '';
	var tenKH = '';
	var sdtKH = '';

	//Loại bỏ dấu console.log('Lê Tuấn'.normalize("NFD").replace(/[\u0300-\u036f]/g, ""));

	$('.idClient').keyup(function() {
		idKH = $(this).val().trim().toLowerCase();
		var clientArrRes = clientArr.filter((item) => {
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH == '') {
				return item.id == idKH;
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH));
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(tenKH));
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH != '' && hoKH != '' && tenKH != '' && sdtKH != '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH) && item.sdt.toLowerCase().includes(sdtKH));
			}
			//ho
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH);
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//ten
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH);
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//sdt
			if (idKH == '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return item.sdt.toLowerCase().includes(sdtKH);
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			else return item;
		});
		renderClient(clientArrRes);
	});

	$('.fnClient').keyup(function() {
		hoKH = $(this).val().trim().normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
		var clientArrRes = clientArr.filter((item) => {
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH == '') {
				return item.id == idKH;
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH));
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(tenKH));
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH != '' && hoKH != '' && tenKH != '' && sdtKH != '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH) && item.sdt.toLowerCase().includes(sdtKH));
			}
			//ho
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH);
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//ten
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH);
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//sdt
			if (idKH == '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return item.sdt.toLowerCase().includes(sdtKH);
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			else return item;
		});
		renderClient(clientArrRes);
	});

	$('.lnClient').keyup(function() {
		tenKH = $(this).val().trim().normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase();
		var clientArrRes = clientArr.filter((item) => {
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH == '') {
				return item.id == idKH;
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH));
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(tenKH));
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH != '' && hoKH != '' && tenKH != '' && sdtKH != '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH) && item.sdt.toLowerCase().includes(sdtKH));
			}
			//ho
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH);
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//ten
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH);
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//sdt
			if (idKH == '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return item.sdt.toLowerCase().includes(sdtKH);
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			else return item;
		});
		renderClient(clientArrRes);
	});

	$('.phoneClient').keyup(function() {
		sdtKH = $(this).val().trim().toLowerCase();
		var clientArrRes = clientArr.filter((item) => {
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH == '') {
				return item.id == idKH;
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH));
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(tenKH));
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH != '' && hoKH != '' && tenKH != '' && sdtKH != '') {
				return (item.id == idKH && item.ho.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH) && item.sdt.toLowerCase().includes(sdtKH));
			}
			//ho
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH);
			}
			if (idKH != '' && hoKH != '' && tenKH == '' && sdtKH == '') {
				return (item.id == idKH && item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//ten
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH);
			}
			if (idKH != '' && hoKH == '' && tenKH != '' && sdtKH == '') {
				return (item.id == idKH && item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH != '' && tenKH != '' && sdtKH == '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			//sdt
			if (idKH == '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return item.sdt.toLowerCase().includes(sdtKH);
			}
			if (idKH != '' && hoKH == '' && tenKH == '' && sdtKH != '') {
				return (item.id == idKH && item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH != '' && tenKH == '' && sdtKH != '') {
				return (item.ho.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(hoKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			if (idKH == '' && hoKH == '' && tenKH != '' && sdtKH != '') {
				return (item.ten.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLowerCase().includes(tenKH)
					&& item.sdt.toLowerCase().includes(sdtKH));
			}
			else return item;
		});
		renderClient(clientArrRes);
	});

})