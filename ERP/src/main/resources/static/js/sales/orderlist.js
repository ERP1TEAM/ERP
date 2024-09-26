let currentPage = 0;
let totalPages = 0;

// 페이지 로드 시 세션 스토리지에서 저장된 값 복원
document.addEventListener('DOMContentLoaded', function() {
    loadStateFromSessionStorage(); // 세션 스토리지에서 상태 로드
    filterOrders(currentPage); // 페이지 로드 시 첫 페이지 로드
});

// 주문 목록 필터링 및 검색 기능
function filterOrders(page = 0) {
    const searchType = document.getElementById('searchOption').value;
    const searchText = document.getElementById('searchInput').value.toLowerCase();
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const size = 30;  // 한 페이지에 표시할 데이터 수

    // 현재 상태를 세션 스토리지에 저장 (여기서 page는 현재 페이지)
    saveStateToSessionStorage(searchType, searchText, startDate, endDate, page);

    fetch(`/sales/filter?searchType=${searchType}&searchText=${searchText}&startDate=${startDate}&endDate=${endDate}&page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('orderTableBody');
            tbody.innerHTML = ''; // 기존 데이터 비우기
            data.content.forEach(order => {
                const orderDate = new Date(order.orderDate).toLocaleDateString(); // JavaScript로 날짜 형식 변경

                const row = `
                    <tr data-order-id="${order.orderId}">
                        <td class="center">${order.orderId}</td>
                        <td>${order.name}</td>
                        <td class="center">${order.tel}</td>
                        <td>${order.email}</td>
                        <td>[${order.post}] ${order.address} ${order.addressDetail}</td>
                        <td class="center">${orderDate}</td>  
                        <td>${order.manager}</td>
                        <td class="center">
                            <button type="button" class="btn btn-primary" style="background-color: #474B54; border: none;" onclick="showOrderDetails(this)">상세보기</button>
                        </td>
                    </tr>`;
                tbody.insertAdjacentHTML('beforeend', row);
            });
            currentPage = data.currentPage;  // 서버에서 받은 현재 페이지로 업데이트
            totalPages = data.totalPages;  // 서버에서 받은 총 페이지 수로 업데이트
            updatePagination();
        })
        .catch(error => console.error('Error fetching filtered orders:', error));
}

// 페이징 UI 업데이트 함수
function updatePagination() {
    const pagination = document.querySelector('.pagination');
    pagination.innerHTML = '';
    const pagesPerGroup = 5; // 그룹당 5개의 페이지 버튼
    const currentGroup = Math.floor(currentPage / pagesPerGroup); // 현재 페이지 그룹

    const startPage = currentGroup * pagesPerGroup; // 현재 그룹의 시작 페이지
    const endPage = Math.min(startPage + pagesPerGroup, totalPages); // 현재 그룹의 마지막 페이지

    // < 버튼 (이전 그룹으로 이동)
    if (currentGroup > 0) {
        pagination.innerHTML += `<li><a href="#" onclick="filterOrders(${startPage - 1})">&lt;</a></li>`;
    }

    // 현재 그룹의 페이지 번호들
    for (let i = startPage; i < endPage; i++) {
        const activeClass = (i === currentPage) ? 'active' : '';
        pagination.innerHTML += `<li class="${activeClass}"><a href="#" onclick="filterOrders(${i})">${i + 1}</a></li>`;
    }

    // > 버튼 (다음 그룹으로 이동)
    if (endPage < totalPages) {
        pagination.innerHTML += `<li><a href="#" onclick="filterOrders(${endPage})">&gt;</a></li>`;
    }
}

// 상태를 세션 스토리지에 저장하는 함수
function saveStateToSessionStorage(searchType, searchText, startDate, endDate, page) {
    const state = {
        searchType: searchType,
        searchText: searchText,
        startDate: startDate,
        endDate: endDate,
        currentPage: page // 현재 페이지 저장
    };
    sessionStorage.setItem('orderListState', JSON.stringify(state));
}

// 세션 스토리지에서 상태를 로드하는 함수
function loadStateFromSessionStorage() {
    const savedState = sessionStorage.getItem('orderListState');
    if (savedState) {
        const state = JSON.parse(savedState);
        document.getElementById('searchOption').value = state.searchType;
        document.getElementById('searchInput').value = state.searchText;
        document.getElementById('startDate').value = state.startDate;
        document.getElementById('endDate').value = state.endDate;
        currentPage = state.currentPage || 0; // 세션 스토리지에서 저장된 currentPage 값 복원
    }
}


// 주문 상세보기 버튼 클릭 시 호출되는 함수
function showOrderDetails(button) {
    var orderId = button.closest('tr').getAttribute('data-order-id');
    document.getElementById('modalOrderId').textContent = '주문번호: ' + orderId;

    // Ajax 호출로 서버에서 주문 상품 데이터를 가져옴
    fetch(`/sales/${orderId}/products`)
        .then(response => response.json())
        .then(data => {
            var productDetails = '';
            var clientMemo = '';  // 수취인 메모
            var managerMemo = ''; // 관리자 메모

            // 상품 목록 처리
            data.forEach(product => {
                productDetails += `
                    <tr>
                        <td>${product.productCode}</td>
                        <td>${product.productName}</td>
                        <td>${product.qty}</td>
                        <td>${product.status ?? '처리중'}</td>  <!-- 처리현황 출력 -->
                    </tr>`;
                
                // 수취인 메모와 관리자 메모는 첫 번째 product에서만 가져옴
                if (!clientMemo && product.clientMemo) {
                    clientMemo = product.clientMemo;
                }
                if (!managerMemo && product.managerMemo) {
                    managerMemo = product.managerMemo;
                }
            });

            // 메모 출력 (forEach 바깥에서 한 번만 추가)
            productDetails += `
                <tr class="no-border">
                    <th>수취인 메모</th>
                    <td colspan="3">${clientMemo || '없음'}</td>  <!-- 수취인 메모 출력 -->
                </tr>
                <tr class="no-border">
                    <th>관리자 메모</th>
                    <td colspan="3">${managerMemo || '없음'}</td>  <!-- 관리자 메모 출력 -->
                </tr>`;

            document.getElementById('orderProductDetails').innerHTML = productDetails;
            // 모달 표시
            openModal();
        })
        .catch(error => {
            console.error('Error fetching order products:', error);
        });
}

// 모달을 열 때 호출되는 함수
function openModal() {
    var modal = document.getElementById('orderDetailModal');
    var backdrop = document.createElement('div');
    backdrop.className = 'modal-backdrop';
    document.body.appendChild(backdrop);

    modal.style.display = 'block';
    backdrop.style.display = 'block';
}

// 모달을 닫을 때 호출되는 함수
function closeModal() {
    var modal = document.getElementById('orderDetailModal');
    var backdrop = document.querySelector('.modal-backdrop');
    
    modal.style.display = 'none';
    if (backdrop) {
        backdrop.style.display = 'none';
        document.body.removeChild(backdrop);
    }
}

// 시작 날짜가 변경되었을 때의 이벤트 리스너
document.getElementById('startDate').addEventListener('change', function() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    // 종료 날짜가 선택되지 않았거나, 시작 날짜보다 이전일 경우
    if (!endDate || new Date(endDate) < new Date(startDate)) {
        document.getElementById('endDate').value = startDate;  // 종료 날짜를 시작 날짜로 설정
    }
});

// 종료 날짜가 변경되었을 때의 이벤트 리스너
document.getElementById('endDate').addEventListener('change', function() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    // 종료 날짜가 시작 날짜보다 이전일 경우
    if (!startDate | new Date(endDate) < new Date(startDate)) {
        document.getElementById('startDate').value = endDate;  // 시작 날짜를 종료 날짜로 설정
    }
});
