function showForm(formId, check){
		if (check)
			document.getElementById(formId).style.display="block";
		else document.getElementById(formId).style.display="none";
		var f = document.getElementById('main-form'), s, opacity;
		s = f.style;
		opacity = check? '10' : '100';
		s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
		s.filter = 'alpha(opacity='+opacity+')';
		for(var i=0; i<f.length; i++) f[i].disabled = check;
	}

function preUpdatevt(formId, check){
	vtMa = $('input:checkbox[name=vtMa]:checked').val();
	var vtMaList = [];
	$.each($("input[name='vtMa']:checked"), function(){            
		vtMaList.push($(this).val());
    });
	if (vtMaList.length == 0)
		alert('Bạn phải chọn 1 vai trò để thay đổi!!');
	else if (vtMaList.length > 1)
		alert('Bạn chỉ được chọn 1 vai trò để thay đổi!!');
	else {
		
		$.ajax({
			url: getRoot() +  "/preEditvt.html",
			type: "GET",
			dataType: "JSON",
			data: {"vtMa": vtMa},
			contentType: "application/json",
			mimeType: "application/json",
			
			success: function(vt){		
				
				$('input:text[name=vtMaUpdate]').val(vt.vtMa);
				$('input:text[name=vtTenUpdate]').val(vt.vtTen);
				
			  	showForm(formId, check);
			  	$('#vtUpFocus').focus();
			}
			
		});
		
		
	}
}
function confirmDelete(){
	vtMa = $('input:checkbox[name=vtMa]:checked').val();
	var vtMaList = [];
	$.each($("input[name='vtMa']:checked"), function(){            
		vtMaList.push($(this).val());
    });
	var str = vtMaList.join(", ");
	if (vtMaList.length == 0)
		alert('Bạn phải chọn 1 hoặc nhiều vai trò để xóa!!');
	else if (confirm('Bạn có chắc xóa vai trò ' + str))
		deletevt(str);
}

 function deletevt(str) {
	 
	$.ajax({
		url: getRoot() +  "/deletevt.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "vtList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(vtList) {
	  		$('table tr').has('input[name="vtMa"]:checked').remove();
	  		alert('Vai trò ' + str + " đã được xóa");
	    } 
	});  
} 
function addvt() {
	vtTen = $('#add-form input:text[name=vtTen]').val();
	vtMa = $('#add-form input:text[name=vtMa]').val();
	if (vtMa == '') {
		 $('#requirevtMa').html('Vui lòng nhập mã vai trò');
		 return;
	 } else if (vtTen == ''){
			$('#requirevtTen').html('Vui lòng nhập tên vai trò');
			return;
	 } else {
		$.ajax({
			url: getRoot() +  "/addvt.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: {"vtMa": vtMa, "vtTen": vtTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(result) {
		  		if(result == "success")
 				{
					$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' + vtMa +'\"</td><td class=\"col\">' + vtMa + '</td><td class=\"col\">' + vtTen+'</td></tr>');
					$('#add-form input:text[name=vtTen]').val('');
					$('#add-form input:text[name=vtMa]').val('');
		  			showForm("add-form", false);	
			  		alert("Vai trò "+ vtTen + " đã được thêm ");	
				}
		  		else{
		  			alert("Vai trò "+ vtTen + " đã tồn tại ");
		  		}
		  	}
		});
	}
}
function confirmUpdatevt(){
	var vtTenUpdate = $('input:text[name=vtTenUpdate]').val();
	var vtMaUpdate = $('input:text[name=vtMaUpdate]').val();
	if (confirm('Bạn có chắc thay đổi vai trò: ' + vtMaUpdate))
		updatevt(vtMaUpdate, vtTenUpdate);
}
function updatevt(vtMaUpdate, vtTenUpdate) {
	if (vtTenUpdate == '')
		{
			$('#requirevtTenUp').html('Vui lòng nhập tên vai trò');
		}
	
	else {

		$.ajax({
			url: getRoot() +  "/updatevt.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: {"vtMaUpdate": vtMaUpdate, "vtTenUpdate": vtTenUpdate},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(vt) {
		  		$('table tr').has('input[name="vtMa"]:checked').remove();
		  		$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' + vtMaUpdate + '\"</td><td class=\"col\">' + vtMaUpdate + '</td><td class=\"col\">' + vtTenUpdate +'</td></tr>');						
		  		showForm("update-form", false);	
		  		alert("Thay đổi thành công vai trò "+ vtTenUpdate);
		  		vtTenUpdate = $('input:text[name=vtTenUpdate]').val('');
		  		$('input[name="vtMa"]:checked').prop('checked',false);
		  	}
		});
	}
}
function loadAddVt() {
	showForm('add-form', false);
	$('input[name="vtMa"]:checked').prop('checked',false);
}
function loadUpdateVt() {
	showForm('update-form', false);
	$('input[name="vtMa"]:checked').prop('checked',false);
}
function resetUpdatevt(){
	$('#update-form input:text[name=vtTenUpdate]').val('');
}
function changevtTen(){
	$('#requirevtMa').html('');
	$('#requirevtTen').html('');
//	$('#add-form input:text[name=vtTen]').focus();
}	



function changevtTenUp(){
	$('#requirevtTenUp').html('');
	$('#update-form input:text[name=vtTenUpdate]').focus();
}

$(document).ready(function() {
  	$('.page').click(function(){
	var pageNumber = $(this).val();
    	$.ajax({
			url: getRoot() +  "/loadPagevt.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "pageNumber": pageNumber},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(vtList) {
		  		$('#view-table table .rowContent').remove();
				if(vtList.length>0){
					for(i = 0;i < vtList.length; i++ ) {
						var vt = vtList[i] ;
						var style = '';	
						if (i % 2 == 0)
							style = 'style=\"background : #CCFFFF;\"';
						var str = '';
						str = '<tr class=\"rowContent\" ' + style + '>'
							+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' 
							+ vt.vtTen +'\" class=\"checkbox\"></td>'
							+ '<td class=\"col\">' + vt.vtMa + '</td>'
							+ '<td class=\"col\">' + vt.vtTen + '</td>'
							+ '</tr>';
						$('#view-table table tr:first').after(str);
					}
				}
		  	}
		});
    });	
})   
$(document).ready(function() {
 	$('#add-form').keypress(function(e) {
 	 var key = e.which;
 	 if(key == 13)  // the enter key code
 	  {
 		 addvt();
 	    return false;  
 	  }
 	});   
});   
$(document).ready(function() {
 	$('#update-form').keypress(function(e) {
 	 var key = e.which;
 	 if(key == 13)  // the enter key code
 	  {
 		confirmUpdatevt();
 	    return false;  
 	  }
 	});   
});