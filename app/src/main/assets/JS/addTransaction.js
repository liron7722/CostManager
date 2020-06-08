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
    var id = parseInt(window.vm.getSettings("id"));
    var name = document.getElementById("name").value;
    var category = document.getElementById("category").value;
    var isIncome = document.getElementById("isincome").value;
    var date = document.getElementById("date").value;
    var amount = document.getElementById("amount").value;
    var price = document.getElementById("price").value;
    var currency = document.getElementById("currency").value;
    var paymentType = document.getElementById("paymenttype").value;
    var description = document.getElementById("description").value;
    var isRepeat = document.getElementById("isrepeat").value;
    var repeat = document.getElementById("repeat").value;

    var info = {"id": id, "TName": name, "Category": category, "isIncome": isIncome,
                "Date": date, "Amount": amount, "Price": price,
                "Currency": currency, "PaymentType": paymentType, "Description": description};
    return [info, isRepeat, repeat];
}

function handleResponse(response){
    if (response.result) {
        window.vm.updateSettings("id" ,parseInt(response.data));
        window.vm.loadStartPage();
        window.vm.Toast("Added successfully");
    }
    else {
        window.vm.Toast('Something went wrong, please try again');
    }
}