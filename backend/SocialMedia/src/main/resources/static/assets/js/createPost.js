// const LOCAL_HOST = 'localhost:9001';

window.onload = () => {
    console.log("window.onload");
    document.getElementById("new_post_form").onsubmit = createPost;
}

const createPost = async (event) => {
    console.log("createPost");

    event.preventDefault();

    let text = document.getElementById("new_post_text").value;
    // let image = document.getElementById("new_post_image");
    submitNewPost(text);
}

const submitNewPost = async (text) => {
    console.log("submitNewPost");
    console.log("text ===> ", text);
    // console.log("image ===> ", image);

    let postBody = JSON.stringify({
        "text": text
    })

    let post = await fetch(`http://${LOCAL_HOST}/api/posts/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: postBody
        
    })
    .then(response => {
        if (response.ok) {
            console.log("Create post success");
            // document.getElementById("about_info").innerText = about;
        } else {
            console.log("Create post Failed #1");
        }
    })
    .catch(error => {
        console.log("Create post Failed #2");
        console.log("error => ", error);
    });
}