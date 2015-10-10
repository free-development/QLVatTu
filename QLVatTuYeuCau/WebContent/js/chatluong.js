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
 
	function preUpdateCl(formId, check) {
		var clMa = $('input:checkbox[name=clMa]:checked').val();
		var clMaList = [];
		$.each($("input[name='clMa']:checked"), function(){            
			clMaList.push($(this).val());
	    });
		if (clMaList.length == 0)
			alert('Bạn phải chọn 1 chất lượng để thay đổi!!');
		else if (clMaList.length > 1)
			alert('Bạn chỉ được chọn 1 chất lượng để thay đổi!!');
		else {
		$.ajax({
			url: getRoot() +  "/preUpdateCl.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "clMa": clMa},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(cl) {
			  	$('input:text[name=clMaUpdate]').val(cl.clMa);
			  	$('input:text[name=clTenUpdate]').val(cl.clTen);
		  		showForm(formId, check);	
		  		$('#clTenFocus').focus();
		  	}
		});
		}
	}
	function confirmDeleteCl(){
		var clMa = $('input:checkbox[name=clMa]:checked').val();
		var clMaList = [];
		$.each($("input[name='clMa']:checked"), function(){            
			clMaList.push($(this).val());
	    });
		var str = clMaList.join(", ");
		if (clMaList.length == 0)
			alert('Bạn phải chọn 1 hoặc nhiều chất lượng để xóa!!');
		else if (confirm('Bạn có chắc xóa chất lượng có mã ' + str))
			deleteCl(str);
	}
		
 	 function deleteCl(str) {
		$.ajax({
			url: getRoot() +  "/deleteCl.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "clList": str},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function() {
						$('table tr').has('input[name="clMa"]:checked').remove();
						alert('Chất lượng có mã ' + str + " đã bị xóa");
		    } 
		});  
	}
 	function addCl() {
		var clMa = $('#add-form input:text[name=clMa]').val();
		var clTen = $('#add-form input:text[name=clTen]').val();
		if(clMa == '') 
			{
				$('#requireClMa').html('Vui lòng nhập mã chất lượng');
			}
		else if (clTen == '')
 			{
 				$('#requireClTen').html('Vui lòng nhập tên chất lượng');
 			}
		else{
			$.ajax({
				url: getRoot() +  "/addCl.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "clMa": clMa, "clTen": clTen},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(result) {
			  		if(result == "success")
	 				{
 			  		 	$('input:text[name=clMa]').val(clMa);
 					  	$('input:text[name=clTen]').val(clTen);
 				  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"clMa\" value=\"' +clMa + '\"</td><td class=\"col\">'+ clMa +'</td><td class=\"col\">' + clTen+'</td></tr>');
 				  		$('#add-form input:text[name=clMa]').val('');
 						$('#add-form input:text[name=clTen]').val('');
 				  		showForm("add-form", false);
 				  		alert(clMa + " đã được thêm ");	
 					}
 			  		else{
 			  			alert(clMa + " đã tồn tại ");
 			  		}
 			  			
			  	}
			});
		}
	}
 	
 	function loadAddCl() {
 		showForm('add-form', false);
 		$('input[name="clMa"]:checked').prop('checked',false);
 	}
 	function loadUpdateCl() {
 		showForm('update-form', false);
 		$('input[name="clMa"]:checked').prop('checked',false);
 	}
 	function resetUpdateCL(){
			$('#update-form input:text[name=clTenUpdate]').val('');
 	}
 	function changeClMa(){
  		$('#requireClMa').html('');
  		$('#add-form input:text[name=clMa]').focus();
 	} 
 	function changeClTen(){
  		$('#requireClTen').html('');
  		$('#add-form input:text[name=clTen]').focus();
 	} 
 	function changeClTenUpdate(){
  		$('#requireClTenUpdate').html('');
  		$('#add-form input:text[name=clTenUpdate]').focus();
 	} 
 	function confirmUpdateCl(){
		var clMaUpdate = $('input:text[name=clMaUpdate]').val();
		var clTenUpdate = $('input:text[name=clTenUpdate]').val();
		 if (clTenUpdate == '')
 			{
 				$('#requireClTenUpdate').html('Vui lòng nhập tên chất lượng');
 			}
		else{
		if (confirm('Bạn có chắc thay doi chất lượng có mã ' + clMaUpdate))
			updateCl(clMaUpdate, clTenUpdate);
		}
	}
 	function updateCl(clMaUpdate, clTenUpdate) {
 		
		$.ajax({
			url: getRoot() +  "/updateCl.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "clMaUpdate": clMaUpdate, "clTenUpdate": clTenUpdate},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(cl) {
		  		$('table tr').has('input[name="clMa"]:checked').remove();
		  		$('#view-table table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"clMa\" value=\"' +clMaUpdate + '\"</td><td class=\"col\">'+ clMaUpdate +'</td><td class=\"col\">' + clTenUpdate+'</td></tr>');
		  		$('input:text[name=clMaUpdate]').val('');
				clTenUpdate = $('input:text[name=clTenUpdate]').val('');
		  		showForm("update-form", false);	
		  		alert("Thay đổi thành công chất lượng có mã "+clMaUpdate+ " !");
		  		$('input[name="clMa"]:checked').prop('checked',false);
		  	}
		});
	}
 	function resetUpdateCL(){
 		$('#update-form input:text[name=clTenUpdate]').val('');
 	}
 	$(document).ready(function() {
 	  	$('.page').click(function(){
 		var pageNumber = $(this).val();
 	    	$.ajax({
 				url: getRoot() +  "/loadPageCl.html",	
 			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "pageNumber": pageNumber},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
 			  	
 			  	success: function(clList) {
 			  		$('#view-table table .rowContent').remove();
 					if(clList.length>0){
 						for(i = 0;i < clList.length; i++ ) {
 							var cl = clList[i] ;
 							var style = '';	
 							if (i % 2 == 0)
 								style = 'style=\"background : #CCFFFF;\"';
 							var str = '';
 							str = '<tr class=\"rowContent\" ' + style + '>'
 								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"clMa\" value=\"' 
 								+ cl.clMa +'\" class=\"checkbox\"></td>'
 								+ '<td class=\"col\">' + cl.clMa + '</td>'
 								+ '<td class=\"col\">' + cl.clTen + '</td>'
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
		 addCl();
	    return false;  
	  }
	});   
});   
$(document).ready(function() {
	$('#update-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 confirmUpdateCl();
	    return false;  
	  }
	});   
});  