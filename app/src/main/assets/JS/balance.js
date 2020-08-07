window.onerror = handlingError;

function getData() {
    let info = JSON.stringify({
        "columns": null,
        "whereClause": "id = ?",
        "whereArgs": []
    });
    let stringRequest = JSON.stringify({ "cmd": "get", "table": "Transactions", "data": info });
    window.vm.Request(stringRequest);
}

function handleResponse(input) {
    console.log('input received');
    console.log(input);
    window.data = JSON.parse(input)["array"];
    showCharts(window.data);
}

function rgbaRandom() {
    let r = Math.floor(Math.random() * 255);
    let g = Math.floor(Math.random() * 255);
    let b = Math.floor(Math.random() * 255);
    return 'rgba(' + r.toString() + ', ' + g.toString() + ', ' + b.toString() + ', 1';
}

function transactionsChart(data) {
    let lb = [];
    let dataNum = [];
    let bgC = [];
    let bC = [];
    let rgbaGreen = 'rgba(41, 241, 195, 1)';
    let rgbaRed = 'rgba(246, 36, 89, 1)';
    let stringColor;
    data.forEach(function(ta) {
        lb.push(ta.TName);
        dataNum.push(ta.Price);
        if (ta.isIncome === "1")
            stringColor = rgbaGreen;
        else
            stringColor = rgbaRed;
        bgC.push(stringColor);
        bC.push(stringColor);
    });
    let ctx = document.getElementById('barChart').getContext('2d');
    let myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: lb,
            datasets: [{
                label: ['x', 'z'],
                //labelString: ['x', 'z'],
                data: dataNum,
                backgroundColor: bgC,
                borderColor: bC,
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

function showCharts(data) {
    transactionsChart(data);
}

getData();