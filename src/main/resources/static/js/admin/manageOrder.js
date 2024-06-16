$(document).ready(function () {

    // load first page on the first time opening page
    getAllOrdersByPage(1);

    function getAllOrdersByPage(page) {
        const data = {status: $('#status').val(), fromDate: $('#fromDate').val(), toDate: $('#toDate').val()};
        $.ajax({
            type: "GET",
            data: data,
            contentType: "application/json",
            url: "http://localhost:8080/api/order/all" + '?page=' + page,
            success: function (result) {
                $.each(result.content, function (i, order) {
                    // calculate order gross value
                    let sum = 0;
                    const check = order.orderStatus === "Completed" || order.orderStatus === "Waiting for approval";
                    if (check) {
                        $.each(order.orderDetailsList, function (i, details) {
                            sum += details.cost * details.receivedQuantity;
                        });
                    } else {
                        $.each(order.orderDetailsList, function (i, details) {
                            sum += details.cost * details.orderQuantity;
                        });
                    }

                    let orderEntry = '<tr>' +
                        '<td>' + order.id + '</td>' +
                        '<td>' + order.receiver + '</td>' +
                        '<td>' + order.orderStatus + '</td>' +
                        '<td>' + sum + '</td>' +
                        '<td>' + order.orderDate + '</td>' +
                        '<td>' + order.deliveryDate + '</td>' +
                        '<td>' + order.receivedDate + '</td>' +
                        '<td>' + '<input type="hidden" class="donHangId" value=' + order.id + '>' + '</td>' +
                        '<td><button class="btn btn-warning btnOrderDetail" >Details</button>';
                    if (order.orderStatus === "Waiting for Delivery" || order.orderStatus === "Delivering") {
                        orderEntry += ' &nbsp;<button class="btn btn-primary btnAssignDeliverer">Assign</button>' +
                            ' &nbsp;<button class="btn btn-danger btnCancelOrder">Cancel order</button>';
                    } else if (order.orderStatus === "Waiting for approval") {
                        orderEntry += ' &nbsp;<button class="btn btn-primary btnUpdateOrder" >Update</button> </td>';
                    }

                    $('.donHangTable tbody').append(orderEntry);

                    $('td').each(function (i) {
                        if ($(this).html() === 'null') {
                            $(this).html('');
                        }
                    });
                });

                if (result.totalPages > 1) {
                    for (let numberPage = 1; numberPage <= result.totalPages; numberPage++) {
                        const li = '<li class="page-item "><a class="pageNumber">' + numberPage + '</a></li>';
                        $('.pagination').append(li);
                    }


                    // active page pagination
                    $(".pageNumber").each(function (index) {
                        if ($(this).text() === page) {
                            $(this).parent().removeClass().addClass("page-item active");
                        }
                    });
                }
            },
            error: function (e) {
                alert("Error fetching orders from db! ");
                console.log("Error", e);
            }
        });
    }


    // event - clicking on order assigning button
    $(document).on('click', '.btnAssignDeliverer', function (event) {
        event.preventDefault();
        const orderID = $(this).parent().prev().children().val();
        $("#donHangId").val(orderID);
        console.log(orderID);
        $("#phanCongModal").modal();
    });

    $(document).on('click', '#btnAssignDelivererSubmit', function (event) {
        const email = $("select[name=deliver]").val();
        const orderID = $("#donHangId").val();
        console.log(orderID);
        ajaxPostPhanCongDonHang(email, orderID)

    });

    function ajaxPostPhanCongDonHang(email, orderID) {

        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/order/assign?deliver=" + email + "&orderID=" + orderID,
            enctype: 'multipart/form-data',

            success: function (response) {
                alert("Assigning order successfully");
                const status = $('#status').val();
                location.href = window.location.href;
            },
            error: function (e) {
                alert("Error assigning deliverer to an order")
                console.log("ERROR: ", e);
            }
        });
    }

    $(document).on('click', '#btnFilterOrder', function (event) {
        event.preventDefault();
        resetData();
    });

    // reset table after post, put, filter
    // TODO: quick fix since change in sidebar mean the old code no longer work
    function resetData() {
        // const page = $('li.active').children().text();
        $('.donHangTable tbody tr').remove();
        $('.pagination li').remove();
        // ajaxGet(page);
        getAllOrdersByPage(1);
    }

    // event - clicking on order paging
    $(document).on('click', '.pageNumber', function (event) {
//		event.preventDefault();
        const page = $(this).text();
        $('.donHangTable tbody tr').remove();
        $('.pagination li').remove();
        getAllOrdersByPage(page);
    });

    // event - clicking on order detail button
    $(document).on('click', '.btnOrderDetail', function (event) {
        event.preventDefault();

        const orderID = $(this).parent().prev().children().val();
//		console.log(donHangId);
        const href = "http://localhost:8080/api/order/" + orderID;
        $.get(href, function (order) {
            $('#maDonHang').text("Order ID: " + order.id);
            $('#hoTenNguoiNhan').text("Người nhận: " + order.receiver);
            $('#sdtNhanHang').text("Phone num.: " + order.receivedPhone);
            $('#diaChiNhan').text("Address: " + order.receiveAddress);
            $('#trangThaiDonHang').text("Order status:" + order.orderStatus);
            $("#ngayDatHang").text("Order date: " + order.orderDate);

            if (order.deliveryDate != null) {
                $("#ngayShipHang").text("Delivery date: " + order.deliveryDate);
            }

            if (order.receivedDate != null) {
                $("#ngayNhanHang").text("Retrieval date: " + order.receivedDate);
            }

            if (order.note != null) {
                $("#ghiChu").html("<strong>Note</strong>:<br> " + order.note);
            }

            if (order.orderer != null) {
                $("#nguoiDat").html("<strong>Ordering party</strong>:  " + order.orderer.fullName);
            }

            if (order.deliver != null) {
                $("#deliver").html("<strong>Deliver</strong>: " + order.deliver.fullName);
            }

            const check = order.orderStatus === "Completed" || order.orderStatus === "Waiting for approval";
            if (check) {
                $('.chiTietTable').find('thead tr').append('<th id="soLuongNhanTag" class="border-0 text-uppercase small font-weight-bold">Receiving quantity</th>');
            }
            // add table
            let sum = 0; // order gross value
            let no = 1;
            $.each(order.orderDetailsList, function (i, details) {
                let rowDetails = '<tr>' +
                    '<td>' + no + '</td>' +
                    '<td>' + details.product.productName + '</td>' +
                    '<td>' + details.cost + '</td>' +
                    '<td>' + details.orderQuantity + '</td>';

                if (check) {
                    rowDetails += '<td>' + details.receivedQuantity + '</td>';
                    sum += details.cost * details.receivedQuantity;
                } else {
                    sum += details.cost * details.orderQuantity;
                }

                $('.chiTietTable tbody').append(rowDetails);
                no++;
            });

            $("#tongTien").text("Total: " + sum);
        });
        $("#chiTietModal").modal();
    });


    // event - clicking on finding order by id
    $(document).on('keyup', '#searchById', function (event) {
        event.preventDefault();
        const orderID = $('#searchById').val();
        console.log(orderID);
        if (orderID !== '') {
            $('.donHangTable tbody tr').remove();
            $('.pagination li').remove();
            const href = "http://localhost:8080/api/order/" + orderID;
            $.get(href, function (order) {
                // order gross
                let sum = 0;
                const check = order.orderStatus === "Completed" || order.orderStatus === "Waiting for approval";

                if (check) {
                    $.each(order.orderDetailsList, function (i, details) {
                        sum += details.cost * details.receivedQuantity;
                    });
                } else {
                    $.each(order.orderDetailsList, function (i, details) {
                        sum += details.cost * details.orderQuantity;
                    });
                }

                let orderEntry = '<tr>' +
                    '<td>' + order.id + '</td>' +
                    '<td>' + order.receiver + '</td>' +
                    '<td>' + order.orderStatus + '</td>' +
                    '<td>' + sum + '</td>' +
                    '<td>' + order.orderDate + '</td>' +
                    '<td>' + order.deliveryDate + '</td>' +
                    '<td>' + order.receivedDate + '</td>' +
                    '<td>' + '<input type="hidden" id="donHangId" value=' + order.id + '>' + '</td>' +
                    '<td><button class="btn btn-primary btnOrderDetail" >Detail</button>';

                if (order.orderStatus === "Waiting for Delivery" || order.orderStatus === "Delivering") {
                    orderEntry += ' &nbsp;<button class="btn btn-danger btnAssignDeliverer">Assign</button>';
                } else if (order.orderStatus === "Waiting for approval") {
                    orderEntry += ' &nbsp;<button class="btn btn-warning btnUpdateOrder" >Update</button> </td>';
                }

                $('.donHangTable tbody').append(orderEntry);
                $('td').each(function (i) {
                    if ($(this).html() === 'null') {
                        $(this).html('');
                    }
                });
            });
        } else {
            resetData();
        }
    });

    // event - clicking on update order button
    $(document).on('click', '.btnUpdateOrder', function (event) {
        event.preventDefault();
        const orderID = $(this).parent().prev().children().val();
        $("#idDonHangXacNhan").val(orderID);
        const href = "http://localhost:8080/api/order/" + orderID;
        $.get(href, function (order) {
            // add table
            let no = 1;
            $.each(order.orderDetailsList, function (i, details) {
                const rowDetails = '<tr>' +
                    '<td>' + no + '</td>' +
                    '<td>' + details.product.productName + '</td>' +
                    '<td>' + details.cost + '</td>' +
                    '<td>' + details.orderQuantity + '</td>' +
                    '<td>' + details.receivedQuantity + '</td>' +
                    '<td><input type="hidden" value="' + details.id + '" ></td>';
                $('.chiTietTable tbody').append(rowDetails);
                no++;
            });
            var sum = 0;
            $.each(order.orderDetailsList, function (i, details) {
                sum += details.cost * details.receivedQuantity;
            });
            $("#tongTienXacNhan").text("Total: " + sum);
        });
        $("#capNhatTrangThaiModal").modal();
    });

    $(document).on('click', '#btnXacNhan', function (event) {
        event.preventDefault();
        postCompletionConfirm();
        resetData();
    });

    // post request confirming order completion
    function postCompletionConfirm() {
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/order/update?orderID=" + $("#idDonHangXacNhan").val() + "&adminNote=" + $("#ghiChuAdmin").val(),
            enctype: 'multipart/form-data',
            success: function (response) {
                $("#capNhatTrangThaiModal").modal('hide');
                alert("Confirming order completion successfully");
            },
            error: function (e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }

    $(document).on('click', '.btnCancelOrder', function (event) {
        event.preventDefault();
        const orderID = $(this).parent().prev().children().val();
        const confirmation = confirm("Confirm cancelling this order?");
        if (confirmation) {
            postCancellingOrder(orderID);
            resetData();
        }
    });

    // post request cancelling order
    function postCancellingOrder(orderID) {
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/order/cancel?orderID=" + orderID,
            success: function (response) {
                alert("Cancelling successfully");
            },
            error: function (e) {
                alert("Error when cancelling order!")
                console.log("ERROR: ", e);
            }
        });
    }

    // event when hiding detail modal
    $('#chiTietModal,#capNhatTrangThaiModal').on('hidden.bs.modal', function (e) {
        e.preventDefault();
        $("#chiTietForm p").html(""); // reset p tags
        $("#capNhatTrangThaiForm h4").text("");
        $("#ghiChuAdmin").text("");
        $('.chiTietTable #soLuongNhanTag').remove();
        $('.chiTietTable tbody tr').remove();
        $('.chiTietCapNhatTable tbody tr').remove();
    });
});