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
