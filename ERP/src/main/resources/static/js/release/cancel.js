let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns = new Paging();
window.clickPageBtn = function(pg) {
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
	pagingIns.getPage("./cancel/page",_page,_select,_param,_sd,_ed).then(result => {
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
function expand_post(thisElement,onum){
}
