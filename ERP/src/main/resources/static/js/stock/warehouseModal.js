document.addEventListener('DOMContentLoaded', function() {
document.getElementById('warehousebtn').addEventListener('click',function(){
warehousemainmodal();
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
            
            document.getElementById('warehouseCode').value = '';
       		document.getElementById('warehouseName').value = '';
       		document.getElementById('warehouseMemo').value = '';
        })
        .catch(error => {
            alert("error");
        });
    });

//창고등록
document.getElementById('warehouseregister').addEventListener('click', function() {
        
        const warehouseCode = document.getElementById('warehouseCode').value.trim();
        const warehouseName = document.getElementById('warehouseName').value.trim();
        const warehouseMemo = document.getElementById('warehouseMemo').value.trim();
        
        if (!warehouseCode || !warehouseName) {
        alert('창고 코드와 이름을 모두 입력해주세요.');
        return false;
    	}
        
        const warehouseData = {
            code: warehouseCode,
            name: warehouseName,
            memo: warehouseMemo
        };
        
        fetch('/warehouse/warehouses', {
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
    
    fetch(`/warehouse/${warehouseCodePath}`, {
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
            alert('창고 삭제 중 오류가 발생했습니다.');
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
    fetch(`/warehouse/${warehouseCode}`, {
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
            console.error('Error fetching warehouse info:', error);
            alert('창고 정보를 불러오는 데 실패했습니다.');
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
        
        const warehouseData = {
            name: warehouseName,
            memo: warehouseMemo
        };
        console.log(warehouseName);
        
        fetch(`/warehouse/${warehouseCode}`, {
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

//창고수정취소
document.getElementById('warehousemodifyback').addEventListener('click',function(){
    document.getElementById('warehousemodifymodal').style.display = 'none';
    document.getElementById('warehouselistmodal').style.display = 'block';   // 오버레이 숨기기
});

//창고리스트 출력
function warehousemainmodal(){
	fetch('/warehouse/warehouses',{
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
			
    	let th = `<tr class="odd gradeX">
                    <th><input type="checkbox" class="checkbox" value="${warehouse.code}"></th>
                    <td>${warehouse.code}</td>
                    <td>${warehouse.name}</td>
                    <td><input type="button" value="메모"></td>
                    <td><input type="button" value="수정" class="warehousemodifybtn"></td>
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
        }
});