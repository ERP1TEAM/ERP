document.addEventListener('DOMContentLoaded',function(){
	const rowcode = document.getElementById('rowcode');
	const firstrow = 'A'.charCodeAt(0);
	const totalrowsea = 4;
	
	for(let i= 0; i<totalrowsea; i++){
		const totalrows=String.fromCharCode(firstrow+i);
		const rowoption =document.createElement('option');
		rowoption.value=`${totalrows}`;
		rowoption.textContent=`${totalrows}`;
		rowcode.appendChild(rowoption);
	}
	
	const levelcode = document.getElementById('levelcode');
	const maxlevel = 5;
	for(let i=1; i<maxlevel; i++){
		const leveloption=document.createElement('option');
		leveloption.value=`${i}`;
		leveloption.textContent=`${i}`;
		levelcode.appendChild(leveloption);
	}
	
	//창고 데이터 갖고오기
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
});