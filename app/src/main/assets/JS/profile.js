window.onerror = handlingError;

function update() {
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let info = JSON.stringify({ "Name": name, "Email": email, "Password": password, "whereClause": "id = ?", "whereArgs": []});

    let stringRequest = JSON.stringify({ "cmd": 'update', "table": 'Profile', "data": info });
    window.vm.Request(stringRequest);
    changeUrl("home.html");
}

