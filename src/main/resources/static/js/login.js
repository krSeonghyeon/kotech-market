document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById('login-form');

    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const formData = {
            email: document.getElementById('email').value.trim(),
            password: document.getElementById('password').value.trim(),
        };

        fetch("http://localhost:8080/members/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                alert("로그인이 완료되었습니다!");
                window.location.href = "http://localhost:8080/main.html";
            })
            .catch(error => {
                alert(`${error.message}`);
            });
    });
});
