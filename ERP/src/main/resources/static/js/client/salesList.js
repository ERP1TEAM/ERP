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
	let searchCode = getQueryParam("code") || '거래처코드';  // 검색 코드
	let searchWord = getQueryParam("word") || '';  // 검색어

	document.getElementById("search_code").value = searchCode;
	document.getElementById("search_word").value = searchWord;

	// 페이징 함수를 전역으로 설정
	window.paging = function(p, code = searchCode, word = searchWord) {
		tableData(p, code, word);
	}

	window.pgNext = function() {
		tableData(endPage + 1, searchCode, searchWord);
	}

	window.pgPrev = function() {
		tableData(startPage - 1, searchCode, searchWord);
	}

	window.toggleActions = function(button) {
		const actionButtons = button.nextElementSibling;
		const isVisible = actionButtons.style.display === 'block';
		document.querySelectorAll('.action-buttons').forEach(function(btn) {
			btn.style.display = 'none';
		});
		actionButtons.style.display = isVisible ? 'none' : 'block';
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

	// 전화번호 포맷팅 함수
	function formatTel(tel) {
		if (tel.length === 10) {
			return tel.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3'); // 000-000-0000
		} else if (tel.length === 11) {
			return tel.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'); // 000-0000-0000
		} else if (tel.length === 8) {
			return tel.replace(/(\d{4})(\d{4})/, '$1-$2');
		}
		return tel; // 기본 그대로 반환 (기타 형식)
	};


	// 테이블 출력
	const tableData = (pno, code = '', word = '') => {
		fetch(`./salesList/${pno}?code=${code}&word=${word}`, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				const items = data.content;
				totalPages = data.totalPages;

				let tbody = document.querySelector('#tbody');
				tbody.innerHTML = '';
				items.forEach(function(item) {
					const rdt = formatDate(item.createDate);
					const formattedTel = formatTel(item.tel);
					let th = `
                    <tr class="odd gradeX">
                        <td style="text-align:center;">${item.code}</td>
                        <td>${item.name}</td>
                        <td>${item.email}</td>
                        <td style="text-align:center;">${formattedTel}</td>
                        <td>${item.address}</td>
                        <td style="text-align:center;">${rdt}</td>
                        <td style="text-align:center;"><button class="modify-btn" data-code="${item.code}">수정</button></td>
                    </tr>`;
					tbody.innerHTML += th;
				})

				document.querySelectorAll(".modify-btn").forEach(function(button) {
					button.addEventListener("click", function() {
						document.getElementById("locationoverlay").style.display = "block";
						document.getElementById("locationinmodal").style.display = "block";
						document.body.style.overflow = "hidden";
						const code = this.getAttribute("data-code");
						fetch(`./salesOne/${code}`, {
							method: "GET",
						})
							.then(response => response.json())
							.then(data => {
								document.getElementById("name").value = data.name;
								document.getElementById("email").value = data.email;
								document.getElementById("tel").value = data.tel;
								document.getElementById("h2").innerText = "거래처 수정";
								document.getElementById("btn_div").innerHTML = `<input type="button" value="수정" class="p_button p_button_color2"
										id="mod_btn">`
								const address = data.address.split('(')[0].trim(); // 괄호 이전의 주소
								const match = data.address.match(/\(([^)]+)\)/); // 괄호 안의 상세주소

								let detailAddress = ''; // 기본값 설정
								if (match) {
									detailAddress = match[1]; // 첫 번째 그룹을 가져옴
								}
								document.getElementById("address").value = address;
								document.getElementById("detail_address").value = detailAddress;

								//수정버튼 클릭시
								document.querySelector("#mod_btn").addEventListener("click", function(event) {
									event.preventDefault();
									const formData = new FormData(sales_frm);

									const name = formData.get("name");
									const tel = formData.get("tel");
									const email = formData.get("email");
									const address = formData.get("address");
									const detailAddress = formData.get("detail_address");

									if (!name) {
										alert("거래처명을 입력하세요");
										return;
									}
									if (!tel) {
										alert("연락처를 입력하세요");
										return;
									}
									if (isNaN(tel)) {
										alert("연락처는 숫자만 가능합니다.");
										return;
									}
									if (!email) {
										alert("이메일을 입력하세요");
										return;
									}
									// 이메일 형식 검사
									const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
									if (!emailPattern.test(email)) {
										alert("올바른 이메일 형식을 입력하세요");
										return;
									}
									if (!address) {
										alert("주소를 입력하세요");
										return;
									}
									// detailAddress가 있을 경우에만 추가
									if (detailAddress) {
										formData.set("address", `${address} (${detailAddress})`);
									} else {
										formData.set("address", address);
									}
									formData.set("code", code);
									formData.set("create_date", null);
									fetch(`./modifySales/${code}`, {
										method: "PATCH",
										body: formData
									})
										.then(response => response.text())
										.then(data => {
											if (data === "ok") {
												alert("거래처가 수정되었습니다.");
												window.location.reload();
											} else {
												console.log(data);
												alert("거래처 수정중 오류가 발생하였습니다.");
											}
										})
										.catch(error => {
											console.log(error);
											alert("거래처 수정중 오류가 발생하였습니다.");
										})
								})

							})
					});
				})

				const paging = document.getElementById("paging");
				paging.innerHTML = '';

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
                        <li class="${className}"><a class="page-link" onclick="paging(${i}, '${code}', '${word}')">${i}</a></li>
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
					history.replaceState({}, '', location.pathname + `?p=${pno}`);
				} else {
					history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
				}
			})
			.catch(function(error) {
				alert(error);
			});
	}

	// 첫 페이지 로드 시 테이블 데이터 출력
	tableData(p, searchCode, searchWord);

	//검색
	document.getElementById("search_form").addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 폼 제출 방지
		searchCode = document.getElementById("search_code").value;
		searchWord = document.getElementById("search_word").value;
		if (!searchCode && searchWord) {
			alert("검색분류를 선택해주세요");
		} else {
			paging(1, searchCode, searchWord); // 검색 후 첫 페이지부터 시작				
		}
	});



	const salesFrm = document.getElementById("sales_frm");

	//로케이션 모달 열기	
	document.getElementById("locationbtn").addEventListener("click", function() {
		resetSalesForm();
		document.getElementById("locationoverlay").style.display = "block";
		document.getElementById("locationinmodal").style.display = "block";
		document.body.style.overflow = "hidden";
	});

	// 모달 닫기 함수
	function closeModal() {
		document.getElementById("locationoverlay").style.display = "none";
		document.getElementById("locationinmodal").style.display = "none";
		document.body.style.overflow = 'auto';
		// 폼 데이터 초기화
		resetSalesForm();
	}

	// 로케이션 X버튼으로 모달 닫기
	document.getElementById("locationclosemodal").addEventListener("click", closeModal);
	// 오버레이 클릭 시 모달 닫기
	document.getElementById("locationoverlay").addEventListener("click", closeModal);

	function resetSalesForm() {
		document.getElementById("name").value = '';
		document.getElementById("email").value = '';
		document.getElementById("tel").value = '';
		document.getElementById("address").value = '';
		document.getElementById("detail_address").value = '';
		document.getElementById("h2").innerText = "거래처 등록"; // 타이틀 초기화
		document.getElementById("btn_div").innerHTML = `<input type="button" value="등록" class="p_button p_button_color2" id="add_btn">`;

		document.querySelector("#add_btn").addEventListener("click", function(event) {
			event.preventDefault();
			const formData = new FormData(sales_frm);

			const name = formData.get("name");
			const tel = formData.get("tel");
			const email = formData.get("email");
			const address = formData.get("address");
			const detailAddress = formData.get("detail_address");

			if (!name) {
				alert("거래처명을 입력하세요");
				return;
			}
			if (!tel) {
				alert("연락처를 입력하세요");
				return;
			}
			if (isNaN(tel)) {
				alert("연락처는 숫자만 가능합니다.");
				return;
			}
			if (!email) {
				alert("이메일을 입력하세요");
				return;
			}
			// 이메일 형식 검사
			const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
			if (!emailPattern.test(email)) {
				alert("올바른 이메일 형식을 입력하세요");
				return;
			}
			if (!address) {
				alert("주소를 입력하세요");
				return;
			}
			// detailAddress가 있을 경우에만 추가
			if (detailAddress) {
				formData.set("address", `${address} (${detailAddress})`);
			} else {
				formData.set("address", address);
			}

			fetch("./addSales", {
				method: "post",
				body: formData
			})
				.then(response => response.text())
				.then(data => {
					if (data === "ok") {
						alert("거래처가 등록되었습니다.");
						window.location.reload();
					} else {
						alert("거래처 등록중 오류가 발생하였습니다.");
					}
				})
				.catch(error => {
					console.log(error);
					alert("거래처 등록중 오류가 발생하였습니다.");
				})
		})
	}

	salesFrm.addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 submit 동작 방지
	});

});
