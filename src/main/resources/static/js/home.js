window.onload = (event) =>{
    $.ajax({
        url: "http://localhost:8080/home/",
        method: "GET",
        success: function(response) {
            let maindiv = document.getElementById("main");

          

            let element = "";
            for( data in response){

//                element = `<div class="card" style="width: 180rem;">
//                <div class="card-body">
//                  <h5 class="card-title">${response[data]["title"]}</h5>
//                  <h6 class="card-subtitle mb-2 text-muted">${response[data]["account"]}</h6>
//                  <p class="card-text">${response[data]["body"]}</p>
//                  <a href="#" class="card-link">${response[data]["date"]}</a>
//                  <a href="/getpost?id=${response[data]["id"]}" class="card-link">View</a>
//                </div>
//              </div>`
                const dateString = response[data]["date"];
                const date = new Date(dateString);
                const options = { year: 'numeric', month: 'long', day: 'numeric' };
                const formattedDate = date.toLocaleDateString('en-US', options);
                element = `<div class="post-preview">
                           <a href="/getpost?id=${response[data]["id"]}">
                               <h2 class="post-title">${response[data]["title"]}</h2>
                               <h3 class="post-subtitle">${response[data]["body"]}</h3>
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