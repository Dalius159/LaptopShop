function addToCart(id) {
	fetch(`http://localhost:8080/api/cart/addSanPham?id=${id}`)
		.then(response => response.json())
		.then(result => {
			if (result.status === "false") {
				window.alert("Product out of stock");
			} else { window.alert("Added to cart"); }
		})
		.catch(error => {
			alert("Error: " + error.message);
			console.log("Error", error);
		});

	itemCount++;
	document.getElementById('itemCount').innerHTML = itemCount;
	document.getElementById('itemCount').style.display = 'block';
}
