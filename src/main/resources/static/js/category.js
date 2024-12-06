function goToPage(button) {
    const categoryId = button.getAttribute("data-category-id");
    const page = button.getAttribute("data-page");

    const minPrice = document.querySelector('input[name="minPrice"]').value;
    const maxPrice = document.querySelector('input[name="maxPrice"]').value;

    let url = `/products/categories/${categoryId}?page=${page}`;

    if (minPrice) url += `&minPrice=${minPrice}`;
    if (maxPrice) url += `&maxPrice=${maxPrice}`;

    window.location.href = url;
}
document.addEventListener("DOMContentLoaded", () => {
    const productCards = document.querySelectorAll(".product-card");

    productCards.forEach(card => {
        const status = card.getAttribute("data-status");
        if (status === "판매완료") {
            card.classList.add("sold-out");
        }
    });
});