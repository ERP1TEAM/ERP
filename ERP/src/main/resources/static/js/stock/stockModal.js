function showwarehouse() {
	 // Ajax 요청 보내기
	 fetch('/stock/warehouse-info', {
	 method: 'GET'
	 })
     .then(response => response.json())
     .then(data => {
    document.getElementById('warehouselistmodal').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';   // 오버레이 숨기기
    document.body.style.overflow = 'hidden';  // 배경 스크롤 다시 활성화
        });
}

function closemodal() {
    document.getElementById('warehouselistmodal').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
    document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
}