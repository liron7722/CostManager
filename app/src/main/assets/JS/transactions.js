function getData(){
    let info = JSON.stringify({"columns": null,
                               "whereClause": "id = ?",
                               "whereArgs": []});
    let stringRequest = JSON.stringify({ "cmd": "get", "table": "Transactions", "data": info });
    window.vm.Request(stringRequest);
}

function handleResponse(input) {
    let data = JSON.parse(input)["array"];
    console.log(data);
    let addedRows = 2;
    let table = document.getElementById("Table");
    for(let index = addedRows; index <= data.length + addedRows; index++){
        AddTableRow(data[index - addedRows], index, table);
    }        
}

function AddTableRow(data, index, table){
    let col = 0;
    let row = table.insertRow(index);
    for(let value in data){
        AddTableCell(data[value], col++, row);
    }        
}

function AddTableCell(value, index, row){
    let cell = row.insertCell(index);
    cell.innerHTML = value;
}

function DeleteTableRow(index) {
  document.getElementById("Table").deleteRow(index);
}

getData();

//$(#Transactions).hide().trigger("updatelayout");

/*$(document).ready(function() {
    $('[data-toggle="toggle"]').change(function(){
        $(this).parents().next('.hide').toggle();
    });
});


$(document).ready(function(){
  $("#filterInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#Table tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
*/

//Delete all below this line after fixing app call
/*var data2 = [
    {
        "UserID": 1,
        "UserName": "rooter",
        "Password": "12345",
        "Country": "UK",
        "Email": "sac@gmail.com"
    },
    {
        "UserID": 2,
        "UserName": "binu",
        "Password": "123",
        "Country": "uk",
        "Email": "Binu@gmail.com"
    },
    {
        "UserID": 3,
        "UserName": "cal",
        "Password": "123",
        "Country": "uk",
        "Email": "cal@gmail.com"
    },
    {
        "UserID": 4,
        "UserName": "nera",
        "Password": "1234",
        "Country": "uk",
        "Email": "nera@gmail.com"
    }
];
handleResponse(data2); */