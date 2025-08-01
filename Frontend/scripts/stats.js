
fetch('components/header.html')
    .then(res => res.text())
    .then(data => {
        document.getElementById('header').innerHTML = data;
        const script = document.createElement('script');
        script.src = 'scripts/header.js';
        script.onload = () => {
            if (typeof initHeader === 'function') {
                initHeader();
            }
        };
        document.body.appendChild(script);
    });

function updateDate() {
    const now = new Date();
    const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    };
    const dateString = now.toLocaleDateString('en-GB', options);
    document.getElementById('currentDate').textContent = dateString;
}

updateDate();

window.addEventListener('load', () => {
    const elements = document.querySelectorAll('.user-profile, .stats-panel');
    elements.forEach((el, index) => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';

        setTimeout(() => {
            el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
            el.style.opacity = '1';
            el.style.transform = 'translateY(0)';
        }, index * 200);
    });
});