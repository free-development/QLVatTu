/**
 * 
 */
function removeSession(name) {
	$.ajax({
		url: getRoot() +  "/removeSession.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "name": name},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(result){
	    	if (result == "authentication error") {
				location.assign("login.jsp");
			} else {
				window.close();
			}
	    }	  	
	});
}
$(document).ready(function() {
	$('#exit_button').click(function() {
		removeSession("objectList");
		
	});   
});
$(document).ready(function() {
	$('#exitError_button').click(function() {
		removeSession("errorList");
		
	});   
});
$(document).ready(function(){
	$('#import-form').submit(function(){
		showForm('import-formct',false);
	});
});
