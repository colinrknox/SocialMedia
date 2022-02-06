const sendPostBtn = document.getElementById("new_post_form");


const createNewPost = (event) => {
    event.preventDefault();

    let text = document.getElementById("new_post_text").value;
    let image = document.getElementById("new_post_image").files[0];
    submitNewPost(text, image);
};

sendPostBtn.addEventListener("submit", createNewPost);

function handleErrors(response) {
    if (!(response.status === 200 || response.status === 204)) {
        return response.json()
            .then(response => {
                return Promise.reject({ code: response.status, message: response.message });
            })
            .catch(err => {
                throw err;
            });
    } else {
        return response.status === 200 ? response.json() : new Promise(function (resolve, reject) {
            resolve();
        });
    }
}

const submitNewPost = async (text, image) => {
    const formData = new FormData();
    formData.append('file', image);
    formData.append('text', text);
    const config = {
        headers: {
            'content-type': 'multipart/form-data' // TODO - remove?
        }
    };

    const url = `http://${LOCAL_HOST}/api/posts/create`;

    return fetch(url, {
        method: 'POST',
        body: formData,
        config: config
    })
        .then((response) => {
            if (response.ok) {
                console.log("Create post success");
                document.getElementById("new_post_form").reset();
            } else {
                console.log("Create post Failed #1");
            }
        })
        .catch((error) => {
            console.log("Create post Failed #2");
            console.log("error => ", error);
        });
};
