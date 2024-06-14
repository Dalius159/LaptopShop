$(document).ready(function () {

    getAllProducts(1);

    function getAllProducts(page) {
        // prepare data
        const data = $('#searchForm').serialize();
        $.ajax({
            type: "GET",
            data: data,
            contentType: "application/json",
            url: "http://localhost:8080/api/product/all" + '?page=' + page,
            success: function (result) {
                $.each(result.content, function (i, product) {
                    let productRow = '<tr>' +
                        '<td>' + '<img src="/product_images/' + product.id + '.png" class="img-responsive" style="height: 50px; width: 50px" />' + '</td>' +
                        '<td>' + product.productName + '</td>' +
                        '<td>' + product.category.categoryName + '</td>' +
                        '<td>' + product.manufacturer.manufacturerName + '</td>' +
                        '<td>' + product.price + '</td>' +
                        '<td>' + product.warehouseUnit + '</td>' +
                        '<td>' + '<input type="hidden" id="sanPhamId" value=' + product.id + '>' + '</td>' +
                        '<td> <button class="btn btn-warning btnChiTiet" style="margin-right: 6px">Detail</button>';

                    const categoryNameCheck = (product.category.categoryName.toLowerCase()).indexOf("Laptop".toLowerCase());
                    productRow += (categoryNameCheck != -1) ? '<button class="btn btn-primary btnCapNhatLapTop" >Update</button>' : '<button class="btn btn-primary btnCapNhatOther" >Cập nhật</button>';
                    productRow += '  <button class="btn btn-danger btnXoaSanPham">Xóa</button></td>' + '</tr>';
                    $('.sanPhamTable tbody').append(productRow);
                });

                if (result.totalPages > 1) {
                    for (let numberPage = 1; numberPage <= result.totalPages; numberPage++) {
                        const li = '<li class="page-item "><a class="pageNumber">' + numberPage + '</a></li>';
                        $('.pagination').append(li);
                    }
                    ;


                    // active page pagination
                    $(".pageNumber").each(function (index) {
                        if ($(this).text() === page) {
                            $(this).parent().removeClass().addClass("page-item active");
                        }
                    });
                }
                ;
            },
            error: function (e) {
                alert("Error: ", e);
                console.log("Error", e);
            }
        });
    };

    // event - clicking on dropdown menu to chose category for new product
    $('#danhMucDropdown').mouseup(function () {
        var open = $(this).data("isopen");
        if (open) {
            var label = $('#danhMucDropdown option:selected').text();
            if ((label.toLowerCase()).indexOf("Laptop".toLowerCase()) !== -1) {
                $('.lapTopModal').modal('show');
                $("#idDanhMucLaptop").val($(this).val());
                $('#lapTopForm').removeClass().addClass("addLapTopForm");
                $('#lapTopForm #btnSubmit').removeClass().addClass("btn btn-primary btnSaveLapTopForm");
            } else {
                $('.otherModal').modal('show');
                $("#idDanhMucKhac").val($(this).val());
                $('#otherForm').removeClass().addClass("addOtherForm");
                $('#otherForm #btnSubmit').removeClass().addClass("btn btn-primary btnSaveOtherForm");
            }
            $(".modal-title").text("Thêm mới sản phẩm danh mục " + label);

        }
        $(this).data("isopen", !open);
    });

    $(document).on('click', '#btnDuyetSanPham', function (event) {
        event.preventDefault();
        $('.sanPhamTable tbody tr').remove();
        $('.pagination li').remove();
        getAllProducts(1);

    });


    // event - hide modal form
    $('.lapTopModal, .otherModal').on('hidden.bs.modal', function (e) {
        e.preventDefault();
        $("#idDanhMucLaptop, #idDanhMucKhac").val("");
        $("#idSanPhamLapTop, #idSanPhamKhac").val("");

        $('#lapTopForm').removeClass().addClass("lapTopForm");
        $('#lapTopForm #btnSubmit').removeClass().addClass("btn btn-primary");
        $('#lapTopForm').trigger("reset");

        $('#otherForm').removeClass().addClass("otherForm");
        $('#otherForm #btnSubmit').removeClass().addClass("btn btn-primary");
        $('#otherForm').trigger("reset");
        $('input, textarea').next().remove();
    });

    // Save Form Laptop Event
    $(document).on('click', '.btnSaveLapTopForm', function (event) {
        event.preventDefault();
        ajaxPostLapTop();
        resetData();
    });

    function ajaxPostLapTop() {
        // PREPARE DATA
        const form = $('.addLapTopForm')[0];
        const data = new FormData(form);

        // do post
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/product/save",
            enctype: 'multipart/form-data',
            data: data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,

            success: function (response) {
                if (response.status == "success") {
                    $('.lapTopModal').modal('hide');
                    alert("Thêm thành công");
                } else {
                    $('input, textarea').next().remove();
                    $.each(response.errorMessages, function (key, value) {
                        if (key != "id") {
                            $('input[name=' + key + ']').after('<span class="error">' + value + '</span>');
                            $('textarea[name=' + key + ']').after('<span class="error">' + value + '</span>');
                        }
                        ;
                    });
                }

            },
            error: function (e) {
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
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/product/save",
            enctype: 'multipart/form-data',
            data: data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            // contentType: false,
            cache: false,
            timeout: 1000000,

            success: function (response) {
                if (response.status === "success") {
                    $('.otherModal').modal('hide');
                    alert("Added successfully");
                } else {
                    $('input, textarea').next().remove();
                    $.each(response.errorMessages, function (key, value) {
                        if (key !== "id") {
                            $('input[name=' + key + ']').after('<span class="error">' + value + '</span>');
                            $('textarea[name=' + key + ']').after('<span class="error">' + value + '</span>');
                        }
                        ;
                    });
                }

            },
            error: function (e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }


    // click cập nhật button 
    // vs danh mục laptop
    $(document).on("click", ".btnCapNhatLapTop", function (event) {
        event.preventDefault();
        var sanPhamId = $(this).parent().prev().children().val();
        $('#lapTopForm').removeClass().addClass("updateLaptopForm");
        $('#lapTopForm #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateLaptopForm");

        var href = "http://localhost:8080/api/product/" + sanPhamId;
        $.get(href, function (product) {
            populate('.updateLaptopForm', product);
            $("#idDanhMucLaptop").val(product.category.id);
            const manufacturerID = product.manufacturer.id;
            $("#nhaSXId").val(manufacturerID);
        });

        removeElementsByClass("error");
        $('.updateLaptopForm .lapTopModal').modal();
    });

    // btn update Laptop form Event
    $(document).on('click', '.btnUpdateLaptopForm', function (event) {
        event.preventDefault();
        postLaptopProduct();
        resetData();
    });

    function postLaptopProduct() {

        const form = $('.updateLaptopForm')[0];
        const data = new FormData(form);
        console.log(data);

        // do post
        $.ajax({
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/product/save",
            enctype: 'multipart/form-data',
            data: data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            // contentType: false,
            cache: false,
            timeout: 1000000,

            success: function (response) {
                if (response.status === "success") {
                    $('.lapTopModal').modal('hide');
                    alert("Update successfully");
                } else {
                    $('input, textarea').next().remove();
                    if (key !== "id") {
                        $('input[name=' + key + ']').after('<span class="error">' + value + '</span>');
                        $('textarea[name=' + key + ']').after('<span class="error">' + value + '</span>');
                    }
                    ;
                }

            },
            error: function (e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }


    // other categories
    $(document).on("click", ".btnCapNhatOther", function (event) {
        event.preventDefault();
        const productID = $(this).parent().prev().children().val();
        $('#otherForm').removeClass().addClass("updateOtherForm");
        $('#otherForm #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateOtherForm");

        const href = "http://localhost:8080/api/product/" + productID;
        $.get(href, function (product) {
            populate('.updateOtherForm', product);
            var hangSXId = product.hangSanXuat.trouid;
            $("#nhaSXIdKhac").val(hangSXId);
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
            async: false,
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/product/save",
            enctype: 'multipart/form-data',
            data: data,

            // prevent jQuery from automatically transforming the data into a
            // query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,

            success: function (response) {
                if (response.status === "success") {
                    $('.otherModal').modal('hide');
                    alert("Updated successfully");
                } else {
                    $('input, textarea').next().remove();
                    $.each(response.errorMessages, function (key, value) {
                        if (key != "id") {
                            $('input[name=' + key + ']').after('<span class="error">' + value + '</span>');
                            $('textarea[name=' + key + ']').after('<span class="error">' + value + '</span>');
                        }
                        ;
                    });
                }

            },
            error: function (e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
    }


    // clicking on delete button
    $(document).on("click", ".btnXoaSanPham", function () {

        const productID = $(this).parent().prev().children().val();
        const workingObject = $(this);

        var confirmation = confirm("Confirm to delete this product ?");
        if (confirmation) {
            $.ajax({
                async: false,
                type: "DELETE",
                url: "http://localhost:8080/api/product/delete/" + productID,
                success: function (resultMsg) {
                    resetDataForDelete();
                    alert("Delete successfully");
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }
            });
        }
        resetData();
    });

    // clicking on detail button
    $(document).on("click", ".btnChiTiet", function () {

        const productID = $(this).parent().prev().children().val();
        console.log(productID);

        const href = "http://localhost:8080/api/product/" + productID;
        $.get(href, function (product) {
            $('.hinhAnh').attr("src", "/product_images/" + product.id + ".png");
            $('.tenSanPham').html("<span style='font-weight: bold'>Product name: </span> " + product.tenSanPham);
            $('.maSanPham').html("<span style='font-weight: bold'>Product ID: </span>" + product.id);
            $('.hangSangXuat').html("<span style='font-weight: bold'>Manufacturer: </span>" + product.hangSanXuat.tenHangSanXuat);

            const categoryNameCheck = (product.category.categoryName.toLowerCase()).indexOf("Laptop".toLowerCase());

            console.log(categoryNameCheck !== -1);
            if (categoryNameCheck !== -1) {
                $('.cpu').html("<span style='font-weight: bold'>CPU: </span>" + product.cpu);
                $('.ram').html("<span style='font-weight: bold'>RAM: </span>" + product.ram);
                $('.thietKe').html("<span style='font-weight: bold'>Design: </span>" + product.design);
                $('.dungLuongPin').html("<span style='font-weight: bold'>Battery capacity: </span>" + product.batteryCapacity_mAh);
                $('.heDieuHanh').html("<span style='font-weight: bold'>OS: </span>" + product.operatingSystem);
                $('.manHinh').html("<span style='font-weight: bold'>Screen: </span>" + product.screen);
            }
            $('.thongTinChung').html("<span style='font-weight: bold'>Thông tin chung: </span>" + product.warrantyInfor);
            $('.donGia').html("<span style='font-weight: bold'>Price: </span>" + product.price);
            $('.baoHanh').html("<span style='font-weight: bold'>Warranty: </span>" + product.warrantyInfor);
            $('.donViKho').html("<span style='font-weight: bold'>In stock: </span>" + product.warehouseUnit);
            $('.donViBan').html("<span style='font-weight: bold'>Sold: </span>" + product.salesUnit);
        });

        $('#chiTietModal').modal('show');

    });

    // reset table after delete
    function resetDataForDelete() {
        const count = $('.sanPhamTable tbody').children().length;
        $('.sanPhamTable tbody tr').remove();
        const page = $('li.active').children().text();
        $('.pagination li').remove();
        console.log(page);
        if (    count === 1) {
            getAllProducts(page - 1);
        } else {
            getAllProducts(page);
        }

    };

    // reset table after post, put, filter
    function resetData() {
        var page = $('li.active').children().text();
        $('.sanPhamTable tbody tr').remove();
        $('.pagination li').remove();
        getAllProducts(page);
    };

    // event khi click vào phân trang Sản phẩm
    $(document).on('click', '.pageNumber', function (event) {
        event.preventDefault();
        var page = $(this).text();
        $('.sanPhamTable tbody tr').remove();
        $('.pagination li').remove();
        getAllProducts(page);
    });


    // event khi click vào nhấn phím vào ô tìm kiếm sản phẩm theo tên
    $(document).on('keyup', '#searchById', function (event) {
        event.preventDefault();
        const productID = $('#searchById').val();
        console.log(productID);
        if (productID !== '') {
            $('.sanPhamTable tbody tr').remove();
            $('.pagination li').remove();
            var href = "http://localhost:8080/api/product/" + productID;
            $.get(href, function (product) {
                let productRow = '<tr>' +
                    '<td>' + '<img src="/product_images/' + product.id + '.png" class="img-responsive" style="height: 50px; width: 50px" />' + '</td>' +
                    '<td>' + product.productName + '</td>' +
                    '<td>' + product.category.categoryName + '</td>' +
                    '<td>' + product.manufacturer.manufacturerName + '</td>' +
                    '<td>' + product.price + '</td>' +
                    '<td>' + product.warehouseUnit + '</td>' +
                    '<td>' + '<input type="hidden" id="sanPhamId" value=' + product.id + '>' + '</td>' +
                    '<td><button class="btn btn-warning btnChiTiet" style="margin-right: 6px">Details</button>';

                const catNameCheck = (product.manufacturer.manufacturerName.toLowerCase()).indexOf("Laptop".toLowerCase());
                productRow += (catNameCheck !== -1) ? '  <button class="btn btn-primary btnCapNhatLapTop" >Update</button>' : '<button class="btn btn-primary btnCapNhatOther" >Cập nhật</button>';
                productRow += ' <button class="btn btn-danger btnXoaSanPham">Xóa</button></td>' + '</tr>';
                $('.sanPhamTable tbody').append(productRow);
            });
        } else {
            resetData();
        }
    });

    // fill input form với JSon Object
    function populate(frm, data) {
        $.each(data, function (key, value) {
            $('[name=' + key + ']', frm).val(value);
        });
    }

    // event - hide detail modal
    $('#chiTietModal').on('hidden.bs.modal', function (e) {
        e.preventDefault();
        $(".chiTietForm p").text(""); // reset text thẻ p
    });

    // remove element by class name
    function removeElementsByClass(className) {
        const elements = document.getElementsByClassName(className);
        while (elements.length > 0) {
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