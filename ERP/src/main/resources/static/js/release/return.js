let tBody = document.querySelector("#tbody");
paging(0);
function paging(pg) {
    fetch("./refundpaging", {
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
        a
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='8'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            list.forEach(function(product) {
				 let btns = "";
                if (product.status === '대기') {
                    btns = `
                        <input type="button" value="폐기" onclick="discard(event, '${product.lotNumber}','${product.qty}')">
                        <input type="button" value="입고" onclick="receive(event, '${product.lotNumber}','${product.qty}')">
                    `;
                }
                
                html += `
                    <tr class='odd gradeX'>
                        <td><input type='checkbox' onclick='event.stopPropagation();'></td>
                        <td>${product.relNumber}</td>
                        <td>${product.salesName}</td>
                        <td>${product.supplierName}</td>
                        <td>${product.lotNumber}</td>
                        <td>${product.qty}</td>
                        <td>${product.price}</td>
                        <td>${product.manager}</td>
                        <td>${product.reason}</td>
                        <td>${product.status}</td>
                        <td>상품123출력</td>
                        <td>주문취소</td>
                        <td>${btns}</td>
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

function discard(e,lotNumber, productQty){
	e.stopPropagation();
	
}
function receive(e,lotNumber, productQty){
	e.stopPropagation();
	
}