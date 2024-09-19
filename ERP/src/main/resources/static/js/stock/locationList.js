document.addEventListener('DOMContentLoaded', function() {

function locationlistmain(){
	fetch('/main/stock/locations',{
		method:'GET',
		headers:{
				'Content-Type':'application/json',
		}	
	})
	.then(response=>response.json())
	.then(data=>{
		let locationlisttbody=document.querySelector('#locationlisttbody');
    	locationlisttbody.innerHTML='';

    	data.forEach(function(locationlist){
		
		let locationlistmemo;
		if(locationlist.memo){
			locationlistmemo = locationlist.memo;
		}else{
			locationlistmemo='';
		}
		
		let locationlistuseFlag;
                if (locationlist.useFlag == 'Y') {
                    locationlistuseFlag = '사용';
                } else if (locationlist.useFlag == 'N') {
                    locationlistuseFlag = '미사용';
                }
			
    	let locationlistth = `<tr class="odd gradeX">
                    <th><input type="checkbox" class="checkbox" value="${locationlist.code}"></th>
                    <td>${locationlist.code}</td>
                    <td>${locationlist.warehouseCode}</td>
                    <td>${locationlist.rackCode}</td>
                    <td>${locationlist.rowCode}</td>
                    <td>${locationlist.levelCode}</td>
                    <td>${locationlistuseFlag}</td>
                    <td>${locationlistmemo}</td>
                    <td><input type="button" value="수정" class="locationlistmodifybtn"></td>
                 </tr>`;
			locationlisttbody.innerHTML +=locationlistth;
		});
		})
		.catch(function(error){
			alert("error");
		});
        }
        
        
//로케이션 삭제   
document.getElementById('locationdelete').addEventListener('click',function(){
	
	let selectLocation = [];
    let checkboxes = document.querySelectorAll('.checkbox:checked');
    
    checkboxes.forEach(function (checkbox) {
        selectLocation.push(checkbox.value);
    });

    if (selectLocation.length == 0) {
        alert('삭제할 로케이션을 선택하세요.');
        return false;
    }
    
    if(!confirm("로케이션을 삭제하시겠습니까?")){
		return false;
	}
    
    const locationCodePath=encodeURIComponent(selectLocation.join(','));
    
    fetch(`/main/stock/locations/${locationCodePath}`, {
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
            alert('로케이션이 삭제되었습니다.');
            locationlistmain();
        } else {
            alert('로케이션 삭제 중 오류가 발생했습니다.');
        }
    })
    .catch(function (error) {
        console.log(error);
        alert('오류가 발생했습니다.');
    });
});         
locationlistmain();
});