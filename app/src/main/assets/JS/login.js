function login() {
    var email = document.getElementById("email").value;
    var paswword = document.getElementById("password").value;

    var info = { "email": email, "password": password }

    var result = sendRequest('insert', 'Profile', info);
    if (result) {
        changeUrl('home.html');
        _id = 0;
    } else {
        alert('Something went worng, please try again');
        changeUrl('register.html');
    }
}