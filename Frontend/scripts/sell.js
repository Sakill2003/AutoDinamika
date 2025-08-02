

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

document.addEventListener('DOMContentLoaded', function () {
    const carSelectButton = document.getElementById('carSelectButton');
    const carSelectOptions = document.getElementById('carSelectOptions');
    const selectedCarText = document.getElementById('selectedCarText');
    const hiddenCarSelect = document.getElementById('carSelect');

    console.log('Elements found:', {
        button: !!carSelectButton,
        options: !!carSelectOptions,
        text: !!selectedCarText,
        hidden: !!hiddenCarSelect
    });

    if (!carSelectButton || !carSelectOptions) {
        console.error('Car select elements not found');
        return;
    }

    carSelectButton.addEventListener('click', function (e) {
        e.preventDefault();
        e.stopPropagation();

        console.log('Button clicked');

        const isOpen = carSelectOptions.classList.contains('open');
        console.log('Is open:', isOpen);

        if (isOpen) {
            carSelectOptions.classList.remove('open');
            carSelectButton.classList.remove('open');
            console.log('Closing dropdown');
        } else {
            carSelectOptions.classList.add('open');
            carSelectButton.classList.add('open');
            console.log('Opening dropdown');
        }
    });

    document.querySelectorAll('.select-option').forEach(option => {
        option.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();

            const value = this.dataset.value;
            const carImage = this.querySelector('.car-image').textContent;
            const carName = this.querySelector('.car-name').textContent;

            console.log('Option selected:', value, carName);

            selectedCarText.innerHTML = `
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <div style="width: 30px; height: 20px; background: linear-gradient(45deg, #ff6b35, #ff8c61); border-radius: 4px; display: flex; align-items: center; justify-content: center; font-size: 0.8rem;">${carImage}</div>
                            <span>${carName}</span>
                        </div>
                    `;

            hiddenCarSelect.value = value;

            carSelectOptions.classList.remove('open');
            carSelectButton.classList.remove('open');
        });
    });

    document.addEventListener('click', function (e) {
        if (!carSelectButton.contains(e.target) && !carSelectOptions.contains(e.target)) {
            carSelectOptions.classList.remove('open');
            carSelectButton.classList.remove('open');
        }
    });

    fetch("http://localhost:8080/api/available-cars")
        .then(res => res.json())
        .then(data => {
            const carSelectOptions = document.getElementById('carSelectOptions');
            const hiddenCarSelect = document.getElementById('carSelect');

            carSelectOptions.innerHTML = '';
            hiddenCarSelect.innerHTML = '<option value="">Choose your car</option>';

            data.forEach(car => {
                // --- Visual dropdown option ---
                const optionDiv = document.createElement('div');
                optionDiv.className = 'select-option';
                optionDiv.dataset.value = car.chasis;

                optionDiv.innerHTML = `
                <div class="car-image">
                    <img src="${car.image}" alt="${car.displayName}" style="width:30px; height:auto;">
                </div>
                <div class="car-name">${car.displayName}</div>
            `;

                optionDiv.addEventListener('click', function (e) {
                    e.preventDefault();
                    e.stopPropagation();

                    const value = this.dataset.value;
                    const carName = this.querySelector('.car-name').textContent;
                    const carImg = this.querySelector('.car-image').innerHTML;

                    selectedCarText.innerHTML = `
                    <div style="display: flex; align-items: center; gap: 10px;">
                        ${carImg}
                        <span>${carName}</span>
                    </div>
                `;

                    hiddenCarSelect.value = value;

                    carSelectOptions.classList.remove('open');
                    carSelectButton.classList.remove('open');
                });

                carSelectOptions.appendChild(optionDiv);

                // --- Hidden <select> option ---
                const selectOption = document.createElement('option');
                selectOption.value = car.chasis;
                selectOption.textContent = car.displayName;
                hiddenCarSelect.appendChild(selectOption);
            });
        })
        .catch(err => {
            console.error("Error loading available cars:", err);
        });

    document.getElementById('carSellingForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);
        const data = Object.fromEntries(formData);

        if (!data.firstName || !data.lastName || !data.idType || !data.idNumber || !data.paymentMethod || !data.carSelect) {
            alert('Please fill in all required fields.');
            return;
        }

        const user = JSON.parse(localStorage.getItem('user'));

        const salePayload = {
            customerFirstName: data.firstName,
            customerLastName: data.lastName,
            idType: data.idType,
            documentNumber: data.idNumber,
            paymentMethod: data.paymentMethod,
            chasis: data.carSelect,
            sellerId: user.id
        };

        fetch("http://localhost:8080/api/sell-car", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(salePayload)
        })
            .then(res => res.json())
            .then(result => {
                console.log(salePayload)
                if (result.success) {
                    alert('Car sale successful!\nCommission earned: $' + result.commission.toFixed(2));
                    document.getElementById('carSellingForm').reset();
                    selectedCarText.textContent = 'Choose your car';
                    location.reload();
                } else {
                    alert('Sale failed: ' + result.message);
                }
            })
            .catch(err => {
                console.error("Sale error:", err);
                alert('Error processing sale.');
            });
    });
});