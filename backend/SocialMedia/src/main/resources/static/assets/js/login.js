
const LOCAL_HOST = 'localhost:9001';

window.onload = () => {
    document.getElementById("loginForm").onsubmit = loginSubmit;
}

function loginSubmit(event) {
    event.preventDefault();

    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    login(email, password);
}

async function login(email, password) {
    let loginFailAlert = document.getElementById('loginFail');
    let postBody = JSON.stringify({
        'email': email,
        'password': password
    })
    let result = await fetch(`http://${LOCAL_HOST}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: postBody
    })
    .then(response  => {
        if (response.ok) {
            loginFailAlert.classList.add('d-none');
            sessionStorage['loggedIn'] = true;
            location.href = `http://${LOCAL_HOST}/feed.html`;
        } else {
            console.log("Login Failed");
            loginFailAlert.classList.remove('d-none');
        }
    })
    .catch(error => {
        console.log("Login Failed");
        loginFailAlert.classList.remove('d-none');
    });

}