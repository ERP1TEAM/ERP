document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('showinventory').style.display = 'none';
    fetch('/main/stock/locationstatus')
        .then(response => response.json())
        .then(data => {
            renderRackButtons(data);
        })
        .catch(error => {
            console.error('Error fetching location data:', error);
        });
});

function renderRackButtons(locations) {
    const locationContainer = document.querySelector('.location_frame1');
    locationContainer.innerHTML = '';

    const uniqueRacks = [...new Set(locations.map(location => location.rackCode))];  // 중복 제거 후 unique한 rackCode만 추출

    uniqueRacks.forEach(rackCode => {
        const rackButton = document.createElement('div');  // 변수가 함수 안에서 선언되어야 합니다.
        rackButton.className = 'location_large_controller';
        rackButton.textContent = rackCode;

        rackButton.addEventListener('click', function() {
            const activeButton = document.querySelector('.location_large_controller.active');
            if (activeButton) {
                activeButton.classList.remove('active');
            }

            this.classList.add('active');
            renderLocationDetails(locations, rackCode);
            document.getElementById('showinventory').style.display = 'none';
        });

        locationContainer.appendChild(rackButton);
});
}

// 선택한 rack_code에 따른 row_code와 level_code를 화면에 출력하는 함수
function renderLocationDetails(locations, selectedRack) {
    
    const detailContainer = document.querySelector('.location_frame3');  // Row와 Level 코드가 들어갈 컨테이너
    detailContainer.innerHTML = '';

    // 해당 rack_code에 속하는 row_code와 level_code를 찾음
    const filteredLocations = locations.filter(location => location.rackCode === selectedRack);

    // levelCode와 rowCode 기준으로 정렬
    filteredLocations.sort((a, b) => {
        if (a.levelCode == b.levelCode) {
            return a.rowCode.localeCompare(b.rowCode);  // 알파벳 순으로 정렬
        }
        return a.levelCode - b.levelCode;  // 숫자 기준으로 정렬
    });

    let currentLevel = null;
    let rowContainer = null;

    filteredLocations.forEach(location => {
        if (location.levelCode !== currentLevel) {
            // 새로운 층(level)이 시작되면 그 층의 row_code를 담을 컨테이너 생성
            rowContainer = document.createElement('div');
            rowContainer.className = 'location_level_row';
            detailContainer.appendChild(rowContainer);
            currentLevel = location.levelCode;
        }

        const rowLevel = `${location.levelCode}${location.rowCode}`;  // 예: 1A, 2B 등

        const smallBox = document.createElement('div');
        smallBox.className = 'location_small_box';
        smallBox.textContent = rowLevel;

         smallBox.addEventListener('click', function() {
            const activeSmallBox = document.querySelector('.location_small_box.active');
            if (activeSmallBox) {
                activeSmallBox.classList.remove('active');
            }
            this.classList.add('active');

            document.getElementById('showinventory').style.display = 'table';
            fetchInventoryData(location.code); 
        });

        rowContainer.appendChild(smallBox);
    });
}

function fetchInventoryData(locationCode) {
   
   fetch(`/main/stock/locationstatuslist/${locationCode}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            // 응답이 빈 경우 JSON으로 파싱하지 않고 빈 배열 반환
            return response.text().then(text => {
                return text ? JSON.parse(text) : [];
            });
        })
        .then(inventoryData => {
            renderInventoryTable(inventoryData);  // 데이터 렌더링 함수 호출
        })
        .catch(error => {
            console.error('Error fetching inventory data:', error);
        });
}

function renderInventoryTable(inventoryData) {
    const tableBody = document.querySelector('#showinventory tbody');
    tableBody.innerHTML = '';

	const filterData = inventoryData.filter(item => item.lotQuantity > 0);
	console.log(inventoryData);
	if (filterData.length == 0) {
        const emptyRow = document.createElement('tr');
        const emptyCell = document.createElement('td');

        emptyCell.colSpan = 5;  // 4개의 열을 합친 셀
        emptyCell.textContent = "해당 로케이션은 현재 비어있습니다.";
        emptyCell.style.textAlign = "center";

        emptyRow.appendChild(emptyCell);
        tableBody.appendChild(emptyRow);

        return;
    }


    filterData.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="lot-number-column">${item.lotNumber}</td>
            <td class="supplier-name-column">${item.supplierName}</td>
            <td class="product-name-column">${item.productName}</td>
            <td class="quantity-column">${item.lotQuantity}</td>
        `;
        tableBody.appendChild(row);
    });
}