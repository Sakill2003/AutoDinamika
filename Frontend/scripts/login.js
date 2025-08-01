
const loginToggle = document.getElementById('loginToggle');
const registerToggle = document.getElementById('registerToggle');
const toggleSlider = document.getElementById('toggleSlider');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');

let isLogin = true;

/*
This function is called when the user clicks on Login button. It hides register section.
*/

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

/*
This function is called when the user clicks on Register button. It hides login section.
*/

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

/*
This function is a visual parallax that detects mouse movements to move the background image.
Args:
    mousemove: The movement detected of the mouse on X and Y axis.
*/

document.addEventListener('mousemove', function (e) {
    const mouseX = e.clientX / window.innerWidth;
    const mouseY = e.clientY / window.innerHeight;

    const background = document.querySelector('.background');
    background.style.transform = `translate(${mouseX * 20}px, ${mouseY * 20}px) scale(1.1)`;
});

/*
This function controlls the login form, saves the input values, detect syntaxis errors and comunicates with database to compare users with the inputs.
If login is succesful, it saves the current state depending on the type of user (seller or admin).
Args:
   submit: This function has the values of email and password.
*/

loginForm.addEventListener('submit', function (e) {
    e.preventDefault();
    clearAllErrors(this);

    const email = this.querySelector('input[type="email"]').value;
    const password = this.querySelector('input[type="password"]').value;
    let hasErrors = false;

    if (!email.includes('@')) {
        showError('loginEmailError', 'Please enter a valid email address with @');
        hasErrors = true;
    }

    if (!password) {
        showError('loginPasswordError', 'Please enter your password');
        hasErrors = true;
    }

    if (hasErrors) return;

    const submitBtn = this.querySelector('.submit-btn');
    submitBtn.classList.add('loading');

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    })
    .then(res => res.json())
    .then(data => {
        submitBtn.classList.remove('loading');

        if (data.success) {
            localStorage.setItem('user', JSON.stringify({
                id: data.seller_ID,
                name: data.name,
                role: data.role
            }));

            alert('Login Successful');
            location.href ="index.html";
        } else {
            showError('loginPasswordError', data.message || 'Login failed');
        }
    })
    .catch(err => {
        submitBtn.classList.remove('loading');
        alert('Connection error with the server');
    });
});

/*
This function controlls the register form, saves the input values, detect syntaxis errors and comunicates with database to register the user.
The function detects errors on the form and alerts the user if there is an error.
Args:
   submit: This function has the values of the user (name, birthday date, email, phone, address, password).
*/

registerForm.addEventListener('submit', function (e) {
    e.preventDefault();
    clearAllErrors(this);

    const inputs = registerForm.querySelectorAll('input');
    const data = {
        first_Name: inputs[0].value,
        last_Name: inputs[1].value,
        birthday_Date: inputs[2].value,
        email: inputs[3].value,
        phone: inputs[4].value,
        address: inputs[5].value,
        password: inputs[6].value
    };

    let hasErrors = false;

    if (!data.email.includes('@')) {
        showError('registerEmailError', 'Please enter a valid email address with @');
        hasErrors = true;
    }

    if (data.password.length < 8) {
        showError('registerPasswordError', 'Password must be at least 8 characters long');
        hasErrors = true;
    }

    if (hasErrors) return;

    const submitBtn = this.querySelector('.submit-btn');
    submitBtn.classList.add('loading');

    fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(response => {
        submitBtn.classList.remove('loading');

        if (response.success) {
            alert('Successful register, now you can log in');
            switchToLogin();
        } else {
            alert(response.message || 'Register Error');
        }
    })
    .catch(() => {
        submitBtn.classList.remove('loading');
        alert('Connection error with the server');
    });
});
