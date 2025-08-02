
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

document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(localStorage.getItem('user'));

    document.getElementById('currentDate').textContent = new Date().toLocaleDateString();

    fetch(`http://localhost:8080/api/seller/stats/${user.id}`)
        .then(res => res.json())
        .then(data => {

            document.getElementById('seller-age').textContent = `${data.age} years`;
            document.getElementById('seller-birth').textContent = data.birthDate;
            document.getElementById('seller-phone').textContent = data.phone;
            document.getElementById('seller-email').textContent = data.email;
            document.getElementById('seller-address').textContent = data.address;

            document.getElementById('seller-commissions').textContent = `$${data.totalCommissions.toLocaleString()}`;
            document.getElementById('seller-sales').textContent = data.totalSales;

            const salesList = document.getElementById('sales-list');
            salesList.innerHTML = '';

            data.latestSales.forEach(sale => {
                const item = document.createElement('div');
                item.classList.add('sale-item');
                item.innerHTML = `
                    <div class="car-info">
                        <div class="car-brand">${sale.brand}</div>
                        <div class="car-model">${sale.model} ${sale.year}</div>
                    </div>
                    <div class="sale-date">${sale.saleDate}</div>
                `;
                salesList.appendChild(item);
            });
        })
        .catch(err => {
            console.error('Error fetching seller stats:', err);
            alert('Failed to load statistics');
        });
});