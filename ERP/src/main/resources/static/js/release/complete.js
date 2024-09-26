let tBody = document.querySelector("#tbody");
let post_opened=false;
let post_onum=null;
import Paging from '../module/paging.js';
const pagingIns= new Paging();
window.clickPageBtn = function(pg,sel,par) {
    console.log(pg);
    paging(pg-1, sel, par);
};
document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_);
});

window.expand_post=expand_post;
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};
function paging(_page,_select,_param){
	pagingIns.getPage("./complete/page",_page,_select,_param).then(result => {
		let list = result.content;
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='8'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            list.forEach(function(release) {
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${release.relNumber}")'>
                        <td>${release.relNumber}</td>
                        <td>${release.orderNumber}</td>
                        <td>${release.salesName}(${release.salesCode})</td>
                        <td>${release.manager}</td>
                        <td>${pagingIns.dateFormat(release.dt)}</td>
                        <td></td>
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
		fetch("./completedetail",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "rNum="+encodeURIComponent(onum)
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
						let li4 = document.createElement("li");
						li4.innerText="LOT번호 : "+data.lotNumber;
						let li2 = document.createElement("li");
						li2.innerText="발주처 : "+data.supplierName+"("+data.supplierCode+")";
						let li3 = document.createElement("li");
						li3.innerText="수량 : "+data.qty;
						let li5 = document.createElement("li");
						let span = document.createElement("span");
						span.innerText="반품할 수량을 입력하세요 : ";
						let inp = document.createElement("input");
						inp.type="number";
						let btn = document.createElement("input");
						btn.type="button";
						btn.value="반품";
						btn.addEventListener("click",function(){
							returnProduct(data.relNumber,data.lotNumber,data.qty);
						});
						li5.append(span);
						li5.append(inp);
						li5.append(btn);
						li3.innerText="수량 : "+data.qty;
						ul.append(li1);
						ul.append(li4);
						ul.append(li2);
						ul.append(li3);
						ul.append(li5);
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
window.returnProduct = function(rCode,lCode,qty) {
    fetch("./return/add",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "rCode="+encodeURIComponent(rCode)+"&lCode="+encodeURIComponent(lCode)
				+"&qty="+encodeURIComponent(qty)+"&reason="+encodeURIComponent(reason)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(result){
				if(result="OK"){
					alert("반품 처리가 완료되었습니다");
					
				}
				
			}).catch(function(error){
				alert('오류 발생으로 반품 처리 하지 못했습니다.');
			});	
};
document.querySelector("#searchbtn").addEventListener("click",function(){
	let select=document.querySelector("#searchselect").value;
	let prm=document.querySelector("#searchtxt").value;
	paging(0,select,prm);
});
