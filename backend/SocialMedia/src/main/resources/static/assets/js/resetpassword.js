
const LOCAL_HOST = 'localhost:9001';

window.onload = () => {
    document.getElementById("resetForm").onsubmit = changePassword;
}

function changePassword(event) {
    event.preventDefault();

    let pass = document.getElementById('password').value;
    sendNewPassword(pass);
}

async function sendNewPassword(password) {
    let failBox = document.getElementById('resetFail');
    let successBox = document.getElementById('resetSuccess');

    let urlParams = new URLSearchParams(window.location.search);
    let uuid = urlParams.get('token');
    let result = await fetch(`http://${LOCAL_HOST}/password/reset/${uuid}`, {
        method: 'POST',
        body: password
    })
    .then(response => {
        if (response.ok) {
            failBox.classList.add('d-none');
            successBox.classList.remove('d-none');
        } else {
            failBox.classList.remove('d-none');
            successBox.classList.add('d-none');
        }
    });
}