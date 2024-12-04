var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(36.763718, 127.2819405),
    level: 4,
};

var map = new kakao.maps.Map(container, options);

map.setZoomable(false);

var marker = new kakao.maps.Marker({
    position: options.center,
    map: map
});

var latitudeInput = document.createElement('input');
latitudeInput.type = 'hidden';
latitudeInput.id = 'latitude';
latitudeInput.name = 'latitude';
latitudeInput.value = options.center.getLat();
document.getElementById('product-register-form').appendChild(latitudeInput);

var longitudeInput = document.createElement('input');
longitudeInput.type = 'hidden';
longitudeInput.id = 'longitude';
longitudeInput.name = 'longitude';
longitudeInput.value = options.center.getLng();
document.getElementById('product-register-form').appendChild(longitudeInput);

kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    var latLng = mouseEvent.latLng;
    marker.setPosition(latLng);
    latitudeInput.value = latLng.getLat();
    longitudeInput.value = latLng.getLng();
});

function previewImage(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    const previewBox = document.getElementById('image-preview');

    reader.onload = function () {
        const img = document.createElement('img');
        img.src = reader.result;
        img.alt = '상품 이미지 미리보기';
        previewBox.innerHTML = '';
        previewBox.appendChild(img);
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

document.getElementById('image-preview').addEventListener('click', function () {
    document.getElementById('image').click();
});

document.addEventListener("DOMContentLoaded", function() {
    fetchCategories();
});

function fetchCategories() {
    fetch('/categories')
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 장애가 발생했습니다.');
            }
            return response.json();
        })
        .then(data => {
            data.sort((a, b) => a.id - b.id);
            populateCategoryDropdown(data);
        })
        .catch(error => {
            console.error('데이터를 불러오는 중 문제가 발생했습니다', error);
        });
}

function populateCategoryDropdown(categories) {
    const categorySelect = document.getElementById("categoryId");

    categorySelect.innerHTML = "";

    const defaultOption = document.createElement("option");
    defaultOption.value = "";
    defaultOption.disabled = true;
    defaultOption.selected = true;
    defaultOption.textContent = "카테고리를 선택하세요.";
    categorySelect.appendChild(defaultOption);

    categories.forEach(category => {
        const option = document.createElement("option");
        option.value = category.id;
        option.textContent = category.name;
        categorySelect.appendChild(option);
    });
}

document.getElementById('submit-btn').addEventListener('click', function (event) {
    event.preventDefault();

    const form = document.getElementById('product-register-form');

    const formData = new FormData();
    formData.append('categoryId', form.categoryId.value);
    formData.append('title', form.title.value);
    formData.append('content', form.content.value);
    formData.append('price', form.price.value);
    formData.append('image', form.image.files[0]);
    formData.append('latitude', document.getElementById('latitude').value);
    formData.append('longitude', document.getElementById('longitude').value);

    fetch('/products', {
        method: 'POST',
        body: formData,
    })
        .then((response) => {
            if (response.ok) {
                alert('상품 등록이 완료되었습니다.');
                window.location.href = "http://localhost:8080/index.html";
            } else {
                return response.json().then((data) => {
                    console.error('에러 응답:', data);
                    alert('상품 등록 중 문제가 발생했습니다.');
                });
            }
        })
        .catch((error) => {
            console.error('서버 요청 중 에러:', error);
            alert('서버와의 통신 중 문제가 발생했습니다.');
        });
});

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
            if (!isLoggedIn) {
                alert("로그인 후 이용할 수 있습니다.");
                window.location.href = '/login.html?redirect=' + encodeURIComponent(window.location.href);
            }
        })
        .catch(error => {
            console.error("로그인 상태 확인 실패:", error);
            window.location.href = '/login.html?redirect=' + encodeURIComponent(window.location.href);
        });
}


