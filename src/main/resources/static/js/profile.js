window.onload = (event) =>{

    const namediv = document.getElementById("name");
    const emaildiv = document.getElementById("email");

    namediv.innerText = localStorage.getItem("name");
    emaildiv.innerText = localStorage.getItem('email');


    $.ajax({
        url: "http://localhost:8080/post/user",
        method: "GET",
        headers: {
                    "Authorization": "Bearer " + localStorage.getItem("access_token")
                },
        success: function(response) {
            let maindiv = document.getElementById("main");



            let element = "";
            for( data in response){
                const content =  splitTags(response[data]["body"])

                let text = ''
                if(content.length!=0){
                    for(i=0;i<2;i++){
                            if(typeof content[i] !=='undefined' && content[i].length <100 && !content[i].includes("img")){
                            text += content[i];
                        }
                        else{
                            break;
                        }

                    }
                }
                else{
                    text = response[data]["body"]

                }






                const dateString = response[data]["date"];
                const date = new Date(dateString);
                const options = { year: 'numeric', month: 'long', day: 'numeric' };
                const formattedDate = date.toLocaleDateString('en-US', options);
                element = `<div class="post-preview">
                           <a href="/getpost?id=${response[data]["id"]}">
                               <h2 class="post-title">${response[data]["title"]}</h2>
                               <h3 class="post-subtitle">${text}</h3>
                           </a>
                           <p class="post-meta">
                               Posted by
                               <a href="#!">${response[data]["account"]}</a>
                               on ${formattedDate}
                           </p>
                       </div>
                       <!-- Divider-->
                       <hr class="my-4" />`

                maindiv.innerHTML += element;



            }
        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });


    console.log("Page Has Been Loaded")
};

const splitTags = (text) => {
  const regex = /<(\w+)>(.*?)<\/(\w+)>/gm;
  let matches = regex.exec(text);
  let result = [];
  while (matches) {
    result.push(matches[0]);
    matches = regex.exec(text);
  }
  return result;
};