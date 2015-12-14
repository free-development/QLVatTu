/**
 * 
 */
count = 0;
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
function backupData(){
	
	var moTa = $("#moTa").val();
	$.ajax({
		url: getRoot() +  "/backupData.html",	
	  	type: "GET",
	  	dateType: "JSON",
	  	contentType:"application/json",
	  	mimeType: 'application/json',
	  	data: { "moTa": moTa},
	  	success: function(backupInfo) {
	  		if (backupInfo == "fail") {
	  			alert("Sao lưu dữ liệu thất bại");
	  		} else {
	  			var style = '';
	  			
	  			if (count % 2 == 1)
	  				style = '\"background: #CCFFFF\"';
	  			var content = '<tr class=\"rowContent\" style=' + style + '> '
	  						+ '<td class=\"left-column\"><input type=\"checkbox\" name=\"id\" '
	  						+ 'value=\"' + backupInfo.stt + '\" id=\"id\" class=\"checkbox\"></td>'
	  						+ '<td class=\"col\">' + backupInfo.thoiGian.substring(9) + '</td>'
	  						+ '<td class=\"col\">' + backupInfo.moTa + '</td>'
	  						+ '</tr>';
	  			$('#view-table table tr:first').after(content);
	  			alert("Sao lưu dữ liệu thành công");
	  		}
	  		showForm("backup-form", false);
	  	}
	});
	count ++;
};
function restoreData(){
//	var id = $("#id:checked").val();
	var idList = []
//	$.each($("input[name='clMa']:checked"), function(){            
//		clMaList.push($(this).val());
//    });
	$.each($("#id:checked"), function(){            
		idList.push($(this).val());
    });
	if (idList.length > 1)
		alert("Bạn chỉ được chọn 1 dữ liệu để phục hồi!!!");
	else {
		$.ajax({
			url: getRoot() +  "/restoreData.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	contentType:"application/json",
		  	mimeType: 'application/json',
		  	data: { "id": idList[0]},
		  	success: function(status) {
		  		if (status == "success")
		  			alert("Phục hồi dữ liệu thành công");
		  		else
		  			alert("Phục hồi dữ liệu thất bại");
		  	}
		});
	}
//	location.reload();
};
$(document).ready(function(){
	$("#preBackup").click(function(){
		showForm('backup-form', true);
	});
	return false;
});
//backupData/

$(document).ready(function(){
	$("#backup-form").submit(function(){
//		document.body.style.cursor= 'img/default.gif';
//		$('#wrapper').dis
		document.getElementById("wrapper").disabled=true;
//		document.getElementById("main-form").disabled=true;
		backupData();
		
		return false;
	});
	
});

$(document).ready(function(){
	$("#main-form").submit(function(){
//		document.getElementById("main-form").disabled=true;
		$("#main-form").children().prop('disabled',true);
		restoreData();
//		location.reload();
		return false;
	});
});

$(document).ready(function(){
	$("#exitBackup").click(function(){
		showForm("backup-form", false);
	});
});