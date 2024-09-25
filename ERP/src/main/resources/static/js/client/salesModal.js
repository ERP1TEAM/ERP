document.addEventListener('DOMContentLoaded', function() {

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
	});
	
	document.querySelector("#add_btn").addEventListener("click", function(){
		const formData = new FormData(sales_frm);
		console.log(formData.get("client_name"));
	})

});
