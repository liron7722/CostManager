var data = [
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

function updateList(data) {
    $(document).ready(function() {
        var table = '<table" class="table table-striped"';

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