function changeInformation() {
    const name = document.getElementById("name").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;
    let flag = 0;
    if (name.length === 0) {
        flag = 1;
        document.getElementById("nameWarning").innerHTML = "Name can't be empty";
    }
    if (phone.length === 0) {
        flag = 1;
        document.getElementById("phoneWarning").innerHTML = "Phone number can't be empty";
    }
    if (address.length === 0) {
        flag = 1;
        document.getElementById("addressWarning").innerHTML = "Address can't be empty";
    }
    const pat = "[0-9]+{9,10}";
    if (!/^([0-9]{9,10})$/.test(phone)) {
        flag = 1;
        document.getElementById("phoneWarning").innerHTML = "Your phone call should have 9 or 10 number";
    }
    if (flag === 1) {
        return;
    }
    const send = {};
    send.full_name = name;
    send.phone_number = phone;
    send.address = address;
    const data = JSON.stringify(send);

    fetch("http://localhost:8080/updateInfo", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                alert("Information updated");
                window.location.href = "/account";
            } else {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
        })
        .catch(error => {
            alert("Error: " + error.message);
            console.log("Error", error);
        });

}