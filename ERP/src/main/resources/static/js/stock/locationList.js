document.addEventListener('DOMContentLoaded', function() {

	var totalPages = 1;
    var startPage = 0;
    var endPage = 0;
    const pageSize = 5; // 페이지 번호 그룹 크기 설정

    const getQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }

    let p = parseInt(getQueryParam("p")) || 1;
    let searchCode = getQueryParam("code") || '1';  // 검색 코드
    let searchWord = getQueryParam("word") || '';  // 검색어
    
    document.getElementById("locationSearchtype").value = searchCode;
    document.getElementById("locationSearch").value = searchWord;

    // 페이징 함수를 전역으로 설정
    window.paging = function(p, code = searchCode, word = searchWord) {
        locationlistmain(p, code, word);
    }

    window.pgNext = function() {
        locationlistmain(endPage + 1, searchCode, searchWord);
    }

    window.pgPrev = function() {
        locationlistmain(startPage - 1, searchCode, searchWord);
    }

	function locationlistmain(pno, code = '', word = '') {
		fetch(`/main/stock/locations/${pno}?code=${code}&word=${word}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			}
		})
			.then(response => response.json())
			.then(data => {
				let locationlisttbody = document.querySelector('#locationlisttbody');
				locationlisttbody.innerHTML = '';
				
				const items = data.content;
				totalPages = data.totalPages;
				
				items.forEach(function(locationlist) {

					let locationlistmemo;
					if (locationlist.memo) {
						locationlistmemo = locationlist.memo;
					} else {
						locationlistmemo = '';
					}

					let locationlistuseFlag;
					if (locationlist.useFlag == 'Y') {
						locationlistuseFlag = '사용';
					} else if (locationlist.useFlag == 'N') {
						locationlistuseFlag = '미사용';
					}

					let locationlistth = `<tr class="odd gradeX">
                    <th><input type="checkbox" class="checkbox" value="${locationlist.code}"></th>
                    <td>${locationlist.code}</td>
                    <td>${locationlist.warehouseCode}</td>
                    <td>${locationlist.rackCode}</td>
                    <td>${locationlist.rowCode}</td>
                    <td>${locationlist.levelCode}</td>
                    <td>${locationlistuseFlag}</td>
                    <td>${locationlistmemo}</td>
                    <td><input type="button" value="수정" class="locationlistmodifybtn"></td>
                 </tr>`;
					locationlisttbody.innerHTML += locationlistth;
				});
				
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
                if(word === ""){
	                history.replaceState({}, '', location.pathname + `?p=${pno}`);			
				}else{
					history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
				}
				
				
				
			})
			.catch(function(error) {
				alert("error");
				console.log(error);
			});
	}


	//로케이션 삭제   
	document.getElementById('locationdelete').addEventListener('click', function() {

		let selectLocation = [];
		let checkboxes = document.querySelectorAll('.checkbox:checked');

		checkboxes.forEach(function(checkbox) {
			selectLocation.push(checkbox.value);
		});

		if (selectLocation.length == 0) {
			alert('삭제할 로케이션을 선택하세요.');
			return false;
		}

		if (!confirm("로케이션을 삭제하시겠습니까?")) {
			return false;
		}

		const locationCodePath = encodeURIComponent(selectLocation.join(','));

		fetch(`/main/stock/locations/${locationCodePath}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json',
			}
		})
			.then(response => {
				return response.json();
			})
			.then(data => {
				if (data.ok) {
					alert('로케이션이 삭제되었습니다.');
					locationlistmain();
				} else {
					alert('로케이션 삭제 중 오류가 발생했습니다.');
				}
			})
			.catch(function(error) {
				console.log(error);
				alert('오류가 발생했습니다.');
			});
	});
	locationlistmain(p, searchCode, searchWord);
	
	//검색
    document.getElementById("search_form").addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 폼 제출 방지
        searchCode = document.getElementById("locationSearchtype").value;
        searchWord = document.getElementById("locationSearch").value;
		paging(1, searchCode, searchWord); // 검색 후 첫 페이지부터 시작				
    });
});