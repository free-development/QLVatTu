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
		
		function addBp() {
 			dvMa = $('#add-form input:text[name=dvMa]').val();
 			dvTen = $('#add-form input:text[name=dvTen]').val();
 			sdt = $('#add-form input:text[name=sdt]').val();
 			diaChi = $('#add-form input:text[name=diaChi]').val();
 			email = $('#add-form input:text[name=email]').val();
 			if(dvMa == '') {
 				$('#requiredvMa').html('Vui lòng nhập mã BPSD');
 			}
 			else if (dvTen == '')
	 			{
	 				$('#requiredvTen').html('Vui lòng nhập tên BPSD');
	 			}
 			else if (sdt == '')
 			{
 				$('#requireSdt').html('Vui lòng nhập số điện thoại');
 			}
 			else if (diaChi == '')
 			{
 				$('#requireDiachi').html('Vui lòng nhập địa chỉ');
 			}
 			else if (email == '')
 			{
 				$('#requireEmail').html('Vui lòng nhập email');
 			}
 			else {
		 			$.ajax({
		 				url: getRoot() +  "/addBp.html",	
					  	type: "GET",
		 			  	dateType: "JSON",
		 			  	data: { "dvMa": dvMa, "dvTen": dvTen, "sdt": sdt, "diaChi": diaChi, "email": email},
		 			  	contentType: 'application/json',
		 			    mimeType: 'application/json',
					  	
		 			  	success: function(result) {
		 			  		if (result == "authentication error") {
		 			  			location.assign("login.jsp");
		 			  		} else if(result == "success") {
		 			  			$('#view-table-bo-phan table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"dvMa\" value=\"'+ dvMa + '\"</td><td class=\"col\">' + dvMa +'</td><td class=\"col\">' + dvTen+'</td><td class=\"col\">' + sdt+'</td><td class=\"col\">'+ diaChi+'</td><td class=\"col\">'+ email+'</td></tr>');
								$('#add-form input:text[name=dvMa]').val('');
					 			$('#add-form input:text[name=dvTen]').val('');
					 			$('#add-form input:text[name=sdt]').val('');
					 			$('#add-form input:text[name=diaChi]').val('');
					 			$('#add-form input:text[name=email]').val('');
					 			showForm("add-form", false);
					 			alert(dvMa + " đã được thêm! ");	
							} else {
					  			alert(dvMa + " đã tồn tại ");
					  		}
		 			  	}
		 			 });
 			}
 		}
		function loadAddBp() {
	 		showForm('add-form', false);
	 		$('input[name="dvMa"]:checked').prop('checked',false);
	 	}
	 	function loadUpdateBp() {
	 		showForm('update-form', false);
	 		$('input[name="dvMa"]:checked').prop('checked',false);
	 	}
		function preUpdateBp(formId, check){
			dvMa = $('input:checkbox[name=dvMa]:checked').val();
			var dvMaList = [];
			$.each($("input[name='dvMa']:checked"), function(){            
				dvMaList.push($(this).val());
		    });
			if (dvMaList.length == 0)
				alert('Bạn phải chọn 1 bộ phận để thay đổi!!');
			else if (dvMaList.length > 1)
				alert('Bạn chỉ được chọn 1 đơn vị để thay đổi!!');
			else {
				$.ajax({
					url: getRoot() +  "/preEditBp.html",
					type: "GET",
					dataType: "JSON",
					data: {"dvMa": dvMa},
					contentType: "application/json",
					mimeType: "application/json",
					
					success: function(dv){
						if (dv == "authentication error") {
	 			  			location.assign("login.jsp");
	 			  		} else {	
							$('input:text[name=dvMaUpdate]').val(dv.dvMa);
						  	$('input:text[name=dvTenUpdate]').val(dv.dvTen);
						  	$('input:text[name=sdtUpdate]').val(dv.sdt);
						  	$('input:text[name=diaChiUpdate]').val(dv.diaChi);
						  	$('input:text[name=emailUpdate]').val(dv.email);
						  	showForm(formId, check);
						  	 $('#dvTenFocus').focus();
	 			  		}
					}
				});
			}
		}
 	 	function confirmUpdateBp(){
			var dvMaUpdate = $('input:text[name=dvMaUpdate]').val();
			var dvTenUpdate = $('input:text[name=dvTenUpdate]').val();
			var sdtUpdate = $('input:text[name=sdtUpdate]').val();
 			var diaChiUpdate = $('input:text[name=diaChiUpdate]').val();
 			var emailUpdate = $('input:text[name=emailUpdate]').val();
 			if (dvTenUpdate == '')
 			{
 				$('#requiredvTenUp').html('Vui lòng nhập tên BPSD');
 			}
		else if (sdtUpdate == '')
		{
			$('#requireSdtUp').html('Vui lòng nhập số điện thoại');
		}
		else if (diaChiUpdate == '')
		{
			$('#requireDiachiUp').html('Vui lòng nhập địa chỉ');
		}
		else if (emailUpdate == '')
		{
			$('#requireEmailUp').html('Vui lòng nhập email');
		}
		else {
				if (confirm('Bạn có chắc thay đổi đơn vị có mã ' + dvMaUpdate))
					updateBp(dvMaUpdate, dvTenUpdate, sdtUpdate, diaChiUpdate, emailUpdate);
			}
		}
 	 	function updateBp(dvMaUpdate, dvTenUpdate, sdtUpdate, diaChiUpdate, emailUpdate) {
 	 		 if (dvTenUpdate == '')
	 			{
	 				$('#requiredvTenUp').html('Vui lòng nhập tên BPSD');
	 			}
			else if (sdtUpdate == '')
			{
				$('#requireSdtUp').html('Vui lòng nhập số điện thoại');
			}
			else if (diaChiUpdate == '')
			{
				$('#requireDiachiUp').html('Vui lòng nhập địa chỉ');
			}
			else if (emailUpdate == '')
			{
				$('#requireEmailUp').html('Vui lòng nhập email');
			}
			else {
					$.ajax({
						url: getRoot() +  "/updateBp.html",	
					  	type: "GET",
					  	dateType: "JSON",
					  	data: { "dvMaUpdate": dvMaUpdate, "dvTenUpdate": dvTenUpdate, "sdtUpdate": sdtUpdate, "diaChiUpdate": diaChiUpdate, "emailUpdate": emailUpdate},
					  	contentType: 'application/json',
					    mimeType: 'application/json',
					  	
					  	success: function(dv) {
					  		if (dv == "authentication error") {
		 			  			location.assign("login.jsp");
		 			  		} else {
						  		$('table tr').has('input[name="dvMa"]:checked').remove();
								$('#view-table-bo-phan table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"dvMa\" value=\"' +dvMaUpdate + '\"</td><td class=\"col\">'
										 + dvMaUpdate +'</td><td class=\"col\">' 
										 + dvTenUpdate +'</td><td class=\"col\">' 
										 + sdtUpdate +'</td><td class=\"col\">' 
										 + diaChiUpdate +'</td><td class=\"col\">' 
										 + emailUpdate +'</td></tr>');
						  		$('input:text[name=dvMaUpdate]').val('');
								dvTenUpdate = $('input:text[name=dvTenUpdate]').val('');
								sdtUpdate = $('input:text[name=sdtUpdate]').val('');
								diaChiUpdate = $('input:text[name=diaChiUpdate]').val('');
								emailUpdate = $('input:text[name=emailUpdate]').val('');
						  		showForm("update-form", false);	
						  		alert("Thay đổi thành công bộ phân có mã "+ dvMaUpdate);
						  		$('input[name="dvMa"]:checked').prop('checked',false);
		 			  		}
					  	}
					});
			}
		}

 	 	function resetUpdateBP(){
 	 		$('#update-form input:text[name=dvTenUpdate]').val('');
			$('#update-form input:text[name=sdtUpdate]').val('');
			$('#update-form input:text[name=diaChiUpdate]').val('');
			$('#update-form input:text[name=emailUpdate]').val('');
 	 	}
		function confirmDelete(){

			dvMa = $('input:checkbox[name=dvMa]:checked').val();
			var dvMaList = [];
			$.each($("input[name='dvMa']:checked"), function(){            
				dvMaList.push($(this).val());
		    });
			var str = dvMaList.join(", ");
			if (dvMaList.length == 0)
				alert('Bạn phải chọn 1 hoặc nhiều bộ phận để xóa!!');
			else if (confirm('Bạn có chắc xóa bộ phận có mã ' + str))
				deleteBp(str);
		}
 		
	 	 function deleteBp(str) {
			 
			$.ajax({
				url: getRoot() +  "/deleteBp.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "dvList": str},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(result) {
			  		if (result == "authentication error") {
 			  			location.assign("login.jsp");
 			  		} else {
						$('table tr').has('input[name="dvMa"]:checked').remove();
						alert('Bộ phận có mã ' + str + " đã bị xóa");
 			  		}
			    } 
			});  
		}
	 	 function resetUpdateBP(){
	 		$('#update-form input:text[name=dvTenUpdate]').val('');
			$('#update-form input:text[name=sdtUpdate]').val('');
			$('#update-form input:text[name=diaChiUpdate]').val('');
			$('#update-form input:text[name=emailUpdate]').val('');
	 	 }
	 	function changedvMa(){
 	  		$('#requiredvMa').html('');
 	  		$('#add-form input:text[name=dvMa]').focus();
 	 	} 	
 	  	
 	  	function changedvTen(){
 	  		$('#requirevtTen').html('');
 	  		$('#add-form input:text[name=dvTen]').focus();
 	 	}	
 	  	
 	 	function changeSdt(){
 	  		$('#requireSdt').html('');
 	  		$('#add-form input:text[name=sdt]').focus();
 	 	} 	
 	  	
 	  	function changeDiachi(){
 	  		$('#requireDiachi').html('');
 	  		$('#add-form input:text[name=diaChi]').focus();
 	 	}	
 	  	function changeEmail(){
 	  		$('#requireEmail').html('');
 	  		$('#add-form input:text[name=email]').focus();
 	 	}	
 	  	
 	  	
 	  	function changedvTenUp(){
 	  		$('#requirevtTenUp').html('');
 	  		$('#add-form input:text[name=dvTenUpdate]').focus();
 	 	}	
 	  	
 	 	function changeSdtUp(){
 	  		$('#requireSdtUp').html('');
 	  		$('#add-form input:text[name=sdtUpdate]').focus();
 	 	} 	
 	  	
 	  	function changeDiachiUp(){
 	  		$('#requireDiachiUp').html('');
 	  		$('#add-form input:text[name=diaChiUpdate]').focus();
 	 	}	
 	  	function changeEmailUp(){
 	  		$('#requireEmailUp').html('');
 	  		$('#add-form input:text[name=emailUpdate]').focus();
 	 	}	
 	  	
 	  	$(document).ready(function() {
 	 	  	$('.page').click(function(){
 	 		var pageNumber = $(this).val();
 	 	    	$.ajax({
 	 				url: getRoot() +  "/loadPageDv.html",	
 	 			  	type: "GET",
 	 			  	dateType: "JSON",
 	 			  	data: { "pageNumber": pageNumber},
 	 			  	contentType: 'application/json',
 	 			    mimeType: 'application/json',
 	 			  	
 	 			  	success: function(dvList) {
	 	 			  	if (dvList == "authentication error") {
	 			  			location.assign("login.jsp");
	 			  		} else {
	 	 			  		$('#view-table-bo-phan table .rowContent').remove();
	 	 					if(dvList.length>0){
	 	 						for(i = 0;i < dvList.length; i++ ) {
	 	 							var dv = dvList[i] ;
	 	 							var style = '';	
	 	 							if (i % 2 == 0)
	 	 								style = 'style=\"background : #CCFFFF;\"';
	 	 							var str = '';
	 	 							str = '<tr class=\"rowContent\" ' + style + '>'
	 	 								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"dvMa\" value=\"' 
	 	 								+ dv.dvMa +'\" class=\"checkbox\"></td>'
	 	 								+ '<td class=\"mid-column\" style=\"font-family:.VnTime;text-align: left;\">' + dv.dvMa + '</td>'
	 	 								+ '<td class=\"column-2\" style=\"text-align: left;\">' + dv.dvTen + '</td>'
	 	 								+ '<td class=\"column-3\" style=\"text-align: left;\">' + dv.sdt + '</td>'
	 	 								+ '<td class=\"column-4\" style=\"text-align: left;\">' + dv.diaChi + '</td>'
	 	 								+ '<td class=\"column-5\" style=\"text-align: left;\">' + dv.email + '</td>'
	 	 								+ '</tr>';
	 	 							$('#view-table-bo-phan table tr:first').after(str);
	 	 						}
	 	 					}
 	 					}
 	 			  	}
 	 			});
 	 	    });	
 	 	});
 	 	$(document).ready(function() {
 		$('#add-form').keypress(function(e) {
 		 var key = e.which;
 		 if(key == 13)  // the enter key code
 		  {
 			 addBp();
 		    return false;  
 		  }
 		});   
 	});   
 	$(document).ready(function() {
 		$('#update-form').keypress(function(e) {
 		 var key = e.which;
 		 if(key == 13)  // the enter key code
 		  {
 			confirmUpdateBp();
 		    return false;  
 		  }
 		});   
 	});  