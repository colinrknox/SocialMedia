
const LOCAL_HOST = 'localhost:9001'
window.onload = () => {
    document.getElementById('recoveryForm').onsubmit = recoverySubmit;
}

function recoverySubmit(event) {
    event.preventDefault();

    let email = document.getElementById("email");

    submitEmail(email);
}

async function submitEmail(email) {
    let successBox = document.getElementById('recoverySuccess');
    let failBox = document.getElementById('recoveryFail');

    let result = await fetch(`http://${LOCAL_HOST}/password/recovery`, {
        method: 'POST',
        body: email.value
    })
    .then(response => {
        if (response.ok) {
            successBox.classList.remove('d-none');
            failBox.classList.add('d-none');
        } else {
            successBox.classList.add('d-none');
            failBox.classList.remove('d-none');
        }
    });
}