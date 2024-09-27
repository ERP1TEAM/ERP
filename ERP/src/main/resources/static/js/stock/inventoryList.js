document.addEventListener('DOMContentLoaded', function() {
	var inventorylistTotalPages = 1;
    var inventorylistStartPage = 0;
    var inventorylistEndPage = 0;
    const inventorylistPageSize = 3; // 페이지 번호 그룹 크기 설정
    
    const getinventorylistQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }
    
    let inventorylistP = parseInt(getinventorylistQueryParam("p")) || 1;
    let inventorylistSearchCode = getinventorylistQueryParam("code") || '1';  // 검색 코드
    let inventorylistSearchWord = getinventorylistQueryParam("word") || '';  // 검색어
    
    document.getElementById("inventorylistSearchtype").value = inventorylistSearchCode;
    document.getElementById("inventorylistSearch").value = inventorylistSearchWord;

	 // 페이징 함수를 전역으로 설정
    window.inventorylistPaging = function(p = inventorylistP, code = inventorylistSearchCode, word = inventorylistSearchWord) {
        inventorylistmain(p, code, word);
    }

    window.inventorylistPgNext = function() {
        inventorylistmain(inventorylistEndPage + 1, inventorylistSearchCode, inventorylistSearchWord);
    }

    window.inventorylistPgPrev = function() {
        inventorylistmain(inventorylistStartPage - 1, inventorylistSearchCode, inventorylistSearchWord);
    }

function inventorylistmain(pno, code = '', word = ''){
	fetch(`/main/stock/viewproductstocks/${pno}?code=${code}&word=${word}`,{
		method:'GET',
		headers:{
				'Content-Type':'application/json',
		}	
	})
	.then(response=>response.json())
	.then(data=>{
		let inventorylisttbody=document.querySelector('#inventorylisttbody');
    	inventorylisttbody.innerHTML='';
		
		const items = data.content;
		inventorylistTotalPages = data.totalPages;
		
    	items.forEach(function(inventorylist){
		
		let inventorylistmemo;
		if(inventorylist.memo){
			inventorylistmemo = inventorylist.memo;
		}else{
			inventorylistmemo='';
		}
		
		let inventorylistuseFlag;
                if (inventorylist.useFlag == 'Y') {
                    inventorylistuseFlag = '사용';
                } else if (inventorylist.useFlag == 'N') {
                    inventorylistuseFlag = '미사용';
                }
        let inventorylistth = `<tr class="odd gradeX">
                    <td>${inventorylist.productCode}</td>
                    <td>${inventorylist.productName}</td>
                    <td>${inventorylist.supplierCode}</td>
                    <td>${inventorylist.locationCode}</td>
                    <td>${inventorylist.classificationCode}</td>
                    <td>${inventorylist.price}</td>
                    <td>${inventorylist.totalQty}</td>
                    <td>${inventorylist.availableQty}</td>
                    <td>${inventorylist.unavailableQty}</td>
                    <td>${inventorylist.safetyQty}</td>
                    <td>${inventorylistuseFlag}</td>
                    <td>${inventorylist.manager}</td>
                    <td>${inventorylistmemo}</td>
                    <td><input type="button" value="수정" class="locationlistmodifybtn"></td>
                 </tr>`;
			inventorylisttbody.innerHTML +=inventorylistth;
		}); 
		const inventorylistPaging = document.getElementById("inventorylistPaging");
		inventorylistPaging.innerHTML = '';
		
		 // 페이지 그룹의 시작과 끝 계산
         inventorylistStartPage = Math.floor((pno - 1) / inventorylistPageSize) * inventorylistPageSize+ 1;
         inventorylistEndPage = Math.min(inventorylistStartPage + inventorylistPageSize- 1, inventorylistTotalPages);

         // 페이징 HTML 생성
         let paginationHTML = `<ul class="pagination">`;

         // 'Precious' 링크 추가
         if ( inventorylistStartPage >  inventorylistPageSize) {
        	 paginationHTML += `
        	 <li class="page-item"><a class="page-link" aria-label="Previous" onclick=" inventorylistPgPrev()">
        	 <span aria-hidden="true">&laquo;</span>
        	 </a></li>`;
             }
         // 페이지 번호 링크 추가
                for (let i =  inventorylistStartPage; i <=  inventorylistEndPage; i++) {
                    const className = pno == i ? 'page-item current-page' : 'page-item';
                    paginationHTML += `
                        <li class="${className}"><a class="page-link" onclick=" inventorylistPaging(${i})">${i}</a></li>
                    `;
                }

                // 'Next' 링크 추가
                if ( inventorylistEndPage <  inventorylistTotalPages) {
                    paginationHTML += `
                        <li class="page-item"><a class="page-link" aria-label="Next" onclick=" inventorylistPgNext()">
                            <span aria-hidden="true">&raquo;</span>
                        </a></li>
                    `;
                }

                paginationHTML += `</ul>`;

                // 페이징 HTML을 페이지에 삽입
                inventorylistPaging.innerHTML = paginationHTML;
				
                // URL 업데이트 (검색 조건도 포함)
                if(word == ""){
	                history.replaceState({}, '', location.pathname + `?p=${pno}`);			
				}else{
					history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
				}  
		})
		.catch(function(error){
			alert("error");
		});
}
inventorylistmain(inventorylistP, inventorylistSearchCode, inventorylistSearchWord);

//검색
document.getElementById("inventorylist_form").addEventListener("submit", function(event) {
event.preventDefault(); // 기본 폼 제출 방지
inventorylistSearchCode = document.getElementById("inventorylistSearchtype").value;
inventorylistSearchWord = document.getElementById("inventorylistSearch").value;
inventorylistPaging(1, inventorylistSearchCode, inventorylistSearchWord); // 검색 후 첫 페이지부터 시작	
})
});