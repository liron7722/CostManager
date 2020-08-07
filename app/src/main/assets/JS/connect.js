function changeUrl(url) {
	window.location.href = url;
}

$("button#logout").on({
	click: function() {
		window.vm.logOut();
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

function LoginStatus(){
	if (window.vm.loginStatus()){
		console.log('logged in');
		// TODO show profile, logout
		// TODO hide home, Transactions, register, login
	}
	else {
		console.log('Not logged in');
		location.href='welcome.html';
		// TODO show register, login
		// TODO hide profile, logout
	}
}

//$("#mypanel").trigger("updatelayout" );