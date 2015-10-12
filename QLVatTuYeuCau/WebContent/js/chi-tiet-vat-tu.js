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
		function preAddCTVatTu(formId, check){
			var vtMa = $('#add-chitiet input:text[name=vtMa]').val();
			
			if(vtMa != null){
				showForm2('chitiet' ,'add-chitiet', check);
			}
			$('#noisanxuat').focus();
		}
		function addCTVattu() {
 			var vtMa = $('#add-chitiet input:text[name=vtMa]').val();
 			var vtTen = $('#add-chitiet input:text[name=vtTen]').val();
 			var dvt = $('#add-chitiet input:text[name=dvt]').val();
 			var noiSanXuat = $('#add-chitiet select[name=noiSanXuat]').val();
 			var chatLuong = $('#add-chitiet select[name=chatLuong]').val();
 			var dinhMuc = $('#add-chitiet input[name=dinhMuc]').val();
 			var soLuongTon = $('#add-chitiet input[name=soLuongTon]').val();
 			if(noiSanXuat == '') {
 				$('#requireNsx').html('Vui lòng chọn nơi sản xuất');
 			}
 			else if(chatLuong == '') {
 				$('#requireCl').html('Vui lòng chọn chất lượng');
 			}
// 			else if(dinhMuc == ''){
// 				dinhMuc = 0;
// 			}
 			else if(soLuongTon == '') {
 				$('#requireSl').html('Vui lòng nhập số lượng');
 			}
 			else {
 				if(dinhMuc == ''){
 	 				dinhMuc = 0;
 				}
 			$.ajax({
 				url: getRoot() +  "/addCTVattu.html",	
			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "vtMa": vtMa, "vtTen": vtTen, "dvt": dvt, "noiSanXuat": noiSanXuat, "chatLuong": chatLuong, "dinhMuc": dinhMuc, "soLuongTon": soLuongTon},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
			  	
 			  	success: function(ctvt) {
 			  		
			  		if(ctvt != "")
	 				{
					$('#view-table-chi-tiet table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"ctvtId\" value=\"' 
							+ctvt.ctvtId + '\"</td><td class=\"col\">'
							+ vtMa +'</td><td class=\"col\">'
							+ vtTen +'</td><td class=\"col\">'
							+ ctvt.noiSanXuat.nsxTen +'</td><td class=\"col\">' 				
							+ ctvt.chatLuong.clTen +'</td><td class=\"col\">'
							+ dvt +'</td><td class=\"col\">'
							+ dinhMuc +'</td><td class=\"col\">'
							+ soLuongTon +'</td></tr>');
					$('#add-chitiet select[name=noiSanXuat]').val('');
					$('#add-chitiet select[name=chatLuong]').val('');
					
					$('#add-chitiet input[name=dinhMuc]').val('');
					$('#add-chitiet input[name=soLuongTon]').val('');
					showForm2('chitiet' ,'add-chitiet', false);
			  		alert("Chi tiết vật tư "+vtMa + " đã được thêm ");	
				}
		  		else{
		  			alert("Chi tiết vật tư "+vtMa + " đã tồn tại ");
		  		}
			  	
 			  	}
 			});
 			}
 		}
		function resetAddCTVT()
		{
			$('#add-chitiet select[name=noiSanXuat]').val('');
			$('#add-chitiet select[name=chatLuong]').val('');			
			$('#add-chitiet input[name=dinhMuc]').val('');
			$('#add-chitiet input[name=soLuongTon]').val('');
		}
		function preEditCTVattu(formId, check){
			ctvtId = $('#view-table-chi-tiet input:checkbox[name=ctvtId]:checked').val();
			var ctvtMaList = [];
			$.each($("input[name='ctvtId']:checked"), function(){            
				ctvtMaList.push($(this).val());
		    });
			if (ctvtMaList.length == 0)
				alert('Bạn phải chọn 1 chi tiết vật tư để thay đổi!!');
			else if (ctvtMaList.length > 1)
				alert('Bạn chỉ được chọn 1 chi tiết vật tư để thay đổi!!');
			else {
			//alert(ctvtId);
				$.ajax({
					url: getRoot() +  "/preEditCTVattu.html",
					type: "GET",
					dataType: "JSON",
					data: {"ctvtId": ctvtId},
					contentType: "application/json",
					mimeType: "application/json",
					
					success: function(vt){
						
						$('#update-chitiet input:text[name=vtMaUpdate]').val(vt.vatTu.vtMa);
					  	$('#update-chitiet input:text[name=vtTenUpdate]').val(vt.vatTu.vtTen);
//					  	$('#noisanxuatUp option[value='+vt.noiSanXuat.nsxMa+']').prop('selected',true);
//					  	$('#chatluongUp option[value='+vt.chatLuong.clMa+']').prop('selected',true);
					  	$('#update-chitiet input:text[name=nsxUpdate]').val(vt.noiSanXuat.nsxTen);
					  	$('#update-chitiet input[name=maNsx]').val(vt.noiSanXuat.nsxMa);
					  	$('#update-chitiet input[name=maCl]').val(vt.chatLuong.clMa);
						$('#update-chitiet input:text[name=clUpdate]').val(vt.chatLuong.clTen);
						$('#update-chitiet input:text[name=dvtUpdate]').val(vt.vatTu.dvt.dvtTen);
						$('#update-chitiet input[name=dinhMucUpdate]').val(vt.dinhMuc);
						$('#update-chitiet input[name=soLuongTonUpdate]').val(vt.soLuongTon);
					  	
						showForm2('chitiet' ,'update-chitiet', check);
						$('#dinhMucFocus').focus();
					}
					
				});
		}
		}

		function confirmUpdateCTVattu(){
			var vtMaUpdate = $('#update-chitiet input:text[name=vtMaUpdate]').val();
			var vtTenUpdate = $('#update-chitiet input:text[name=vtTenUpdate]').val();
			var nsxUpdate = $('#update-chitiet input[name=maNsx]').val();
			var clUpdate = $('#update-chitiet input[name=maCl]').val();
			var dvtUpdate = $('#update-chitiet input:text[name=dvtUpdate]').val();
			var dinhMucUpdate = $('#update-chitiet input[name=dinhMucUpdate]').val();
			var soLuongTonUpdate = $('#update-chitiet input[name=soLuongTonUpdate]').val();
			if(soLuongTonUpdate == '') {
 				$('#requireSlUp').html('Vui lòng nhập số lượng');
 			}
			
 			else {
 				 if(dinhMucUpdate == ''){
 	 				dinhMuc = 0;
 	 			}
					if (confirm('Bạn có chắc thay đổi vật tư có mã ' + vtMaUpdate))
						updateCTVattu(vtMaUpdate, vtTenUpdate, dvtUpdate, nsxUpdate, clUpdate, dinhMucUpdate, soLuongTonUpdate);
 			}
		}
 		function updateCTVattu(vtMaUpdate, vtTenUpdate, dvtUpdate, nsxUpdate, clUpdate, dinhMucUpdate, soLuongTonUpdate){
 			$.ajax({
				url: getRoot() +  "/updateCTVattu.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "vtMaUpdate": vtMaUpdate, "vtTenUpdate": vtTenUpdate, "dvtUpdate": dvtUpdate, "nsxUpdate": nsxUpdate, "clUpdate": clUpdate, "dinhMucUpdate": dinhMucUpdate, "soLuongTonUpdate": soLuongTonUpdate},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
				  	
				  	success: function(ctvt) {
				  		$('table tr').has('input[name="ctvtId"]:checked').remove();
				  		$('#view-table-chi-tiet table tr:first').after('<tr class="rowContent"><td class=\"left-column\"><input type=\"checkbox\" name=\"ctvtId\" value=\"' 
				  				+ctvt.ctvtId + '\"</td><td class=\"col\">'
				  				+ vtMaUpdate +'</td><td class=\"col\">'
				  				+ vtTenUpdate +'</td><td class=\"col\">'
				  				+ ctvt.noiSanXuat.nsxTen +'</td><td class=\"col\">'
				  				+ ctvt.chatLuong.clTen +'</td><td class=\"col\">'
				  				+ dvtUpdate +'</td><td class=\"col\">'
				  				+ dinhMucUpdate +'</td><td class=\"col\">'
				  				+ soLuongTonUpdate+'</td></tr>');
				  		$('input:text[name=vtMaUpdate]').val('');			 
				  		$('input:text[name=vtTenUpdate]').val('');
				  		$('input:text[name=nsxUpdate]').val('');
				  		$('input:text[name=clUpdate]').val('');
						$('input:text[name=dvtUpdate]').val('');
						$('input[name=dinhMucUpdate]').val('');
						$('input[name=soLuongTonUpdate]').val('');
						showForm2('chitiet' ,'update-chitiet', false);
				  		alert("Sửa thành công chi tiết vật tư có mã "+ vtMaUpdate+ " !");
				  		$('input[name="ctvtId"]:checked').prop('checked',false);
				  	
				  	}
				});
 	}
 		function resetUpdateCTVT(){
 			$('#update-chitiet input[name=dinhMucUpdate]').val('');
			$('#update-chitiet input[name=soLuongTonUpdate]').val('');
 		}
 	function confirmDeleteCTVT(){
 		ctvtId = $('#view-table-chi-tiet input:checkbox[name=ctvtId]:checked').val();
 		var ctvtMaList = [];
		$.each($("input[name='ctvtId']:checked"), function(){            
			ctvtMaList.push($(this).val());
	    });
		var str = ctvtMaList.join(", ");
		if (ctvtMaList.length == 0)
			alert('Bạn phải chọn 1 hoặc nhiều chi tiết vật tư để xóa!!');
		else if (confirm('Bạn có chắc xóa chi tiết vật tư có mã ' + str))
			deleteCTVattu(str);
 	}
	
  	 function deleteCTVattu(str) {
  	///	alert(str); 
 		$.ajax({
 			url: getRoot() +  "/deleteCTVattu.html",	
 		  	type: "GET",
 		  	dateType: "JSON",
 		  	data: { "ctvtList": str},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
 		  	success: function() {
 		  		$('#view-table-chi-tiet table tr').has('input[name="ctvtId"]:checked').remove();
 		  		alert('Chi tiết vật tư có mã ' + str + " đã bị xóa");	
				
 		    } 
 		});  
 	}

  	function changeDM(){
  		$('#requireDM').html('');
  		$('#add-chitiet input[name=dinhMuc]').focus();
 	} 	
  	
  	function changeSL(){
  		$('#requireSl').html('');
  		$('#add-chitiet input[name=soLuongTon]').focus();
 	}
	function changeNsx(){
  		$('#requireNSX').html('');
  		$('#add-chitiet select[name=noisanxuat]').focus();
 	}
	function changeCl(){
  		$('#requireCl').html('');
  		
 	}
	function changeDMUp(){
  		$('#requireDM').html('');
  		$('#update-chitiet input[name=dinhMucUpdate]').focus();
 	}
	function changeSLUp(){
  		$('#requireSL').html('');
  		$('#update-chitiet input[name=soLuongTonUpdate]').focus();
 	}
    $(document).ready(function() {
        $('#view-table-chi-tiet .checkCTAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('#view-table-chi-tiet .checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('#view-table-chi-tiet .checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });
            }
        });
        
    }); 

 	    function loadPageCTVatTu(pageNumber){
 		if (pageNumber == 'Next') {
 			var lastPage = document.getElementsByClassName('page')[9].value;
 			var p = (lastPage) / 5;
 			var page = p * 5;
 		}
 		else if (pageNumber == 'Previous') {
 			var firstPage = document.getElementsByClassName('page')[0].value;
 			var p = (firstPage - 1) / 5;
 			var page =  firstPage-2;
 		}
 		else {
 			var page = pageNumber;
 		}
 	    	$.ajax({
 				url: getRoot() +  "/loadPageCTVatTu.html",	
 			  	type: "GET",
 			  	dateType: "JSON",
 			  	data: { "pageNumber": page},
 			  	contentType: 'application/json',
 			    mimeType: 'application/json',
 			  	
 			  	success: function(objectList) {
 			  		var size = objectList[1];
 			  		var ctvtList = objectList[0];
 			  		var length = ctvtList.length;
 			  		$('#view-table-chi-tiet table .rowContent').remove();
 						for(i = 0;i < length; i++ ) {
 							var ctvt = ctvtList[i];
 							var cells = '';
 							var style = '';
 							if (i % 2 == 0)
 								style = 'style=\"background : #CCFFFF;\"';
		 					cells = '<td class=\"col\">' + ctvt.vatTu.vtMa + '</td>'
		 							+'<td class=\"col\" style=\"text-align: left;\">' + ctvt.vatTu.vtTen + '</td>'
	 								+ '<td class=\"col\" style=\"text-align: left;\">' + ctvt.noiSanXuat.nsxTen+ '</td>'
	 								+ '<td class=\"col\" style=\"text-align: left;\">' + ctvt.chatLuong.clTen+ '</td>'
	 								+ '<td class=\"col\">' + ctvt.vatTu.dvt.dvtTen+ '</td>'
	 								+ '<td class=\"col\">' + ctvt.dinhMuc+ '</td>'
	 								+ '<td class=\"col\">' + ctvt.soLuongTon+ '</td>'
		 					var row = '<tr class=\"rowContent\" ' + style + '>' + cells + '</tr>';
		 					$('#view-table-chi-tiet table tr:first').after(row);
 						}
 					var button = '';
					if(pageNumber == 'Next') {
						for (var i = 0; i < 10; i++) {
							var t = ((p -1) * 5 + i + 1);
							
							button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageCTVatTu(' + ((p -1)*5 + i)  +')\">&nbsp;';
							if (t > size)
								break;
						}
						button = '<input type=\"button\" class=\"pageMove\" value=\"<<Trước\" onclick= \"loadPageCTVatTu(\'Previous\')\">&nbsp;'  + button;
						if ((p + 1) * 5 < size)
							button += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPageCTVatTu(\'Next\');\">';
						$('#paging').html(button);
						$('.page')[5].focus();
					} else if (pageNumber == 'Previous'){
						if (p > 0)
							p = p -1;
						for (var i = 0; i < 10; i++)
							button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageCTVatTu(' + (p * 5 + i)  +')\">&nbsp;';
						
						button = button + '<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPageCTVatTu(\'Next\');\">';
						if (p >= 1)
							button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPageCTVatTu(\'Previous\')\">&nbsp;' + button;
						$('#paging').html(button);	
						$('.page')[4].focus();
					}
 			  	}
 			});
    }
 	$(document).ready(function() {
	$('#add-chitiet').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 addCTVattu();
	    return false;  
	  }
	});   
});   
$(document).ready(function() {
	$('#update-chitiet').keypress(function(e) {
	 var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		 confirmUpdateCTVattu();
	    return false;  
	  }
	});   
});  