function addTransaction() {
    let id = parseInt(window.vm.getSettings("id"));
    let name = document.getElementById("name").value;
    let category = document.getElementById("category").value;
    let isIncome = document.getElementById("isincome").value;
    let date = document.getElementById("date").value;
    let amount = document.getElementById("amount").value;
    let price = document.getElementById("price").value;
    let currency = document.getElementById("currency").value;
    let paymentType = document.getElementById("paymenttype").value;
    let description = document.getElementById("description").value;
    let isRepeat = document.getElementById("isrepeat").value;
    let repeat = document.getElementById("repeat").value;

    isIncome = isIncome === "on";
    isRepeat = isRepeat === "on";

    let info = {"id": id, "TName": name, "Category": category, "isIncome": isIncome,
                "Date": date, "Amount": amount, "Price": price, "isRepeat": isRepeat, "Repeat": repeat,
                "Currency": currency, "PaymentType": paymentType, "Description": description};
    let stringRequest = JSON.stringify({ "cmd": 'insert', "table": 'Transactions', "data": info});
    window.vm.Request(stringRequest);
}

function handleResponse(response){
    if (response){
        location.href='home.html';
        console.log('Added successfully');
    }else
        console.log('Something went wrong, please try again');
}


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