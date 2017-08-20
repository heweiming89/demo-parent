var sysUserViewSetting = function() {
    var baseApiUrl = contextPath + "/system/user/api";
    var dtTable = null;

    var addValidator = null;
    var editValidator = null;

    function viewInit() {
        listViewSetting();
        addViewSetting();
        infoViewSetting();
        editViewSetting();
    }

    function globalSetting() {

    }

    function listViewSetting() {
        var tableConfig = {
            // dom : 'Bfrtip',
            ajax : {
                url : baseApiUrl,
                type : "get"
            },
            rowId : 'id',

            columns : [
            // {
            // title : "id",
            // data : "id"
            // },
            {
                title : "用户账号",
                data : "username",
                className : "text-center"
            }, {
                title : "用户姓名",
                data : "realName",
                className : "text-center"
            }, {
                title : "手机号码",
                data : "mobile",
                className : "text-center"
            }, {
                title : "邮箱地址",
                data : "email",
                className : "text-center"
            }, {
                title : "状态",
                data : "sysState",
                className : "text-center",
                createdCell : function(td, cellData, rowData, row, col) {
                    var $td = $(td);
                    if (cellData == 0 || cellData == "0") {
                        $td.html("正常");
                    } else if (cellData == 1 || cellData == "1") {
                        $td.html("锁定");
                    }

                }
            }, {
                title : "操作",
                data : null,
                className : "text-center",
                createdCell : function(td, cellData, rowData, row, col) {
                    var $td = $(td);
                    $td.empty();
                    $td.dtOperBtn("info", function() {
                        var $infoModal = $("#system_user_info_modal");
                        $infoModal.loadForm($.extend({}, rowData, {

                        }));
                        $infoModal.modal("show");
                    });
                    $td.dtOperBtn("edit", function() {
                        var $editModal = $("#system_user_edit_modal");
                        var $editForm = $("#system_user_edit_form");
                        cleanValidInfo(editValidator, $editForm);
                        $editModal.loadForm(rowData);
                        $editModal.modal("show");
                    })

                    $td.dtOperBtn("remove", function() {
                        $.ajax({
                            url : baseApiUrl + "/" + rowData.id,
                            type : "delete",
                            toastr : true,
                            blockUi : true,
                            success : function(data, textStatus, jqXHR) {
                                if (data.success) {
                                    dtTable.ajax.reload();
                                }
                            }
                        })
                    })

                }
            } ]
        }

        var buttons = [ {
            extend : "create",
            action : function(e, dt, button, config) {
                var $addModal = $("#system_user_add_modal");
                var $addForm = $("#system_user_add_form");
                cleanValidInfo(addValidator, $addForm);
                $addModal.modal("show");
            }
        }, {
            extend : "search",
            action : function(e, dt, button, config) {
                var searchParams = $("#system_user_search_form").serializeObject();
                searchParams.staffName = $.encodeURIComponent(searchParams.staffName);
                dt.settings()[0].ajax.data = searchParams;
                dt.ajax.reload();
            }
        } ];

        var $table = $("#system_user_list_table");
        dtTable = $table.customizeDataTables(tableConfig, buttons);
    }

    function addViewSetting() {

        var $addModal = $("#system_user_add_modal");
        var $addForm = $("#system_user_add_form");

        addValidator = $addForm.validate({
            rules : {
                username : {
                    required : true,
                    rangelength : [ 4, 20 ],
                    remote : {
                        url : contextPath + "/system/user/api/usable-username",
                        type : "get"
                    }
                },
                password : {
                    required : true,
                    rangelength : [ 6, 20 ]
                },
                comfirmPassword : {
                    required : true,
                    equalTo : "#system_user_add_form_password"
                },
                realName : {
                    required : true,
                    rangelength : [ 2, 10 ]
                },
                mobile : {
                    required : true
                },
                email : {
                    required : true
                }

            },
            messages : {
                username : {
                    remote : "该用户名已被使用"
                }
            },
            errorElement : "em",
            errorPlacement : function(error, element) {
                // Add the `help-block` class to the error element
                error.addClass("help-block");

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents(".col-md-5").addClass("has-feedback");

                if (element.prop("type") === "checkbox") {
                    error.insertAfter(element.parent("label"));
                } else {
                    error.insertAfter(element);
                }

                // Add the span element, if doesn't exists, and apply the icon
                // classes to it.
                if (!element.next("span")[0]) {
                    $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
                }
            },
            success : function(label, element) {
                // Add the span element, if doesn't exists, and apply the icon
                // classes to it.
                if (!$(element).next("span")[0]) {
                    $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
                }
            },
            highlight : function(element, errorClass, validClass) {
                $(element).parents(".col-md-5").addClass("has-error").removeClass("has-success");
                $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
            },
            unhighlight : function(element, errorClass, validClass) {
                $(element).parents(".col-md-5").addClass("has-success").removeClass("has-error");
                $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
            },
            submitHandler : function(form) {
                console.info($(form).serialize());
                $.ajax({
                    url : baseApiUrl,
                    type : "post",
                    toastr : true,
                    blockUi : true,
                    data : $(form).serializeObject(),
                    success : function(data, textStatus, jqXHR) {
                        if (data.success) {
                            dtTable.ajax.reload();
                        }
                    }
                });
                $addModal.modal("hide");

            }
        });

    }

    function infoViewSetting() {

    }

    function editViewSetting() {
        var $editModal = $("#system_user_edit_modal");
        var $editForm = $("#system_user_edit_form");

        editValidator = $editForm.validate({
            rules : {
                // username : {
                // required : true,
                // rangelength : [ 4, 20 ],
                // remote : {
                // url : contextPath + "/system/user/api/usable-username",
                // type : "get"
                // }
                // },
                // password : {
                // required : true,
                // rangelength : [ 6, 20 ]
                // },
                realName : {
                    required : true,
                    rangelength : [ 2, 10 ]
                },
                mobile : {
                    required : true
                },
                email : {
                    required : true
                },
                sysState : {
                    required : true
                }
            },
            messages : {},
            errorElement : "em",
            errorPlacement : function(error, element) {
                // Add the `help-block` class to the error element
                error.addClass("help-block");

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents(".col-md-5").addClass("has-feedback");

                if (element.prop("type") === "checkbox") {
                    error.insertAfter(element.parent("label"));
                } else {
                    error.insertAfter(element);
                }

                // Add the span element, if doesn't exists, and apply the icon
                // classes to it.
                if (!element.next("span")[0]) {
                    $("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
                }
            },
            success : function(label, element) {
                // Add the span element, if doesn't exists, and apply the icon
                // classes to it.
                if (!$(element).next("span")[0]) {
                    $("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
                }
            },
            highlight : function(element, errorClass, validClass) {
                $(element).parents(".col-md-5").addClass("has-error").removeClass("has-success");
                $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
            },
            unhighlight : function(element, errorClass, validClass) {
                $(element).parents(".col-md-5").addClass("has-success").removeClass("has-error");
                $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
            },
            submitHandler : function(form) {
                var paramsObj = $(form).serializeObject();
                $.ajax({
                    url : baseApiUrl,
                    type : "put",
                    toastr : true,
                    blockUi : true,
                    data : JSON.stringify(paramsObj),
                    dataType : "json",
                    contentType : "application/json",
                    success : function(response, textStatus, jqXHR) {
                        if (response.success) {
                            dtTable.ajax.reload();
                        } else {
                            var errorMessage = "";
                            var data = response.data;
                            for ( var field in data) {
                                errorMessage += data[field] + ",";
                            }
                            toastr.error(errorMessage, "编辑用户失败！");
                        }
                    }
                });
                $editModal.modal("hide");

            }
        });
    }

    function cleanValidInfo(validator, form) {
        validator.resetForm();
        var $fieldDiv = form.find("div.col-md-5.has-feedback");
        $fieldDiv.removeClass("has-error has-success");
        $fieldDiv.find("span.form-control-feedback").removeClass("glyphicon-remove glyphicon-ok");
        form[0].reset();
    }

    return {
        init : function() {
            viewInit();
        }
    }

}();
jQuery(document).ready(function() {
    sysUserViewSetting.init();

});