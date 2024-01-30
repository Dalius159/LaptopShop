function changePass()
{
	var old = document.getElementById("old").value;
	var new1 = document.getElementById("new1").value;
	var new2 = document.getElementById("new2").value;
	var flag = 0;
	if(old.length == 0)
	{
		flag = 1;
		document.getElementById("oldWarning").innerHTML = "Can't be empty";
	}
	if(new1.length < 8)
	{
		flag = 1;
		document.getElementById("new1Warning").innerHTML = "Password length must have at least 8 charater";
	}
	if(new1 != new2)
	{
		flag = 1;
		document.getElementById("new2Warning").innerHTML = "Wrong confirm password";
	}
	if(flag == 1)
	{
		return;
	}
	var object = new Object();
	object.oldPassword = old;
	object.newPassword = new1;
	data = JSON.stringify(object)
	$.ajax({
			type: "POST",	
			data: data,	
			contentType : "application/json",
			url: "http://localhost:8080/updatePassword",
			success: function(result){
				if(result.status == "old")
				{
					alert("Wrong old password");
//					window.location.reload();
					return;
				}else{
					alert("Password Changed");
					window.location.href = "/account";
				}
				
			},
			error : function(e){
				alert("Error: ",e);
				console.log("Error" , e );
			}
		});
}