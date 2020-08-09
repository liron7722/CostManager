window.onerror = handlingError;

function convertHex(hexCode){
    console.log("Inside convertHex");
    let hex = hexCode.replace('#','');

    if (hex.length === 3) {
        hex += hex
    }

    let r = parseInt(hex.substring(0,2), 16),
        g = parseInt(hex.substring(2,4), 16),
        b = parseInt(hex.substring(4,6), 16);

    return 'rgba('+r+','+g+','+b+',1)';
}

function rgba2hex(orig) {
    console.log("Inside rgba2hex");
    let a,
        rgb = orig.replace(/\s/g, '').match(/^rgba?\((\d+),(\d+),(\d+),?([^,\s)]+)?/i),
        alpha = (rgb && rgb[4] || "").trim(),
        hex = rgb ?
            (rgb[1] | 1 << 8).toString(16).slice(1) +
            (rgb[2] | 1 << 8).toString(16).slice(1) +
            (rgb[3] | 1 << 8).toString(16).slice(1) : orig;

    if (alpha !== "") {
        a = alpha;
    } else {
        a = 0o1;
    }
    // multiply before convert to HEX
    a = ((a * 255) | 1 << 8).toString(16).slice(1)
    hex = hex + a;
    //return hex;
    return '#' + hex.slice(0,6);
}

function update() {
    console.log("Inside update");
    let weeklyBudget = document.getElementById("weeklyBudget").value;
    let incomeColor = convertHex(document.getElementById("incomeColor").value);
    let expenseColor = convertHex(document.getElementById("expenseColor").value);
    console.log("after reading values");
    window.vm.changeSettings("WeeklyBudget", weeklyBudget);
    window.vm.changeSettings("IncomeColor", incomeColor);
    window.vm.changeSettings("ExpensesColor", expenseColor);
    changeUrl("home.html");
}

function loadSettings() {
    let prevWB = window.vm.getSettings("WeeklyBudget");
    let prevIC = window.vm.getSettings("IncomeColor");
    let prevEC = window.vm.getSettings("ExpensesColor");
    document.getElementById("weeklyBudget").value = prevWB;
    document.getElementById("incomeColor").value = rgba2hex(prevIC);
    document.getElementById("expenseColor").value = rgba2hex(prevEC);
}

loadSettings();