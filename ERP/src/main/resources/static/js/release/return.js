let tBody = document.querySelector("#tbody");
import Paging from '../module/paging.js';
const pagingIns= new Paging();
window.clickPageBtn = function(pg,sel,par) {
    console.log(pg);
    paging(pg-1, sel, par);
};
document.addEventListener("DOMContentLoaded", function() {
    paging(pagingIns.currentPage_-1);
});
window.pgNext = function() {
    pagingIns.pgNext();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};

window.pgPrev = function() {
    pagingIns.pgPrev();
     paging(pagingIns.currentPage_ - 1, pagingIns.select_, pagingIns.param_);
};
function paging(_page,_select,_param){
	pagingIns.getPage("./return/page",_page,_select,_param).then(result => {
        let list=result.content;
        while (tBody.firstChild) {
            tBody.removeChild(tBody.firstChild);
        }
        let html = "";
        if (list.length === 0) {
            html = "<tr><td colspan='12' style='text-align:center;'>데이터가 존재하지 않습니다.</td></tr>";
        } else {
            list.forEach(function(product) {
				 let btns = "";
                if (product.status === '대기') {
                    btns = `
                    	<input type="number" name="renum">
                        <input type="button" value="폐기" onclick="discardp(event, '${product.relNumber}','${product.lotNumber}')">
                        <input type="button" value="입고" onclick="receivep(event, '${product.relNumber}','${product.lotNumber}')">
                    `;
                }
                html += `
                    <tr class='odd gradeX'>
                        <td>${product.relNumber}</td>
                        <td>${product.productName}(${product.productCode})</td>
                        <td>${product.supplierName}</td>
                        <td>${product.lotNumber}</td>
                        <td>${product.qty}</td>
                        <td>${pagingIns.dateFormat(product.dt)}</td>
                        <td>${product.manager}</td>
                        <td>${product.status}</td>
                        <td>${btns}</td>
                    </tr>
                `;
            });
        }
       pagingIns.appendPagingTag();
        tBody.innerHTML = html;
    });
}
window.discardp = function(e,relNumber,lotNumber){
	e.stopPropagation();
	fetch("./return/discard",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "relNum="+encodeURIComponent(relNumber)+"&lotNum="+encodeURIComponent(lotNumber)+"&qty="+encodeURIComponent( e.target.parentElement.querySelector('input[name="renum"]').value)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(result){
				if(result=="OK"){
					window.location.reload();
					alert('해당 상품이 모두 폐기되었습니다.');
				}else{
					alert('오류 발생으로 상태를 변경하지 못했습니다.');
				}
			})
			.catch(function(error){
				alert('오류 발생으로 상태를 변경하지 못했습니다.');
			});	
	
}
window.receivep = function(e,relNumber,lotNumber){
	e.stopPropagation();
	fetch("./return/receive",{
				method : "POST",
				headers : {"content-type":"application/x-www-form-urlencoded"},
				body : "relNum="+encodeURIComponent(relNumber)+"&lotNum="+encodeURIComponent(lotNumber)+"&qty="+encodeURIComponent( e.target.parentElement.querySelector('input[name="renum"]').value)
			})
			.then(function(response){
				return response.text();
			})
			.then(function(result){
				if(result=="OK"){
					window.location.reload();
					alert('해당 상품이 모두 입고되었습니다.');
				}else{
					alert('오류 발생으로 상태를 변경하지 못했습니다.');
				}
			})
			.catch(function(error){
				alert('오류 발생으로 상태를 변경하지 못했습니다.');
			});	
	
	
}
document.querySelector("#searchbtn").addEventListener("click",function(){
	alert('서비스 준비중 입니다.');
	/*
	let select=document.querySelector("#searchselect").value;
	let prm=document.querySelector("#searchtxt").value;
	paging(0,select,prm);
	*/
});
