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
		function update(formId, check) {
			var nsxMa = $('input:checkbox[name=nsxMa]').val();
			$.ajax({
				url: "/QLVatTuYeuCau/preEditNsx.html",	
			  	type: "GET",
			  	dateType: "JSON",
			  	data: { "nsxMa": nsxMa},
			  	beforeSend: function(xhr) {
			  		xhr.setRequestHeader("Accept", "application/json");
			  		xhr.setRequestHeader("Content-Type", "application/json");
			  	},
			  	
			  	success: function(preEditNsx) {
				  	alert('Send ok');
// 			  		var respContent = "";
// 			  		var rowToDelete = $(event.target).closest("tr");
// 			  		rowToDelete.remove();
// 			  		respContent += "<span class='success'>Smartphone was deleted: [";
// 			  		respContent += smartphone.producer + " : ";
// 			  		respContent += smartphone.model + " : " ;
// 			  		respContent += smartphone.price + "]</span>";
			  		
// 			  		$("#addForm").html(respContent);   	
// 				
// 			  		showForm(formId, check);	
			  	}
			});
			event.preventDefault();
		}
		function confirmDelete(){
			return confirm('Bạn có chắc xóa');
		}

		 $(document).ready(function() {
// 			 	var nsxMa =  $("input[nsxMa]").val();
// 				var nsxMa =  $("#updateNsx").val();
		      	var nsxMa = $('input:checkbox[name=nsxMa]').val();
				$("#updateNsx").click(function(event) {
		    	  
					$.ajax({
						url: "/QLVatTuYeuCau/preEditNsx.html",	
					  	type: "GET",
					  	data: { "nsxMa": nsxMa},
					  	beforeSend: function(xhr) {
					  		xhr.setRequestHeader("Accept", "application/json");
					  		xhr.setRequestHeader("Content-Type", "application/json");
					  	},
					  	
					  	success: function(smartphone) {
					  		var respContent = "";
					  		var rowToDelete = $(event.target).closest("tr");
					  		
					  		rowToDelete.remove();
					  		
					  		respContent += "<span class='success'>Smartphone was deleted: [";
					  		respContent += smartphone.producer + " : ";
					  		respContent += smartphone.model + " : " ;
					  		respContent += smartphone.price + "]</span>";
					  		
					  		$("#addForm").html(respContent);   		
					  	}
					});
		  
					event.preventDefault();
				});
		       
		}); 