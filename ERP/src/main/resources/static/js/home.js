// 서버에서 사용자 이름을 가져와 페이지에 표시하는 함수
async function fetchUserName() {
    try {
        const response = await fetch('/api/user-info', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include' // 쿠키를 포함하여 요청
        });

        if (response.ok) {
            const data = await response.json();
            document.getElementById('manager').value = data.name; // 사용자 이름 설정
        } else {
            console.error('Failed to fetch user info');
        }
    } catch (error) {
        console.error('Error fetching user info:', error);
    }
}
// 토큰에서 사용자 이름을 가져오기
async function fetchCompanyCode() {
    try {
        const response = await fetch('/api/code-info', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include' // 쿠키를 포함하여 요청
        });

        if (response.ok) {
            const data = await response.json();
            document.getElementById('companyCode').value = data.code; // 사용자 소속사 코드
        } else {
            console.error('Failed to fetch user info');
        }
    } catch (error) {
        console.error('Error fetching user info:', error);
    }
}

// 공지사항 목록 필터링 및 페이징 처리 함수
function filterNotices(page = 0) {
    const size = 10;  // 한 페이지에 표시할 공지사항 수

    fetch(`./filterNotices?page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('noticeTableBody');
            tbody.innerHTML = '';  // 기존 데이터 비우기

            data.content.forEach(notice => {
                const row = `
                    <tr>
                        <td class="center">${notice.no}</td>
                        <td><a href="./noticeView/${notice.id}">${notice.title}</a></td>
                        <td>${notice.manager}</td>
                        <td class="center">${notice.views}</td>
                        <td class="center">${new Date(notice.createdAt).toLocaleDateString()}</td>
                    </tr>`;
                tbody.insertAdjacentHTML('beforeend', row);
            });

            // 페이징 UI 업데이트
            currentPage = data.number;
            totalPages = data.totalPages;
            updatePagination();
        })
        .catch(error => console.error('Error fetching notices:', error));
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
        pagination.innerHTML += `<li><a href="#" onclick="filterNotices(${startPage - 1})">&lt;</a></li>`;
    }

    // 현재 그룹의 페이지 번호들
    for (let i = startPage; i < endPage; i++) {
        const activeClass = (i === currentPage) ? 'active' : '';
        pagination.innerHTML += `<li class="${activeClass}"><a href="#" onclick="filterNotices(${i})">${i + 1}</a></li>`;
    }

    // > 버튼 (다음 그룹으로 이동)
    if (endPage < totalPages) {
        pagination.innerHTML += `<li><a href="#" onclick="filterNotices(${endPage})">&gt;</a></li>`;
    }
}


// 초기화: 페이지 로드 시 첫 페이지 로드
document.addEventListener('DOMContentLoaded', function() {
	fetchCompanyCode(); //로그인 관리자 정보
	fetchUserName();	//로그인 관리자 정보
    filterNotices();  // 첫 페이지 로드
});

var calendar;  // 전역 변수로 선언

// 메모 저장 함수 (서버에 저장 후 캘린더에 추가)
function saveMemoToServer(title, start, end) {
    const companyCode = document.getElementById("companyCode").value; 
    const manager = document.getElementById("manager").value; 
    
    fetch('/api/memos/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            title: title,
            startDate: start,
            endDate: end,
            code: companyCode,
            manager: manager 
        }),
    })
    .then(response => response.json())
    .then(data => {
        // calendar가 초기화된 후에만 addEvent 호출
        if (calendar) {
            calendar.addEvent({
                id: data.id, // 서버에서 반환된 ID
                title: data.title,
                start: data.startDate,
                end: data.endDate,
                allDay: true,
                extendedProps: { 
                    manager: data.manager // 서버에서 받은 데이터로 추가
                }
            });
        } else {
            console.error('Calendar is not initialized.');
        }
        console.log('메모 등록 완료')
    })
    .catch(error => console.error('Error saving memo:', error));
}

// FullCalendar 초기화
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: [], 
        selectable: true,
        select: function(info) {
            var title = prompt('메모를 입력하세요:');
            if (title) {
                saveMemoToServer(title, info.startStr, info.endStr);  // 메모 저장 후 이벤트 추가
            }
        },
        eventContent: function(arg) {
            let fullTitle = arg.event.title;
            let truncatedTitle = fullTitle.length > 20 ? fullTitle.substring(0, 20) + '...' : fullTitle;

            let eventElement = document.createElement('div');
            eventElement.innerHTML = truncatedTitle;

            // 툴팁 추가
            eventElement.setAttribute('title', fullTitle);
            return { domNodes: [eventElement] };
        },
        eventClick: function(info) {
            // 메모 삭제 확인 창 표시
            if (confirm(`${info.event.extendedProps.manager}  :  ${info.event.title}\n\n(메모를 삭제하시겠습니까?)`)) {
                // FullCalendar에서 메모 제거
                info.event.remove();

                // 서버에 삭제 요청
                deleteMemoFromServer(info.event.id);
            }
        }
    });

    calendar.render();  // 캘린더 렌더링
    loadMemosFromServer();  // 페이지 로드 시 기존 메모 불러오기
});

// 서버에서 메모 불러오기 함수
function loadMemosFromServer() {
    fetch('/api/memos/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(memo => {
                calendar.addEvent({
                    id: memo.id,  // 서버에서 받은 메모의 ID 추가
                    title: memo.title,
                    start: memo.startDate,
                    end: memo.endDate,
                    allDay: true,
                    extendedProps: {  // manager를 extendedProps로 추가
                        manager: memo.manager
                    }
                });
            });
        })
        .catch(error => console.error('Error fetching memos:', error));
}

// 메모 삭제 함수 (서버에 삭제 요청)
function deleteMemoFromServer(eventId) {
    fetch(`/api/memos/delete/${eventId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (response.ok) {
            console.log('메모가 삭제되었습니다.');
        } else {
            console.error('메모 삭제 실패');
        }
    })
    .catch(error => console.error('메모 삭제 중 오류 발생:', error));
}
