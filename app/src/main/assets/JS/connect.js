function sendRequest(cmd, tableName, info) {
    var stringRequest = JSON.stringify({ "cmd": cmd, "table": tableName, "data": info });
    var unParseResponse = window.vm.Request(stringRequest);
    var response = JSON.parse(unParseResponse);
    return response
}

function logOut(){
    window.vm.updateSettings("id" ,-1);
    window.vm.loadStartPage();
    window.vm.Toast("logged out successfully");
}
