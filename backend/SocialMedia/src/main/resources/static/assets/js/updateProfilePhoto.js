const sendPhotoBtn = document.getElementById("update_user_photo");

console.log("sendPhotoBtn===>", sendPhotoBtn);

const updateProfilePhoto = (event) => {
    event.preventDefault();
    //console.log("event => ", event);
    console.log("updateProfilePhoto");

    let photo = document.getElementById("update_user_photo").files[0];
    console.log("photo", photo);
    submitUpdatephoto(photo);
}

sendPhotoBtn.addEventListener('change', updateProfilePhoto);

const submitUpdatephoto = async (photo) => {
    console.log("submitUpdatephoto");

    let result = await fetch(`http://${LOCAL_HOST}/photo/save`, {
        method: 'POST',
        // headers: {
        //     'Content-Type': 'application/json'
        // },
        body: photo
    })
        .then(response => {
            if (response.ok) {
                console.log("Photo update success");
                console.log(document.getElementById("user_photo").src);
                console.log(document.getElementById("photo").photo);

                // need to send requesti to s3 to retrieve photo
                // document.getElementById("user_photo").src = photo;
            } else {
                console.log("Photo update Failed #1");
            }
        })
        .catch(error => {
            console.log("Photo update Failed #2");
            console.log("error => ", error);
        });
}