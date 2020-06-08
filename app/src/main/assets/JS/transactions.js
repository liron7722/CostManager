function getData(){
    var info = JSON.stringify({"columns": null,
                               "whereClause": "id = ?",
                               "whereArgs": []});
    var stringRequest = JSON.stringify({ "cmd": "get", "table": "Transactions", "data": info });
    window.vm.Request(stringRequest);
}

function handleResponse(data) {
    var table = document.getElementById("Table");
    for(var index = 1; index <= data.length; index++){
        AddTableRow(data[index], index, table);
    }        
}

function AddTableRow(data, index, table){
    var col = 0;
    var row = table.insertRow(index);
    for(value in data){
        AddTableCell(data[value], col++, row);
    }        
}

function AddTableCell(value, index, row){
    var cell = row.insertCell(index);
    cell.innerHTML = value;
}

function DeleteTableRow(index) {
  document.getElementById("Table").deleteRow(index);
}

//getData();
//Delete all below this line after fixing app call
var data2 = [
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
handleResponse(data2); 