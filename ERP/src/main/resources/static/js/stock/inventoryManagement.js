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
                        <td style="text-align:center;">
                        <input type="button" value="수정" 
                        class="inventorymanagementmodifybtn" 
                        data-product-code="${inventorymanagement.productCode}"
                        data-maincatename="${categoryname.mainName}" 
 			            data-subcatename="${categoryname.subName}"
 			            data-maincatecode="${categoryname.mainCode}"
           				data-subcatecode="${categoryname.subCode}">
                        </td>
                    </tr>`;
                inventorymanagementtbody.innerHTML += inventorymanagementth;
            });

            // 모달 열기
            document.querySelectorAll('.inventorymanagementmodifybtn').forEach(button => {
                button.addEventListener('click', function() {
                    let productCode = this.getAttribute('data-product-code');
                    let maincatename = this.getAttribute('data-maincatename');
                    let subcatename = this.getAttribute('data-subcatename');
                    let maincatecode = this.getAttribute('data-maincatecode'); // 대메뉴 코드 가져오기
  				    let subcatecode = this.getAttribute('data-subcatecode'); 
                   
                    document.querySelector('#inventoryManagementmodal').style.display = 'block';
                    document.querySelector('#inventoryManagementoverlay').style.display = 'block'; 
                    document.querySelector('#inventorymodifycode').value = productCode;
					document.querySelector('#inventorymodifymaincategorycode').value = maincatecode; 
       				document.querySelector('#inventorymodifysubcategorycode').value = subcatecode; 
					
					document.querySelector('#inventorymodifydelbtn').setAttribute('data-product-code', productCode);
					 
                    fetch(`/main/stock/inventorymodify/${productCode}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    })
                    .then(response => response.json())
                    .then(data => {
                        document.querySelector('#inventorymodifycode').value = productCode;
                        document.querySelector('#inventorymodifyname').value = data.productName;
                        document.querySelector('#inventorysuppliermodifyname').value = data.supplierName;
                        document.querySelector('#inventorymodifymaincategory').value = maincatename; 
                        document.querySelector('#inventorymodifysubcategory').value = subcatename ;
                        document.querySelector('#inventorymodifyprice').value = data.price;

                        if (data.useFlag == 'Y') {
                            document.querySelector('input[name="inventorymodifyuseflag"][value="Y"]').checked = true;
                        } else {
                            document.querySelector('input[name="inventorymodifyuseflag"][value="N"]').checked = true;
                        }
                    })
                    .catch(error => {
                        alert("상품 정보를 가져오는데 실패했습니다.");
                    });
                });
            });
            
            //데이터 수정
            document.querySelector('#inventorymodifysave').addEventListener('click', function() {
		        const productCode = document.querySelector('#inventorymodifycode').value;
		        const productName = document.querySelector('#inventorymodifyname').value;
		        const mainCategoryCode = document.querySelector('#inventorymodifymaincategorycode').value;
    			const subCategoryCode = document.querySelector('#inventorymodifysubcategorycode').value;
		        const price = parseInt(document.querySelector('#inventorymodifyprice').value, 10); 
		        const useFlag = document.querySelector('input[name="inventorymodifyuseflag"]:checked').value;
				
				const classification_code = `${mainCategoryCode}${subCategoryCode}`;
		        
		        if (!productName || !price) {
			            alert("모든 값을 채워주세요.");
			            return;
			        }
			        
			        const numberPattern = /^[0-9]+$/; 
			        
				   if (!numberPattern.test(price)) {
					    alert("상품 가격은 숫자만 입력 가능합니다.");
					    return;
					}
					if (isNaN(price)) {
					    alert("상품 가격은 유효한 숫자여야 합니다.");
					    return;
					}
        
		        
		        
		        fetch(`/main/stock/inventorymodifysave/${productCode}`, {
		            method: 'PUT',
		            headers: {
		                'Content-Type': 'application/json'
		            },
		            body: JSON.stringify({
		               	name: productName,
		                classificationCode: classification_code,
		                price: price,
		                useFlag: useFlag
		            })
		        })
		        .then(response => {
		            if (response.ok) {
		                alert('상품이 수정되었습니다.');
		                document.querySelector('#inventoryManagementmodal').style.display = 'none';
        				document.querySelector('#inventoryManagementoverlay').style.display = 'none';
		    	        
		    	        window.location.reload();

		            } else {
		                alert('상품 수정에 실패했습니다.');
		            }
		        })
		        .catch(error => {
		            alert('서버 오류로 수정에 실패했습니다.');
		        });
	    	});
	    
            
            //데이터 삭제
			document.querySelector('#inventorymodifydelbtn').addEventListener('click', function() {
			
			let productCode = this.getAttribute('data-product-code');		    
		    if (confirm("상품을 삭제하시겠습니까? (재고가 0개일 때만 삭제 가능합니다.)")) {
		        fetch(`/main/stock/inventorydelete/${productCode}`, {
		            method: 'DELETE',
		            headers: {
		                'Content-Type': 'application/json'
		            },
		        })
		        .then(response => {
		            if (response.ok) {
		                alert('상품이 삭제되었습니다.');
		                document.querySelector('#inventoryManagementmodal').style.display = 'none';
        				document.querySelector('#inventoryManagementoverlay').style.display = 'none';
		    	        
		    	        window.location.reload();
		    	        
		            } else {
		                alert('상품 삭제에 실패했습니다.');
		            }
		        })
		        .catch(error => {
		            alert('서버 오류로 삭제에 실패했습니다.');
		        });
		    }
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
            if (word == "") {
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
