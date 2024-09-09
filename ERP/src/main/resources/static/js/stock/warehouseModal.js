document.addEventListener('DOMContentLoaded', function() {
document.getElementById('warehousebtn').addEventListener('click',function(){
	 fetch('/warehouse/warehouse-info', {
	 method: 'GET',
	 cache : "no-cache",
	 })
     .then(response=>response.json())
     .then(data=>{
		let warehousetbody=document.querySelector('#tbody');
    	warehousetbody.innerHTML='';
    	
    	data.forEach(function(warehouse){
			
    	let th = `<tr class="odd gradeX">
                    <th><input type="checkbox" class="checkbox"></th>
                    <td>${warehouse.code}</td>
                    <td>${warehouse.name}</td>
                    <td><input type="button" value="메모"></td>
                    <td><input type="button" value="수정"></td>
                 </tr>`;
			warehousetbody.innerHTML +=th;
		});
    document.getElementById('warehouselistmodal').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';   // 오버레이 숨기기
    document.body.style.overflow = 'hidden';  // 배경 스크롤 다시 활성화
		})
		.catch(function(error){
			alert("error");
		});
});

    document.getElementById('warehousein').addEventListener('click', function() {
        fetch('/warehouse/warehousein-modal', {
            method: 'GET',
            cache: 'no-cache'
        })
        .then(response => response.text())
        .then(data => {
       
            let warehouseinmodal = document.querySelector('#warehouseinmodal');
            warehouseinmodal.style.display = 'block';
            document.getElementById('warehouselistmodal').style.display = 'none';
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });


document.getElementById('warehouseregister').addEventListener('click', function() {
        
        // 전송할 데이터를 준비 (HTML 폼 데이터를 가져와서 객체로 변환)
        const warehouseData = {
            code: document.getElementById('warehouseCode').value,  // 창고 코드
            name: document.getElementById('warehouseName').value   // 창고 이름
        };
        
        // fetch API를 사용하여 서버에 POST 요청 보내기
        fetch('/warehouse/warehouse-register', {
            method: 'POST',  // POST 메서드 사용
            headers: {
                'Content-Type': 'application/json',  // JSON 데이터임을 명시
            },
            body: JSON.stringify(warehouseData),  // 데이터를 JSON 문자열로 변환하여 전송
        })
        .then(response => {
            if (response.ok) {
                return response.text();  // 서버 응답 텍스트를 가져옴
            }
            throw new Error('창고 등록에 실패했습니다.');
        })
        .then(data => {
            // 서버 응답을 처리 (성공 메시지 출력)
            alert(data);  // 예: "창고가 성공적으로 등록되었습니다."
            
              document.getElementById('warehouseinmodal').style.display = 'none';
              document.getElementById('warehouselistmodal').style.display = 'block';
              loadWarehouseList();
        })
        .catch(error => {
            console.error('Error:', error);  // 오류 처리
            alert('오류가 발생했습니다.');
        });
    });
    
    
//모달닫기
document.getElementById('closemodal').addEventListener('click',function(){
    document.getElementById('warehouselistmodal').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
    document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
});

//창고등록취소
document.getElementById('warehouseinback').addEventListener('click',function(){
    document.getElementById('warehouseinmodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'block';   // 오버레이 숨기기
});
});