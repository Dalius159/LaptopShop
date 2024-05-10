import bootstrap from "../../bootstrap/bootstrap.min";

document.addEventListener("DOMContentLoaded", function () {
    // load first page by default
    ajaxGet(1);

    // reset table after request
    function resetData() {
        const activePage = document.querySelector('li.active').textContent.trim();
        document.querySelectorAll('.donHangTable tbody tr').forEach(function (row) {
            row.remove();
        });
        document.querySelectorAll('.pagination li').forEach(function (li) {
            li.remove();
        });
        ajaxGet(activePage);
    }


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
                result.content.forEach(function (orderPage_respose) {
                    let sum = 0;
                    const check = orderPage_respose.orderStatus === "Hoàn thành" || orderPage_respose.orderStatus === "Chờ duyệt";
                    orderPage_respose.orderDetailsList.forEach(function (detail) {
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
                    } else if (orderPage_respose.orderStatus === "Waiting for approval") {
                        orderRow += ' &nbsp;<button class="btn btn-primary btnCapNhat">Update</button> </td>';
                    }

                    document.querySelector('.donHangTable tbody').insertAdjacentHTML('beforeend', orderRow);
                });

                document.querySelectorAll('td').forEach(function (td) {
                    if (td.innerHTML === 'null') {
                        td.innerHTML = '';
                    }
                });

                if (result.totalPages > 1) {
                    for (var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
                        var li = '<li class="page-item"><a class="pageNumber">' + numberPage + '</a></li>';
                        document.querySelector('.pagination').insertAdjacentHTML('beforeend', li);
                    }

                    document.querySelectorAll('.pageNumber').forEach(function (pageNumber) {
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

    document.addEventListener('click', function (event) {
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

    document.addEventListener('click', async function (event) {
        if (event.target && event.target.id === 'btnPhanCongSubmit') {
            const email = document.querySelector('select[name=shipper]').value;
            const orderID = document.getElementById('donHangId').value;
            console.log(orderID);
            try {
                const response = await ajaxPostPhanCongDonHang(email, orderID);
                if (response.ok) {
                    alert("Asking delivery order successfully");
                    // reload page
                    location.href = window.location.href;
                } else {
                    throw new Error('Network response was not ok');
                }
            } catch (error) {
                alert("Error!");
                console.error("Error in handling AJAX call:", error);
            }
        }
    });

    // on clicking on order pagination
    async function ajaxPostPhanCongDonHang(email, orderID) {
        try {
            return await fetch(`http://localhost:8080/laptopshop/api/order/assign?shipper=${email}&orderID=${orderID}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'enctype': 'multipart/form-data'
                },
                body: JSON.stringify({})
            });
        } catch (error) {
            console.error("ERROR: ", error);
            throw error; // Re-throw the error for the caller to handle
        }
    }

    // on clicking on order details button
    document.addEventListener('click', function (event) {
        if (event.target && event.target.classList.contains('btnChiTiet')) {
            event.preventDefault();

            const donHangId = event.target.parentElement.previousElementSibling.firstElementChild.value;
            // console.log(donHangId);
            const href = "http://localhost:8080/laptopshop/api/order/" + donHangId;
            fetch(href)
                .then(response => response.json())
                .then(function (order) {
                    document.getElementById('maDonHang').textContent = "Order ID: " + order.id;
                    document.getElementById('hoTenNguoiNhan').textContent = "Receiver: " + order.receiver;
                    document.getElementById('sdtNhanHang').textContent = "Phone num.: " + order.receivedPhone;
                    document.getElementById('diaChiNhan').textContent = "Adress: " + order.receiveAddress;
                    document.getElementById('trangThaiDonHang').textContent = "Status: " + order.orderStatus;
                    document.getElementById('ngayDatHang').textContent = "Ordering date: " + order.orderDate;

                    if (order.deliveryDate !== null) {
                        document.getElementById('ngayShipHang').textContent = "Delivery date: " + order.deliveryDate;
                    }

                    if (order.receivedDate !== null) {
                        document.getElementById('ngayNhanHang').textContent = "Receiving date: " + order.receivedDate;
                    }

                    if (order.note !== null) {
                        document.getElementById('ghiChu').innerHTML = "<strong>Note</strong>:<br> " + order.note;
                    }

                    if (order.orderer !== null) {
                        document.getElementById('nguoiDat').innerHTML = "<strong>Purchaser</strong>:  " + order.orderer.fullName;
                    }

                    if (order.shipper !== null) {
                        document.getElementById('shipper').innerHTML = "<strong>Shipper</strong>: " + order.shipper.fullName;
                    }

                    let check = order.orderStatus === "Completed" || order.orderStatus === "Waiting for approval";
                    if (check) {
                        document.querySelector('.chiTietTable thead tr').innerHTML += '<th id="soLuongNhanTag" class="border-0 text-uppercase small font-weight-bold"> SỐ LƯỢNG NHẬN </th>';
                    }

                    let sum = 0; // order gross value
                    let no = 1;
                    order.orderDetailsList.forEach(function (detail) {
                        let rowDetail = '<tr>' +
                            '<td>' + no + '</td>' +
                            '<td>' + detail.product.productName + '</td>' +
                            '<td>' + detail.cost + '</td>' +
                            '<td>' + detail.orderQuantity + '</td>';

                        if (check) {
                            rowDetail += '<td>' + detail.orderQuantity + '</td>';
                            sum += detail.cost * detail.receivedQuantity;
                        } else {
                            sum += detail.donGia * detail.orderQuantity;
                        }

                        document.querySelector('.chiTietTable tbody').innerHTML += rowDetail;
                        no++;
                    });

                    document.getElementById('tongTien').textContent = "Tổng : " + sum;
                })
                .catch(function (error) {
                    console.error('Error:', error);
                });

            const modal = document.getElementById('chiTietModal');
            if (typeof modal !== 'undefined' && modal !== null) {
                new bootstrap.Modal(modal).show();
            }
        }
    });

    // on clicking to search by id
    document.addEventListener('keyup', function (event) {
        if (event.target.matches('#searchById')) {
            event.preventDefault();
            var orderID = document.getElementById('searchById').value;
            console.log(orderID);
            if (orderID !== '') {
                document.querySelectorAll('.donHangTable tbody tr').forEach(function (row) {
                    row.remove();
                });
                document.querySelectorAll('.pagination li').forEach(function (li) {
                    li.remove();
                });
                var href = "http://localhost:8080/laptopshop/api/order/" + orderID;
                fetch(href)
                    .then(response => response.json())
                    .then(function (order) {
                        var sum = 0;
                        var check = order.orderStatus === "Completed" || order.trangThaiDonHang === "Waiting for approval";

                        if (check) {
                            order.orderDetailsList.forEach(function (detail) {
                                sum += detail.cost * detail.receivedQuantity;
                            });
                        } else {
                            order.orderDetailsList.forEach(function (detail) {
                                sum += detail.cost * detail.orderQuantity;
                            });
                        }

                        var donHangRow = '<tr>' +
                            '<td>' + order.id + '</td>' +
                            '<td>' + order.receiver + '</td>' +
                            '<td>' + order.orderStatus + '</td>' +
                            '<td>' + sum + '</td>' +
                            '<td>' + order.orderDate + '</td>' +
                            '<td>' + order.deliveryDate + '</td>' +
                            '<td>' + order.receivedDate + '</td>' +
                            '<td width="0%">' + '<input type="hidden" id="donHangId" value=' + order.id + '>' + '</td>' +
                            '<td><button class="btn btn-primary btnChiTiet" >Detail</button>';

                        if (order.orderStatus === "Waiting for delivery" || order.orderStatus === "Delivering") {
                            donHangRow += ' &nbsp;<button class="btn btn-danger btnPhanCong">Assign</button>';
                        } else if (order.orderStatus === "Waiting for approval") {
                            donHangRow += ' &nbsp;<button class="btn btn-warning btnCapNhat" >Update</button> </td>';
                        }

                        document.querySelector('.donHangTable tbody').insertAdjacentHTML('beforeend', donHangRow);
                        document.querySelectorAll('td').forEach(function (td) {
                            if (td.innerHTML === 'null') {
                                td.innerHTML = '';
                            }
                        });
                    });
            } else {
                resetData();
            }
        }
    });

    // on clicking on update order button
    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('btnCapNhat')) {
            event.preventDefault();
            const orderID = event.target.parentElement.previousElementSibling.querySelector('input').value;
            document.getElementById('idDonHangXacNhan').value = orderID;
            fetch(`http://localhost:8080/laptopshop/api/order/${orderID}`)
                .then(response => response.json())
                .then(function (donHang) {
                    let no = 1;
                    order.orderDetailsList.forEach(function (orderDetails) {
                        const rowDetails = '<tr>' +
                            '<td>' + no + '</td>' +
                            '<td>' + orderDetails.product.productName + '</td>' +
                            '<td>' + orderDetails.cost + '</td>' +
                            '<td>' + orderDetails.orderQuantity + '</td>' +
                            '<td>' + orderDetails.receivedQuantity + '</td>' +
                            '<td><input type="hidden" value="' + orderDetails.id + '" ></td>';
                        document.querySelector('.chiTietTable tbody').insertAdjacentHTML('beforeend', rowDetails);
                        no++;
                    });
                    var sum = 0;
                    order.orderDetailsList.forEach(function (orderDetails) {
                        sum += orderDetails.cost * orderDetails.receivedQuantity;
                    });
                    document.getElementById('tongTienXacNhan').textContent = "Total : " + sum;
                });
            document.getElementById('capNhatTrangThaiModal').style.display = 'block';
        }
    });

    // on clicking on confirming completing order
    document.addEventListener('click', async function (event) {
        if (event.target.matches('#btnXacNhan')) {
            event.preventDefault();
            try {
                const response = await
                    fetch(`http://localhost:8080/laptopshop/api/order/update` +
                        `?orderID=${document.getElementById('idDonHangXacNhan').value}` +
                        `&adminNote=${document.getElementById('ghiChuAdmin').value}`,
                        {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                        });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const modal = document.getElementById('capNhatTrangThaiModal');
                const modalBackdrop = document.getElementsByClassName('modal-backdrop')[0];
                modal.style.display = 'none';       // Hide modal
                modalBackdrop.remove();             // TODO: Remove backdrop, might be redundant

                alert("Order completed successfully");
                resetData();
            } catch (error) {
                console.error('Error at confirming order button:', error);
                alert("Error!");
            }
        }
    });

    document.addEventListener('click', async function (event) {
        if (event.target.matches('.btnHuy')) {
            event.preventDefault();
            const orderID = event.target.parentElement.previousElementSibling.querySelector('input').value;
            const confirmation = confirm("Do you want to cancel this order?");
            if (confirmation) {
                try {
                    const response = await fetch(`http://localhost:8080/laptopshop/api/order/cancel?orderID=${orderID}`, {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'}
                    });
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    alert("Order Cancelled successfully");
                    resetData();
                } catch (error) {
                    console.error('Error at order cancel button:', error);
                    alert("Error!");
                }
            }
        }
    });

    document.getElementById('chiTietModal').addEventListener('hidden.bs.modal', function (e) {
        e.preventDefault();
        document.querySelectorAll("#chiTietForm p").forEach(function(element) {
            element.innerHTML = "";
        });
        document.querySelectorAll("#chiTietForm h4").forEach(function(element) {
            element.textContent = "";
        });
        document.getElementById("ghiChuAdmin").textContent = "";
        document.querySelectorAll('.chiTietTable #soLuongNhanTag').forEach(function(element) {
            element.remove();
        });
        document.querySelectorAll('.chiTietTable tbody tr').forEach(function(row) {
            row.remove();
        });
    });

    document.getElementById('capNhatTrangThaiModal').addEventListener('hidden.bs.modal', function (e) {
        e.preventDefault();
        document.querySelectorAll("#capNhatTrangThaiForm h4").forEach(function(element) {
            element.textContent = "";
        });
        document.getElementById("ghiChuAdmin").textContent = "";
        document.querySelectorAll('.chiTietCapNhatTable tbody tr').forEach(function(row) {
            row.remove();
        });
    });


})