document.addEventListener('DOMContentLoaded', function () {
	document.querySelector(".add-to-cart").addEventListener('click', function () {
		fetchAddToCart(document.getElementById("spid").textContent);
	});

	function fetchAddToCart(id) {
		fetch("http://localhost:8080/api/gio-hang/addSanPham?id=" + id, {
			method: 'GET',
		})
			.then(response => {
				if (!response.ok) { throw new Error("Cannot add to cart: server failed to response"); }
				return response.json();
			})
			.then(result => {
				if (result.status === "false") {
					window.alert("Out of stock");
				} else {
					window.alert("Added to cart");
				}
			})
			.catch(error => {
				alert("Error: " + error.message);
				console.log("Error", error);
			});
	}
});
