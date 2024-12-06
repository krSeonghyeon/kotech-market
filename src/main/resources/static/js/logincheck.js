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
                showMobileLogout();
            } else {
                showLoginButton();
                showMobileLogin();
            }
        })
        .catch(error => {
            console.error("로그인 상태 확인 실패:", error);
        });
}

function showLoginButton() {
    document.getElementById("loginButton").style.display = 'block';
    document.getElementById("signupButton").style.display = 'block';
    document.getElementById("logoutButton").style.display = 'none';
}

function showLogoutButton() {
    document.getElementById("loginButton").style.display = 'none';
    document.getElementById("signupButton").style.display = 'none';
    document.getElementById("logoutButton").style.display = 'block';
}

function showMobileLogin() {
    document.getElementById("login-link").style.display = 'block';
    document.getElementById("signup-link").style.display = 'block';
    document.getElementById("logout-link").style.display = 'none';
}

function showMobileLogout() {
    document.getElementById("login-link").style.display = 'none';
    document.getElementById("signup-link").style.display = 'none';
    document.getElementById("logout-link").style.display = 'block';
}

function logout() {
    fetch('/members/logout', {
        method: 'POST',
        credentials: 'same-origin',
    })
        .then(response => {
            if (response.ok) {
                showLoginButton();
                showMobileLogin();
            } else {
                console.error("로그아웃 실패");
            }
        })
        .catch(error => {
            console.error("로그아웃 요청 중 오류 발생:", error);
        });
}
