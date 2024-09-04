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
});
