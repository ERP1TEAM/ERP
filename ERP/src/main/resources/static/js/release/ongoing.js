let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns= new Paging();
let post_opened=false;
let post_onum=null;
window.clickPageBtn = function(pg,sel,par) {
    console.log(pg);
    paging(pg-1, sel, par);
};
document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_);
});
window.expand_post=expand_post;
window.postpone=postpone;
window.cancelRelease=cancelRelease;
window.complete=complete;
window.onload = function() {
	pagingIns.currentPage_=localStorage.getItem("currentpg");
	pagingIns.mapping_=localStorage.getItem("mapping");
	pagingIns.param_=localStorage.getItem("param");
	pagingIns.totalPages_=localStorage.getItem("totalpg");
	pagingIns.select_=localStorage.getItem("select");
	paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};
document.querySelector("#all").addEventListener("click",function(){
	paging(0);
	
});
document.querySelector("#ready").addEventListener("click",function(){
	paging(0,"4","출고준비");
});
document.querySelector("#pone").addEventListener("click",function(){
	paging(0,"4","출고지연");
});

function paging(_page,_select,_param){
	pagingIns.getPage("./page",_page,_select,_param).then(result => {
		let list=result.content;
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
                        <input type="button" value="취소" onclick="cancelRelease(event, '${release.number}')">
                        <input type="button" value="완료" onclick="complete(event, '${release.number}')">
                    `;
                } else if (release.status === '출고지연') {
                    btns = `
                        <input type="button" value="취소" onclick="cancelRelease(event, '${release.number}')">
                        <input type="button" value="완료" onclick="complete(event, '${release.number}')">
                    `;
                }
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${release.number}")'>
                        <td>${release.number}</td>
                        <td>${release.orderNumber}</td>
                        <td>${release.salesName}</td>
                        <td>${release.manager}</td>
                        <td>${pagingIns.dateFormat(release.dt)}</td>
                        <td>${release.status}</td>
                        <td>${btns}</td>
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
		fetch("./detail",{
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
						ul.append(li1);
						ul.append(li4);
						ul.append(li2);
						ul.append(li3);
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
					localStorage.setItem("currentpg",pagingIns.currentPage_);
					localStorage.setItem("mapping",pagingIns.mapping_);
					localStorage.setItem("param",pagingIns.param_);
					localStorage.setItem("totalpg",pagingIns.totalPages_);
					localStorage.setItem("select",pagingIns.select_);
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
function cancelRelease(e, rnum){
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
					localStorage.setItem("currentpg",pagingIns.currentPage_);
					localStorage.setItem("mapping",pagingIns.mapping_);
					localStorage.setItem("param",pagingIns.param_);
					localStorage.setItem("totalpg",pagingIns.totalPages_);
					localStorage.setItem("select",pagingIns.select_);
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
					localStorage.setItem("currentpg",pagingIns.currentPage_);
					localStorage.setItem("mapping",pagingIns.mapping_);
					localStorage.setItem("param",pagingIns.param_);
					localStorage.setItem("totalpg",pagingIns.totalPages_);
					localStorage.setItem("select",pagingIns.select_);
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
document.querySelector("#searchbtn").addEventListener("click",function(){
	let select=document.querySelector("#searchselect").value;
	let prm=document.querySelector("#searchtxt").value;
	paging(0,select,prm);
});
