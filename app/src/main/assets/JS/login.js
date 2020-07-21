function attemptLogin() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let info = JSON.stringify({"columns": "id",
                               "whereClause": "Email = ? AND Password = ?",
                               "whereArgs": email + ',' + password});
    let stringRequest = JSON.stringify({ "cmd": 'get', "table": 'Profile', "data": info });
    window.vm.Request(stringRequest);
}


function handleResponse(response){
    if (response) {
        location.href='home.html';
    }
}


//window.vm.getTest();