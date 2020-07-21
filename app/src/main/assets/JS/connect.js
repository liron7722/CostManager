function LoginStatus(){
	if (window.vm.loginStatus()){
		console.log('logged in');
		// TODO show profile, logout
		// TODO hide home, Transactions, register, login
	}
	else {
		console.log('Not logged in');
		location.href='login.html';
		// TODO show register, login
		// TODO hide profile, logout
	}
}

LoginStatus();