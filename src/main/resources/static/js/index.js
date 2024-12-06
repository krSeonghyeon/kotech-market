const slider = document.querySelector('.slider');
const slides = document.querySelectorAll('.slide');
const dotsContainer = document.querySelector('.dots-container');
let currentIndex = 0;
let slideInterval;

dotsContainer.innerHTML = '';
slides.forEach((_, index) => {
    const dot = document.createElement('span');
    dot.classList.add('dot');
    if (index === 0) dot.classList.add('active');
    dot.addEventListener('click', () => {
        stopAutoSlide();
        currentIndex = index;
        showSlide(index);
        startAutoSlide();
    });
    dotsContainer.appendChild(dot);
});

const dots = document.querySelectorAll('.dot');

function showSlide(index) {
    slider.style.transform = `translateX(-${index * 100}%)`;
    dots.forEach(dot => dot.classList.remove('active'));
    dots[index].classList.add('active');
}

function nextSlide() {
    currentIndex = (currentIndex + 1) % slides.length;
    showSlide(currentIndex);
}

function prevSlide() {
    currentIndex = (currentIndex - 1 + slides.length) % slides.length;
    showSlide(currentIndex);
}

function startAutoSlide() {
    slideInterval = setInterval(nextSlide, 5000);
}

function stopAutoSlide() {
    clearInterval(slideInterval);
}

document.querySelector('.next-btn').addEventListener('click', () => {
    stopAutoSlide();
    nextSlide();
    startAutoSlide();
});

document.querySelector('.prev-btn').addEventListener('click', () => {
    stopAutoSlide();
    prevSlide();
    startAutoSlide();
});

startAutoSlide();

const productList = document.getElementById("product-list");
const loading = document.getElementById("loading");

let currentPage = 0;
const pageSize = 4;
let isLoading = false;
let hasMoreProducts = true;

function getRelativeTime(dateTime) {
    const now = new Date();
    const then = new Date(dateTime);
    const diff = (now - then) / 1000;

    if (diff < 60) return `${Math.floor(diff)}초 전`;
    if (diff < 3600) return `${Math.floor(diff / 60)}분 전`;
    if (diff < 86400) return `${Math.floor(diff / 3600)}시간 전`;
    if (diff < 2592000) return `${Math.floor(diff / 86400)}일 전`;
    return `${Math.floor(diff / 2592000)}달 전`;
}

async function fetchProducts(page, size) {
    try {
        const response = await fetch(`/products/simple-info?page=${page}&size=${size}`);
        if (!response.ok) throw new Error("상품 데이터를 가져오는데 실패했습니다.");
        const products = await response.json();

        if (products.length < size) {
            hasMoreProducts = false;
        }
        return products;
    } catch (error) {
        console.error("상품 데이터를 가져오는 중 오류:", error);
        return [];
    }
}

function renderProducts(products) {
    products.forEach(product => {
        const productCard = document.createElement("div");
        productCard.className = "product-card";
        productCard.onclick = () => {
            window.location.href = `/product-detail/${product.productId}`;
        };

        const productImage = product.image && product.image.trim() ? product.image : 'defaultImage.png';
        const relativeTime = getRelativeTime(product.createdAt);

        let statusClass = "";
        let statusOverlay = "";
        if (product.status === "판매완료") {
            statusClass = "sold-out";
            statusOverlay = `
                <div class="overlay">
                    <span>판매완료된 상품</span>
                </div>
            `;
        }

        productCard.innerHTML = `
            <div class="image-container ${statusClass}">
                <img src="${productImage}" alt="${product.title}">
                ${statusOverlay}
            </div>
            <div class="title">${product.title}</div>
            <div class="price">${product.price.toLocaleString()}원</div>
            <div class="relative-time">${relativeTime}</div>
        `;

        productList.appendChild(productCard);
    });
}

async function loadInitialProducts() {
    const initialProducts = await fetchProducts(0, 20);
    if (initialProducts.length > 0) {
        renderProducts(initialProducts);
        currentPage = Math.ceil(20 / pageSize);
    }
}

async function loadMoreProducts() {
    if (isLoading || !hasMoreProducts) return;

    isLoading = true;
    loading.style.display = "block";

    const products = await fetchProducts(currentPage, pageSize);
    if (products.length > 0) {
        renderProducts(products);
        currentPage++;
    }

    loading.style.display = "none";
    isLoading = false;
}

window.addEventListener("scroll", () => {
    const { scrollTop, scrollHeight, clientHeight } = document.documentElement;

    if (scrollTop + clientHeight >= scrollHeight - 5) {
        loadMoreProducts();
    }
});

loadInitialProducts();

const topButton = document.getElementById("top-button");

window.addEventListener("scroll", () => {
    if (window.scrollY > 300) {
        topButton.style.display = "block";
    } else {
        topButton.style.display = "none";
    }
});

topButton.addEventListener("click", () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
});

