let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns = new Paging();
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
	pagingIns.getPage("./cancel/page",_page,_select,_param).then(result => {
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
                    <tr class='odd gradeX' onclick='expand_post(this, "${release.number}")'>
                        <td>${release.relNumber}</td>
                        <td>${release.orderNumber}</td>
                        <td>${release.salesName}</td>
                        <td>${release.manager}</td>
                        <td>${release.reason}</td>
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
document.querySelector("#searchbtn").addEventListener("click",function(){
	let select=document.querySelector("#searchselect").value;
	let prm=document.querySelector("#searchtxt").value;
	paging(0,select,prm);
});
function expand_post(thisElement,onum){
}
