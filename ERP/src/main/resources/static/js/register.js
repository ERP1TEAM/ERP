// 페이지가 로드될 때 fade-in 효과 적용
window.onload = () => {
    const divContainer = document.getElementById('div-container');
    divContainer.classList.add('fade-in');
};

// 뒤로가기 버튼 클릭 시 로그인 페이지로 이동
function goBack() {
    window.location.href = '/login';
}

// 권한 코드 입력 시 상태 확인
document.getElementById('code').addEventListener('input', function() {
    const codeInput = this.value;
    const companyInfo = document.getElementById('company-info');
    const errorMessage = document.getElementById('error-message');
    const company = document.getElementById('company');

    if (codeInput === 'QKOALA') {
        companyInfo.innerHTML = 'Main: 퀵코알라';
        company.value = '퀵코알라';
        errorMessage.style.display = 'none';
        companyInfo.style.display = 'block';
        
    } else if (codeInput.startsWith('SP')) {
        companyInfo.style.display = 'block';
        errorMessage.style.display = 'none';

        // 서버에 요청하여 소속사 확인
        fetch(`/api/checkCompany?code=${codeInput}`)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    companyInfo.innerHTML = `Supplier: <span style="color: blue;">${data.name}</span>`;
                    company.value = `${data.name}`;
                } else {
                    companyInfo.innerHTML = 'Supplier: <span style="color: red;">[소속사 미확인]</span>';
                    company.value = '';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                companyInfo.innerHTML = 'Supplier: <span style="color: red;">[소속사 미확인]</span>';
            });
        
    } else if (codeInput.startsWith('SL')) {
        companyInfo.style.display = 'block';
        errorMessage.style.display = 'none';

        // 서버에 요청하여 소속사 확인 (Sales)
        fetch(`/api/checkCompany?code=${codeInput}`)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    companyInfo.innerHTML = `Sales: <span style="color: blue;">${data.name}</span>`;
                    company.value = `${data.name}`;
                } else {
                    companyInfo.innerHTML = 'Sales: <span style="color: red;">[소속사 미확인]</span>';
                    company.value = '';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                companyInfo.innerHTML = 'Sales: <span style="color: red;">[소속사 미확인]</span>';
            });
        
    } else if (codeInput === '') {
        companyInfo.style.display = 'none';
        errorMessage.style.display = 'block';
        errorMessage.innerHTML = '권한 코드를 입력하세요.';
        company.value = '';
    } else {
        companyInfo.style.display = 'none';
        errorMessage.style.display = 'block';
        errorMessage.innerHTML = '소속 코드를 확인할 수 없습니다.';
        company.value = '';
    }
});


// 아이디 중복 확인
document.getElementById('userId').addEventListener('input', function() {
    const idInput = this.value;
    const idMessage = document.getElementById('id-message');
    if (idInput.length > 0) {
        // 서버에 요청하여 아이디 중복 확인
        fetch(`/api/checkId?userId=${idInput}`)
            .then(response => response.json())
            .then(exists => {  // 응답을 boolean으로 처리
                if (exists) {
                    idMessage.innerHTML = '아이디 중복';
                    idMessage.style.color = 'red';
                } else {
                    idMessage.innerHTML = '사용 가능';
                    idMessage.style.color = 'blue';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                idMessage.innerHTML = '특수기호 사용불가';
                idMessage.style.color = 'red';
            });
    } else {
        idMessage.innerHTML = '';
    }
});

// 회원가입 처리
function register() {
    const form = document.getElementById('form');
    const formData = new FormData(form);

    // 유효성 검사
    if (!validateForm()) {
        return; // 유효성 검사가 실패하면 종료
    }

    const memberData = {
        code: formData.get('code'),
        role: document.getElementById('company-info').textContent.split(':')[0].trim(),
        company: formData.get('company'),
        department: formData.get('department'),
        name: formData.get('name'),
        userId: formData.get('userId'),
        password: formData.get('mpass'),
        tel: formData.get('mtel'),
        email: formData.get('email')
    };

    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(memberData)
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        location.href = "/login"; // 성공 후 리다이렉트
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// 폼 유효성 검사
function validateForm() {
    const code = document.getElementById('code').value;
    const company = document.getElementById('company').value;
    const department = document.getElementById('department').value;
    const name = document.getElementById('name').value;
    const userId = document.getElementById('userId').value;
    const password = document.getElementById('mpass').value;
    const passwordConfirm = document.getElementById('mpass2').value;
    const tel = document.getElementById('mtel').value;
    const telPattern = /^\d+$/;
    const email = document.getElementById('email').value;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    let valid = true;

    if (company === '') {
        alert('올바른 권한코드를 입력해 주세요');
        document.getElementById('code').focus();
        valid = false;
    } else if (department === '') {
        alert('부서명을 입력해 주세요.');
        document.getElementById('department').focus();
        valid = false;
    } else if (name === '') {
        alert('이름을 입력해 주세요.');
        document.getElementById('name').focus();
        valid = false;
    } else if (userId === '') {
        alert('아이디를 입력해 주세요.');
        document.getElementById('userId').focus();
        valid = false;
    } else if (password.length < 8) {
        alert('패스워드는 8자 이상이어야 합니다.');
        document.getElementById('mpass').focus();
        valid = false;
    } else if (password !== passwordConfirm) {
        alert('패스워드가 일치하지 않습니다.');
        document.getElementById('mpass2').focus();
        valid = false;
    } else if (tel === '') {
        alert('연락처를 입력해 주세요.');
        document.getElementById('mtel').focus();
        valid = false;
    } else if (!telPattern.test(tel)) {
        alert('연락처는 숫자만 포함해야 합니다.');
        document.getElementById('mtel').focus();
        valid = false;
    } else if (email === '') {
        alert('이메일을 입력해 주세요.');
        document.getElementById('email').focus();
        valid = false;
    } else if (!emailPattern.test(email)) {
        alert('유효한 이메일 주소를 입력해 주세요.');
        document.getElementById('email').focus();
        valid = false;
    }

    return valid;
}

//패스워드 일치 확인
function checkPasswordMatch() {
    const passwordInput = document.getElementById('mpass').value;
    const passwordConfirmInput = document.getElementById('mpass2').value;
    const passwordCheckMessage = document.getElementById('password-check-message');
    
    if (passwordConfirmInput !== passwordInput) {
        passwordCheckMessage.innerHTML = '패스워드 불일치';
        passwordCheckMessage.style.color = 'red';
    } else {
        passwordCheckMessage.innerHTML = '✔';
        passwordCheckMessage.style.color = 'blue';
    }
}

// 패스워드 입력 시 최소 길이 체크 및 일치 여부 확인
document.getElementById('mpass').addEventListener('input', function() {
    const passwordInput = this.value;
    const passwordMessage = document.getElementById('password-message');
    
    if (passwordInput.length < 8 && passwordInput !== '') {
        passwordMessage.innerHTML = '패스워드는 8자 이상입니다';
        passwordMessage.style.color = 'red';
    } else {
        passwordMessage.innerHTML = '';
    }

    checkPasswordMatch(); // 패스워드 입력 시 일치 여부도 체크
});

// 패스워드 확인 입력 시 일치 여부 체크
document.getElementById('mpass2').addEventListener('input', checkPasswordMatch);