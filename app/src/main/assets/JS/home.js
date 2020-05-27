var _id;
var _transactionID;
var ProfileTableName = "Profile"
var TransactionTableName = "Transactions"
var SettingsTableName = "Settings"



$("button.getBalance").on({
    click: function() {
        changeUrl("balance.html")
        //var req = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": query }); //fix
        //sendRequest("get", TransactionTableName, req);
    }
});

$("button#addTransaction").on({
    click: function() {
        changeUrl("add-transaction.html")
        var req = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": query }); //fix
        sendRequest("insert", TransactionTableName, req);
        
    }
});

$("button#getTransaction").on({
    click: function() {
        var req = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": query }); //fix
        sendRequest("get", TransactionTableName, req);
        changeUrl("transactions.html");
    }
});

$("button#show").on({
    click: function() {
            var style = window.getComputedStyle("#show");
        console.log(style.display);
        if (style.display === 'none') { $('#showData').show(); } else { $('#showData').hide(); }
    }
});

function changeUrl(url) {
    window.location.href = url;
}
