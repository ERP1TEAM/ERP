document.addEventListener('DOMContentLoaded', function() {

//창고모달열기
document.getElementById('warehousebtn').addEventListener('click',function(){
warehousemainmodal();
});

    document.getElementById('warehousein').addEventListener('click', function() {
        fetch('/main/stock/warehousein-modal', {
            method: 'GET',
            cache: 'no-cache'
        })
        .then(response => response.text())
        .then(data => {
       
            let warehouseinmodal = document.querySelector('#warehouseinmodal');
            warehouseinmodal.style.display = 'block';
            document.getElementById('warehouselistmodal').style.display = 'none';
            
            document.getElementById('warehouseCode').value = '';
       		document.getElementById('warehouseName').value = '';
       		document.getElementById('warehouseMemo').value = '';
        })
        .catch(error => {
            alert("error");
        });
    });
/*
//창고 페이징
	var warehouseTotalPages = 1;
    var warehouseStartPage = 0;
    var warehouseEndPage = 0;
    const warehousePageSize = 3;
const getWarehouseQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
} 
let warehouseP = parseInt(getWarehouseQueryParam("p")) || 1;
let warehouseSearchCode = getWarehouseQueryParam("code") || '1';  // 검색 코드
let warehouseSearchWord = getWarehouseQueryParam("word") || '';  // 검색어   

document.getElementById("warehouseSearchtype").value = warehouseSearchCode;
document.getElementById("warehouseSearch").value = warehouseSearchWord;

// 페이징 함수를 전역으로 설정
window.warehousePaging = function(p = warehouseP, code = warehouseSearchCode, word = warehouseSearchWord) {
      warehouselistmain(p, code, word);
}

window.warehousePgNext = function() {
      warehouselistmain(warehouseEndPage + 1, warehouseSearchCode, warehouseSearchWord);
}

window.warehousePgPrev = function() {
      warehouselistmain(warehouseStartPage - 1, warehouseSearchCode, warehouseSearchWord);
}
// 창고 리스트 조회 및 페이징 구현
    function warehouselistmain(pno, code = '', word = '') {
        fetch(`/main/stock/warehouses/${pno}?code=${code}&word=${word}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }    
        })
        .then(response => {
			console.log(response.status);
        return response.json();})
        .then(data => {
			console.log(data);
			console.log(data.totalPages);
            let warehousetbody = document.querySelector('#warehousetbody');
            warehousetbody.innerHTML = '';

            const items = data.content;
            warehouseTotalPages = data.totalPages;

            items.forEach(function(warehouse) {
                let warehousememo = warehouse.memo ? warehouse.memo : '';
                let warehouseth = `<tr class="odd gradeX">
                    <th><input type="checkbox" class="checkbox" value="${warehouse.code}"></th>
                    <td>${warehouse.code}</td>
                    <td>${warehouse.name}</td>
                    <td>${warehousememo}</td>
                    <td><input type="button" value="수정" class="warehousemodifybtn"></td>
                </tr>`;
                warehousetbody.innerHTML += warehouseth;
            });

            const pagingElement = document.getElementById("warehouselistpaging");
            pagingElement.innerHTML = '';

            // 페이지 그룹의 시작과 끝 계산
            warehouseStartPage = Math.floor((pno - 1) / warehousePageSize) * warehousePageSize + 1;
            warehouseEndPage = Math.min(warehouseStartPage + warehousePageSize - 1, warehouseTotalPages);

            // 페이징 HTML 생성
            let paginationHTML = `<ul class="pagination">`;

            if (warehouseStartPage > warehousePageSize) {
                paginationHTML += `
                <li class="page-item"><a class="page-link" aria-label="Previous" onclick="warehousePgPrev()">
                <span aria-hidden="true">&laquo;</span>
                </a></li>`;
            }

            // 페이지 번호 링크 추가
            for (let i = warehouseStartPage; i <= warehouseEndPage; i++) {
                const className = pno == i ? 'page-item current-page' : 'page-item';
                paginationHTML += `
                    <li class="${className}"><a class="page-link" onclick="warehousePaging(${i})">${i}</a></li>
                `;
            }

            // 'Next' 링크 추가
            if (warehouseEndPage < warehouseTotalPages) {
                paginationHTML += `
                    <li class="page-item"><a class="page-link" aria-label="Next" onclick="warehousePgNext()">
                        <span aria-hidden="true">&raquo;</span>
                    </a></li>
                `;
            }

            paginationHTML += `</ul>`;

            // 페이징 HTML을 페이지에 삽입
            pagingElement.innerHTML = paginationHTML;

            // URL 업데이트 (검색 조건도 포함)
            if(word == "") {
                history.replaceState({}, '', location.pathname + `?p=${pno}`);            
            } else {
                history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
            }  
        })
        .catch(function(error) {
            alert("error");
            console.log(error);
        });
    }

*/
//검색어
document.getElementById('warehouseSearchbtn').addEventListener('click', function() {
   	const warehouseSearchtype = document.getElementById('warehouseSearchtype').value;
    const warehouseSearch = document.getElementById('warehouseSearch').value.trim();
    
    let fetchUrl;
    
    if (!warehouseSearch) {
        // 검색어가 없을 경우 전체 리스트를 불러오는 URL로 설정
        fetchUrl = '/main/stock/warehouses';
    } else {
        // 검색어가 있을 경우 검색 결과를 불러오는 URL로 설정
        fetchUrl = `/main/stock/warehousesearch?warehouseSearchtype=${warehouseSearchtype}&warehouseSearch=${warehouseSearch}`;
    }
	
	fetch(fetchUrl, {
            method: 'GET',
            headers: {
				'Content-Type' : 'application/json'
			},
        })
        .then(response => response.json())
        .then(data=> {
			let warehousetbody = document.querySelector('#warehousetbody');
 		    warehousetbody.innerHTML = '';
		
        if(data.length==0){
			warehousetbody.innerHTML = '<tr><td colspan="5"> 검색 결과가 없습니다.</td></tr>';
		}else{
	data.forEach(warehouse => {
    let th = `<tr class="odd gradeX">
                  <th style="text-align:center;"><input type="checkbox" class="checkbox" value="${warehouse.code}"></th>
                  <td style="text-align:center;">${warehouse.code}</td>
                  <td>${warehouse.name}</td>
                  <td>${warehouse.memo}</td>
                  <td style="text-align:center;"><input type="button" value="수정" class="warehousemodifybtn"></td>
              </tr>`;
    warehousetbody.innerHTML += th;
        });
        }
}).catch(error =>{
	alert('오류가 발생했습니다.');
});
});

//창고등록
document.getElementById('warehouseregister').addEventListener('click', function() {
        
        const warehouseCode = document.getElementById('warehouseCode').value.trim();
        const warehouseName = document.getElementById('warehouseName').value.trim();
        const warehouseMemo = document.getElementById('warehouseMemo').value.trim();
        
        const specialCharPattern = /[^a-zA-Z0-9가-힣\s]/;
        
        if (!warehouseCode || !warehouseName) {
        alert('창고 코드와 이름을 모두 입력해주세요.');
        return false;
    	}
    	if (specialCharPattern.test(warehouseName)||specialCharPattern.test(warehousCode)) {
            alert('창고 이름과 코드에 특수문자를 사용할 수 없습니다.');
            return false;
        }

    	
        
        const warehouseData = {
            code: warehouseCode,
            name: warehouseName,
            memo: warehouseMemo
        };
        
        fetch('/main/stock/warehouses', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(warehouseData),
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }else if(response.status == 409){
				return response.text();
				}else{
				alert('창고 등록에 실패했습니다.');
			}
        })
        .then(data => {
            alert(data);
            if(data=='창고가 등록되었습니다.'){
              document.getElementById('warehouseinmodal').style.display = 'none';
              document.getElementById('warehouselistmodal').style.display = 'block';
              warehousemainmodal();
              }
        })
        .catch(error => {
            alert('오류가 발생했습니다.');
        });
    });

//창고삭제    
document.getElementById('warehousedelete').addEventListener('click',function(){
	
	let selectWarehouse = [];
    let checkboxes = document.querySelectorAll('.checkbox:checked');
    
    checkboxes.forEach(function (checkbox) {
        selectWarehouse.push(checkbox.value);
    });

    if (selectWarehouse.length == 0) {
        alert('삭제할 창고를 선택하세요.');
        return false;
    }
    
    if(!confirm("창고를 삭제하시겠습니까?")){
		return false;
	}
    
    const warehouseCodePath=selectWarehouse.join(',');
    
    fetch(`/main/stock/warehouses/${warehouseCodePath}`, {
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
            alert('창고가 삭제되었습니다.');
            warehousemainmodal();
        } else {
            alert('창고 삭제에 실패했습니다');
        }
    })
    .catch(function (error) {
        alert('오류가 발생했습니다.');
    });
}); 

//창고 수정 틀 갖고오기
document.querySelector('#warehousetbody').addEventListener('click', function(event) {
if (event.target && event.target.classList.contains('warehousemodifybtn')) {
    let warehouseCode = event.target.closest('tr').querySelector('.checkbox').value;
    fetch(`/main/stock/${warehouseCode}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response=>response.json())
        .then(data => {
			document.getElementById('warehouseMcode').value = data.code;
            document.getElementById('warehouseMname').value = data.name;
            document.getElementById('warehouseMmemo').value = data.memo;
            
            document.getElementById('warehousemodifymodal').style.display = 'block';
            document.getElementById('overlay').style.display = 'block';
		})
		.catch(error => {
            alert(' 창고 수정 페이지 ERROR!! ');
        });
    }
});

//창고수정
document.getElementById('warehouseModifybtn').addEventListener('click', function() {
        
        
        const warehouseCode = document.getElementById('warehouseMcode').value;
        const warehouseName = document.getElementById('warehouseMname').value;
		const warehouseMemo = document.getElementById('warehouseMmemo').value;

        if (!warehouseName) {
        alert('창고 이름을 입력해주세요.');
        return false;
    	}
    	const specialCharPattern = /[^a-zA-Z0-9가-힣\s]/;
        
    	if (specialCharPattern.test(warehouseName)) {
            alert('창고 이름에 특수문자를 사용할 수 없습니다.');
            return false;
        }
        
        const warehouseData = {
            name: warehouseName,
            memo: warehouseMemo
        };
        fetch(`/main/stock/${warehouseCode}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(warehouseData),
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }else if(response.status == 409){
				return response.text();
				}else{
				alert('창고 수정에 실패했습니다.');
			}
        })
        .then(data => {
            alert(data);
            if(data=='창고가 수정되었습니다.'){
              document.getElementById('warehousemodifymodal').style.display = 'none';
              document.getElementById('warehouselistmodal').style.display = 'block';
              warehousemainmodal();
              }
        })
        .catch(error => {
            alert('오류가 발생했습니다.');
        });
    });

//창고등록취소
document.getElementById('warehouseinback').addEventListener('click',function(){
    document.getElementById('warehouseinmodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'block';   // 오버레이 숨기기
});

//창고수정취소
document.getElementById('warehousemodifyback').addEventListener('click',function(){
    document.getElementById('warehousemodifymodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'block';   // 오버레이 숨기기
});

//창고리스트 출력
function warehousemainmodal(){
	fetch('/main/stock/warehouses',{
		method:'GET',
		headers:{
			'Content-Type':'application/json',
		}
	})
	.then(response=>response.json())
    .then(data=>{
		let warehousetbody=document.querySelector('#warehousetbody');
    	warehousetbody.innerHTML='';
    	
    	data.forEach(function(warehouse){
		
		let warehousememo;
		if(warehouse.memo){
			warehousememo = warehouse.memo;
		}else{
			warehousememo='';
		}
			
    	let th = `<tr class="odd gradeX">
                    <th><input type="checkbox" class="checkbox" value="${warehouse.code}"></th>
                    <td style="text-align:center;">${warehouse.code}</td>
                    <td>${warehouse.name}</td>
                    <td>${warehousememo}</td>
                    <td style="text-align:center;"><input type="button" value="수정" class="warehousemodifybtn"></td>
                 </tr>`;
			warehousetbody.innerHTML +=th;
		});
	document.getElementById('warehouseinmodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';   // 오버레이 숨기기
    document.body.style.overflow = 'hidden';
		})
		.catch(function(error){
			alert("error");
		});
        }
});

//모달닫기
document.querySelectorAll('.closemodal').forEach(function(warehouseclosebtn) {
    warehouseclosebtn.addEventListener('click',function(){
    document.getElementById('warehousemodifymodal').style.display = 'none';
    document.getElementById('warehouseinmodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
    document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
});
});

// 모달 외부를 클릭하면 모달 닫기
window.addEventListener('click', function(event) {
    const warehouselistmodal = document.getElementById('warehouselistmodal');
    const warehouseinmodal = document.getElementById('warehouseinmodal');
    const warehousemodifymodal = document.getElementById('warehousemodifymodal');
    const overlay = document.getElementById('overlay');
    
    if (event.target == overlay) {
        warehouseinmodal.style.display = 'none'; 
        warehouselistmodal.style.display = 'none';
        warehousemodifymodal.style.display = 'none';
        overlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    }
});