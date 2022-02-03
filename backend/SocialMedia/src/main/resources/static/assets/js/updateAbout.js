const LOCAL_HOST = 'localhost:9001';

window.onload = () => {
    console.log("window.onload");
    document.getElementById("about").onsubmit = updateAbout;
}

const updateAbout = async (event) => {
    console.log("updateAbout");

    event.preventDefault();

    let about = document.getElementById("about_info_popup").value;

    submitUpdateAbout(about);
}

const submitUpdateAbout = async (about) => {
    console.log("submitUpdateAbout");
    console.log("about ===> ", about);

    let result = await fetch(`http://${LOCAL_HOST}/about/save`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: about
    })
    .then(response => {
        if (response.ok) {
            console.log("About update success");
            document.getElementById("about_info").innerText = about;
        } else {
            console.log("About update Failed #1");
        }
    })
    .catch(error => {
        console.log("About update Failed #2");
        console.log("error => ", error);
    });
}