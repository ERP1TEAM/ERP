let tBody = document.querySelector("#tbody");
paging(0);
function paging(pg) {
    fetch("./cancelpaging", {
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
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${release.number}")'>
                        <td><input type='checkbox' onclick='event.stopPropagation();'></td>
                        <td>${release.relNumber}</td>
                        <td>${release.orderNumber}</td>
                        <td>${release.salesName}</td>
                        <td>상품123출력</td>
                        <td>${release.manager}</td>
                        <td>${release.reason}</td>
                        <td><input type="button" value="삭제" onclick="remove(event,'${release.number}')"></td>
                    </tr>
                `;
            });
        }
       
        tBody.innerHTML = html;
    });
}
document.querySelector("#page-test").addEventListener("change",function(){
	paging(this.value-1);
});
function expand_post(thisElement,onum){
}

function remove(e, rnum){
	
}