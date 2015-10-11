/**
 * 
 */
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
		function preUpdateNsx(formId, check) {
			var nsxMa = $('input:checkbox[name=nsxMa]:checked').val();
			$.ajax({
				url: "/QuanLyVatTu/preEditNsx.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "nsxMa": nsxMa},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(nsx) {
				  	$('input:text[name=nsxMaUpdate]').val(nsx.nsxMa);
				  	$('input:text[name=nsxTenUpdate]').val(nsx.nsxTen);
			  		showForm(formId, check);	
			  		
			  	}
			});
		}
		function confirmDelete(){
			var nsxMa = $('input:checkbox[name=nsxMa]:checked').val();
			if (confirm('Bạn có chắc xóa' + nsxMa))
				deleteNsx(nsxMa);
		}
 		
	 	 function deleteNsx(nsxMa) {
			$.ajax({
				url: "/QuanLyVatTu/deleteNsx.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "nsxMa": nsxMa},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	success: function() {
				  	alert(nsxMa + "da bi xoa");
							$('table tr').has('input[name="nsxMa"]:checked').remove();
			    } 
			});  
		} 
	 	
	 	function addNsx() {
			var nsxMa = $('#add-form input:text[name=nsxMa]').val();
			var nsxTen = $('#add-form input:text[name=nsxTen]').val();
			$.ajax({
				url: "/QuanLyVatTu/addNsx.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "nsxMa": nsxMa, "nsxTen": nsxTen},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(nsx) {
				  	$('input:text[name=nsxMa]').val(nsx.nsxMa);
				  	$('input:text[name=nsxTen]').val(nsx.nsxTen);
			  		$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"nsxMa\" value=\"' +nsx.nsxMa + '\"</td><td class=\"col\">'+ nsxMa +'</td><td class=\"col\">' + nsxTen+'</td></tr>');
			  		$('#add-form input:text[name=nsxMa]').val('');
					$('#add-form input:text[name=nsxTen]').val('');
			  		showForm("add-form", false);	
			  	}
			});
		}
	 	
	 	function confirmUpdateNsx(){
			var nsxMaUpdate = $('input:text[name=nsxMaUpdate]').val();
			var nsxTenUpdate = $('input:text[name=nsxTenUpdate]').val();
			if (confirm('Bạn có chắc thay doi noi san xuat co ma ' + nsxMaUpdate))
				updateNsx(nsxMaUpdate, nsxTenUpdate);
		}
	 	function updateNsx(nsxMaUpdate, nsxTenUpdate) {

			$.ajax({
				url: "/QuanLyVatTu/updateNsx.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "nsxMaUpdate": nsxMaUpdate, "nsxTenUpdate": nsxTenUpdate},
			  	contentType: 'application/json',
			    mimeType: 'application/json',
			  	
			  	success: function(nsx) {
			  		$('table tr').has('input[name="nsxMa"]:checked').remove();
			  		$('#view-table table tr:first').after('<tr><td class=\"left-column\"><input type=\"checkbox\" name=\"nsxMa\" value=\"' +nsxMaUpdate + '\"</td><td class=\"col\">'+ nsxMaUpdate +'</td><td class=\"col\">' + nsxTenUpdate+'</td></tr>');
			  		$('input:text[name=nsxMaUpdate]').val('');
					nsxTenUpdate = $('input:text[name=nsxTenUpdate]').val('');
			  		showForm("update-form", false);	
			  	}
			});
		}
