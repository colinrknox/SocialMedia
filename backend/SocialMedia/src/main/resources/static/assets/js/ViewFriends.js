async function viewFriends(element){
    if(event.key === 'Enter') {
        const requestOptions = {
            method: 'POST',
            //headers: { 'Content-Type': 'application/json' },
            body: element.value
        };
        
        const response = await fetch('http://localhost:9001/user/email', requestOptions);
        const ourJSON = await response.json();
        if(ourJSON.id != null){
            window.location.replace("http://localhost:9001/profile.html?id=" + ourJSON.id);
        }else{
            alert("No user with this email address")
        }
        
    }
  }