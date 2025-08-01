
const cars = [];


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
document.getElementById('inventoryForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(e.target);
    const carData = {
        id: Date.now(),
        brand: formData.get('brand'),
        model: formData.get('model'),
        year: parseInt(formData.get('year')),
        chasis: formData.get('chasis'),
        motor: formData.get('motor'),
        price: parseFloat(formData.get('price')),
        color: formData.get('color'),
        units: parseInt(formData.get('units')),
        images: [
            formData.get('image1'),
            formData.get('image2'),
            formData.get('image3')
        ],
        dateAdded: new Date().toISOString()
    };

    cars.push(carData);

    const successMessage = document.getElementById('successMessage');
    successMessage.style.display = 'block';

    e.target.reset();

    setTimeout(() => {
        successMessage.style.display = 'none';
    }, 3000);

    console.log('Car added to inventory:', carData);
    console.log('Total cars in inventory:', cars.length);
});

const inputs = document.querySelectorAll('input');
inputs.forEach(input => {
    input.addEventListener('focus', function () {
        this.parentElement.style.transform = 'translateY(-2px)';
    });

    input.addEventListener('blur', function () {
        this.parentElement.style.transform = 'translateY(0)';
    });
});
