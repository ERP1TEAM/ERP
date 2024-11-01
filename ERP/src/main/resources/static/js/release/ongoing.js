let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns= new Paging();
let post_opened=false;
let post_onum=null;
window.clickPageBtn = function(pg) {
    paging(pg-1);
};
document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_);
});
window.expand_post=expand_post;
window.postpone=postpone;
window.cancelRelease=cancelRelease;
window.complete=complete;
window.onload = function() {
	paging(pagingIns.currentPage_ - 1);
};
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1);
};
document.querySelector("#all").addEventListener("click",function(){
	paging(0,"null","null","null","null");
	
});
document.querySelector("#ready").addEventListener("click",function(){
	paging(0,"4","출고준비","null","null");
});
document.querySelector("#pone").addEventListener("click",function(){
	paging(0,"4","출고지연","null","null");
});

function paging(_page,_select,_param,_sd,_ed){
	pagingIns.getPage("./page",_page,_select,_param,_sd,_ed).then(result => {
		let list=result.content;
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='8' style='text-align:center;'>데이터가 존재하지 않습니다.</td></tr>";
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
                        <td style="text-align:center;">${release.number}</td>
                        <td style="text-align:center;">${release.orderNumber}</td>
                        <td>${release.salesName}</td>
                        <td style="text-align:center;">${release.manager}</td>
                        <td style="text-align:center;">${pagingIns.dateFormat(release.dt)}</td>
                        <td style="text-align:center;">${release.status}</td>
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
