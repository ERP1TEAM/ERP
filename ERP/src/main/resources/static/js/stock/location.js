function r_button(button){
	
	var rackbutton = document.querySelectorAll('.rackbutton');
    rackbutton.forEach(function(button) {
        button.classList.remove('r_active');
    });
	
	button.classList.toggle('r_active');
	
	var locationbutton = document.querySelectorAll('.locationbutton');
    locationbutton.forEach(function(button) {
        button.classList.remove('l_active');
    });
	
	var showbutton= document.getElementById("showbutton");
	showbutton.style.display="block";
}

function l_button(button){
	
	var locationbutton = document.querySelectorAll('.locationbutton');
    locationbutton.forEach(function(button) {
        button.classList.remove('l_active');
    });
	
	button.classList.toggle('l_active');
}