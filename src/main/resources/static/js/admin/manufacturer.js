$(document).ready(function(){

    // click event button add new category
    $('.btnAddManufacturer').on("click", function(event) {
        event.preventDefault();
        $('.manufacturerForm #manufacturerModal').modal();
        $('.manufacturerForm #id').prop("disabled", true);
        $('#form').removeClass().addClass("addForm");
        $('#form #btnSubmit').removeClass().addClass("btn btn-primary btnSaveForm");
    });

    $('#manufacturerModal').on('hidden.bs.modal', function () {
        $('#form').removeClass().addClass("manufacturerForm");
        $('#form #btnSubmit').removeClass().addClass("btn btn-primary");
        resetText();
    });

    // reset text in form
    function resetText(){
        $("#id").val("");
        $("#manufacturerName").val("");
    }


    ajaxGet(1);

    // do get
    function ajaxGet(page){
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/manufacturer/all?page=" + page,
            success: function(result){
                $.each(result.content, function(i, manufacturer){
                    var manufacturerRow = '<tr style="text-align: center;">' +
                        '<td>' + manufacturer.id + '</td>' +
                        '<td>' + manufacturer.manufacturerName + '</td>' +
                        '<td>'+'<input type="hidden" value=' + manufacturer.id + '>'
                        + '<button class="btn btn-primary btnUpdateManufacturer">Update</button>' +
                        '   <button class="btn btn-danger btnDeleteManufacturer">Delete</button></td>'
                    '</tr>';
                    $('.manufacturerTable tbody').append(manufacturerRow);
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


    $(document).on('click', '.btnSaveForm', function (event) {
        event.preventDefault();
        ajaxPost();
        resetData();
    });

    function ajaxPost(){
        // PREPARE FORM DATA
        var formData = { manufacturerName : $("#manufactureName").val() } ;

        // DO POST
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/api/manufacturer/save",
            data : JSON.stringify(formData),
            // dataType : 'json',
            success : function(response) {
                if(response.status === "success"){
                    $('#manufacturerModal').modal('hide');
                    alert("Add successful!");
                } else {
                    $('input').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        $('input[id='+ key +']').after('<span class="error">'+value+'</span>');
                    });
                }

            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }

    // click edit button
    $(document).on("click",".btnUpdateManufacturer",function(){
        event.preventDefault();
        $('.manufacturerForm #id').prop("disabled", true);
        var manufacturerID = $(this).parent().find('input').val();

        $('#form').removeClass().addClass("updateForm");
        $('#form #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateForm");
        var href = "http://localhost:8080/api/manufacturer/" + manufacturerID;
        $.get(href, function(manufacturer, status) {
            $('.updateForm #id').val(manufacturer.id);
            $('.updateForm #manufacturerName').val(manufacturer.manufacturerName);
        });

        removeElementsByClass("error");

        $('.updateForm #manufacturerModal').modal();
    });

    // put request

    $(document).on('click', '.btnUpdateForm', function (event) {
        event.preventDefault();
        ajaxPut();
        resetData();
    });


    function ajaxPut(){
        // PREPARE FORM DATA
        var formData = {
            id : $('#id').val(),
            tenHangSanXuat : $("#manufacturerName").val(),
        }
        // DO PUT
        $.ajax({
            async:false,
            type : "PUT",
            contentType : "application/json",
            url : "http://localhost:8080/api/manufacturer/update",
            data : JSON.stringify(formData),
            // dataType : 'json',
            success : function(response) {

                if(response.status === "success"){
                    $('#manufacturerModal').modal('hide');
                    alert("Update Successful");
                } else {
                    $('input').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        $('input[id='+ key +']').after('<span class="error">'+value+'</span>');
                    });
                }
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }

    // delete request
    $(document).on("click",".btnDeleteManufacturer", function() {

        var manufacturerID = $(this).parent().find('input').val();

        var confirmation = confirm("Are you sure to delete this manufacturer?");
        if(confirmation){
            $.ajax({
                type : "DELETE",
                url : "http://localhost:8080/api/manufacturer/delete/" + manufacturerID,
                success: function(resultMsg){
                    resetDataForDelete();
                    alert("Delete successful");
                },
                error : function(e) {
                    alert("Can not delete, check again!");
                    console.log("ERROR: ", e);
                }
            });
        }
    });

    // reset table after post, put
    function resetData(){
        $('.manufacturerTable tbody tr').remove();
        var page = $('li.active').children().text();
        $('.pagination li').remove();
        ajaxGet(page);
    }

    // reset table after post, put
    function resetDataForDelete(){
        var count = $('.manufacturerTable tbody').children().length;
        $('.manufacturerTable tbody tr').remove();
        var page = $('li.active').children().text();
        $('.pagination li').remove();
        if(count === 1){
            ajaxGet(page -1 );
        } else {
            ajaxGet(page);
        }

    }

    // event when click manufacturer pagination
    $(document).on('click', '.pageNumber', function (event){
//		event.preventDefault();
        var page = $(this).text();
        $('.manufacturerTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    });


    function removeElementsByClass(className){
        var elements = document.getElementsByClassName(className);
        while(elements.length > 0){
            elements[0].parentNode.removeChild(elements[0]);
        }
    }
});