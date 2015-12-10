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
}
function hideForm(formId, check){
    if (check)
        document.getElementById(formId).style.display="block";
    else 
        document.getElementById(formId).style.display="none";
    var f = document.getElementById(formId), s, opacity;
    s = f.style;
    opacity = check? '10' : '100';
    s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
    s.filter = 'alpha(opacity='+opacity+')';
    for(var i=0; i<f.length; i++) f[i].disabled = check;
}
function showTime(formId){
    
        document.getElementById(formId).style.display="block";
    var f = document.getElementById(formId), s, opacity;
    s = f.style;
    opacity = '100';
    s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
    s.filter = 'alpha(opacity='+opacity+')';
    for(var i=0; i<f.length; i++) f[i].disabled = false;
}
function hideAddForm(){
	showForm('main-form', 'add-form', false);
	showForm('time-form', 'add-form', false);
	showForm('search-form', 'add-form', false);
}
function hideUpdateForm(){
	showForm('main-form', 'update-form', false);
	showForm('time-form', 'update-form', false);
	showForm('search-form', 'add-form', false);
}
function checkAdd(){
	var cvSo = $('#add-form input:text[name=cvSo]').val();
	var ngayNhan = $('#add-form input:text[name=ngayNhan]').val();
	var mucDich = $('#add-form select[name=mucDich]').val();
	var donVi = $('#add-form select[name=donVi]').val();
	var file = $('#add-form input[name=file]').val();
	var moTa = $('#add-form textarea[name=moTa]').val();

//	if(cvSo == ''){
//		$('#requireSoCv').html('Vui lòng nhập số công văn');
//		return false;
//	}
	if(ngayNhan == ''){
		$('#requireNgayNhan').html('Vui lòng chọn ngày nhận công văn');
		return false;
	}
	else if(mucDich == null){
		$('#requireMucDich').html('Vui lòng chọn mục đích');
		return false;
	}
	else if(donVi == null){
		$('#requireDonVi').html('Vui lòng chọn đơn vị');
		return false;
	}
	else if(file == ''){
		$('#requireFile').html('Vui lòng đính kèm file');
		return false;
	}
	else if(moTa == ''){
		$('#requireMoTa').html('Vui lòng nhập nội dung');
		return false;
	}
	if(cvSo.length > 10){
		$('#requireSoCv').html('Số công văn đến phải không được quá 10 ký tự');
		return false;
	}
	else if(ngayNhan == ''){
		$('#requireNgayNhan').html('Vui lòng chọn ngày nhận công văn');
		return false;
	}
	else if(mucDich == null){
		$('#requireMucDich').html('Vui lòng chọn mục đích');
		return false;
	}
	else if(donVi == null){
		$('#requireDonVi').html('Vui lòng chọn đơn vị');
		return false;
	}
	else if(file == ''){
		$('#requireFile').html('Vui lòng đính kèm file');
		return false;
	}
	else if(moTa == ''){
		$('#requireMoTa').html('Vui lòng nhập nội dung');
		return false;
	}
//	return true;
	addCongVan();
}
function changeSoCv(){
	$('#requireSoCv').html('');
} 	
function changeNgayNhan(){
	$('#requireNgayNhan').html('');
} 	
function changeMucDich(){
	$('#requireMucDich').html('');
} 	
function changeDonVi(){
	$('#requireDonVi').html('');
} 	
function changeFile(){
	$('#requireFile').html('');
} 	
function changeMoTa(){
	$('#requireMoTa').html('');
} 	

function changeNgayNhanUp(){
	$('#requireNgayNhanUp').html('');
} 	
function changeMucDichUp(){
	$('#requireMucDichUp').html('');
} 	
function changeDonViUp(){
	$('#requireDonViUp').html('');
} 	
function changeFileUp(){
	$('#requireFileUp').html('');
} 	
//function changeTrangThaiUp(){
//	$('#requireTrangThaiUp').html('');
//}
function checkUp(){
	var ngayNhan = $('#update-form input:text[name=ngayNhanUpdate]').val();
	var mucDich = $('#update-form select[name=mucDichUpdate]').val();
	var donVi = $('#update-form select[name=donViUpdate]').val();
	var file = $('#update-form input[name=fileUpdate]').val();
	var trangThai = $('#update-form radio[name=trangThaiUpdate]').val();

	if(ngayNhan == ''){
		$('#requireNgayNhanUp').html('Vui lòng chọn ngày nhận công văn');
		return false;
	}
	else if(mucDich == null){
		$('#requireMucDichUp').html('Vui lòng chọn mục đích');
		return false;
	}
	else if(donVi == null){
		$('#requireDonViUp').html('Vui lòng chọn đơn vị');
		return false;
	}
	else if(file == ''){
		$('#requireFileUp').html('Vui lòng đính kèm file');
		return false;
	}
	else if(trangThai == ''){
		$('#requireTrangThaiUp').html('Vui lòng cập nhật trạng thái');
		return false;
	}
//	return true;
	updateCongVan();
}
function checkCongVan() {
	var congVanList = [];
	$.each($("input[name='cvId']:checked"), function(){            
		congVanList.push($(this).val());
    });
	if (congVanList.length == 1)
		return true;
	if (congVanList.length == 0)
		alert('Bạn phải chọn 1 công văn để cập nhật yêu cầu!!');
	else if (congVanList.length > 1)
		alert('Bạn chỉ được chọn 1 công văn để  cập nhật yêu cầu!!');
		return false;
}


function confirmDelete(){
	var cvId = $('input:checkbox[name=cvId]:checked').val();
	var congVanList = [];
	$.each($("input[name='cvId']:checked"), function(){            
		congVanList.push($(this).val());
    });
	var str = congVanList.join(', ');
	if (confirm('Bạn có chắc xóa cong van?'))
		deleteCv(str);
}
function addCongVan() {
	var cvSo = $('#add-form input:text[name=cvSo]').val();
	var form = new FormData(document.getElementById('add-form'));
	$.ajax({
		url: getRoot() +  "/addCongVanInfo.html",	
		data: form,
		  dataType: 'text',
		  processData: false,
		  contentType: false,
		  
		  type: 'POST',
	  	
	  	success: function(result) {
	  		if (result == "exist")
	  			alert('Công văn đã tồn tại');
	  		else if (result == "error") 
	  			alert('Có lỗi khi thêm công văn');
	  		else if (result == "file error") {
	  			alert('Tệp đính kèm không hợp lệ. Vui lòng kiểm tra và cập nhật lại!!!');
	  			window.location.assign("cong-van-error.jsp")
	  		}
	  		else {
	  			var objectList = JSON.parse(result);
		  		loadAddCongVan(objectList, "add");
		  		$('.scroll_content table tr:last').remove();
		  		$('#add-form input:text[name=cvSo]').val('');
		  		$('#add-form input:text[name=ngayNhan]').val(new Date());
		  		$('#add-form select[name=mucDich]').val('');
		  		$('#add-form select[name=donVi]').val('');
		  		$('#add-form input[name=file]').val('');
		  		$('#add-form textarea[name=trichYeu]').val('');
		  		$('#add-form textarea[name=moTa]').val('');
		  		$('#add-form textarea[name=butPhe]').val('');
		  		hideAddForm();
		  		alert('Thêm công văn mới thành công');
		  		window.location.reload();
		  		
	  		}
	  		
	  	}
	});
	
	
}
function updateCongVan() {
	var form = new FormData(document.getElementById('update-form'));
	$.ajax({
		url: getRoot() +  "/updateCongVanInfo.html",	
		data: form,
		  dataType: 'text',
		  processData: false,
		  contentType: false,
		  
		  type: 'POST',
	  	
	  	success: function(result) {
	  		if (result == "error") 
	  			alert('Có lỗi khi sửa công văn');
	  		else if (result == "file error") {
	  			alert('Tệp đính kèm không hợp lệ. Vui lòng kiểm tra và cập nhật lại!!!');
	  			window.location.assign("cong-van-error.jsp")
	  		}
	  		else {
	  			var objectList = JSON.parse(result);
		  		loadAddCongVan(objectList, "update");
		  		hideUpdateForm();
		  		$('#scroll_content table tr').has('input[name="cvId"]:checked').remove();
		  		alert('Sửa đổi công văn thành công');
		  		window.location.reload();
	  		}
	  		
	  	}
	});
	
	
}
function loadAddCongVan(objectList, action) {
	var congVan = objectList[0];
	var file = objectList[1];
	var style = '';
	var tables = '';
	var cvNgayNhan = parseDate(congVan.cvNgayNhan);
	var cvNgayDi = parseDate(congVan.cvNgayDi);
	if (countAdd % 2 == 1)
		style += 'style = \"background : #CCFFFF; width: 100%; font-size: 18px;\"';
	else
		style += 'style = \"background : #FFFFFF; width: 100%; font-size: 18px;\"';
//	style += ' font-size: 16px; width: 1224px;\"';
	tables += '<td><table class=\"tableContent\" ' + style + ' class=\"border-congvan\">'
				+ '<tr >';
				if(chucDanhMa == vanThuMa || chucDanhMa == adminMa) {
				tables += '<td class=\"column-check\" rowspan=\"9\" style=\"margin-right: 30px;\">'
				+ 'Chọn <input title=\"Click để chọn công văn\" type=\"checkbox\" name=\"cvId\" value=\"' + congVan.cvId + '\">'
				+ '</td>';
				}
				tables += '<td class=\"left-column-soden\" style=\"font-weight: bold;\">Số nhận: &nbsp;&nbsp;</td>'
				+ '<td class=\"column-so-den\" style=\"text-align: left\">' + congVan.soDen + '</td>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Ngày nhận: &nbsp;&nbsp;</td>'
				+ '<td colspan =\"3\" class=\"column-date\" style=\"text-align: left;color:blue;\">' + cvNgayNhan + '</td>'
//				+ '<td colspan=\"1\" style=\"font-weight: bold;\">Trạng thái</td>'
//				+ '<td colspan=\"1\" style=\"color:red;font-weight: bold;font-style: oblique;\">' + congVan.trangThai.ttTen + '</td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td class=\"left-column-socv\" style=\"font-weight: bold;\">Số công văn đến: &nbsp;&nbsp;</td>'
				+ '<td colspan=\"3\" class=\"column-socv\" style=\"text-align: left;color:red;\">' + congVan.cvSo + '</td>'
				+ '<th  class=\"left-column-ngdi\" style=\"font-weight: bold;\">Ngày của công văn đến:&nbsp;&nbsp;</th>'
				+ '<td class=\"column-date\" style=\"text-align: left;color:blue;\">' + cvNgayDi+ '</td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Mục đích: &nbsp;&nbsp;</td>'
				+ '<td class=\"column-color\"  colspan=\"3\"  style=\"text-align: left\">' + congVan.mucDich.mdTen + '</td>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi gửi: &nbsp;&nbsp;</td>'
				+ '<td class=\"column-color\" style=\"text-align: left\">' +  congVan.donVi.dvTen + '</td>'
				
				+ '</tr>'
				+ '<tr>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nội dung công tác: &nbsp;&nbsp;</td>'
				+ '<td class=\"column-color\" colspan=\"6\" style=\"text-align: left;font-weight: bold;\">' +  congVan.trichYeu + '</td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Bút phê: &nbsp;&nbsp;</td>'
				+ '<td class=\"column-color\" colspan=\"6\">' +  congVan.butPhe + '</td>'
				+ '</tr>';
//				+ '<tr>'
//				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi GQ chính</td>'
//				+ '<td class=\"column-color\" colspan=\"3\">' + congVan.donVi.dvTen + '</td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi GQ chính</td>'
				+ '<td class=\"column-color\" colspan=\"3\">' + congVan.donVi.dvTen + '</td>'
//				+ '<td colspan=\"3\" style=\"float: right;\">'
//				+ '<button  class=\"button-chia-se\" id=\"chiaSe\" type=\"button\" style=\"width: 170px; height: 30px;\"' 
//				+ '  onclick=\"location.href=\'/QLVatTuYeuCau/cscvManage.html?action=chiaSeCv&congVan=' + congVan.cvId + '\'\">'
//				+ '<i class=\"fa fa-spinner\"></i>&nbsp;&nbsp;Chia sẻ công văn'
//				+ '</button>'
//				+ '</td>'
				+ '<tr>';
			tables += '<tr>';	
			if (chucDanhMa == truongPhongMa || chucDanhMa == vanThuMa || chucDanhMa == adminMa) {
				tables += '<td class=\"left-column-first\" style=\"font-weight: bold;\">Người xử lý</td>'
				+ '<td class=\"column-color\" colspan=\"3\">' + '</td>';

			}
			tables += '</tr>';
			
	  		var path = file.diaChi;
			var index = path.lastIndexOf("/");
			var index2 = path.lastIndexOf("-");
			var index3 = path.lastIndexOf(".");
			fileName = path.substring(index + 1, index2);
			if (index3 != -1)
				fileName += path.substring(index3);
		tables	+= '</tr>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Xem công văn: </td>'
				+ '<td colspan=\"5\">'
				+ '<a  target=\"_black\" href=\"' + getRoot() + '/downloadFileMn.html' + '?action=download&file=' + congVan.cvId + '\">'
				+ '<div class=\"mo-ta\">' + fileName + '</div>'
				+ '</a> '
				+ '</td>'
				
				+ '</tr>'
				+ '<tr>'
				+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Ghi chú</td>'
				+ '<td colspan = \"5\">' + file.moTa + '</td>'
				+ '</tr>';
				
		tables += '<tr>' 
			+ '<th style="text-align: left"><label for=\"TT\">Trạng thái</label></th>'
			+ '<td style=\"text-align: left; padding-left: 10px;\" colspan = \"3\" id =\"' + congVan.cvId  + 'ttMaCongVan\">';
		if (chucDanhMa == truongPhongMa || chucDanhMa == vanThuMa || chucDanhMa == adminMa) {
			tables += '<input type=\"radio\"' + ('CGQ'== congVan.trangThai.ttMa ? ' checked ' : '') + 'name=' + congVan.cvId  + ' value=\"' + congVan.cvId +'#' + 'CGQ\"' + ' class=\"ttMaUpdate\" >'; //onchange=\"changeTrangThai()\"
			tables += '&nbsp;<label for=\"' + congVan.cvId + '#CGQ\">Chưa giải quyết</label>&nbsp;&nbsp;&nbsp';
			tables += '<input type=\"radio\"' + ('DGQ'==congVan.trangThai.ttMa ? ' checked ' : '') + 'name=' + congVan.cvId + ' value=\"' + congVan.cvId +'#' + 'DGQ\"' + 'DGQ\" class=\"ttMaUpdate\">';
			tables += '&nbsp;<label for=\"' + congVan.cvId + '#DGQ\">Còn thiếu hàng</label>&nbsp;&nbsp;&nbsp';
			tables += '<input type=\"radio\"' + ('DaGQ'== congVan.trangThai.ttMa ? ' checked ' : '') + 'name=' + congVan.cvId + ' value=\"' + congVan.cvId +'#' + 'DaGQ\"  class=\"ttMaUpdate\">';
			tables += '&nbsp;<label for=\"' + congVan.cvId + '#DaGQ\">Đã cấp đủ hàng</label>&nbsp;&nbsp;&nbsp</td>';
			tables += '<td colspan=\"2\" style=\"float: right;\">'
				+ '<button  class=\"button\" id=\"chiaSe\" type=\"button\" style=\"width: 170px; height: 30px;\"' 
				+ '  onclick=\"location.href=\'/QLVatTuYeuCau/cscvManage.html?action=chiaSeCv&congVan=' + congVan.cvId + '\'\">'
				+ '<i class=\"fa fa-spinner\"></i>&nbsp;&nbsp;Chia sẻ công văn'
				+ '</button>'
				+ '</td>';
		}
		tables += '</tr>'
				+ '</table>'
				+'<br>'
				+'<hr></td>';
		if (action == "add")
			$('#scroll_content table tr:first').before('<tr>' + tables + '</tr>');
		else if (action == "update")
			$('#scroll_content table tr').has('input[name="cvId"]:checked').html(tables);
//		$('#scroll_content').after(tables);
}
function checkUpdate() {
	var congVanList = [];
	$.each($("input[name='cvId']:checked"), function(){            
		congVanList.push($(this).val());
    });
	
	if (congVanList.length == 0)
		alert('Bạn phải chọn 1 công văn để sửa đổi!!');
	else if (congVanList.length > 1)
		alert('Bạn chỉ được chọn 1 công văn để  sửa đổi!!');
	else if (congVanList.length == 1)
		preUpdateCv(congVanList[0]);
}
function preUpdateCv(cv) {
	$.ajax({
		url: getRoot() +  "/preUpdateCv.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "congVan": cv},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(objectList) {
//			$('table').has('input[name="cvId"]:checked').remove();
//			alert("Cong van da bi xoa");
	  		//alert(congVan.trangThai.ttMa);
	  		var congVan = objectList[0];
	  		
			$('#update-form input:hidden[name=cvId]').val(congVan.cvId);
	  		$('#update-form input:text[name=soDen]').val(congVan.soDen);
	  		$('#update-form input:text[name=cvSo]').val(congVan.cvSo);
	  		$('#update-form input:text[name=ngayGoiUpdate]').val(congVan.cvNgayGoi);
	  		$('#update-form input:text[name=ngayNhanUpdate]').val(congVan.cvNgayNhan);
	  		$('#update-form select[name=donViUpdate] option[value=' + congVan.donVi.dvMa+']').prop('selected',true);
//	  		$('#dvtUp option[value='+vt.dvt.dvtTen+']').prop('selected',true);
//	  		$('#update-form input[name=file]').val(fileName);
	  		$('#update-form select[name=mucDichUpdate] option[value=' + congVan.mucDich.mdMa+']').prop('selected',true);
	  		$('#update-form textarea[name=trichYeuUpdate]').val(congVan.trichYeu);
	  		$('#update-form textarea[name=butPheUpdate]').val(congVan.butPhe);
//	  		$('#update-form input:radio[name=ttMaUpdate][value='+congVan.trangThai.ttMa+']').prop('checked',true);
//	  		$('#linkCv').html('<a href=\"' + path + '\" target=\"_blank\">Xem công văn</a>')
	  		var file = objectList[1];
	  		var fileNameFull = '';
	  		if (file != null) {
		  		var path = file.diaChi;
		  		var index = path.lastIndexOf("/");
				var index2 = path.lastIndexOf("-");
				var index3 = path.lastIndexOf(".");
				fileNameFull = path.substring(index + 1, index2);
				if (index3 != -1)
					fileNameFull += path.substring(index3);
				$('#linkCv').attr('href', getRoot() + 'downloadFile.html?action=download&file=' + congVan.cvId);
				$('#update-form textarea[name=moTa]').val(file.moTa);
	  		} else {
	  			$('#linkCv').attr('href', '');
	  			$('#update-form textarea[name=moTa]').val('Không có ghi chú');
	  		}
	  		$('#linkCv').html(fileNameFull);
	  		$('#update-form textarea[name=butPheUpdate]').val(congVan.butPhe);
	  		
	  		showForm('main-form', 'update-form', true);
	  		showForm('time-form', 'update-form', true);
	  		showForm('search-form', 'update-form', true);
	    }
	});  
}
function deleteCv(cvId) {
	$.ajax({
		url: getRoot() +  "/deleteCv.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "cvId": cvId},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function() {
			$('#scroll_content table tr').has('input[name="cvId"]:checked').remove();
			alert("Cong van da bi xoa");
	    } 
	});  
}	
function loadDataCv() {
//	showForm('main-form','add-form', true);
	showForm('main-form','add-form', true);
	hideForm('search-form', true);
	hideForm('time-form', true);
}
function chiaSeCv() {
//	var cvId = $('input:checkbox[name=cvId]:checked').val();
	
		$.ajax({
			url: getRoot() +  "/chiaSeCv.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "cvId": cvId},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		   
		});  
}
function loadByYear(year, checked) {
//	var root = getRoot();
//	var root = ${pageContext.request.contextPath};
	$.ajax({
		url:  getRoot() + "/loadByYear.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year, "checked": checked},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	// load month 
	    	var monthList = objectList[2];
	    	var content = '';
	    	var length = monthList.length;
	    	var monthLi = '';
	    	var monthOl = '';
	    	for(var i = 0; i< length; i++) {
	    		monthLi += 	'<li id = \"month' + monthList[i] + '\">' 
							+ '<label for=\"y' + year + 'm' + monthList[i] + '\">' + 'Tháng ' + monthList[i]  + '</label>' 
							+' <input type="checkbox" class=\"month\" id=\"y' + year+ 'm' + monthList[i] +  '\" value = \"'+ year + '#' + monthList[i]  + '\"  ' + '\"/>' 
							+ '<ol></ol> </li>'; 
	    	}
	    	var yearLi = '';
	    	var s = '  <script type=\"text/javascript\">'
	    		+ '$(\'.month\').bind(\'change\', function(){'
	    		+ 'var checked = $( this ).is( ":checked" );'
	    		+ 'var str = $(this).val();'
	    		+ 'var temp = str.split("#");'
	    		+ 'loadByMonth(temp[0], temp[1], checked);'
	    		+ '}); </script> ';
	    	$('#year'+year + ' ol').html(monthLi + s);
	    	// load congVan
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[3];
	    	var nguoiXlCongVan = objectList[4];
	  		var vaiTroList = objectList[5];
	  		var vtCongVanList = objectList[6];
	    	loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList);
	    	loadPageNumber(0, '',size);
	    } 
	});  
}
function loadByMonth(year, month, checked) {
//	var checked = $( ".month" ).is( ":checked" );
	$.ajax({
		url: getRoot() +  "/loadByMonth.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year, "month": month, "checked": checked },
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var content = '';
	    	var dateList = objectList[2];
	    	var length = dateList.length;
	    	var dateLi = '';
	    	var dateOl = '';
	    	for(var i = 0; i< length; i++) {
	    		dateLi += 	'<li id = \"date' + dateList[i] + '\">' 
							+ '<label for=\"y' + year + 'm'  + month + 'd' + dateList[i] + '\">' + 'Ngày ' + dateList[i]  + '</label>' 
//							+' <input type="button" class=\"date\" id=\"y' + year + 'm'  + month + 'd' + dateList[i] +'\" value =\"' + year + '#' + month  + '#' + dateList[i] + '\"' + '\"/>'
							+' <input type="checkbox" class=\"date\" id=\"y' + year+ 'm' + month + 'd' + dateList[i] +  '\" value = \"'+ year + '#' + month  + '#' + dateList[i]  + '\"  ' + '\"/>'
							+ '</li>'; 
	    	}
	    	var s = '  <script type=\"text/javascript\">'
	    		+ '$(\'.date\').bind(\'change\', function(){'
	    		+ 'var checked = $( this ).is( ":checked" );'
	    		+ 'var str = $(this).val();'
	    		+ 'var temp = str.split("#");'
	    		+ 'loadByDate(temp[0], temp[1], temp[2], checked);'
	    		+ '}); </script> ';
	    	$('#month'+month + ' ol').html(dateLi + s);
	    	// load Cong van
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[3];
	    	var nguoiXlCongVan = objectList[4];
	  		var vaiTroList = objectList[5];
	  		var vtCongVanList = objectList[6];
	    	loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList);
	    	loadPageNumber(0, '',size);
	    } 
	});  
}
function loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList) {
//	$('.tableContent').remove();
	var length = congVanList.length;
	var tables = '';
	if(length > 0) {
		for (var i = 0; i < length; i++) {
			var style = 'style=\"';
			var congVan = congVanList[i];
			
			var cvNgayNhan = parseDate(congVan.cvNgayNhan);
//  			var dateTemp = congVan.cvNgayNhan.split('\\-');
//  			var date = dateTemp[2] + '/' + dateTemp[1] + '/' + dateTemp[0];   
//  			alert(date);
//			alert(cvNgayNhan);
//			alert(congVan.cvNgayNhan);
			var cvNgayDi = parseDate(congVan.cvNgayDi);   
			var file = fileList[i];
			if (i % 2 == 1)
				style += 'background : #CCFFFF; ';
			else
				style += 'background : #FFFFFF; ';
			style += ' font-size: 16px; width: 100%;\"';
			tables +=     '<tr><td><table class=\"tableContent\" ' + style + ' class=\"border-congvan\">'
						+ '<tr >';
						if(chucDanhMa == vanThuMa || chucDanhMa == adminMa || chucDanhMa == thuKyMa) {
							var row = '';
							if (chucDanhMa == adminMa)
								row = 11;
							else 
								row = 10;
						tables += '<td class=\"column-check\" rowspan=\"' + row + '\" style=\"margin-right: 30px;\">'
						+ 'Chọn <input title=\"Click để chọn công văn\" type=\"checkbox\" name=\"cvId\" value=\"' + congVan.cvId + '\">'
						+ '</td>';
						}
						tables += '<td class=\"left-column-soden\" style=\"font-weight: bold;\">Số nhận: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-so-den\" style=\"text-align: left\">' + congVan.soDen + '</td>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Ngày nhận: &nbsp;&nbsp;</td>'
						+ '<td colspan =\"3\" class=\"column-date\" style=\"text-align: left;color:blue;\">' + cvNgayNhan + '</td>'
//						+ '<td colspan=\"1\" style=\"font-weight: bold;\">Trạng thái</td>'
//						+ '<td colspan=\"1\" style=\"color:red;font-weight: bold;font-style: oblique;\">' + congVan.trangThai.ttTen + '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-socv\" style=\"font-weight: bold;\">Số công văn đến: &nbsp;&nbsp;</td>'
						+ '<td colspan=\"3\" class=\"column-socv\" style=\"text-align: left;color:red;\">' + congVan.cvSo + '</td>'
						+ '<th  class=\"left-column-ngdi\" style=\"font-weight: bold;\">Ngày của công văn đến:&nbsp;&nbsp;</th>'
						+ '<td class=\"column-date\" style=\"text-align: left;color:blue;\">' + cvNgayDi+ '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Mục đích: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\"  colspan=\"3\"  style=\"text-align: left\">' + congVan.mucDich.mdTen + '</td>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi gửi: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" style=\"text-align: left\">' +  congVan.donVi.dvTen + '</td>'
						
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nội dung công tác: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" colspan=\"6\" style=\"text-align: left;font-weight: bold;\">' +  congVan.trichYeu + '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Bút phê: &nbsp;&nbsp;</td>'
						+ '<td class=\"column-color\" colspan=\"6\">' +  congVan.butPhe + '</td>'
						+ '</tr>';
//						+ '<tr>'
//						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi GQ chính</td>'
//						+ '<td class=\"column-color\" colspan=\"3\">' + congVan.donVi.dvTen + '</td>'
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Nơi GQ chính</td>'
						+ '<td class=\"column-color\" colspan=\"3\">' + congVan.donVi.dvTen + '</td>'
//						+ '<td colspan=\"3\" style=\"float: right;\">'
//						+ '<button  class=\"button-chia-se\" id=\"chiaSe\" type=\"button\" style=\"width: 170px; height: 30px;\"' 
//						+ '  onclick=\"location.href=\'/QLVatTuYeuCau/cscvManage.html?action=chiaSeCv&congVan=' + congVan.cvId + '\'\">'
//						+ '<i class=\"fa fa-spinner\"></i>&nbsp;&nbsp;Chia sẻ công văn'
//						+ '</button>'
//						+ '</td>'
						+ '<tr>';
					if (chucDanhMa == truongPhongMa || chucDanhMa == vanThuMa || chucDanhMa == adminMa || chucDanhMa == phoPhongMa) {
						var cellNguoiXl = '';
						if (nguoiXlCongVan.length > 0) {
							var nguoiXlList = nguoiXlCongVan[i];
							cellNguoiXl = nguoiXlList.join(', ');
//							for (var j = 0; j < nguoiXlList.length; j++) {
//								cellNguoiXl += nguoiXlList[j];
//							}
						}
						tables += '<tr><td class=\"left-column-first\" style=\"font-weight: bold;\">Người xử lý</td>'
						+ '<td class=\"column-color\" colspan=\"3\">' + cellNguoiXl + '</td></tr>';
							

					} 
					if (chucDanhMa == nhanVienMa || chucDanhMa == phoPhongMa || chucDanhMa == adminMa) { 
						tables += '<tr>';
						if (chucDanhMa != adminMa) {
							var cellVaiTro = '';
							tables += '<td class=\"left-column-first\" style=\"font-weight: bold;\">Vai trò</td>'
								+ '<td class=\"column-color\" colspan=\"5\">';
							tables += '<table>';
							if (vaiTroList.length > 0) {
								var vaiTro = vaiTroList[i];  
								var vtCongvan = vtCongVanList[i];  
	//							cellVaiTro = vtCongVanList.vtTen.join(', ');
								for (var j = 0; j < vtCongvan.length; j++) {
									var vtcv = vtCongvan[j];
									var vt = vaiTro[j];
									var capPhat = false;
									if (vtcv.vtId == capVatTuId )
										capPhat = true;
									tables += '<tr>';
									tables += '<td>' + vt.vtTen + ': &nbsp;&nbsp;&nbsp;</td>';
									tables += '<td><input type=\"radio\"' + ('CGQ'== vtcv.trangThai.ttMa ? ' checked ' : '') + 'name=\"' + msnv + '#' + vtcv.cvId  + '#' + vtcv.vtId + '\"' + ' value=\"' + msnv + '#' + vtcv.cvId  + '#' + vtcv.vtId  +'#' + 'CGQ\"' + ' class=\"ttMaVtUpdate\" >'; 
									tables += '&nbsp;<label for=\"' + vtcv.cvId + '#CGQ\">Chưa giải quyết</label>&nbsp;&nbsp;&nbsp</td>';
									tables += '<td><input type=\"radio\"' + ('DGQ'==vtcv.trangThai.ttMa ? ' checked ' : '') + 'name=\"' + msnv + '#' + vtcv.cvId  + '#' + vtcv.vtId + '\"' + ' value=\"' + msnv + '#' + vtcv.cvId  + '#' + vtcv.vtId  + 'DGQ\"' + ' class=\"ttMaVtUpdate\">';
									tables += '&nbsp;<label for=\"' + vtcv.cvId + '#DGQ\">Còn thiếu hàng</label>&nbsp;&nbsp;&nbsp</td>';
									tables += '<td><input type=\"radio\"' + ('DaGQ'==vtcv.trangThai.ttMa ? ' checked ' : '') + 'name=\"' + msnv + '#' + vtcv.cvId  + '#' + vtcv.vtId + '\"' + ' value=\"' + msnv + '#' + vtcv.cvId  + '#' + vtcv.vtId  +'#' + 'DaGQ\"  class=\"ttMaVtUpdate\">';
									tables += '&nbsp;<label for=\"' + vtcv.cvId + '#DaGQ\">Đã cấp đủ hàng</label>&nbsp;&nbsp;&nbsp</td>';
									tables += '<div id="requireTrangThaiUp" style="color: red"></div>';
									
									tables += '<tr>';
								}
							}
							
							tables += '</table></td>';
						} else {
							tables += '<th style=\"text-align: left;\">Vai trò</th><td colspan = \"5\"></td>';
						}	
							if (capPhat == true || chucDanhMa == adminMa) {
								tables += '<td colspan=\"1\" style=\"float: right;\">' 
								+ '<button class=\"button\" type=\"button\" style=\"width: 200px; height: 30px;\"' 
								+ '  onclick=\"location.href=\'/QLVatTuYeuCau/ycvtManage.html?cvId=' + congVan.cvId + '\'\">'
								+ '<i class="fa fa-spinner"></i>&nbsp;&nbsp;Cập nhật vật tư thiếu'
								+ '</button>'
								+ '</td>';
							}
						
						tables += '</tr>';
//						var s = '  <script type=\"text/javascript\">$(\'.ttMaVtUpdate\').bind(\'change\', function(){'
//							+ ' var trangThai = $(this).val(); '
//							+ ' changeTrangThaiVt(trangThai) ;'
//						+ '}); </script>';
//						tables += s;
						
						
							
						
					}
					var fileName = '';
					var moTa = '';
					if (file != null) {
				  		var path = file.diaChi;
						var index = path.lastIndexOf("/");
						var index2 = path.lastIndexOf("-");
						var index3 = path.lastIndexOf(".");
						fileName = path.substring(index + 1, index2);
						if (index3 != -1)
							fileName += path.substring(index3);
						moTa = file.moTa;
					} else {
						fileName = 'Không tồn tại tệp';
					}
				tables	+= '</tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Xem công văn: </td>'
						+ '<td colspan=\"5\">'
						+ '<a  target=\"_black\" href=\"' + getRoot() + '/downloadFileMn.html' + '?action=download&file=' + congVan.cvId + '\">'
						+ '<div class=\"mo-ta\">' + fileName + '</div>'
						+ '</a> '
						+ '</td>'
						
						+ '</tr>'
						+ '<tr>'
						+ '<td class=\"left-column-first\" style=\"font-weight: bold;\">Ghi chú</td>'
						+ '<td colspan = \"5\">' + moTa + '</td>'
						+ '</tr>';
						
				tables += '<tr>' 
					+ '<th style="text-align: left"><label for=\"TT\">Trạng thái</label></th>'
					+ '<td style=\"text-align: left; padding-left: 10px;\" colspan = \"5\" id =\"' + congVan.cvId  + 'ttMaCongVan\">';
				if (chucDanhMa == truongPhongMa || chucDanhMa == vanThuMa || chucDanhMa == adminMa || chucDanhMa == phoPhongMa) {
					tables += '<input type=\"radio\"' + ('CGQ'== congVan.trangThai.ttMa ? ' checked ' : '') + 'name=' + congVan.cvId  + ' value=\"' + congVan.cvId +'#' + 'CGQ\"' + ' class=\"ttMaUpdate\" >'; //onchange=\"changeTrangThai()\"
					tables += '&nbsp;<label for=\"' + congVan.cvId + '#CGQ\">Chưa giải quyết</label>&nbsp;&nbsp;&nbsp';
					tables += '<input type=\"radio\"' + ('DGQ'==congVan.trangThai.ttMa ? ' checked ' : '') + 'name=' + congVan.cvId + ' value=\"' + congVan.cvId +'#' + 'DGQ\"' + 'DGQ\" class=\"ttMaUpdate\">';
					tables += '&nbsp;<label for=\"' + congVan.cvId + '#DGQ\">Còn thiếu hàng</label>&nbsp;&nbsp;&nbsp';
					tables += '<input type=\"radio\"' + ('DaGQ'== congVan.trangThai.ttMa ? ' checked ' : '') + 'name=' + congVan.cvId + ' value=\"' + congVan.cvId +'#' + 'DaGQ\"  class=\"ttMaUpdate\">';
					tables += '&nbsp;<label for=\"' + congVan.cvId + '#DaGQ\">Đã cấp đủ hàng</label>&nbsp;&nbsp;&nbsp</td>';
					tables += '<td colspan=\"1\" style=\"float: right;\">'
						+ '<button  class=\"button\" id=\"chiaSe\" type=\"button\" style=\"width: 200px; height: 30px;\"' 
						+ '  onclick=\"location.href=\'/QLVatTuYeuCau/cscvManage.html?action=chiaSeCv&congVan=' + congVan.cvId + '\'\">'
						+ '<i class=\"fa fa-spinner\"></i>&nbsp;&nbsp;Chia sẻ công văn'
						+ '</button>'
						+ '</td>';
				}
				else
					tables += congVan.trangThai.ttTen;
					tables += '</td>'
					+ '</tr>'
					+ '</table>'
					+'<br>'
					+'<hr></td></tr>';
			
		}
//		var s = '   <script type="text/javascript">$(\'.ttMaUpdate\').change(function(){ '
//			+ ' var trangThai = $(this).val(); '
//			+ ' alert(trangThai); '
//			+ ' $.ajax({ '
//			+ ' url: getRoot() +  \"/changeTrangThai.html\", type: \"GET\", dateType: \"JSON\", data: { \"trangThai\": trangThai}, contentType: \'application/json\', mimeType: \'application/json\','
//			  	
//			+ 'success: function(status) {'
//			+ ' if (status == \"success\")'
//			+ ' alert(\"Bạn đã thay đổi trạng thái của công văn thành công!!!\"); '
//			+ ' else '
//			+ 'alert(\"Bạn không thể thay đổi trạng thái của công văn!!!\");'
//			+ '};});});	</script>';
		
//		var s = '';
	} else{
		alert('Không tồn tại công văn');
	}
	var s = '  <script type=\"text/javascript\">'
		
		+ '$(\'.ttMaUpdate\').bind(\'change\', function(){'
		+ 'var trangThaiCv = $(this).val(); '
		+ ' changeTrangThaiCv(trangThaiCv) ;'
		+ '});'
		+ '$(\'.month\').bind(\'change\', function(){'
		+ 'var checked = $( this ).is( ":checked" );'
		+ 'var str = $(this).val();'
		+ 'alert(str);'
		+ 'var temp = str.split("#");'
		+ 'alert(temp[0]);'
		+ 'loadByYear(temp[0], temp[1], monthchecked);'
	+ '});'
		+ ' $(\'.ttMaVtUpdate\').bind(\'change\', function(){'
			+ ' var trangThaiVt = $(this).val(); '
			+ ' changeTrangThaiVt(trangThaiVt) ;'
	+ '}); </script> ';
	$('.scroll_content table').html(tables + s);
	if (check == false) {
		$('.button-chia-se').hide();;
	}
}
function loadByDate(year, month, date, checked) {
	$.ajax({
		url: getRoot() +  "/loadByDate.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "year": year, "month": month, "date": date, "checked": checked},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[2];
	    	var nguoiXlCongVan = objectList[3];
	  		var vaiTroList = objectList[4];
	  		var vtCongVanList = objectList[5];
	    	loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList);
	    	loadPageNumber(0, '',size);
	    } 
	});
};
function filterData(filter, filterValue) {

	$.ajax({
		url: getRoot() +  "/filter.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: {"filter": filter, "filterValue": filterValue},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[2];
	    	var nguoiXlCongVan = objectList[3];
	  		var vaiTroList = objectList[4];
	  		var vtCongVanList = objectList[5];
	    	loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList);
	    	loadPageNumber(0, '',size);
	    } 
	});
}
function searchByTrangThai(trangThai) {

	$.ajax({
		url: getRoot() +  "/searchByTrangThai.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "trangThai": trangThai},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function(objectList) {
	    	var congVanList = objectList[0];
	    	var fileList = objectList[1];
	    	var size = objectList[2];
	    	var nguoiXlCongVan = objectList[3];
	  		var vaiTroList = objectList[4];
	  		var vtCongVanList = objectList[5];
	    	loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList);
			 loadPageNumber(0, '',size)
	    } 
	});
}
/*
$(document).ready(function() {
	  	$('.page').click(function(){
		var pageNumber = $(this).val();
	    	$.ajax({
				url: getRoot() +  "/loadPageCongVan.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "pageNumber": pageNumber},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(cvList) {
			  		$('#view-table table .rowContent').remove();
					if(cvList.length>0){
						for(i = 0;i < cvList.length; i++ ) {
							var cv = cvList[i] ;
							var style = '';	
							if (i % 2 == 0)
								style = 'style=\"background : #CCFFFF;\"';
							var str = '';
							str = '<tr class=\"rowContent\" ' + style + '>'
								+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"cvId\" value=\"' 
								+ cv.cvId +'\" class=\"checkbox\"></td>'
								+ '<td class=\"col\">' + cv.cvId + '</td>'
								+ '<td class=\"col\">' + cv.donVi.dvMa + '</td>'
								+ '<td class=\"col\">' + cv.trangThai.ttMa + '</td>'
								+ '<td class=\"col\">' + cv.mucDich.mdMa + '</td>'
								+ '<td class=\"col\">' + cv.soDen + '</td>'
								+ '<td class=\"col\">' + cv.ngayNhan + '</td>'
								+ '<td class=\"col\">' + cv.cvSo + '</td>'
								+ '<td class=\"col\">' + cv.ngayGoi + '</td>'
								+ '<td class=\"col\">' + cv.trichYeu + '</td>'
								+ '<td class=\"col\">' + cv.butPhe + '</td>'
								+ '</tr>';
							$('#view-table table tr:first').after(str);
						}
					}
			  	}
			});
	    });	
	}) ; 
	*/
function loadPage(pageNumber) {
	
//	var page = 0;
//	var p = 0;
	if (pageNumber == 'Next') {
		var lastPage = document.getElementsByClassName('page')[9].value;
//		var pageNext = lastPage
		
		var p = (lastPage) / 5;
		var page = lastPage;
	} else if (pageNumber == 'Previous') {
		var firstPage = document.getElementsByClassName('page')[0].value;
		var p = (firstPage -1) / 5;
		var page =  firstPage - 2;
	} else {
		var page = pageNumber;
	}

	$.ajax({
		url: getRoot() +  "/loadPageCongVan.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "pageNumber": page},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(objectList) {
	  		var size = objectList[2];
	  		var congVanList = objectList[0];
	  		var fileList = objectList[1];
	  		var nguoiXlCongVan = objectList[3];
	  		var vaiTroList = objectList[4];
	  		var vtCongVanList = objectList[5];
	    	loadCongVan(congVanList, fileList, nguoiXlCongVan, vaiTroList, vtCongVanList);
	  		loadPageNumber(p, pageNumber,size) ;
					
	  	}
	});
};
function loadPageNumber(p, pageNumber, size) {
	var buttons = '';
	if(pageNumber == 'Next') {
		for (var i = 0; i < 10; i++) {
			var t = ((p - 1) * 5 + i + 1);
			buttons += '<input type=\"button\" value=\"' + t + '\" class=\"page\" onclick= \"loadPage(' + ((p -1)*5 + i)  +')\">&nbsp;';
			if (t == size)
				break;
		}
		buttons = '<input type=\"button\" class=\"pageMove\"  value=\"<< Trước\" onclick= \"loadPage(\'Previous\')\">&nbsp;'  + buttons;
		if ((p + 1) * 5 < size)
			buttons += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
		$('#paging').html(buttons);
		$('.page')[5].focus();
	} else if (pageNumber == 'Previous'){
		if (p > 0)
			p = p -1;
		for (var i = 0; i < 10; i++)
			buttons += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPage(' + (p * 5 + i)  +')\">&nbsp;';
		
		buttons = buttons + '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
		if (p >= 1)
			buttons = '<input type=\"button\" class=\"pageMove\" value=\"<<Trước\" onclick= \"loadPage(\'Previous\')\">&nbsp;' + buttons;
		$('#paging').html(buttons);
		$('.page')[4].focus();
	} else if (pageNumber == '') {
		var buttons = '';
		var index = 0;
		if (size <= 10)
			index = size;
		else 
			index = 10;
		for (var i = 0; i < index; i++) {
//			var t = ((p - 1) * 5 + i + 1);
			buttons += '<input type=\"button\" value=\"' + (i + 1) + '\" class=\"page\" onclick= \"loadPage(' + i  +')\">&nbsp;';
		}
		if (size > 10)
			buttons += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
		$('#paging').html(buttons);
		if (size > 0)
			$('.page')[0].focus();
	}
}

//function preUpdateCv() {
//	
//	showForm('main-form','update-form', true);
//}
//function preUpdatecV(formId, check) {
//	var cvId = $('input:checkbox[name=cvId]:checked').val();
//	$.ajax({
//		url: getRoot() +  "/preEditCongVan.html",	
//	  	type: "GET",
//	  	dateType: "JSON",
//	  	data: { "cvId": cvId},
//	  	contentType: 'application/json',
//	    mimeType: 'application/json',
//	  	
//	  	success: function(congVan) {
//		  	$('input:text[name=soDensUpdate]').val(nsx.soDen);
//		  	$('input:text[name=cvSoUpdate]').val(congVan.cvSo)
//		  	document.getElementById('personlist').getElementsByTagName('option')[11].selected = 'selected';
//		  	$('input:text[name=cvSoUpdate]').val(congVan.cvSo);
//	  		showForm(formId, check);	
//	  		
//	  	}
//	});
//	showForm('main-form','update-form', true);
//}
$(document).ready(function(){
	$('.year').change(function(){
		var checked = $( this ).is( ":checked" );
		var year = $(this).val();
			loadByYear(year, checked);
	});
});
$(document).ready(function(){
	$('.month').change(function(){
		var checked = $( this ).is( ":checked" );
		var str = $(this).val();
		alert(str);
		var temp = str.split("#");
		alert(temp[0]);
			loadByYear(year, checked);
	});
});
/*
$(document).ready(function(){
	$('.month').change(function(){
		var checked = $( this ).is( ":checked" );
		var year = $(this).val();
			loadByYear(year, checked);
	});
});
$(document).ready(function(){
	$('.date').change(function(){
		var checked = $( this ).is( ":checked" );
		var year = $(this).val();
			loadByYear(year, checked);
	});
});
*/
$(document).ready(function(){
	$('#ttFilter').change(function(){
//		alert($(this).val());
		var trangThai = $(this).val();
		searchByTrangThai(trangThai);
	});
});	
$(document).ready(function(){
	$('#buttonSearch').click(function(){
		var filterValue = $('#filterValue').val()+'';	
		var filter = $('#filter').val();
		filterData(filter, filterValue);
	});
});
function getDonVi() {
	var content = '';
	$.ajax({
		url: getRoot() +  "/getDonVi.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(donViList) {
	  		for (i = 0; i < donViList.length; i++) {
	  			content += '<option value=\"' + donViList[i].dvMa + '\">' + donViList[i].dvTen + '</option>';
	  		}
	  		content = '<select class=\"select\" id=\"filterValue\" name=\"filterValue\">' + content + '</select>';
	  		$('#searchContent').html(content);
	  	}
	
	}); 
}
function getMucDich() {
	var content = '';
	$.ajax({
		url: getRoot() +  "/getMucDich.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(mucDichList) {
	  		for (i = 0; i < mucDichList.length; i++) {
	  			content += '<option value=\"' + mucDichList[i].mdMa + '\">' + mucDichList[i].mdTen + '</option>';
	  		}
	  		content = '<select class=\"select\" id=\"filterValue\" name=\"filterValue\">' + content + '</select>';
	  		$('#searchContent').html(content);
	  	}
	
	}); 
}
$(document).ready(function(){
	$('#filter').change(function(){
		var filter = $(this).val();
		if (filter == 'mdMa') {
			getMucDich();
		}
		else if (filter == 'dvMa') {
			getDonVi();
		}
		else if (filter == 'cvNgayNhan' || filter == 'cvNgayDi')
			$('#searchContent').html('<input class = \"text\" type = \"date\" name = \"filterValue\" id=\"filterValue\">');
		else 
			$('#searchContent').html('<input type=\"search\" autofocus class=\"text\" name=\"filterValue\" id=\"filterValue\" readonly style=\"background: #D1D1E0;\" placeholder=\"Nội dung tìm kiếm\" />');
		if (filter == '') {
			$('#filterValue').val('');
			$('#filterValue').prop('readonly', true);
			$('#filterValue').css('background-color', '#D1D1E0');
			filterData('','');
		}
		else {
			$('#filterValue').prop('readonly', false);
			$('#filterValue').css('background-color', '#FFFFFF');
		}
	});
});	
function changeTrangThaiCv(trangThai) {
	$.ajax({
		url: getRoot() +  "/changeTrangThaiCv.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "trangThai": trangThai},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(status) {
		  if (status == "success")
			  alert("Bạn đã thay đổi trạng thái của công văn thành công!!!");
		  else
			  alert("Bạn không thể thay đổi trạng thái của công văn!!!");
	  	}
	});
}
function changeTrangThaiVt(trangThai) {
	var temp = trangThai.split('\\#');
	var cvId = temp[1];
	$.ajax({
		url: getRoot() +  "/changeTrangThaiVt.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "trangThai": trangThai},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(status) {
	  		if (status == "changTtCongVan") {
	  			
	  			alert("Bạn đã thay đổi trạng thái của công văn thành công!!!");
	  			$('#' + cvId + 'ttMaCongVan').html('Đã cấp đủ hàng');
	  		}
	  			
	  		else if (status == "success")
			  alert("Bạn đã thay đổi trạng thái của công văn thành công!!!");
	  		else
			  alert("Bạn không thể thay đổi trạng thái của công văn!!!");
	  	}
	});
};

$(document).ready(function(){
	$('#add-form').submit(function(){
		addCongVan();
		return false;
	});
});
$(document).ready(function(){
	$('#update-form').submit(function(){
		updateCongVan();
		return false;
	});
});
