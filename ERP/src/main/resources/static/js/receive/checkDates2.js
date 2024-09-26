const startDateInput = document.getElementById('start_date');
const endDateInput = document.getElementById('end_date');

// 날짜 변경 시 이벤트 핸들러
startDateInput.addEventListener('change', checkDates);
endDateInput.addEventListener('change', checkDates);

function checkDates() {
	let startDate2 = "";
	let endDate2 = "";
	
	if(startDateInput.value){
		startDate2 = new Date(startDateInput.value);
		const sdate2 = new Date(startDate2);
		const formattedsDate2 = sdate2.toISOString().split('T')[0];
		startDate = formattedsDate2;
	}else{
		startDate = "";
	}
	
	if(endDateInput.value){
		endDate2 = new Date(endDateInput.value);
		const edate2 = new Date(endDate2);
		const formattedeDate2 = edate2.toISOString().split('T')[0];
		endDate = formattedeDate2;
	}else{
		endDate = "";
	}
	
	// 만약 start_date가 end_date보다 크다면 두 날짜를 같게 설정
	if (startDate2 > endDate2) {
		endDateInput.value = startDateInput.value;
		endDate2 = startDate2;
	}
	// 만약 end_date가 start_date보다 작다면 두 날짜를 같게 설정
	else if (endDate2 < startDate2) {
		startDateInput.value = endDateInput.value;
		startDate2 = endDate2;
	}
	
	paging(1, searchCode, searchWord, startDate, endDate);
}