document.addEventListener('DOMContentLoaded', function() {
	var stocklistTotalpages = 1;
    var stocklistStartPage = 0;
    var stocklistEndPage = 0;
    const stocklistPageSize = 3; // 페이지 번호 그룹 크기 설정
    
    const getstocklistQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }
    
    let stocklistP = parseInt(getstocklistQueryParam("p")) || 1;
    let stocklistSearchCode = getstocklistQueryParam("code") || '1';  // 검색 코드
    let stocklistSearchWord = getstocklistQueryParam("word") || '';  // 검색어
    
    document.getElementById("stocklistSearchtype").value = stocklistSearchCode;
    document.getElementById("stocklistSearch").value = stocklistSearchWord;

	 // 페이징 함수를 전역으로 설정
    window.stocklistPaging = function(p = stocklistP, code = stocklistSearchCode, word = stocklistSearchWord) {
        stocklistmain(p, code, word);
    }

    window.stocklistPgNext = function() {
        stocklistmain(stocklistEndPage + 1, stocklistSearchCode, stocklistSearchWord);
    }

    window.stocklistPgPrev = function() {
        stocklistmain(stocklistStartPage - 1, stocklistSearchCode, stocklistSearchWord);
    }

function stocklistmain(pno, code = '', word = ''){
	fetch(`/main/stock/viewproductstocks/${pno}?code=${code}&word=${word}`,{
		method:'GET',
		headers:{
				'Content-Type':'application/json',
		}	
	})
	.then(response=>response.json())
	.then(data=>{
		let stocklisttbody=document.querySelector('#stocklisttbody');
    	stocklisttbody.innerHTML='';
		
		const items = data.content;
		stocklistTotalpages = data.totalPages;
		
    	items.forEach(function(stocklist){
		
        let stocklistth = `<tr class="odd gradeX">
                    <td style="text-align:center;">${stocklist.productCode}</td>
                    <td>${stocklist.productName}</td>
                    <td style="text-align:center;">${stocklist.supplierCode}</td>
                    <td>${stocklist.supplierName}</td>
                    <td style="text-align:right;">${stocklist.totalQty}</td>
                    <td style="text-align:right;">${stocklist.availableQty}</td>
                    <td style="text-align:right;">${stocklist.unavailableQty}</td>
                    <td style="text-align:right;">${stocklist.safetyQty}</td>
                    <td style="text-align:center;"><input type="button" value="상세보기" class="dailyinventorybtn" data-product-code="${stocklist.productCode}"></td>
                    <td style="text-align:center;"><input type="button" value="설정" class="safetyqtysetting"></td>
                 </tr>`;
			stocklisttbody.innerHTML +=stocklistth;
		}); 
		const stocklistPaging = document.getElementById("stocklistPaging");
		stocklistPaging.innerHTML = '';
		
		 // 페이지 그룹의 시작과 끝 계산
         stocklistStartPage = Math.floor((pno - 1) / stocklistPageSize) * stocklistPageSize+ 1;
         stocklistEndPage = Math.min(stocklistStartPage + stocklistPageSize- 1, stocklistTotalpages);

         // 페이징 HTML 생성
         let paginationHTML = `<ul class="pagination">`;

         if ( stocklistStartPage >  stocklistPageSize) {
        	 paginationHTML += `
        	 <li class="page-item"><a class="page-link" aria-label="Previous" onclick="stocklistPgPrev()">
        	 <span aria-hidden="true">&laquo;</span>
        	 </a></li>`;
             }
         // 페이지 번호 링크 추가
                for (let i =  stocklistStartPage; i <=  stocklistEndPage; i++) {
                    const className = pno == i ? 'page-item current-page' : 'page-item';
                    paginationHTML += `
                        <li class="${className}"><a class="page-link" onclick="stocklistPaging(${i})">${i}</a></li>
                    `;
                }

                // 'Next' 링크 추가
                if ( stocklistEndPage <  stocklistTotalpages) {
                    paginationHTML += `
                        <li class="page-item"><a class="page-link" aria-label="Next" onclick="stocklistPgNext()">
                            <span aria-hidden="true">&raquo;</span>
                        </a></li>
                    `;
                }

                paginationHTML += `</ul>`;

                // 페이징 HTML을 페이지에 삽입
                stocklistPaging.innerHTML = paginationHTML;
				
                // URL 업데이트 (검색 조건도 포함)
                if(word == ""){
	                history.replaceState({}, '', location.pathname + `?p=${pno}`);			
				}else{
					history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
				}  
		})
		.catch(function(error){
			alert("error");
			console.log(error);
		});
}
stocklistmain(stocklistP, stocklistSearchCode, stocklistSearchWord);

//검색
document.getElementById("stocklist_form").addEventListener("submit", function(event) {
event.preventDefault(); // 기본 폼 제출 방지
stocklistSearchCode = document.getElementById("stocklistSearchtype").value;
stocklistSearchWord = document.getElementById("stocklistSearch").value;
stocklistPaging(1, stocklistSearchCode, stocklistSearchWord); // 검색 후 첫 페이지부터 시작	
})


});