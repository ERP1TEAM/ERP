document.addEventListener('DOMContentLoaded', function() {
    
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
            
            document.getElementById('safetyqtymodify').addEventListener('click', function() {
                const safetyQty = document.getElementById('stockmodalSafetyQty').value;

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
                        alert('안전재고 개수가 성공적으로 수정되었습니다.');

                        row.querySelector('td:nth-child(8)').innerText = safetyQty;

                        document.getElementById("stockoverlay").style.display = "none";
                        document.getElementById("safetyqtymodifymodal").style.display = "none";
                        document.body.style.overflow = 'auto';
                    } else {
                        alert('안전재고 개수 수정에 실패했습니다.');
                        console.log(error);
                    }
                })
                .catch(error => {
                    console.error('Error updating safetyQty:', error);
                });
            }, { once: true });
        }
    });

    document.getElementById("stockclosemodal").addEventListener("click", function() {
        document.getElementById("stockoverlay").style.display = "none";
        document.getElementById("safetyqtymodifymodal").style.display = "none";
        document.body.style.overflow = 'auto';
    });

    document.getElementById("safetyqtycancle").addEventListener("click", function() {
        document.getElementById("stockoverlay").style.display = "none";
        document.getElementById("safetyqtymodifymodal").style.display = "none";
        document.body.style.overflow = 'auto';
    });

    document.getElementById("stockoverlay").addEventListener("click", function() {
        document.getElementById("stockoverlay").style.display = "none";
        document.getElementById("safetyqtymodifymodal").style.display = "none";
        document.body.style.overflow = 'auto';
    });
});