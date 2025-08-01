

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
window.addEventListener('load', () => {
    const cards = document.querySelectorAll('.stat-card, .performance-card');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(30px)';

        setTimeout(() => {
            card.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 100);
    });
});

document.querySelectorAll('.view-more-link').forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        console.log('Navigate to detailed view for:', e.target.closest('.performance-card').querySelector('.card-title').textContent);
    });
});

document.querySelectorAll('.list-item').forEach(item => {
    item.addEventListener('mouseenter', () => {
        item.style.transform = 'translateX(8px) scale(1.02)';
    });

    item.addEventListener('mouseleave', () => {
        item.style.transform = 'translateX(0) scale(1)';
    });
});