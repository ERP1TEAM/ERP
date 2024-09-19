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

//카테고리 페이징
var categorytotalPages=1;
var categorystartPage = 0;
var categoryendPage = 0;
const categorypageSize = 2; // 페이지 번호 그룹 크기 설정

const getQueryParam = (param) => { //쿼리 파라미터 가져옴
	const urlParams = new URLSearchParams(window.location.search); //window.location.search: url쿼리문자열 반환 
	return urlParams.get(param);
}
let p = parseInt(getQueryParam("p")) || 1;
let searchCode = getQueryParam("code") || '가입고코드';  // 검색 코드
let searchWord = getQueryParam("word") || '';  // 검색어

document.getElementById("search_code").value = searchCode;
document.getElementById("search_word").value = searchWord;

//paging 함수를 전역으로 설정
window.paging = function(p, code = searchCode, word = searchWord) {
    tableData(p, code, word);
}
window.pgNext = function() {
    tableData(endPage + 1, searchCode, searchWord);
}
window.pgPrev = function() {
    tableData(startPage - 1, searchCode, searchWord);
}
    
    

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

//카테고리 리스트
function categorymainmodal() {
    fetch('/main/stock/categories', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => response.json())
    .then(data => {
        let categorytbody = document.querySelector('#categorytbody');
        categorytbody.innerHTML = '';

		/*
        data.forEach(function(category) {
           
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
        */
        
        document.getElementById('categoryoverlay').style.display = 'block';
        document.getElementById('categorylistmodal').style.display = 'block';
        document.body.style.overflow = 'hidden';
    })
    .catch(function(error) {
        alert('카테고리 모달을 불러오는 데 오류가 발생했습니다.');
    });
}

//카테고리 등록 취소
document.getElementById('categoryinback').addEventListener('click',function(){
    document.getElementById('categoryinmodal').style.display = 'none';
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
    categorymainmodal();
	});
});
//카테고리 X버튼으로 모달 닫기
document.querySelectorAll(".categoryclosemodal").forEach(function(button) {
    button.addEventListener("click", function() {
        document.getElementById("categoryoverlay").style.display = "none";
        document.getElementById("categorylistmodal").style.display = "none";
        document.getElementById("categoryinmodal").style.display = "none";
        document.body.style.overflow = "auto";
    });
});

//카테고리 모달 외부를 클릭하면 모달 닫기
window.addEventListener('click', function(event) {
    const categorylistmodal = document.getElementById('categorylistmodal');
    const categoryinmodal = document.getElementById('categoryinmodal');
    const categoryoverlay = document.getElementById('categoryoverlay');
    
    if (event.target == categoryoverlay) {
        categorylistmodal.style.display = 'none';
        categoryinmodal.style.display = 'none';
        categoryoverlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    }

});

});    