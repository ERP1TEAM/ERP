function showwarehouse() {
	 // Ajax 요청 보내기
	 fetch('/warehouse/warehouse-info', {
	 method: 'GET'
	 })
     .then(response => response.json())
     .then(data => {
		let warehousetbody=document.querySelector('#tbody');
    	warehousetbody.innerHTML='';
    	
    	data.forEach(warehouse =>{
			
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
		.catch(error=>console.error('error'));
		}

function closemodal() {
    document.getElementById('warehouselistmodal').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
    document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
}