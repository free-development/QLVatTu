/**
 * 
 */
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
function showForm2(formId1, formId2, check){
	if (check)
		document.getElementById(formId2).style.display="block";
	else document.getElementById(formId2).style.display="none";
	var f = document.getElementById(formId1), s, opacity;
	s = f.style;
	opacity = check? '10' : '100';
	s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
	s.filter = 'alpha(opacity='+opacity+')';
	for(var i=0; i<f.length; i++) f[i].disabled = check;
}	
function preUpdateNsx(formId, check) {
	var nsxMa = $('input:checkbox[name=nsxMa]:checked').val();
	var nsxMaList = [];
	$.each($("input[name='nsxMa']:checked"), function(){            
		nsxMaList.push($(this).val());
    });
	if (nsxMaList.length == 0)
		alert('Bạn phải chọn 1 nơi sản xuất để thay đổi!!');
	else if (nsxMaList.length > 1)
		alert('Bạn chỉ được chọn 1 nơi sản xuất để thay đổi!!');
	else {
		$.ajax({
			url: getRoot() +  "/preEditNsx.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "nsxMa": nsxMa},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(nsx) {
		  		
			  	$('input:text[name=nsxMaUpdate]').val(nsx.nsxMa);
			  	$('input:text[name=nsxTenUpdate]').val(nsx.nsxTen);
		  		showForm(formId, check);	
		  		
		  	}
		});
	}
}
function loadAddNsx() {
		showForm('add-form', false);
		$('input[name="nsxMa"]:checked').prop('checked',false);
	}
	function loadUpdateNsx() {
		showForm('update-form', false);
		$('input[name="nsxMa"]:checked').prop('checked',false);
	}
function confirmDelete(){
	var nsxMa = $('input:checkbox[name=nsxMa]:checked').val();
	var nsxMaList = [];
	
	$.each($("input[name='nsxMa']:checked"), function(){            
		nsxMaList.push($(this).val());
    });
	var str = nsxMaList.join(", ");
	if (nsxMaList.length == 0)
		alert('Bạn phải chọn 1 hoặc nhiều nơi sản xuất để xóa!!');
	else if (confirm('Bạn có chắc xóa nơi sản xuất có mã ' + str))
		deleteNsx(str);
}

 function deleteNsx(str) {
	$.ajax({
		url: getRoot() +  "/deleteNsx.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "nsxList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function() {
					$('table tr').has('input[name="nsxMa"]:checked').remove();
					alert('Nơi sản xuất có mã ' + str + " đã bị xóa");
	    } 
	});  
} 

function addNsx() {
	var nsxMa = $('#add-form input:text[name=nsxMa]').val();
	var nsxTen = $('#add-form input:text[name=nsxTen]').val();
	if(nsxMa == '') 
		$('#requirensxTen').html('Vui lòng nhập tên nơi sản xuất');
	else {
		$.ajax({
			url: getRoot() +  "/addNsx.html",	
			type: "GET",
			dateType: "JSON",
			data: { "nsxMa": nsxMa, "nsxTen": nsxTen},
			contentType: 'application/json',
			mimeType: 'application/json',
		  	
		  	success: function(result) {
		//			  		alert(result);
		  		if (result == "success")
			  	{
			  		$('#view-table table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"nsxMa\" value=\"' +nsxMa + '\"</td><td class=\"col\">'+ nsxMa +'</td><td class=\"col\">' + nsxTen+'</td></tr>');
			  		$('#add-form input:text[name=nsxMa]').val('');
					$('#add-form input:text[name=nsxTen]').val('');
			  		showForm("add-form", false);	
			  		alert("Nơi sản xuất "+ nsxMa + " đã được thêm ");	
				} else {
		  			alert("Nơi sản xuất "+nsxMa + " đã tồn tại ");
		  		}
		  	}	
		});
	}
}

function confirmUpdateNsx(){
	var nsxMaUpdate = $('input:text[name=nsxMaUpdate]').val();
	var nsxTenUpdate = $('input:text[name=nsxTenUpdate]').val();
	
	if (nsxTenUpdate == '')
	{
		$('#requirensxTenUp').html('Vui lòng nhập tên nơi sản xuất');
	}

	else {
			if (confirm('Bạn có chắc thay doi noi san xuat co ma ' + nsxMaUpdate))
				updateNsx(nsxMaUpdate, nsxTenUpdate);
		}
}
function updateNsx(nsxMaUpdate, nsxTenUpdate) {
if (nsxTenUpdate == '')
		{
			$('#requirensxTenUp').html('Vui lòng nhập tên nơi sản xuất');
	 			}
 
 			else {
 				$.ajax({
				url: getRoot() +  "/updateNsx.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "nsxMaUpdate": nsxMaUpdate, "nsxTenUpdate": nsxTenUpdate},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(nsx) {
			  		$('table tr').has('input[name="nsxMa"]:checked').remove();
			  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"nsxMa\" value=\"' +nsxMaUpdate + '\"</td><td class=\"col\">'+ nsxMaUpdate +'</td><td class=\"col\">' + nsxTenUpdate+'</td></tr>');
			  		$('input:text[name=nsxMaUpdate]').val('');
					nsxTenUpdate = $('input:text[name=nsxTenUpdate]').val('');
			  		showForm("update-form", false);	
			  		alert("Thay đổi thành công nơi sản xuất có mã "+ nsxMaUpdate);
			  	}
			});
	}
}
function resetUpdateNsx(){
	$('#update-form input:text[name=nsxTenUpdate]').val('');
}
function changensxMa(){
	$('#requirensxMa').html('');
	$('#add-form input:text[name=nsxMa]').focus();
} 	

function changensxTen(){
	$('#requirensxTen').html('');
	$('#add-form input:text[name=nsxTen]').focus();
}	



function changensxTenUp(){
	$('#requirensxTenUp').html('');
	$('#update-form input:text[name=nsxTenUpdate]').focus();
}
$(document).ready(function() {
  	$('.page').click(function(){
	var pageNumber = $(this).val();
    	$.ajax({
			url: getRoot() +  "/loadPageNsx.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "pageNumber": pageNumber},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(nsxList) {
		  		$('#view-table table .rowContent').remove();
				if(nsxList.length>0){
					for(i = 0;i < nsxList.length; i++ ) {
						var nsx = nsxList[i] ;
						var style = '';	
						if (i % 2 == 0)
							style = 'style=\"background : #CCFFFF;\"';
						var str = '';
						str = '<tr class=\"rowContent\" ' + style + '>'
							+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"nsxMa\" value=\"' 
							+ nsx.nsxMa +'\" class=\"checkbox\"></td>'
							+ '<td class=\"col\">' + nsx.nsxMa + '</td>'
							+ '<td class=\"col\">' + nsx.nsxTen + '</td>'
							+ '</tr>';
						$('#view-table table tr:first').after(str);
					}
				}
		  	}
		});
    });	
})   
	function reset(){
			$('#update-form input:text[name=nsxTenUpdate]').vval('');
 	}
$(document).ready(function() {
	$('#add-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 addNsx();
//		 alert('ok');
	    return false;  
	  }
	});   
});   
$(document).ready(function() {
	$('#update-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 confirmUpdateNsx();
	    return false;  
	  }
	});   
});  