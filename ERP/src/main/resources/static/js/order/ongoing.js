let modal_opened=false;
let post_opened=false;
let post_onum=null;
let myModal = document.querySelector("#modal1");
let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns= new Paging();
window.clickPageBtn = function(pg) {
    paging(pg-1);
};

document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_);
});
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1);
};
window.approve=approve;
window.cancelOrder=cancelOrder;
window.expand_post=expand_post;
document.querySelector("#all").addEventListener("click",function(){
	paging(0,"null","null","null","null");
	
});
document.querySelector("#apv").addEventListener("click",function(){
	paging(0,"4","승인","null","null");
});
document.querySelector("#wait").addEventListener("click",function(){
	paging(0,"4","미승인","null","null");
});
window.onload = function() {
	paging(pagingIns.currentPage_ - 1);
};
function paging(_page,_select,_param,_sd,_ed){
	pagingIns.getPage("./page",_page,_select,_param,_sd,_ed).then(result => {
		let list = result.content;
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='8' style='text-align:center;'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            list.forEach(function(order) {
                let btns = "";
                let approveDt="";
                if (order.status.toString() === '미승인') {
                    btns = `
                        <input type="button" value="승인" onclick="approve(event, '${order.number}')">
                        <input type="button" value="취소" onclick="cancelOrder(event, '${order.number}')">
                    `;
                }else if(order.status.toString() === '승인'){
					approveDt=`${pagingIns.dateFormat(order.approvedDt)}`;
					
				}
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${order.number}")'>
                        <td style="text-align:center;">${order.number}</td>
                        <td>${order.salesName}</td>
                        <td style="text-align:center;">${order.manager}</td>
                        <td style="text-align:center;">${pagingIns.dateFormat(order.dt)}</td>
                        <td style="text-align:center;">${approveDt}</td>
                        <td style="text-align:right;">${order.orderTotal.toLocaleString()}</td>
                        <td style="text-align:center;">${order.status}</td>
                        <td style="text-align:center;">${btns}</td>
                    </tr>
                `;
            });
        }
        pagingIns.appendPagingTag();
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
						li5.innerText="금액 : "+data.price.toLocaleString();
						let li6 = document.createElement("li");
						li6.innerText="총액 : "+(data.price*data.qty).toLocaleString();
						let li7 = document.createElement("li");
						let btn = document.createElement("input");
						btn.type="button";
						btn.value="발주요청";
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
				if(data=="OK"){
					window.location.reload();
					alert('해당 주문이 승인되었습니다');
					
				}else if("STOCKERROR"){
					alert('재고가 부족하여 승인되지 않았습니다');
					}else{
					alert('오류 발생으로 상태가 변경되지 않았습니다');
				}
				
			})
			.catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다');
			});	
}
function cancelOrder(e,num){
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
	document.getElementById("reqPrice").value=product.price;
	document.getElementById("reqPdt").value=product.productCode;
	myModal.style="display:block;";
	modal_opened=true;
}

function close_modal(){
	myModal.style="display:none;";
	modal_opened=false;
}

document.querySelector("#reqOk").addEventListener("click",function(){
	var req_qty = document.querySelector("#reqNum").value;
	var req_pdt_name = document.querySelector("#pdt").innerText;
	var req_pdt_code = document.querySelector("#reqPdt").value;
	var req_sup_name = document.querySelector("#sup").innerText;
	var req_price = document.querySelector("#reqPrice").value;
	let formData = new FormData();
	alert(req_pdt_code);
	formData.set("product_code", req_pdt_code);
	formData.set("supplier", req_sup_name);
	formData.set("product", req_pdt_name);
	formData.set("quantity", req_qty);
	formData.set("price", req_price);
	formData.set("total_price", req_qty*req_price);
	fetch("../receive/purchaseAdd",{
				method: "POST",
			body: formData
		})
			.then(response => response.text())
			.then(data => {
				if (data === "success") {
					alert("발주등록 되었습니다.");
					location.reload();
				} else {
					alert("오류로 인하여 발주등록에 실패하였습니다.");
				}
			})
			.catch(error => {
				alert("오류로 인하여 발주등록에 실패하였습니다.");
			})
});
document.querySelector("#reqNo").addEventListener("click",function(){
	close_modal();
});
 document.getElementById('frm').addEventListener('submit', function(event) {
        event.preventDefault();
    });

document.querySelector("#searchbtn").addEventListener("click",function(event){
	let StartDate = document.querySelector("#start_date").value;
	let EndDate = document.querySelector("#end_date").value;
	let select=document.querySelector("#searchselect").value;
	let prm=document.querySelector("#searchtxt").value;
	if(StartDate>EndDate||(StartDate==null&&EndDate!=null)||(StartDate!=null&&EndDate==null)){
		alert("탐색할 날짜를 확인하세요.");
	}else{
		if(prm==""||prm==null){
			paging(0, "", prm,StartDate,EndDate);
		}else{
			paging(0, select, prm,StartDate,EndDate);
		}
		
	}
	
});