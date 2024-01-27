function addToCart(id) {
	fetch(`http://localhost:8080/api/gio-hang/addSanPham?id=${id}`, {
		method: 'GET',
		headers: {'Content-Type': 'application/json'}
	})
		.then(response => {
			if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
			return response.json();
		})
		.then(result => {
			if (result.status === "false") {
				window.alert("Out of stock");
			} else {
				window.alert("Added to Cart");
			}
		})
		.catch(error => {
			alert("Error: " + error.message);
			console.log("Error", error);
		});
}
