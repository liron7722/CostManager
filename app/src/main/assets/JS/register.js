var password = document.getElementById("password")
var confirm_password = document.getElementById("confirm_password");

function validatePassword() {
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
        confirm_password.setCustomValidity('');
    }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;

function register() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var info = { "Name": name, "Email": email, "Password": password }
    return JSON.stringify(info);
}

function handleResponse(response){
    if (response.result) {
        window.vm.updateSettings(response.data);
        window.vm.loadStartPage();
        window.vm.Toast("User created");
    }
    else {
        window.vm.Toast('Something went wrong, please try again');
    }
}