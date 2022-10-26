$(function() {
	var sidebarContent = $(".xacdinh-sidebar").html();
	var sidebarElement = $("." + sidebarContent + "");
	if (sidebarElement) {
		sidebarElement.addClass('selected');
		sidebarElement.children().addClass('active');
	}

	$('.lockInput').attr('readonly', true);
	$('.lockBtn').attr('disabled', true);

	var productArr = [];
	const formatter = new Intl.NumberFormat('vi-VN', {
		style: 'currency',
		currency: 'VND',
		minimumFractionDigits: 0
	})

	function setUpArr() {
		var productItems = $('.choose-table').children();
		if (productItems.length > 0) {
			$('.vat-pay').attr('disabled', false);
			for (var i = 0; i < productItems.length; i++) {
				productItems[i] = $(productItems[i]);
				var productId = +productItems[i].find('.choosen-id').html().trim();
				var productName = productItems[i].find('.choosen-pd-name').html().trim();
				var productPrice = +productItems[i].find('.calculated-price')
					.html().trim().replaceAll('.', '');
				var quantiy = +productItems[i].find('.choosen-quan').val();
				var maxQuantiy = +productItems[i].find('.choosen-quan').attr("max");

				productArr.push({
					id: productId,
					name: productName,
					price: productPrice,
					quantiy: quantiy,
					maxQuan: maxQuantiy
				});
			}

			//Set up cac du lieu ban dau
			var payClient = +$('.pay-client').val();
			var sumTotalEle = $('.calculated-total-price').toArray();
			var sumTotal = 0;
			var vatPay = +$('.vat-pay').val();
			sumTotalEle.forEach((item) => {
				var itemPrice = +$(item).html().replaceAll('.', '');
				sumTotal += itemPrice;
			});
			$('.total-pay').html(formatter.format(`${sumTotal + vatPay}`));
			$('.total-of-bill').html(formatter.format(`${sumTotal}`));
			$('.total-payment').attr("data-set", sumTotal + vatPay);
			var totalPay = +$('.total-payment').attr("data-set");

			//Set up dinh dang value input
			$('.total-bill').val(+$('.total-bill').val());
			$('.pay-client').val(+$('.pay-client').val());
			$('.vat-pay').val(+$('.vat-pay').val());
			var oweMoney = (+$('.client-payment').val()) - $('.total-payment').attr('data-set');
			$('.client-payment-format').html(formatter.format(`${oweMoney}`));
		}
	}

	setUpArr();
	disableVAT(productArr);
	$('.client-payment').val(+$('.client-payment').val());
	if ($('.client-payment').val() != 0) {
		var oweMoney = (+$('.client-payment').val()) - $('.total-payment').attr('data-set');
		$('.client-payment-format').html(formatter.format(`${oweMoney}`));
	}

	function containsObject(list, id) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].id == id) {
				return true;
			}
		}
		return false;
	}

	function disableVAT(productArr) {
		if (productArr.length == 0) {
			$('.vat-pay').attr('disabled', true);
			$('.vat-pay').val(0);
			$('.client-payment').attr('disabled', true);
			$('.client-payment').val(0);
		} else {
			$('.client-payment').attr('disabled', false);
			$('.vat-pay').attr('disabled', false);
		}
	}

	function updatePrice() {
		//Cap nhat lai gia
		var sumTotalEle = $('.calculated-total-price').toArray();
		var sumTotal = 0;
		var vatPay = +$('.vat-pay').val();
		sumTotalEle.forEach((item) => {
			var itemPrice = +$(item).html().replaceAll('.', '');
			sumTotal += itemPrice;
		});
		$('.total-pay').html(formatter.format(`${sumTotal + vatPay}`));
		$('.total-bill').val(sumTotal);
		$('.total-of-bill').html(formatter.format(`${sumTotal}`));
		$('.total-payment').attr("data-set", sumTotal + vatPay);
	}

	$(document).on('click', '.choose-product', function(e) {
		var parentTableEle = $(this).closest('.table-item');
		var productId = parentTableEle.find('.product-id').html();
		var productName = parentTableEle.find('.product-name').html();
		var productPrice = parentTableEle.find('.product-price').html().replaceAll('.', '');
		var productQuan = +parentTableEle.find('.product-quan').html();

		productArr = [];
		setUpArr();

		if (!containsObject(productArr, productId)) {
			productArr.push({
				id: productId,
				name: productName,
				price: productPrice,
				quantiy: 1,
				maxQuan: productQuan
			});
		} else return;

		$('.choose-table').empty();
		for (var i = 0; i < productArr.length; i++) {
			$('.choose-table').append(`
			<tr class="table-choosen">
				<td class="text-center align-middle choosen-id">${productArr[i].id}</td>
				<td class="text-center align-middle">
					<div class="choosen-pd-name">${productArr[i].name}</div>
				</td>
				<td
					class="text-center align-middle d-flex justify-content-center">
					<input style="max-width: 90px" id="soluong"
					type="number" class="form-control choosen-quan" name="SL" value="${productArr[i].quantiy}" min="1" max="${productArr[i].maxQuan}"
					oninput="this.value = Math.abs(this.value)">
				</td>
				<td class="text-center align-middle calculated-price">
					${productArr[i].price}
				</td>
				<td class="text-center align-middle calculated-total-price" data-set="${productArr[i].price * productArr[i].quantiy}">
					${productArr[i].price * productArr[i].quantiy}
				</td>
				<td class="text-center align-middle">
					<button class="remove-product btn-danger" style="border-radius: 3px;">
						<i class="fas fa-trash"></i>
					</button>
				</td>
			</tr>
		`);
		}
		disableVAT(productArr);
		updatePrice();
		//Update tien thua
		var oweMoney = (+$('.client-payment').val()) - $('.total-payment').attr('data-set');
		$('.client-payment-format').html(formatter.format(`${oweMoney}`));
	});

	$(document).on('click', '.remove-product', function() {
		var parentTableEle = $(this).closest('.table-choosen');
		var productId = parentTableEle.find('.choosen-id').html();

		productArr = [];
		setUpArr();

		//Cap nhat lai gia
		var totalProductPrice = parentTableEle.find('.calculated-total-price').attr('data-set');
		if (totalProductPrice) {
			var totalPay = $('.total-payment').attr('data-set');
			var price = totalPay - totalProductPrice;
			$('.total-bill').val(price);
			$('.total-payment').attr('data-set', price);
			$('.total-pay').html(formatter.format(price));
		} else $('.total-bill').val($('.total-bill').val());

		productArr = productArr.filter(product => product.id != productId);
		$('.choose-table').empty();
		for (var i = 0; i < productArr.length; i++) {
			$('.choose-table').append(`
			<tr class="table-choosen">
				<td class="text-center align-middle choosen-id">${productArr[i].id}</td>
				<td class="text-center align-middle">
					<div class="choosen-pd-name">${productArr[i].name}</div>
				</td>
				<td
					class="text-center align-middle d-flex justify-content-center">
					<input style="max-width: 90px" id="soluong"
					type="number" class="form-control choosen-quan" name="SL" value="${productArr[i].quantiy}" min="1" max="${productArr[i].maxQuan}"
					oninput="this.value = Math.abs(this.value)">
				</td>
				<td class="text-center align-middle calculated-price">
					${productArr[i].price}
				</td>
				<td class="text-center align-middle calculated-total-price" data-set="${productArr[i].price * productArr[i].quantiy}">
					${productArr[i].price * productArr[i].quantiy}
				</td>
				<td class="text-center align-middle">
					<button class="remove-product btn-danger" style="border-radius: 3px;">
						<i class="fas fa-trash"></i>
					</button>
				</td>
			</tr>
		`);
		}
		disableVAT(productArr);
		updatePrice();
		//Update tien thua
		var oweMoney = (+$('.client-payment').val()) - $('.total-payment').attr('data-set');
		$('.client-payment-format').html(formatter.format(`${oweMoney}`));
	});

	function getChiTietHoaDon() {
		var tableChoosen = $('.table-choosen').toArray();
		tableChoosen.forEach((item) => {
			jItem = $(item)
			var id = +jItem.find('.choosen-id').html();
			var quantity = +jItem.find('.choosen-quan').val();

			choosenArrProduct.push({ id: id, quantity: quantity });
		});

		var total = $('.total-payment').attr('data-set');
		var vat = $('.vat-pay').val();
		var clientPayment = $('.client-payment').val();

		return {
			id: +$('.phieuNhapId').html(),
			cthd: choosenArrProduct,
			vat: vat,
			tongTien: total,
			tienTra: clientPayment
		};
	}

	$(document).on('keyup change', '.choosen-quan', function() {
		var max = parseInt($(this).attr('max'));
		var min = parseInt($(this).attr('min'));
		if ($(this).val() > max) {
			$(this).val(max);
		}
		else if ($(this).val() < min) {
			$(this).val(min);
		}
	});

	var choosenArrProduct = [];
	$('.btn-submit-product').click(function(e) {
		var idHD = +$('.idHoaDon').val();
		e.preventDefault();

		//Neu tra khong du tien
		if (+$('.client-payment').val() < $('.total-payment').attr('data-set')) {
			$('.not-enough').show();
			setTimeout(function() {
				$('.not-enough').fadeOut('slow');
			}, 4000);
			return;
		}
		$.ajax({
			url: 'http://localhost:8080/NhaSachPN/invoice/get-cthd.htm',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(getChiTietHoaDon())
		})
			.done(function(respone) {
				alert(respone.data);
				location.reload();
			})
			.fail(function(xhr, exception) {
				var err = JSON.parse(JSON.stringify(exception));
				var msg = "";
				if (xhr.status === 0) {
					msg = "Not connect.\n Verify Network." + xhr.responseText;
				} else if (xhr.status == 404) {
					msg = "Requested page not found. [404]" + xhr.responseText;
				} else if (xhr.status == 500) {
					msg = "Internal Server Error [500]." + xhr.responseText;
				} else if (exception === "parsererror") {
					msg = "Requested JSON parse failed.";
				} else if (exception === "timeout") {
					msg = "Time out error." + xhr.responseText;
				} else if (exception === "abort") {
					msg = "Ajax request aborted.";
				} else {
					msg = "Error:" + xhr.status + " " + xhr.responseText;
				}
				window.location.href = `http://localhost:8080/NhaSachPN/invoice/addHD/${idHD}.htm?linkHD`;
			});
	});

	$(document).on('keyup mouseup', '.choosen-quan', function() {
		var payClient = +$('.pay-client').val();
		var parent = $(this).closest('.table-choosen');
		var price = +parent.find('.calculated-price').html().replaceAll('.', '');
		var quantity = +parent.find('.choosen-quan').val();
		var res = `${price * quantity}`;

		parent.find('.calculated-total-price').attr("data-set", price * quantity);
		parent.find('.calculated-total-price').html(res);

		var sumTotalEle = $('.calculated-total-price').toArray();
		var sumTotal = 0;
		var vatPay = +$('.vat-pay').val();
		sumTotalEle.forEach((item) => {
			var itemPrice = +$(item).html().replaceAll('.', '');
			sumTotal += itemPrice;
		});

		$('.total-of-bill').html(formatter.format(`${sumTotal}`));
		$('.total-bill').val(`${sumTotal + vatPay}`);
		$('.total-pay').html(formatter.format(`${sumTotal + vatPay}`));
		$('.total-payment').attr("data-set", sumTotal + vatPay);
		var totalPay = +$('.total-payment').attr("data-set");
		var oweMoney = (+$('.client-payment').val()) - $('.total-payment').attr('data-set');
		$('.client-payment-format').html(formatter.format(`${oweMoney}`));
	});

	$(document).on('keyup mouseup', '.vat-pay', function() {
		var sumTotalEle = $('.calculated-total-price').toArray();
		var sumTotal = 0;
		var vatPay = +$('.vat-pay').val();
		sumTotalEle.forEach((item) => {
			var itemPrice = +$(item).html().replaceAll('.', '');
			sumTotal += itemPrice;
		});

		$('.total-of-bill').html(formatter.format(`${sumTotal}`));
		$('.total-bill').val(`${sumTotal + vatPay}`);
		$('.total-pay').html(formatter.format(`${sumTotal + vatPay}`));
		$('.total-payment').attr("data-set", sumTotal + vatPay);
		var payClient = +$('.pay-client').val();
		var totalPay = +$('.total-payment').attr("data-set");
	});

	$(document).on('keyup mouseup', '.client-payment', function() {
		if ($(this).val() == 0) {
			$(this).val('');
		}
		var oweMoney = $(this).val() - $('.total-payment').attr('data-set');
		$('.client-payment-format').html(formatter.format(`${oweMoney}`));
	});

	//Tim Kiem Session
	function renderProducts(products) {
		$('.product-table-body').empty();
		for (var i = 0; i < products.length; i++) {
			var tinhTrang;
			if (products[i].tinhTrang == 1) tinhTrang = "Còn hàng";
			else if (products[i].tinhTrang == 2) tinhTrang = "Hết hàng";
			$('.product-table-body').append(`
			<tr data-set="${products[i].id}" class="table-item">
				<th scope="row"
					class="text-center align-middle product-id">${products[i].id}</th>
					<td class="text-center align-middle text-1-line-140">
						<div class="text-1-line-140 product-name">${products[i].ten}</div>
					</td>
					<td class="text-center align-middle"><img
						src="${products[i].hinhAnh}" alt="ảnh sản phẩm"
						class="rounded mx-auto"
						style="max-width: 100px; max-height: 50px"></td>
					<td class="text-center align-middle product-quan">${products[i].soLuong}</td>
					<td class="text-center align-middle product-price">
						${products[i].giaBan}</td>
					<td class="text-center align-middle">
						${products[i].loai}
					</td>
					<td class="text-center align-middle">
						${products[i].thuongHieu}
					</td>
					<td class="text-center align-middle">
						<button class="choose-product"
						style="border-radius: 3px; background-color: #20c997;">
							<i class="fas fa-cart-arrow-down"></i>
						</button>
					</td>
				</tr>
		`);
		}
	}

	var productsJSON = $('.product-json').toArray();
	var products = [];
	productsJSON.forEach((item) => { products.push(JSON.parse($(item).html().trim())) });
	renderProducts(products);

	var loaiSearchVal = '';
	var tenSearchVal = '';
	var thuongHieuVal = '';

	//id and name
	$('.idAndNameP').keyup(function() {
		tenSearchVal = $(this).val().toLowerCase();
		var productsRes = products.filter((item) => {
			if (loaiSearchVal == '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.loai == loaiSearchVal);
			}
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			//
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal == '') {
				return (item.loai == loaiSearchVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.loai == loaiSearchVal && item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.loai == loaiSearchVal && item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			//
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.thuongHieu == thuongHieuVal && item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			else return item;
		});
		renderProducts(productsRes);
	});

	// Loai
	$('.loaiSearch').change(function() {
		loaiSearchVal = $(this).val().trim();
		if (!loaiSearchVal) loaiSearchVal = '';
		productsRes = products.filter((item) => {
			if (loaiSearchVal == '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.loai == loaiSearchVal);
			}
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			//
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal == '') {
				return (item.loai == loaiSearchVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.loai == loaiSearchVal && item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.loai == loaiSearchVal && item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			//
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.thuongHieu == thuongHieuVal && item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			else return item;
		});
		renderProducts(productsRes);

	});

	// Tinh trang
	$('.thuongHieuSearch').change(function() {
		thuongHieuVal = $(this).val().trim();
		productsRes = products.filter((item) => {
			if (loaiSearchVal == '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.loai == loaiSearchVal);
			}
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			//
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal == '') {
				return (item.loai == loaiSearchVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.loai == loaiSearchVal && item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal == '' && tenSearchVal != '') {
				return (item.loai == loaiSearchVal && item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			//
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.thuongHieu == thuongHieuVal);
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal == '') {
				return (item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			if (loaiSearchVal == '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.thuongHieu == thuongHieuVal && item.ten.toLowerCase().includes(tenSearchVal));
			}
			if (loaiSearchVal != '' && thuongHieuVal != '' && tenSearchVal != '') {
				return (item.ten.toLowerCase().includes(tenSearchVal)
					&& item.thuongHieu == thuongHieuVal && item.loai == loaiSearchVal);
			}
			else return item;
		});
		renderProducts(productsRes);
	});

	//var products = JSON.parse(productsJSON);
});