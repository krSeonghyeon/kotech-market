document.addEventListener("DOMContentLoaded", function () {
    checkLoginStatus();
});

function checkLoginStatus() {
    fetch('/members/login-status', {
        method: 'GET',
        credentials: 'same-origin',
    })
        .then(response => response.json())
        .then(isLoggedIn => {
            if (isLoggedIn) {
                showLogoutButton();
            } else {
                showLoginButton();
            }
        })
        .catch(error => {
            console.error("로그인 상태 확인 실패:", error);
        });
}

// 로그인 버튼 보이기
function showLoginButton() {
    document.getElementById("loginButton").style.display = 'block';
    document.getElementById("signupButton").style.display = 'block';
    document.getElementById("logoutButton").style.display = 'none';
}

// 로그아웃 버튼 보이기
function showLogoutButton() {
    document.getElementById("loginButton").style.display = 'none';
    document.getElementById("signupButton").style.display = 'none';
    document.getElementById("logoutButton").style.display = 'block';
}

// 로그아웃 처리
function logout() {
    fetch('/members/logout', {
        method: 'POST',
        credentials: 'same-origin',
    })
        .then(response => {
            if (response.ok) {
                showLoginButton();
            } else {
                console.error("로그아웃 실패");
            }
        })
        .catch(error => {
            console.error("로그아웃 요청 중 오류 발생:", error);
        });
}
