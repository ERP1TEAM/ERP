document.addEventListener('DOMContentLoaded', function() {
    
     let safetyQtyModifyBtn = document.getElementById('safetyqtymodify');
     
    document.getElementById("stocklisttbody").addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('safetyqtysetting')) {
            let row = event.target.closest('tr');
            let productCode = row.querySelector('td:nth-child(1)').innerText;
            let productName = row.querySelector('td:nth-child(2)').innerText;
            let supplierCode = row.querySelector('td:nth-child(3)').innerText;
            let supplierName = row.querySelector('td:nth-child(4)').innerText;
            
            document.getElementById("stockoverlay").style.display = "block";
            document.getElementById("safetyqtymodifymodal").style.display = "block";
            document.body.style.overflow = "hidden";
            
            document.getElementById('stockmodifyproductcode').value = productCode;
            document.getElementById('stockmodifyproductname').value = productName;
            document.getElementById('stockmodifysuppliercode').value = supplierCode;
            document.getElementById('stockmodifysuppliername').value = supplierName;
            
            safetyQtyModifyBtn.removeEventListener('click', handleSafetyQtyModify);
            safetyQtyModifyBtn.addEventListener('click', handleSafetyQtyModify);
             }
            });
             function handleSafetyQtyModify() {
        let safetyQty = document.querySelector('#stockmodalSafetyQty').value;

        if (!/^[0-9]+$/.test(safetyQty)) {
            alert('0부터 9까지만 입력 가능합니다.');
            safetyQty = safetyQty.replace(/[^0-9]/g, '');
            document.querySelector('#stockmodalSafetyQty').value = safetyQty;
            return;
        }

        let productCode = document.getElementById('stockmodifyproductcode').value;

        fetch(`/main/stock/updateSafetyQty/${productCode}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                safetyQty: safetyQty
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('안전재고 개수가 수정되었습니다.');
                let rows = document.querySelectorAll('#stocklisttbody tr');
                rows.forEach(row => {
                    let firstCell = row.querySelector('td:first-child');
                    if (firstCell && firstCell.innerText.trim() === productCode) {
                        row.querySelector('td:nth-child(8)').innerText = safetyQty;
                    }
                });
                document.getElementById("stockoverlay").style.display = "none";
                document.getElementById("safetyqtymodifymodal").style.display = "none";
                document.body.style.overflow = 'auto';
                resetModalFields();  // 필드 초기화
            } else {
                alert('수정에 실패했습니다.');
            }
        })
        .catch(error => {
            alert("안전재고 개수를 업데이트하는데 오류가 발생했습니다.");
        });
    }
    
function resetModalFields() {
    document.getElementById('stockmodifyproductcode').value = "";
    document.getElementById('stockmodifyproductname').value = "";
    document.getElementById('stockmodifysuppliercode').value = "";
    document.getElementById('stockmodifysuppliername').value = "";
    document.getElementById('stockmodalSafetyQty').value = "";
}
    document.getElementById("stockclosemodal").addEventListener("click", function() {
        resetModalFields();
        document.getElementById("stockoverlay").style.display = "none";
        document.getElementById("safetyqtymodifymodal").style.display = "none";
        document.body.style.overflow = 'auto';
    });

    document.getElementById("safetyqtycancle").addEventListener("click", function() {
        resetModalFields();
        document.getElementById("stockoverlay").style.display = "none";
        document.getElementById("safetyqtymodifymodal").style.display = "none";
        document.body.style.overflow = 'auto';
    });

    document.getElementById("stockoverlay").addEventListener("click", function() {
        resetModalFields();
        document.getElementById("stockoverlay").style.display = "none";
        document.getElementById("safetyqtymodifymodal").style.display = "none";
        document.body.style.overflow = 'auto';
    });
});