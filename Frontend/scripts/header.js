
const menuButton = document.getElementById('menuButton');
const dropdownMenu = document.getElementById('dropdownMenu');
const menuOverlay = document.getElementById('menuOverlay');
const menuIcon = menuButton.querySelector('i');

menuButton.addEventListener('click', () => {
    dropdownMenu.classList.toggle('active');
    menuOverlay.classList.toggle('active');

    if (dropdownMenu.classList.contains('active')) {
        menuIcon.classList.remove('fa-bars');
        menuIcon.classList.add('fa-times');
    } else {
        menuIcon.classList.remove('fa-times');
        menuIcon.classList.add('fa-bars');
    }
});

menuOverlay.addEventListener('click', () => {
    dropdownMenu.classList.remove('active');
    menuOverlay.classList.remove('active');
    menuIcon.classList.remove('fa-times');
    menuIcon.classList.add('fa-bars');
});

document.querySelectorAll('.dropdown-menu .nav-link').forEach(link => {
    link.addEventListener('click', () => {
        dropdownMenu.classList.remove('active');
        menuOverlay.classList.remove('active');
        menuIcon.classList.remove('fa-times');
        menuIcon.classList.add('fa-bars');
    });
});

let lastScrollTop = 0;
const navbar = document.querySelector('.navbar-header');

window.addEventListener('scroll', () => {
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    if (scrollTop > lastScrollTop && scrollTop > 100) {
        navbar.style.transform = 'translateY(-100%)';
    } else {
        navbar.style.transform = 'translateY(0)';
    }
    lastScrollTop = scrollTop;
});

document.addEventListener('DOMContentLoaded', () => {
    navbar.style.transform = 'translateY(-100%)';
    setTimeout(() => {
        navbar.style.transition = 'transform 0.5s ease';
        navbar.style.transform = 'translateY(0)';
    }, 100);
});
