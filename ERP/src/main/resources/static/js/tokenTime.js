async function extendSession() {
    try {
        const response = await fetch('/api/extend-session', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include' // 쿠키를 포함하여 요청
        });

        if (response.ok) {
            console.log('Session extended successfully');
            cancelled = false; // 세션이 연장되었으니 취소 상태를 리셋
            await checkSessionStatus();
        } else {
            console.error('Failed to extend session');
        }
    } catch (error) {
        console.error('Error occurred during session extension', error);
    }
}

// extend 함수를 정의합니다
function extend() {
    console.log("Extend button clicked");
    extendSession();
}

// 페이지가 로드된 후 이벤트를 연결합니다.
document.addEventListener('DOMContentLoaded', function() {
    const extendButton = document.getElementById('extend-button');
    
    if (extendButton) {
        extendButton.addEventListener('click', extend);
    } else {
        console.error('extend-button element not found');
    }
});

let cancelled = false; // 취소 여부 플래그
let remainingTime = 0; // 서버로부터 남은 시간(ms)을 받을 변수
let intervalId;

async function checkSessionStatus() {
    try {
        const response = await fetch('/api/token-remaining-time', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include' // 쿠키를 포함하여 요청
        });

        if (response.ok) {
            const data = await response.json();
            remainingTime = data; // 남은 시간이 ms로 반환됩니다.
            console.log(remainingTime);
            
            // 남은 시간이 0 이하일 때 자동 로그아웃
            if (remainingTime <= 0) {
                alert("로그인 인증 시간이 만료되어 자동으로 로그아웃 되었습니다.");
                location.href = "/api/logout";
            }

            // 남은 시간이 5분 이하일 때 알림을 띄움
            if (remainingTime <= 300000 && !cancelled) { 
                if (confirm('5분 후 자동 로그아웃됩니다. 로그인 시간을 연장하시겠습니까?')) {
                    await extendSession();
                } else {
                    cancelled = true; // 취소 버튼을 눌렀을 때 다시 물어보지 않도록 설정
                }
            }
        } else if (response.status === 401) {
            // 토큰이 만료되어 401 상태 코드를 받았을 때
            alert("로그인 인증 시간이 만료되어 자동으로 로그아웃 되었습니다.");
            location.href = "/api/logout";
        } else {
            console.error('Error in response status:', response.status); // 상태 코드 출력
        }
    } catch (error) {
        console.error('Error fetching token remaining time:', error); // 에러 메시지 출력
    }
}

// 남은 시간을 분:초 형식으로 표시하는 함수
function displayTime() {
    if (remainingTime > 0) {
        const minutes = Math.floor(remainingTime / 60000); // 분
        const seconds = Math.floor((remainingTime % 60000) / 1000); // 초
        document.getElementById('timer').textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
        
        remainingTime -= 1000; // 1초씩 줄어듦
    } else {
        clearInterval(intervalId); // 타이머 중지
        document.getElementById('timer').textContent = "00:00";
    }
}
//타이머를 설정하여 주기적으로 세션 상태를 확인합니다.
async function initializeTimer() {
    await checkSessionStatus(); // 남은 시간을 먼저 받아옴
    intervalId = setInterval(displayTime, 1000); // 1초마다 남은 시간 표시
    setInterval(checkSessionStatus, 60000); // 60초마다 세션 상태 확인
}

// 페이지 로드 시 타이머 초기화
initializeTimer();