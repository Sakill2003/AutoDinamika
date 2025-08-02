

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

document.addEventListener('DOMContentLoaded', () => {
    fetch("http://localhost:8080/api/manager/stats")
        .then(res => res.json())
        .then(data => {
            console.log("DATA:", data);
            document.querySelectorAll('.stat-value')[0].textContent = data.totalSales;
            document.querySelectorAll('.stat-value')[1].textContent = `$${data.totalCollected.toLocaleString()}`;
            document.querySelectorAll('.stat-value')[2].textContent = `$${data.earnings.toLocaleString()}`;

            function fillList(containerSelector, items) {
                const container = document.querySelector(containerSelector);
                container.innerHTML = '';
                items.forEach((item, index) => {
                    container.innerHTML += `
                        <div class="list-item">
                            <div class="item-rank">${index + 1}</div>
                            <div class="item-name">${item.name}</div>
                            <div class="item-value">${item.value} sales</div>
                        </div>`;
                });
            }

            fillList(".performance-section .performance-card:nth-child(1) .performance-list", data.topSellers);
            fillList(".performance-section .performance-card:nth-child(2) .performance-list", data.topBrands);
            fillList(".performance-section .performance-card:nth-child(3) .performance-list", data.topModels);
        })

        .catch(err => {
            console.error("Failed to load manager stats", err);
        });
});