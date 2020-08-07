function log(message) {
    window.vm.log(message);
}

function handlingError() {
    let message = "[location:" + arguments[1] + "]\n[error:" + arguments[0] + "]";
    window.vm.handlingError(message);
    return true;
}

window.onerror = handlingError;

function changeUrl(url) {
    window.location.href = url;
    log("url changed to: " + url);
}

$("button#logout").on({
    click: function() {
        log("user clicked log out");
        window.vm.logOut();
        changeUrl("welcome.html");
    }
});

$("button.panelButton").on({
    click: function() {
        changeUrl(this.value + ".html");
    }
});

$("button.navbarButton").on({
    click: function() {
        changeUrl(this.value + ".html");
    }
});

function LoginStatus() {
    if (window.vm.loginStatus()) {
        log('logged in');
    } else {
        console.log('Not logged in');
        changeUrl('welcome.html');
    }
}