	//엑셀불러오기
	/*
	document.querySelector("#excel_btn").addEventListener("click",function(){
		const fileInput = document.querySelector("#excel");
		const file = fileInput.files[0];
	
		if (!file) {
			alert("파일을 선택해 주세요.");
			return;
		}
	
		// 파일 확장자 체크
		const validExtensions = ['.xls', '.xlsx'];
		const fileExtension = file.name.split('.').pop().toLowerCase();
		if (!validExtensions.includes('.' + fileExtension)) {
			alert("엑셀 파일(.xls, .xlsx)만 업로드할 수 있습니다.");
			return;
		}
	    
		formData = new FormData(excel_frm);
	    
		fetch("./upload-excel",{
			method:"post",
			body:formData
		})
		.then(response => response.json())
		.then(data => {
			const tbody = document.getElementById('tbody');
			let resultHTML = '';
	
			data.forEach(item => {
				resultHTML += `<tr class="cb-table-tbody-data">
									<td><input type="text" name="product_code" value="${item.product_code}" readonly></td>
									<td><input type="text" name="supplier" value="${item.supplier}" readonly></td>
									<td><input type="text" name="product" value="${item.product}" readonly></td>
									<td><input type="text" name="quantity" value="${item.quantity}" readonly></td>
									<td><input type="text" name="price" value="${item.price}" readonly></td>
									<td><input type="text" name="total_price" value="${item.total_price}" readonly></td>
							   </tr>`;
			});
	
			tbody.innerHTML = resultHTML;
		})
		.catch(error => {
			console.log(error);
		})
	})
	*/
	
	//자동완성
	/*
	const input = document.getElementById('supplier');
	  const resultsContainer = document.getElementById('autocomplete-results');

	  input.addEventListener('input', function() {
		const query = this.value;

		if (query.length > 0) {
		  fetch(`./autocomplete?term=${encodeURIComponent(query)}`)
			.then(response => response.json())
			.then(data => {
			  resultsContainer.innerHTML = '';

			  data.forEach(item => {
				const div = document.createElement('div');
				div.classList.add('autocomplete-item');
				div.textContent = item.name;
				div.addEventListener('click', function() {
				  input.value = item.name;
				  resultsContainer.innerHTML = '';
				});
				resultsContainer.appendChild(div);
			  });
			})
			.catch(error => console.error('Error:', error));
		} else {
		  resultsContainer.innerHTML = '';
		}
	  });
	*/
	
	
	//테이블 출력
	const tableData = (sta) => {
		fetch(`../main/receive/temporaryData/${sta}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				let thead = document.querySelector("#thead");
				thead.innerHTML = '';
				let tbody = document.querySelector('#tbody');
				tbody.innerHTML = '';
				if (sta === "처리대기") {
					let th = `<tr>
										<th class="center" style="width: 8%;">가입고코드</th>
													<th class="center" style="width: 7%;">주문번호</th>
													<th class="center" style="width: 7%;">제조사</th>
													<th class="center" style="width: 8%;">품목코드</th>
													<th class="center" style="width: 28%;">품목명</th>
													<th class="center" style="width: 7%;">발주수량</th>
													<th class="center" style="width: 7%;">대기수량</th>
													<th class="center" style="width: 10%;">가입고일시</th>
													<th class="center" style="width: 5%;">담당자</th>
													<th class="center" style="width: 5%;">입고</th>
												</tr>`;
					thead.innerHTML = th;
					data.forEach(function(item) {
						let th = `
				    <tr class="odd gradeX">
				        <td>${item.code}</td>
				        <td>${item.orderNumber}</td>
				        <td>${item.supplierName}</td>
				        <td>${item.productCode}</td>
				        <td>${item.productName}</td>
				        <td>${item.quantity}</td>
				        <td>${item.wtQuantity}</td>
				        <td>${item.date}</td>
				        <td>${item.manager}</td>
				        <td><button type="button" class="small-tap receiving"
														 	data-on="${item.orderNumber}"
															data-code="${item.code}" 
															data-name="${item.productName}"
															data-qty="${item.quantity}"
															data-wqty="${item.wtQuantity}">입고</button></td>
				    </tr>`;
						tbody.innerHTML += th;
					})
				}else {
					let th = `<tr>
								<th class="center" style="width: 8%;">가입고코드</th>
								<th class="center" style="width: 7%;">주문번호</th>
								<th class="center" style="width: 7%;">제조사</th>
								<th class="center" style="width: 8%;">품목코드</th>
								<th class="center" style="width: 28%;">품목명</th>
								<th class="center" style="width: 7%;">발주수량</th>
								<th class="center" style="width: 7%;">대기수량</th>
								<th class="center" style="width: 10%;">가입고일시</th>
								<th class="center" style="width: 5%;">담당자</th>
								</tr>`;
					thead.innerHTML = th;
					data.forEach(function(item) {
						let th = `
				    <tr class="odd gradeX">
				        <td>${item.code}</td>
				        <td>${item.orderNumber}</td>
				        <td>${item.supplierName}</td>
				        <td>${item.productCode}</td>
				        <td>${item.productName}</td>
				        <td>${item.quantity}</td>
				        <td>${item.wtQuantity}</td>
				        <td>${item.date}</td>
				        <td>${item.manager}</td>
				    </tr>`;
						tbody.innerHTML += th;
					})
				}



			})
			.catch(function(error) {
				alert(error);
			});
	}

	tableData(null);

	// 스타일을 설정하는 함수
	function setActiveStyle(activeElement) {
		const elements = [document.getElementById("wait"), document.getElementById("finish"), document.getElementById("all")];

		elements.forEach(element => {
			if (element === activeElement) {
				element.style.backgroundColor = "#007BFF";
				element.style.color = "white";
			} else {
				element.style.backgroundColor = "#FFFFFF";
				element.style.color = "black";
			}
		});
	}
	setActiveStyle(document.getElementById("all"));

	// 데이터 로딩 함수
	function loadData(status) {
		tableData(status);
	}

	// 이벤트 리스너 설정
	function setupEventListeners() {
		document.getElementById("wait").addEventListener("click", function() {
			setActiveStyle(this);
			loadData("처리대기");
		});

		document.getElementById("finish").addEventListener("click", function() {
			setActiveStyle(this);
			loadData("처리완료");
		});

		document.getElementById("all").addEventListener("click", function() {
			setActiveStyle(this);
			loadData("null");
		});
	}

	// 초기화
	setupEventListeners();