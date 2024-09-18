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
document.getElementById('locationIn').addEventListener('click', function() {
	
	const rackcodeval = document.getElementById('rackcode').value.trim();
    const warehouseSelectval = document.getElementById('warehouseSelect').value;
    const rowcodeval = document.getElementById('rowcode').value;
    const levelcodeval = document.getElementById('levelcode').value;
    const locationmemoval = document.getElementById('locationmemo').value;
    const useflagval = document.querySelector('input[name="locationuseflag"]:checked').value;
    
    const autolocationcodeval = `${warehouseSelectval}-${rackcodeval}-${levelcodeval}${rowcodeval}`;
    
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
          memo: locationmemoval,
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
          if (response.ok) {
          	  return response.text();
          }else if(response.status == 409){
			  return response.text();
		  }else{
			  alert('로케이션 등록에 실패했습니다.');
		  }
    })
    .then(data => {
          alert(data);
          if(data=='로케이션이 등록되었습니다.'){
             document.getElementById('locationinmodal').style.display = 'none';
             document.getElementById('locationoverlay').style.display = 'none';
          }
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
document.getElementById("locationclosemodal").addEventListener("click", function() {
    document.getElementById("locationoverlay").style.display = "none";
    document.getElementById("locationinmodal").style.display = "none";
 document.body.style.overflow = 'auto';
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
});

});
    