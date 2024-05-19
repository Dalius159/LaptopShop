$(document).ready(function() {

    // load first when coming page
    ajaxGet(1);

    // do get
    function ajaxGet(page){
        // prepare data
        var data = $('#searchForm').serialize();
        $.ajax({
            type: "GET",
            data: data,
            contentType : "application/json",
            url: "http://localhost:8080/laptopshop/api/product/all" + '?page=' + page,
            success: function(result){
                $.each(result.content, function(i, product){
                    var productRow = '<tr>' +
                        '<td>' + '<img src="/laptopshop/img/'+product.id+'.png" class="img-responsive" style="height: 50px; width: 50px"  alt=""/>'+'</td>' +
                        '<td>' + product.productName + '</td>' +
                        '<td>' + product.category.categoryName + '</td>' +
                        '<td>' + product.manufacturer.manufactureName + '</td>' +
                        '<td>' + product.price + '</td>' +
                        '<td>' + product.warehouseUnit+ '</td>' +
                        '<td width="0%">'+'<input type="hidden" id="productId" value=' + product.id + '>'+ '</td>' +
                        '<td> <button class="btn btn-warning btnDetail" style="margin-right: 6px">Detail</button>' ;

                    var categoryName = (product.category.categoryName.toLowerCase()).indexOf("Laptop".toLowerCase());
                    productRow += ( categoryName !== -1)?'<button class="btn btn-primary btnUpdateLapTop" >Update</button>':'<button class="btn btn-primary btnUpdateOther" >Update</button>';
                    productRow += '  <button class="btn btn-danger btnDeleteProdcut">Delete</button></td>'+'</tr>';
                    $('.productTable tbody').append(productRow);
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
    };

    // event when click dropdown and choose add product
    $('#categoryDropdown').mouseup(function() {
        var open = $(this).data("isopen");
        if (open) {
            var label = $('#categoryDropdown option:selected').text();
            if ((label.toLowerCase()).indexOf("Laptop".toLowerCase()) !== -1) {
                $('.lapTopModal').modal('show');
                $("#idLaptopCategory").val($(this).val());
                $('#lapTopForm').removeClass().addClass("addLapTopForm");
                $('#lapTopForm #btnSubmit').removeClass().addClass("btn btn-primary btnSaveLapTopForm");
            } else {
                $('.otherModal').modal('show');
                $("#idOtherCategory").val($(this).val());
                $('#otherForm').removeClass().addClass("addOtherForm");
                $('#otherForm #btnSubmit').removeClass().addClass("btn btn-primary btnSaveOtherForm");
            }
            $(".modal-title").text("New Category"+ label);

        }
        $(this).data("isopen", !open);
    });

    $(document).on('click', '#btnBrowseProduct', function (event) {
        event.preventDefault();
        $('.productTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(1);

    });


    // event when hide modal form
    $('.lapTopModal, .otherModal').on('hidden.bs.modal', function(e) {
        e.preventDefault();
        $("#idLaptopCategory, #idOtherCategory").val("");
        $("#idProductLapTop, #idOtherProduct").val("");

        $('#lapTopForm').removeClass().addClass("lapTopForm");
        $('#lapTopForm #btnSubmit').removeClass().addClass("btn btn-primary");
        $('#lapTopForm').trigger("reset");

        $('#otherForm').removeClass().addClass("otherForm");
        $('#otherForm #btnSubmit').removeClass().addClass("btn btn-primary");
        $('#otherForm').trigger("reset");
        $('input, textarea').next().remove();
    });

    // btn Save Form Laptop Event
    $(document).on('click', '.btnSaveLapTopForm', function (event) {
        event.preventDefault();
        ajaxPostLapTop();
        resetData();
    });

    function ajaxPostLapTop() {
        // PREPATEE DATA
        var form = $('.addLapTopForm')[0];
        var data = new FormData(form);

        // do post
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/laptopshop/api/product/save",
            enctype: 'multipart/form-data',
            data : data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,

            success : function(response) {
                if(response.status === "success"){
                    $('.lapTopModal').modal('hide');
                    alert("Add successful!");
                } else {
                    $('input, textarea').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        if(key !== "id"){
                            $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
                            $('textarea[name='+ key +']').after('<span class="error">'+value+'</span>');
                        }
                    });
                }

            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }

    // btnSaveOtherForm event click
    $(document).on('click', '.btnSaveOtherForm', function (event) {
        event.preventDefault();
        ajaxPostOther();
        resetData();
    });

    function ajaxPostOther() {
        // PREPATEE DATA
        var form = $('.addOtherForm')[0];
        var data = new FormData(form);
        // do post
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/laptopshop/api/product/save",
            enctype: 'multipart/form-data',
            data : data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,

            success : function(response) {
                if(response.status === "success"){
                    $('.otherModal').modal('hide');
                    alert("Add successful!");
                } else {
                    $('input, textarea').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        if(key !== "id"){
                            $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
                            $('textarea[name='+ key +']').after('<span class="error">'+value+'</span>');
                        }
                    });
                }

            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }


    // click update button
    // vs category laptop
    $(document).on("click",".btnUpdateLapTop", function(event){
        event.preventDefault();
        var productId = $(this).parent().prev().children().val();
        $('#lapTopForm').removeClass().addClass("updateLaptopForm");
        $('#lapTopForm #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateLaptopForm");

        var href = "http://localhost:8080/laptopshop/api/product/"+productId;
        $.get(href, function(product) {
            populate('.updateLaptopForm', product);
            $("#idLaptopCategory").val(product.category.id);
            var hangSXId = product.manufacturer.id;
            $("#manufacturerID").val(manufacturerID);
        });

        removeElementsByClass("error");
        $('.updateLaptopForm .lapTopModal').modal();
    });

    // btn update Laptop form Event
    $(document).on('click', '.btnUpdateLaptopForm', function (event) {
        event.preventDefault();
        ajaxPutLapTop();
        resetData();
    });

    function ajaxPutLapTop() {

        var form = $('.updateLaptopForm')[0];
        var data = new FormData(form);
        console.log(data);

        // do post
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/laptopshop/api/product/save",
            enctype: 'multipart/form-data',
            data : data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,

            success : function(response) {
                if(response.status === "success"){
                    $('.lapTopModal').modal('hide');
                    alert("Update successful!");
                } else {
                    $('input, textarea').next().remove();
                    if(key !== "id"){
                        $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
                        $('textarea[name='+ key +']').after('<span class="error">'+value+'</span>');
                    }
                }

            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }


    // with other categories
    $(document).on("click",".btnUpdateOther", function(event){
        event.preventDefault();
        var productId = $(this).parent().prev().children().val();
        $('#otherForm').removeClass().addClass("updateOtherForm");
        $('#otherForm #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateOtherForm");

        var href = "http://localhost:8080/laptopshop/api/product/"+productId;
        $.get(href, function(product) {
            populate('.updateOtherForm', product);
            $("#idOtherCategory").val(product.category.id);
            var hangSXId = product.manufacturer.id;
            $("#manufacturerIdOther").val(manufacturerID);
        });
        removeElementsByClass("error");
        $('.updateOtherForm .otherModal').modal();
    });

    // btnUpdateOtherForm event click
    $(document).on('click', '.btnUpdateOtherForm', function (event) {
        event.preventDefault();
        ajaxPutOther();
        resetData();
    });

    function ajaxPutOther() {
        // PREPARE DATA
        var form = $('.updateOtherForm')[0];
        var data = new FormData(form);
        // do put
        $.ajax({
            async:false,
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8080/laptopshop/api/product/save",
            enctype: 'multipart/form-data',
            data : data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,

            success : function(response) {
                if(response.status === "success"){
                    $('.otherModal').modal('hide');
                    alert("Update successful!");
                } else {
                    $('input, textarea').next().remove();
                    $.each(response.errorMessages, function(key,value){
                        if(key !== "id"){
                            $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
                            $('textarea[name='+ key +']').after('<span class="error">'+value+'</span>');
                        }
                    });
                }

            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }


    // click button delete
    $(document).on("click",".btnDeleteProduct", function() {

        var productId = $(this).parent().prev().children().val();
        var workingObject = $(this);

        var confirmation = confirm("Are you sure to delete this item?");
        if(confirmation){
            $.ajax({
                async:false,
                type : "DELETE",
                url : "http://localhost:8080/laptopshop/api/product/delete/" + productId,
                success: function(resultMsg){
                    resetDataForDelete();
                    alert("Delete successful!");
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                }
            });
        }
        resetData();
    });

    // click button detail
    $(document).on("click",".btnDetail", function() {

        var productId = $(this).parent().prev().children().val();
        console.log(productId);

        var href = "http://localhost:8080/laptopshop/api/product/"+productId;
        $.get(href, function(product) {
            $('.image').attr("src", "/laptopshop/img/"+product.id+".png");
            $('.productName').html("<span style='font-weight: bold'>Product name: </span> "+ product.productName);
            $('.productId').html("<span style='font-weight: bold'>Product Id: </span>"+ product.id);
            $('.manufacturer').html("<span style='font-weight: bold'>Manufacturer: </span>"+ product.manufacturer.manufacturerName);

            var checkCategoryName = (product.category.categoryName.toLowerCase()).indexOf("Laptop".toLowerCase());

            console.log(checkCategoryName !== -1);
            if(checkCategoryName !== -1){
                $('.cpu').html("<span style='font-weight: bold'>CPU: </span>"+ product.cpu);
                $('.ram').html("<span style='font-weight: bold'>RAM: </span>"+ product.ram);
                $('.design').html("<span style='font-weight: bold'>Design: </span>"+ product.design);
                $('.batteryCapacity_mAh').html("<span style='font-weight: bold'>Battery capacity: </span>"+ product.batteryCapacity_mAh);
                $('.operatingSystem').html("<span style='font-weight: bold'>Operating system: </span>"+ product.operatingSystem);
                $('.screen').html("<span style='font-weight: bold'>Screen: </span>"+ product.screen);
            }
            $('.generalInfor').html("<span style='font-weight: bold'>General information: </span>"+ product.generalInfor);
            $('.price').html("<span style='font-weight: bold'>Price: </span>"+ product.price +" $");
            $('.warrantyInfor').html("<span style='font-weight: bold'>Warranty: </span>"+ product.warrantyInfor);
            $('.warehouseUnit').html("<span style='font-weight: bold'>Unit in warehouse: </span>"+ product.warehouseUnit);
            $('.salesUnit').html("<span style='font-weight: bold'>Sale unit: </span>"+ product.salesUnit);

        });

        $('#detailModal').modal('show');

    });

    // reset table after delete
    function resetDataForDelete(){
        var count = $('.productTable tbody').children().length;
        $('.productTable tbody tr').remove();
        var page = $('li.active').children().text();
        $('.pagination li').remove();
        console.log(page);
        if(count === 1){
            ajaxGet(page -1 );
        } else {
            ajaxGet(page);
        }

    }

    // reset table after post, put, filter
    function resetData(){
        var page = $('li.active').children().text();
        $('.productTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    }

    // event when click into product pagination
    $(document).on('click', '.pageNumber', function (event){
        event.preventDefault();
        var page = $(this).text();
        $('.productTable tbody tr').remove();
        $('.pagination li').remove();
        ajaxGet(page);
    });


    // event when click into field find product by name
    $(document).on('keyup', '#searchById', function (event){
        event.preventDefault();
        var productId = $('#searchById').val();
        console.log(productId);
        if(productId !== ''){
            $('.productTable tbody tr').remove();
            $('.pagination li').remove();
            var href = "http://localhost:8080/laptopshop/api/product/"+productId;
            $.get(href, function(product) {
                var productRow = '<tr>' +
                    '<td>' + '<img src="/laptopshop/img/'+product.id+'.png" class="img-responsive" style="height: 50px; width: 50px" />'+'</td>' +
                    '<td>' + product.productName + '</td>' +
                    '<td>' + product.category.categoryName + '</td>' +
                    '<td>' + product.manufacturer.manufacturerName + '</td>' +
                    '<td>' + product.price + '</td>' +
                    '<td>' + product.warehouseUnit + '</td>' +
                    '<td width="0%">'+'<input type="hidden" id="productId" value=' + product.id + '>'+ '</td>' +
                    '<td><button class="btn btn-warning btnDetail" style="margin-right: 6px">Detail</button>'  ;

                var checkCategoryName = (product.category.categoryName.toLowerCase()).indexOf("Laptop".toLowerCase());
                productRow += ( checkCategoryName !== -1)?'  <button class="btn btn-primary btnUpdateLapTop" >Update</button>':'<button class="btn btn-primary btnUpdateOther" >Update</button>';
                productRow += ' <button class="btn btn-danger btnDeleteProduct">Delete</button></td>'+'</tr>';
                $('.productTable tbody').append(productRow);
            });
        } else {
            resetData();
        }
    });

    // fill input form by JSon Object
    function populate(frm, data) {
        $.each(data, function(key, value){
            $('[name='+key+']', frm).val(value);
        });
    }

    // event when hide detail modal
    $('#detailModal').on('hidden.bs.modal', function(e) {
        e.preventDefault();
        $(".detailForm p").text(""); // reset text tag p
    });

    // remove element by class name
    function removeElementsByClass(className){
        var elements = document.getElementsByClassName(className);
        while(elements.length > 0){
            elements[0].parentNode.removeChild(elements[0]);
        }
    }

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

});