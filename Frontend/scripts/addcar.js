
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

    const formData = new FormData(this);

    const carData = {
        brand: formData.get('brand'),
        model: formData.get('model'),
        year: parseInt(formData.get('year')),
        chassisNumber: formData.get('chasis'),
        engineNumber: formData.get('motor'),
        price: parseFloat(formData.get('price')),
        color: formData.get('color'),
        imgf: formData.get('image1'),
        imgs: formData.get('image2'),
        imgt: formData.get('image3')
    };

    if (!carData.chassisNumber || !carData.engineNumber || !carData.brand || !carData.model || !carData.year) {
        alert("All required fields must be filled.");
        return;
    }

    fetch("http://localhost:8080/api/add-car", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(carData)
    })
        .then(res => res.json())
        .then(result => {
            if (result.success) {
                const successMessage = document.getElementById('successMessage');
                successMessage.style.display = 'block';
                document.getElementById('inventoryForm').reset();

                setTimeout(() => {
                    successMessage.style.display = 'none';
                }, 3000);

                console.log("Car added:", result.message);
            } else {
                alert("Failed to add car: " + result.message);
            }
        })
        .catch(err => {
            console.error("Error adding car:", err);
            alert("Error while processing the request.");
        });
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
