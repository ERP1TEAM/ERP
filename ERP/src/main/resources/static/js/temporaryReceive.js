document.addEventListener("DOMContentLoaded", function() {
    const params = new URLSearchParams(window.location.search);
    const status = params.get("status");
    if (status === null || status === "입고대기") {
        let wait = document.getElementById("wait");
        wait.style.backgroundColor = "#007BFF";
        wait.style.color = "white";
    }else if(status === "입고완료") {
		let finish = document.getElementById("finish");
        finish.style.backgroundColor = "#007BFF";
        finish.style.color = "white";
	}else if(status === "입고반품") {
		let returns = document.getElementById("return");
        returns.style.backgroundColor = "#007BFF";
        returns.style.color = "white";
	}
	
	document.querySelector("#wait").addEventListener("click",function(){
		location.href="./temporaryReceive?status=입고대기";
	})
	document.querySelector("#finish").addEventListener("click",function(){
		location.href="./temporaryReceive?status=입고완료";
	})
	document.querySelector("#return").addEventListener("click",function(){
		location.href="./temporaryReceive?status=입고반품";
	})
	
	document.querySelectorAll(".receiving").forEach(function(btn){
		btn.addEventListener("click",function(){
			let idx = btn.getAttribute("data");
			let ea = document.getElementById(idx).value;
			if(!ea){
				alert("입고수량을 입력하셔야 합니다.");
				return;
			}
			fetch("./receiving?data="+idx+"&ea="+ea,{
				method:"GET"
			})
			.then(response => response.text())
			.then(data => {
				if(data === "ok"){
					alert('입고가 완료되었습니다.');
					window.location.reload();
				}else if(data === "over"){
					alert("대기수량을 확인해주세요.");
				}
			})
			.catch(error => {
				conosole.log(error);
			})
		})
	})
});
