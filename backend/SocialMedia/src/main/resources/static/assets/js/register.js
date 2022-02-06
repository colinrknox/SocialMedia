console.log("connected");

window.onload = () => {
    document.getElementById("registerForm").onsubmit = registerSubmit;
}

function registerSubmit(event)
{
	console.log("right after button is clicked");
    event.preventDefault();
    let email = document.getElementById("email").value; 
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let password = document.getElementById("password").value; 
    console.log("right before");
    let data = {
        'email': email,
        'firstName': firstName,
        'lastName': lastName,
        'password': password
    };
    console.log("Right after data is added");
    console.log(data);
    sendRegister(data);
}

async function sendRegister(data)
{
    let succcess = document.getElementById('regSuccess');
    let fail = document.getElementById('regFail');
    let result = await fetch(`http://localhost:9001/register`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => {
        if(response.ok)
        {
            succcess.classList.remove('d-none');
            fail.classList.add('d-none');
        }
        else
        {
            fail.classList.remove('d-none');
            succcess.classList.add('d-none');
        }
    })
}

