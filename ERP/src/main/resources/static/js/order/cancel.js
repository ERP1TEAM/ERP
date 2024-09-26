let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns= new Paging();
window.clickPageBtn = function(pg,sel,par) {
    console.log(pg);
    paging(pg-1, sel, par);
};
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};
document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_);
});
window.expand_post=expand_post;
function paging(_pg,_select,_param){
	pagingIns.getPage("./cancel/page",_pg,_select,_param).then(result => {
		let data = result.content;
		while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        let html = "";
        if (data.length === 0) {
            html = "<tr><td colspan='8'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            data.forEach(function(order) {
                html += `
                    <tr class='odd gradeX' onclick='expand_post(this, "${order.orderNumber}")'>
                        <td>${order.orderNumber}</td>
                        <td>${order.salesName}</td>
                        <td>${order.manager}</td>
                        <td>${pagingIns.dateFormat(order.dt)}</td>
                        <td>${order.orderTotal}</td>
                        <td><input type="button" value="삭제" onclick="approve(event,'${order.orderNumber}')"></td>
                    </tr>
                `;
            });
        }
        pagingIns.appendPagingTag();
        tBody.innerHTML = html;
		});
}

document.querySelector("#searchbtn").addEventListener("click",function(){
	let select=document.querySelector("#searchselect").value;
	let prm=document.querySelector("#searchtxt").value;
	paging(page,select,prm);
});

function expand_post(thisElement,onum){
}