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
    var paswword = document.getElementById("password").value;
    var info = { "name": name, "email": email, "password": password }

    var result = sendRequest('insert', 'Profile', info);
    if (result) {
        changeUrl('home.html');
        _id = 0;
    } else {
        alert('Something went worng, please try again');
        changeUrl('register.html');
    }
}