const LOCAL_HOST = 'localhost:9001';

window.onload = () => {
    console.log("window.onload");
    document.getElementById("about").onsubmit = updateAbout;
}

function updateAbout(event) {
    console.log("updateAbout");

    event.preventDefault();

    let about = document.getElementById("about_info_popup").value;

    submitUpdateAbout(about);
}

async function submitUpdateAbout (about) {
    console.log("submitUpdateAbout");
    console.log("about ===> ", about);

    // let postBody = about;

    let result = await fetch(`http://${LOCAL_HOST}/about/save`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: about
    })
    .then(response  => {
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

// const updateAbout = async (event) => {
//     console.log("updateAbout ");

//     event.preventDefault();

//     let about = document.getElementById("about_info_popup");

//     submitUpdateAbout(about);
// }

// const submitUpdateAbout = async (about) => {

//     console.log("submitUpdateAbout ");


//     let postBody = JSON.stringify({
//         'about': about
//     })

//     let result = await fetch(`http://${LOCAL_HOST}/about/save`, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: postBody
//     })
//     .then(response  => {
//         if (response.ok) {
//             console.log("About update success");
//             document.getElementById("about_info").innerText = about;
//             // loginFailAlert.classList.add('d-none');
//             // location.href = `http://${LOCAL_HOST}/feed.html`;
//         } else {
//             console.log("About update Failed #1");
//             // loginFailAlert.classList.remove('d-none');
//         }
//     })
//     .catch(error => {
//         console.log("About update Failed #2");
//         console.log("error => ", error);
//         // loginFailAlert.classList.remove('d-none');
//     });
// }