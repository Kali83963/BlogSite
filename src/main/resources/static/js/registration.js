
document.addEventListener("DOMContentLoaded",function(){
    const register_button = document.getElementById("register-button");




    register_button.addEventListener('click',function(){

        const firstname = document.getElementById("typeFirstX");
        const lastname = document.getElementById("typeLastX");
        const email = document.getElementById("typeEmailX");
        const password1 = document.getElementById("typePasswordX1");
        const password2 = document.getElementById("typePasswordX2");


        if(password1.value!=password2.value){
            console.log("Password not same")
        }
        else{
        console.log("eles condition")


       data=  {
            "firstname":firstname.value ,
            "lastname": lastname.value,
            "email": email.value,
            "password": password1.value
        }

        $.ajax({
            url:"http://localhost:8080/auth/register",
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



        }

    })


})