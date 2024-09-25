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
               document.getElementById('uploadStatus').innerText = result;  // 서버 응답 메시지 출력
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
           <th>주소 &nbsp;&nbsp; 
               <button type="button" class="btn btn-primary" style="border: none; height:30px; background-color:#dddddd; font-size: 12px; color: black;" 
               onclick="findPostcode('${recipientPostcodeId}', '${recipientAddressId}')">주소 찾기</button>
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
