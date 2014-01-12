var keylist = "abcdefghijklmnopqrstuvwxyz123456789";
var temp = '';

function generatepass(plength) {
	var temp = '';
	for (i = 0; i < plength; i++) {
		temp += keylist.charAt(Math.floor(Math.random() * keylist.length));
	}
	return temp;
}