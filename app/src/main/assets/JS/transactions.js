function getData(){
    let info = JSON.stringify({"columns": null,
                               "whereClause": "id = ?",
                               "whereArgs": []});
    let stringRequest = JSON.stringify({ "cmd": "get", "table": "Transactions", "data": info });
    window.vm.Request(stringRequest);
}

function handleResponse(input) {
    console.log('input received');
    console.log(input);
    window.data = JSON.parse(input)["array"];
    updateTable(window.data);
}

function updateTable(data) {
    let addedRows = 1;
    let table = document.getElementById("Table");
    for(let index = addedRows, j = 1; index < data.length + addedRows; index++, j++){
        AddTableRow(data[index - addedRows], index, j, table);
    }
}

function AddTableRow(data, index, j, table){
    let col = 0;
    let row = table.insertRow(index);  // create row
    AddTableCell(j, col++, row);  // number of row added
    for(let value in data){
        AddTableCell(data[value], col++, row);  // insert values
    }        
}

function AddTableCell(value, index, row){
    let cell = row.insertCell(index);  // add cell
    if (index === 9){
        cell.innerHTML = '<input type="button" value=value data-role="button" data-icon="delete" data-iconpos="notext">'
    }
    else
        cell.innerHTML = value;
}

function DeleteTableRow(index) {
  document.getElementById("Table").deleteRow(index);
}

$("button.panelButton").on({
    click: function() {
        index = this.
        document.getElementById("Table").deleteRow(index);

    }
});

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
