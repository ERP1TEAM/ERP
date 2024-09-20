document.addEventListener("DOMContentLoaded", function() {
	// 모든 가격 요소를 선택
	var prices = document.querySelectorAll('.price');

	// 각 가격에 대해 포맷 적용
	prices.forEach(function(priceElement) {
		// 가격 요소의 텍스트를 가져와 숫자로 변환
		var price = parseFloat(priceElement.textContent);
		// 숫자가 유효하면 3자리마다 쉼표를 추가한 문자열로 변환
		if (!isNaN(price)) {
			priceElement.textContent = price.toLocaleString('en-US');
		}
	});


	//수량입력시 총 금액 자동반영
	const input_qty = document.getElementById("quantity");
	const price = document.getElementById("unit_price");
	const tt_price = document.getElementById("total_price");
	input_qty.addEventListener("input", function() {
		let qty = parseFloat(this.value.replace(/,/g, ''));
		let unitPrice = parseFloat(price.value.replace(/,/g, ''));
		tt_price.value = (qty * unitPrice).toLocaleString('en-US');
		if (tt_price.value == "NaN") {
			tt_price.value = 0;
		}
	})

	//발주요청
	document.querySelector("#purchase_request").addEventListener("click", function() {
		formData = new FormData(order_frm);

		function hasEmptyFields(formData) {
			let hasEmpty = false;
			formData.forEach((value) => {
				if (!value.trim()) { // trim()으로 공백 제거 후 빈 값 확인
					hasEmpty = true;
				}
			});
			return hasEmpty;
		}

		if (hasEmptyFields(formData)) {
			alert("상품을 선택하고 수량을 입력해주세요.");
			return; // 빈 값이 있으면 요청을 중단합니다.
		}

		if (formData.get("quantity") === "0") {
			alert("수량은 0이 될 수 없습니다.");
			return;
		}

		const totalPrice = formData.get("total_price").replace(/,/g, '');
		const unitPrice = formData.get("price").replace(/,/g, '');

		// 숫자 변환
		formData.set("total_price", totalPrice);
		formData.set("price", parseFloat(unitPrice));

		fetch("./purchaseAdd", {
			method: "POST",
			body: formData
		})
			.then(response => response.text())
			.then(data => {
				if (data === "success") {
					alert("발주등록 되었습니다.");
					location.reload();
				} else {
					alert("오류로 인하여 발주등록에 실패하였습니다.");
				}
			})
			.catch(error => {
				console.log(error);
			})
	})

	//상품목록(모달)
	const modal = document.getElementById('myModal');
	const openModalBtn = document.getElementById('open-modal-btn');
	const closeModalBtn = document.getElementById('close-modal-btn');

	const productCode = document.getElementById('product_code');
	const supplier = document.getElementById('supplier');
	const product = document.getElementById('product');
	const unitPrice = document.getElementById('unit_price');
	const body = document.body;



	// 모달 열기
	openModalBtn.addEventListener('click', () => {
		modal.style.display = 'block';
		overlay.style.display = 'block';
		body.classList.add('no-scroll');
		products();
	});

	// 모달 데이터
	const products = (code = '', word = '') => {
		fetch(`./productData?code=${code}&word=${word}`, {
			method: "GET"
		})
			.then(response => response.json())
			.then(data => {
				let tbody = document.getElementById("tbody2");
				tbody.innerHTML = '';

				data.forEach(function(item) {
					th = `<tr class="modal-item">
							<td data-code="${item.productCode}"
								>${item.productCode}</td>
							<td data-maf="${item.manufacturer}"
								>${item.manufacturer}</td>
							<td data-name="${item.productName}"
								>${item.productName}</td>
							<td class="price" data-price="${item.unitPrice}"
							 	style="text-align:right;">${item.unitPrice.toLocaleString()}</td>
						</tr>`;
					tbody.innerHTML += th;
				})

				// 모달 아이템 클릭 시 데이터 반영
				const modalItems = document.querySelectorAll('.modal-item');
				modalItems.forEach(item => {
					item.addEventListener('click', (event) => {
						const pRow = event.currentTarget;
						const pCode = pRow.querySelector('[data-code]').getAttribute('data-code');
						const maf = pRow.querySelector('[data-maf]').getAttribute('data-maf');
						const pName = pRow.querySelector('[data-name]').getAttribute('data-name');
						const uPrice = pRow.querySelector('[data-price]').getAttribute('data-price');

						productCode.value = pCode;
						supplier.value = maf;
						product.value = pName;
						unitPrice.value = parseFloat(uPrice).toLocaleString("en-US");
						input_qty.value = 1;
						tt_price.value = unitPrice.value;
						body.classList.remove('no-scroll');
						overlay.style.display = 'none';
						modal.style.display = 'none';
					});
				});
			})
			.catch(error => {
				console.log(error);
				alert("예기치 못한 오류가 발생하였습니다.");
			})
	}

	// 모달 닫기
	closeModalBtn.addEventListener('click', () => {
		modal.style.display = 'none';
		overlay.style.display = 'none';
		body.classList.remove('no-scroll');
	});

	// 모달 외부 클릭 시 닫기
	window.addEventListener('click', (event) => {
		if (event.target === modal) {
			modal.style.display = 'none';
			overlay.style.display = 'none';
			body.classList.remove('no-scroll');
		}
	});

	//검색
	document.getElementById("search_form").addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 폼 제출 방지
		searchCode = document.getElementById("search_code").value || '제조사';
		searchWord = document.getElementById("search_word").value;
		products(searchCode, searchWord);
	});

})

