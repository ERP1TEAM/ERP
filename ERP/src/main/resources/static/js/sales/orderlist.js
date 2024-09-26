let currentPage = 0;
let totalPages = 0;

// 페이지 로드 시 로컬 스토리지에서 저장된 값 복원
document.addEventListener('DOMContentLoaded', function() {
    loadStateFromLocalStorage(); // 로컬 스토리지에서 상태 로드
    filterOrders(); // 페이지 로드 시 첫 페이지 로드
});

// 주문 목록 필터링 및 검색 기능
function filterOrders(page = 0) {
    const searchType = document.getElementById('searchOption').value;
    const searchText = document.getElementById('searchInput').value.toLowerCase();
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    const size = 30;  // 한 페이지에 표시할 데이터 수

    // 현재 상태를 로컬 스토리지에 저장
    saveStateToLocalStorage(searchType, searchText, startDate, endDate, page);

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
                        <td class="center">${order.name}</td>
                        <td class="center">${order.tel}</td>
                        <td class="center">${order.email}</td>
                        <td>[${order.post}] ${order.address} ${order.addressDetail}</td>
                        <td class="center">${orderDate}</td>  
                        <td>${order.managerMemo ?? ''}</td>
                        <td class="center">
                            <button type="button" class="btn btn-primary" style="background-color: #474B54; border: none;" onclick="showOrderDetails(this)">상세보기</button>
                        </td>
                    </tr>`;
                tbody.insertAdjacentHTML('beforeend', row);
            });

            currentPage = data.currentPage;
            totalPages = data.totalPages;
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

// 상태를 로컬 스토리지에 저장하는 함수
function saveStateToLocalStorage(searchType, searchText, startDate, endDate, page) {
    const state = {
        searchType: searchType,
        searchText: searchText,
        startDate: startDate,
        endDate: endDate,
        currentPage: page
    };
    localStorage.setItem('orderListState', JSON.stringify(state));
}

// 로컬 스토리지에서 상태를 로드하는 함수
function loadStateFromLocalStorage() {
    const savedState = localStorage.getItem('orderListState');
    if (savedState) {
        const state = JSON.parse(savedState);
        document.getElementById('searchOption').value = state.searchType;
        document.getElementById('searchInput').value = state.searchText;
        document.getElementById('startDate').value = state.startDate;
        document.getElementById('endDate').value = state.endDate;
        currentPage = state.currentPage || 0;
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
            data.forEach(product => {
                productDetails += `
                    <tr>
                        <td>${product.productCode}</td>
                        <td>${product.productName}</td>
                        <td>${product.qty}</td>
                        <td>${product.status ?? '처리중'}</td>  <!-- 처리현황 출력 -->
                    </tr>`;
            });
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
