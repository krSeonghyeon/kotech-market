document.addEventListener("DOMContentLoaded", () => {
    const myInfoSection = document.getElementById("myinfo");
    const editInfoSection = document.getElementById("editinfo");
    const deleteAccountSection = document.getElementById("deleteaccount");

    document.querySelectorAll(".sidebar li").forEach((menuItem) => {
        menuItem.addEventListener("click", (e) => {
            document.querySelectorAll(".section").forEach((section) => section.classList.add("hidden"));
            document.querySelectorAll(".sidebar li").forEach((item) => item.classList.remove("active"));
            e.target.classList.add("active");

            if (menuItem.id === "menu-myinfo") {
                myInfoSection.classList.remove("hidden");
                loadMemberInfo();
            } else if (menuItem.id === "menu-editinfo") {
                editInfoSection.classList.remove("hidden");
                fillEditForm();
            } else if (menuItem.id === "menu-deleteaccount") {
                deleteAccountSection.classList.remove("hidden");
            }
        });
    });

    let memberData = {};

    async function loadMemberInfo() {
        try {
            const response = await fetch("/members");
            if (!response.ok) throw new Error("Failed to fetch member info.");
            memberData = await response.json();

            document.getElementById("email").textContent = memberData.email;
            document.getElementById("name").textContent = memberData.name;
            document.getElementById("nickname").textContent = memberData.nickname;
            document.getElementById("studentNumber").textContent = memberData.studentNumber;
            document.getElementById("phone").textContent = memberData.phoneNumber;
        } catch (error) {
            alert("회원 정보를 불러오는 데 실패했습니다.");
            const redirectUrl = encodeURIComponent(window.location.href);
            window.location.href = `http://localhost:8080/login.html?redirect=${redirectUrl}`;
        }
    }

    function fillEditForm() {
        if (memberData.nickname) {
            document.getElementById("nicknameInput").value = memberData.nickname;
        }
        if (memberData.phoneNumber) {
            document.getElementById("phoneInput").value = memberData.phoneNumber;
        }
    }

    // Update member info
    document.getElementById("editForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const nickname = document.getElementById("nicknameInput").value;
        const phoneNumber = document.getElementById("phoneInput").value;

        try {
            const response = await fetch("/members", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nickname, phoneNumber }),
            });
            if (!response.ok) throw new Error("Failed to update member info.");
            alert("회원 정보가 성공적으로 수정되었습니다.");
            loadMemberInfo();
            document.getElementById("menu-myinfo").click();
        } catch (error) {
            alert("회원 정보를 수정하는 데 실패했습니다.");
        }
    });

    // Delete member account
    document.getElementById("confirm-delete").addEventListener("click", async () => {
        if (!confirm("정말로 탈퇴하시겠습니까?")) return;

        try {
            const response = await fetch("/members", { method: "DELETE" });
            if (!response.ok) throw new Error("Failed to delete account.");
            alert("회원 탈퇴가 완료되었습니다.");
            window.location.href = "http://localhost:8080/login.html";
        } catch (error) {
            alert("회원 탈퇴 중 문제가 발생했습니다.");
        }
    });

    loadMemberInfo();
});
