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
function preUpdateCd(formId, check) {
	var cdMa = $('input:checkbox[name=cdMa]:checked').val();
	var cdMaList = [];
	$.each($("input[name='cdMa']:checked"), function(){            
		cdMaList.push($(this).val());
    });
	if (cdMaList.length == 0)
		alert('Bạn phải chọn 1 chức danh để thay đổi!!');
	else if (cdMaList.length > 1)
		alert('Bạn chỉ được chọn 1 chức danh để thay đổi!!');
	else {
	$.ajax({
		url: "/QLVatTuYeuCau/preUpdateCd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "cdMa": cdMa},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(cd) {
		  	$('input:text[name=cdMaUpdate]').val(cd.cdMa);
		  	$('input:text[name=cdTenUpdate]').val(cd.cdTen);
	  		showForm(formId, check);	
	  		
	  	}
	});
	}
}
function loadAddCd() {
		showForm('add-form', false);
		$('input[name="cdMa"]:checked').prop('checked',false);
	}
	function loadUpdateCd() {
		showForm('update-form', false);
		$('input[name="cdMa"]:checked').prop('checked',false);
	}
$(document).ready(function() {
	  	$('.page').click(function(){
		var pageNumber = $(this).val();
	    	$.ajax({
				url: "/QLVatTuYeuCau/loadPageCd.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "pageNumber": pageNumber},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(cdList) {
			  		$('#view-table table .rowContent').remove();
					if(cdList.length>0){
						for(i = 0;i < cdList.length; i++ ) {
							var cd = cdList[i] ;
							var style = '';	
							if (i % 2 == 0)
								style = 'style=\"background : #CCFFFF;\"';
							var str = '';
							str = '<tr class=\"rowContent\" ' + style + '>'
								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"cdMa\" value=\"' 
								+ cd.cdMa +'\" class=\"checkbox\"></td>'
								+ '<td class=\"col\">' + cd.cdMa + '</td>'
								+ '<td class=\"col\">' + cd.cdTen + '</td>'
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
	 addCd();
    return false;  
  }
});   
});   
$(document).ready(function() {
$('#update-form').keypress(function(e) {
 var key = e.which;
 if(key == 13)  // the enter key code
  {
    updateCd();
    return false;  
  }
});   
});  
function confirmDeleteCd(){
	var cdMa = $('input:checkbox[name=cdMa]:checked').val();
	var cdMaList = [];
	$.each($("input[name='cdMa']:checked"), function(){            
		cdMaList.push($(this).val());
    });
	var str = cdMaList.join(", ");
	if (cdMaList.length == 0)
		alert('Bạn phải chọn 1 hoặc nhiều chức danh để xóa!!');
	else if (confirm('Bạn có chắc xóa chức danh có mã ' + str))
		deleteCd(str);
}
	
	 function deleteCd(str) {
	$.ajax({
		url: "/QLVatTuYeuCau/deleteCd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "cdList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function() {
					$('table tr').has('input[name="cdMa"]:checked').remove();
					alert('Chức danh có mã ' + str + " đã bị xóa");
	    } 
	});  
} 
	
	function addCd() {
	var cdMa = $('#add-form input:text[name= cdMa]').val();
	var cdTen = $('#add-form input:text[name=cdTen]').val();
	if(cdMa == '') 
	{
		$('#requireCdMa').html('Vui lòng nhập mã chức danh');
	}
	else if (cdTen == '')
		{
			$('#requireCdTen').html('Vui lòng nhập tên chức danh');
		}
	else{
	$.ajax({
		url: "/QLVatTuYeuCau/addCd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "cdMa": cdMa, "cdTen": cdTen},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(result) {
	    if(result == "success")
			{
	    	$('input:text[name=cdMa]').val(cdMa);
			  	$('input:text[name=cdTen]').val(cdTen);
		  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"cdMa\" value=\"' +cdMa + '\"</td><td class=\"col\">'+ cdMa +'</td><td class=\"col\">' + cdTen+'</td></tr>');
		  		$('#add-form input:text[name=cdMa]').val('');
				$('#add-form input:text[name=cdTen]').val('');
		  		showForm("add-form", false);
		  		alert(cdMa + " đã được thêm ");	
			}
	  		else{
	  			alert(cdMa + " đã tồn tại ");
	  		}
	  			
  	}
	});
	}
}
	function changeCdMa(){
  		$('#requireCdMa').html('');
  		$('#add-form input:text[name=cdMa]').focus();
 	} 
 	function changeCdTen(){
  		$('#requireCdTen').html('');
  		$('#add-form input:text[name=cdTen]').focus();
 	} 
 	function changeCdTenUpdate(){
  		$('#requireCdTenUpdate').html('');
  		$('#update-form input:text[name=cdTenUpdate]').focus();
 	} 
	
	function confirmUpdateCd(){
	var cdMaUpdate = $('input:text[name=cdMaUpdate]').val();
	var cdTenUpdate = $('input:text[name=cdTenUpdate]').val();
	if (cdTenUpdate == '')
		{
			$('#requireCdTenUpdate').html('Vui lòng nhập tên chức danh');
		}
	else{
	if (confirm('Bạn có chắc thay đổi chức danh có mã ' + cdMaUpdate))
		updateCd(cdMaUpdate, cdTenUpdate);
	}
}
	function updateCd(cdMaUpdate, cdTenUpdate) {

	$.ajax({
		url: "/QLVatTuYeuCau/updateCd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "cdMaUpdate": cdMaUpdate, "cdTenUpdate": cdTenUpdate},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(cd) {
	  		$('table tr').has('input[name="cdMa"]:checked').remove();
	  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"cdMa\" value=\"' +cdMaUpdate + '\"</td><td class=\"col\">'+ cdMaUpdate +'</td><td class=\"col\">' + cdTenUpdate+'</td></tr>');
	  		$('input:text[name=cdMaUpdate]').val('');
			cdTenUpdate = $('input:text[name=cdTenUpdate]').val('');
			$('input[name="cdMa"]:checked').prop('checked',false);
	  		showForm("update-form", false);	
	  	}
	});
}
	function resetUpdateCD(){
		$('#update-form input:text[name=cdTenUpdate]').val('');
	}