document.addEventListener('DOMContentLoaded',function(){

//카테고리 코드 자동 생성
const categorymainCode = document.getElementById('categorymainCode');
const categorysubCode = document.getElementById('categorysubCode');
const categoryCode = document.getElementById('categoryCode');

function updateCategoryCode() {
const categorymainCodeval = categorymainCode.value.trim();
const categorysubCodeval = categorysubCode.value.trim();

// 대메뉴 코드와 소메뉴 코드가 모두 입력되면 카테고리 코드에 값을 설정
if (categorymainCodeval && categorysubCodeval) {
const autocategorycodeval = `${categorymainCodeval}${categorysubCodeval}`;
categoryCode.value = autocategorycodeval;
} else {
// 대메뉴나 소메뉴 코드 중 하나가 비어 있으면 카테고리 코드를 빈값으로 설정
categoryCode.value = '';
}
}
// 대메뉴 코드, 소메뉴 코드에 변화가 있을 때 카테고리 코드를 자동 업데이트
categorymainCode.addEventListener('input', updateCategoryCode);
categorysubCode.addEventListener('input', updateCategoryCode);

//카테고리 등록
document.getElementById('categoryregister').addEventListener('click', function() {
        
        const categorymainCodeval = document.getElementById('categorymainCode').value.trim();
        const categorymainNameval = document.getElementById('categorymainName').value.trim();
        const categorysubCodeval = document.getElementById('categorysubCode').value.trim();
        const categorysubNameval = document.getElementById('categorysubName').value.trim();
        const categorymemoval = document.getElementById('categorymemo').value.trim();
        const categoryuseflagval = document.querySelector('input[name="categoryuseflag"]:checked').value;
        
        const autocategorycodeval = `${categorymainCodeval}${categorysubCodeval}`;
        
        if (!categorymainCodeval || !categorysubCodeval) {
        alert('대메뉴코드와 소메뉴코드 모두 입력해주세요.');
        return false;
    	}
    	else if(!categorymainNameval || !categorysubNameval){
		alert('대메뉴이름과 소메뉴이름 모두 입력해주세요.');
		return false;	
		}
        
        const categoryData = {
            code: autocategorycodeval,
            mainCode: categorymainCodeval,
            mainName: categorymainNameval,
            subCode: categorysubCodeval,
            subName: categorysubNameval,
            useFlag: categoryuseflagval,
            memo: categorymemoval
        };
        
        fetch('/main/stock/categories', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(categoryData),
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }else if(response.status == 409){
				return response.text();
				}else{
				alert('카테고리 등록에 실패했습니다.');
			}
        })
        .then(data => {
            alert(data);
            if(data=='카테고리가 등록되었습니다.'){
              document.getElementById('categoryinmodal').style.display = 'none';
              document.getElementById('categorylistmodal').style.display = 'block';
              categorymainmodal();
              }
        })
        .catch(error => {
            alert('오류가 발생했습니다.');
        });
    });

// 카테고리 수정 시 코드 동적 업데이트
const categoryModifyMainCode = document.getElementById('categoryModifymainCode');
const categoryModifySubCode = document.getElementById('categoryModifysubCode');
const categoryModifyCode = document.getElementById('categoryModifyCode');

function updateModifyCategoryCode() {
    const mainCode = categoryModifyMainCode.value.trim();
    const subCode = categoryModifySubCode.value.trim();

    if (mainCode && subCode) {
        const autoCode = `${mainCode}${subCode}`;
        categoryModifyCode.value = autoCode;
    } else {
        categoryModifyCode.value = '';
    }
}

categoryModifyMainCode.addEventListener('input', updateModifyCategoryCode);
categoryModifySubCode.addEventListener('input', updateModifyCategoryCode);

//카테고리 수정 틀 갖고오기
document.querySelector('#categorytbody').addEventListener('click', function(event) {
if (event.target && event.target.classList.contains('categorymodifybtn')) {
    let categoryCode = event.target.closest('tr').querySelector('.checkbox').value;
    fetch(`/main/stock/category/${categoryCode}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response=>response.json())
        .then(data => {
			console.log(data);
			document.getElementById('categoryModifyCode').value = data.code;
            document.getElementById('categoryModifymainCode').value =  data.mainCode;
            document.getElementById('categoryModifymainName').value = data.mainName;
            document.getElementById('categoryModifysubCode').value = data.subCode;
            document.getElementById('categoryModifysubName').value = data.subName;
            document.getElementById('categoryModifymemo').value = data.memo;
            
             if (data.useFlag === 'Y') {
                  document.querySelector('input[name="categoryModifyuseflag"][value="Y"]').checked = true;
             } else {
                  document.querySelector('input[name="ccategoryModifyuseflag"][value="N"]').checked = true;
             }
            document.getElementById('categorymodifymodal').style.display = 'block';
            document.getElementById('categoryoverlay').style.display = 'block';
		})
		.catch(error => {
            console.error('Error fetching warehouse info:', error);
            alert('카테고리 정보를 불러오는 데 실패했습니다.');
        });
    }
});

//카테고리 페이징
var categoryTotalPages = 1;
var categoryStartPage = 0;
var categoryEndPage = 0;
const categoryPageSize = 5; // 페이지 번호 그룹 크기 설정

const getCategoryQueryParam = (param) => {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
}

let categoryP = parseInt(getCategoryQueryParam("p")) || 1;
let categorySearchCode = getCategoryQueryParam("code") || '1';  // 검색 코드
let categorySearchWord = getCategoryQueryParam("word") || '';  // 검색어
document.getElementById("categorySearchtype").value = categorySearchCode;
document.getElementById("categorySearch").value = categorySearchWord;

// 페이징 함수를 전역으로 설정
window.categoryPaging = function(p = categoryP, code = categorySearchCode, word = categorySearchWord) {
      categorymainmodal(p, code, word);
}

window.categoryPgNext = function() {
      categorymainmodal(categoryEndPage + 1,categorySearchCode, categorySearchWord);
}

window.categoryPgPrev = function() {
      categorymainmodal(categoryStartPage - 1, categorySearchCode, categorySearchWord);
}
//카테고리 리스트
function categorymainmodal(pno, code = '', word = '') {
    fetch(`/main/stock/categories/${pno}?code=${code}&word=${word}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => response.json())
    .then(data => {
        let categorytbody = document.querySelector('#categorytbody');
        categorytbody.innerHTML = '';
		
		const items = data.content;
		categoryTotalPages = data.totalPages;
		
        items.forEach(function(category) {
           
            let categorymemo;
			if(category.memo){
				categorymemo = category.memo;
			}else{
				categorymemo='';
			}
			
			let categoryuseFlag;
                if (category.useFlag == 'Y') {
                    categoryuseFlag = '사용';
                } else if (category.useFlag == 'N') {
                    categoryuseFlag = '미사용';
                }
			
            
            let categoryth = `<tr class="odd gradeX">
                        <th><input type="checkbox" class="checkbox" value="${category.code}"></th>
                        <td>${category.code}</td>
                        <td>${category.mainCode}</td>
                        <td>${category.mainName}</td>
                        <td>${category.subCode}</td>
                        <td>${category.subName}</td>
                        <td>${categoryuseFlag}</td>
                        <td>${categorymemo}</td>
                        <td><input type="button" value="수정" class="categorymodifybtn"></td>
                      </tr>`;
            categorytbody.innerHTML += categoryth;
        });
        
        const categoryPaging = document.getElementById("categorypaging");
		categoryPaging.innerHTML = '';
        
        // 페이지 그룹의 시작과 끝 계산
         categoryStartPage = Math.floor((pno - 1) / categoryPageSize) * categoryPageSize + 1;
         categoryEndPage = Math.min(categoryStartPage + categoryPageSize - 1, categoryTotalPages);

         // 페이징 HTML 생성
         let paginationHTML = `<ul class="pagination">`;

         // 'Precious' 링크 추가
         if (categoryStartPage > categoryPageSize) {
        	 paginationHTML += `
        	 <li class="page-item"><a class="page-link" aria-label="Previous" onclick="categoryPgPrev()">
        	 <span aria-hidden="true">&laquo;</span>
        	 </a></li>`;
             }
         // 페이지 번호 링크 추가
                for (let i = categoryStartPage; i <= categoryEndPage; i++) {
                    const className = pno === i ? 'page-item current-page' : 'page-item';
                    paginationHTML += `
                        <li class="${className}"><a class="page-link" onclick="categoryPaging(${i})">${i}</a></li>
                    `;
                }

                // 'Next' 링크 추가
                if (categoryEndPage < categoryTotalPages) {
                    paginationHTML += `
                        <li class="page-item"><a class="page-link" aria-label="Next" onclick="categoryPgNext()">
                            <span aria-hidden="true">&raquo;</span>
                        </a></li>
                    `;
                }

                paginationHTML += `</ul>`;

                // 페이징 HTML을 페이지에 삽입
                categoryPaging.innerHTML = paginationHTML;
				
                // URL 업데이트 (검색 조건도 포함)
                if(word === ""){
	                history.replaceState({}, '', location.pathname + `?p=${pno}`);			
				}else{
					history.replaceState({}, '', location.pathname + `?p=${pno}&code=${code}&word=${word}`);
				}
        document.getElementById('categoryoverlay').style.display = 'block';
        document.getElementById('categorylistmodal').style.display = 'block';
        document.body.style.overflow = 'hidden';
    })
    .catch(function(error) {
        alert('카테고리 모달을 불러오는 데 오류가 발생했습니다.');
        console.log(error);
    });
}

//검색
document.getElementById("category_form").addEventListener("submit", function(event) {
event.preventDefault(); // 기본 폼 제출 방지
categorySearchCode = document.getElementById("categorySearchtype").value;
categorySearchWord = document.getElementById("categorySearch").value;
categoryPaging(1, categorySearchCode, categorySearchWord); // 검색 후 첫 페이지부터 시작				
    });

//카테고리 등록 취소
document.getElementById('categoryinback').addEventListener('click',function(){
    document.getElementById('categoryinmodal').style.display = 'none';
    document.getElementById('categorylistmodal').style.display = 'block';
});
//카테고리 수정 취소
document.getElementById('categorymodifyback').addEventListener('click',function(){
    document.getElementById('categorymodifymodal').style.display = 'none';
    document.getElementById('categorylistmodal').style.display = 'block';
});

//카테고리 등록 모달 출력
document.getElementById('categoryin').addEventListener('click', function() {
        document.getElementById('categorylistmodal').style.display = 'none';
        document.getElementById('categoryinmodal').style.display = 'block';
});

//카테고리 모달 열기	
document.querySelectorAll(".categorybtn").forEach(function(button) {
	button.addEventListener("click", function() {
    categorymainmodal(categoryP, categorySearchCode, categorySearchWord);
	});
});
//카테고리 X버튼으로 모달 닫기
document.querySelectorAll(".categoryclosemodal").forEach(function(button) {
    button.addEventListener("click", function() {
        document.getElementById("categoryoverlay").style.display = "none";
        document.getElementById("categorylistmodal").style.display = "none";
        document.getElementById("categoryinmodal").style.display = "none";
        document.getElementById("categorymodifymodal").style.display = "none";
        document.body.style.overflow = "auto";
    });
});

//카테고리 모달 외부를 클릭하면 모달 닫기
window.addEventListener('click', function(event) {
    const categorylistmodal = document.getElementById('categorylistmodal');
    const categoryinmodal = document.getElementById('categoryinmodal');
    const categorymodifymodal = document.getElementById('categorymodifymodal');
    const categoryoverlay = document.getElementById('categoryoverlay');
    
    if (event.target == categoryoverlay) {
        categorylistmodal.style.display = 'none';
        categoryinmodal.style.display = 'none';
        categorymodifymodal.style.display = 'none';
        categoryoverlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    }

});

});    