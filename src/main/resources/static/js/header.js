function toggleMenu() {
    const menu = document.getElementById('mobile-menu');
    menu.classList.toggle('open');
}

function toggleCategory() {
    const categoryList = document.getElementById('category-list');
    const isHidden = categoryList.style.display === 'none' || categoryList.style.display === '';
    categoryList.style.display = isHidden ? 'block' : 'none';
}
