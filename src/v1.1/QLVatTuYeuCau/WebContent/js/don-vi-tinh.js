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

		function preUpdatedvt(formId, check){
			//dvtId = $('input:checkbox[name=dvtId]:checked').val();
			var dvtMaList = [];
			$.each($("input[name='dvtId']:checked"), function(){            
				dvtMaList.push($(this).val());
		    });
			if (dvtMaList.length == 0)
				alert('Bạn phải chọn 1 đơn vị tính để thay đổi!!');
			else if (dvtMaList.length > 1)
				alert('Bạn chỉ được chọn 1 đơn vị tính để thay đổi!!');
			else {
				var temp = dvtMaList[0].split("##");
				$.ajax({
					url: getRoot() +  "/preEditdvt.html",
					type: "GET",
					dataType: "JSON",
					data: {"dvtId": temp[0]},
					contentType: "application/json",
					mimeType: "application/json",
					
					success: function(dvt){		
						if (dvt == "authentication error") {
							location.assign("login.jsp");
						} else {
							$('input:text[name=dvtTenUpdate]').val(dvt.dvtTen);
						  	showForm(formId, check);
						  	$('#dvtUpFocus').focus();
						}
					},
					error : function(e) {
						alert("Có lỗi xảy ra");
						console.log("ERROR: ", e);
						display(e);
						
					},
					done : function(e) {
						console.log("DONE");
					}
				});
				
				
			}
		}
		function loadAddDvt() {
	 		showForm('add-form', false);
	 		$('input[name="dvtId"]:checked').prop('checked',false);
	 	}
	 	function loadUpdateDvt() {
	 		showForm('update-form', false);
	 		$('input[name="dvtId"]:checked').prop('checked',false);
	 	}
		function confirmDelete(){
			vtId = $('input:checkbox[name=dvtId]:checked').val();
			var dvtMaList = [];
			var dvtTenList = [];
			$.each($("input[name='dvtId']:checked"), function(){
				var id = $(this).val();
				var temp = id.split("##") ;
				//var dvtTen = $(this).val();
				dvtMaList.push(temp[0]);
				dvtTenList.push(temp[1]);
		    });
			var str = dvtTenList.join(", ");
			var str2 = dvtMaList.join("##");
			if (dvtMaList.length == 0)
				alert('Bạn phải chọn 1 hoặc nhiều vai trò để xóa!!');
			else if (confirm('Bạn có chắc xóa đơn vị tính ' + str))
				deletedvt(str2, str);
		}
 		
	 	 function deletedvt(dvtMaList, dvtTenList) {
			 
			$.ajax({
				url: getRoot() +  "/deletedvt.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "dvtList": dvtMaList},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function(dvtList) {
			  		if (dvtList == "authentication error") {
						location.assign("login.jsp");
					} else if (dvtList == "fail") {
			  			alert("Đã có lỗi xảy ra!!!");
			  			location.assign("login.jsp");
			  		} else {
				  		$('table tr').has('input[name="dvtId"]:checked').remove();
				  		alert('Đơn vị tính ' + dvtTenList + " đã được xóa");
			  		}		
			    } 
			});  
		} 
 	 	function adddvt() {
// 			dvtId = $('#add-form input[name=dvtId]').val();
 			dvtTen = $('#add-form input:text[name=dvtTen]').val();
// 			if(dvtId == '') {
// 				$('#requiredvtId').html('Vui lòng nhập mã đơn vị tính');
// 			}
 			 if (dvtTen == '')
	 			{
	 				$('#requiredvtTen').html('Vui lòng nhập tên đơn vị tính');
	 			}
 			else {

		 			$.ajax({
		 				url: getRoot() +  "/adddvt.html",	
					  	type: "GET",
		 			  	dateType: "JSON",
		 			  	data: {"dvtTen": dvtTen},
		 			  	contentType: 'application/json',
		 			    mimeType: 'application/json',
					  	
		 			  	success: function(result) {
		 			  		if (result == "authentication error") {
								location.assign("login.jsp");
							} else if(result == "fail")
			 				{
					  			alert("Đơn vị tính "+ dvtTen + " đã tồn tại ");	
							}
					  		else{
					  			$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"dvtId\" value=\"' + result.dvtId + '##' + dvtTen+'\"</td><td class=\"col\">' + dvtTen+'</td></tr>');
								$('#add-form input:text[name=dvtTen]').val('');
						  		showForm("add-form", false);	
						  		alert("Đơn vị tính "+ dvtTen + " đã được thêm ");
					  			
					  		}
		 			  	}
		 			});
 			}
 		}
 	 	function confirmUpdatedvt(){
//			var dvtIdUpdate = $('input[name=dvtIdUpdate]').val();
			var dvtTenUpdate = $('input:text[name=dvtTenUpdate]').val();
			
			if (confirm('Bạn có chắc thay đổi đơn vị tính: ' + dvtTenUpdate))
				updatedvt(dvtTenUpdate);
		}
 	 	function updatedvt(dvtTenUpdate) {
 	 		if (dvtTenUpdate == '')
	 			{
	 				$('#requiredvtTenUp').html('Vui lòng nhập tên đơn vị tính');
	 			}
 			
 			else {

					$.ajax({
						url: getRoot() +  "/updatedvt.html",	
					  	type: "GET",
					  	dateType: "JSON",
					  	data: {"dvtTenUpdate": dvtTenUpdate},
					  	contentType: 'application/json',
					    mimeType: 'application/json',
					  	
					  	success: function(dvt) {
					  		if (dvt == "authentication error") {
								location.assign("login.jsp");
							} else if (dvt == "fail") {
					  			alert("Đã có lỗi xảy ra");
					  			location.assign("login.jsp");
					  		} else {
						  		$('table tr').has('input[name="dvtId"]:checked').remove();
						  		$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"dvtId\" value=\"' + dvt.dvtId + '##' + dvtTenUpdate + '\"</td><td class=\"col\">' + dvtTenUpdate+'</td></tr>');						
						  		showForm("update-form", false);	
						  		alert("Thay đổi thành công đơn vị tính "+ dvtTenUpdate);
						  		dvtTenUpdate = $('input:text[name=dvtTenUpdate]').val('');
						  		$('input[name="dvtId"]:checked').prop('checked',false);
					  		}	
					  	}
					});
 			}
		}
 	 	function resetUpdatedvt(){
 	 		$('#update-form input:text[name=dvtTenUpdate]').val('');
 	 	}
// 	 	function changedvtId(){
// 	  		$('#requiredvtId').html('');
// 	  		$('#add-form input[name=dvtId]').focus();
// 	 	} 	
 	  	
 	  	function changedvtTen(){
 	  		$('#requiredvtTen').html('');
 	  		$('#add-form input:text[name=dvtTen]').focus();
 	 	}	
 	  	
 	
 	  	
 	  	function changedvtTenUp(){
 	  		$('#requiredvtTenUp').html('');
 	  		$('#update-form input:text[name=dvtTenUpdate]').focus();
 	 	}
 	  	
 	  	$(document).ready(function() {
 		  	$('.page').click(function(){
 			var pageNumber = $(this).val();
 		    	$.ajax({
 					url: getRoot() +  "/loadPagedvt.html",	
 				  	type: "GET",
 				  	dateType: "JSON",
 				  	data: { "pageNumber": pageNumber},
 				  	contentType: 'application/json',
 				    mimeType: 'application/json',
 				  	
 				  	success: function(dvtList) {
 				  		if (dvtList == "authentication error") {
							location.assign("login.jsp");
						} else {
	 				  		$('#view-table table .rowContent').remove();
	 						if(dvtList.length>0){
	 							for(i = 0;i < dvtList.length; i++ ) {
	 								var dvt = dvtList[i] ;
	 								var style = '';	
	 								if (i % 2 == 0)
	 									style = 'style=\"background : #CCFFFF;\"';
	 								var str = '';
	 								str = '<tr class=\"rowContent\" ' + style + '>'
	 									+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"dvtId\" value=\"' 
	 									+ dvt.dvtId + '##' + dvt.dvtTen +'\" class=\"checkbox\"></td>'
	// 									+ '<td class=\"col\">' + dvt.dvtId + '</td>'
	 									+ '<td class=\"col\">' + dvt.dvtTen + '</td>'
	 									+ '</tr>';
	 								$('#view-table table tr:first').after(str);
	 							}
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
 		 adddvt();
 	    return false;  
 	  }
 	});   
 	});   
 	$(document).ready(function() {
 	$('#update-form').keypress(function(e) {
 	 var key = e.which;
 	 if(key == 13)  // the enter key code
 	  {
 		confirmUpdatedvt();
 	    return false;  
 	  }
 	});   
 	});