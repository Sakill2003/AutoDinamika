
const carData = [
    {
        id: 1,
        name: "BMW M4 Competition",
        model: "M4 Competition",
        description: "High-performance luxury coupe with twin-turbo engine and advanced aerodynamics.",
        brand: "BMW",
        year: 2023,
        price: "$75,900",
        color: "Midnight Black",
        units: 3,
        images: [
            "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1542362567-b07e54358753?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1619767886558-efdc259cde1a?w=600&h=400&fit=crop"
        ]
    },
    {
        id: 2,
        name: "Mercedes-AMG GT",
        model: "AMG GT 63 S",
        description: "Luxury sports car combining performance with sophisticated comfort and cutting-edge technology.",
        brand: "Mercedes-Benz",
        year: 2023,
        price: "$89,500",
        color: "Alpine White",
        units: 2,
        images: [
            "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1580273916550-e323be2ae537?w=600&h=400&fit=crop"
        ]
    },
    {
        id: 3,
        name: "Audi RS7 Sportback",
        model: "RS7 Sportback Performance",
        description: "Ultimate grand tourer with quattro all-wheel drive and distinctive fastback design.",
        brand: "Audi",
        year: 2024,
        price: "$92,800",
        color: "Nardo Gray",
        units: 1,
        images: [
            "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=600&h=400&fit=crop"
        ]
    },
    {
        id: 4,
        name: "Porsche 911 Turbo S",
        model: "911 Turbo S",
        description: "Iconic sports car delivering exceptional performance with timeless design and engineering excellence.",
        brand: "Porsche",
        year: 2023,
        price: "$207,000",
        color: "Guards Red",
        units: 1,
        images: [
            "https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=600&h=400&fit=crop"
        ]
    },
    {
        id: 5,
        name: "Lamborghini Huracán",
        model: "Huracán EVO",
        description: "Italian supercar with naturally aspirated V10 engine and advanced vehicle dynamics.",
        brand: "Lamborghini",
        year: 2023,
        price: "$248,295",
        color: "Arancio Borealis",
        units: 1,
        images: [
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1542362567-b07e54358753?w=600&h=400&fit=crop"
        ]
    },
    {
        id: 6,
        name: "McLaren 720S",
        model: "720S Performance",
        description: "British supercar featuring carbon fiber construction and advanced aerodynamics for ultimate performance.",
        brand: "McLaren",
        year: 2022,
        price: "$299,000",
        color: "Volcano Orange",
        units: 1,
        images: [
            "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=600&h=400&fit=crop",
            "https://images.unsplash.com/photo-1542362567-b07e54358753?w=600&h=400&fit=crop"
        ]
    }
];

let currentImageIndex = 0;
let currentCarData = null;
let filteredCars = [...carData];

fetch('components/header.html')
    .then(res => res.text())
    .then(data => {
        document.getElementById('header').innerHTML = data;
        const script = document.createElement('script');
        script.src = 'scripts/header.js';
        document.body.appendChild(script);
    });

function populateInventory(cars = carData) {
    const grid = document.getElementById('inventoryGrid');
    grid.innerHTML = '';

    if (cars.length === 0) {
        grid.innerHTML = '<div style="grid-column: 1/-1; text-align: center; color: #b0b0b0; font-size: 1.2rem; padding: 40px;">No cars found matching your criteria.</div>';
        return;
    }

    cars.forEach(car => {
        const cardHTML = `
                    <div class="car-card" onclick="openModal(${car.id})">
                        <img src="${car.images[0]}" alt="${car.name}" class="car-image">
                        <div class="car-info">
                            <h3 class="car-name">${car.name}</h3>
                            <p class="car-price">${car.price}</p>
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
        const matchesSearch = car.name.toLowerCase().includes(searchTerm) ||
            car.model.toLowerCase().includes(searchTerm) ||
            car.description.toLowerCase().includes(searchTerm);
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

    populateInventory();
});

function openModal(carId) {
    currentCarData = carData.find(car => car.id === carId);
    if (!currentCarData) return;

    document.getElementById('modalTitle').textContent = currentCarData.name;
    document.getElementById('detailModel').textContent = currentCarData.model;
    document.getElementById('detailDescription').textContent = currentCarData.description;
    document.getElementById('detailBrand').textContent = currentCarData.brand;
    document.getElementById('detailYear').textContent = currentCarData.year;
    document.getElementById('detailPrice').textContent = currentCarData.price;
    document.getElementById('detailColor').textContent = currentCarData.color;
    document.getElementById('detailUnits').textContent = currentCarData.units;

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

populateInventory();