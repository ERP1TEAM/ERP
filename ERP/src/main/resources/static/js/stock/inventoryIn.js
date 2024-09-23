document.addEventListener('DOMContentLoaded', function() {
    
    //옵션
    fetch('/main/stock/inventoryselectoptions', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => response.json())
    .then(data => {
		
        const warehouseSelect = document.getElementById('inventoryWarehouseoption');
        warehouseSelect.innerHTML = '';

		let warehouseoption;
		for (let i = 0; i < data.warehouses.length; i++) {
            if (data.warehouses[i].idx == 1) {
                warehouseoption = data.warehouses[i];
                break;
            }
        }
        
        if (warehouseoption) {
            let option = document.createElement('option');
            option.value = warehouseoption.code;
            option.textContent = option.name;
            warehouseSelect.appendChild(option);
        }
        data.warehouses.forEach(warehouse => {
            if (warehouse != warehouseoption) {
                let option = document.createElement('option');
                option.value = warehouse.code;
                option.textContent = warehouse.name;
                warehouseSelect.appendChild(option);
            }
        });

        const locationSelect = document.getElementById('inventoryLocationoption');
        locationSelect.innerHTML = '';

		let locationoption;
		for (let i = 0; i < data.locations.length; i++) {
            if (data.locations[i].idx == 1) {
                locationoption = data.locations[i];
                break;
            }
        }
         if (locationoption) {
            let option = document.createElement('option');
            option.value = locationoption.code;
            option.textContent = option.code;
            locationSelect.appendChild(option);
        }
        
        data.locations.forEach(location => {
            if (location != locationoption) {
                let option = document.createElement('option');
                option.value = location.code;
                option.textContent = location.code;
                locationSelect.appendChild(option);
            }
        });
        
        const supplierSelect = document.getElementById('inventorySuppliercodeoption');
        const supplierNameInput = document.getElementById('inventorySuppliernameoption');
        supplierSelect.innerHTML = '';
        
        let supplieroption;
        if (data.suppliers.length > 0) {
            supplieroption = data.suppliers[0];
            supplierSelect.value = supplieroption.code;
            supplierNameInput.value = supplieroption.name;
        }
        if (supplieroption) {
            let option = document.createElement('option');
            option.value = supplieroption.code;
            option.textContent = supplieroption.code;
            supplierSelect.appendChild(option);
        }
        data.suppliers.forEach(supplier => {
            if (supplier != supplieroption) {
                let option = document.createElement('option');
                option.value = supplier.code;
                option.textContent = supplier.code;
                supplierSelect.appendChild(option);
            }
        });
        
        supplierSelect.addEventListener('change', function() {
        	const selectedCode = supplierSelect.value;
        	let selectedSupplier = null;
        	 
        	for (let i = 0; i < data.suppliers.length; i++) {
    		let supplier = data.suppliers[i];
		
			if (supplier.code === selectedCode) {
       			selectedSupplier = supplier;
        		break;
    			}
			}
			
			if (selectedSupplier) {
                supplierNameInput.value = selectedSupplier.name;
            } else {
                supplierNameInput.value = '';
            }
       		});
		

        const maincategorySelect = document.getElementById('inventorymaincategoryoption');
        maincategorySelect.innerHTML = '';
		
		const duplidelmainname = new Set();
		
        let maincategoryOption;
		for (let i = 0; i < data.categories.length; i++) {
            if (data.categories[i].idx == 1) {
              maincategoryOption = data.categories[i];
                break;
            }
        }
         if (maincategoryOption) {
            let option = document.createElement('option');
            option.value = maincategoryOption.mainCode;
            option.textContent = maincategoryOption.mainName;
            maincategorySelect.appendChild(option);
        }
        
       data.categories.forEach(maincategory => {
            if (!duplidelmainname.has(maincategory.mainName)) {
                duplidelmainname.add(maincategory.mainName);
                let option = document.createElement('option');
                option.value = maincategory.mainCode;
                option.textContent = maincategory.mainName;
                maincategorySelect.appendChild(option);
            }
        });
        
        const subcategorySelect = document.getElementById('inventorysubcategoryoption');
        subcategorySelect.innerHTML = '';

        let subcategoryOption;
		for (let i = 0; i < data.categories.length; i++) {
            if (data.categories[i].idx == 1) {
             subcategoryOption = data.categories[i];
                break;
            }
        }
         if (subcategoryOption) {
            let option = document.createElement('option');
            option.value = subcategoryOption.subCode;
            option.textContent = subcategoryOption.subName;
            subcategorySelect.appendChild(option);
        }
        
        data.categories.forEach(subcategory => {
            if (subcategory != subcategoryOption) {
                let option = document.createElement('option');
                option.value = subcategory.subCode;
                option.textContent = subcategory.subName;
                subcategorySelect.appendChild(option);
            }
        });
    })
    .catch(error => {
        console.error('Error fetching options:', error);
    });
    
    //상품 등록
    document.getElementById('inventorysave').addEventListener('click', function () {
        const productcode = document.getElementById('inventorycode').value;
        const supplierCode = document.getElementById('inventorySuppliercodeoption').value;
        const mainCategory = document.getElementById('inventorymaincategoryoption').value;
        const subCategory = document.getElementById('inventorysubcategoryoption').value;
		const classificationCode = mainCategory+subCategory;
        const locationCode = document.getElementById('inventoryLocationoption').value;
        const useFlag = document.querySelector('input[name="inventoryuseflag"]:checked').value;
        const productname = document.getElementById('inventoryname').value;
        const productprice = document.getElementById('inventoryprice').value;
        const memo = document.getElementById('inventorymemo').value;
        
        const inventoryData = {
            code: productcode,
            supplierCode: supplierCode,
            classificationCode: classificationCode,
            storageLocation: locationCode,
            useFlag: useFlag,
            name: productname,
            price: productprice,
            memo: memo
        };
        const manager = "김중앙";

        fetch(`/main/stock/inventories?manager=${encodeURIComponent(manager)}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(inventoryData),
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }else if(response.status == 409){
				return response.text();
				}else{
				throw new Error('상품 등록에 실패했습니다.');
			}
        })
        .then(data => {
            alert("상품이 등록 되었습니다.");
    		document.getElementById('inventoryname').value = '';
    		document.getElementById('inventoryprice').value = '';
    		document.getElementById('inventorymemo').value = '';
    		 
    		 fetch('/main/stock/inventoryrandomcode')
      			 .then(response => {
            	 if (response.ok) {
               		 return response.text();
            	} else {
              	  throw new Error('새로운 상품코드를 받는 데 실패했습니다.');
         	    }
       		    })
        		.then(newcode=> {
            		document.getElementById('inventorycode').value = newcode;
        		})
        		.catch(error => {
            		alert("새로운 상품코드를 가져오는 중 오류 발생");
        		});
        	})
        .catch(error => {
            alert("오류발생");
        });
    });

//난수번호
const productcode = document.getElementById("inventorycode");
fetch('/main/stock/inventoryrandomcode')
.then(response=>{
	if(response.ok){
	return response.text();
	}else if(response.status == 409){
		 return response.text().then(errorMessage => {
               	alert(errorMessage);
         });
	}else{
		throw new Error("서버 오류"); 
	}
	})
.then(data=>{
	if(data){
	productcode.value=data;
	}
})
.catch(error=>{
	alert("오류가 발생했습니다.");
});


	
document.getElementById('inventorycancle').addEventListener('click', function () {
    document.getElementById('inventoryname').value = '';
    document.getElementById('inventoryprice').value = '';
    document.getElementById('inventorymemo').value = '';
});
});