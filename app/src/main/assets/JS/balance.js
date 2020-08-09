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

function getColors() {
    let rgbaGreen = window.vm.getSettings("IncomeColor");
    let rgbaRed = window.vm.getSettings("ExpensesColor");

    return [rgbaGreen, rgbaRed];
}

function transactionsChart(data, colorA, colorB) {
    let lb = [];
    let dataNum = [];
    let bgC = [];
    let bC = [];
    let stringColor;
    data.forEach(function(ta) {
        lb.push(ta.TName);
        dataNum.push(ta.Price);
        if (ta.isIncome === "1")
            stringColor = colorA;
        else
            stringColor = colorB;
        bgC.push(stringColor);
        bC.push(stringColor);
    });
    let ctx = document.getElementById('barChart').getContext('2d');
    let myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: lb,
            datasets: [{
                label: ["Income", "Expense"],
                data: dataNum,
                labelColors: [colorA, colorB],
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

function totalChart(data, colorA, colorB) {
    let totalIncome = 0;
    let totalExpense = 0;
    data.forEach(function(ta) {
        if (ta.isIncome === "1")
            totalIncome += ta.Price;
        else
            totalExpense += ta.Price;
    });

    let ctx = document.getElementById('pieChart').getContext('2d');
    let myChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ["Income", "Expense"],
            datasets: [{
                label: ["Income", "Expense"],
                data: [totalIncome, totalExpense],
                backgroundColor: [colorA, colorB],
                borderColor: [colorA, colorB],
                borderWidth: 1
            }]
        },

    });
}

function checkTime(dateCheck, code) {
    let currentDate = new Date();
    let dateFrom;
    let dateTo = currentDate.getFullYear() + "-" + (currentDate.getMonth()+1)  + "-" + currentDate.getDate();

    if (code === "7")
        if (currentDate.getDate() > 7)
            dateFrom = currentDate.getFullYear() + "-" + (currentDate.getMonth() + 1)  + "-" + (currentDate.getDate() - 7);
        else {
            if (currentDate.getMonth() > 0)
                dateFrom = currentDate.getFullYear() + "-" + currentDate.getMonth()  + "-" + (currentDate.getDate() - 7);
            else
                dateFrom = (currentDate.getFullYear() - 1) + "-" + 12  + "-" + (30 + currentDate.getDate() - 7);
        }

    let d1 = dateFrom.split("-");
    let d2 = dateTo.split("-");
    let c = dateCheck.split("-");

    let from = new Date(d1[0], parseInt(d1[1])-1, d1[2]);  // -1 because months are from 0 to 11
    let to   = new Date(d2[0], parseInt(d2[1])-1, d2[2]);
    let check = new Date(c[0], parseInt(c[1])-1, c[2]);

    return check > from && check < to;

}

function filterData(data, code) {
    let filterData = [];
    if (code === 10) {
        return data.slice(data.length - 10);
    }
    else if (code !== "0"){
        data.forEach(function (ta) {
            if (checkTime(ta.Date, code))
                filterData.push(ta);
        });
        return filterData;
    }
    return data;
}

function showCharts(data) {
    console.log(document.getElementById("filter").value);
    let colors = getColors();
    data = filterData(data, document.getElementById("filter").value);
    transactionsChart(data, colors[0], colors[1]);
    totalChart(data, colors[0], colors[1]);
}

getData();

$('#filter').on('change', function() {
    showCharts(window.data);
});
