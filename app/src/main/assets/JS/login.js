function login() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var info = { "email": email, "password": password};
    return info;
}

function handleResponse(response){
    if (response.result) {
        window.vm.updateSettings("id" ,parseInt(response.data));
        window.vm.loadStartPage();
        window.vm.Toast("logged in successfully");
    }
    else {
        window.vm.Toast("Couldn't logged in, check credentials");
    }
}
