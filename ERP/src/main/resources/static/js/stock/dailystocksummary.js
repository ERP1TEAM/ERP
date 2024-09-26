document.addEventListener('DOMContentLoaded', function() {
    document.getElementById("stocklisttbody").addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('dailyinventorybtn')) {
            let productCode = event.target.getAttribute('data-product-code');
            document.getElementById('dailystockproductcode').value = productCode;
            document.getElementById("dailystockmodal").style.display = "block";
            document.getElementById("dailystockoverlay").style.display = "block";
            document.body.style.overflow = "hidden";
        }
    });

    document.getElementById('dailystockdate').addEventListener('change', function() {
        let selectedDate = document.getElementById('dailystockdate').value;
        let productCode = document.getElementById('dailystockproductcode').value;

        if (selectedDate && productCode) {
            fetch(`/main/stock/daily_summary?date=${selectedDate}&productCode=${productCode}`)
                .then(response => response.json())
                .then(data => {
					console.log(data);
                    document.getElementById('dailystocktotalqty').value = data.data.totalQty;
                    document.getElementById('dailystockavailableqty').value = data.data.availableQty;
                    document.getElementById('dailystockunavailableqty').value = data.data.unavailableQty;
                    document.getElementById('dailystockreceivereturnqty').value = data.data.receiveReturnQty;
                    document.getElementById('dailystockreturnqty').value = data.data.returnQty;
                    document.getElementById('dailystockreceivedqty').value = data.data.receivedQty;
                    document.getElementById('dailystockshippedqty').value = data.data.shippedQty;
                })
                .catch(error => {
                    console.log(error);
                    alert('데이터 조회 중 오류가 발생했습니다.');
                });
        } else {
            alert('날짜를 선택해 주세요.');
        }
    });
    
    document.getElementById("dailystockclosemodal").addEventListener("click", function() {
        document.getElementById("dailystockmodal").style.display = "none";
        document.getElementById("dailystockoverlay").style.display = "none";
        document.body.style.overflow = "auto";
    });

    document.getElementById("dailystockoverlay").addEventListener("click", function() {
        document.getElementById("dailystockmodal").style.display = "none";
        document.getElementById("dailystockoverlay").style.display = "none";
        document.body.style.overflow = "auto";
    });
    
});