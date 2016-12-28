
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
function showForm3(formId1, formId2, check){
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
function showForm2(formId, check){
	if (check)
		document.getElementById(formId).style.display="block";
	else document.getElementById(formId).style.display="none";
	var f = document.getElementById('main-form'), s, opacity;
	s = f.style;
	opacity = check? '10' : '100';
	s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
	s.filter = 'alpha(opacity='+opacity+')';
	for(var i=0; i<f.length; i++) f[i].disabled = check;
	$('#main-form').show();
	
}

function confirmResetNd(){
	var msnv = $('input:checkbox[name=msnv]:checked').val();
	var ndMaList = [];
	$.each($("input[name='msnv']:checked"), function(){            
		ndMaList.push($(this).val());
    });
	var str = ndMaList.join(", ");
	if (ndMaList.length == 0)
		alert('Bạn phải chọn 1 hoặc nhiều Tài khoản để mở khóa!!');
	else if (confirm('Bạn có chắc mở tài khoản có mã ' + str))
		resetNd(str);
}
function resetNd(str) {
	$.ajax({
		url: getRoot() +  "/resetNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "ndList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(result) {
	  		if (result == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
				$('table tr').has('input[name="msnv"]:checked').remove();
				alert('Tài khoản có mã ' + str + " đã mở khóa");
		  		$('#view-table-chia-se table tr:first').after('<tr class="rowContent"><td style=\"text-align: center;\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
		  				+msnv + '\"</td><td class=\"col\">'+ msnv +'</td><td class=\"col\">' + hoten+'</td><td class=\"col\">' + chucdanh+'</td><td class=\"col\">' 
		  				+ email+'</td><td class=\"col\">' + diachi+'</td><td class=\"col\">' + sdt+'</td></tr>');
	  		}
				
	    } 
	});  
}
function confirmLockNd(){
	var msnv = $('input:checkbox[name=msnv]:checked').val();
	var ndMaList = [];
	$.each($("input[name='msnv']:checked"), function(){            
		ndMaList.push($(this).val());
    });
	var str = ndMaList.join(", ");
	if (ndMaList.length == 0)
		alert('Bạn phải chọn 1 hoặc nhiều Tài khoản để khóa!!');
	else if (confirm('Bạn có chắc khóa tài khoản có mã ' + str))
		lockNd(str);
}
	 function lockNd(str) {
	$.ajax({
		url: getRoot() +  "/lockNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "ndList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(result) {
	  		if (result == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
				$('table tr').has('input[name="msnv"]:checked').remove();
				alert('Tài khoản có mã ' + str + " đã bị khóa");
	  		}
	    } 
	});  
}
	 
	 function confirmResetMK(){
			var msnv = $('input:checkbox[name=msnv]:checked').val();
			var ndMaList = [];
			$.each($("input[name='msnv']:checked"), function(){            
				ndMaList.push($(this).val());
		    });
			var str = ndMaList.join(", ");
			if (ndMaList.length == 0)
				alert('Bạn phải chọn 1 hoặc nhiều Tài khoản để khôi phục mật khẩu!!');
			else if (confirm('Bạn có chắc khôi phục mật khẩu của tài khoản có mã ' + str))
				resetMK(str);
		}
function resetMK(str) {
	document.getElementById("loading").style.display="block";
	document.body.style.cursor = "wait";
	$.ajax({
		url: getRoot() +  "/resetMK.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "ndList": str},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(result) {
	  		if (result == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
				alert('Tài khoản có mã ' + str + " đã được khôi phục mật khẩu thành công!");
				document.getElementById("loading").style.display="none";
				document.body.style.cursor = "auto";
	  		}
	    } 
	});
}
function preUpdateNd(formId, check) {
	var msnv = $('input:checkbox[name=msnv]:checked').val();
	var MsnvList = [];
	$.each($("input[name='msnv']:checked"), function(){            
		MsnvList.push($(this).val());
    });
	if (MsnvList.length == 0)
		alert('Bạn phải chọn 1 tài khoản để thay đổi!!');
	else if (MsnvList.length > 1)
		alert('Bạn chỉ được chọn 1 tài khoản để thay đổi!!');
	else {
	$.ajax({
		url: getRoot() +  "/preUpdateNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "msnv": msnv},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(nd) {
	  		if (nd == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
			  	$('input:text[name=msnv]').val(nd.msnv);
			  	$('input:text[name=hoten]').val(nd.hoTen);
			  	$('input:text[name=email]').val(nd.email);
			  	$('input:text[name=diachi]').val(nd.diaChi);
			  	$('input:text[name=sdt]').val(nd.sdt);
			  	$('#chucDanh option[value='+nd.chucDanh.cdMa+']').prop('selected',true);
			  	$('#hoTen').focus();
			  	showForm3('main-form','update-form',true);
	  		}
		  //	showForm(formId, check);	
		  	//$('#main-form').hide();
	  	}
	});
	}
}
function updateNd(msnv, hoten,chucdanh, email, diachi, sdt) {

	 if (hoten == '')
			{
				$('#requireHoten').html('Vui lòng nhập họ tên');
				return;
			}
	 if (chucdanh == '')
		{
			$('#requireChucdanh').html('Vui lòng chọn chức danh');
			return;
		}
	 if (email == '')
		{
			$('#requireEmail').html('Vui lòng nhập email');
			return;
		}
	 if (diachi == '')
		{
			$('#requireDiachi').html('Vui lòng nhập địa chỉ');
			return;
		}
	 if (sdt == '')
		{
			$('#requireSdt').html('Vui lòng nhập số điện thoại');
			return;
		}
//	else{
	$.ajax({
			url: getRoot() +  "/updateNd.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv, "hoten": hoten, "chucdanh": chucdanh, "email": email, "diachi": diachi, "sdt": sdt},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	
		  	success: function(nd) {
		  		if (nd == "authentication error") {
		  			location.assign("login.jsp");
		  		} else {
			  		$('table tr').has('input[name="msnv"]:checked').remove();
			  		$('#view-table-chia-se table tr:first').after('<tr class="rowContent"><td style=\"text-align: center;\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
			  				+msnv + '\"</td><td class=\"col\">'+ msnv +'</td><td class=\"col\">' + hoten+'</td><td class=\"col\">' + chucdanh+'</td><td class=\"col\">' 
			  				+ email+'</td><td class=\"col\">' + diachi+'</td><td class=\"col\">' + sdt+'</td></tr>');
			  		$('input:text[name=msnv]').val('');
					hoten = $('input:text[name=hoten]').val('');
					chucdanh = $('select[name=chucdanh]').val('');
					email = $('input:text[name=email]').val('');
					diachi = $('input:text[name=diachi]').val('');
					sdt = $('input:text[name=sdt]').val('');
			  		alert("Thay đổi thành công người dùng có mã "+msnv+ " !");
			  		$('input[name="msnv"]:checked').prop('checked',false);
			  		showForm2('update-form',false);	
		  		}
		  	}
		});
//	}
}
function confirmUpdateNd(){
	var msnv = $('input:text[name=msnv]').val();
	var hoten = $('input:text[name=hoten]').val();
	var chucdanh = $('select[name=chucdanh]').val();
	var email = $('input:text[name=email]').val();
	var diachi = $('input:text[name=diachi]').val();
	var sdt = $('input:text[name=sdt]').val();
	if (confirm('Bạn có chắc thay đổi người dùng có mã ' + msnv))
		updateNd(msnv, hoten, chucdanh, email, diachi,sdt);
	
		//$('#danh-sach-tai-khoan').show();
}
function confirmDelete(){
	return confirm('Bạn có chắc xóa');
}
function checkPassword()
	{
		var password = document.forms["taoTaiKhoan"]["matkhau"].value;
		var confirmPassword = document.forms["taoTaiKhoan"]["nlmatkhau"].value;
		if(password != confirmPassword)
		{
			alert("Mật khẩu nhập lại chưa chính xác. Vui lòng kiểm tra lại!");
			return false;
		}
		else
		{
			addNd();
		}
	}

//$(document).ready(function() {
//  	$('.page').click(function(){
//	var pageNumber = $(this).val();
//    	$.ajax({
//			url: getRoot() +  "/loadPageNd.html",	
//		  	type: "GET",
//		  	dateType: "JSON",
//		  	data: { "pageNumber": pageNumber},
//		  	contentType: 'application/json',
//		    mimeType: 'application/json',
//		  	
//		  	success: function(ndList) {
//		  		$('#view-table-chia-se table .rowContent').remove();
//				if(ndList.length>0){
//					for(i = 0;i < ndList.length; i++ ) {
//						var nd = ndList[i] ;
//						var style = '';	
//						if (i % 2 == 0)
//							style = 'style=\"background : #CCFFFF;\"';
//						var str = '';
//						str = '<tr class=\"rowContent\" ' + style + '>'
//							+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
//							+ nd.msnv +'\" class=\"checkbox\"></td>'
//							+ '<td class=\"col\">' + nd.msnv + '</td>'
//							+ '<td class=\"col\">' + nd.hoTen + '</td>'
//							+ '<td class=\"col\">' + nd.chucDanh.cdTen + '</td>'
//							+ '<td class=\"col\">' + nd.email + '</td>'
//							+ '<td class=\"col\">' + nd.diaChi + '</td>'
//							+ '<td class=\"col\">' + nd.sdt + '</td>'
//							+ '</tr>';
//						$('#view-table-chia-se table tr:first').after(str);
//					}
//				}
//		  	}
//		});
//    });	
//})   
function loadPageNd(pageNumber){
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
		url: getRoot() +  "/loadPageNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "pageNumber": page},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(objectList) {
	  		if (objectList == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
		  		var size = objectList[1];
		  		var ndList = objectList[0];
		  		var length = ndList.length;
		  		$('#view-table table .rowContent').remove();
					for(i = 0;i < length; i++ ) {
						var nd = ndList[i];
						var cells = '';
						var style = '';
						if (i % 2 == 0)
							style = 'style=\"background : #CCFFFF;\"';
						
						
					cells = '<td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
							+ nd.msnv +'\" class=\"checkbox\"></td>'
							+ '<td class=\"col\">' + nd.msnv + '</td>'
							+ '<td class=\"col\">' + nd.hoTen + '</td>'
							+ '<td class=\"col\">' + nd.chucDanh.cdTen + '</td>'
							+ '<td class=\"col\">' + nd.email + '</td>'
							+ '<td class=\"col\">' + nd.diaChi + '</td>'
							+ '<td class=\"col\">' + nd.sdt + '</td>'
						var row = '<tr class=\"rowContent\" ' + style + '>' + cells + '</tr>';
	 					$('#view-table table tr:first').after(row);
					}
				var button = '';
				if(pageNumber == 'Next') {
					for (var i = 0; i < 10; i++) {
						
						var t = ((p -1) * 5 + i + 1);
						
						button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageNd(' + ((p -1)*5 + i)  +')\">&nbsp;';
						if (t > size)
							break;
					}
					button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPageNd(\'Previous\')\">&nbsp;'  + button;
					if ((p + 1) * 5 < size)
						button += '<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPageNd(\'Next\');\">';
					$('#paging').html(button);
					$('.page')[5].focus();
				} else if (pageNumber == 'Previous'){
					if (p > 0)
						p = p -1;
					for (var i = 0; i < 10; i++)
						button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageNd(' + (p * 5 + i)  +')\">&nbsp;';
					
					button = button + '<input type=\"button\"  class=\"pageMove\"  value=\"Sau >>\" onclick= \"loadPageNd(\'Next\');\">';
					if (p >= 1)	
						button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPageNd(\'Previous\')\">&nbsp;' + button;
					$('#paging').html(button);	
					$('.page')[4].focus();
					}
		  		}
		  	}
		});
}
function loadPageNdKP(pageNumber){
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
		url: getRoot() +  "/loadPageNdKP.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "pageNumber": page},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(objectList) {
	  		if (objectList == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
		  		var size = objectList[1];
		  		var ndList = objectList[0];
		  		var length = ndList.length;
		  		$('#view-table-chia-se table .rowContent').remove();
					for(i = 0;i < length; i++ ) {
						var nd = ndList[i];
						var cells = '';
						var style = '';
						if (i % 2 == 0)
							style = 'style=\"background : #CCFFFF;\"';
						
						
					cells = '<td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' 
							+ nd.msnv +'\" class=\"checkbox\"></td>'
							+ '<td class=\"col\">' + nd.msnv + '</td>'
							+ '<td class=\"col\">' + nd.hoTen + '</td>'
							+ '<td class=\"col\">' + nd.chucDanh.cdTen + '</td>'
							+ '<td class=\"col\">' + nd.email + '</td>'
							+ '<td class=\"col\">' + nd.diaChi + '</td>'
							+ '<td class=\"col\">' + nd.sdt + '</td>'
						var row = '<tr class=\"rowContent\" ' + style + '>' + cells + '</tr>';
	 					$('#view-table-chia-se table tr:first').after(row);
					}
				var button = '';
				if(pageNumber == 'Next') {
					for (var i = 0; i < 10; i++) {
						
						var t = ((p -1) * 5 + i + 1);
						
						button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageNdKP(' + ((p -1)*5 + i)  +')\">&nbsp;';
						if (t > size)
							break;
					}
					button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPageNdKP(\'Previous\')\">&nbsp;'  + button;
					if ((p + 1) * 5 < size)
						button += '<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPageNdKP(\'Next\');\">';
					$('#paging').html(button);
					$('.page')[5].focus();
				} else if (pageNumber == 'Previous'){
					if (p > 0)
						p = p -1;
					for (var i = 0; i < 10; i++)
						button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPageNdKP(' + (p * 5 + i)  +')\">&nbsp;';
					
					button = button + '<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPageNdKP(\'Next\');\">';
					if (p >= 1)	
						button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPageNdKP(\'Previous\')\">&nbsp;' + button;
					$('#paging').html(button);	
					$('.page')[4].focus();
				}
		  	}
	  	}
	});
}
 function login() {
	var msnv = $('input:text[name=msnv]').val();
	var matkhau = $('input:password[name=matkhau]').val();
	$.ajax({
		url: getRoot() +  "/login.html",	
	  	type: "POST",
	  	dateType: "JSON",
	  	data: { "msnv": msnv, "matkhau": matkhau},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(result) {
	  		if (result == "authentication error") {
	  			location.assign("login.jsp");
	  		} else if(result == "success") {
		  		alert("Xin chào "+ msnv + " bạn đã đăng nhập thành công");	
		  		$('input:text[name=msnv]').val('');
				$('input:password[name=matkhau]').val('');
		  		window.location.assign(getRoot() +  "");
			}
	  		else{
	  			alert("Mã số nhân viên và mật khẩu chưa đúng vui lòng kiểm tra lại");
	  		}
	  	}
	});
}
 
 function checkAdd(){
 	var msnv = $('#add-form input:text[name=msnv]').val();
	var matkhau = $('#add-form input:password[name=matkhau]').val();
	var nlmatkhau = $('#add-form input:password[name=nlmatkhau]').val();
	var hoten = $('#add-form input:text[name=hoten]').val();
	var chucdanh = $('#add-form select[name=chucdanh]').val();
	var sdt = $('#add-form input:text[name=sdt]').val();
	var email = $('#add-form input:text[name=email]').val();
	var diachi = $('#add-form input:text[name=diachi]').val();

	if(msnv == ''){
		$('#requireMsnv').html('Vui lòng nhập mã số nhân viên ');
		return false;
	}
	else if(matkhau == ''){
		$('#requireMatkhau').html('Vui lòng nhập mật khẩu');
		return false;
	}
	else if(nlmatkhau == ''){
		$('#requireNlmatkhau').html('Vui lòng nhập lại mật khẩu');
		return false;
	}
	else if(hoten == ''){
		$('#requireHoten').html('Vui lòng nhập họ tên');
		return false;
	}
	else if(chucdanh == null){
		$('#requireChucdanh').html('Vui lòng chọn chức danh');
		return false;
	}
	else if(sdt == ''){
		$('#requireSdt').html('Vui lòng nhập số điện thoại');
		return false;
	}
	else if(email == ''){
		$('#requireEmail').html('Vui lòng nhập email');
		return false;
	}
	else if(diachi == ''){
		$('#requireDiachi').html('Vui lòng nhập địa chỉ');
		return false;
	}
	return true;
}
 
 function changeMsnv(){
	 var msnv = $('#msnv').val();
	 $.ajax({
			url: getRoot() +  "/loadHoten.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(hoTen){
		  		$('#add-form input:text[name=hoten]').val(hoTen);
		  	}
		  	
		});
	 
	} 	
	function changeMatkhau(){
		$('#requireMatkhau').html('');
	} 	
	function changeNlmatkhau(){
		$('#requireNlmatkhau').html('');
	} 	
	function changeHoten(){
		$('#requireHoten').html('');
	} 	
	function changeChucdanh(){
		$('#requireChucdanh').html('');
	} 	
	function changeSdt(){
		$('#requireSdt').html('');
	} 
	function changeEmail(){
		$('#requireEmail').html('');
	} 
	function changeDiachi(){
		$('#requireDiachi').html('');
	} 
	
	function checkNd()
	{
		if(checkAdd()){
			checkPassword();
		}
	}
	function loadAddNd() {
 		showForm2('update-form', false);
 		$('input[name="msnv"]:checked').prop('checked',false);
 	}
 	function loadUpdateNd() {
 		showForm2('update-form', false);
 		$('input[name="msnv"]:checked').prop('checked',false);
 	}
 function addNd() {
	var msnv = $('#add-form input:text[name=msnv]').val();
	var matkhau = $('#add-form input:password[name=matkhau]').val();
	var nlmatkhau = $('#add-form input:password[name=nlmatkhau]').val();
	var hoten = $('#add-form input:text[name=hoten]').val();
	var chucdanh = $('#add-form select[name=chucdanh]').val();
	var sdt = $('#add-form input:text[name=sdt]').val();
	var email = $('#add-form input:text[name=email]').val();
	var diachi = $('#add-form input:text[name=diachi]').val();
	
	$.ajax({
		url: getRoot() +  "/addNd.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "msnv": msnv,"matkhau":matkhau,"nlmatkhau":nlmatkhau,"hoten":hoten,"chucdanh":chucdanh,"sdt":sdt,"email":email,"diachi":diachi},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(result) {
	  		if (result == "authentication error") {
	  			location.assign("login.jsp");
	  		} else if(result == "success") {
		  		alert("Tạo người dùng với mã số "+"'"+msnv+"'"+ "thành công");	
		  		$('#add-form input:text[name=msnv]').val('');
				$('#add-form input:password[name=matkhau]').val('');
				$('#add-form input:password[name=nlmatkhau]').val('');
				$('#add-form input:text[name=hoten]').val('');
				$('#add-form select[name=chucdanh]').val('');
				$('#add-form input:text[name=sdt]').val('');
				$('#add-form input:text[name=email]').val('');
				$('#add-form input:text[name=diachi]').val('');
			}
	  		else{
	  			alert("Người dùng có mã số"+"'"+msnv+"'"+ "đã tồn tại");	
	  		}
	  	}
	});
}
 
 function timKiemNguoidung(){
	var hoTen = '';
	var msnv = '';
	var check = $('#checkTen:checked').val();
	if (check != null)
		hoTen = $('#search-nguoidung input[name=nguoidung]').val();
	else 
		msnv = $('#search-nguoidung input[name=nguoidung]').val();
	
	$.ajax({
		url: getRoot() +  "/timKiemNguoidung.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "msnv": msnv, "hoTen": hoTen},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(ndList){
	  		if (ndList == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
		  		if(ndList.length>0){
		  			//alert('OKs');
		  			var content = '';
		  			$('#view-table table .rowContent').remove();
					for(i = 0;i < ndList.length; i++ ) {
						nd = ndList[i];
						var style = '';
						if  (i % 2 == 1)
							style = 'style=\"background : #CCFFFF;\"'
						//alert(ndList[i].msnv);
		  				content +='<tr class=\"rowContent\"' + style + '><td style=\"text-align: center\"><input type=\"checkbox\" name=\"msnv\" value=\"' +nd.msnv 
						+ '\"</td><td class=\"col\">'+ nd.msnv +'</td><td class=\"col\">' + nd.hoTen
						+'</td><td class=\"col\">' + nd.chucDanh.cdTen
						+'</td><td class=\"col\">' + nd.email
						+'</td><td class=\"col\">' + nd.diaChi
						+'</td><td class=\"col\">' + nd.sdt
						+'</tr>';
					}
					$('#view-table table tr:first').after(content);
		  		}
		  		else {
	  				alert("Không tìm thấy tài khoản!");
	  			}
	  		}
	  	}
	});
}
 function timKiemNguoidungKhoa(){
		var hoTen = '';
		var msnv = '';
		var check = $('#checkTen:checked').val();
		if (check != null)
			hoTen = $('#search-nguoidung-khoa input[name=nguoidung]').val();
		else 
			msnv = $('#search-nguoidung-khoa input[name=nguoidung]').val();
		
		$.ajax({
			url: getRoot() +  "/timNguoiDungKhoa.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv, "hoTen": hoTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(ndList){
		  		if (ndList == "authentication error") {
		  			location.assign("login.jsp");
		  		} else {
			  		if(ndList.length>0){
			  			//alert('OKs');
			  			var content = '';
			  			$('#view-table table .rowContent').remove();
						for(i = 0;i < ndList.length; i++ ) {
							nd = ndList[i];
							var style = '';
							if  (i % 2 == 1)
								style = 'style=\"background : #CCFFFF;\"'
							//alert(ndList[i].msnv);
			  				content +='<tr class=\"rowContent\"' + style + '><td style=\"text-align: center\"><input type=\"checkbox\" name=\"msnv\" value=\"' +nd.msnv 
							+ '\"</td><td class=\"col\">'+ nd.msnv +'</td><td class=\"col\">' + nd.hoTen
							+'</td><td class=\"col\">' + nd.chucDanh.cdTen
							+'</td><td class=\"col\">' + nd.email
							+'</td><td class=\"col\">' + nd.diaChi
							+'</td><td class=\"col\">' + nd.sdt
							+'</tr>';
						}
						$('#view-table table tr:first').after(content);
			  		}
			  		else {
		  				alert("Không tìm thấy tài khoản!");
		  			}
		  		}
		  	}
		});
	}
 
 function timKiemNguoidungCs(){
	var hoTen = '';
	var msnv = '';
	var check = $('#checkTen:checked').val();
	if (check != null)
		hoTen = $('#search input[name=nguoidung]').val();
	else 
		msnv = $('#search input[name=nguoidung]').val();
	$.ajax({
		url: getRoot() +  "/timKiemNguoidung.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "msnv": msnv, "hoTen": hoTen},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(ndList){
	  		if (result == "authentication error") {
	  			location.assign("login.jsp");
	  		} else {
		  		if(ndList.length>0){
		  			$('#view-table-chia-se table .rowContent').remove();
					for(i = 0;i < ndList.length; i++ ) {
						nd = ndList[i];
		  				$('#view-table-chia-se table tr:first').after('<tr class=\"rowContent\"><td class=\"left-column\"><input type=\"checkbox\" name=\"msnv\" value=\"' +nd.msnv 
						+ '\"</td><td class=\"col\">'+ nd.msnv +'</td><td class=\"col\">' + nd.hoTen
						+'</td><td class=\"col\">' + nd.chucDanh.cdTen
						+'</td><td class=\"col\">' + nd.vaiTro.vtTen
						+'</tr>');
					}
		  		}
		  		else {
	  				alert("Không tìm thấy tài khoản!");
	  			}
	  		}
	  	}
	});
}
 function timKiemNguoidungKpmk(){
		var hoTen = '';
		var msnv = '';
		var check = $('#checkTen:checked').val();
		if (check != null)
			hoTen = $('#search-nguoidung input[name=nguoidung]').val();
		else 
			msnv = $('#search-nguoidung input[name=nguoidung]').val();
		
		$.ajax({
			url: getRoot() +  "/timKiemNguoidung.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	data: { "msnv": msnv, "hoTen": hoTen},
		  	contentType: 'application/json',
		    mimeType: 'application/json',
		  	success: function(ndList){
		  		if (ndList == "authentication error") {
		  			location.assign("login.jsp");
		  		} else {
			  		if(ndList.length>0){
			  			//alert('OKs');
			  			var content = '';
			  			$('#view-table table .rowContent').remove();
						for(i = 0;i < ndList.length; i++ ) {
							nd = ndList[i];
							var style = '';
							if  (i % 2 == 1)
								style = 'style=\"background : #CCFFFF;\"'
							//alert(ndList[i].msnv);
			  				content +='<tr class=\"rowContent\"' + style + '><td style=\"text-align: center\"><input type=\"checkbox\" name=\"msnv\" value=\"' +nd.msnv 
							+ '\"</td><td class=\"col\">'+ nd.msnv +'</td><td class=\"col\">' + nd.hoTen
							+'</td><td class=\"col\">' + nd.chucDanh.cdTen
							+'</td><td class=\"col\">' + nd.email
							+'</td><td class=\"col\">' + nd.diaChi
							+'</td><td class=\"col\">' + nd.sdt
							+'</tr>';
						}
						$('#view-table table tr:first').after(content);
			  		}
			  		else {
		  				alert("Không tìm thấy tài khoản!");
		  			}
		  		}
		  	}
		});
	}
/* 
 $(document).ready(function() {
		$('#add-form').keypress(function(e) {
		 var key = e.which;
		 if(key == 13)  // the enter key code
		  {
			 addNd();
		    return false;  
		  }
		});   
	});   
 */
 /*
	$(document).ready(function() {
		$('#update-form').keypress(function(e) {
		 var key = e.which;
		 if(key == 13)  // the enter key code
		  {
		    updateNd();
		    return false;  
		  }
		  
		}); 
	}); 
*/

 // click event
 /*
$(document).ready(function(){
	$('#login').click(function(){
		login();
	});
}) ;
*/ 	
$(document).ready(function(){
	$('#add-form').submit(function(){
		checkPassword();
		return false;
	});
})
$(document).ready(function(){
	$('#update-form').submit(function(){
		confirmUpdateNd();
		return false;
	});
})

$(document).ready(function(){
	$('#search-nguoidung').submit(function(){
		timKiemNguoidung();
		return false;
	});
});
$(document).ready(function(){
	$('#search-nguoidung-khoa').submit(function(){
		timKiemNguoidungKhoa();
		return false;
	});
})

$(document).ready(function(){
	$('#searchName').keypress(function(){
		timKiemNguoidung();
	});
});