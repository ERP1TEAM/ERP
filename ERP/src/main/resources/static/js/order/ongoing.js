
let modal_opened=false;
let post_opened=false;
let post_onum=null;
let myModal = document.querySelector("#modal1");
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
            list.forEach(function(order) {
                let btns = "";
                if (order.status === '미승인') {
                    btns = `
                        <input type="button" value="승인" onclick="approve(event, '${order.number}')">
                        <input type="button" value="취소" onclick="cancel(event, '${order.number}')">
                    `;
                } else if (order.status === '승인') {
                    btns = `
                        <input type="button" value="취소" onclick="cancel(event, '${order.number}')">
                    `;
                }
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${order.number}")'>
                        <td><input type='checkbox' onclick='event.stopPropagation();'></td>
                        <td>${order.number}</td>
                        <td>${order.salesName}</td>
                        <td>상품123출력</td>
                        <td>${order.manager}</td>
                        <td>${order.orderTotal}</td>
                        <td>${order.status}</td>
                        <td>${btns}</td>
                    </tr>
                `;
            });
        }
       
        tBody.innerHTML = html;
    });
}


function expand_post(thisElement,onum){
	let post = document.getElementById("post-content");
	if (post) {
		    post.parentNode.removeChild(post);
		}
	if(post_opened&&post_onum==onum){
		post_opened=false;
	}else{
		post_opened=true;
		post_onum=onum;
		fetch("./detailok.do",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "orderNum="+encodeURIComponent(onum)
			})
			.then(function(response){
				return response.json();
			})
			.then(function(result){
				let tr = document.createElement("tr");
				tr.id="post-content";
				let td = document.createElement("td");
					result.forEach(data => {
					    let ul = document.createElement("ul");
						let li1 = document.createElement("li");
						li1.innerText="상품 : "+data.productName+"("+data.productCode+")";
						let li2 = document.createElement("li");
						li2.innerText="발주처 : "+data.supplierName+"("+data.supplierCode+")";
						let li3 = document.createElement("li");
						li3.innerText="수량 : "+data.qty;
						let li4 = document.createElement("li");
						li4.innerText="가용재고 : "+data.availableQty;
						let li5 = document.createElement("li");
						li5.innerText="금액 : "+data.price;
						let li6 = document.createElement("li");
						li6.innerText="총액 : "+data.price*data.qty;
						let li7 = document.createElement("li");
						let btn = document.createElement("input");
						btn.type="button";
						btn.value="입고신청";
						btn.addEventListener("click",function(e){
							e.stopPropagation();
							if(modal_opened){
								close_modal();
							}else{
								open_modal(data);
							}
						});
						li7.append(btn);
						ul.append(li1);
						ul.append(li2);
						ul.append(li3);
						ul.append(li4);
						ul.append(li5);
						ul.append(li6);	
						ul.append(li7);
						td.colSpan="8";
						td.append(ul);
					});
				tr.append(td);
				var parent = thisElement.parentNode;
			    var nextSibling = thisElement.nextSibling;
			    if (nextSibling) {
			        parent.insertBefore(tr, nextSibling);
			    } else {
			        parent.appendChild(tr);
			    }
			})
			.catch(function(error){
				alert('오류 발생으로 상품 정보를 불러오지 못했습니다.');
			});	
	}
}

function approve(e,num){
	
	e.stopPropagation();
			fetch("./approveok.do",{
				method : "PUT",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "id=" +encodeURIComponent(num)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(data){
				console.log(data);
				if(data=="OK"){
					window.location.reload();
					alert('해당 주문이 승인되었습니다');
					
				}else{
					alert('오류 발생으로 상태가 변경되지 않았습니다');
				}
				
			})
			.catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다');
			});	
}

function cancel(e,num){
	
	e.stopPropagation();
			fetch("./cancelok.do",{
				method : "PUT",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "id=" +encodeURIComponent(num)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(data){
				if(data=="OK"){
					window.location.reload();
					alert('해당 주문이 취소되었습니다');
					
				}else{
					alert('오류 발생으로 상태가 변경되지 않았습니다');
				}
				
			})
			.catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다');
			});	
}

function open_modal(product){
	document.getElementById("pdt").innerText=product.productName;
	document.getElementById("sup").innerText=product.supplierName;
	document.getElementById("qty").innerText=product.qty;
	document.getElementById("avqty").innerText=product.availableQty;
	//document.getElementById("memo").innerText="아직";
	document.getElementById("reqPdt").value=product.productCode;
	myModal.style="display:block;";
	modal_opened=true;
}

function close_modal(){
	myModal.style="display:none;";
	modal_opened=false;
}

document.querySelector("#reqOk").addEventListener("click",function(){
	fetch("./requestReceptionok.do",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "reqNum=" +encodeURIComponent(document.getElementById("reqNum").value)+"&reqPdt="+encodeURIComponent(document.getElementById("reqPdt").value)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(data){
				if(data=="OK"){
					window.location.reload();
					alert('입고 요청되었습니다.');
					
				}else{
					myModal.style="display:none;";
					modal_opened=false;
					alert('오류 발생으로 입고 요쳥 실패하였습니다');
				}
			})
			.catch(function(error){
				myModal.style="display:none;";
				modal_opened=false;
				alert('오류 발생으로 입고 요쳥 실패하였습니다');
			});	
	
});
document.querySelector("#page-test").addEventListener("change",function(){
	paging(this.value-1);
});
document.querySelector("#reqNo").addEventListener("click",function(){
	myModal.style="display:none;";
	modal_opened=false;
});
