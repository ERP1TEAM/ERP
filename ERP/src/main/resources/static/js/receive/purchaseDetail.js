document.addEventListener("DOMContentLoaded", function() {
	var totalPages = 1;
	var startPage = 0;
	var endPage = 0;
	const pageSize = 3; // 페이지 번호 그룹 크기 설정

	const getQueryParams = (params) => {
		const urlParams = new URLSearchParams(window.location.search);
		const result = {};

		// 주어진 파라미터 배열을 순회하면서 각 파라미터 값을 객체에 추가
		params.forEach(param => {
			const value = urlParams.get(param);
			result[param] = value;
		});

		return result;
	}

	// 쿼리 파라미터 가져오기
	const queryParams = getQueryParams(["p", "s", "code", "word"]);

	// 파라미터 값 갖고오기 및 기본값 설정
	let pa1 = queryParams["p"] || 1;
	let pa2 = queryParams["s"] || "all";
	let searchCode = queryParams["code"] || '발주번호';
	let searchWord = queryParams["word"] || '';
	
	// 검색 코드와 단어를 폼 필드에 설정
    document.getElementById("search_code").value = searchCode;
    document.getElementById("search_word").value = searchWord;

	//paging 함수를 전역으로 설정
	window.paging = function(p, code = searchCode, word = searchWord) {
		params = getQueryParams(["p", "s", "code", "word"]);
		tableData(p, params["s"], code, word);
	}
	window.pgNext = function() {
		params = getQueryParams(["p", "s", "code", "word"]);
		tableData(endPage + 1, params["s"], code, word);
	}
	window.pgPrev = function() {
		params = getQueryParams(["p", "s", "code", "word"]);
		tableData(startPage - 1, params["s"], code, word);
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
	const tableData = (pno, sta, code = '', word = '') => {
		fetch(`./purchaseData/${pno}/${sta}?code=${code}&word=${word}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				pno = parseInt(pno);
				const items = data.content;
				totalPages = data.totalPages;

				let tbody = document.querySelector('#tbody');
				tbody.innerHTML = '';
				items.forEach(function(item) {
					const rdt = formatDate(item.orderDate);
					let th = `
				    <tr class="odd gradeX">
				        <td>${item.orderNumber}</td>
				        <td>${item.supplierName}</td>
				        <td>${item.productName}</td>
				        <td>${item.quantity}</td>
				        <td>${item.price.toLocaleString()}</td>
				        <td>${item.totalPrice.toLocaleString()}</td>
				        <td>${rdt}</td>
				        <td>${item.expectedDate}</td>
				        <td>${item.manager}</td>
				        <td>${item.status}</td>
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
				if (startPage > pageSize) {
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
				if (endPage < totalPages) {
					paginationHTML += `
					        <li class="page-item"><a class="page-link" aria-label="Next" onclick="pgNext()">
					            <span aria-hidden="true">&raquo;</span>
					        </a></li>
					`;
				}

				paginationHTML += `</ul>`;

				// 페이징 HTML을 페이지에 삽입
				paging.innerHTML = paginationHTML;

				// URL 업데이트 (검색 조건도 포함)
				if (word === "") {
					history.replaceState({}, '', location.pathname + `?p=${pno}` + `&s=${sta}`);
				} else {
					history.replaceState({}, '', location.pathname + `?p=${pno}` + `&s=${sta}&code=${code}&word=${word}`);
				}
			})
			.catch(function(error) {
				alert(error);
			});
	}

	//페이지 로드시 실행
	const start = () => {
		tableData(pa1, pa2, searchCode, searchWord);
	}
	start();



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

	// 새로고침해도 탭부분 스타일 유지
	if (queryParams["s"] === "wa") {
		let le = document.getElementById("wait");
		setActiveStyle(le);
	} else if (queryParams["s"] === "su") {
		let le = document.getElementById("finish");
		setActiveStyle(le);
	} else {
		let le = document.getElementById("all");
		setActiveStyle(le);
	}


	// 데이터 로딩 함수
	function loadData(status) {
		document.getElementById("search_word").value = "";
		tableData(1, status);
	}

	// 이벤트 리스너 설정
	function setupEventListeners() {
		document.getElementById("all").addEventListener("click", function() {
			setActiveStyle(this);
			loadData("all");
		});

		document.getElementById("wait").addEventListener("click", function() {
			setActiveStyle(this);
			loadData("wa");
		});

		document.getElementById("finish").addEventListener("click", function() {
			setActiveStyle(this);
			loadData("su");
		});

	}

	// 초기화
	setupEventListeners();

	//검색
	document.getElementById("search_form").addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 폼 제출 방지
		searchCode = document.getElementById("search_code").value || '발주번호';
		searchWord = document.getElementById("search_word").value;
		paging(1, searchCode, searchWord); // 검색 후 첫 페이지부터 시작		
		setActiveStyle(document.getElementById("all"));		
	});
});
