document.addEventListener('DOMContentLoaded',function(){

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
        
        document.getElementById('categoryoverlay').style.display = 'block';
        document.getElementById('categorylistmodal').style.display = 'block';
        document.body.style.overflow = 'hidden';
    })
    .catch(function(error) {
        alert('카테고리 모달을 불러오는 데 오류가 발생했습니다.');
    });
}


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
        document.body.style.overflow = "auto";
    });
});

//카테고리 모달 외부를 클릭하면 모달 닫기
window.addEventListener('click', function(event) {
    const categorylistmodal = document.getElementById('categorylistmodal');
    const categoryoverlay = document.getElementById('categoryoverlay');
    
    if (event.target == categoryoverlay) {
        categorylistmodal.style.display = 'none';
        categoryoverlay.style.display = 'none';
        document.body.style.overflow = 'auto';
    }

});

});    