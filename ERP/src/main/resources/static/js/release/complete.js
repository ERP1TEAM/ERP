let tBody = document.querySelector("#tbody");
let post_opened=false;
let post_onum=null;
import Paging from '../module/paging.js';
const pagingIns= new Paging();
window.clickPageBtn = function(pg) {
    console.log(pg);
    paging(pg-1);
};
document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_-1);
});

window.expand_post=expand_post;
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1);
};
function paging(_page,_select,_param,_sd,_ed){
	pagingIns.getPage("./complete/page",_page,_select,_param,_sd,_ed).then(result => {
		let list = result.content;
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='8' style='text-align:center;'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            list.forEach(function(release) {
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${release.relNumber}")'>
                        <td style="text-align:center;">${release.relNumber}</td>
                        <td style="text-align:center;">${release.orderNumber}</td>
                        <td>${release.salesName}(${release.salesCode})</td>
                        <td style="text-align:center;">${release.manager}</td>
                        <td style="text-align:center;">${pagingIns.dateFormat(release.dt)}</td>
                        <td style="text-align:center;"></td>
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
						
						console.log(data.returnFlag);
						
						if(data.returnFlag=="Y"){
							let span= document.createElement("span");
							span.innerText="[반품 처리된 상품입니다]";
							let br= document.createElement("br");
							let br2= document.createElement("br");
						li5.append(span);
						li5.append(br);
						li5.append(br2);
						
						}else{
							let span = document.createElement("span");
						span.innerText="반품할 수량을 입력하세요 : ";
						let inp = document.createElement("input");
						inp.type="number";
						inp.maxLength=9999;
						let btn = document.createElement("input");
						btn.type="button";
						btn.value="반품";
						btn.addEventListener("click",function(){
							returnProduct(data.relNumber,data.lotNumber,inp.value);
						});
						let br= document.createElement("br");
						
						
						li5.append(span);
						li5.append(inp);
						li5.append(btn);
						li5.append(br);
						
						}
						
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
				+"&qty="+encodeURIComponent(qty)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(result){
				if(result="OK"){
					window.location.reload();
					alert("반품 처리가 완료되었습니다");
					
				}else{
					alert("오류 발생으로 상태가 변경되지 않았습니다.");
				}
				
			}).catch(function(error){
				alert('오류 발생으로 상태가 변경되지 않았습니다.');
			});	
};
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
