function initHeader() {
    const menuButton = document.getElementById('menuButton');
    const dropdownMenu = document.getElementById('dropdownMenu');
    const menuOverlay = document.getElementById('menuOverlay');
    const menuIcon = menuButton.querySelector('i');
    const navbar = document.querySelector('.navbar-header');

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
    window.addEventListener('scroll', () => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        if (scrollTop > lastScrollTop && scrollTop > 100) {
            navbar.style.transform = 'translateY(-100%)';
        } else {
            navbar.style.transform = 'translateY(0)';
        }
        lastScrollTop = scrollTop;
    });

    navbar.style.transform = 'translateY(-100%)';
    setTimeout(() => {
        navbar.style.transition = 'transform 0.5s ease';
        navbar.style.transform = 'translateY(0)';
    }, 100);

    // Access control
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

    if (user.role !== 'guest') {
        loginBtn.innerHTML = `<i class="fas fa-sign-out-alt"></i><span>Logout</span>`;
        loginBtn.href = "#";
        loginBtn.addEventListener('click', () => {
            localStorage.removeItem('user');
            window.location.href = 'index.html';
        });
    }

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
}
