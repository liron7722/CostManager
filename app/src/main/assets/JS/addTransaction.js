$('#isrepeat').change(function() {
    if (!$(this).is(':checked'))
        $("#repeat").addClass("ui-state-disabled");
    else
        $("#repeat").removeClass("ui-state-disabled");
});

$('#moreInfo').change(function(){
    $("#showInfo").toggle();
});

function addTransaction() {
    let id = parseInt(window.vm.getSettings("id"));
    let name = document.getElementById("name").value;
    let category = document.getElementById("category").value;
    let isIncome = $('#isincome').is(':checked');
    let date = document.getElementById("date").value;
    let amount = document.getElementById("amount").value;
    let price = document.getElementById("price").value;
    let currency = document.getElementById("currency").value;
    let paymentType = document.getElementById("paymenttype").value;
    let description = document.getElementById("description").value;
    let isRepeat = $('#isrepeat').is(':checked');
    let repeat = document.getElementById("repeat").value;


    let info = {"id": id, "TName": name, "Category": category, "isIncome": isIncome,
                "Date": date, "Amount": amount, "Price": price, "isRepeat": isRepeat, "Repeat": repeat,
                "Currency": currency, "PaymentType": paymentType, "Description": description};
    console.log(info);
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

