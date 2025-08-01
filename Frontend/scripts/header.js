
const menuButton = document.getElementById('menuButton');
const dropdownMenu = document.getElementById('dropdownMenu');
const menuOverlay = document.getElementById('menuOverlay');
const menuIcon = menuButton.querySelector('i');

/*
This function controls the animation of the dropdown menu, there are two different states of the menu, when it's open or closed the icon changes.
Args:
   click: It detects when user clicks on the icon, changing it.
*/

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

/*
This function controls the animation of the dropdown menu.
This function controls the access to different sections available on the dropdown menu depending on the type of user that logged in, 
it uses the local Storage user saved after logging in. 
(guest only have access to inventory preview, seller has access to self stats and start a sale, admin has access to add and modify cars).
This function changes the login button to log out button after logging in, if the user uses the log out button, it removes the current state.
Args:
   'DOMContentLoaded': It's a Document Object Model, an event that fires when the HTML section (navbar) has been completely loaded.
*/

document.addEventListener('DOMContentLoaded', () => {
    navbar.style.transform = 'translateY(-100%)';
    setTimeout(() => {
        navbar.style.transition = 'transform 0.5s ease';
        navbar.style.transform = 'translateY(0)';
    }, 100);

    //access control depending on the state (default user -> guest)
    const user = JSON.parse(localStorage.getItem('user')) || { role: 'guest' };
    const loginBtn = document.querySelector('.login-button');
    const menuItems = document.querySelectorAll('.dropdown-menu .nav-link');

    const accessControl = {
        guest: {
            allowed: ['inventory.html'],
            redirect: 'login.html'
        },
        seller: {
            allowed: ['inventory.html', 'stats.html', 'sell.html'],
            alertOn: ['addcar.html', 'manager.html']
        },
        admin: {
            allowed: ['inventory.html', 'stats.html', 'sell.html', 'addcar.html', 'manager.html']
        }
    };

    // Change Login in button to Log Out button after loggin in. (If the user press log out button, it removes current state)
    if (user.role !== 'guest') {
        loginBtn.innerHTML = `<i class="fas fa-sign-out-alt"></i><span>Logout</span>`;
        loginBtn.href = "#";
        loginBtn.addEventListener('click', () => {
            localStorage.removeItem('user');
            window.location.href = 'index.html';
        });
    }

    // Control the buttons, alerts in case the current state is not allowed to enter a section.
    menuItems.forEach(link => {
        const href = link.getAttribute('href');
        const fileName = href.split('/').pop();

        if (!accessControl[user.role].allowed.includes(fileName)) {
            if (user.role === 'guest') {
                link.setAttribute('href', accessControl.guest.redirect);
            } else if (accessControl[user.role].alertOn?.includes(fileName)) {
                link.addEventListener('click', e => {
                    e.preventDefault();
                    alert('Only Administrative users can access to this service');
                });
            }
        }
    });
});