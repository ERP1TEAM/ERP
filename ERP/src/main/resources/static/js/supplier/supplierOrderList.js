document.addEventListener("DOMContentLoaded",function(){
	document.querySelectorAll(".purchase_request").forEach(function(btn){
		btn.addEventListener("click",function(){
			let idx = btn.getAttribute("data");
			let ea = document.getElementById(idx).value;
			if(!ea){
				alert("납품수량을 입력하셔야 합니다.");
				return;
			}
			fetch("./delivery_regi?data="+idx+"&ea="+ea,{
				method:"GET"
			})
			.then(response => response.text())
			.then(data => {
				if(data === "ok"){
					alert('납품등록이 완료되었습니다.');
					window.location.reload();
				}else if(data === "over"){
					alert("주문수량을 확인해주세요.");
				}
			})
			.catch(error => {
				conosole.log(error);
			})
		})
	})
})