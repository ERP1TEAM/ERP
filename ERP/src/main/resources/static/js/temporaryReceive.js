document.addEventListener("DOMContentLoaded", function() {
	const params = new URLSearchParams(window.location.search);
	const status = params.get("status");
	if (status === null || status === "입고대기") {
		let wait = document.getElementById("wait");
		wait.style.backgroundColor = "#007BFF";
		wait.style.color = "white";
	} else if (status === "입고완료") {
		let finish = document.getElementById("finish");
		finish.style.backgroundColor = "#007BFF";
		finish.style.color = "white";
	} else if (status === "입고반품") {
		let returns = document.getElementById("return");
		returns.style.backgroundColor = "#007BFF";
		returns.style.color = "white";
	}

	document.querySelector("#wait").addEventListener("click", function() {
		location.href = "./temporaryReceive?status=입고대기";
	})
	document.querySelector("#finish").addEventListener("click", function() {
		location.href = "./temporaryReceive?status=입고완료";
	})
	document.querySelector("#return").addEventListener("click", function() {
		location.href = "./temporaryReceive?status=입고반품";
	})
	/*
	document.querySelectorAll(".receiving").forEach(function(btn) {
		btn.addEventListener("click", function() {
			let idx = btn.getAttribute("data-code");
			let ea = document.getElementById(idx).value;
			if (!ea) {
				alert("입고수량을 입력하셔야 합니다.");
				return;
			}
			fetch("./receiving?data=" + idx + "&ea=" + ea, {
				method: "GET"
			})
				.then(response => response.text())
				.then(data => {
					if (data === "ok") {
						alert('입고가 완료되었습니다.');
						window.location.reload();
					} else if (data === "over") {
						alert("대기수량을 확인해주세요.");
					} else if (data === "no") {
						alert("입고등록에 실패하였습니다.");
					}
				})
				.catch(error => {
					conosole.log(error);
				})
		})
	})
	*/

	//입고버튼 클릭
	document.querySelectorAll(".receiving").forEach(function(btn) {
		btn.addEventListener("click", function() {
			const name = btn.getAttribute("data-name");
			const qty = btn.getAttribute("data-qty");
			const wqty = btn.getAttribute("data-wqty");
			receivingModal(name, qty, wqty);
		})
	})

	//모달닫기
	document.getElementById('closemodal').addEventListener('click', function() {
		document.getElementById('warehouselistmodal').style.display = 'none';
		document.getElementById('overlay').style.display = 'none';   // 오버레이 숨기기
		document.body.style.overflow = 'auto';  // 배경 스크롤 다시 활성화
	});
	
	//입고확정 모달 출력
	function receivingModal(name, qty, wqty) {
		fetch('./receivingModal?name=' + name + '&qty=' + qty + '&wqty=' + wqty, {
			method: 'GET'
		})
			.then(response => response.json())
			.then(data => {
				let warehousetbody = document.querySelector('#tbody2');
				warehousetbody.innerHTML = '';
				if(data.name.length > 20){
					data.name = data.name.slice(0,12) + "...";
				}
				
				
				let th = `<tr class="odd gradeX">
                    <td>${data.name}</td>
                    <td>${data.qty}</td>
                    <td>${data.wqty}</td>
                    <td><input type="text"></td>
                    <td><input type="text"></td>
                    <td><input type="text"></td>
                 </tr>`;
				warehousetbody.innerHTML += th;
				document.getElementById('warehouselistmodal').style.display = 'block';
				document.getElementById('overlay').style.display = 'block';   // 오버레이 숨기기
				document.body.style.overflow = 'hidden';  // 배경 스크롤 다시 활성화
			})
			.catch(function(error) {
				alert("error");
			});
	}
});
