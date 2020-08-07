window.onerror = handlingError;

var password = document.getElementById("password");
var confirm_password = document.getElementById("confirm_password");

function validatePassword() {
    if (password.value !== confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
        confirm_password.setCustomValidity('');
    }
}

//password.onchange = validatePassword;
//confirm_password.onkeyup = validatePassword;

$('#password').change(function() { validatePassword(); });
$('#confirm_password').on('change', function() { validatePassword(); });

function register() {
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let info = JSON.stringify({ "Name": name, "Email": email, "Password": password });
    let stringRequest = JSON.stringify({ "cmd": 'insert', "table": 'Profile', "data": info });
    window.vm.Request(stringRequest);
}

function handleResponse(response) {
    if (response)
        location.href = 'home.html';
}