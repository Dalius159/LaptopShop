$(document).ready(function() {

    // load first when coming page
    ajaxGet(1);

    function ajaxGet(page){
        var data = { roleName:$("#role").val()};
        $.ajax({
            type: "GET",
            data: data,
            contentType : "application/json",
            url: "http://localhost:8080/api//all" + '?page=' + page,
            success: function(result){
                $.each(result.content, function(i, account){
                    var accountRow = '<tr>' +
                        '<td>' + account.id+ '</td>' +
                        '<td>' + account.fullName + '</td>' +
                        '<td>' + account.email + '</td>' +
                        '<td>' + account.phoneNumber + '</td>' +
                        '<td>' + account.address + '</td>'+ '<td>';

                    $.each(account.role, function(i, role){
                        accountRow += role.roleName;
                        accountRow += "<br>";
                    });

                    accountRow +='</td>' +
                        '<td width="0%">'+'<input type="hidden" id="idAccount" value=' + account.id + '>'+ '</td>'+
                        //					                  '<td><button class="btn btn-primary btnUpdate" >Update</button></td>' +
                        '<td><button class="btn btn-danger btnDelete" >Delete</button></td>';			;
                    $('.accountTable tbody').append(accountRow);

                });

                if(result.totalPages > 1 ){
                    for(var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
                        var li = '<li class="page-item "><a class="pageNumber">'+numberPage+'</a></li>';
                        $('.pagination').append(li);
                    };

                    // active page pagination
                    $(".pageNumber").each(function(index){
                        if($(this).text() == page){
                            $(this).parent().removeClass().addClass("page-item active");
                        }
                    });
                };
            },
            error : function(e){
                alert("Error: ",e);
                console.log("Error" , e );
            }
        });
    };

    // event when click dropdown button and choose add new product
    $('#role').mouseup(function() {
        var open = $(this).data("isopen");
        if (open) {
            resetData();
        }
        $(this).data("isopen", !open);
    });

    // click add new account
    $(document).on('click', '.btnNewAccount', function (event) {
        event.preventDefault();
        $("#accountModal").modal();
    });

    // confirm add new account
    $(document).on('click', '#btnSubmit', function (event) {
        event.preventDefault();
        ajaxPostAccount();
        resetData();
    });

    function ajaxPostAccount() {
        var data = JSON.stringify($('.accountForm').serializeJSON());
        console.log(data);

        // do post
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/api/account/save",
            enctype: 'multipart/form-data',
            data : data,
            success : function(response) {
                if(response.status === "success"){
                    $('#accountModal').modal('hide');
                    alert("Add Successful!");
                } else {
                    $('input').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        if(key !== "id"){
                            $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
                        };
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
    $(document).on("click",".btnDelete", function() {

        var accountID = $(this).parent().prev().children().val();
        var confirmation = confirm("Are you sure to delete this account?");
        if(confirmation){
            $.ajax({
                type : "DELETE",
                url : "http://localhost:8080/api/account/delete/" + accountID,
                success: function(resultMsg){
                    resetData();
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                }
            });
        }
    });

    // event when hide modal form
    $('#accountModal').on('hidden.bs.modal', function(e) {
        e.preventDefault();
        $('.accountForm input').next().remove();
    });

    // reset table after post, put, filter
    function resetData(){
        var page = $('li.active').children().text();
        $('.accountTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    };

    (function ($) {
        $.fn.serializeFormJSON = function () {

            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };
    })(jQuery);

    // remove element by class name
    function removeElementsByClass(className){
        var elements = document.getElementsByClassName(className);
        while(elements.length > 0){
            elements[0].parentNode.removeChild(elements[0]);
        }
    }
});