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
	function preUpdateMd(formId, check) {
		var mdMa = $('input:checkbox[name=mdMa]:checked').val();
		
		var mdMaList = [];
		$.each($("input[name='mdMa']:checked"), function(){            
			mdMaList.push($(this).val());
	    });
		if (mdMaList.length == 0)
			alert('Bạn phải chọn 1 mục đích để thay đổi!!');
		else if (mdMaList.length > 1)
			alert('Bạn chỉ được chọn 1 mục đích để thay đổi!!');
		else {
		$.ajax({
			url: getRoot() +  "/preUpdateMd.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "mdMa": mdMa},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(md) {
			  	$('input:text[name=mdMaUpdate]').val(md.mdMa);
			  	$('input:text[name=mdTenUpdate]').val(md.mdTen);
		  		showForm(formId, check);	
		  		
		  	}
		});
		}
	}
	function loadAddMd() {
 		showForm('add-form', false);
 		$('input[name="mdMa"]:checked').prop('checked',false);
 	}
 	function loadUpdateMd() {
 		showForm('update-form', false);
 		$('input[name="mdMa"]:checked').prop('checked',false);
 	}
	function confirmDeleteMd(){
		var mdMa = $('input:checkbox[name=mdMa]:checked').val();
		var mdMaList = [];
		$.each($("input[name='mdMa']:checked"), function(){            
			mdMaList.push($(this).val());
	    });
		var str = mdMaList.join(", ");
		if (mdMaList.length == 0)
			alert('Bạn phải chọn 1 hoặc nhiều mục đích để xóa!!');
		else if (confirm('Bạn có chắc xóa mục đích có mã ' + str))
			deleteMd(str);
	}
		
 	 function deleteMd(str) {
 		$.ajax({
			url: getRoot() +  "/deleteMd.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "mdList": str},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function() {
						$('table tr').has('input[name="mdMa"]:checked').remove();
						alert('Mục đích có mã ' + str + " đã bị xóa");
		    } 
		});  
	} 
 	
 	function addMd() {
		var mdMa = $('#add-form input:text[name= mdMa]').val();
		var mdTen = $('#add-form input:text[name=mdTen]').val();
		if(mdMa == '') 
		{
			$('#requireMdMa').html('Vui lòng nhập mã mục đích');
		}
		else if (mdTen == '')
			{
				$('#requireMdTen').html('Vui lòng nhập tên mục đích');
			}
		else{
		$.ajax({
			url: getRoot() +  "/addMd.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "mdMa": mdMa, "mdTen": mdTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(result) {
				if(result == "success")
				{
		  		 	$('input:text[name=mdMa]').val(mdMa);
				  	$('input:text[name=mdTen]').val(mdTen);
			  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"mdMa\" value=\"' +mdMa + '\"</td><td class=\"col\">'+ mdMa +'</td><td class=\"col\">' + mdTen+'</td></tr>');
			  		$('#add-form input:text[name=mdMa]').val('');
					$('#add-form input:text[name=mdTen]').val('');
			  		showForm("add-form", false);
			  		alert(mdMa + " đã được thêm ");	
				}
		  		else{
		  			alert(mdMa + " đã tồn tại ");
		  		}
		  			
			}
		});
		}
	}
 	function changeMdMa(){
  		$('#requireMdMa').html('');
  		$('#add-form input:text[name=mdMa]').focus();
 	} 
 	function changeMdTen(){
  		$('#requireMdTen').html('');
  		$('#add-form input:text[name=mdTen]').focus();
 	} 
 	function changeMdTenUpdate(){
  		$('#requireMdTenUpdate').html('');
  		$('#update-form input:text[name=mdTenUpdate]').focus();
 	} 
 	function confirmUpdateMd(){
		var mdMaUpdate = $('input:text[name=mdMaUpdate]').val();
		var mdTenUpdate = $('input:text[name=mdTenUpdate]').val();
		if (mdTenUpdate == '')
		{
			$('#requireMdTenUpdate').html('Vui lòng nhập tên mục đích');
		}
		else{
		if (confirm('Bạn có chắc thay đổi mục đích có mã ' + mdMaUpdate))
			updateMd(mdMaUpdate, mdTenUpdate);
		}
	}
 	function updateMd(mdMaUpdate, mdTenUpdate) {

		$.ajax({
			url: getRoot() +  "/updateMd.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "mdMaUpdate": mdMaUpdate, "mdTenUpdate": mdTenUpdate},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(md) {
		  		$('table tr').has('input[name="mdMa"]:checked').remove();
		  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"mdMa\" value=\"' +mdMaUpdate + '\"</td><td class=\"col\">'+ mdMaUpdate +'</td><td class=\"col\">' + mdTenUpdate+'</td></tr>');
		  		$('input:text[name=mdMaUpdate]').val('');
				mdTenUpdate = $('input:text[name=mdTenUpdate]').val('');
				$('input[name="mdMa"]:checked').prop('checked',false);
		  		showForm("update-form", false);	
		  	}
		});
	}
 	function resetUpdateMd(){
 		$('#update-form input:text[name=mdTenUpdate]').val('');
 	}
 	$(document).ready(function() {
 	  	$('.page').click(function(){
 		var pageNumber = $(this).val();
 	    	$.ajax({
 				url: getRoot() +  "/loadPageMd.html",	
 			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "pageNumber": pageNumber},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
 			  	
 			  	success: function(mdList) {
 			  		$('#view-table table .rowContent').remove();
 					if(mdList.length>0){
 						for(i = 0;i < mdList.length; i++ ) {
 							var md = mdList[i] ;
 							var style = '';	
 							if (i % 2 == 0)
 								style = 'style=\"background : #CCFFFF;\"';
 							var str = '';
 							str = '<tr class=\"rowContent\" ' + style + '>'
 								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"mdMa\" value=\"' 
 								+ md.mdMa +'\" class=\"checkbox\"></td>'
 								+ '<td class=\"col\">' + md.mdMa + '</td>'
 								+ '<td class=\"col\">' + md.mdTen + '</td>'
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
		 addMd();
	    return false;  
	  }
	});   
});   
$(document).ready(function() {
	$('#update-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 confirmUpdateMd();
	    return false;  
	  }
	});   
});  