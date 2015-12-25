/*
 function loadBaoCao() {
	var eCvNgayNhan = $('#eCvNgayNhan').val();
	var sCvNgayNhan = $('#sCvNgayNhan').val();
	var eCvNgayDi = $('#eCvNgayDi').val();
	var sCvNgayDi = $('#sCvNgayDi').val();
	var mucDich = $('#mucDich').val();
	var trangThai = '';
	var temp = $('input:radio[name=trangThai]:checked').val();
	if (temp != null)
		trangThai = temp;
	var donVi = $('#donVi').val();
	var cvSo = $('#cvSo').val();
	$.ajax({
		url: getRoot() + "/loadBcCongVan.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "eCvNgayNhan": eCvNgayNhan, "sCvNgayNhan": sCvNgayNhan, "eCvNgayDi": eCvNgayDi, "sCvNgayDi": sCvNgayDi
	  		, "mucDich": mucDich, "trangThai": trangThai, "donVi": donVi, "cvSo": cvSo},
//	  	data: { "eCvNgayNhan": eCvNgayNhan, "sCvNgayNhan": sCvNgayNhan, "eCvNgayDi": eCvNgayDi, "sCvNgayDi": sCvNgayDi, "mucDich": mucDich, "trangThai": trangThai, "donVi": donVi},
//	  		, "mucDich": mucDich, "trangThai": trangThai, "donVi": donVi, "cvSo": cvSo},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(congVanList) {
	  		$('#view-table table .rowContent').remove();
	  		var length = congVanList.length;
	  		var content = "";
	  		
	  		for (var i = 0; i < length; i++ ) {
	  			var congVan = congVanList[i];
	  			var style = '';
	  			if (i % 2 == 0)
	  				style = ' style=\"background : #CCFFFF;\" ';
	  			else
	  				style = ' style=\"background : #FFFFFF;\" ';
	  			content += '<tr class=\"rowContent\"' + style + '>'
	  					+ '<td style=\"width: 50px; text-align: center;\">' + congVan.soDen + '</td>'
						
						+ '<td style=\"width: 100px; text-align: center;\">' + parseDate(congVan.cvNgayNhan) + '</td>'
						+ '<td style=\"width: 50px; text-align: center;\">' + congVan.cvSo + '</td>'	
						+ '<td style=\"width: 50px; text-align: center;\">' + parseDate(congVan.cvNgayNhan) + '</td>'
						+ '<td style=\"text-align: left; width: 300px;\">' + congVan.mucDich.mdTen + '</td>'
						+ '<td style=\"text-align: left; width: 100px;\">' + congVan.donVi.dvTen + '</td>'
						+ '<td style=\"width: 50px;\">' + congVan.trichYeu + '</td>'
						+ '<td style=\"text-align: left; width: 100px;\">' + congVan.butPhe + '</td>'
						+ '<td style=\"text-align: left; width: 150px;\">' + congVan.trangThai.ttTen + '</td>'
						+ '</tr>';
	  		}
	  		$('#view-table table tr:last').after(content);
	  	}
	});
};
$(document).ready(function(){
	$('#xem').click(function(){
		loadBaoCao();
	});
});
	*/
function loadBaoCao() {
	var eCvNgayNhan = $('#eCvNgayNhan').val();
	var sCvNgayNhan = $('#sCvNgayNhan').val();
	var eCvNgayDi = $('#eCvNgayDi').val();
	var sCvNgayDi = $('#sCvNgayDi').val();
	var mucDich = $('#mucDich').val();
	var trangThai = '';
	var temp = $('input:radio[name=trangThai]:checked').val();
	if (temp != null)
		trangThai = temp;
	var donVi = $('#donVi').val();
	var cvSo = $('#cvSo').val();
	$.ajax({
		url: getRoot() + "/loadBcCongVan.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	data: { "eCvNgayNhan": eCvNgayNhan, "sCvNgayNhan": sCvNgayNhan, "eCvNgayDi": eCvNgayDi, "sCvNgayDi": sCvNgayDi
	  		, "mucDich": mucDich, "trangThai": trangThai, "donVi": donVi, "cvSo": cvSo},
//	  	data: { "eCvNgayNhan": eCvNgayNhan, "sCvNgayNhan": sCvNgayNhan, "eCvNgayDi": eCvNgayDi, "sCvNgayDi": sCvNgayDi, "mucDich": mucDich, "trangThai": trangThai, "donVi": donVi},
//	  		, "mucDich": mucDich, "trangThai": trangThai, "donVi": donVi, "cvSo": cvSo},
	  	contentType: 'application/json',
	    mimeType: 'application/json',
	  	success: function(congVanList) {
	  		if (congVanList == "authentication error") {
				location.assign("login.jsp");
			} else {
		  		$('#view-table table .rowContent').remove();
		  		var length = congVanList.length;
		  		var content = "";
		  		
		  		for (var i = 0; i < length; i++ ) {
		  			var congVan = congVanList[i];
		  			var style = '';
		  			if (i % 2 == 0)
		  				style = ' style=\"background : #CCFFFF;\" ';
		  			else
		  				style = ' style=\"background : #FFFFFF;\" ';
		  			content += '<tr class=\"rowContent\"' + style + '>'
		  					+ '<td style=\"width: 50px; text-align: center;\">' + congVan.soDen + '</td>'
							
							+ '<td style=\"width: 100px; text-align: center;\">' + parseDate(congVan.cvNgayNhan) + '</td>'
							+ '<td style=\"width: 50px; text-align: center;\">' + congVan.cvSo + '</td>'	
							+ '<td style=\"width: 50px; text-align: center;\">' + parseDate(congVan.cvNgayNhan) + '</td>'
							+ '<td style=\"text-align: left; width: 300px;\">' + congVan.mucDich.mdTen + '</td>'
							+ '<td style=\"text-align: left; width: 100px;\">' + congVan.donVi.dvTen + '</td>'
							+ '<td style=\"width: 50px;\">' + congVan.trichYeu + '</td>'
							+ '<td style=\"text-align: left; width: 100px;\">' + congVan.butPhe + '</td>'
							+ '<td style=\"text-align: left; width: 150px;\">' + congVan.trangThai.ttTen + '</td>'
							+ '</tr>';
		  		}
		  		$('#view-table table tr:last').after(content);
			}
	  	}
	});
};
$(document).ready(function(){
	$('#xem').click(function(){
		loadBaoCao();
		return false;
	});
});
