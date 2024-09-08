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
    // 창고 등록 폼을 비동기적으로 가져옴
    fetch('/warehouse/warehouse-in', {
        method: 'GET',
        cache: 'no-cache'
    })
    .then(response => response.text())  // HTML 텍스트로 응답을 받음
    .then(data => {
        // 창고 등록 폼을 모달에 삽입
        let warehouseinmodal = document.getElementById('warehouseinmodal');
        warehouseinmodal.innerHTML = data;
        warehouseinmodal.style.display = 'block';  // 모달 표시

        // 창고 리스트 모달 숨기기
        document.getElementById('warehouselistmodal').style.display = 'none';
    })
	.catch(function(error){
			alert("error");
		});
});

//in 취소
document.getElementById('warehouseincancle').addEventListener('click', function() {
   
    document.getElementById('warehouseinmodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';
    document.body.style.overflow = 'hidden';
});

//창고 모달 닫기
document.getElementById('closemodal').addEventListener('click',function(){
    document.getElementById('warehouselistmodal').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
    document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
});