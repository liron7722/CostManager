$("#hidden").hide();
$("#showmore").hide();

$("#moreinfo").click(function() {

    $("#showmore").show();
    $("#moreinfo").hide();
});

$("#isrepeat").click(function() {
    if ($(this).is(":checked")) {
        $("#hidden").show();
    } else {
        $("#hidden").hide();
    }
});



function addTransaction() {
    var name = document.getElementById("name").value;
    var category = document.getElementById("category").value;
    var isincome = document.getElementById("isincome").value;
    var date = document.getElementById("date").value;
    var amount = document.getElementById("amount").value;
    var price = document.getElementById("price").value;
    var currency = document.getElementById("currency").value;
    var paymenttype = document.getElementById("paymenttype").value;
    var description = document.getElementById("description").value;
    var isrepeat = document.getElementById("isrepeat").value;
    var repeat = document.getElementById("repeat").value;

    var info = { "name": name, "email": email, "password": password }

    var result = sendRequest('insert', 'Profile', info);
    if (result) {
        changeUrl('home.html');
        _id = 0;
    } else {
        alert('Something went worng, please try again');
        changeUrl('register.html');
    }
}