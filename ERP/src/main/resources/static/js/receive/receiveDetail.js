document.addEventListener("DOMContentLoaded", function() {
	var totalPages = 1;
	var startPage = 0;
	var endPage = 0;
	const pageSize = 3; // 페이지 번호 그룹 크기 설정
	
	const getQueryParam = (param) => {
		const urlParams = new URLSearchParams(window.location.search);
		return urlParams.get(param);
	}
	let p = parseInt(getQueryParam("p")) || 1;
	let searchCode = getQueryParam("code") || '입고번호';  // 검색 코드
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
		fetch(`./detailData/${pno}?code=${code}&word=${word}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				const items = data.content;
				totalPages = data.totalPages;

				let tbody = document.querySelector('#tbody');
				tbody.innerHTML = '';
				items.forEach(function(item) {
					const rdt = formatDate(item.receiveDate);
					let th = `
				    <tr class="odd gradeX">
				        <td>${item.receiveCode}</td>
				        <td>${item.orderNumber}</td>
				        <td>${item.supplierName}</td>
				        <td>${item.productCode}</td>
				        <td>${item.productName}</td>
				        <td>${item.receiveQuantity}</td>
				        <td>${rdt}</td>
				        <td>${item.manager}</td>
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
				
				// URL 업데이트 (검색 조건도 포함)
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
		paging(1, searchCode, searchWord); // 검색 후 첫 페이지부터 시작				
    });

});
