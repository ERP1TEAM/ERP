document.addEventListener("DOMContentLoaded", function() {
	//수량입력시 총 금액 자동반영
	const input_qty = document.getElementById("quantity");
	const price = document.getElementById("unit_price");
	const tt_price = document.getElementById("total_price");
	input_qty.addEventListener("input", function() {
		tt_price.value = this.value * price.value;
	})

	//발주요청
	document.querySelector("#purchase_request").addEventListener("click", function() {
		formData = new FormData(order_frm);

		function hasEmptyFields(formData) {
			let hasEmpty = false;
			formData.forEach((value, key) => {
				if (!value.trim()) { // trim()으로 공백 제거 후 빈 값 확인
					hasEmpty = true;
				}
			});
			return hasEmpty;
		}

		if (hasEmptyFields(formData)) {
			alert("모든 필드를 채워주세요.");
			return; // 빈 값이 있으면 요청을 중단합니다.
		}

		fetch("./purchaseAdd", {
			method: "POST",
			body: formData
		})
			.then(response => response.text())
			.then(data => {
				alert(data);
			})
			.catch(error => {
				console.log(error);
			})
	})

	//상품목록(모달)
	const modal = document.getElementById('myModal');
	const openModalBtn = document.getElementById('open-modal-btn');
	const closeModalBtn = document.getElementById('close-modal-btn');
	const modalItems = document.querySelectorAll('.modal-item');
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
	});

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

	// 모달 아이템 클릭 시 데이터 반영
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
			unitPrice.value = uPrice;
			input_qty.value = 1;
			tt_price.value = uPrice;
			body.classList.remove('no-scroll');
			overlay.style.display = 'none';
			modal.style.display = 'none';
		});
	});
})

