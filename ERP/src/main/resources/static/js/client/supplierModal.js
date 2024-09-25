document.addEventListener('DOMContentLoaded', function() {
	const supplierFrm = document.getElementById("supplier_frm");

	//로케이션 모달 열기	
	document.getElementById("locationbtn").addEventListener("click", function() {
		document.getElementById("locationoverlay").style.display = "block";
		document.getElementById("locationinmodal").style.display = "block";
		document.body.style.overflow = "hidden";
	});

	//로케이션 X버튼으로 모달 닫기
	document.getElementById("locationclosemodal").addEventListener("click", function() {
		document.getElementById("locationoverlay").style.display = "none";
		document.getElementById("locationinmodal").style.display = "none";
		document.body.style.overflow = 'auto';
	});
	//오버레이 클릭시 모달 닫기
	document.getElementById("locationoverlay").addEventListener("click", function() {
		document.getElementById("locationoverlay").style.display = "none";
		document.getElementById("locationinmodal").style.display = "none";
		document.body.style.overflow = 'auto';
	});

	supplierFrm.addEventListener("submit", function(event) {
		event.preventDefault(); // 기본 submit 동작 방지
	});

	document.querySelector("#add_btn").addEventListener("click", function(event) {
		event.preventDefault();
		const formData = new FormData(supplier_frm);

		const name = formData.get("name");
		const tel = formData.get("tel");
		const email = formData.get("email");
		const address = formData.get("address");
		const detailAddress = formData.get("detail_address");

		if (!name) {
			alert("발주처명을 입력하세요");
			return;
		}
		if (!tel) {
			alert("연락처를 입력하세요");
			return;
		}
		if (isNaN(tel)) {
			alert("연락처는 숫자만 가능합니다.");
			return;
		}
		if (!email) {
			alert("이메일을 입력하세요");
			return;
		}
		// 이메일 형식 검사
		const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test(email)) {
			alert("올바른 이메일 형식을 입력하세요");
			return;
		}
		if (!address) {
			alert("주소를 입력하세요");
			return;
		}
		// detailAddress가 있을 경우에만 추가
		if (detailAddress) {
			formData.set("address", `${address} (${detailAddress})`);
		} else {
			formData.set("address", address);
		}

		fetch("./addSupplier", {
			method: "post",
			body: formData
		})
			.then(response => response.text())
			.then(data => {
				if (data === "ok") {
					alert("발주처가 등록되었습니다.");
					window.location.reload();
				} else {
					alert("발주처 등록중 오류가 발생하였습니다.");
				}
			})
			.catch(error => {
				console.log(error);
				alert("발주처 등록중 오류가 발생하였습니다.");
			})

	})

});
