
let isliked = false;
document.addEventListener("DOMContentLoaded",function(){

    const urlparam = new URLSearchParams(window.location.search)

    const id = urlparam.get("id");

    console.log(id)
    const editbutton = document.getElementById("edit-post");
    const deletebutton = document.getElementById("delete-post");
    let like_count = document.getElementsByClassName("likes-count")[0];
    const like_button = document.getElementById("like-button");
    let like_icon = document.getElementById("like-icon");
    const comment_button = document.getElementById("comment-sumbit");
//    console.log(like_count);
       const comment_div = document.getElementById("comment-div");

    let comments_count = document.getElementsByClassName("comments-count")[0];
    console.log(comments_count)
    $.ajax({
        url:"http://localhost:8080/post/"+id,
        method:"GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token")
        },
        success:function(response){
            
            let maindiv = document.getElementById("main-content");
            let headerdiv = document.getElementById("header");


            const dateString = response["date"];
                            const date = new Date(dateString);
                            const options = { year: 'numeric', month: 'long', day: 'numeric' };
                            const formattedDate = date.toLocaleDateString('en-US', options);
            const header = `<div class="post-heading">
                                <h1>${response['title']}</h1>
//                                <h2 class="subheading"></h2>
                                <span class="meta">
                                    Posted by
                                    <a href="#!">${response["account"]}</a>
                                    on ${formattedDate}
                                </span>
                            </div>`
            headerdiv.innerHTML +=  header;

            const element = `<p>${response["body"]}</p>`

            maindiv.innerHTML += element;
            like_count.innerText = response['like'];
            comments_count.innerText = response["comments"];

            isliked = response['hasuserlikepost']
//            console.log(isliked)
//            if(isliked){
//
//                isliked = false;
//                like_icon.classList.remove("fa-regular");
//                like_icon.classList.add("fa-solid");
//                console.log(like_icon)
//            }


        },
        error:function(xhr,error,status){
            console.log(error);
        }
    })


    $.ajax({
            url:"http://localhost:8080/comment/" +id,
            method:"GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("access_token")
            },
            contentType: "application/json",
            success:function(response){
                console.log(response)
                for(_ in response){
                    var newDiv = document.createElement("div");
                    newDiv.innerHTML = `<div class="d-flex mb-2 align-items-center justify-content-between">
                                   <div>
                                       <div>
                                           <img src="image/profile-photos/1/user.png" class="rounded-circle img-thumbnail" width="70px">
                                       </div>
                                       <div class="p-2 d-flex flex-column">
                                           <span>${response[_]['user']}</span>
                                           <span class="text-muted">Two Years Ago</span>
                                       </div>
                                       <div id="${response[_]['id']}"><p>${response[_]['content']}</p></div>
                                   </div>

                                    ${localStorage.getItem("email") == response[_]['user'] ?
                                   `<div class="dropdown">
                                       <a class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                           <i class="fa-solid fa-ellipsis"></i>
                                       </a>
                                       <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                           <li><a class="dropdown-item" href="#" onclick="showEditModal(${response[_]['id']})">Edit</a></li>
                                           <li><a class="dropdown-item" onclick="showConfirmationModal()">Delete</a></li>

                                       </ul>

                                   </div>
                                   <div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                                       <div class="modal-dialog modal-dialog-centered">
                                         <div class="modal-content">
                                           <div class="modal-header">
                                             <h5 class="modal-title" id="confirmationModalLabel">Confirmation</h5>
                                             <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                           </div>
                                           <div class="modal-body">
                                             <p>Are you sure you want to delete?</p>
                                           </div>
                                           <div class="modal-footer">
                                             <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                             <button type="button" class="btn btn-danger" onclick="deleteItem(${response[_]['id']})">Delete</button>
                                           </div>
                                         </div>
                                       </div>
                                     </div>

                                  </div>
                                  <div class="modal fade" id="editmodal" tabindex="-1" aria-labelledby="editModalLabel"
                                                                       aria-hidden="true">
                                      <div class="modal-dialog modal-dialog-centered">
                                          <div class="modal-content">
                                              <div class="modal-header">
                                                  <h5 class="modal-title" id="editModalLabel">Edit</h5>
                                                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                              </div>
                                              <div class="modal-body">
                                                  <div id="editDiv" class="border-none outline-none border-0 p-2 editable" oninput="autoExpand(this)" contenteditable="true" data-placeholder = "What are your thoughts?"></div>
                                              </div>
                                              <div class="modal-footer">
                                                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                  <button type="button" class="btn btn-success" onclick="editcomment(${response[_]['id']})">Edit</button>
                                              </div>
                                          </div>
                                      </div>
                                  </div>` : ''
                                   }

                               `
                            comment_div.prepend(newDiv);


                }

        //            window.location.href = "/";
            },
            error:function(xhr,error,status){
                console.log(error)
            }
            })




editbutton.setAttribute("href", `/editpost?id=${id}`)

deletebutton.addEventListener("click",()=> deletepost(id))

like_button.addEventListener("click",()=> togglelike(id))

comment_button.addEventListener("click",()=> addcomment(id,comments_count))

})

function deletepost(id){
    $.ajax({
        url:"http://localhost:8080/post/delete/"+id,
        method:"POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token")
        },
        data: JSON.stringify(id),
        contentType: "application/json",
        success:function(response){
            console.log(response)
//            window.location.href = "/";
        },
        error:function(xhr,error,status){
            console.log(error)
        }
    })
    console.log("Hello")
}


function togglelike(id){

     let like_count = document.getElementsByClassName("likes-count")[0];
     let like_icon = document.getElementById("like-icon");


     if(isliked == false){

        $.ajax({
                url:"http://localhost:8080/post/" +id +"/like",
                method:"POST",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("access_token")
                },
                contentType: "application/json",
                success:function(response){
                    console.log(response)
                    isliked = true;
                    like_icon.classList.remove("fa-regular");
                    like_icon.classList.add("fa-solid");
                    console.log(like_icon)
                    like_count.innerText = (parseInt(like_count.innerText, 10) + 1).toString();
        //            window.location.href = "/";
                },
                error:function(xhr,error,status){
                    console.log(error)
                }
            })





     }else{

            $.ajax({
            url:"http://localhost:8080/post/" +id +"/unlike",
            method:"POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("access_token")
            },
            contentType: "application/json",
            success:function(response){
                console.log(response)
               isliked = false;
               like_icon.classList.remove("fa-solid");
               like_icon.classList.add("fa-regular");
                like_count.innerText = (parseInt(like_count.innerText, 10) - 1).toString();;
    //            window.location.href = "/";
            },
            error:function(xhr,error,status){
                console.log(error)
            }
        })



     }
}

function autoExpand(div) {

  div.style.height = "auto"; // Reset the height to allow the div to shrink
  div.style.height = div.scrollHeight + "px"; // Set the height to match the content

}

function addcomment(id,comments_count){
    console.log("clicked")
    const content_div = document.getElementById("myDiv");
    const comment_div = document.getElementById("comment-div");
    data = {
        'post_id':id,
         'content': content_div.innerText

    }
    content_div.innerText = '';
    $.ajax({
        url:"http://localhost:8080/comment/",
        method:"POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token")
        },
        data: JSON.stringify(data),
        contentType: "application/json",
        success:function(response){
            console.log(response)
            var newDiv = document.createElement("div");
            newDiv.innerHTML = `<div class="d-flex mb-2 align-items-center justify-content-between">
                           <div>
                               <div>
                                   <img src="image/profile-photos/1/user.png" class="rounded-circle img-thumbnail" width="70px">
                               </div>
                               <div class="p-2 d-flex flex-column">
                                   <span>${localStorage.getItem("name")}</span>
                                   <span class="text-muted">Two Years Ago</span>
                               </div>
                               <div id = "${response['id']}"><p>${response['content']}</p></div>
                           </div>

                            ${localStorage.getItem("email") == response['user'] ?
                           `<div class="dropdown">
                               <a class="dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                   <i class="fa-solid fa-ellipsis"></i>
                               </a>
                               <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                   <li><a class="dropdown-item" href="#" onclick = "showEditModal(${response['id']})">Edit</a></li>
                                   <li><a class="dropdown-item" href="#" onclick="showConfirmationModal()">Delete</a></li>

                               </ul>
                           </div>
                           <div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel"
                               aria-hidden="true">
                               <div class="modal-dialog modal-dialog-centered">
                                 <div class="modal-content">
                                   <div class="modal-header">
                                     <h5 class="modal-title" id="confirmationModalLabel">Confirmation</h5>
                                     <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                   </div>
                                   <div class="modal-body">
                                     <p>Are you sure you want to delete?</p>
                                   </div>
                                   <div class="modal-footer">
                                     <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                     <button type="button" class="btn btn-danger" onclick="deleteItem(${response['id']})">Delete</button>
                                   </div>
                                 </div>
                               </div>
                             </div>


                             <div class="modal fade" id="editmodal" tabindex="-1" aria-labelledby="editModalLabel"
                                                                  aria-hidden="true">
                             <div class="modal-dialog modal-dialog-centered">
                                 <div class="modal-content">
                                     <div class="modal-header">
                                         <h5 class="modal-title" id="editModalLabel">Edit</h5>
                                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                     </div>
                                     <div class="modal-body">
                                         <div id="editDiv" class="border-none outline-none border-0 p-2 editable" oninput="autoExpand(this)" contenteditable="true" data-placeholder = "What are your thoughts?"></div>
                                     </div>
                                     <div class="modal-footer">
                                         <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                         <button type="button" class="btn btn-success" onclick="editcomment(${response['id']})">Edit</button>
                                     </div>
                                 </div>
                             </div>
                         </div>` : ''
                           }

                       </div>
                       `
                    comment_div.prepend(newDiv);
                    comments_count.innerText = (parseInt(comments_count.innerText, 10) + 1).toString();

    //            window.location.href = "/";
        },
        error:function(xhr,error,status){
            console.log(error)
        }
    })


}

function showConfirmationModal() {
  $('#confirmationModal').modal('show'); // Show the confirmation modal

}

function showEditModal(id){
    console.log(id)
    console.log($(`#${id} p`).text())
    $("#editDiv").text($(`#${id} p`).text());
    $('#editmodal').modal('show');

}

function deleteItem(id){
    console.log(id)
    $.ajax({
            url:"http://localhost:8080/comment/delete/"+id,
            method:"POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("access_token")
            },
            contentType: "application/json",
            success:function(response){
                console.log(response)
                window.location.reload();


        //            window.location.href = "/";
            },
            error:function(xhr,error,status){
                console.log(error)
            }
        })

}



function editcomment(id){
    console.log(id);
    const content = $("#editDiv").text()
    console.log(content);
    data = {
        'content':content
    }
    $.ajax({
        url:"http://localhost:8080/comment/edit/"+id,
        method:"POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token")
        },
        data: JSON.stringify(data),
        contentType: "application/json",
        success:function(response){
            console.log(response)
            $(`#${response['id']} p`).text(response['content'])
            $('#editmodal').modal('hide');



    //            window.location.href = "/";
        },
        error:function(xhr,error,status){
            console.log(error)
        }
    })


}

function showdeletepostModal(){
    $("#deletepostModal").modal("show")

}

function deletepost(id){
    console.log(id);

    $.ajax({
        url:"http://localhost:8080/post/delete/"+id,
        method:"POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("access_token")
        },

        contentType: "application/json",
        success:function(response){
            console.log(response)
            if(response == true){
                window.location.href = "/"
            }



    //            window.location.href = "/";
        },
        error:function(xhr,error,status){
            console.log(error)
        }
    })

}
