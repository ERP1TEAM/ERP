document.addEventListener("DOMContentLoaded", function() {
	var totalPages = 1;

	const getQueryParams = (params) => {
		const urlParams = new URLSearchParams(window.location.search);
		const result = [];

		// 주어진 파라미터 배열을 순회하면서 각 파라미터 값을 배열에 추가
		params.forEach(param => {
			const value = urlParams.get(param);
			result.push({ [param]: value });
		});

		return result;
	}

	let params = getQueryParams(["p","s"]);
	
	//paging 함수를 전역으로 설정
	window.paging = function(p) {
		params = getQueryParams(["p","s"]);
		tableData(p,params[1]["s"]);
	}
	window.pgNext = function() {
		params = getQueryParams(["p","s"]);
		tableData(totalPages,params[1]["s"]);
	}
	window.pgPrev = function() {
		params = getQueryParams(["p","s"]);
		tableData(1,params[1]["s"]);
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
	const tableData = (pno, sta) => {
		fetch(`../main/receive/purchaseData/${pno}/${sta}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
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

				// 페이징 HTML 생성
				let paginationHTML = `
				    <ul class="pagination">
				        <li class="page-item"><a class="page-link" aria-label="Previous" onclick="pgPrev()">
				            <span aria-hidden="true">&laquo;</span>
				        </a></li>
				`;

				// 페이지 번호 링크 추가
				for (let i = 1; i <= totalPages; i++) {
					const className = pno === i ? 'page-item current-page' : 'page-item';
					paginationHTML += `
		                <li class="${className}"><a class="page-link" onclick="paging(${i})">${i}</a></li>
		            `;
				}

				// 'Next' 링크 추가
				paginationHTML += `
				        <li class="page-item"><a class="page-link" aria-label="Next" onclick="pgNext()">
				            <span aria-hidden="true">&raquo;</span>
				        </a></li>
				    </ul>
				`;

				// 페이징 HTML을 페이지에 삽입
				paging.innerHTML = paginationHTML;
				history.replaceState({}, '', location.pathname + `?p=${pno}` + `&s=${sta}`);
			})
			.catch(function(error) {
				alert(error);
			});
	}

	tableData(1,"all");

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
		tableData(1,status);
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
});
