function showwarehouse(){
document.querySelector('.modal').style.display = "block";
}


function closeModal(this) {
 this.closest('.modal').style.display = "none";
}