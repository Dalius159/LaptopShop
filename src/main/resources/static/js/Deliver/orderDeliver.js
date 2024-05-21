$(document).ready(function() {

    // load first when coming page
    ajaxGet(1);

    function ajaxGet(page){
        var data = { orderStatus : $('#orderStatus').val(), fromDate: $('#fromDate').val(), toDate: $('#toDate').val(), idDeliver: $('#idDeliver').val()  }
        $.ajax({
            type: "GET",
            data: data,
            contentType : "application/json",
            url: "http://localhost:8080/laptopshop/api/deliver/order/all" + '?page=' + page,
            success: function(result){
                $.each(result.content, function(i, order){
                    // Compute order price
                    var sum = 0;
                    if(order.orderStatus === "Completed" || order.orderStatus === "Waiting for approval" ){
                        $.each(order.orderDetailsList, function(i, detail){
                            sum += detail.cost * detail.receivedQuantity;
                        });
                    } else {
                        $.each(order.orderDetailsList, function(i, detail){
                            sum += detail.cost * detail.orderQuantity;
                        });
                    }
                    var ordersRow = '<tr>' +
                        '<td>' + order.id+ '</td>' +
                        '<td>' + order.receiver + '</td>' +
                        '<td>' + order.orderStatus + '</td>' +
                        '<td>' + sum + '</td>' +
                        '<td>' + order.orderDate + '</td>' +
                        '<td>' + order.deliveryDate + '</td>' +
                        '<td>' + order.receivedDate + '</td>' +
                        '<td width="0%">'+'<input type="hidden" class="orderID" value=' + order.id + '>'+ '</td>'+
                        '<td><button class="btn btn-primary btnDetail" >Detail</button>';
                    if(order.orderStatus === "Delivering"){
                        ordersRow += ' &nbsp;<button class="btn btn-warning btnUpdate">Update</button> </td>';
                    }


                    $('.orderTable tbody').append(ordersRow);

                    $('td').each( function(i){
                        if ($(this).html() === 'null'){
                            $(this).html('');
                        }
                    });
                });

                if(result.totalPages > 1 ){
                    for(var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
                        var li = '<li class="page-item "><a class="pageNumber">'+numberPage+'</a></li>';
                        $('.pagination').append(li);
                    }

                    // active page pagination
                    $(".pageNumber").each(function(index){
                        if($(this).text() === page){
                            $(this).parent().removeClass().addClass("page-item active");
                        }
                    });
                }
            },
            error : function(e){
                alert("Error: ",e);
                console.log("Error" , e );
            }
        });
    }

    $(document).on('click', '#btnBrowseOrder', function (event) {
        event.preventDefault();
        resetData();
    });


    // event when click order pagination
    $(document).on('click', '.pageNumber', function (event){
//		event.preventDefault();
        var page = $(this).text();
        $('.orderTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    });

    // event when click field find order by id
    $(document).on('keyup', '#searchById', function (event){
        event.preventDefault();
        var orderID = $('#searchById').val();
        console.log(orderID);
        if(orderID !== ''){
            $('.orderTable tbody tr').remove();
            $('.pagination li').remove();
            var href = "http://localhost:8080/laptopshop/api/deliver/order/"+orderID;
            $.get(href, function(order) {
                // compute order price
                var sum = 0;
                $.each(order.orderDetailsList, function(i, detail){
                    sum += detail.cost * detail.orderQuantity;
                });

                var donHangRow = '<tr>' +
                    '<td>' + order.id+ '</td>' +
                    '<td>' + order.receiver + '</td>' +
                    '<td>' + order.orderStatus + '</td>' +
                    '<td>' + sum + '</td>' +
                    '<td>' + order.orderDate + '</td>' +
                    '<td>' + order.deliveryDate + '</td>' +
                    '<td>' + order.receivedDate + '</td>' +
                    '<td width="0%">'+'<input type="hidden" id="orderID" value=' + order.id + '>'+ '</td>'+
                    '<td><button class="btn btn-primary btnDetail" >Detail</button>';

                if(order.orderStatus === "Delivering"){
                    donHangRow += ' &nbsp;<button class="btn btn-warning btnUpdate" >Update</button> </td>';
                }

                $('.orderTable tbody').append(donHangRow);
                $('td').each( function(i){
                    if ($(this).html() === 'null'){
                        $(this).html('');
                    }
                });
            });
        } else {
            resetData();
        }
    });

    // event when click button order detail
    $(document).on('click', '.btnDetail', function (event){
        event.preventDefault();

        var orderID = $(this).parent().prev().children().val();
//		console.log(orderId);
        var href = "http://localhost:8080/laptopshop/api/deliver/order/"+orderID;
        $.get(href, function(order) {
            $('#orderCode').text("Order ID: "+ order.id);
            $('#receiver').text("Receiver: "+ order.receiver);
            $('#receivedPhone').text("Phone: "+ order.receivedPhone);
            $('#receiveAddress').text("Address: "+ order.receiveAddress);
            $('#orderStatus').text("Order Status: "+ order.orderStatus);
            $("#orderDate").text("Order Date: "+ order.orderDate);

            if(order.deliveryDate != null){
                $("#deliveryDate").text("Delivery date: "+ order.deliveryDate);
            }

            if(order.receivedDate != null){
                $("#receivedDate").text("Received date: "+ order.receivedDate);
            }

            if(order.note != null){
                $("#note").text("Note: "+ order.note);
            }

            if(order.orderer != null){
                $("#orderer").text("Orderer: "+ order.orderer.fullName);
            }

            if(order.deliver != null){
                $("#deliver").text("Deliver: "+ order.deliver.fullName);
            }

            var check = order.orderStatus === "Completed" || order.orderStatus === "Waiting for approval" ;
            if(check){
                $('.detailTable').find('thead tr').append('<th id="receivedQuantityTag" class="border-0 text-uppercase small font-weight-bold"> Received Quantity </th>');
            }
            // add table:
            var sum = 0;
            var stt = 1;
            $.each(order.orderDetailsList, function(i, detail){
                console.log(detail.orderQuantity);
                var detailsRow = '<tr>' +
                    '<td>' + stt + '</td>' +
                    '<td>' + detail.product.productName + '</td>' +
                    '<td>' + detail.cost + '</td>'+
                    '<td>' + detail.orderQuantity+ '</td>';

                if(check){
                    detailsRow += '<td>' + detail.receivedQuantity + '</td>';
                    sum += detail.cost * detail.receivedQuantity;
                } else {
                    sum += detail.cost * detail.orderQuantity;
                }

                $('.detailTable tbody').append(detailsRow);
                stt++;
            });
            $("#totalCostUpdate").text("Total: "+ sum);
        });
        $("#detailModal").modal();
    });

    // event when hide modal detail
    $('#detailModal, #updateStatusModal').on('hidden.bs.modal', function(e) {
        e.preventDefault();
        $("#detailForm p").text(""); // reset text tag p
        $("#updateStatusForm h4").text(""); // reset text tag p
        $('.detailTable tbody tr').remove();
        $('.detailTable #receivedQuantityTag').remove();
        $('.detailUpdateTable tbody tr').remove();
    });

    // event when click button order update
    $(document).on('click', '.btnUpdate', function (event){
        event.preventDefault();
        var orderID = $(this).parent().prev().children().val();
        $("#orderID").val(orderID);
        var href = "http://localhost:8080/laptopshop/api/deliver/order/"+orderID;
        $.get(href, function(order) {
            // add table:
            var stt = 1;
            $.each(order.orderDetailsList, function(i, detail){
                var detailsRow = '<tr>' +
                    '<td>' + stt + '</td>' +
                    '<td>' + detail.product.productName + '</td>' +
                    '<td>' + detail.cost + '</td>'+
                    '<td>' + detail.orderQuantity + '</td>'+
                    '<td><input type="number" class="receivedQuantity" style="width: 40px; text-align: center;" value ="'+detail.orderQuantity +'" min="0" max="'+detail.orderQuantity +'" ></td>'+
                    '<td><input type="hidden" value="'+detail.id+'" ></td>'
                $('.detailUpdateTable tbody').append(detailsRow);
                stt++;
            });
            var sum = 0;
            $.each(order.orderDetailsList, function(i, detail){
                sum += detail.cost * detail.orderQuantity;
            });
            $("#totalCostUpdate").text("Total : "+ sum);
        });
        $("#updateStatusModal").modal();
    });

    //
    $(document).on('change', '.receivedQuantity', function (event) {
        var table = $(".detailUpdateTable tbody");
        sum  = 0;
        table.find('tr').each(function (i) {
            cost = $(this).find("td:eq(2)").text();
            quantityUpdate = $(this).find("td:eq(4) input[type='number']").val();
            sum += cost * quantityUpdate;
        });
        $("#totalCostUpdate").text("Total: "+ sum);

    });

    $(document).on('click', '#btnConfirm', function (event) {
        event.preventDefault();
        ajaxPostUpdateOrderStatus();
        resetData();
    });

    // post request UpdateOrderStatus deliver
    function ajaxPostUpdateOrderStatus() {

        var listDetailUpdate = [] ;
        var table = $(".detailUpdateTable tbody");
        table.find('tr').each(function (i) {
            var detailUpdate = { idDetail : $(this).find("td:eq(5) input[type='hidden']").val(),
                receivedQuantity: $(this).find("td:eq(4) input[type='number']").val() };
            listDetailUpdate.push(detailUpdate);
        });


        var data = { idOrder : $("#orderID").val(),
            deliverNote: $("#deliverNote").val(),
            updateOrderDetailsList: listDetailUpdate } ;
//    	 console.log(data);
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/laptopshop/api/deliver/order/update",
            enctype: 'multipart/form-data',

            data : JSON.stringify(data),
            // dataType : 'json',
            success : function(response) {
                $("#updateStatusModal").modal('hide');
                alert("Update order delivery successful");
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }

    // reset table after post, put, filter
    function resetData(){
        var page = $('li.active').children().text();
        $('.orderTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    }
});