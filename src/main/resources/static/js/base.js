
document.addEventListener("DOMContentLoaded", function() {
    const navdiv = document.getElementById("nav-items");
    if (localStorage.getItem("access_token")) {
    const element = `<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="/profile">Profile</a></li>
                           <li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href= "#" onclick="logout()">Logout</a></li>`

      navdiv.innerHTML += element;
    } else {
      console.log("not Here");
      const element = `<li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="/register">Register</a></li>
                             <li class="nav-item"><a class="nav-link px-lg-3 py-3 py-lg-4" href="/login">Login</a></li>`
      navdiv.innerHTML += element;
    }
  });




function logout(){
    localStorage.clear();
    window.location.href = '/'

}