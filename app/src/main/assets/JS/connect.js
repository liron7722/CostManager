var _id;
var _transactionID;
var ProfileTableName = "Profile"
var TransactionTableName = "Transactions"
var SettingsTableName = "Settings"


function sendRequest(cmd, tableName, data) {
    alert("request Send")
    var data = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": data });
    return window.vm.Request(data);
}
