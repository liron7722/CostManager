function test(){
	console.log('test worked');
}

$("button.getBalance").on({
	click: function() {test();}
});


$("button#addTransaction").on({
	click: function() {test();}
});
