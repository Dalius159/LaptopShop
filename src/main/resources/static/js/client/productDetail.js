document.addEventListener('DOMContentLoaded', function () {
	document.querySelector(".add-to-cart").addEventListener('click', function () {
		fetchAddToCart(document.getElementById("prod_id").textContent);
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

	var disqus_config = function () 
	{
		this.page.url = 'http://127.0.0.1/' + product_id;  // Replace PAGE_URL with your page's canonical URL variable
		this.page.identifier = disqus_seed + product_id; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
	};

	var disqus_url = 'https://laptopshop-1.disqus.com/embed.js';
	(function() { // DON'T EDIT BELOW THIS LINE
	var d = document, s = d.createElement('script');
	s.src = disqus_url;
	s.setAttribute('data-timestamp', +new Date());
	(d.head || d.body).appendChild(s);
	})();
});
