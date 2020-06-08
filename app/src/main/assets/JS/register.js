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
    var info = JSON.stringify({ "Name": name, "Email": email, "Password": password });
    var stringRequest = JSON.stringify({ "cmd": 'insert', "table": 'Profile', "data": info });
    window.vm.Request(stringRequest);
}

function handleResponse(response){
    if (response)
        window.vm.loadStartPage();
}
