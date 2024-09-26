document.addEventListener('DOMContentLoaded', function() {
	
	 document.getElementById("stocklisttbody").addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('dailyinventorybtn')) {
            let productCode = event.target.getAttribute('data-product-code'); // 제품 코드를 가져옴
            showDailyInventoryModal(productCode);
        }
    });
	
	
function showDailyInventoryModal(productCode) {
        fetch(`/main/stock/daily_summary/${productCode}`)
        .then(response => response.json())
        .then(data => {
           document.getElementById('dailystockdate').value = data.stock_date;
            document.getElementById('dailystockproductcode').value = data.product_code;
            document.getElementById('dailystocktotalqty').value = data.total_qty;
            document.getElementById('dailystockavailableqty').value = data.available_qty;
            document.getElementById('dailystockunavailableqty').value = data.unavailable_qty;
            document.getElementById('dailystockreceivereturnqty').value = data.receive_return_qty;
            document.getElementById('dailystockreturnqty').value = data.return_qty;
            document.getElementById('dailystockreceivedqty').value = data.received_qty;
            document.getElementById('dailystockshippedqty').value = data.shipped_qty;

            document.getElementById("dailystockoverlay").style.display = "block";
            document.getElementById("dailystockmodal").style.display = "block";
            document.body.style.overflow = "hidden";
        })
        .catch(error => {
            console.log(error);
            alert('상세보기를 불러오는 중 문제가 발생했습니다.');
        });
    }

    document.getElementById("dailystockclosemodal").addEventListener("click", function() {
        document.getElementById("dailystockoverlay").style.display = "none";
        document.getElementById("dailystockmodal").style.display = "none";
        document.body.style.overflow = "auto";
    });



    document.getElementById("dailystockoverlay").addEventListener("click", function() {
        document.getElementById("dailystockoverlay").style.display = "none";
        document.getElementById("dailystockmodal").style.display = "none";
        document.body.style.overflow = "auto";
    });

});