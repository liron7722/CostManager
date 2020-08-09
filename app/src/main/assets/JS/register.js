window.onerror = handlingError;

function validatePassword() {
    let password = document.getElementById("password");
    let confirm_password = document.getElementById("confirm_password");

    if (password.value !== confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
        confirm_password.setCustomValidity('');
    }
}
$("#password").change(function () {
    validatePassword();
});

$("#confirm_password").change(function () {
    validatePassword();
});


$("#terms").on({
    click: function() {
        alert("This app on beta, bugs may happen.\nAll info given to the app saved only on the phone.");
    }
});

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
        changeUrl('home.html');
}