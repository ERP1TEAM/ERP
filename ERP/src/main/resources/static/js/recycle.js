//엑셀불러오기
	/*
	document.querySelector("#excel_btn").addEventListener("click",function(){
		const fileInput = document.querySelector("#excel");
		const file = fileInput.files[0];
	
		if (!file) {
			alert("파일을 선택해 주세요.");
			return;
		}
	
		// 파일 확장자 체크
		const validExtensions = ['.xls', '.xlsx'];
		const fileExtension = file.name.split('.').pop().toLowerCase();
		if (!validExtensions.includes('.' + fileExtension)) {
			alert("엑셀 파일(.xls, .xlsx)만 업로드할 수 있습니다.");
			return;
		}
	    
		formData = new FormData(excel_frm);
	    
		fetch("./upload-excel",{
			method:"post",
			body:formData
		})
		.then(response => response.json())
		.then(data => {
			const tbody = document.getElementById('tbody');
			let resultHTML = '';
	
			data.forEach(item => {
				resultHTML += `<tr class="cb-table-tbody-data">
									<td><input type="text" name="product_code" value="${item.product_code}" readonly></td>
									<td><input type="text" name="supplier" value="${item.supplier}" readonly></td>
									<td><input type="text" name="product" value="${item.product}" readonly></td>
									<td><input type="text" name="quantity" value="${item.quantity}" readonly></td>
									<td><input type="text" name="price" value="${item.price}" readonly></td>
									<td><input type="text" name="total_price" value="${item.total_price}" readonly></td>
							   </tr>`;
			});
	
			tbody.innerHTML = resultHTML;
		})
		.catch(error => {
			console.log(error);
		})
	})
	*/
	
	//자동완성
	/*
	const input = document.getElementById('supplier');
	  const resultsContainer = document.getElementById('autocomplete-results');

	  input.addEventListener('input', function() {
		const query = this.value;

		if (query.length > 0) {
		  fetch(`./autocomplete?term=${encodeURIComponent(query)}`)
			.then(response => response.json())
			.then(data => {
			  resultsContainer.innerHTML = '';

			  data.forEach(item => {
				const div = document.createElement('div');
				div.classList.add('autocomplete-item');
				div.textContent = item.name;
				div.addEventListener('click', function() {
				  input.value = item.name;
				  resultsContainer.innerHTML = '';
				});
				resultsContainer.appendChild(div);
			  });
			})
			.catch(error => console.error('Error:', error));
		} else {
		  resultsContainer.innerHTML = '';
		}
	  });
	*/