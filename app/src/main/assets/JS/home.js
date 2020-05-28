

$("button.getBalance").on({
    click: function() {
        changeUrl("balance.html");
    }
});

$("button#addTransaction").on({
    click: function() {
        changeUrl("add-transaction.html");
    }
});

$("button#getTransaction").on({
    click: function() {
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
