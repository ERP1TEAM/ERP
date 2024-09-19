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
            list.forEach(function(order) {
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${order.orderNumber}")'>
                        <td><input type='checkbox' onclick='event.stopPropagation();'></td>
                        <td>${order.orderNumber}</td>
                        <td>${order.salesName}</td>
                        <td>상품123출력</td>
                        <td>${order.manager}</td>
                        <td>${order.orderTotal}</td>
                        <td>주문취소</td>
                        <td><input type="button" value="삭제" onclick="approve(event,'${order.orderNumber}')"></td>
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