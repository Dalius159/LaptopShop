function changeInformation()
{
	var name = document.getElementById("name").value;
	var phone = document.getElementById("phone").value;
	var address = document.getElementById("address").value;
	var flag = 0;
	if(name.length == 0)
	{
		flag = 1;
		document.getElementById("nameWarning").innerHTML = "Name can't be empty";
	}
	if(phone.length == 0)
	{
		flag = 1;
		document.getElementById("phoneWarning").innerHTML = "Phone number can't be empty";
	}
	if(address.length == 0)
	{
		flag = 1;
		document.getElementById("addressWarning").innerHTML = "Adress can't be empty";
	}
	var pat = "[0-9]+{9,10}"
	if(!/^([0-9]{9,10})$/.test(phone))
	{
		flag = 1;
		document.getElementById("phoneWarning").innerHTML = "Your phone call shold have 9 or 10 number";
	}
	if(flag == 1)
	{
		return;
	}
	var send = new Object();
	send.hoTen = name;
	send.soDienThoai = phone;
	send.diaChi = address;
	var data = JSON.stringify(send)
	$.ajax({
			type: "POST",	
			data: data,	
			contentType : "application/json",
			url: "http://localhost:8080/updateInfo",
			success: function(result){
				alert("Thông tin đã cập nhật");
				window.location.href = "/account";
			},
			error : function(e){
				alert("Error: ",e);
				console.log("Error" , e );
			}
		});
	
}