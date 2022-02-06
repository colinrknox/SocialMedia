console.log('connected to user post page');
window.onload = function () {
	if (!sessionStorage['loggedIn']) {
		location.href = 'http://localhost:9001/login.html';
	}
	document.getElementById("new_post_form").onsubmit = createPost;
	document.getElementById("about").onsubmit = updateAbout;
	document.getElementById("about").addEventListener('submit', updateAbout);
	document.getElementById("update_user_photo").addEventListener('change', updateProfilePhoto);
	getPosts();
}

//Functoion to create like and change color of heart icon
const likePost = function (id) {

	return async function (event) {


		let element = event.currentTarget;
		element.children[0].classList.add("post__like--red");
		const response = await fetch('http://localhost:9001/api/like/create/' + id, { method: 'POST' });
		location.reload();

	};
};

//Function to create comment 
const createComment = function (id) {

	return async function (event) {
		event.preventDefault();
		let element = event.currentTarget;
		let text = element.children[0].children[0].children[1].value;


		const requestOptions = {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ text: text, postId: id })
		};
		const response = await fetch('http://localhost:9001/api/comments/create/', requestOptions);
		location.reload();
	};
};

//luis change for certain user posts
async function getPosts() {
	//Request that gets certain user posts
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const email = urlParams.get('id');

	let response = null;
	let ourJSON = null;
	if (email != null) {
		response = await fetch('http://localhost:9001/api/posts/' + email);
		ourJSON = await response.json();
	} else {
		response = await fetch('http://localhost:9001/api/posts/myposts');
		ourJSON = await response.json();
	}



	for (let i = 0; i < ourJSON.length; i++) {
		//Get Author data of post
		const response2 = await fetch('http://localhost:9001/user/' + ourJSON[i].author);
		const ourJSON2 = await response2.json();

		//Get number of likes for post
		const response3 = await fetch('http://localhost:9001/api/likes/' + ourJSON[i].id);
		const ourJSON3 = await response3.json();
		//Get Comments from each post
		const response4 = await fetch('http://localhost:9001/api/comments/' + ourJSON[i].id);
		const ourJSON4 = await response4.json();

		//Create Divs
		let div1 = document.createElement("div");
		div1.className = "post";
		let div2 = document.createElement("div");
		div2.className = "post__header"
		let div3 = document.createElement("div");
		div3.className = "post__header-right";

		let a = document.createElement("a");
		a.className = "post__avatar-link round-avatar__container";

		//User profile photo
		let img = document.createElement("img");
		img.className = "round-avatar__avatar";
		img.src = ourJSON2.photo;
		img.alt = "avatar";
		a.appendChild(img);

		//User name and post creation date
		let a2 = document.createElement("a");
		a2.className = "post__author font-weight-bold";
		a2.innerHTML = ourJSON2.firstName + " " + ourJSON2.lastName;
		let date = document.createElement("p");
		date.className = "post__date mt-2";
		date.innerHTML = ourJSON[i].creationDate.split("T")[0];
		div3.appendChild(a2);
		div3.appendChild(date);

		//Text from post
		let div4 = document.createElement("div");
		div4.className = "post__content";
		let text = document.createElement("p");
		text.className = "mt-2"
		text.innerHTML = (ourJSON[i].text);
		div4.appendChild(text);

		//Image from post
		let img2 = document.createElement("img");
		img2.className = "post__image mt-2";
		img2.src = ourJSON[i].image;
		img2.alt = "post picture";
		div4.appendChild(img2);

		//Like post
		let div5 = document.createElement("div");
		div5.className = "post__actions";
		a3 = document.createElement("a");
		a3.className = "pointer";
		a3.innerHTML = `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="heart" id="post_like_icon"
      class="post__like pointer post_like_icon svg-inline--fa fa-heart fa-w-16" role="img" xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 512 512">
      <path  fill="currentColor"
          d="M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z">
      </path>
      </svg>
      <p class="post__action-description">Like</p>`
		a3.addEventListener("click", likePost(ourJSON[i].id));
		let likesNum = document.createElement("p");
		likesNum.innerHTML = ourJSON3;
		a4 = document.createElement("a");
		a4.className = "ml-10 pointer";
		a4.innerHTML = `<svg enable-background="new 0 0 512 512" height="35px" id="Layer_1" version="1.1"
      viewBox="0 0 512 512" xml:space="preserve" xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink">
      <path
          d="M170.476,166.19h155.097c4.285,0,7.76-3.469,7.76-7.754s-3.475-7.765-7.76-7.765H170.476c-4.285,0-7.754,3.48-7.754,7.765  S166.191,166.19,170.476,166.19z" />
      <path
          d="M348.088,203.362H202.74c-4.284,0-7.759,3.469-7.759,7.754s3.475,7.765,7.759,7.765h145.348c4.284,0,7.754-3.48,7.754-7.765  S352.372,203.362,348.088,203.362z" />
      <path
          d="M306.695,256.052H170.476c-4.285,0-7.754,3.469-7.754,7.754c0,4.284,3.469,7.754,7.754,7.754h136.219  c4.279,0,7.754-3.47,7.754-7.754C314.448,259.521,310.974,256.052,306.695,256.052z" />
      <path
          d="M396.776,86.288H115.225c-29.992,0-54.403,22.562-54.403,50.308v154.83c0,27.735,24.411,50.297,54.403,50.297h166.034  l119.812,83.989v-84.135c27.996-2.038,50.108-23.753,50.108-50.151v-154.83C451.179,108.85,426.768,86.288,396.776,86.288z   M427.906,291.426c0,14.902-13.972,27.025-31.131,27.025h-18.978v62.523l-89.193-62.523h-173.38  c-17.164,0-31.131-12.123-31.131-27.025v-154.83c0-14.913,13.967-27.035,31.131-27.035h281.551  c17.159,0,31.131,12.123,31.131,27.035V291.426z" />
  </svg>
  <p class="post__action-description">Comment</p>`
		div5.appendChild(a3);
		div5.appendChild(likesNum);
		div5.appendChild(a4);

		let div6 = document.createElement("div");
		div6.className = "post__comments";



		for (let j = 0; j < ourJSON4.length; j++) {
			//Request to get user data from each comment
			const response5 = await fetch('http://localhost:9001/user/' + ourJSON4[j].author);
			const ourJSON5 = await response5.json();
			//Get photo from user
			let img3 = document.createElement("img");
			img3.className = "post__avatar";
			img3.src = ourJSON5.photo;
			img3.alt = "avatar";
			//Comment Author
			let commentAuthor = document.createElement("p");
			commentAuthor.className == "post__comment-author";
			commentAuthor.innerHTML = ourJSON5.firstName + " " + ourJSON5.lastName;
			//Comment content
			let comment = document.createElement("p");
			comment.className == "post__comment-content";
			comment.innerHTML = ourJSON4[j].text;


			let div7 = document.createElement("div");
			div7.className = "post__comment";
			div7.appendChild(img3);
			let div8 = document.createElement("div");
			div8.className = "post__comment-text";

			div8.appendChild(commentAuthor);
			div8.appendChild(comment);
			div7.appendChild(div8);
			div6.appendChild(div7);
		}


		//Get Data From Current User
		const response6 = await fetch('http://localhost:9001/myaccount');
		const ourJSON6 = await response6.json();
		div9 = document.createElement("div");
		div9.className = "post__write-comment";
		//Use photo from current user
		let form = document.createElement("form");
		form.className = "post__write-comment"
		div9.innerHTML = `<div class="post__write-comment-top">
      <img class="post__avatar" src=`+ ourJSON6.photo + `/>
      <textarea class="post__write-comment-input" id = new_comment_text rows="5" name = text cols="10"></textarea>
      </div> `

		//Create Comment
		let commentInput = document.createElement("input");
		commentInput.className = "btn btn-primary ml-40 mt-10";
		commentInput.setAttribute("type", "submit");
		commentInput.setAttribute("value", "submit");
		div9.appendChild(commentInput);
		form.appendChild(div9);
		form.addEventListener("submit", createComment(ourJSON[i].id));
		div6.appendChild(form);


		div2.appendChild(a);
		div1.appendChild(div2);
		div2.appendChild(div3);
		div1.appendChild(div4);
		div1.appendChild(div5);
		div1.appendChild(div6);

		let container = document.getElementsByClassName("posts")[0];
		container.appendChild(div1);



		//luis Addition to set the side bar of the profile page
		let gettingUser = null;
		let user = null;

		if (email != null) {
			gettingUser = await fetch('http://localhost:9001/user/' + email);
			user = await gettingUser.json();
		} else {
			gettingUser = await fetch('http://localhost:9001/myaccount');
			user = await gettingUser.json();
		}

		let profilename = document.getElementById('profileName');
		profilename.innerText = (user.firstName + " " + user.lastName);

		let profileInfo = document.getElementById('about_info');
		profileInfo.innerText = user.about;


		let profilePic = document.getElementById('profilePic');
		profilePic.src = user.photo;

		let profilePic2 = document.getElementById('profilePic2');
		profilePic2.src = user.photo;


	}


}

/***************************************
 * SAVE POST FUNCTIONALITY
 ***************************************/

function createPost(event) {
	event.preventDefault();
	console.log("createPost");

	let text = document.getElementById("new_post_text").value;
	let image = document.getElementById('new_post_image').files[0];
	console.log(image);
	submitNewPost(text, image);
}

async function submitNewPost(text, image) {
	console.log("submitNewPost");
	console.log("text ===> ", text);
	// console.log("image ===> ", image);

	let response = await fetch(`http://localhost:9001/api/posts/create`, {
		method: 'POST',
		body: text
	});
	if (!response.ok) {
		console.log('Create post failed');
		return;
	}
	let postObj = await response.json();

	if (image && postObj) {
		await fetch(`http://localhost:9001/api/posts/add/photo/${postObj.id}`, {
			method: 'POST',
			body: image
		}).then(response => {
			if (response.ok) {
				console.log('image added to post');
			} else {
				console.log('image failed to be added');
			}
		});
	}
	getPosts();
}

/***************************************
 * SAVE ABOUT FUNCTIONALITY
 ***************************************/

function updateAbout(event) {
	event.preventDefault();

	let about = document.getElementById("about_info_popup").value;
	submitUpdateAbout(about);
}

async function submitUpdateAbout(about) {
	let result = await fetch(`http://localhost:9001/about/save`, {
		method: 'POST',
		body: about
	}).then(response => {
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

/***************************************
 * UPDATE PROFILE PHOTO FUNCTIONALITY
 ***************************************/
function updateProfilePhoto(event) {
	event.preventDefault();

	let photo = document.getElementById("update_user_photo").files[0];
	console.log("photo", photo);
	submitUpdatePhoto(photo);
}

async function submitUpdatePhoto(photo) {
	let result = await fetch(`http://localhost:9001/photo/save`, {
		method: "POST",
		body: photo,
	}).then((response) => {
		if (response.ok) {
			console.log("Photo update success");
			return response.json();
		} else {
			console.log("Photo update Failed #1");
		}
	}).then((postObj) => {
		document.getElementById("user_photo").src = postObj.photo;
	}).catch((error) => {
		console.log("Photo update Failed #2");
		console.log("error => ", error);
	});
}