CKEDITOR.replace('editor', {
    removePlugins: 'uploadimage,uploadfile,filebrowser'
});

// 폼 제출 시 HTML 태그 제거
document.querySelector('form').addEventListener('submit', function(event) {
    var editorData = CKEDITOR.instances.editor.getData();
    var strippedData = editorData.replace(/<\/?[^>]+(>|$)/g, "");  // HTML 태그 제거
    CKEDITOR.instances.editor.setData(strippedData);
});

document.addEventListener('DOMContentLoaded', function() {
    fetchUserName();
	fetchCompanyCode();
});

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

// 서버에서 사용자 이름을 가져와 페이지에 표시하는 함수
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

