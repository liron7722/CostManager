var _id;
var _transactionID;
var ProfileTableName = "Profile"
var TransactionTableName = "Transactions"
var SettingsTableName = "Settings"


function sendRequest(cmd, tableName, info) {
    var stringRequest = JSON.stringify({ "cmd": cmd, "table": tableName, "data": info });
    var unParseResponse = window.vm.Request(stringRequest);
    var response = JSON.parse(unParseResponse);
    return response
}
