document.addEventListener('DOMContentLoaded', function() {
    var inventorySupplierlistTotalPages = 1;
    var inventorySupplierlistStartPage = 0;
    var inventorySupplierlistEndPage = 0;
    const inventorySupplierlistPageSize = 5; // 페이지 번호 그룹 크기 설정

    const getInventorySupplierlistQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }
    let inventorySupplierlistP = parseInt(getInventorySupplierlistQueryParam("p")) || 1;
    let inventorySupplierlistSearchCode = getInventorySupplierlistQueryParam("code") || '1';  // 검색 코드
    let inventorySupplierlistSearchWord = getInventorySupplierlistQueryParam("word") || '';  // 검색어
    
    let inventorysuplliercurrentPage = inventorySupplierlistP;
    
    document.getElementById("inventorySupplierSearchtype").value = inventorySupplierlistSearchCode;
    document.getElementById("inventorySupplierSearch").value = inventorySupplierlistSearchWord;
    window.inventorySupplierlistPaging = function(p = inventorySupplierlistP, code = inventorySupplierlistSearchCode, word = inventorySupplierlistSearchWord) {
        inventorysuplliercurrentPage =p;
        inventorySupplierlistMainModal(p, code, word);
    }

    window.inventorySupplierlistPgNext = function() {
        inventorySupplierlistMainModal(inventorySupplierlistEndPage + 1, inventorySupplierlistSearchCode, inventorySupplierlistSearchWord);
    }

    window.inventorySupplierlistPgPrev = function() {
        inventorySupplierlistMainModal(inventorySupplierlistStartPage - 1, inventorySupplierlistSearchCode, inventorySupplierlistSearchWord);
    }

	function convertInventorySupplierSearchCode(code) {
        if (code == '1') return '발주처코드';
        if (code == '2') return '발주처명';
        return code;
    }
    
    function inventorySupplierlistMainModal(pno, code = '', word = '') {
        code = convertInventorySupplierSearchCode(code);
        
        fetch(`/main/stock/inventorysupplierlist/${pno}?code=${code}&word=${word}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            let inventorySuppliertbody = document.querySelector('#inventorySuppliertbody');
            inventorySuppliertbody.innerHTML = '';
	
            const items = data.supplierlistdata.content;
            inventorySupplierlistTotalPages = data.supplierlistdata.totalPages;

            items.forEach(function(inventorySupplierlist) {

                let inventorySupplierlistRow = 
                `<tr class="odd gradeX"  data-code="${inventorySupplierlist.code}" data-name="${inventorySupplierlist.name}">
                    <td>${inventorySupplierlist.code}</td>
                    <td>${inventorySupplierlist.name}</td>
                </tr>`;
                inventorySuppliertbody.innerHTML += inventorySupplierlistRow;
            });
            
            document.querySelectorAll("#inventorySuppliertbody tr").forEach(row => {
   			 row.style.cursor = 'pointer';
			});

            const inventorySupplierlistPaging = document.getElementById("inventorySupplierlistPaging");
            inventorySupplierlistPaging.innerHTML = '';

            inventorySupplierlistStartPage = Math.floor((pno - 1) / inventorySupplierlistPageSize) * inventorySupplierlistPageSize + 1;
            inventorySupplierlistEndPage = Math.min(inventorySupplierlistStartPage + inventorySupplierlistPageSize - 1, inventorySupplierlistTotalPages);

            let paginationHTML = `<ul class="pagination">`;

            if (inventorySupplierlistStartPage > inventorySupplierlistPageSize) {
                paginationHTML += `
                <li class="page-item"><a class="page-link" aria-label="Previous" onclick="inventorySupplierlistPgPrev()">
                <span aria-hidden="true">&laquo;</span>
                </a></li>`;
            }
            for (let i = inventorySupplierlistStartPage; i <= inventorySupplierlistEndPage; i++) {
                const className = pno === i ? 'page-item current-page' : 'page-item';
                paginationHTML += `
                    <li class="${className}"><a class="page-link" onclick="inventorySupplierlistPaging(${i})">${i}</a></li>
                `;
            }

            if (inventorySupplierlistEndPage < inventorySupplierlistTotalPages) {
                paginationHTML += `
                    <li class="page-item"><a class="page-link" aria-label="Next" onclick="inventorySupplierlistPgNext()">
                        <span aria-hidden="true">&raquo;</span>
                    </a></li>`;
            }

            paginationHTML += `</ul>`;
			
            // 페이징 HTML을 페이지에 삽입
            inventorySupplierlistPaging.innerHTML = paginationHTML;

            // URL 업데이트 (검색 조건도 포함)
            if (word === "") {
                history.replaceState({}, '', location.pathname + `?p=${pno}`);
            } else {
                history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
            }

            document.getElementById('inventorySupplierlistoverlay').style.display = 'block';
            document.getElementById('inventorySupplierlistmodal').style.display = 'block';
            document.body.style.overflow = 'hidden';
            
           document.querySelector('#inventorySuppliertbody').addEventListener('click', function(event) {
   	 		const clickedRow = event.target.closest('tr');

    		if (clickedRow && clickedRow.hasAttribute('data-code') && clickedRow.hasAttribute('data-name')) {
      		  const selectedCode = clickedRow.getAttribute('data-code');
       		  const selectedName = clickedRow.getAttribute('data-name');

        document.getElementById("inventorySuppliercodeoption").value = selectedCode;
        document.getElementById("inventorySuppliernameoption").value = selectedName;

        document.getElementById("inventorySupplierlistoverlay").style.display = "none";
        document.getElementById("inventorySupplierlistmodal").style.display = "none";
        document.body.style.overflow = 'auto';
   				}
            });
        })
        .catch(function(error) {
            alert('공급업체 모달을 불러오는 데 오류가 발생했습니다.');
        });
    }

    // 검색
    document.getElementById("inventorySupplierlist_form").addEventListener("submit", function(event) {
        event.preventDefault();
        inventorySupplierlistSearchCode = document.getElementById("inventorySupplierSearchtype").value;
        inventorySupplierlistSearchWord = document.getElementById("inventorySupplierSearch").value;
        inventorySupplierlistPaging(1, inventorySupplierlistSearchCode, inventorySupplierlistSearchWord);
    });
    document.getElementById("inventorySupplierSearchbtn").addEventListener("click", function() {
        inventorySupplierlistSearchCode = document.getElementById("inventorySupplierSearchtype").value;
        inventorySupplierlistSearchWord = document.getElementById("inventorySupplierSearch").value;
        inventorySupplierlistPaging(1, inventorySupplierlistSearchCode, inventorySupplierlistSearchWord);
    });

 document.getElementById('inventoryInsupplierbtn').addEventListener('click', function() {
 inventorySupplierlistMainModal(inventorysuplliercurrentPage, inventorySupplierlistSearchCode, inventorySupplierlistSearchWord);
});
    document.querySelectorAll(".inventorySupplierlistclosemodal").forEach(function(button) {
        button.addEventListener("click", function() {
            document.getElementById("inventorySupplierlistoverlay").style.display = "none";
            document.getElementById("inventorySupplierlistmodal").style.display = "none";
            document.body.style.overflow = "auto";
        });
    });

    window.addEventListener('click', function(event) {
        const inventorySupplierlistmodal = document.getElementById('inventorySupplierlistmodal');
        const inventorySupplierlistoverlay = document.getElementById('inventorySupplierlistoverlay');

        if (event.target == inventorySupplierlistoverlay) {
            inventorySupplierlistmodal.style.display = 'none';
            inventorySupplierlistoverlay.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
    });
});
