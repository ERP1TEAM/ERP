document.addEventListener('DOMContentLoaded', function() {
    fetch('/main/stock/locationstatus')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            renderRackButtons(data);
        })
        .catch(error => {
            console.error('Error fetching location data:', error);
        });
});

// 로케이션 데이터를 받아와서 선반(R1, R2 등) 버튼을 동적으로 생성하는 함수
function renderRackButtons(locations) {
    const locationContainer = document.querySelector('.location_frame1');
    locationContainer.innerHTML = '';

    const uniqueRacks = [...new Set(locations.map(location => location.rackCode))];  // 중복 제거 후 unique한 rackCode만 추출

    uniqueRacks.forEach(rackCode => {
        const rackButton = document.createElement('div');
        rackButton.className = 'location_large_controller';
        rackButton.textContent = rackCode;

        // 클릭 이벤트: 해당 선반의 row_code와 level_code 결합하여 출력
        rackButton.addEventListener('click', function() {
            // 기존에 active 클래스를 가지고 있는 상자가 있다면 제거
            const activeButton = document.querySelector('.location_large_controller.active');
            if (activeButton) {
                activeButton.classList.remove('active');
            }

            // 현재 클릭한 상자에 active 클래스 추가
            this.classList.add('active');

            // 클릭한 선반에 맞는 row와 level 데이터를 출력
            renderLocationDetails(locations, rackCode);  
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

    // levelCode 기준으로 정렬 (1층이 가장 아래쪽에 위치하도록)
    filteredLocations.sort((a, b) => a.levelCode - b.levelCode);

    // 층마다 작은 상자를 만듦
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
            // 기존의 active smallbox가 있다면 해제
            const activeSmallBox = document.querySelector('.location_small_box.active');
            if (activeSmallBox) {
                activeSmallBox.classList.remove('active');
            }

            // 클릭한 smallbox에 active 추가
            this.classList.add('active');
        });

        rowContainer.appendChild(smallBox);  // row_container에 추가
    });
}