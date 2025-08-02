
let carData = [];
let currentImageIndex = 0;
let currentCarData = null;
let filteredCars = [];

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

function populateInventory(cars = carData) {
    const grid = document.getElementById('inventoryGrid');
    grid.innerHTML = '';

    if (cars.length === 0) {
        grid.innerHTML = '<div style="grid-column: 1/-1; text-align: center; color: #b0b0b0; font-size: 1.2rem; padding: 40px;">No cars found matching your criteria.</div>';
        return;
    }

    cars.forEach((car, index) => {
        const cardHTML = `
            <div class="car-card" onclick="openModal(${index})">
                <img src="${car.imgf}" alt="${car.brand} ${car.model}" class="car-image">
                <div class="car-info">
                    <h3 class="car-name">${car.brand} ${car.model}</h3>
                    <p class="car-price">$${car.price.toLocaleString()}</p>
                </div>
            </div>
        `;
        grid.innerHTML += cardHTML;
    });

    updateResultsInfo(cars.length);
}

function updateResultsInfo(count) {
    const resultsInfo = document.getElementById('resultsInfo');
    if (count === carData.length) {
        resultsInfo.textContent = `Showing all ${count} vehicles`;
    } else {
        resultsInfo.textContent = `Showing ${count} of ${carData.length} vehicles`;
    }
}

function filterCars() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const brandFilter = document.getElementById('brandFilter').value;

    filteredCars = carData.filter(car => {
        const matchesSearch = car.model.toLowerCase().includes(searchTerm) ||
            car.brand.toLowerCase().includes(searchTerm);
        const matchesBrand = !brandFilter || car.brand === brandFilter;

        return matchesSearch && matchesBrand;
    });

    populateInventory(filteredCars);
}

document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const brandFilter = document.getElementById('brandFilter');

    searchInput.addEventListener('input', filterCars);
    brandFilter.addEventListener('change', filterCars);

    fetch("http://localhost:8080/api/inventory")
        .then(res => res.json())
        .then(data => {
            if (!Array.isArray(data)) {
                throw new Error("Respuesta inesperada del backend: se esperaba un arreglo.");
            }
            carData = data.map((car, idx) => ({
                ...car,
                name: `${car.brand} ${car.model}`,
                images: [car.imgf, car.imgs, car.imgt],
                id: idx,
                color: car.color || '-'
            }));
            filteredCars = [...carData];
            populateInventory();
        })
        .catch(err => {
            console.error("Error cargando inventario:", err);
        });
});

function openModal(index) {
    currentCarData = filteredCars[index];
    if (!currentCarData) return;

    document.getElementById('modalTitle').textContent = currentCarData.name;
    document.getElementById('detailModel').textContent = currentCarData.model;
    document.getElementById('detailBrand').textContent = currentCarData.brand;
    document.getElementById('detailYear').textContent = currentCarData.year;
    document.getElementById('detailPrice').textContent = `$${currentCarData.price.toLocaleString()}`;
    document.getElementById('detailColor').textContent = currentCarData.color || '-';
    document.getElementById('detailUnits').textContent = currentCarData.availableUnits;

    for (let i = 0; i < 3; i++) {
        document.getElementById(`image${i + 1}`).src = currentCarData.images[i];
    }

    currentImageIndex = 0;
    showImage(0);

    document.getElementById('modalOverlay').style.display = 'flex';
    document.body.style.overflow = 'hidden';
}

function closeModal() {
    document.getElementById('modalOverlay').style.display = 'none';
    document.body.style.overflow = 'auto';
}

function showImage(index) {
    const images = document.querySelectorAll('.slider-image');
    const dots = document.querySelectorAll('.dot');

    images.forEach((img, i) => {
        img.classList.toggle('active', i === index);
    });

    dots.forEach((dot, i) => {
        dot.classList.toggle('active', i === index);
    });

    currentImageIndex = index;
}

function nextImage() {
    currentImageIndex = (currentImageIndex + 1) % 3;
    showImage(currentImageIndex);
}

function prevImage() {
    currentImageIndex = (currentImageIndex - 1 + 3) % 3;
    showImage(currentImageIndex);
}

function currentImage(index) {
    showImage(index - 1);
}

document.getElementById('modalOverlay').addEventListener('click', function (e) {
    if (e.target === this) {
        closeModal();
    }
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        closeModal();
    }
});
