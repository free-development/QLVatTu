/**
 * 
 */
function getRoot() {
	var path = location.pathname;
	var index = path.indexOf("/", 1);
	var root = path.substring(0, index);
	return root;
}
