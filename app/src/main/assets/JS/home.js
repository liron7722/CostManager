window.onerror = handlingError;

$("button#addTransaction").on({
    click: function() {
        changeUrl("add-transaction.html");
    }
});


LoginStatus();