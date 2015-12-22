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
	document.getElementById("loading").style.display="block";
	document.body.style.cursor = "wait";
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
	  		document.getElementById("loading").style.display="none";
	  		document.body.style.cursor = "auto";
	  	}
	});
	
	count ++;
};
function restoreData(){
//	var id = $("#id:checked").val();
	
	var idList = [];
//	$.each($("input[name='clMa']:checked"), function(){            
//		clMaList.push($(this).val());
//    });
	$.each($("#id:checked"), function(){            
		idList.push($(this).val());
    });
	if (idList.length > 1)
		alert("Bạn chỉ được chọn 1 dữ liệu để phục hồi!!!");
	else if (idList.length < 1)
		alert("Bạn phải chọn 1 dữ liệu để phục hồi!!!");
	else {
		document.getElementById("loading").style.display="block";
		document.body.style.cursor = "wait";
		$.ajax({
			url: getRoot() +  "/restoreData.html",	
		  	type: "GET",
		  	dateType: "JSON",
		  	contentType:"application/json",
		  	mimeType: 'application/json',
		  	data: { "id": idList[0]},
		  	success: function(status) {
		  		document.getElementById("loading").style.display="none";
		  		document.body.style.cursor = "auto";
//		  		document.element.
		  		if (status == "success")
		  			alert("Phục hồi dữ liệu thành công");
		  		else
		  			alert("Phục hồi dữ liệu thất bại");
		  	}
		});
		
	}
//	
//	location.reload();
};
function loadPage(pageNumber){
		
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
				url: getRoot() +  "/loadPageBackup.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "pageNumber": page},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(objectList) {
			  		var size = objectList[1];
			  		var backupList = objectList[0];
			  		alert(backupList);
			  		var length = backupList.length;
			  		$('#view-table table .rowContent').remove();
						for(i = 0;i < length; i++ ) {
							var backupInfo = backupList[i];
							var cells = '';
							var style = '';
							if (i % 2 == 0)
								style = 'style=\"background : #CCFFFF;\"';
//							str = '<tr class=\"rowContent\" ' + style + '>'
	 					cells = '<td class=\"left-column\"><input type=\"checkbox\" name=\"id\"'
	 							+ ' value=\"' + backupInfo.stt + '\" id=\"id\" class=\"checkbox\"></td>'
	 							+ '<td class=\"col\">' + backupInfo.thoiGian + '</td>'
	 							+ '<td class=\"col\">' + backupInfo.moTa + '</td>';
	 					var row = '<tr ' +style + 'class = \"rowContent\">' + cells + '</tr>';
	 					$('#view-table table tr:first').after(row);
						}
					var button = '';
				if(pageNumber == 'Next') {
					for (var i = 0; i < 10; i++) {
						var t = ((p -1) * 5 + i + 1);
						
						button += '<input type=\"button\" value=\"' + ((p -1) * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPage(' + ((p -1)*5 + i)  +')\">&nbsp;';
						
						if (t > size)
							break;
					}
					button = '<input type=\"button\" class=\"pageMove\" value=\"<<Trước\" onclick= \"loadPage(\'Previous\')\">&nbsp;'  + button;
					if ((p + 1) * 5 < size)
						button += '<input type=\"button\" class=\"pageMove\" value=\"Sau>>\" onclick= \"loadPage(\'Next\');\">';
					$('#paging').html(button);
					$('.page')[5].focus();
				} else if (pageNumber == 'Previous'){
					if (p > 0)
						p = p -1;
					for (var i = 0; i < 10; i++)
						button += '<input type=\"button\" value=\"' + (p * 5 + i + 1) + '\" class=\"page\" onclick= \"loadPage(' + (p * 5 + i)  +')\">&nbsp;';
					
					button = button + '<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPage(\'Next\');\">';
					if (p >= 1)
						button = '<input type=\"button\" class=\"pageMove\" value=\"<< Trước\" onclick= \"loadPage(\'Previous\')\">&nbsp;' + button;
					$('#paging').html(button);	
					$('.page')[4].focus();
				}
			  	}
			});
}
$(document).ready(function(){
	$("#preBackup").click(function(){
		showForm('backup-form', true);
	});
	return false;
});
//backupData/

$(document).ready(function(){
	$("#backup-form").submit(function(){
		backupData();
		return false;
	});
	
});

$(document).ready(function(){
	
	$("#main-form").submit(function(){
//		document.getElementById("loading").style.display="block";
		$("#main-form").children().prop('disabled',true);
		restoreData();
//		document.getElementById("loading").style.display="none";
		return false;
	});
});

$(document).ready(function(){
	$("#exitBackup").click(function(){
		showForm("backup-form", false);
	});
});
