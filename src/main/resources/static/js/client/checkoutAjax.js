calculateOrder()

function calculateOrder() {
	const element = document.getElementsByClassName("total");
	let res = 0;
	for (let i = 0; i < element.length; i++) {
		res = res + parseInt(element[i].textContent);
	}
	const element2 = document.getElementById("ordertotal");
	element2.innerHTML = accounting.formatMoney(res);

	const element3 = document.getElementById("tongGiaTri");
	element3.setAttribute("value", res);
	if (res === 0) {
		document.getElementById("submit").disabled = true;
	}
}
