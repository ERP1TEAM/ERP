document.addEventListener("DOMContentLoaded", function() {
	let rowCount = 0; // 행의 고유한 인덱스 관리
	const orderFrm = document.getElementById("order_frm");

	orderFrm.addEventListener("submit", function(event) {
		event.preventDefault();
	})

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

	//수량 입력 시 총 가격 자동 계산
	document.body.addEventListener('input', function(event) {
		// 클릭된 요소가 quantity 입력 필드인지 확인
		if (event.target && event.target.id.startsWith('quantity_')) {
			// 현재 입력된 quantity 필드의 ID에서 rowCount를 추출
			const rowNum = event.target.id.split('_')[1]; // 예: quantity_1 -> 1

			const input_qty = document.getElementById(`quantity_${rowNum}`);
			const price = document.getElementById(`unit_price_${rowNum}`);
			const tt_price = document.getElementById(`total_price_${rowNum}`);

			let qty = parseFloat(input_qty.value.replace(/,/g, ''));
			let unitPrice = parseFloat(price.value.replace(/,/g, ''));

			// 총 가격 계산
			if (!isNaN(qty) && !isNaN(unitPrice)) {
				tt_price.value = (qty * unitPrice).toLocaleString('en-US');
			} else {
				tt_price.value = '0';
			}
		}
	});

	// 발주 요청
	document.querySelector("#purchase_request").addEventListener("click", function() {
		const formData = new FormData(orderFrm);

		function hasEmptyFields(formData) {
			let hasEmpty = false;
			formData.forEach((value) => {
				if (!value.trim()) { // 공백 제거 후 빈 값 확인
					hasEmpty = true;
				}
			});
			return hasEmpty;
		}

		if (hasEmptyFields(formData)) {
			alert("상품을 선택하고 수량을 입력해주세요.");
			return; // 빈 값이 있으면 요청 중단
		}

		// 모든 수량 확인
		const quantities = formData.getAll('quantity');
		for (const value of quantities) {
			if (parseInt(value, 10) === 0) {
				alert("수량은 0이 될 수 없습니다.");
				return; // 반복문 종료
			}
		}

		// total_price와 price 변환
		const totalPrices = formData.getAll('total_price');
		const prices = formData.getAll('price');

		// 기존 값을 모두 삭제
		formData.delete('total_price');
		formData.delete('price');

		// 변환 후 다시 추가
		totalPrices.forEach((value) => {
			const convertedValue = value.replace(/,/g, ''); // 쉼표 제거
			formData.append('total_price', convertedValue); // 변환된 값을 추가
		});

		prices.forEach((value) => {
			const convertedValue = value.replace(/,/g, ''); // 쉼표 제거
			formData.append('price', convertedValue); // 변환된 값을 추가
		});

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
					alert("오류로 인해 발주등록에 실패하였습니다.");
				}
			})
			.catch(error => {
				console.log(error);
			});
	});

	//상품목록(모달)
	const modal = document.getElementById('myModal');
	const closeModal = document.getElementById('overlay');
	const closeModalBtn = document.getElementById('close-modal-btn');
	const body = document.body;

	//모달 열기
	document.body.addEventListener('click', function(event) {
		// 클릭된 요소가 modal-btn 클래스를 가지고 있는지 확인
		if (event.target.classList.contains('modal-btn')) {
			const dataIdx = event.target.getAttribute('data-row');

			// modal과 overlay가 정상적으로 정의되어 있는지 확인
			modal.style.display = 'block';
			overlay.style.display = 'block';
			body.classList.add('no-scroll');
			products('', '', dataIdx); // 상품 목록을 불러오는 함수
		}
	});

	// 모달 데이터
	const products = (code = '', word = '', dataIdx) => {
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

						// 현재 선택된 행의 ID 가져오기
						const currentRowId = dataIdx;
						document.getElementById(`product_code_${currentRowId}`).value = pCode;
						document.getElementById(`supplier_${currentRowId}`).value = maf;
						document.getElementById(`product_${currentRowId}`).value = pName;
						document.getElementById(`unit_price_${currentRowId}`).value = parseFloat(uPrice).toLocaleString("en-US");

						// 수량과 총 가격도 설정
						document.getElementById(`quantity_${currentRowId}`).value = 1;
						document.getElementById(`total_price_${currentRowId}`).value = parseFloat(uPrice).toLocaleString("en-US");

						// 모달 닫기
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
		if (event.target === closeModal || event.target === modal) {
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


	//행 추가
	function addRow() {
		rowCount++; // 행이 추가될 때마다 1씩 증가
		let tbody = document.getElementById("tbody");
		// 새로운 행 추가
		const newRow = tbody.insertRow();

		// 각 셀에 데이터 추가
		const cell1 = newRow.insertCell(0);
		cell1.style.display = 'flex';
		cell1.innerHTML = `
        <button class="product-btn modal-btn" style="width:170px;" data-row="${rowCount}">상품목록</button>
        <input type="text" id="product_code_${rowCount}" name="product_code" readonly>
    `;

		const cell2 = newRow.insertCell(1);
		cell2.innerHTML = `
        <div style="position: relative;">
            <input type="text" id="supplier_${rowCount}" name="supplier" readonly>
            <div class="autocomplete-items"></div>
        </div>
    `;

		const cell3 = newRow.insertCell(2);
		cell3.innerHTML = `<input type="text" id="product_${rowCount}" name="product" readonly>`;

		const cell4 = newRow.insertCell(3);
		cell4.innerHTML = `<input type="text" id="quantity_${rowCount}" name="quantity" maxlength="8" style="text-align:right;">`;

		const cell5 = newRow.insertCell(4);
		cell5.innerHTML = `<input type="text" id="unit_price_${rowCount}" name="price" style="text-align:right;" readonly>`;

		const cell6 = newRow.insertCell(5);
		cell6.innerHTML = `<input type="text" id="total_price_${rowCount}" style="text-align:right;" name="total_price" readonly>`;

	}
	document.getElementById("add_row").addEventListener("click", function() {
		addRow();
	})

})

