$(document).ready(function() {

    // load first when coming page
    ajaxGet(1);

    function ajaxGet(page){
        var data = { contactStatus : $('.status').val(), fromDate: $('#fromDate').val(), toDate: $('#toDate').val()  }
        $.ajax({
            type: "GET",
            data: data,
            contentType : "application/json",
            url: "http://localhost:8080/api/contact/all" + '?page=' + page,
            success: function(result){
                $.each(result.content, function(i, contact){
                    var contactRow = '<tr>' +
                        '<td>' + contact.id+ '</td>' +
                        '<td>' + contact.contactEmail + '</td>' +
                        '<td>' + contact.contactDate + '</td>' +
                        '<td>' + contact.status + '</td>' +
                        '<td>' + contact.respondDate + '</td>';

                    contactRow += '<td width="0%">'+'<input type="hidden" id="contactId" value=' + contact.id + '>'+ '</td>'+
                        '<td><button class="btn btn-primary btnDetail" >Detail</button>' +
                        '&nbsp; <button class="btn btn-warning btnRespond" >Respond</button></td>';

                    $('.contactTable tbody').append(contactRow);

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


    $(document).on('click', '#btnBrowseContact', function (event) {
        event.preventDefault();
        console.log("123");
        resetData();
    });

    // reset table after post, put, filter
    function resetData(){
        var page = $('li.active').children().text();
        $('.contactTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    }

    // event khi click vào phân trang Đơn hàng
    $(document).on('click', '.pageNumber', function (event){
//		event.preventDefault();
        var page = $(this).text();
        $('.contactTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    });

    // btnChiTiet click event
    $(document).on('click', '.btnDetail', function (event){
        event.preventDefault();
        var contactId =  $(this).parent().prev().children().val();
        var href = "http://localhost:8080/api/contact/"+contactId;
        $.get(href, function(contact) {
            $('#contactMessage').html("<strong>Contact Message</strong>: <br> "+ contact.contactMessage);
            $('#contactDate').html("<strong>Contact Date</strong>: "+ contact.contactDate);
            $('#email').html("<strong>Contact Email</strong>: "+ contact.contactEmail);
            $('#status').html("<strong>Status</strong>: "+ contact.status);

            if(contact.status === "Responded"){
                $('#respondMessage').html("<strong>Respond Message</strong>: "+ contact.respondMessage);
                $('#respondDate').html("<strong>Respond Date</strong>: "+ contact.respondDate);
            } else {
                $('#respondMessage').html("");
                $('#respondDate').html("");
            }
        });

        $("#detailModal").modal();
    });


    // btnChiTiet click event
    $(document).on('click', '.btnRespond', function (event){
        event.preventDefault();
        var contactId =  $(this).parent().prev().children().val();
        var href = "http://localhost:8080/api/contact/"+contactId;
        $.get(href, function(lienHe) {
            $('#contactMes').val(lienHe.contactMessage);
            $('#contactEmail').val(lienHe.contactEmail);
        });
        $('input[name=contactId]').val(contactId);
        $("#contactModal").modal();
    });

    $(document).on('click', '.btnSubmitRespond', function (event) {
        event.preventDefault();
        ajaxPostReply();
        resetData();
    });

    function ajaxPostReply(){
        // PREPARE FORM DATA
        var formData = { id : $('input[name=contactId]').val(),
            respondMessage : $('textarea[name=respondMessage]').val(),
            title : "Respond:" + $('textarea[name=contactMessage]').val(),
            toAddress: $('input[name=contactEmail]').val()} ;
        console.log(formData);
        // DO POST
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/api/contact/reply",
            data : JSON.stringify(formData),
            success : function(response) {
                if(response.status === "success"){
                    $('#lienHeModal').modal('hide');
                    alert("Respond Successful!");
                } else {
                    $('textarea').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        $('textarea[name='+ key +']').after('<span class="error">'+value+'</span>');
                    });
                }

            },
            error : function(e) {
                alert("Can't respond! Try again")
                console.log("ERROR: ", e);
            }
        });
    }

    // event khi hide modal
    $('#contactModal').on('hidden.bs.modal', function () {
        $('textarea').next().remove();
    });

});