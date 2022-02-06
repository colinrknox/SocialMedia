
window.onload = () => {
    sessionStorage['loggedIn'] = false;
    logout();
}

async function logout() {
    let data = await fetch('http://localhost:9001/logout').then(response => {
        location.href = 'http://localhost:9001/login.html';
    });
}