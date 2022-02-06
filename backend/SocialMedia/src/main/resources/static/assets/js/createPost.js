const sendPostBtn = document.getElementById("new_post_form");

const createNewPost = (event) => {
    event.preventDefault();
    console.log("event => ", event);

    console.log("createNewPost");

    let text = document.getElementById("new_post_text").value;
    let image = document.getElementById("new_post_image").files[0];
    console.log("text", text);
    console.log("image", image);

    submitNewPost(text, image);
};

sendPostBtn.addEventListener("submit", createNewPost);

const submitNewPost = async (text, image) => {
    console.log("submitNewPost");
    console.log("text", text);
    console.log("image", image);

    let postBody = {
        post: text,
        img: image,
    };
    console.log("postBody===>", postBody);
    let post = await fetch(`http://${LOCAL_HOST}/api/posts/create`, {
        method: "POST",
        // headers: {
        //     'Content-Type': 'application/json'
        // },
        body: postBody,
    })
        .then((response) => {
            if (response.ok) {
                console.log("Create post success");
            } else {
                console.log("Create post Failed #1");
            }
        })
        .catch((error) => {
            console.log("Create post Failed #2");
            console.log("error => ", error);
        });
};
