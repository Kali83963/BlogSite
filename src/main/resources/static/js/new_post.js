document.addEventListener("DOMContentLoaded",function(){
    if(localStorage.getItem("access_token")== null){
        window.location.href = "/login?callback=addnewpost";
    }


    let body;
    const checkBodyAvailability = () => {
      return new Promise((resolve, reject) => {
      const editor = tinymce.get('mytextarea');
      if (editor && editor.initialized) {
        body = editor.getBody();
        resolve(body);
      } else {
        console.log("Not Available");
        reject(new Error("Body not available"));
      }
        });
    };



    tinymce.init({
        selector: '#mytextarea',
        autoresize: true,
        skin:'naked',
        theme:'silver',

        plugins: 'anchor autolink charmap codesample emoticons image link lists media searchreplace table visualblocks wordcount autoresize',
        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table | align lineheight | numlist bullist indent outdent | emoticons charmap | removeformat',
        placeholder: "Tell your story..",
        min_height: 500,
        setup: function (editor) {
                editor.on('change', function () {
                  editor.resize(); // Trigger resize when content changes dynamically
                });
                },
        init_instance_callback: () => {
          checkBodyAvailability()
            .then((body) => {
              // Proceed with the logic once body is available
              console.log(body);
            })
              // Continue with other operations or function calls
            .catch((error) => {
              console.log(error);
              // Handle the error condition if body is not available
            });
        }
        
    });




    const form = document.getElementById("post-form");

    form.addEventListener("submit",function(event){
    event.preventDefault();
    

    const title = document.getElementById("titleDiv");
    

    data = {
        "title": title.innerText,
        "body": body.innerHTML
    }


    $.ajax({
        url: "http://localhost:8080/post/",
        method: "POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token")
        },
        data:JSON.stringify(data),
        contentType: "application/json",
        success:function(response){
            console.log(response);
            window.location.href = "/getpost?id="+response["id"];
        },
        error:function(xhr,error,status){
            console.log(error)
        }
    })


})

});

function autoExpand(div) {

  div.style.height = "auto"; // Reset the height to allow the div to shrink
  div.style.height = div.scrollHeight + "px"; // Set the height to match the content

}