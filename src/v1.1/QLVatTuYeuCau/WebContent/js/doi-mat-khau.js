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
			function confirmDelete(){
				return confirm('Bạn có chắc xóa');
			}
			function checkPassword()
				{
				//alert("Xin chào ");	
//				var msnv = $('input:text[name=msnv]').val();
//				alert(msnv);
				var msnv = $('input:text[name=msnv]').val();
				var passOld = $('input:password[name=passOld]').val();
				var passNew = $('input:password[name=passNew]').val();
				var rePassNew = $('input:password[name=rePassNew]').val();
				if(msnv == ''){
					$('#requireMsnv').html('Vui lòng nhập mã số nhân viên ');
					return false;
				}
				else if(passOld == ''){
					$('#requirePassOld').html('Vui lòng nhập mật khẩu cũ');
					return false;
				}
				else if(passNew == ''){
					$('#requirePassNew').html('Vui lòng nhập mật khẩu mới');
					return false;
				}
				else if(rePassNew == ''){
					$('#requireRePassNew').html('Vui lòng nhập nhập lại mật khẩu mới');
					return false;
				}
				else if(passNew != rePassNew)
					{
						alert("Mật khẩu nhập lại chưa chính xác. Vui lòng kiểm tra lại!");
						return false;
					}
				else
						{
							changePassWord(passNew);
							
						}
				}
			
			function changeMsnv(){
				$('#requireMsnv').html('');
			} 	
			function changePassOld(){
				$('#requirePassOld').html('');
			} 	
			function changePassNew(){
				$('#requirePassNew').html('');
			} 	
			function changeRePassNew(){
				$('#requireRePassNew').html('');
			} 	
			
			function changePassWord(passNew){
				var msnv = $('input:text[name=msnv]').val();
				var passOld = $('input:password[name=passOld]').val();
				$.ajax({
					url: getRoot() +  "/changePass.html",	
				  	type: "GET",
				  	dateType: "JSON",
				  	data: { "msnv": msnv, "passOld": passOld, "passNew": passNew},
				  	contentType: 'application/json',
				    mimeType: 'application/json',
				  	success: function(result) {
				  		//alert(result);
				  		if (result == "authentication error") {
							location.assign("login.jsp");
						} else if(result == "success"){
				  			$('input:text[name=msnv]').val('');
							$('input:password[name=passOld]').val('');
							alert("Đổi mật khẩu thành công!");
							showForm("add-form",false);
				  			//window.location.assign("home");
				  		}
				  		else {
				  			alert('Tên tài khoản hoặc mật khẩu chưa đúng');
				  		}
				  	}
				});
			}