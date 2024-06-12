function sendContact()
{
    var email =  document.getElementById("email").value;
    var content = document.getElementById("content").value;
    var flag = 0;
    if(!/^[^0-9][A-z0-9_]+([.][A-z0-9_]+)*[@][A-z0-9_]+([.][A-z0-9_]+)*[.][A-z]{2,4}$/.test(email))
    {
        document.getElementById("emailWarning").innerHTML = "Wrong Email format";
        flag = 1;
    }
    if(content.length === 0)
    {
        document.getElementById("contentWarning").innerHTML = "Required field";
        flag = 1;
    }
    if(flag === 1)
    {
        return;
    }
    var send = new Object();
    send.contactEmail = email;
    send.contactMessage = content;
    send.status = 'Waiting for respond';
    var data = JSON.stringify(send)
    $.ajax({
        type: "POST",
        data: data,
        contentType : "application/json",
        url: "http://localhost:8080/createContact",
        success: function(result){
            alert("Thanks for contacted us. Leolap will response as soon as posible!");
            window.location.href = "/contact";
        },
        error : function(e){
            alert("Error: ",e);
            console.log("Error" , e );
        }
    });
}