let searchCode = "";
let searchWord = "";
let startDate = "";
let endDate = "";

document.addEventListener("DOMContentLoaded", function() {
	var totalPages = 1;
	var startPage = 0;
	var endPage = 0;
	const pageSize = 5; // 페이지 번호 그룹 크기 설정

	const getQueryParam = (param) => {
		const urlParams = new URLSearchParams(window.location.search);
		return urlParams.get(param);
	}
	let p = parseInt(getQueryParam("p")) || 1;
	searchCode = getQueryParam("code") || '납품번호';  // 검색 코드
	searchWord = getQueryParam("word") || '';  // 검색어
	startDate = getQueryParam("sDate") || '';
	endDate = getQueryParam("eDate") || new Date().toISOString().split('T')[0];

	document.getElementById("search_code").value = searchCode;
	document.getElementById("search_word").value = searchWord;
	document.getElementById("start_date").value = startDate;
	document.getElementById("end_date").value = endDate;

	//paging 함수를 전역으로 설정
	window.paging = function(p, code = searchCode, word = searchWord, sDate = startDate, eDate = endDate) {
		tableData(p, code, word, sDate, eDate);
	}

	window.pgNext = function() {
		tableData(endPage + 1, searchCode, searchWord, startDate, endDate);
	}

	window.pgPrev = function() {
		tableData(startPage - 1, searchCode, searchWord, startDate, endDate);
	}

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
	const tableData = (pno, code = '', word = '', sDate = '', eDate = '') => {
		const params = new URLSearchParams({
			code: code,
			word: word,
			sDate: sDate,
			eDate: eDate
		}).toString();
		fetch(`./deliveryData/${pno}?${params}`, {
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
				        <td style="text-align:center;">${item.deliveryCode}</td>
				        <td style="text-align:center;">${item.orderNumber}</td>
				        <td style="text-align:center;">${item.productCode}</td>
				        <td>${item.productName}</td>
				        <td style="text-align:right;">${item.quantity}</td>
				        <td style="text-align:center;">${rdt}</td>
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
				if (eDate === "") {
					history.replaceState({}, '', location.pathname + `?p=${pno}`);
				} else if (word === "" && sDate === "") {
					history.replaceState({}, '', location.pathname + `?p=${pno}` + `&eDate=${eDate}`);
				} else if (sDate === "") {
					history.replaceState({}, '', location.pathname + `?p=${pno}` + `&code=${code}&word=${word}&eDate=${eDate}`);
				} else if (word === "") {
					history.replaceState({}, '', location.pathname + `?p=${pno}` + `&sDate=${sDate}&eDate=${eDate}`);
				} else {
					history.replaceState({}, '', location.pathname + `?p=${pno}` + `&sDate=${sDate}&eDate=${eDate}&code=${code}&word=${word}`);
				}
			})
			.catch(function(error) {
				alert(error);
			});
	}

	tableData(p, searchCode, searchWord, startDate, endDate);

	//검색
	document.getElementById("search_form").addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 폼 제출 방지
		startDate = document.getElementById("start_date").value;
		endDate = document.getElementById("end_date").value;
		searchCode = document.getElementById("search_code").value;
		searchWord = document.getElementById("search_word").value;
		if (startDate !== "" && endDate === "") {
			endDate = new Date().toISOString().split('T')[0];
			document.getElementById("end_date").value = endDate;
			paging(1, searchCode, searchWord, startDate, endDate);
		} else if (startDate > endDate) {
			alert("기간이 잘못 설정되었습니다.");
		} else {
			paging(1, searchCode, searchWord, startDate, endDate); // 검색 후 첫 페이지부터 시작						
		}
	});

	document.getElementById("reset_btn").addEventListener("click", function() {
		searchCode = '납품번호';
		searchWord = '';
		startDate = '';
		endDate = new Date().toISOString().split('T')[0];
		document.getElementById("search_code").value = searchCode;
		document.getElementById("search_word").value = searchWord;
		document.getElementById("start_date").value = startDate;
		document.getElementById("end_date").value = endDate;
		paging(1, '', '', '', '');
	})


});
