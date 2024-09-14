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

	//paging 함수를 전역으로 설정
	window.paging = function(p) {
		tableData(p);
	}
	window.pgNext = function() {
		tableData(endPage+1);
	}
	window.pgPrev = function() {
		tableData(startPage-1);
	}

	//테이블 출력
	const tableData = (pno) => {
		fetch(`../main/receive/summaryData/${pno}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				const items = data.content;
				totalPages = data.totalPages;

				let tbody = document.querySelector('#tbody');
				tbody.innerHTML = '';
				items.forEach(function(item) {
					let th = `
				    <tr class="odd gradeX">
				        <td>${item.orderNumber}</td>
				        <td>${item.productName}</td>
				        <td>${item.purchaseQuantity}</td>
				        <td>${item.totalWtQuantity}</td>
				        <td>${item.totalReceiveQuantity}</td>
				        <td>${item.totalReturnQuantity}</td>
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
				history.replaceState({}, '', location.pathname + `?p=${pno}`);
			})
			.catch(function(error) {
				alert(error);
			});
	}

	tableData(p);


});
