window.onload = () => {
    document.getElementById("about").onsubmit = updateAbout;
}

const updateAbout = async (event) => {
    event.preventDefault();

    let about = document.getElementById("about_info_popup");

    submitUpdateAbout(about);
}

const submitUpdateAbout = async (about) => {
    let postBody = JSON.stringify({
        'about': about
    })

    let result = await fetch(`http://${LOCAL_HOST}/about/save`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: postBody
    })
    .then(response  => {
        if (response.ok) {
            console.log("About update success");
            document.getElementById("about_info").innerText = about;
            // loginFailAlert.classList.add('d-none');
            // location.href = `http://${LOCAL_HOST}/feed.html`;
        } else {
            console.log("About update Failed #1");
            // loginFailAlert.classList.remove('d-none');
        }
    })
    .catch(error => {
        console.log("About update Failed #2");
        console.log("error => ", error);
        // loginFailAlert.classList.remove('d-none');
    });
}