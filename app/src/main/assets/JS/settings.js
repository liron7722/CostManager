window.onerror = handlingError;

function updateSettings() {
    let weeklyBudget = document.getElementById("weeklybudget").value;
    let incomeColor = document.getElementById("incomeColor").value;
    let expenseColor = document.getElementById("expenseColor").value;

    if (weeklyBudget.length > 0 && prevWB !== weeklyBudget)
        window.vm.updateSettings("WeeklyBudget", weeklyBudget);
    if (incomeColor.length > 0 && prevIC !== incomeColor)
        window.vm.updateSettings("IncomeColor", incomeColor);
    if (expenseColor.length > 0 && prevEC !== expenseColor)
        window.vm.updateSettings("ExpensesColor", expenseColor);
}

function loadSettings() {
    document.getElementById("weeklybudget").value = window.vm.getSettings("WeeklyBudget");
    document.getElementById("incomeColor").value = window.vm.getSettings("IncomeColor");
    document.getElementById("expenseColor").value = window.vm.getSettings("ExpensesColor");
}

loadSettings();

var prevWB = document.getElementById("weeklybudget").value;
var prevIC = document.getElementById("incomeColor").value;
var prevEC = document.getElementById("expenseColor").value;