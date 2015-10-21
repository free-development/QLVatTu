		function showForm(formId, check){
			if (check)
				document.getElementById(formId).style.display="block";
			else document.getElementById(formId).style.display="none";
			var f = document.getElementById('vattu'), s, opacity;
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
		function timKiemVattu(){
			var vtTen = '';
			var vtMa = '';
			var check = $('#checkTen:checked').val();
			if (check != null)
				vtTen = $('#search input[name=vattu]').val();
			else 
				vtMa = $('#search input[name=vattu]').val();
			$.ajax({
				url: getRoot() +  "/timKiemVattu.html",	
			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "vtMa": vtMa, "vtTen": vtTen},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
 			  	success: function(objectList){
 			  		var vtList = objectList[0];
 			  		var size = objectList[1];
 			  		if(vtList.length>0){
 			  			$('#view-table-vat-tu table .rowContent').remove();
						for(i = 0;i < vtList.length; i++ ) {
							vattu = vtList[i];
							//alert(vtList[i].vtMa);
		 			  		
					  				$('#view-table-vat-tu table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' +vattu.vtMa 
									+ '\"</td><td class=\"col\">'+ vattu.vtMa +'</td><td class=\"col\" style=\"text-align: left;\">' + vattu.vtTen
									+'</td><td class=\"col\" style=\"text-align: center;width: 200px;\">' + vattu.dvt.dvtTen
									+'</td><td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\''
									+vattu.vtMa+'\');\">Xem</button></td></tr>');
						}
						var strPage = '';
						for (i = 0; i < size; i++) {
							strPage += '<input type=\"button\" class=\"page\" name="\page\" value=\"' + (i + 1) + '\"  onclick=\"loadPageVatTu(' + i + ')\">  ';
							if (i == 10)
								break;
						}
						if (size > 10) {
							strPage = '<input type=\"button\" name="\page\" class=\"page\" value=\"<< Trước\" onclick= \"loadPageVatTu(\'Previous\');\">  ' 
							+ strPage + ' <input type=\"button\" name="\page\" class=\"page\" value=\">> Sau\" onclick= \"loadPageVatTu(\'Next\');\"> ';
						}
						$('#paging').html(strPage);
 			  		}
 			  		else {
 			  				alert("Không tìm thấy vật tư!");
		  			}
 			  	}
			});
		}
		function addVattu() {
 			vtMa = $('#add-form input:text[name=vtMa]').val();
 			vtTen = $('#add-form input:text[name=vtTen]').val();
 			dvt = $('#add-form select[name=dvt]').val();
 			if(vtMa == '') {
 				$('#requireVtMa').html('Vui lòng nhập mã vật tư');
 			}
 			else if (vtTen == '')
	 			{
	 				$('#requireVtTen').html('Vui lòng nhập tên vật tư');
	 			}
 			else if(dvt == '')
 				{
 					$('#requireDvt').html('Vui lòng chọn đơn vị tính');
 				}
 			else {

		 			$.ajax({
		 				url: getRoot() +  "/addVattu.html",	
					  	type: "GET",
		 			  	dateType: "JSON",
		 			  	data: { "vtMa": vtMa, "vtTen": vtTen, "dvt": dvt},
		 			  	contentType: 'application/json',
		 			    mimeType: 'application/json',
					  	
		 			  	success: function(result) {
					  		if(result == "fail")
			 				{
					  			alert("Vật tư "+ vtMa + " đã tồn tại ");
					  		}
					  		else{
							$('#view-table-vat-tu table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' +vtMa 
									+ '\"</td><td class=\"col\">'+ vtMa +'</td><td class=\"col\" style=\"text-align: left; width: 200px;\">' + vtTen
									+'</td><td class=\"col\" style=\"text-align: center;\">' + result.dvt.dvtTen
									+'</td><td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\''
									+vtMa+'\');\">Xem</button></td></tr>');
					  		$('#add-form input:text[name=vtMa]').val('');
							$('#add-form input:text[name=vtTen]').val('');
							$('#add-form select[name=dvt]').val('');
//							$('#add-chitiet input:text[name=vtMa]').val(vtMa);
//							$('#add-chitiet input:text[name=vtTen]').val(vtTen);
//							$('#add-chitiet input:text[name=dvt]').val(dvt);
							
//					  		showForm2('add-form','vattu', false);	
							loadAddVt();
					  		alert("Vật tư "+ vtMa + " đã được thêm ");
					  		}
		 			  	}
		 			});
 			}
 		}

		function preEditVattu(formId1,formId2, check){
			vtMa = $('input:checkbox[name=vtMa]:checked').val();
			//alert(vtMa);
			var vtMaList = [];
			$.each($("input[name='vtMa']:checked"), function(){            
				vtMaList.push($(this).val());
		    });
			if (vtMaList.length == 0)
				alert('Bạn phải chọn 1 vật tư để thay đổi!!');
			else if (vtMaList.length > 1)
				alert('Bạn chỉ được chọn 1 vật tư để thay đổi!!');
			else {
				$.ajax({
					url: getRoot() +  "/preEditVattu.html",
					type: "GET",
					dataType: "JSON",
					data: {"vtMa": vtMa},
					contentType: "application/json",
					mimeType: "application/json",
					
					success: function(vt){
						
						$('input:text[name=vtMaUpdate]').val(vt.vtMa);
					  	$('input:text[name=vtTenUpdate]').val(vt.vtTen);
						$('#donvitinhUp option[value='+vt.dvt.dvtId+']').prop('selected',true);
						$('#aa').focus();
					  	showForm2(formId1,formId2, check);
					}
					
				});
		}
		}
		function confirmUpdateVattu(){
			var vtMaUpdate = $('input:text[name=vtMaUpdate]').val();
			var vtTenUpdate = $('input:text[name=vtTenUpdate]').val();
			var dvtUpdate = $('select[name=dvtUpdate]').val();
			if (confirm('Bạn có chắc thay đổi vật tư có mã ' + vtMaUpdate))
				updateVattu(vtMaUpdate, vtTenUpdate, dvtUpdate);
		}
 		function updateVattu(vtMaUpdate, vtTenUpdate, dvtUpdate){
 			
 			if (vtTenUpdate == '')
	 			{
	 				$('#requireVtTenUp').html('Vui lòng nhập tên vật tư');
	 			}
 			else if(dvtUpdate == '')
 				{
 					$('#requireDvtUp').html('Vui lòng chọn đơn vị tính');
 				}
 			else {

		 			$.ajax({
						url: getRoot() +  "/updateVattu.html",	
					  	type: "GET",
					  	dateType: "JSON",
					  	data: { "vtMaUpdate": vtMaUpdate, "vtTenUpdate": vtTenUpdate, "dvtUpdate": dvtUpdate},
					  	contentType: 'application/json',
					    mimeType: 'application/json',
						  	
						  	success: function(vt) {
						  		$('table tr').has('input[name="vtMa"]:checked').remove();
						  		$('#view-table-vat-tu table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"vtMa\" value=\"' +vtMaUpdate + '\"</td><td class=\"col\">'+ vtMaUpdate +'</td><td class=\"col\">' + vtTenUpdate+'</td><td class=\"col\">' 
						  				+ vt.dvt.dvtTen+'</td><td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\''
										+vtMaUpdate+'\');\">Xem</button></td></tr>');
						  		$('input:text[name=vtMaUpdate]').val('');			 
						  		$('input:text[name=vtTenUpdate]').val('');
								$('select[name=dvtUpdate]').val('');
								showForm2('vattu','update-form', false);
						  		alert("Thay đổi thành công vật tư có mã "+vtMaUpdate+ " !");
						  		$('input[name="vtMa"]:checked').prop('checked',false);	
						  		
						  	}
						});
 			}
 	}
 		function resetUpdateVT(){
 			$('#update-form input:text[name=vtTenUpdate]').val('');
			$('#update-form select[name=dvtUpdate]').val('');
 		}
 		function loadAddVt() {
 			showForm2('vattu','add-form', false);
 	 		$('input[name="vtMa"]:checked').prop('checked',false);
 	 	}
 	 	function loadUpdateVt() {
 	 		showForm2('vattu','update-form', false);
 	 		$('input[name="vtMa"]:checked').prop('checked',false);
 	 	}
 	function confirmDeleteVT(){
 		var vtMa = $('input:checkbox[name=vtMa]:checked').val();
		var vtMaList = [];
		$.each($("input[name='vtMa']:checked"), function(){            
			vtMaList.push($(this).val());
	    });
		var str = vtMaList.join(", ");
		if (vtMaList.length == 0)
			alert('Bạn phải chọn 1 hoặc nhiều vật tư để xóa!!');
		else if (confirm('Bạn có chắc xóa vật tư có mã ' + str))
			deleteVattu(str);
 	}
	
  	 function deleteVattu(str) {
		 
 		$.ajax({
 			url: getRoot() +  "/deleteVattu.html",	
 		  	type: "GET",
 		  	dateType: "JSON",
 		  	data: { "vtList": str},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
 		  	success: function(result) {
 		  		
	 		  			$('#view-table-vat-tu table tr').has('input[name="vtMa"]:checked').remove();
	 		  			alert('Vật tư có mã ' + str + " đã bị xóa");	  			
 		    } 
 		});  
 	}
  	 


  	function showCTVatTu(vtMa){ 
  		
  		$.ajax({
  			url: getRoot() +  "/showCTVatTu.html",
			type: "GET",
		  	dateType: "JSON",
		  	data: {"vtMa": vtMa},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		 
			success: function(listCTVatTu){
				
				$('#view-table-chi-tiet table .rowContent').remove();
//				if(listCTVatTu.a == null)
//					alert("1");
//					else alert("2");
			//	alert(listCTVatTu.vtMa)
				if(listCTVatTu.vtMa != null) {
					$('#add-chitiet input:text[name=vtMa]').val(listCTVatTu.vtMa);
					$('#add-chitiet input:text[name=vtTen]').val(listCTVatTu.vtTen);
					$('#add-chitiet input:text[name=dvt]').val(listCTVatTu.dvt.dvtTen);
					alert("Không có chi tiết vật tư!");
				}
				else {
					if(listCTVatTu.length>0){
					
							for(i = 0;i < listCTVatTu.length; i++ ) {
			
							$('#view-table-chi-tiet table tr:first').after("<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"ctvtId\" value=\""
									+ listCTVatTu[i].ctvtId + "\" id=\"checkbox\"></td>"
									+"<td class=\"col\">" +listCTVatTu[i].vatTu.vtMa+ "</td>"
									+"<td class=\"col\" style=\"text-align: left;width: 300px;\">" +listCTVatTu[i].vatTu.vtTen+ "</td>"
									+"<td class=\"col\" style=\"text-align: left;\">" +listCTVatTu[i].noiSanXuat.nsxTen+ "</td>"
									+"<td class=\"col\" style=\"text-align: left;\">" +listCTVatTu[i].chatLuong.clTen+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].vatTu.dvt.dvtTen+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].dinhMuc+ "</td>"
									+"<td class=\"col\">" +listCTVatTu[i].soLuongTon+ "</td></tr>");
							}
							vtMa = listCTVatTu[0].vatTu.vtMa;
							vtTen = listCTVatTu[0].vatTu.vtTen;
							dvt = listCTVatTu[0].vatTu.dvt.dvtTen;
							$('#add-chitiet input:text[name=vtMa]').val(vtMa);
							$('#add-chitiet input:text[name=vtTen]').val(vtTen);
							$('#add-chitiet input[name=dvt]').val(dvt);
							
						}
				}
				//showForm(formId, check);
				showForm2('vattu','chitiet',true);
			}
		  	
  		});
  	}
  	
  	

  	function changeVtMa(){
  		$('#requireVtMa').html('');
  		$('#add-form input:text[name=vtMa]').focus();
 	} 	
  	
  	function changeVtTen(){
  		$('#requireVtTen').html('');
  		$('#add-form input:text[name=vtTen]').focus();
 	}
  	
  	function changeDvt(){
  		$('#requireDvt').html('');
  		$('#add-form input[name=dvt]').focus();
 	}
  	

  	function changeVtTenUp(){
  		$('#requireVtTenUp').html('');
  		$('#update-form input:text[name=vtTenUpdate]').focus();
 	}
  	
  	function changeDvtUp(){
  		$('#requireDvtUp').html('');
  		$('#update-form input[name=dvtUpdate]').focus();
 	}
  	
  	
    $(document).ready(function() {
        $('#view-table-vat-tu .checkAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('#view-table-vat-tu .checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('#view-table-vat-tu .checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });
            }
        });
        
    }); 
    function test1(){
    	$('#test select option[value='+3+']').prop('selected',true);
    }
    
    
    function loadPageVatTu(pageNumber){
 		
 		if (pageNumber == 'Next') {
 			var lastPage = document.getElementsByClassName('page')[9].value;
 			var p = (lastPage) / 5;
 			var page = lastPage;
 		}
 		else if (pageNumber == 'Previous') {
 			var firstPage = document.getElementsByClassName('page')[0].value;
 			var p = (firstPage - 1) / 5;
 			var page =  firstPage-2;
 		}
 		else {
 			page = pageNumber;
 		}
 	    	$.ajax({
 				url: getRoot() +  "/loadPageVatTu.html",	
 			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "pageNumber": page},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
 			  	
 			  	success: function(objectList) {
 			  		var size = objectList[1];
 			  		var vtList = objectList[0];
 			  		var length = vtList.length;
 			  		$('#view-table-vat-tu table .rowContent').remove();
 						for(i = 0;i < length; i++ ) {
 							var vt = vtList[i];
 							var cells = '';
 							var style = '';
 							if (i % 2 == 0)
 								style = 'style=\"background : #CCFFFF;\"';
// 							str = '<tr class=\"rowContent\" ' + style + '>'
		 					cells = 	'<td class=\"left-column\" style=\"width: 20px;\">' 
		 								+'<input type=\"checkbox\" name=\"vtMa\" value=\"'+ vt.vtMa +'\" class=\"checkbox\"></td>'
		 								+ '<td class=\"col\" style=\"text-align: left;\">' + vt.vtMa + '</td>'
		 								+ '<td class=\"col\" style=\"text-align: left;\">' + vt.vtTen + '</td>'
		 								+ '<td class=\"col\">' + vt.dvt.dvtTen + '</td>'
		 								+ '<td style=\"text-align: center;\"><button type=\"button\" class=\"button-xem\" value=\"Xem\" onclick=\"showCTVatTu(\''
										+vt.vtMa+'\');\">Xem</button></td>';
		 					var row = '<tr ' +style + 'class = \"rowContent\">' + cells + '</tr>';
		 					$('#view-table-vat-tu table tr:first').after(row);
 						}
 					var button = '';
					if(pageNumber == 'Next') {
						for (var i = 0; i < 10; i++) {
							var t = ((p -1) * 5 + i + 1);
							
							button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageVatTu(' + ((p -1)*5 + i)  +')\">&nbsp;';
							
							if (t > size)
								break;
						}
						button = '<input type=\"button\" class=\"pageMove\" value=\"<<Trước\" onclick= \"loadPageVatTu(\'Previous\')\">&nbsp;'  + button;
						if ((p + 1) * 5 < size)
							button += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPageVatTu(\'Next\');\">';
						$('#paging').html(button);
						$('.page')[5].focus();
					} else if (pageNumber == 'Previous'){
						if (p > 0)
							p = p -1;
						for (var i = 0; i < 10; i++)
							button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageVatTu(' + (p * 5 + i)  +')\">&nbsp;';
						
						button = button + '<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPageVatTu(\'Next\');\">';
						if (p >= 1)
							button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPageVatTu(\'Previous\')\">&nbsp;' + button;
						$('#paging').html(button);	
						$('.page')[4].focus();
					}
 			  	}
 			});
    }
 	$(document).ready(function() {
	$('#add-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 addVattu();
	    return false;  
	  }
	});   
});   
$(document).ready(function() {
	$('#update-form').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 confirmUpdateVattu();
	    return false;  
	  }
	});   
});  
