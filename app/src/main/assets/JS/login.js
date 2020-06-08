function attemptLogin() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var info = JSON.stringify({"columns": "id",
                               "whereClause": "Email = ? AND Password = ?",
                               "whereArgs": email + ',' + password});
    var stringRequest = JSON.stringify({ "cmd": 'get', "table": 'Profile', "data": info });
    window.vm.Request(stringRequest);
}

/*
function handleResponse(response){
    if (response.result) {
        window.vm.Toast("logged in successfully");
    }
    else {
        window.vm.Toast("Couldn't logged in, check credentials");
    }
}
*/