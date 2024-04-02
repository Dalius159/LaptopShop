document.addEventListener('DOMContentLoaded', function () {
	ajaxGet();

	function ajaxGet() {
		fetch('http://localhost:8080/api/san-pham/latest', {
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

				result.forEach((product, i) => {
					if (i !== result.length - 1) {
						if (i % 4 === 0) {
							content = `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${product.id}">
                                    <img style="width: 300px; height: 238px" src="/product_images/${product.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${product.product_name}</h3>
                                </a>
                                <h3>${accounting.formatMoney(product.price)}</h3>
                                <button onClick="addToCart(${product.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Add to cart
                                </button>
                                <h3></h3>
                            </div>`;
						} else {
							content += `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${product.id}">
                                    <img style="width: 300px; height: 238px" src="/product_images/${product.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${product.product_name}</h3>
                                </a>
                                <h3>${accounting.formatMoney(product.price)} </h3>
                                <button onClick="addToCart(${product.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Add to cart
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
                                <a href="sp?id=${product.id}">
                                    <img style="width: 300px; height: 238px" src="/product_images/${product.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${product.product_name}</h3>
                                </a>
                                <h3>${accounting.formatMoney(product.price)} </h3>
                                <button onClick="addToCart(${product.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Add to cart
                                </button>
                                <h3></h3>
                            </div>`;
							content = section + content + end_section;
							document.querySelector('.content-grids').insertAdjacentHTML('beforeend', content);
						} else {
							content += `<div class="grid_1_of_4 images_1_of_4 products-info">
                                <a href="sp?id=${product.id}">
                                    <img style="width: 300px; height: 238px" src="/product_images/${product.id}.png" alt="product_img">
                                    <h3 style="font-weight: bold;">${product.product_name}</h3>
                                </a>
                                <h3>${accounting.formatMoney(product.price)} </h3>
                                <button onClick="addToCart(${product.id})" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-shopping-cart pull-center"></span>Add to cart
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
