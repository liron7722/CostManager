function updateSettings() {
    var weeklybudget = document.getElementById("weeklybudget").value;
    var incomeColor = document.getElementById("incomeColor").value;
    var expenseColor = document.getElementById("expenseColor").value;

    if (weeklybudget.length > 0 && prevWB != weeklybudget)
        window.vm.updateSettings("WeeklyBudget", weeklybudget);
    if (incomeColor.length > 0 && prevIC != incomeColor)
        window.vm.updateSettings("IncomeColor", incomeColor);
    if (expenseColor.length > 0 && prevEC != expenseColor)
        window.vm.updateSettings("ExpensesColor", expenseColor);
}

function loadSettings(){
    document.getElementById("weeklybudget").value = window.vm.getSettings("WeeklyBudget");
    document.getElementById("incomeColor").value = window.vm.getSettings("IncomeColor");
    document.getElementById("expenseColor").value = window.vm.getSettings("ExpensesColor");
}

loadSettings();

var prevWB = document.getElementById("weeklybudget").value;
var prevIC = document.getElementById("incomeColor").value;
var prevEC = document.getElementById("expenseColor").value;