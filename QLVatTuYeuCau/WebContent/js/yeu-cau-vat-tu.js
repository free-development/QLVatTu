function showForm(formId1, formId2, check){
    if (check)
        document.getElementById(formId2).style.display="block";
    else 
        document.getElementById(formId2).style.display="none";
    var f = document.getElementById(formId1), s, opacity;
    s = f.style;
    opacity = check? '10' : '100';
    s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
    s.filter = 'alpha(opacity='+opacity+')';
    for(var i=0; i<f.length; i++) f[i].disabled = check;
   
    
};
/*
function searchCtvt() {
	var vtMa = $('input:text[name=vtMa]').val();
	var vtTen = $('input:text[name=vtTen]').val();
	var nsx = $('select[name=nsx]').val();
	var chatLuong = $('select[name=chatLuong]').val();
	//alert(vtMa + vtTen + nsx + chatLuong);
	$.ajax({
		url: getRoot() +  "/searchCtvt.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "vtMa": vtMa, "vtTen": vtTen, "nsx": nsx, "chatLuong": chatLuong},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(ctVatTuList) {
	  		
	  		$('#view-search table #row').remove();
	  		for(i = 0; i < ctVatTuList.length; i++){
	  			if (i % 2 ==0 )
	  				$('#view-search table tr:first').after('<tr id="row" style=\"background : #CCFFFF;\">' + '<td>' + ctVatTuList[i].vatTu.vtMa + '</td>'  + '<td>' + ctVatTuList[i].vatTu.vtTen + '</td>'  + '<td>' + ctVatTuList[i].noiSanXuat.nsxTen + '</td>'  + '<td>' + ctVatTuList[i].chatLuong.clTen + '</td>'  + '<td>' + ctVatTuList[i].vatTu.dvt + '</td><td><input type=\"radio\" name=\"ctvtId\" value=\"' + ctVatTuList[i].ctvtId + '\"  onchange=\"preAddSoLuong();\"></td>' + '</tr>');
	  			else
	  				$('#view-search table tr:first').after('<tr id="row">' + '<td>' + ctVatTuList[i].vatTu.vtMa + '</td>'  + '<td>' + ctVatTuList[i].vatTu.vtTen + '</td>'  + '<td>' + ctVatTuList[i].vatTu.noiSanXuat.nsxTen + '</td>'  + '<td>' + ctVatTuList[i].vatTu.chatLuong.clTen + '</td>'  + '<td>' + ctVatTuList[i].vatTu.dvt + '</td><td><input type=\"radio\" name=\"ctvtId\" value=\"' + ctVatTuList[i].ctvtId + '\"></td>' + '</tr>');
	
			}
	  	}
	});
};
*/
function preAddSoLuong(){
//	$(document).ready(function() {
//		$('#view-search input:checkbox[name=ctvtId]').clicked(function() {
//			var ctvtId = this.val();
	var ctvtId = $('#view-search input:radio[name=ctvtId]:checked').val();
			$.ajax({
				url: getRoot() +  "/preAddSoLuong.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "ctvtId": ctvtId},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(ctvt) {
			  		$('#vtMaAdd').html(ctvt.vatTu.vtMa);
			  		$('#vtTenAdd').html(ctvt.vatTu.vtTen);
			  		$('#nsxTenAdd').html(ctvt.noiSanXuat.nsxTen);
			  		$('#clTenAdd').html(ctvt.chatLuong.clTen);
			  		$('#dvtAdd').html(ctvt.vatTu.dvt.dvtTen);
			  		$('#dvtAdd').html(ctvt.vatTu.dvt.dvtTen);
			  		$('#soLuongTonAdd').html(ctvt.soLuongTon);
			  	}
			});
				
			showForm('add-yeu-cau-form','add-so-luong-form',true);
			$('#danh-sach-vat-tu').hide();
			$('#main-form').hide();
//		});
//	});	
};

function addSoLuong(){
	var soLuong = $('input[name=soLuongAdd]').val();
	count = 0;
	if (soLuong != 0) { 
		$.ajax({
			url: getRoot() +  "/addSoLuong.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "soLuong": soLuong},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(objectList) {
		  		var yeuCau = objectList[0];
		  		var ctVatTu = objectList[1];
		  			$('input:radio[name=ctvtId]').prop('checked',false);
		  			$('input[name=soLuongAdd]').val('0');
		  			var cells = '<td><input id=\"'+ yeuCau.ycId + '\" type=\"checkbox\" name = \"yeuCau\" value=\"' + yeuCau.ycId + '\"</td>'
						+ '<td>' + ctVatTu.vatTu.vtMa + '</td>'
						+ '<td>' + ctVatTu.vatTu.vtTen + '</td>'
						+ '<td>' + ctVatTu.noiSanXuat.nsxTen + '</td>'
						+ '<td>' + ctVatTu.chatLuong.clTen + '</td>'
						+ '<td>' + ctVatTu.vatTu.dvt.dvtTen + '</td>'
						+ '<td>' + ctVatTu.soLuongTon + '</td>'
						+ '<td>' + yeuCau.ycSoLuong + '</td>'
						+ '<td id=\"soLuongCap' + yeuCau.ycId +'\">' + yeuCau.capSoLuong + '</td>';
					
		  			var row = '<tr id=\"' + yeuCau.ycId +'\"> ' + cells + + '</tr>';
		  			var style = '';
					
		  			if (yeuCau.ycSoLuong == soLuong) {
		  				if (count == 0) 
							style = 'style=\"background : #CCFFFF;\"';
		  					count ++;
			  			$('#view-table-yc table tr:first').after(row);
		  			}
		  			else {
		  				$('#view-table-yc table #' + yeuCau.ycId).html(cells);
		  			}
		  			showForm('add-yeu-cau-form','add-so-luong-form',false);
		  			$('#danh-sach-vat-tu').show();
		  			$('#main-form').show();
		  			alert('Thêm số lượng thành công');
		  	}
		});
	} else {
		alert('Số lượng phải lớn hơn 0!!!');
	}
};
function confirmDelete() {
	var ycList = [];
	$.each($('#view-table-yc input:checkbox[name=yeuCau]:checked'), function(){
		ycList.push($(this).val());
	})
	var str = ycList.join(', ');
	if (ycList.length == 0)
		alert('Bạn phải chọn 1 yêu cầu để sửa đổi!');
	else if (ycList.length > 1)
		alert('Bạn phải chọn 1 yêu cầu để sửa đổi!');
	else if(confirm('Bạn có chắc xóa yêu cầu?'))
		deleteYc(str);
};
function deleteYc(ycList) {	
	$.ajax({
		url: getRoot() +  "/deleteYc.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "ycList": ycList},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(result) {
		  	alert("Yêu cầu đã được xóa!");
			$('#view-table-yc table tr').has('input:checkbox[name=yeuCau]:checked').remove();
	    } 
	});  
};
function preUpdateYc() {
	var ycList = [];
	$.each($('input:checkbox[name=yeuCau]:checked'), function(){
		ycList.push($(this).val());
	})
	var str = ycList.join(', ');
	if (ycList.length == 0)
		alert('Bạn phải chọn 1 yêu cầu để sửa đổi!');
	else if (ycList.length > 1)
		alert('Bạn phải chọn 1 yêu cầu để sửa đổi!');
	else {
		
		$.ajax({
			url: getRoot() +  "/preUpdateYc.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "yeuCau": ycList[0]},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(objectList) {
		  		var yeuCau = objectList[0];
		  		var ctVatTu = objectList[1];
		  		$('#vtMaUpdate').html(ctVatTu.vatTu.vtMa);
		  		$('#vtTenUpdate').html(ctVatTu.vatTu.vtTen);
		  		$('#nsxTenUpdate').html(ctVatTu.noiSanXuat.nsxTen);
		  		$('#clTenUpdate').html(ctVatTu.chatLuong.clTen);
		  		$('#dvtUpdate').html(ctVatTu.vatTu.dvt.dvtTen);
		  		$('#dvtUpdate').html(ctVatTu.vatTu.dvt.dvtTen);
		  		$('#soLuongTonUpdate').html(ctVatTu.soLuongTon);
			  	$('#update-so-luong-form input[name=soLuongUpdate]').val(yeuCau.ycSoLuong);
		    } 
		});  
		showForm('add-yeu-cau-form','update-so-luong-form',true);
		$('#danh-sach-vat-tu').hide();
		$('#main-form').hide();
	}
}
function updateYc() {
	var soLuong = $('input[name=soLuongUpdate]').val();
	count = 0;
	$.ajax({
		url: getRoot() +  "/updateSoLuong.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "soLuong": soLuong},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(ycId) {
	  		if (ycId == 'fail')
	  			alert('Số lương yêu cầu không hợp lệ! Số lượng thiếu phải lớn hơn số lượng cấp!!!');
	  		else {
	  			alert('Cập nhật số lượng thành công');
	//  			alert('soLuong' + ycId);
	//	  			$('input:radio[name=ctvtId]').prop('checked',false);
	  			$('input[name=soLuongUpdate]').val('0');	
				$('#view-table-yc table tr #soLuong' + ycId).html(soLuong);
				showForm('add-yeu-cau-form','update-so-luong-form',false);
				$('input[name="yeuCau"]:checked').prop('checked',false);
		  		$('#danh-sach-vat-tu').show();
		  		$('#main-form').show();
	  		}
	  		
  		}
	});
}
function preCapVatTu() {
	var ycList = [];
	$.each($('input:checkbox[name=yeuCau]:checked'), function(){
		ycList.push($(this).val());
	})
	var str = ycList.join(', ');
	if (ycList.length == 0)
		alert('Bạn phải chọn 1 yêu cầu để cấp phát!');
	else if (ycList.length > 1)
		alert('Bạn phải chọn 1 yêu cầu để cấp phát!');
	else {
		
		$.ajax({
			url: getRoot() +  "/preCapVatTu.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "yeuCau": ycList[0]},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(objectList) {
		  		var yeuCau = objectList[0];
		  		var ctVatTu = objectList[1];
		  		$('#vtMaCap').html(ctVatTu.vatTu.vtMa);
		  		$('#vtTenCap').html(ctVatTu.vatTu.vtTen);
		  		$('#nsxTenCap').html(ctVatTu.noiSanXuat.nsxTen);
		  		$('#clTenCap').html(ctVatTu.chatLuong.clTen);
		  		$('#dvtCap').html(ctVatTu.vatTu.dvt.dvtTen);
		  		
		  		$('#soLuongTonCap').html(ctVatTu.soLuongTon);
		  		$('#soLuongThieu').html(yeuCau.ycSoLuong);
			  	$('#Cap-so-luong-form input[name=soLuongCap]').val(yeuCau.capSoLuong);
		    } 
		});  
		showForm('add-yeu-cau-form','cap-so-luong-form',true);
		$('#danh-sach-vat-tu').hide();
		$('#main-form').hide();
	}
}
function capVatTu() {
	var soLuong = $('input[name=soLuongCap]').val();
	count = 0;
	$.ajax({
		url: getRoot() +  "/capVatTu.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "soLuong": soLuong},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(objectList) {
	  		var ycVatTu = objectList[0];
	  		var ctVatTu = objectList[1];
	  		if (objectList == '-1')
	  			alert('Số lương cấp phát không hợp lệ! Số lượng cấp phát phải nhỏ hơn hoặc bằng tổng số lượng thiếu!!');
	  		else if (objectList == '-2')
	  			alert('Số lượng tồn không đủ để cấp. Vui lòng kiểm tra lại!!!');
	  		else if (objectList == '0')
	  			alert('Đã cấp đủ hàng!!');
	  		else {
	  			alert('Cấp phát vật tư thành công');
	  			$('input[name=soLuongCap]').val('0');
	  			$('#soLuongTon' + ycVatTu.ycId).html(ctVatTu.soLuongTon);
				$('#soLuong' + ycVatTu.ycId).html(ycVatTu.ycSoLuong);
				$('#soLuongCap' + ycVatTu.ycId).html(ycVatTu.capSoLuong);
				$('#view-search #soLuongTon' + ctVatTu.ctvtId).html(ctVatTu.soLuongTon);
//				$('#view-table-yc table tr td').has('input[name="yeuCau"]:checked').prop('checked',false);
				$('input[name="yeuCau"]:checked').prop('checked',false);
	  		}
	  		showForm('add-yeu-cau-form','cap-so-luong-form',false);
	  		$('input[name="yeuCau"]:checked').prop('checked',false);
	  		$('#danh-sach-vat-tu').show();
	  		$('#main-form').show();
  		}
	});
}
function searchCtVt(){
	var vtTen = '';
	var vtMa = '';
	var check = $('#checkTen:checked').val();
	if (check != null)
		vtTen = $('#searchName').val();
	else 
		vtMa = $('#searchName').val();
	$.ajax({
		url: getRoot() +  "/searchCtvtYc.html",	
	  	type: "GET",
		  	dateType: "JSON",
		  	data: { "vtMa": vtMa, "vtTen": vtTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
	  	
		  	success: function(objectList){
		  		
		  		var size = objectList[1];
		  		var ctvtList = objectList[0];
		  		var length = ctvtList.length;
		  		if(length > 0){
		  			
		  			$('#view-table-ds table .rowContent').remove();
					for(i = 0; i < length; i++ ) {
						var ctVatTu = ctvtList[i];
//						alert(ctVatTu.vatTu.vtTen);
						var style = '';
						if (i % 2 == 1)
							style = 'style=\"background : #CCFFFF;\"';
						var cells =   '<td \"style="text-align: center;"\">' + ctVatTu.vatTu.vtMa + '</td>'
									+ '<td >' + ctVatTu.vatTu.vtTen + '</td>'
									+ '<td >' + ctVatTu.noiSanXuat.nsxTen + '</td>'
									+ '<td >' + ctVatTu.chatLuong.clTen + '</td>'
									+ '<td \"style="text-align: center;"\">' + ctVatTu.vatTu.dvt.dvtTen + '</td>'
									+ '<td \"style="text-align: center;"\">' + ctVatTu.soLuongTon + '</td>'
									+ '<td \"style="text-align: center;"\"><input class=\"radio\"  type=\"radio\" id="a" name=\"ctvtId\" value=\"' + ctVatTu.ctvtId + '\" onchange=\"preAddSoLuong();\"> </td>';
						var row = '<tr ' +style + ' class=\"rowContent\">' + cells + '</tr>';
						$('#view-table-ds table tr:first').after(row);
					}
					
						var strPage = '';
						for (i = 0; i < size; i++) {
							strPage += '<input type=\"button\" class=\"page\" name="\page\" value=\"' + (i + 1) + '\" onclick=\"loadPageCtvtYc(' + i + ')\">  ';
							if (i == 10)
								break;
						}
						if (size > 10)
							strPage = '<input type=\"button\" name="\page\" class=\"pageMove\" style = \"width: 90px;\" value=\"<< Trước\" onclick= \"loadPageCtvtYc(\'Previous\');\")\">  ' +strPage + ' <input type=\"button\" name="\page\" class=\"page\" style = \"width: 60px;\" value=\">> Sau\" onclick= \"loadPageCtvtYc(\'Next\');\")\">'
						$('#paging').html(strPage);
		  		} else {
	  				alert("Không tìm thấy vật tư!");
	  			}
	  			
		  	}
		  	
	});
};
function loadPageCtvtYc(pageNumber) {
	
	var page = 0;
	var p = 0;
	if (pageNumber == 'Next') {
		var lastPage = document.getElementsByClassName('page')[9].value;
		p = (lastPage) / 5;
		page = p * 5;
	}
	else if (pageNumber == 'Previous') {
		var firstPage = document.getElementsByClassName('page')[0].value;
		
		p = (firstPage - 1) / 5;
		page =  (p * 5) - 1;
		alert(p);
		alert(page);
	}
	else {
		page = pageNumber;
	}
	$.ajax({
		url: getRoot() +  "/loadPageCtvtYc.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "pageNumber": page},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(objectList) {
	  		var size = objectList[1];
	  		var ctvtList = objectList[0];
	  		var length = ctvtList.length;
	  			$('#view-table-ds table .rowContent').remove();
				for(i = 0; i < length; i++ ) {
					var ctVatTu = ctvtList[i];
					var cells = '';
					var style = '';
					if (i % 2 == 0)
						style = 'style=\"background : #CCFFFF;\"';
					cells =   '<td>' + ctVatTu.vatTu.vtMa + '</td>'
								+ '<td>' + ctVatTu.vatTu.vtTen + '</td>'
								+ '<td>' + ctVatTu.noiSanXuat.nsxTen + '</td>'
								+ '<td>' + ctVatTu.chatLuong.clTen + '</td>'
								+ '<td>' + ctVatTu.vatTu.dvt.dvtTen + '</td>'
								+ '<td>' + ctVatTu.soLuongTon + '</td>'
								+ '<td><input class=\"radio\"  type=\"radio\" id="a" name=\"ctvtId\" value=\"' + ctVatTu.ctvtId + '\" onchange=\"preAddSoLuong();\"> </td>';
					var row = '<tr ' +style + 'class = \"rowContent\">' + cells + '</tr>';
					 $('#view-table-ds table tr:first').after(row);
				}
					var button = '';
					if(pageNumber == 'Next') {
						for (var i = 0; i < 10; i++) {
							var t = ((p -1) * 5 + i + 1);
							
							button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageCtvtYc(' + ((p -1)*5 + i)  +')\">&nbsp;';
							if (t > size)
								break;
						}
						button = '<input type=\"button\" class=\"pageMove\" style = \"width: 90px;\" value=\"<< Trước\" onclick= \"loadPageCtvtYc(\'Previous\')\">&nbsp;'  + button;
						if ((p + 1) * 5 < size)
							button += '<input type=\"button\" class=\"page\" style = \"width: 60px;\" value=\"Sau >>\" onclick= \"loadPageCtvtYc(\'Next\');\">';
						$('#paging').html(button);
					} else if (pageNumber == 'Previous'){
						if (p > 0)
							p = p -1;
						for (var i = 0; i < 10; i++)
							button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageCtvtYc(' + (p * 5 + i)  +')\">&nbsp;';
						
						button = button + '<input type=\"button\" value=\"Next>>\" class="\pageMove\" style = \"width: 60px;\" onclick= \"loadPageCtvtYc(\'Next\');\">';
						if (p >= 1)
							button = '<input type=\"button\" class="\pageMove\" style = \"width: 90px;\" value=\"<<Previous\" onclick= \"loadPageCtvtYc(\'Previous\')\">&nbsp;' + button;
						$('#paging').html(button);	
					}
	  	}
	});
};
/*
 * keypress event 
 */
$(document).ready(function(){
	$('#add-so-luong-form').keypress(function(e) {
	var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		addSoLuong();
	    return false;  
	  }
	}); 
});
//$(document).ready(function(){
//	$('#add-so-luong-form').keypress(function(e) {
//	var key = e.which;
//	 if(key == 13)  // the enter key code
//	  {
//		addSoLuong();
//	    return false;  
//	  }
//	}); 
//});
$(document).ready(function(){
	$('#update-so-luong-form').keypress(function(e) {
	var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		updateYc();
	    return false;  
	  }
	}); 
});
$(document).ready(function(){
	$('#cap-so-luong-form').keypress(function(e) {
	var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		capVatTu();
	    return false;  
	  }
	}); 
});
$(document).ready(function(){
	$('#searchForm').keypress(function(e) {
	var key = e.which;
	 if(key == 13)  // the enter key code
	  {
		searchCtVt();
	    return false;  
	  }
	}); 
});

/*
 * click event
 */
$(document).ready(function(){
	$('#pre-update-yc').click(function(){
		preUpdateYc();
		//showForm('update-yc-vat-tu','add-yeu-cau-form', true);
		return false;
	});
});	
$(document).ready(function(){
	$('#updateYc').click(function(){
		updateYc();
	});
});
// cap vap tu
$(document).ready(function(){
	$('#preCapVatTu').click(function(){
		preCapVatTu();
	});
});
$(document).ready(function(){
	$('#capVatTu').click(function(){
		capVatTu();
	});
});
$(document).ready(function(){
	$('#search-button').click(function(){
		searchCtVt();
	});
});

$(document).ready(function(){
	$('#thoatCapVt').click(function(){
		showForm('add-yeu-cau-form','cap-so-luong-form',false);
		$('input[name="yeuCau"]:checked').prop('checked',false);
		$('#danh-sach-vat-tu').show();
		$('#main-form').show();
	});
});

$(document).ready(function(){
	$('#thoatThemVt').click(function(){
		showForm('add-yeu-cau-form','add-so-luong-form',false);
		$('input[name="ctvtId"]:checked').prop('checked',false);
		$('#danh-sach-vat-tu').show();
		$('#main-form').show();
	});
});
$(document).ready(function(){
	$('#thoatSuaVt').click(function(){
		showForm('add-yeu-cau-form','update-so-luong-form',false);
		$('input[name="yeuCau"]:checked').prop('checked',false);
  		$('#danh-sach-vat-tu').show();
  		$('#main-form').show();
	});
});
/*
$(document).ready(function(){
	$('.page').click(function(){
		var lastPage = document.getElementsByClassName('page')[9].value;
//		alert(items[1].value);
		alert(lastPage);
		loadPageCtvtYc($(this).val(), lastPage);
	});
});
*/


