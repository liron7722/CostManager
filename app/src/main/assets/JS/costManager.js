var _id;
var _transactionID;
var ProfileTableName = "Profile"
var TransactionTableName = "Transactions"
var SettingsTableName = "Settings"

var data = [{
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

$("button.getBalance").on({
    click: function() {
        changeUrl("balance.html")
        var req = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": query }); //fix
        sendRequest("get", TransactionTableName, req);
    }
});

$("button#addTransaction").on({
    click: function() {
        var req = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": query }); //fix
        sendRequest("insert", TransactionTableName, req);
        changeUrl("transactions.html")
    }
});

$("button#getTransaction").on({
    click: function() {
        var req = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": query }); //fix
        sendRequest("get", TransactionTableName, req);
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

function sendRequest(cmd, tableName, data) {
    alert("request Send")
    var data = JSON.stringify({ "cmd": cmd, "tableName": tableName, "data": data });
    return window.handler.Request(data);
}

function updateBalancePage() {
    updateChart(data);
    updateList(data);
}


function updateList(data) {
    $(document).ready(function() {
        var table = '<table class="table table-striped"';

        table += '<tr>';
        $.each(data[0], function(header, _) {
            table += '<th>' + header + '</th>';
        });

        table += '</tr>';
        $.each(data, function(_, value) {
            table += '<tr>';
            $.each(value, function(_, value) {
                table += '<td>' + value + '</td>';
            });
            table += '<tr>';
        });
        table += '</table>';
        $('#showData').html(table);
    });
}

function updateChart(data) {
    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
            datasets: [{
                label: '# of Votes',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}