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