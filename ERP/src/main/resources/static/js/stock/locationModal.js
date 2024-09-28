document.addEventListener('DOMContentLoaded',function(){

	const rowcode = document.getElementById('rowcode');
	const firstrow = 'A'.charCodeAt(0);
	const totalrowsea = 3;
	
	for(let i= 0; i<=totalrowsea; i++){
	const totalrows=String.fromCharCode(firstrow+i);
	const rowoption =document.createElement('option');
	rowoption.value=`${totalrows}`;
		rowoption.textContent=`${totalrows}`;
		rowcode.appendChild(rowoption);
	}
	
	const levelcode = document.getElementById('levelcode');
	const maxlevel = 4;
	for(let i=1; i<=maxlevel; i++){
		const leveloption=document.createElement('option');
		leveloption.value=`${i}`;
		leveloption.textContent=`${i}`;
		levelcode.appendChild(leveloption);
	}
	
//창고 옵션
function htmlwarehouselist(){
	fetch('/main/stock/warehouses',{
		method:'GET',
		headers:{
				'Content-Type' : 'application/json',
		}
	})
	.then(response => response.json())
	.then(data =>{
		const warehouseSelect = document.getElementById('warehouseSelect');
		warehouseSelect.innerHTML='';
			
		data.forEach(warehouse=>{
			const warehouseoption = document.createElement('option');
			warehouseoption.value=warehouse.code;
			warehouseoption.textContent=warehouse.name;
			warehouseSelect.appendChild(warehouseoption);
		});
	})
	.catch(error=>{
		alert("error!");
	});
}
htmlwarehouselist();
	
function autolocationcode(){
	const warehouseSelectval= document.getElementById('warehouseSelect').value;
	const rackcodeval= document.getElementById('rackcode').value;
	const rowcodeval= document.getElementById('rowcode').value;
	const levelcodeval= document.getElementById('levelcode').value;
	
	const autolocationcode = `${warehouseSelectval}-${rackcodeval}-${levelcodeval}${rowcodeval}`;
	
	document.getElementById('locationcode').value = autolocationcode;
}
	document.getElementById('warehouseSelect').addEventListener('change', autolocationcode);
    document.getElementById('rackcode').addEventListener('input',autolocationcode);
    document.getElementById('rowcode').addEventListener('change', autolocationcode);
    document.getElementById('levelcode').addEventListener('change',autolocationcode);

    
    
//로케이션 등록
document.getElementById('locationregister').addEventListener('click', function() {
	const rackcodeval = document.getElementById('rackcode').value.trim();
    const warehouseSelectval = document.getElementById('warehouseSelect').value;
    const rowcodeval = document.getElementById('rowcode').value;
    const levelcodeval = document.getElementById('levelcode').value;
    const useflagval = document.querySelector('input[name="locationuseflag"]:checked').value;
    
    const autolocationcodeval = `${warehouseSelectval}-${rackcodeval}-${levelcodeval}${rowcodeval}`;
    const specialCharPattern = /[^a-zA-Z0-9]/;
    if (specialCharPattern.test(rackcodeval)) {
        alert('선반 열 코드에 특수문자를 사용할 수 없습니다.');
        return false;
    }
    
    if (!rackcodeval) {
    alert('선반 열 코드를 적어주세요');
    return false;
    }
        
    const locationData = {
		  code: autolocationcodeval,
    	  warehouseCode: warehouseSelectval,
          rackCode: rackcodeval,
          rowCode: rowcodeval,
          levelCode: levelcodeval,
          useFlag: useflagval
    };
        
    fetch('/main/stock/locations', {
    	  method: 'POST',
          headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(locationData),
    })
    .then(response => {
    if (!response.ok) {
        if (response.status == 409) {
            alert('이미 존재하는 로케이션 코드입니다.');
        } else {
            alert('로케이션 등록 중 오류가 발생했습니다.');
        }
        return false;
    }
    return response.json();
	})
    .then(data => {
		if(!data) {return false;}
		
		let datauseFlag;
                if (data.useFlag == 'Y') {
                    datauseFlag = '사용';
                } else if (data.useFlag == 'N') {
                    datauseFlag = '미사용';
                }
			const locationlisttbody = document.getElementById('locationlisttbody');  // 로케이션 리스트가 출력되는 DOM 요소
            const locationlisttr = document.createElement('tr');  // 새 행 생성
            locationlisttr.innerHTML = `
             	<td><input type="checkbox" class="checkbox" value="${data.code}"></td>
                <td>${data.code}</td>
                <td>${data.warehouseCode}</td>
                <td>${data.rackCode}</td>
                <td>${data.rowCode}</td>
                <td>${data.levelCode}</td>
                <td>${datauseFlag}</td>
                <td><input type="button" value="수정" class="locationlistmodifybtn"></td>
            `;
            locationlisttbody.appendChild(locationlisttr);
            alert('등록이 완료되었습니다.');
            document.getElementById('rackcode').value = '';
            document.getElementById('locationcode').value = '';
            
             document.getElementById('locationinmodal').style.display = 'none';
             document.getElementById('locationoverlay').style.display = 'none';
         	 document.body.style.overflow = 'auto';

    })
    .catch(error => {
           alert('오류가 발생했습니다.');
        });
    });

//로케이션 모달 열기	
document.getElementById("locationbtn").addEventListener("click", function() {
    document.getElementById("locationoverlay").style.display = "block";
    document.getElementById("locationinmodal").style.display = "block";
    document.body.style.overflow = "hidden";
});
//로케이션 X버튼으로 모달 닫기
document.querySelectorAll('.closemodal').forEach(function(closeBtn) {
     closeBtn.addEventListener('click', function() {
        document.getElementById('locationoverlay').style.display = 'none';
        document.getElementById('locationinmodal').style.display = 'none';
        document.querySelector('#locationmodifymodal').style.display = 'none';
        document.querySelector("#locationmodifyoverlay").style.display = 'none';
        document.body.style.overflow = 'auto';
   });
});
//취소 버튼으로 모달 닫기
document.getElementById("locationcancle").addEventListener("click", function() {
    document.getElementById("locationoverlay").style.display = "none";
    document.getElementById("locationinmodal").style.display = "none";
 document.body.style.overflow = 'auto';
});
//오버레이 클릭시 모달 닫기
document.getElementById("locationoverlay").addEventListener("click", function() {
    document.getElementById("locationoverlay").style.display = "none";
    document.getElementById("locationinmodal").style.display = "none";
 	document.body.style.overflow = 'auto';
});


//로케이션 수정정보
document.getElementById("locationlisttbody").addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('locationlistmodifybtn')) {
            let code = event.target.getAttribute('data-code');
            
            fetch(`/main/stock/locationmodify/${code}`,{
			method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        	}).then(response => response.json())
        	.then(data => {
	        document.querySelector('#locationmodifycode').value = data.code;
            document.querySelector('#locationmodifywarehouse').value = data.warehouseCode;
            document.querySelector('#locationmodifyrackcode').value = data.rackCode;
            document.querySelector('#locationmodifyrowcode').value = data.rowCode;
            document.querySelector('#locationmodifylevelcode').value = data.levelCode;
	            if (data.useFlag == 'Y') {
	                document.querySelector('input[name="locationmodifyuseflag"][value="Y"]').checked = true;
	            } else {
	                document.querySelector('input[name="locationmodifyuseflag"][value="N"]').checked = true;
	            }
            document.querySelector("#locationmodifymodal").style.display = "block";
            document.querySelector("#locationmodifyoverlay").style.display = "block";
            document.body.style.overflow = "hidden";
        	}).catch(error=>{
				alert('로케이션 정보를 가져오는 중 오류가 발생했습니다.');
			});
		}        
    });
});

//로케이션 수정버튼은 수정 후 페이징때문에 아예 로케이션리스트에서 코드 작성함

function resetModalFields() {
    document.querySelector('#locationmodifywarehouse').value = "";
    document.querySelector('#locationmodifycode').value = "";
    document.querySelector('#locationmodifyrackcode').value = "";
    document.querySelector('#locationmodifyrowcode').value = "";
    document.querySelector('#locationmodifylevelcode').value = "";
    document.querySelector('input[name="locationmodifyuseflag"][value="Y"]').checked = true;
    }       

document.querySelector("#locationmodifyoverlay").addEventListener("click", function() {
    document.querySelector("#locationmodifymodal").style.display = "none";
    document.querySelector("#locationmodifyoverlay").style.display = "none";
    document.body.style.overflow = "auto";
     resetModalFields();
    });
document.querySelector("#locationmodifyclose").addEventListener("click", function() {
    document.querySelector("#locationmodifymodal").style.display = "none";
    document.querySelector("#locationmodifyoverlay").style.display = "none";
    document.body.style.overflow = "auto";
     resetModalFields();
    });   
