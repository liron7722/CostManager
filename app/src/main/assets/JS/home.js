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
        let style = window.getComputedStyle("#show");
        console.log(style.display);
        if (style.display === 'none') { $('#showData').show(); } else { $('#showData').hide(); }
    }
});

$(document).ready(function(){
    $(".taphold").bind("taphold", function(event) {
        $(this).html("<h1>was tapped</h1>");
    });
});

function changeUrl(url) {
    window.location.href = url;
}
