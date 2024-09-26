document.addEventListener('DOMContentLoaded', function() {
    
    document.getElementById('inventorysave').addEventListener('click', function () {
        const productcode = document.getElementById('inventorycode').value;
        const productname = document.getElementById('inventoryname').value;
        const supplierCode = document.getElementById('inventorySuppliercodeoption').value;
        const classificationCode = document.getElementById('inventorycategorycode').value;
        const useFlag = document.querySelector('input[name="inventoryuseflag"]:checked').value;
        const productprice = document.getElementById('inventoryprice').value;
        const storageLocation = document.getElementById('inventoryStorageLocation').value;
        
        if (!productname || !supplierCode || !classificationCode|| !productprice) {
            alert("모든 값을 채워주세요.");
            return;
        }
        
        
        const inventoryData = {
            code: productcode,
            name: productname,
            supplierCode: supplierCode,
            classificationCode: classificationCode,
            useFlag: useFlag,
            price: productprice,
            storageLocation : storageLocation
        };
		
        fetch(`/main/stock/inventories`, {
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
    		 
    		 fetch('/main/stock/inventoryrandomcode')
      			 .then(response => {
            	 if (response.ok) {
               		 return response.text();
               		 window.location.reload();
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