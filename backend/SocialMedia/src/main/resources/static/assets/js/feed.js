window.onload = function(){ 

   getPosts();


}

async function getPosts(){

   const response = await fetch('http://localhost:9001/api/posts/all');
   const ourJSON = await response.json();

   
   let htmlElements = "";
   for(let i= 0; i< ourJSON.length; i++){
      const response2 = await fetch('http://localhost:9001/user/' + ourJSON[i].author);
      const ourJSON2 = await response2.json();

      let div1 = document.createElement("div");
      div1.className = "post";
      let div2 = document.createElement("div");
      div2.className = "post__header"
      let div3 = document.createElement("div");
      div3.className = "post__header-right";

      let a = document.createElement("a");
      a.className = "post__avatar-link round-avatar__container";

      let img = document.createElement("img");
      img.className = "round-avatar__avatar";
      img.src = ourJSON2.photo;
      img.alt = "avatar";
      a.appendChild(img);

      let a2 = document.createElement("a");
      a2.className = "post__author font-weight-bold";
      a2.innerHTML = ourJSON2.firstName + " " + ourJSON2.lastName;
      let date = document.createElement("p");
      date.className = "post__date mt-2";
      date.innerHTML = ourJSON[i].creationDate.split("T")[0];
      div3.appendChild(a2);
      div3.appendChild(date);

      let div4 = document.createElement("div");
      div4.className = "post__content";
      let text = document.createElement("p");
      text.className = "mt-2"
      text.innerHTML = (ourJSON[i].text);
      div4.appendChild(text);

      let img2 = document.createElement("img");
      img2.className = "post__image mt-2";
      img2.src = ourJSON[i].image;
      img2.alt = "post picture";
      div4.appendChild(img2);
      
      div2.appendChild(a);
      div1.appendChild(div2);
      div2.appendChild(div3);
      div1.appendChild(div4);

      let container = document.getElementsByClassName("posts")[0];
      container.appendChild(div1);
   

   }
   

}