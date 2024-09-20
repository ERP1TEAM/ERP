

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
            document.getElementById('user-name').textContent = data.name + " 관리자"; // 사용자 이름 설정
        } else {
            console.error('Failed to fetch user info');
        }
    } catch (error) {
        console.error('Error fetching user info:', error);
    }
}

// 페이지 로드 시 사용자 이름을 가져옴
document.addEventListener('DOMContentLoaded', fetchUserName);

async function extendSession() {
    if (cancelled) return; // 로그아웃 중이거나 완료되면 extendSession 중지

    try {
        const response = await fetch('/api/extend-session', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include' // 쿠키를 포함하여 요청
        });

        if (response.ok) {
            cancelled = false; // 세션이 연장되었으니 취소 상태를 리셋
            await checkSessionStatus();
        } else {
            console.error('Failed to extend session');
        }
    } catch (error) {
        console.error('Error occurred during session extension', error);
    }
}


function extend() {
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

    // 클릭 이벤트 발생 시 토큰 연장
    document.addEventListener('click', () => {
        if (!cancelled) {
            extendSession();
        }
    });

    // 페이지 이동 또는 새로고침 시 토큰 연장
    window.addEventListener('beforeunload', function() {
        if (!cancelled) {
            extendSession();
        }
    });
});


let cancelled = false; // 취소 여부 플래그
let remainingTime = 0; // 서버로부터 남은 시간(ms)을 받을 변수
let intervalId;

// 로그아웃 함수 추가
function logout() {
    cancelled = true; // 세션 상태 체크를 중지하기 위해 cancelled를 true로 설정
    // 페이지 이동 전 extendSession 실행을 막기 위해 event listener 제거
    window.removeEventListener('beforeunload', extendSession);

    // 실제 로그아웃 요청을 서버로 전송
    location.href = '/api/logout'; // 로그아웃 경로로 이동
}

async function checkSessionStatus() {
	// 로그아웃되면 더 이상 실행하지 않도록 설정
	
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
            remainingTime = data;
            
            if (remainingTime <= 0) {
                alert("로그인 인증이 만료되어 자동으로 로그아웃 되었습니다.");
                location.href = "/api/logout";
            }

            if (remainingTime <= 300000 && !cancelled) { 
                if (confirm('5분 후 자동 로그아웃됩니다. 로그인 시간을 연장하시겠습니까?')) {
                    await extendSession();
                } else {
                    cancelled = true;
                }
            }
        } else if (response.status === 401) {
            alert("로그인 인증 시간이 만료되어 자동으로 로그아웃 되었습니다.");
            location.href = "/api/logout";
        } else {
            console.error('Error in response status:', response.status);
        }
    } catch (error) {
        console.error('Error fetching token remaining time:', error);
    }
}

// 남은 시간을 분:초 형식으로 표시하는 함수
function displayTime() {
    if (remainingTime > 0) {
        const minutes = Math.floor(remainingTime / 60000);
        const seconds = Math.floor((remainingTime % 60000) / 1000);
        document.getElementById('timer').textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
        remainingTime -= 1000;
    } else {
        clearInterval(intervalId);
        document.getElementById('timer').textContent = "00:00";
    }
}

async function initializeTimer() {
    await checkSessionStatus();
    intervalId = setInterval(displayTime, 1000);
    setInterval(checkSessionStatus, 60000);
}

// 페이지 로드 시 타이머 초기화
initializeTimer();
