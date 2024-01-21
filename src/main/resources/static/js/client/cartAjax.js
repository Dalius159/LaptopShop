calculateOrder()

//	TODO: fix typo
function changeQuantity(id, value, price) {
	fetch(`http://localhost:8080/api/gio-hang/changSanPhamQuanity?id=${id}&value=${value}`, {
		method: 'GET',
	})
		.then(response => {
			if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
			return response.json();
		})
		.then(result => {
			calculatePrice(id, value, price);
			calculateOrder();
			//TODO: debug purpose, remove later
			if (result.status === "success") console.log("changed product ${id} quantity to ${value}");
		})
		.catch(error => {
			alert("Error: " + error.message);
			console.log("Error", error);
		});
}

function deleteFromCart(id) {
	fetch(`http://localhost:8080/api/gio-hang/deleteFromCart?id=${id}`, {
		method: 'GET',
	})
		.then(response => {
			if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
			return response.json();
		})
		.then(result => {
			let element = document.getElementById("item" + id);
			element.parentNode.removeChild(element);
			calculateOrder();
		})
		.catch(error => {
			alert("Error: " + error.message);
			console.log("Error", error);
		});
}


function calculatePrice(id, value, price) {
	const element = document.getElementById("item" + id + "_total");
	element.innerHTML = value * price;
}

function calculateOrder() {
	const element = document.getElementsByClassName("total");
	let res = 0;
	for (let i = 0; i < element.length; i++) {
		res = res + parseInt(element[i].textContent);
	}
	const element2 = document.getElementById("ordertotal");
	element2.innerHTML = accounting.formatMoney(res);
}
