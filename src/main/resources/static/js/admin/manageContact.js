$(document).ready(function() {

    // load first when coming page
    ajaxGet(1);

    function ajaxGet(page){
        var data = {
            contactStatus: $('.status').val(),
            fromDate: $('#fromDate').val(),
            toDate: $('#toDate').val()
        };
        $.ajax({
            type: "GET",
            data: data,
            url: "http://localhost:8080/api/contact/all?page=" + page,
            success: function(result){
                console.log("Response Data: ", result);  // Log response data
                $('.contactTable tbody').empty(); // Clear existing rows

                if (result && result.content) {
                    $.each(result.content, function(i, contact){
                        var contactRow = '<tr>' +
                            '<td>' + contact.id + '</td>' +
                            '<td>' + contact.contactEmail + '</td>' +
                            '<td>' + contact.contactDate + '</td>' +
                            '<td>' + contact.status + '</td>' +
                            '<td>' + contact.respondDate + '</td>' +
                            '<td><input type="hidden" class="contactId" value="' + contact.id + '">' +
                            '<button class="btn btn-primary btnDetail">Detail</button> ' +
                            '<button class="btn btn-warning btnRespond">Respond</button></td>' +
                            '</tr>';

                        $('.contactTable tbody').append(contactRow);
                    });

                    // Pagination
                    renderPagination(page, result.totalPages);
                } else {
                    alert("No contacts found.");
                }
            },
            error: function(e){
                alert("Error: " + e.responseText);
                console.log("Error", e);
            }
        });
    }

    function renderPagination(currentPage, totalPages) {
        $('.pagination').empty(); // Clear pagination before rendering

        var prevDisabled = (currentPage === 1) ? 'disabled' : '';
        var nextDisabled = (currentPage === totalPages) ? 'disabled' : '';

        for(var numberPage = 1; numberPage <= totalPages; numberPage++) {
            var liClass = (numberPage === currentPage) ? 'page-item active' : 'page-item';
            var li = '<li class="' + liClass + '"><a class="page-link pageNumber" href="#" data-page="' + numberPage + '">' + numberPage + '</a></li>';
            $('.pagination').append(li);
        }
    }

    // Event when clicking the browse contact button
    $(document).on('click', '#btnBrowseContact', function (event) {
        event.preventDefault();
        resetData();
    });

    // Reset table after post, put, filter
    function resetData(){
        var page = $('.pagination li.active a').text();
        $('.contactTable tbody').empty();
        $('.pagination').empty();
        ajaxGet(page);
    }

    // Event when clicking on the pagination number
    $(document).on('click', '.pageNumber', function (){
        var page = $(this).text();
        $('.contactTable tbody').empty();
        $('.pagination').empty();
        ajaxGet(page);
    });

    // Event when clicking on the detail button
    $(document).on('click', '.btnDetail', function (event){
        event.preventDefault();
        var contactId = $(this).closest('tr').find('.contactId').val();
        var href = "http://localhost:8080/api/contact/" + contactId;

        $.get(href, function(contact) {
            if (contact) {
                $('#contactMessage').text("Contact Message: " + contact.contactMessage);
                $('#contactDate').text("Contact Date: " + contact.contactDate);
                $('#email').text("Contact Email: " + contact.contactEmail);
                $('#status').text("Status: " + contact.status);

                if(contact.status === "Responded"){
                    $('#respondMessage').text("Respond Message: " + contact.respondMessage);
                    $('#respondDate').text("Respond Date: " + contact.respondDate);
                } else {
                    $('#respondMessage').empty();
                    $('#respondDate').empty();
                }

                $("#detailModal").modal();
            } else {
                alert("Contact not found.");
            }
        }).fail(function() {
            alert("Failed to get contact details.");
        });
    });

    // Event when clicking on the respond button
    $(document).on('click', '.btnRespond', function (event){
        event.preventDefault();
        var contactId = $(this).closest('tr').find('.contactId').val();
        var href = "http://localhost:8080/api/contact/" + contactId;

        $.get(href, function(contact) {
            if (contact) {
                $('#contactMes').val(contact.contactMessage);
                $('#contactEmail').val(contact.contactEmail);
                $('#title').val("Response to your contact");  // Gán tiêu đề mặc định hoặc từ dữ liệu liên hệ
                $('input[name=contactId]').val(contactId);
                $("#contactModal").modal();
            } else {
                alert("Contact not found.");
            }
        }).fail(function() {
            alert("Failed to get contact details.");
        });
    });

    // Event when clicking on the submit respond button
    $(document).on('click', '.btnSubmitRespond', function (event) {
        event.preventDefault();
        ajaxPostReply();
    });

    // AJAX function to post respond
    function ajaxPostReply(){
        var formData = {
            id: $('input[name=contactId]').val(),
            toEmail: $('#contactEmail').val(),
            title: $('#title').val(),
            respondMessage: $('textarea[name=respondMessage]').val()
        };

        console.log("Post Data: ", formData);  // Log post data

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/contact/reply",
            data: JSON.stringify(formData),
            success: function(response) {
                console.log("Response: ", response);  // Log response
                if(response.status === "success"){
                    $('#contactModal').modal('hide');
                    alert("Respond Successful!");
                    resetData();
                } else {
                    alert("Error: " + response.message);
                }
            },
            error: function(e) {
                alert("Error: Can't respond! Try again.");
                console.log("ERROR: ", e);
            }
        });
    }

    // Event when hiding the contact modal
    $('#contactModal').on('hidden.bs.modal', function () {
        $('textarea').val('');
        $('#title').val('');
    });

});
