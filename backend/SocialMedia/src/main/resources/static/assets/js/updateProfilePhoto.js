const sendPhotoBtn = document.getElementById("update_user_photo");

const updateProfilePhoto = (event) => {
    event.preventDefault();

    let photo = document.getElementById("update_user_photo").files[0];
    console.log("photo", photo);
    submitUpdatephoto(photo);
}

sendPhotoBtn.addEventListener('change', updateProfilePhoto);

const submitUpdatephoto = async (photo) => {
    let result = await fetch(`http://${LOCAL_HOST}/photo/save`, {
        method: "POST",
        body: photo,
    })
        .then((response) => {
            if (response.ok) {
                console.log("Photo update success");
                return response.json();
            } else {
                console.log("Photo update Failed #1");
            }
        })
        .then((Object) => {
            document.getElementById("user_photo").src = Object.photo;
        })
        .catch((error) => {
            console.log("Photo update Failed #2");
            console.log("error => ", error);
        });
}