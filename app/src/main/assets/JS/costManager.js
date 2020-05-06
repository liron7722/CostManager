function test(){
	document.write('test worked');
	console.log('test worked');
}

$("button.getBalance").on({
	click: function() {test();}
});


$("button#addTransaction").on({
	click: function() {test();}
});
