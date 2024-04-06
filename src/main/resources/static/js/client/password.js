function changePass() {
    const old = document.getElementById("old").value;
    var new1 = document.getElementById("new1").value;
    var new2 = document.getElementById("new2").value;
    var flag = 0;
    if (old.length == 0) {
        flag = 1;
        document.getElementById("oldWarning").innerHTML = "Can't be empty";
    }
    if (new1.length < 8) {
        flag = 1;
        document.getElementById("new1Warning").innerHTML = "Password length must have at least 8 charater";
    }
    if (new1 != new2) {
        flag = 1;
        document.getElementById("new2Warning").innerHTML = "Wrong confirm password";
    }
    if (flag == 1) {
        return;
    }
    var object = {};
    object.oldPassword = old;
    object.newPassword = new1;
    data = JSON.stringify(object)
    fetch('http://localhost:8080/updatePassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(result => {
            if (result.status === "old") {
                alert("Wrong old password");
                // window.location.reload();
            } else {
                alert("Password Changed");
                window.location.href = "/account";
            }
        })
        .catch(error => {
            alert("Error: " + error.message);
            console.log("Error", error);
        });
    ;
}