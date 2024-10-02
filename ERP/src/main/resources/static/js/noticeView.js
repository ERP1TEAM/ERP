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
            document.getElementById('currentManager').value = data.name; // 사용자 이름 설정
            return data.name; // 사용자 이름 반환
        } else {
            console.error('Failed to fetch user info');
        }
    } catch (error) {
        console.error('Error fetching user info:', error);
    }
    return null;
}

document.addEventListener('DOMContentLoaded', async function() {
    // 사용자 이름이 로드된 후에 비교 로직 실행
    const userName = await fetchUserName(); 
    if (userName) {
        const noticeManager = document.getElementById('noticeManager').value;
        const currentManager = document.getElementById('currentManager').value;

        console.log('공지사항 작성자:', noticeManager);
        console.log('현재 사용자:', currentManager);

        if (noticeManager === currentManager) {
            document.getElementById('deleteButton').disabled = false;
        }
    }
});

// URL에서 공지사항 ID 추출하는 함수
function getNoticeIdFromUrl() {
    const pathParts = window.location.pathname.split('/'); // URL 경로를 '/'로 나누어 배열로 만듦
    return pathParts[pathParts.length - 1]; // 배열의 마지막 부분이 공지사항 ID일 경우
}

document.getElementById('deleteButton').addEventListener('click', function() {
    const noticeId = getNoticeIdFromUrl(); // URL에서 공지사항 ID 추출

    if (confirm("정말로 이 공지사항을 삭제하시겠습니까?")) {
        fetch(`./delete/${noticeId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('공지사항이 성공적으로 삭제되었습니다.');
                window.location.href = '../home'; // 목록 페이지로 이동
            } else {
                alert('공지사항 삭제에 실패하였습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('공지사항 삭제 중 오류가 발생했습니다.');
        });
    }
});
