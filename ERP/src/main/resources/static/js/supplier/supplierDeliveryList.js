document.addEventListener("DOMContentLoaded", function() {
	document.querySelectorAll(".purchase_request").forEach(function(btn) {
		btn.addEventListener("click", function() {
			let idx = btn.getAttribute("data-idx");
			let wt = parseInt(btn.getAttribute("data-wt"));
			let ea = parseInt(document.getElementById(idx).value);
			if (!ea) {
				alert("납품수량을 입력하셔야 합니다.");
				return;
			} else if (wt < ea || wt==0) {
				alert("미납수량을 확인해주세요");
			} else if (confirm(ea + '개 납품등록을 하시겠습니까?')) {
				fetch("./delivery_regi?data=" + encodeURIComponent(idx) + "&ea=" + encodeURIComponent(ea), {
					method: "GET"
				})
					.then(response => response.text())
					.then(data => {
						if (data === "ok") {
							alert('납품등록이 완료되었습니다.');
							window.location.reload();
						} else if (data === "over") {
							alert("주문수량을 확인해주세요.");
						}
					})
					.catch(error => {
						conosole.log(error);
					})
			}
		})
	})
})