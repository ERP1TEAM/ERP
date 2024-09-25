   const fileInput = document.getElementById('fileInput');
   const fileUploadContainer = document.querySelector('.file-upload-container');
   const fileNameDisplay = document.getElementById('fileName');
   const uploadButton = document.getElementById('uploadButton');

   // 파일을 드래그 앤 드롭 또는 클릭으로 선택
   fileUploadContainer.addEventListener('click', function () {
       fileInput.click();
   });

   fileUploadContainer.addEventListener('dragover', function (e) {
       e.preventDefault();
       fileUploadContainer.style.borderColor = '#0000ff';
   });

   fileUploadContainer.addEventListener('dragleave', function (e) {
       e.preventDefault();
       fileUploadContainer.style.borderColor = '#cccccc';
   });

   fileUploadContainer.addEventListener('drop', function (e) {
       e.preventDefault();
       document.getElementById('uploadStatus').innerText = '';
       fileInput.files = e.dataTransfer.files;
       fileUploadContainer.style.borderColor = '#cccccc';
       displaySelectedFile();
   });

   fileInput.addEventListener('change', displaySelectedFile);

   // 파일명을 표시하고 업로드 버튼 활성화
   function displaySelectedFile() {
       const file = fileInput.files[0];
       if (file) {
           fileNameDisplay.textContent = `선택된 파일: ${file.name}`;
           uploadButton.disabled = false;
       } else {
           fileNameDisplay.textContent = '';
           uploadButton.disabled = true;
       }
   }

   // 파일 업로드 함수
   function uploadFile() {
       const file = fileInput.files[0];
       if (file) {
           const formData = new FormData();
           formData.append('file', file);

           fetch('./uploadFile', {
               method: 'POST',
               body: formData
           })
           .then(response => response.text())
           .then(result => {
               document.getElementById('uploadStatus').innerHTML = result;  // 서버 응답 메시지 출력 (innerHTML 사용)
           })
           .catch(error => {
               document.getElementById('uploadStatus').innerText = '파일 업로드 실패';
           });
       } else {
           alert('업로드할 파일을 선택해주세요.');
       }
   }
   
   
   let rowCount = 1;

   function addRow() {
       const table = document.getElementById("orderTable").getElementsByTagName('tbody')[0];

       // 새로운 행 추가
       const newRow1 = table.insertRow();
       const newRow2 = table.insertRow();

       // 각 행의 고유한 rowCount를 부여
       const currentRow = rowCount++;
       const productCodeId = `productCode-${currentRow}`;
       const productNameId = `productName-${currentRow}`;
       const recipientPostcodeId = `recipientPostcode-${currentRow}`;
       const recipientAddressId = `recipientAddress-${currentRow}`;
       const productQuantityId = `productQuantity-${currentRow}`;
       const orderDateId = `orderDate-${currentRow}`;
       const clientMemoId = `clientMemo-${currentRow}`;
       const adminMemoId = `adminMemo-${currentRow}`;

       // 첫 번째 행 생성 (수취인 정보)
       newRow1.innerHTML = `
           <th>수취인</th>
           <td><input type="text" id="recipientName-${currentRow}" placeholder="수취인"></td>
           <th>연락처</th>
           <td><input type="text" id="recipientPhone-${currentRow}" placeholder="수취인 연락처" maxlength="11"></td>
           <th>이메일</th>
           <td><input type="email" id="recipientEmail-${currentRow}" placeholder="수취인 이메일"></td>
           <th>주소
           </th>
           <td style="width:120px;">
               <input type="text" id="${recipientPostcodeId}" class="readonly" placeholder="우편번호" onclick="findPostcode('${recipientPostcodeId}', '${recipientAddressId}')" readonly>
           </td>
           <td>
               <input type="text" id="${recipientAddressId}" class="readonly" placeholder="도로명 주소" onclick="findPostcode('${recipientPostcodeId}', '${recipientAddressId}')" readonly>
           </td>
           <td>
               <input type="text" id="recipientDetailAddress-${currentRow}" placeholder="상세주소">
           </td>
       `;

       // 두 번째 행 생성 (상품 정보 및 메모)
       newRow2.innerHTML = `
           <th>상품코드</th>
           <td><input type="text" class="productCode" id="${productCodeId}" placeholder="상품코드" oninput="fetchProductName('${productCodeId}', '${productNameId}')"></td>
           <th>상품명</th>
           <td><input type="text" class="productName readonly" id="${productNameId}" placeholder="상품명" readonly></td>
           <th>주문갯수</th>
           <td><input type="number" id="${productQuantityId}" placeholder="주문 갯수"></td>
           <th>주문날짜</th>
           <td><input type="datetime-local" id="${orderDateId}"></td>
           <td colspan="1"><input type="text" id="${clientMemoId}" placeholder="수취인 메모" style="width: 100%;"></td>
           <td colspan="3"><input type="text" id="${adminMemoId}" placeholder="관리자 메모" style="width: 100%;"></td>
       `;
   }

   function removeRow() {
       const table = document.getElementById("orderTable").getElementsByTagName('tbody')[0];
       if (rowCount > 1) {
           // 마지막 2개의 행을 삭제
           table.deleteRow(-1);
           table.deleteRow(-1);
           rowCount--;
       }
   }

   // 주소 찾기
   function findPostcode(postcodeId, addressId) {
       new daum.Postcode({
           oncomplete: function(data) {
               // 전달된 id를 사용하여 해당 필드에 값을 설정
               document.getElementById(postcodeId).value = data.zonecode;
               document.getElementById(addressId).value = data.roadAddress;
           }
       }).open();
   }
   		// 주문상품코드를 입력하면 주문상품명을 자동으로 출력
   		async function fetchProductName(productCodeId, productNameId) {
   		    const productCode = document.getElementById(productCodeId).value;
   		    const productNameField = document.getElementById(productNameId);
   		
   		    // 8자리일 때만 서버에서 상품명을 조회
   		    if (productCode.trim().length === 8) {
   		        try {
   		            // 서버에서 상품 정보를 가져오는 API 호출
   		            const response = await fetch(`/sales/getProductByCode?code=${productCode}`);
   		
   		            if (response.ok) {
   		                const product = await response.json();
   		                productNameField.value = product.name; // 해당 행의 상품명 필드에 값 설정
   		            } else {
   		                productNameField.value = ''; // 상품명을 지움
   		                alert('해당 상품코드를 찾을 수 없습니다.');
   		            }
   		        } catch (error) {
   		            console.error('상품 정보를 가져오는 중 오류 발생:', error);
   		            alert('상품 정보를 가져오는 중 오류가 발생했습니다.');
   		        }
   		    } else {
   		        productNameField.value = '상품코드는 8자리입니다'; // 상품코드가 8자리가 아닌 경우
   		    }
   		}

           
   // 직접 주문 등록 버튼 클릭 시 데이터 전송
   async function submitOrder() {
       // 수집할 데이터들
       const orderData = [];
       const rows = document.querySelectorAll('#orderTable tbody tr');
       // 각 테이블 행의 데이터를 수집
       for (let i = 0; i < rows.length; i += 2) {
           const rowIndex = Math.floor(i / 2);  // 행 인덱스를 2로 나눔으로써 고유 ID 값 생성

           const recipientNameField = document.getElementById(`recipientName-${rowIndex}`);
           const recipientPhoneField = document.getElementById(`recipientPhone-${rowIndex}`);
           const recipientEmailField = document.getElementById(`recipientEmail-${rowIndex}`);
           const recipientPostcodeField = document.getElementById(`recipientPostcode-${rowIndex}`);
           const recipientAddressField = document.getElementById(`recipientAddress-${rowIndex}`);
           const recipientDetailAddressField = document.getElementById(`recipientDetailAddress-${rowIndex}`);
           const productCodeField = document.getElementById(`productCode-${rowIndex}`);
           const productNameField = document.getElementById(`productName-${rowIndex}`);
           const productQuantityField = document.getElementById(`productQuantity-${rowIndex}`);
           const clientMemoField = document.getElementById(`clientMemo-${rowIndex}`);
           const adminMemoField = document.getElementById(`adminMemo-${rowIndex}`);
   		   const orderDateField = document.getElementById(`orderDate-${rowIndex}`);
		// 필드가 null이거나 값이 빈 문자열일 경우 처리
		if (
		    !recipientNameField.value.trim() ||
		    !recipientPhoneField.value.trim() ||
		    !recipientEmailField.value.trim() ||
		    !recipientPostcodeField.value.trim() ||
		    !recipientAddressField.value.trim() ||
		    !recipientDetailAddressField.value.trim() ||
		    !productCodeField.value.trim() ||
		    !productNameField.value.trim() ||
		    !productQuantityField.value.trim() ||
		    !orderDateField.value.trim()
		) {
		    alert('모든 필드에 값을 입력해주세요.');
		    return; // 필드가 비어 있으면 함수 실행을 중단
		}
           // 각 주문의 상품 정보 구조화
           const products = [{
               productCode: productCodeField.value,
               productName: productNameField.value,
               qty: productQuantityField.value,
           }];
           const orderItem = {
                   name: recipientNameField.value,
                   tel: recipientPhoneField.value,
                   email: recipientEmailField.value,
                   post: recipientPostcodeField.value,
                   address: recipientAddressField.value,
                   addressDetail: recipientDetailAddressField.value,
                   products: products,
                   orderDate: orderDateField.value,
                   clientMemo: clientMemoField.value,
                   managerMemo: adminMemoField.value
               };

           orderData.push(orderItem);
       }

       // 서버로 POST 요청
       try {
           const response = await fetch('/sales/saveOrder', {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json',
               },
               body: JSON.stringify(orderData), // 배열 자체로 전송
           });

           const result = await response.json(); // 응답 메시지 가져오기

           if (response.ok) {
               alert(result.message); // 성공 메시지 표시
           } else {
               alert(result.message); // 실패 메시지 표시
           }
       } catch (error) {
           console.error('주문 등록 오류:', error);
           alert('서버와의 통신 중 오류가 발생했습니다.');
       }
	}
