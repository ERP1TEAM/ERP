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
            if (maincategory != maincategoryOption) {
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
});