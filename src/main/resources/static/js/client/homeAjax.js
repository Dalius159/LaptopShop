function addToCart(id) {
	fetch(`http://localhost:8080/laptopshop/api/gio-hang/addSanPham?id=${id}`, {
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

document.addEventListener('DOMContentLoaded', function () {
	ajaxGet();

	function ajaxGet() {
		fetch('http://localhost:8080/laptopshop/api/san-pham/latest', {
			method: 'GET',
			headers: {'Content-Type': 'application/json'}
		})
			.then(response => {
				if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`);}
				return response.json();
			})
			.then(result => {
				let content = '';
				const section = '<div class="section group">';
				const end_section = '</div>' + '<br>';

				result.forEach((sanpham, i) => {
					if (i !== result.length - 1) {
						if (i % 4 === 0) {
							content = `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${sanpham.id}">
                                    <img style="width: 300px; height: 238px" src="/img/${sanpham.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${sanpham.tenSanPham}</h3>
                                </a>
                                <h3>${accounting.formatMoney(sanpham.donGia)} VND</h3>
                                <button onClick="addToCart(${sanpham.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Shopping Cart
                                </button>
                                <h3></h3>
                            </div>`;
						} else {
							content += `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${sanpham.id}">
                                    <img style="width: 300px; height: 238px" src="/img/${sanpham.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${sanpham.tenSanPham}</h3>
                                </a>
                                <h3>${accounting.formatMoney(sanpham.donGia)} VND</h3>
                                <button onClick="addToCart(${sanpham.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Shopping Cart
                                </button>
                                <h3></h3>
                            </div>`;
							if (i % 4 === 3) {
								content = section + content + end_section;
								document.querySelector('.content-grids').insertAdjacentHTML('beforeend', content);
							}
						}
					} else {
						if (i % 4 === 0) {
							content = `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${sanpham.id}">
                                    <img style="width: 300px; height: 238px" src="/img/${sanpham.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${sanpham.tenSanPham}</h3>
                                </a>
                                <h3>${accounting.formatMoney(sanpham.donGia)} VND</h3>
                                <button onClick="addToCart(${sanpham.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Shopping Cart
                                </button>
                                <h3></h3>
                            </div>`;
							content = section + content + end_section;
							document.querySelector('.content-grids').insertAdjacentHTML('beforeend', content);
						} else {
							content += `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${sanpham.id}">
                                    <img style="width: 300px; height: 238px" src="/img/${sanpham.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${sanpham.tenSanPham}</h3>
                                </a>
                                <h3>${accounting.formatMoney(sanpham.donGia)} VND</h3>
                                <button onClick="addToCart(${sanpham.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Shopping Cart
                                </button>
                                <h3></h3>
                            </div>`;
							content = section + content + end_section;
							document.querySelector('.content-grids').insertAdjacentHTML('beforeend', content);
						}
					}
				});
			})
			.catch(error => {
				alert(`Error: ${error.message}`);
				console.log('Error', error);
			});
	}
});
