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
                    </tr>`;
            });
            document.getElementById('orderProductDetails').innerHTML = productDetails;

            // 모달 표시
            var modal = document.getElementById('orderDetailModal');
            var instance = M.Modal.init(modal);
            instance.open();
        })
        .catch(error => {
            console.error('Error fetching order products:', error);
        });
}


//페이징 UI와 검색 기능을 구현
let currentPage = 0;
let totalPages = 0;

function filterOrders(page = 0) {
    const searchType = document.getElementById('searchOption').value;
    const searchText = document.getElementById('searchInput').value.toLowerCase();
    const searchDate = document.getElementById('searchDate').value;

    const size = 50;  // 한 페이지에 표시할 데이터 수

    fetch(`/sales/filter?searchType=${searchType}&searchText=${searchText}&searchDate=${searchDate}&page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            // 주문 목록을 업데이트
            const tbody = document.getElementById('orderTableBody');
            tbody.innerHTML = ''; // 기존 데이터 비우기
            data.content.forEach(order => {
                const row = `
                    <tr data-order-id="${order.orderId}">
                        <td class="center">${order.orderId}</td>
                        <td class="center">${order.name}</td>
                        <td class="center">${order.tel}</td>
                        <td class="center">${order.email}</td>
                        <td>[${order.post}] ${order.address} ${order.addressDetail}</td>
                        <td class="center">${order.orderDate}</td>
                        <td class="center">
                            <button type="button" class="btn btn-primary" style="background-color: #474B54; border: none;" onclick="showOrderDetails(this)">상세보기</button>
                        </td>
                    </tr>`;
                tbody.insertAdjacentHTML('beforeend', row);
            });

            // 페이징 정보 업데이트
            currentPage = data.number;
            totalPages = data.totalPages;
            updatePagination();
        })
        .catch(error => console.error('Error fetching filtered orders:', error));
}

// 페이징 UI 업데이트
function updatePagination() {
    const pagination = document.querySelector('.pagination');
    pagination.innerHTML = '';

    // < 버튼
    if (currentPage > 0) {
        pagination.innerHTML += `<li><a href="#" onclick="filterOrders(${currentPage - 1})">&lt;</a></li>`;
    }

    // 페이지 번호들
    for (let i = 0; i < totalPages; i++) {
        pagination.innerHTML += `<li><a href="#" onclick="filterOrders(${i})">${i + 1}</a></li>`;
    }

    // > 버튼
    if (currentPage < totalPages - 1) {
        pagination.innerHTML += `<li><a href="#" onclick="filterOrders(${currentPage + 1})">&gt;</a></li>`;
    }
}

 // 초기화
document.addEventListener('DOMContentLoaded', function() {
    filterOrders();  // 페이지 로드 시 첫 페이지 로드
});



// Materialize 초기화
document.addEventListener('DOMContentLoaded', function() {
    var modals = document.querySelectorAll('.modal');
    M.Modal.init(modals);
});
	
function closeModal() {
    var modal = document.getElementById('orderDetailModal');
    var instance = M.Modal.getInstance(modal);
    instance.close();
}