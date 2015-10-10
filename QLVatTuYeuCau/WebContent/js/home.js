/**
 * click event
 */
//sttNhatKy = 0;
$(document).ready(function(){
	$('#moreNhatKy').click(function(){
		showMoreNhatKy();
	});
});
$(document).ready(function(){
	$('#moreAlert').click(function(){
		showMoreAlert();
	});
});
$(document).ready(function(){
	$('#moreWork').click(function(){
		showMoreWork();
	});
});
/**
 * function
 */
function showMoreNhatKy() {
	$.ajax({
		url: getRoot() +  "/showMoreNhatKy.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(nhatKyList) {
	  		
	  		var content = '';
	  		var length = nhatKyList.length;
	  		for (var i = 0; i < length; i++) {
	  			sttNhatKy ++;
	  			var cvId = '';
	  			var hoatDong = '';
	  			var link = '';
	  			var nhatKy = nhatKyList[i];
	  			var hoatDongTemp = nhatKy.hoatDong;
	  			
	  			if (hoatDongTemp.indexOf("#") != -1) {
					var temp = hoatDongTemp.split("\\#");
					var cvId = temp[0];
					var hoatDong = temp[1];
					
					link = '<a style=\"color: blue; text-decoration: underline;\" href=\"' + getRoot() + '/searchCongVan.html' + '?congVan=' + cvId + '\">' + hoatDong + '<a>'; 
				} else if (hoatDongTemp.indexOf('#') == -1)
					link = '<a style=\"color: blue; text-decoration: underline;\" href=\"' + getRoot() + '/cvManage.html?action=manageCv\">' + hoatDongTemp + '</a>';
	  			var style = '';
	  			if (i % 2 == 0)
	  				style = 'style = \"background: #CCFFFF; \"';
		  			var dateTemp = nhatKy.thoiGian.split('-');
		  			var date = dateTemp[2] + '/' + dateTemp[1] + '/' + dateTemp[0];   
	  			content += '<tr ' + style + '>'
	  					+ '<td style=\"text-align: center;\">' + sttNhatKy + '</td>'
						+ '<td >' + link + '</td>'
						+ '<td style=\"\">' + nhatKy.noiDung + '</td>'
						+ '<td style=\"text-align: center;\">' + date  + '</td>'
						+ '</tr>';
	  		}
	  		$('#nhatKy table tr:last').after(content);
	  	}
	}); 
}
function showMoreWork(){
	$.ajax({
		url: getRoot() +  "/showMoreWork.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	
	  	success: function(objectList) {
	  		if (chucDanhMa == adminMa || chucDanhMa == vanThuMa || chucDanhMa == truongPhongMa) {
	  			
	  			var congVanList = objectList;
		  		var content = '';
		  		var length = objectList.length;
		  		for (var i = 0; i < length; i++) {
		  			sttWork ++;
		  			var congVan = congVanList[i];
		  			var cvNgayNhan = parseDate(congVan.cvNgayNhan);   
					var link = '<a style=\"color: blue; text-decoration: underline;\" href=\"' + getRoot() + '/searchCongVan.html' + '?congVan=' + congVan.cvId + '\">' + 'Công văn có số đến ' + congVan.soDen + ' nhận ngày ' + cvNgayNhan+ '<a>'; 
		  			var style = '';
		  			if (i % 2 != 0)
		  				style = 'style = \"background: #CCFFFF; \"';
		  			var styleTrangThai = '';
					
					var ttMa = congVan.trangThai.ttMa;
					if (ttMa =='CGQ')
						styleTrangThai = 'color: red';
					else
						styleTrangThai = 'color: yello';
					content +=  '<tr ' + style + '>'
							+ '<td colspan = \"3\" style=\"text-align: center;\">' + link + '</td>'
							+ '<td colspan = \"2\" style=\"text-align: center;\"><div style=\"' + styleTrangThai + '\">' + congVan.trangThai.ttTen + '</div></td>'
							+ '</tr>';
		  		}
		  		$('#work table tr:last').after(content);
	  		} else {
	  			var congVanList = objectList[0];
	  			var vaiTroList = objectList[1];
	  			var trangThaiList = objectList[2];
	  			var content = '';
	  			
	  			var length = congVanList.length;
	  			for (var i = 0; i  < length; i++) {
	  				var congVan = congVanList[i];
	  				var cvNgayNhan = parseDate(congVan.cvNgayNhan);
					var style = '';
					if (i % 2 != 0)
		  				style = 'style = \"background: #CCFFFF; \"';
					var noiDung = "Vai trò công văn có số đến " + congVan.soDen + " nhận ngày " + cvNgayNhan + ":";
					var vaiTroCongVanList = vaiTroList[i];
					var trangThaiCongVanList = trangThaiList[i];
					var lengthVtCv = vaiTroCongVanList.length;
					for (var j = 0; j < lengthVtCv; j++) {
						var vaiTro = vaiTroCongVanList[j];
						var trangThai = trangThaiCongVanList[j];
						noiDung += '<br>&nbsp;&nbsp;+ ' + vaiTro.vtTen + ': ' + trangThai + '.';
						j++;
					}
					content += '<tr ' + style + '>'
							+ '<td style=\"text-align: left;\"><a style="color: blue; text-decoration: underline; \" href="' + getRoot() + '/searchCongVan.html' + '?congVan=' + congVan.cvId +'\">' + 'Công văn số ' + noiDung + '</a></td>'
							+ '</tr>';
	  			}
	  			$('#work table tr:last').after(content);
	  		}
	  	}
	}); 
}
function showMoreAlert(){
	$.ajax({
		url: getRoot() +  "/showMoreAlert.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(ctVatTuList) {
	  		var content = '';
	  		var length = ctVatTuList.length;
	  		for (var i = 0; i < length; i++) {
	  			var ctVatTu = ctVatTuList[i];
	  			var style = '';
				if (i % 2 != 0)
					style = 'style = \"background: #CCFFFF; \"';
		  		content += '<tr ' + style + '>'
		  				+ '<td colspan ="1" style=\"text-align: left;\">' + ctVatTu.vatTu.vtMa +'</td>'
		  				+ '<td colspan =\"1\" style=\"text-align: left;\">' + ctVatTu.noiSanXuat.nsxMa + '</td>'
		  				+ '<td colspan =\"1\" style=\"text-align: left;\">' + ctVatTu.chatLuong.clMa + '</td>'
		  				+ '<td colspan =\"1\" style=\"text-align: left;\">' + ctVatTu.dinhMuc + '</td>'
		  				+ '<td colspan =\"1\" style=\"text-align: left;\">' + ctVatTu.soLuongTon + '</td>'
		  				+ '</tr>';
	  		}
	  		$('#alert table tr:last').after(content);
	  	}
		
  	});
}