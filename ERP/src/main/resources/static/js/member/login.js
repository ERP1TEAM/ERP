// 페이지 로드 시 fade-in 효과
window.onload = () => {
    const loginContainer = document.getElementById('login-container');
    loginContainer.classList.add('fade-in');
};

// 캐싱된 페이지에서 뒤로가기 했을 때 새로고침
window.onpageshow = function(event) {
    if (event.persisted) {
        window.location.reload();
    }
};

function redirectToRegister() {
    const loginContainer = document.getElementById('login-container');
    loginContainer.classList.add('fade-out');
    setTimeout(() => {
        window.location.href = '/register';
    }, 500);
}

// 로그인 폼 제출 이벤트 처리
document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음
    
    // 폼 데이터를 가져옴
    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;

    if (!userId || !password) {
        alert('아이디와 비밀번호를 입력해주세요.');
        return;
    }

    try {
        // 로그인 요청을 서버로 전송
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId, password }),
            credentials: 'include' // 쿠키를 포함하는 옵션
        });

        if (response.ok) {
            // 서버에서 리디렉션 URL을 반환하면 해당 URL로 이동
            const redirectUrl = await response.text();
            window.location.href = redirectUrl;
        } else {
            const errorText = await response.text();
            alert('로그인 실패: ' + errorText);
        }
    } catch (error) {
        console.error('Error during login:', error);
        alert('로그인 중 에러가 발생했습니다.');
    }
});