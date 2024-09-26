export default class Paging {
    currentPage_ = 0;
    totalPages_ = 0;
    pageNav = document.querySelector("#paging");
    select_=null;
    param_=null;
    mapping_="";
    startDate_=null;
    endDate_=null;

    async getPage(mapping, pg, select = null, param = null, StartDate=null,EndDate=null) {
		
		if(select!=null){
			this.select_=select;
		}
		if(param!=null){
			this.param_=param;
		}
		if(StartDate!=null){
			this.startDate_=StartDate;
		}
		if(EndDate!=null){
			this.endDate_=EndDate;
			
		}
        const queryParams = new URLSearchParams({
            pg: pg,
            select: this.select_,
            param: this.param_,
            startDate : this.startDate_,
            endDate: this.endDate_
        });

        const response = await fetch(`${mapping}?${queryParams}`, {
            method: "GET"
        });

        if (!response.ok) {
			this.select_="";
        	this.param_="";
            throw new Error('Network error');
            
        }
        
         //console.log("URL:", `${mapping}?${queryParams}`);

        const list = await response.json();
        this.mapping_=mapping;
        this.currentPage_=pg+1;
        this.totalPages_ = list.totalPages;
        return list;
    }

    appendPagingTag() {
        let html = `<ul class="pagination">`;
        if (this.currentPage_ > 10) {
            html += `
                <li class="page-item"><a class="page-link" aria-label="Previous" onclick="pgPrev()">
                    <span aria-hidden="true">&laquo;</span>
                </a></li>
            `;
        }

        let startPage = Math.floor((this.currentPage_ - 1) / 10) * 10 + 1;
        let endPage = Math.min(startPage + 9, this.totalPages_);

        for (let i = startPage; i <= endPage; i++) {
            const className = (i === this.currentPage_) ? 'page-item current-page' : 'page-item';
            html += `
                <li class="${className}"><a class="page-link" onclick="clickPageBtn(${i})">${i}</a></li>
            `;
        }

        if (endPage < this.totalPages_) {
            html += `
                <li class="page-item"><a class="page-link" aria-label="Next" onclick="pgNext()">
                    <span aria-hidden="true">&raquo;</span>
                </a></li>
            `;
        }

        html += `</ul>`;
        this.pageNav.innerHTML = html;
    }

    pgPrev() {
        if (this.currentPage_ > 10) {
             const newPage = Math.max(this.currentPage_ - 10, 1);
            this.currentPage_ = newPage;
            this.updatePaging();
        }
    }

    pgNext() {
        const maxPage = Math.ceil(this.totalPages_ / 10) * 10;
        if (this.currentPage_ < maxPage) {
            const newPage = Math.min(this.currentPage_ + 10, this.totalPages_);
            this.currentPage_ = newPage - (newPage % 10) + 1;
            this.updatePaging();
        }
    }

    async updatePaging() {
		
        this.appendPagingTag();
    }
    
    dateFormat(localDateTime){
		const date = new Date(localDateTime);
		const formattedDate = date.toLocaleString('ko-KR', {
		    year: 'numeric',
		    month: '2-digit',
		    day: '2-digit',
		    hour: '2-digit',
		    minute: '2-digit',
		    second: '2-digit',
		    hour12: false
		});
		return formattedDate;

		
		
	}
}
