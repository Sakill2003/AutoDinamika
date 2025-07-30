
const loginToggle = document.getElementById('loginToggle');
const registerToggle = document.getElementById('registerToggle');
const toggleSlider = document.getElementById('toggleSlider');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');

let isLogin = true;


function switchToLogin() {
    if (!isLogin) {
        isLogin = true;
        loginToggle.classList.add('active');
        registerToggle.classList.remove('active');
        toggleSlider.classList.remove('register');
        loginForm.classList.remove('hide');
        registerForm.classList.remove('show');
    }
}

function switchToRegister() {
    if (isLogin) {
        isLogin = false;
        registerToggle.classList.add('active');
        loginToggle.classList.remove('active');
        toggleSlider.classList.add('register');
        loginForm.classList.add('hide');
        registerForm.classList.add('show');
    }
}

loginToggle.addEventListener('click', switchToLogin);
registerToggle.addEventListener('click', switchToRegister);


function showError(errorElementId, message) {
    const errorElement = document.getElementById(errorElementId);
    const inputElement = errorElement.previousElementSibling;

    errorElement.textContent = message;
    errorElement.classList.add('show');
    inputElement.classList.add('error');
}

function clearError(errorElementId) {
    const errorElement = document.getElementById(errorElementId);
    const inputElement = errorElement.previousElementSibling;

    errorElement.textContent = '';
    errorElement.classList.remove('show');
    inputElement.classList.remove('error');
}

function clearAllErrors(form) {
    const errorMessages = form.querySelectorAll('.error-message');
    const inputs = form.querySelectorAll('input');

    errorMessages.forEach(error => {
        error.textContent = '';
        error.classList.remove('show');
    });

    inputs.forEach(input => {
        input.classList.remove('error');
    });
}


loginForm.addEventListener('submit', function (e) {
    e.preventDefault();
    clearAllErrors(this);

    const email = this.querySelector('input[type="email"]').value;
    let hasErrors = false;

    if (!email.includes('@')) {
        showError('loginEmailError', 'Please enter a valid email address with @');
        hasErrors = true;
    }

    if (hasErrors) return;

    const submitBtn = this.querySelector('.submit-btn');
    submitBtn.classList.add('loading');

    setTimeout(() => {
        submitBtn.classList.remove('loading');
        alert('Login successful! (This is a demo)');
    }, 2000);
});

registerForm.addEventListener('submit', function (e) {
    e.preventDefault();
    clearAllErrors(this);

    const email = this.querySelector('input[type="email"]').value;
    const password = this.querySelector('input[placeholder="Password"]').value;
    const confirmPassword = this.querySelector('input[placeholder="Confirm Password"]').value;
    let hasErrors = false;

    if (!email.includes('@')) {
        showError('registerEmailError', 'Please enter a valid email address with @');
        hasErrors = true;
    }

    if (password.length < 8) {
        showError('registerPasswordError', 'Password must be at least 8 characters long');
        hasErrors = true;
    }

    if (password !== confirmPassword) {
        showError('confirmPasswordError', 'Passwords do not match');
        hasErrors = true;
    }

    if (hasErrors) return;

    const submitBtn = this.querySelector('.submit-btn');
    submitBtn.classList.add('loading');

    setTimeout(() => {
        submitBtn.classList.remove('loading');
        alert('Account created successfully! (This is a demo)');
        switchToLogin();
    }, 2000);
});

document.addEventListener('mousemove', function (e) {
    const mouseX = e.clientX / window.innerWidth;
    const mouseY = e.clientY / window.innerHeight;

    const background = document.querySelector('.background');
    background.style.transform = `translate(${mouseX * 20}px, ${mouseY * 20}px) scale(1.1)`;
});