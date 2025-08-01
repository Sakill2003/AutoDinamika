

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

    document.getElementById('carSellingForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);
        const data = Object.fromEntries(formData);

        if (!data.firstName || !data.lastName || !data.idType || !data.idNumber || !data.paymentMethod || !data.carSelect) {
            alert('Please fill in all required fields.');
            return;
        }

        alert('Car sale confirmed successfully!\n\nDetails:\n' +
            'Customer: ' + data.firstName + ' ' + data.lastName + '\n' +
            'ID: ' + data.idType + ' - ' + data.idNumber + '\n' +
            'Payment: ' + data.paymentMethod.toUpperCase() + '\n' +
            'Car: ' + hiddenCarSelect.selectedOptions[0].text);

        this.reset();
        selectedCarText.textContent = 'Choose your car';
    });

    document.querySelectorAll('input, select').forEach(field => {
        field.addEventListener('focus', function () {
            this.parentElement.style.transform = 'translateY(-2px)';
        });

        field.addEventListener('blur', function () {
            this.parentElement.style.transform = 'translateY(0)';
        });
    });
});