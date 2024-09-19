let tBody = document.querySelector("#tbody");
paging(0);
function paging(pg) {
    fetch("./paging", {
        method: "POST",
        headers: { "content-type": "application/x-www-form-urlencoded" },
        body: "pg=" + encodeURIComponent(pg)
    })
    .then(function(response) {
        return response.json();
    })
    .then(function(list) {
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='8'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            list.forEach(function(release) {
				 let btns = "";
                if (release.status === '출고준비') {
                    btns = `
                        <input type="button" value="지연" onclick="postpone(event, '${release.number}')">
                        <input type="button" value="취소" onclick="cancel(event, '${release.number}')">
                        <input type="button" value="완료" onclick="complete(event, '${release.number}')">
                    `;
                } else if (release.status === '출고지연') {
                    btns = `
                        <input type="button" value="취소" onclick="cancel(event, '${release.number}')">
                        <input type="button" value="완료" onclick="complete(event, '${release.number}')">
                    `;
                }
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${release.number}")'>
                        <td><input type='checkbox' onclick='event.stopPropagation();'></td>
                        <td>${release.number}</td>
                        <td>${release.orderNumber}</td>
                        <td>${release.salesName}</td>
                        <td>상품123출력</td>
                        <td>${release.manager}</td>
                        <td>${release.status}</td>
                        <td>${btns}</td>
					</tr>
                `;
            });
        }
       
        tBody.innerHTML = html;
    });
}

function expand_post(thisElement,onum){
}

function postpone(e, rnum){
	e.stopPropagation();
		fetch("./postpone.do",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "id=" +encodeURIComponent(rnum)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(data){
				console.log(data);
				if(data=="OK"){
					window.location.reload();
					alert('지연 상태로 변경되었습니다');
					
				}else{
					alert('오류 발생으로 상태가 변경되지 않았습니다');
				}
				
			})
			.catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다');
			});	
	
}
function cancel(e, rnum){
	e.stopPropagation();
		fetch("./cancel.do",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "id=" +encodeURIComponent(rnum)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(data){
				console.log(data);
				if(data=="OK"){
					window.location.reload();
					alert('출고 취소되었습니다');
					
				}else{
					alert('오류 발생으로 상태가 변경되지 않았습니다');
				}
				
			})
			.catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다');
			});	
}
function complete(e, rnum){
	e.stopPropagation();
		fetch("./complete.do",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "id=" +encodeURIComponent(rnum)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(data){
				console.log(data);
				if(data=="OK"){
					window.location.reload();
					alert('출고 완료되었습니다');
					
				}else{
					alert('오류 발생으로 상태가 변경되지 않았습니다');
				}
				
			})
			.catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다');
			});	
}

document.querySelector("#page-test").addEventListener("change",function(){
	paging(this.value-1);
});