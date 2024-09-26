document.addEventListener('DOMContentLoaded', function() {
	
	 document.getElementById("stocklisttbody").addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('dailyinventorybtn')) {
            let productCode = event.target.getAttribute('data-product-code'); // 제품 코드를 가져옴
            showDailyInventoryModal(productCode);
        }
    });
	
	
function showDailyInventoryModal(productCode) {
        fetch(`/api/stock/daily_summary/${productCode}`)
        .then(response => response.json())
        .then(data => {
            let modalBody = document.getElementById('dailystocksummarymodaltbody'); // ID를 올바르게 변경
            modalBody.innerHTML = ''; // 모달 내용 초기화

            data.forEach(function(entry) {
                let row = `
                    <tr>
                        <td>${entry.stock_date}</td>
                        <td>${entry.total_qty}</td>
                        <td>${entry.available_qty}</td>
                        <td>${entry.unavailable_qty}</td>
                        <td>${entry.safety_qty}</td>
                        <td>${entry.return_qty}</td>
                        <td>${entry.received_qty}</td>
                        <td>${entry.shipped_qty}</td>
                    </tr>
                `;
                modalBody.innerHTML += row;
            });

            // 모달 표시
            document.getElementById("dailystockoverlay").style.display = "block";
            document.getElementById("dailystockmodal").style.display = "block";
            document.body.style.overflow = "hidden";
        })
        .catch(error => {
            console.log(error);
            alert('상세보기를 불러오는 중 문제가 발생했습니다.');
        });
    }

    // 모달 닫기 버튼 클릭 시
    document.getElementById("dailystockclosemodal").addEventListener("click", function() {
        document.getElementById("dailystockoverlay").style.display = "none";
        document.getElementById("dailystockmodal").style.display = "none";
        document.body.style.overflow = "auto";
    });

    // 오버레이 클릭 시 모달 닫기
    document.getElementById("dailystockoverlay").addEventListener("click", function() {
        document.getElementById("dailystockoverlay").style.display = "none";
        document.getElementById("dailystockmodal").style.display = "none";
        document.body.style.overflow = "auto";
    });

});