document.addEventListener('DOMContentLoaded', function() {
    var inventorymanagementTotalPages = 1;
    var inventorymanagementStartPage = 0;
    var inventorymanagementEndPage = 0;
    const inventorymanagementPageSize = 5; // 페이지 번호 그룹 크기 설정

    const getinventorymanagementQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    };

    let inventorymanagementP = parseInt(getinventorymanagementQueryParam("p")) || 1;
    let inventorymanagementSearchCode = getinventorymanagementQueryParam("code") || '1'; // 검색 코드
    let inventorymanagementSearchWord = getinventorymanagementQueryParam("word") || ''; // 검색어

    document.getElementById("inventorymanagementSearchtype").value = inventorymanagementSearchCode;
    document.getElementById("inventorymanagementSearch").value = inventorymanagementSearchWord;

    window.inventorymanagementPaging = function(p = inventorymanagementP, code = inventorymanagementSearchCode, word = inventorymanagementSearchWord) {
        inventorymanagementmain(p, code, word);
    };

    window.inventorymanagementPgNext = function() {
        inventorymanagementmain(inventorymanagementEndPage + 1, inventorymanagementSearchCode, inventorymanagementSearchWord);
    };

    window.inventorymanagementPgPrev = function() {
        inventorymanagementmain(inventorymanagementStartPage - 1, inventorymanagementSearchCode, inventorymanagementSearchWord);
    };

    function inventorymanagementmain(pno, code = '', word = '') {
        fetch(`/main/stock/inventorymanagement/${pno}?code=${code}&word=${word}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            let inventorymanagementtbody = document.querySelector('#inventorymanagementtbody');
            inventorymanagementtbody.innerHTML = '';

            const items = data.inventoryData.content;
            const categories = data.categories;

            inventorymanagementTotalPages = data.inventoryData.totalPages;

            items.forEach(function(inventorymanagement) {
                let categoryname = categories.find(category => category.code == inventorymanagement.classificationCode);
                let locationCode = inventorymanagement.locationCode || '';
                let price = (inventorymanagement.price || 0).toLocaleString();
                let useFlag = inventorymanagement.useFlag;
                if (useFlag == 'Y') {
                    useFlag = '사용';
                } else {
                    useFlag = '미사용';
                }

                let inventorymanagementth = `
                    <tr class="odd gradeX">
                        <td style="text-align:center;">${inventorymanagement.productCode}</td>
                        <td>${inventorymanagement.productName}</td>
                        <td style="text-align:center;">${inventorymanagement.supplierCode}</td>
                        <td>${inventorymanagement.supplierName}</td>
                        <td style="text-align:center;">${locationCode}</td>
                        <td style="text-align:center;">${inventorymanagement.classificationCode}</td>
                        <td>${categoryname.mainName}(${categoryname.mainCode})</td>
                        <td>${categoryname.subName}(${categoryname.subCode})</td>
                        <td style="text-align:right;">${price}</td>
                        <td style="text-align:center;">${useFlag}</td>
                        <td style="text-align:center;"><input type="button" value="수정" class="inventorymanagementmodifybtn" data-product-code="${inventorymanagement.productCode}"></td>
                    </tr>`;
                inventorymanagementtbody.innerHTML += inventorymanagementth;
            });

            // 모달 열기
            document.querySelectorAll('.inventorymanagementmodifybtn').forEach(button => {
                button.addEventListener('click', function() {
                    let productCode = this.getAttribute('data-product-code');
                    document.querySelector('#inventoryManagementmodal').style.display = 'block'; // querySelector로 id 선택
                    document.querySelector('#inventoryManagementoverlay').style.display = 'block'; // 오버레이도 표시
                    document.querySelector('#inventorymodifycode').value = productCode;

                    fetch(`/main/stock/inventorymodify/${productCode}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    })
                    .then(response => response.json())
                    .then(data => {
						
						console.log(data);
                        document.querySelector('#inventorymodifycode').value = data.inventoryData.productCode;
                        document.querySelector('#inventorymodifyname').value = data.inventoryData.productName;
                        document.querySelector('#inventorysuppliermodifyname').value = data.inventoryData.supplierName;
                        document.querySelector('#inventorymodifymaincategory').value = data.mainCategoryName;
                        document.querySelector('#inventorymodifysubcategory').value = data.subCategoryName;
                        document.querySelector('#inventorymodifyprice').value = data.inventoryData.price;

                        if (data.useFlag === 'Y') {
                            document.querySelector('input[name="inventorymodifyuseflag"][value="Y"]').checked = true;
                        } else {
                            document.querySelector('input[name="inventorymodifyuseflag"][value="N"]').checked = true;
                        }
                    })
                    .catch(error => {
                        console.error("데이터를 가져오는데 실패했습니다.", error);
                    });
                });
            });

            // 페이징 설정
            const inventorymanagementPaging = document.getElementById("inventoryManagementPaging");
            inventorymanagementPaging.innerHTML = '';

            // 페이지 그룹의 시작과 끝 계산
            inventorymanagementStartPage = Math.floor((pno - 1) / inventorymanagementPageSize) * inventorymanagementPageSize + 1;
            inventorymanagementEndPage = Math.min(inventorymanagementStartPage + inventorymanagementPageSize - 1, inventorymanagementTotalPages);

            // 페이징 HTML 생성
            let paginationHTML = `<ul class="pagination">`;

            // 'Previous' 링크 추가
            if (inventorymanagementStartPage > inventorymanagementPageSize) {
                paginationHTML += `
                    <li class="page-item"><a class="page-link" aria-label="Previous" onclick="inventorymanagementPgPrev()">
                        <span aria-hidden="true">&laquo;</span>
                    </a></li>`;
            }

            // 페이지 번호 링크 추가
            for (let i = inventorymanagementStartPage; i <= inventorymanagementEndPage; i++) {
                const className = pno == i ? 'page-item current-page' : 'page-item';
                paginationHTML += `
                    <li class="${className}"><a class="page-link" onclick="inventorymanagementPaging(${i})">${i}</a></li>`;
            }

            // 'Next' 링크 추가
            if (inventorymanagementEndPage < inventorymanagementTotalPages) {
                paginationHTML += `
                    <li class="page-item"><a class="page-link" aria-label="Next" onclick="inventorymanagementPgNext()">
                        <span aria-hidden="true">&raquo;</span>
                    </a></li>`;
            }

            paginationHTML += `</ul>`;
            inventorymanagementPaging.innerHTML = paginationHTML;

            // URL 업데이트 (검색 조건도 포함)
            if (word === "") {
                history.replaceState({}, '', location.pathname + `?p=${pno}`);
            } else {
                history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
            }
        })
        .catch(function(error) {
            alert("error");
        });
    }

    inventorymanagementmain(inventorymanagementP, inventorymanagementSearchCode, inventorymanagementSearchWord);

    // 검색
    document.getElementById("inventoryManagement_form").addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 폼 제출 방지
        inventorymanagementSearchCode = document.getElementById("inventorymanagementSearchtype").value;
        inventorymanagementSearchWord = document.getElementById("inventorymanagementSearch").value;
        inventorymanagementPaging(1, inventorymanagementSearchCode, inventorymanagementSearchWord);
    });

    // 초기화 버튼
    document.querySelector("#inventorymanagementresetbtn").addEventListener("click", function() {
        inventorymanagementSearchCode = '1';
        inventorymanagementSearchWord = '';
        document.querySelector("#inventorymanagementSearchtype").value = inventorymanagementSearchCode;
        document.querySelector("#inventorymanagementSearch").value = inventorymanagementSearchWord;
        inventorymanagementPaging(1, inventorymanagementSearchCode, inventorymanagementSearchWord);
    });

    // 모달 닫기
    document.querySelector('#inventoryManagementclosemodal').addEventListener('click', function() {
        document.querySelector('#inventoryManagementmodal').style.display = 'none';
        document.querySelector('#inventoryManagementoverlay').style.display = 'none';
    });
    document.querySelector('#inventorymodifycancle').addEventListener('click', function() {
        document.querySelector('#inventoryManagementmodal').style.display = 'none';
        document.querySelector('#inventoryManagementoverlay').style.display = 'none';
    });

    // 오버레이 클릭 시 모달 닫기
    document.querySelector('#inventoryManagementoverlay').addEventListener('click', function() {
        document.querySelector('#inventoryManagementmodal').style.display = 'none';
        document.querySelector('#inventoryManagementoverlay').style.display = 'none';
    });
});
