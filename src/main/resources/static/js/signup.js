document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('signup-form');

    form.addEventListener('submit', (event) => {
        event.preventDefault();

        const formData = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            name: document.getElementById('name').value,
            nickname: document.getElementById('nickname').value,
            studentNumber: document.getElementById('studentNumber').value,
            phoneNumber: document.getElementById('phoneNumber').value
        };

        fetch("http://localhost:8080/members", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                alert('회원가입이 완료되었습니다!');
                form.reset();
                window.location.href = "http://localhost:8080/login.html";
            })
            .catch((error) => {
                alert(error.message);
            });
    });
});
