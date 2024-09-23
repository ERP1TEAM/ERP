document.addEventListener("DOMContentLoaded", function() {
	var totalPages = 1;
	var startPage = 0;
	var endPage = 0;
	const pageSize = 2; // 페이지 번호 그룹 크기 설정
	
	const getQueryParam = (param) => {
		const urlParams = new URLSearchParams(window.location.search);
		return urlParams.get(param);
	}
	
	let p = parseInt(getQueryParam("p")) || 1;
	let searchCode = getQueryParam("code") || '가입고코드';  // 검색 코드
    let searchWord = getQueryParam("word") || '';  // 검색어
    
    document.getElementById("search_code").value = searchCode;
    document.getElementById("search_word").value = searchWord;
	
	//paging 함수를 전역으로 설정
	window.paging = function(p, code = searchCode, word = searchWord) {
        tableData(p, code, word);
    }

    window.pgNext = function() {
        tableData(endPage + 1, searchCode, searchWord);
    }

    window.pgPrev = function() {
        tableData(startPage - 1, searchCode, searchWord);
    }
    //날짜를 yyyy-MM-dd HH-mm-ss형식으로 변환
	function formatDate(isoString) {

		const date = new Date(isoString);

		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const day = String(date.getDate()).padStart(2, '0');
		const hours = String(date.getHours()).padStart(2, '0');
		const minutes = String(date.getMinutes()).padStart(2, '0');
		const seconds = String(date.getSeconds()).padStart(2, '0');

		return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
	}

	//테이블 출력
	const tableData = (pno, code = '', word = '') => {
		fetch(`./tempReceiveData/${pno}?code=${code}&word=${word}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				const items = data.content;
				totalPages = data.totalPages;
				
				let tbody = document.querySelector('#tbody');
				tbody.innerHTML = '';
				items.forEach(function(item) {
					const rdt = formatDate(item.date);
					let th = `
				    <tr class="odd gradeX">
				        <td>${item.code}</td>
				        <td style="text-align:center;">${item.orderNumber}</td>
				        <td>${item.supplierName}</td>
				        <td style="text-align:center;">${item.productCode}</td>
				        <td>${item.productName}</td>
				        <td style="text-align:right;">${item.quantity}</td>
				        <td style="text-align:right;">${item.wtQuantity}</td>
				        <td style="text-align:center;">${rdt}</td>
				        <td style="text-align:center;">${item.manager}</td>
				        <td style="text-align:center;"><button type="button" class="small-tap receiving" 
				        			data-productCode="${item.productCode}"
				        			data-deli="${item.deliveryCode}"
									data-on="${item.orderNumber}"
									data-code="${item.code}"
									data-name="${item.productName}"
									data-qty="${item.quantity}"
									data-wqty="${item.wtQuantity}">입고</button></td>
				    </tr>`;
					tbody.innerHTML += th;
				})

				const paging = document.getElementById("paging");
				paging.innerHTML = ''; // 'innerHTML'로 수정

				// 페이지 그룹의 시작과 끝 계산
				startPage = Math.floor((pno - 1) / pageSize) * pageSize + 1;
				endPage = Math.min(startPage + pageSize - 1, totalPages);

				// 페이징 HTML 생성
				let paginationHTML = `<ul class="pagination">`;
				
				// 'Precious' 링크 추가
				if(startPage > pageSize){
					paginationHTML += `
					        <li class="page-item"><a class="page-link" aria-label="Previous" onclick="pgPrev()">
					            <span aria-hidden="true">&laquo;</span>
					        </a></li>
					`;
				}

				// 페이지 번호 링크 추가
				for (let i = startPage; i <= endPage; i++) {
					const className = pno === i ? 'page-item current-page' : 'page-item';
					paginationHTML += `
		                <li class="${className}"><a class="page-link" onclick="paging(${i})">${i}</a></li>
		            `;
				}

				// 'Next' 링크 추가
				if(endPage < totalPages){
					paginationHTML += `
					        <li class="page-item"><a class="page-link" aria-label="Next" onclick="pgNext()">
					            <span aria-hidden="true">&raquo;</span>
					        </a></li>
					`;
				}
				
				paginationHTML += `</ul>`;

				// 페이징 HTML을 페이지에 삽입
				paging.innerHTML = paginationHTML;
				
				if(word === ""){
	                history.replaceState({}, '', location.pathname + `?p=${pno}`);			
				}else{
					history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
				}
			})
			.catch(function(error) {
				alert(error);
			});
	}

	tableData(p, searchCode, searchWord);
	
	//검색
    document.getElementById("search_form").addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 폼 제출 방지
        searchCode = document.getElementById("search_code").value;
        searchWord = document.getElementById("search_word").value;
        if(!searchCode && searchWord){
			alert("검색분류를 선택해주세요");
		}else{
		    paging(1, searchCode, searchWord); // 검색 후 첫 페이지부터 시작			
		}
    });
	
	//입고버튼 클릭
	document.querySelector('#tbody').addEventListener('click', function(event) {
		if (event.target && event.target.classList.contains('receiving')) {
			const btn = event.target;
			const orNum = btn.getAttribute("data-on");
			const code = btn.getAttribute("data-code");
			const name = btn.getAttribute("data-name");
			const qty = btn.getAttribute("data-qty");
			const wqty = btn.getAttribute("data-wqty");
			const deli = btn.getAttribute("data-deli");
			const productCode = btn.getAttribute("data-productCode");
			receivingModal(orNum, code, name, qty, wqty, deli, productCode);
		}
	});

	//모달닫기
	document.getElementById('closemodal').addEventListener('click', function() {
		document.getElementById('warehouselistmodal').style.display = 'none';
		document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
		document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
	});

	//입고확정 모달 출력
	function receivingModal(orNum, code, name, qty, wqty, deli, productCode) {
		fetch(`./receivingModal?ornum=${encodeURIComponent(orNum)}&code=${encodeURIComponent(code)}&name=${encodeURIComponent(name)}&qty=${encodeURIComponent(qty)}&wqty=${encodeURIComponent(wqty)}&deli=${encodeURIComponent(deli)}&productCode=${encodeURIComponent(productCode)}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				let tbody2 = document.querySelector('#tbody2');
				tbody2.innerHTML = '';
				if (data.name.length > 20) {
					//data.name = data.name.slice(0,20) + "...";
				}

				let th = `
			    <tr class="odd gradeX">
			        <input type="hidden" id="ornum" value="${data.ornum}">
			        <input type="hidden" id="code" value="${data.code}">
			        <input type="hidden" id="deli" value="${data.deli}">
			        <input type="hidden" id="productCode" value="${data.productCode}">
			        <td>${data.name}</td>
			        <td>
			        	<select id="location" name="location" class="condition-select" style="display:block;">
			        `;
			        if(data.location.length > 1){
				        data.location.forEach(function(item){
					        th += `
					                <option value="${item}">${item}</option>
					              `;						
						})						
					}else{
						th += `
					                <option value="N">${data.location[0]}</option>
					              `;
					}
			              
			        th += `
			            </select>
			        </td>
			        <td><input type="text" id="qty" value="${data.qty}" readonly class="no-style" style="text-align:right;"></td>
			        <td><input type="text" id="wqty" value="${data.wqty}" readonly class="no-style" style="text-align:right;"></td>
			        <td><input type="text" id="re-qty" name="re_qty" style="text-align:right;"></td>
			        <td><input type="text" id="ca-qty" name="ca_qty" readonly class="no-style" style="text-align:right;"></td>
			        <td>
			            <select id="condition" name="condition" class="condition-select" style="display:block;">
			                <option value="">반품없음</option>
			                <option value="불량">불량</option>
			                <option value="상함">상함</option>
			                <option value="기타">기타</option>
			            </select>
			        </td>
			        <td><input type="text" id="memo" class="no-style"></td>
			    </tr>`;
				tbody2.innerHTML += th;
				document.getElementById('warehouselistmodal').style.display = 'block';
				document.getElementById('overlay').style.display = 'block';   // 오버레이 숨기기
				document.body.style.overflow = 'hidden';  // 배경 스크롤 다시 활성화
			})
			.catch(function(error) {
				alert(error);
			});
	}

	document.querySelector('#tbody2').addEventListener('change', function(event) {
		if (event.target && event.target.matches('select.condition-select')) {
			if (event.target.value === "기타") {
				document.getElementById("memo").innerHTML = `<input type="text">`;
			} else {
				document.getElementById("memo").innerHTML = "";
			}
		}
	});
	document.querySelector('#tbody2').addEventListener('focusout', function(event) {
		if (event.target && event.target.matches("#re-qty")) {
			let wqty = parseInt(document.querySelector("#wqty").value);
			let reQty = parseInt(event.target.value);
			let caQty = parseInt(document.querySelector("#ca-qty").value);
			if (reQty > wqty) {
				event.target.value = wqty;
				document.querySelector("#ca-qty").value = 0;
			} else {
				caQty = wqty - reQty;
				if (isNaN(caQty)) {
					caQty = 0;
				}
				document.querySelector("#ca-qty").value = caQty;
			}
		}
	});

	//입고 및 반품확정 버튼 클릭
	document.getElementById('final-btn').addEventListener('click', function() {
		let orNum = document.getElementById("ornum").value;
		let code = document.getElementById("code").value;
		let wqty = parseInt(document.getElementById("wqty").value);
		let reQty = parseInt(document.getElementById("re-qty").value);
		let caQty = parseInt(document.getElementById("ca-qty").value);
		let con = document.getElementById("condition").value;
		let memo = document.getElementById("memo").value;
		let deli = document.getElementById("deli").value;
		let location = document.getElementById("location").value;
		let productCode = document.getElementById("productCode").value;

		if (!reQty && reQty !== 0) {
			alert("입고수량을 입력하셔야 합니다.");
			return;
		} else if (!caQty && caQty !== 0) {
			alert("반품수량을 입력하셔야 합니다.");
			return;
		} else if (wqty < reQty) {
			alert("대기수량을 확인해주세요");
			return;
		}
		const formData = new FormData();
		formData.append("orderNumber", orNum);
		formData.append("code", code);
		formData.append("deliveryCode", deli);
		formData.append("wqty", wqty);
		formData.append("reQty", reQty);
		formData.append("caQty", caQty);
		formData.append("con", con);
		formData.append("memo", memo);
		formData.append("location", location);
		formData.append("productCode", productCode);

		fetch("./receiving", {
			method: "POST",
			body: formData
		})
			.then(response => response.text())
			.then(data => {
				if (data === "ok") {
					alert('입고가 완료되었습니다.');
					window.location.reload();
				} else if (data === "over") {
					alert("대기수량을 확인해주세요.");
				} else if (data === "no") {
					alert("입고등록에 실패하였습니다.");
				}
			})
			.catch(error => {
				conosole.log(error);
			})

		document.getElementById('warehouselistmodal').style.display = 'none';
		document.getElementById('overlay').style.display = 'none';
		document.body.style.overflow = 'auto';
	})

});