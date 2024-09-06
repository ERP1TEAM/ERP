document.addEventListener("DOMContentLoaded",function(){
	const input = document.getElementById('autocomplete-input');
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
	
	const input_qty = document.getElementById("quantity");
	const price = document.getElementById("price");
	const tt_price = document.getElementById("total_price");
	input_qty.addEventListener("input",function(){
		tt_price.value = this.value * price.value;
	})
	
	document.querySelector("#purchase_request").addEventListener("click",function(){
		formData = new FormData(order_frm);
		
		function hasEmptyFields(formData) {
	        let hasEmpty = false;
	        formData.forEach((value, key) => {
	            if (!value.trim()) { // trim()으로 공백 제거 후 빈 값 확인
	                hasEmpty = true;
	            }
	        });
	        return hasEmpty;
	    }
	    
	    if (hasEmptyFields(formData)) {
	        alert("모든 필드를 채워주세요.");
	        return; // 빈 값이 있으면 요청을 중단합니다.
	    }
	    
		fetch("./purchaseAdd",{
			method:"POST",
			body:formData
		})
		.then(response => response.text())
		.then(data => {
			alert(data);
		})
		.catch(error => {
			console.log(error);
		})
	})
	
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
	
	// 모달 관련 요소
  const modal = document.getElementById('myModal');
  const openModalBtn = document.getElementById('open-modal-btn');
  const closeModalBtn = document.getElementById('close-modal-btn');
  const modalItems = document.querySelectorAll('.modal-item');
  const selectedData = document.getElementById('selected-data');
  const body = document.body;

  // 모달 열기
  openModalBtn.addEventListener('click', () => {
    modal.style.display = 'block';
    overlay.style.display = 'block';
    body.classList.add('no-scroll');
  });

  // 모달 닫기
  closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
    overlay.style.display = 'none';
    body.classList.remove('no-scroll');
  });

  // 모달 외부 클릭 시 닫기
  window.addEventListener('click', (event) => {
    if (event.target === modal) {
      modal.style.display = 'none';
      overlay.style.display = 'none';
      body.classList.remove('no-scroll');
    }
  });

  // 모달 아이템 클릭 시 데이터 반영
  modalItems.forEach(item => {
    item.addEventListener('click', (event) => {
      const value = event.target.getAttribute('data-value');
      selectedData.value = value;
      console.log(selectedData.value);
      body.classList.remove('no-scroll');
      overlay.style.display = 'none';
      modal.style.display = 'none';
    });
  });
})

