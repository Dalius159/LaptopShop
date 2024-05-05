import bootstrap from "../../bootstrap/bootstrap.min";

document.addEventListener("DOMContentLoaded", function() {
    // load first page by default
    ajaxGet(1);

    function ajaxGet(page) {
        const data = {
            status: document.getElementById('status').value,
            fromDate: document.getElementById('fromDate').value,
            toDate: document.getElementById('toDate').value
        };

        const url = "http://localhost:8080/laptopshop/api/order/all" + '?page=' + page;

        fetch(url, {
            method: 'GET',
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
                result.content.forEach(function(orderPage_respose) {
                    let sum = 0;
                    const check = orderPage_respose.orderStatus === "Hoàn thành" || orderPage_respose.orderStatus === "Chờ duyệt";
                    orderPage_respose.orderDetailsList.forEach(function(detail) {
                        sum += check ? detail.cost * detail.receivedQuantity : detail.cost * detail.orderQuantity;
                    });

                    let orderRow = '<tr>' +
                        '<td>' + orderPage_respose.id + '</td>' +
                        '<td>' + orderPage_respose.receiver + '</td>' +
                        '<td>' + orderPage_respose.orderStatus + '</td>' +
                        '<td>' + sum + '</td>' +
                        '<td>' + orderPage_respose.orderDate + '</td>' +
                        '<td>' + orderPage_respose.deliveryDate + '</td>' +
                        '<td>' + orderPage_respose.receivedDate + '</td>' +
                        '<td width="0%"><input type="hidden" class="donHangId" value=' + orderPage_respose.id + '></td>' +
                        '<td><button class="btn btn-warning btnChiTiet">Detail</button>';

                    if (orderPage_respose.orderStatus === "Waiting for delivery" || orderPage_respose.orderStatus === "Delivering") {
                        orderRow += ' &nbsp;<button class="btn btn-primary btnPhanCong">Assign task</button>' +
                            ' &nbsp;<button class="btn btn-danger btnHuy">Cancel order</button>';
                    } else if (orderPage_respose.trangThaiDonHang === "Waiting for approval") {
                        orderRow += ' &nbsp;<button class="btn btn-primary btnCapNhat">Update</button> </td>';
                    }

                    document.querySelector('.donHangTable tbody').insertAdjacentHTML('beforeend', orderRow);
                });

                document.querySelectorAll('td').forEach(function(td) {
                    if (td.innerHTML === 'null') {
                        td.innerHTML = '';
                    }
                });

                if (result.totalPages > 1) {
                    for (var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
                        var li = '<li class="page-item"><a class="pageNumber">' + numberPage + '</a></li>';
                        document.querySelector('.pagination').insertAdjacentHTML('beforeend', li);
                    }

                    document.querySelectorAll('.pageNumber').forEach(function(pageNumber) {
                        if (pageNumber.textContent === page.toString()) {
                            pageNumber.parentElement.classList.add('active');
                        }
                    });
                }
            })
            .catch(error => {
                alert("Error: " + error.message);
                console.error("Error", error);
            });
    }

    document.addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('btnPhanCong')) {
            event.preventDefault();
            const donHangId = event.target.parentElement.previousElementSibling.firstElementChild.value;
            document.getElementById('donHangId').value = donHangId;
            console.log(donHangId);
            const modal = document.getElementById('phanCongModal');
            if (typeof modal !== 'undefined' && modal !== null) {
                const modalInstance = new bootstrap.Modal(modal);
                modalInstance.show();
            }
        }
    });

    document.addEventListener('click', function(event) {
        if (event.target && event.target.id === 'btnPhanCongSubmit') {
            const email = document.querySelector('select[name=shipper]').value;
            const orderId = document.getElementById('donHangId').value;
            console.log(orderId);
            ajaxPostPhanCongDonHang(email, orderId);
        }
    });

    async function ajaxPostPhanCongDonHang(email, donHangId) {
        try {
            const response = await fetch(`http://localhost:8080/laptopshop/api/order/assign?shipper=${email}&donHangId=${donHangId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'enctype': 'multipart/form-data'
                },
                body: JSON.stringify({}) // You can pass data here if needed
            });
            if (response.ok) {
                alert("Asking delivery order successfully");
                const status = document.getElementById('status').value;
                location.href = window.location.href;
            } else {
                throw new Error('Network response was not ok');
            }
        } catch (error) {
            alert("Error!");
            console.error("ERROR: ", error);
        }
    }


});