document.addEventListener("DOMContentLoaded",function(){
    const login_button = document.getElementById("login-button");
    login_button.addEventListener("click",function(event){
        const emailinput = document.getElementById("typeEmailX");
        const passwordinput = document.getElementById("typePasswordX");
        console.log(emailinput.value)
        console.log(passwordinput.value)

        data = {
            "email":emailinput.value,
            "password":passwordinput.value
        }


        $.ajax({
            url:"http://localhost:8080/auth/authenticate",
            method: "POST",
            data: JSON.stringify(data),
             contentType: "application/json",
            success: function(response){
                console.log(response)

                localStorage.setItem("access_token",response["token"]);
                localStorage.setItem("name",response["name"]);
                localStorage.setItem("email",response["email"]);
                localStorage.setItem("id",response["id"]);

                // Go the call back Url
                const urlparam = new URLSearchParams(window.location.search)
                
                if(urlparam.get("callback") !=null){

                    window.location.href = "/" + urlparam.get("callback");
                }else{
                    window.location.href = "/";
                }

                


                // window.location.search
            },
            error: function(xhr, status, error){
                console.log(error);
                console.log(status);
                console.log(xhr);
            }
        })
    })
   
})

