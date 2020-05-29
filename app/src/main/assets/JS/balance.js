function rgbaRandom(){
    var r = Math.floor(Math.random() * 255);
    var g = Math.floor(Math.random() * 255);
    var b = Math.floor(Math.random() * 255);
    return 'rgba(' + r.toString() + ', ' + g.toString() + ', ' + b.toString() + ',';
}

function updateChart(data) {
    var lb = []
    var dataNum = []
    var bgC = []
    var bC = []
    var stringColor;
    data.forEach(function(ta){
        lb.push(ta.Category)
        dataNum.push(ta.Price)
        stringColor = rgbaRandom();
        bgC.push(stringColor + " 0.2)");
        bC.push(stringColor + " 1)");
    });
    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: lb,
            datasets: [{
                label: 'Price',
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
