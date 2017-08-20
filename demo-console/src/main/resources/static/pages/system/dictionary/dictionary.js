var sysUserViewSetting = function() {
    var baseApiUrl = contextPath + "/system/dictionary/api";
    var dtTable = null;

    var addValidator = null;
    var infoValidator = null;
    var editValidator = null;
    var commentValidator = null;

    function viewInit() {
        listViewSetting();
        addViewSetting();
        infoViewSetting();
        editViewSetting();
        // addCommentSetting();
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
                        title : "名词（中文）",
                        data : "name",
                        className : "text-center"
                    },
                    {
                        title : "名词（英文）",
                        data : "code",
                        className : "text-center"
                    },
                    {
                        title : "类别",
                        data : "type",
                        className : "text-center",
                        createdCell : function(td, cellData, rowData, row, col) {
                            $(td).html(formatTypeForDictionary(cellData));
                        }
                    },
                    {
                        title : "名词解释",
                        data : "content",
                        className : "text-center",
                        width : "30%"
                    },
                    // {
                    // title : "名词场景描述",
                    // data : "description",
                    // className : "text-center"
                    // },
                    {
                        title : "操作",
                        data : null,
                        className : "text-center",
                        width : "20%",
                        createdCell : function(td, cellData, rowData, row, col) {
                            var $td = $(td);
                            $td.empty();
                            $td.dtOperBtn("info", function() {
                                var $infoModal = $("#system_dictionary_info_modal");
                                var $commentModal = $('#system_dictionary_info_modal');
                                var $commentForm = $("#system_dictionary_info_form");
                                cleanValidInfo11(infoValidator, $commentForm);
                                cleanValidInfoForColMd7(infoValidator, $commentForm);
                                $commentModal.loadForm($.extend({}, rowData, {
                                    dictionaryId : rowData.id
                                }))
                                var $commentListDiv = $("#dictionary_comment_list");
                                $commentListDiv.empty();
                                $.ajax({
                                    url : baseApiUrl + "/comment/" + rowData.id,
                                    type : "get",
                                    toastr : true,
                                    blockUi : true,
                                    success : function(response, textStatus, jqXHR) {
                                        if (response.success) {
                                            var data = response.data;
                                            if (data != null && data.length > 0) {
                                                for (var i = 0; i < data.length; i++) {
                                                    var dc = data[i];
                                                    var commentDiv = '<div class="item"><div class="item-head"><div class="item-details">' + '<a href="" class="item-name primary-link">'
                                                            + dc.createrUsername + '</a> <span class="item-label">' + dc.createTime
                                                            + '</span></div><span class="item-status"><span class="badge badge-empty badge-success"></span>' + '</span></div><div class="item-body">'
                                                            + dc.comment + '</div></div>';
                                                    $commentListDiv.append(commentDiv);
                                                }
                                            }
                                        }
                                    }
                                })

                                $infoModal.loadForm($.extend({}, rowData, {
                                    type : formatTypeForDictionary(rowData.type)
                                }));

                                $infoModal.modal("show");
                            });
                            $td.dtOperBtn("edit", function() {
                                var $editModal = $("#system_dictionary_edit_modal");
                                var $editForm = $("#system_dictionary_edit_form");
                                cleanValidInfo(editValidator, $editForm);
                                $editModal.loadForm(rowData);
                                $editModal.modal("show");
                            })

                            var $btn = $('<a class="btn green btn-outline" href="javascript:;" title="评论"> <i class="fa fa-commenting-o"></i></a>');
                            $btn.on("click", function() {
                                var $commentModal = $('#system_dictionary_comment_modal');
                                var $commentForm = $("#system_dictionary_comment_form");
                                cleanValidInfo11(commentValidator, $commentForm);
                                cleanValidInfoForColMd7(commentValidator, $commentForm);
                                $commentModal.loadForm($.extend({}, rowData, {
                                    dictionaryId : rowData.id
                                }))
                                var $commentListDiv = $("#dictionary_comment_list");
                                $commentListDiv.empty();
                                $.ajax({
                                    url : baseApiUrl + "/comment/" + rowData.id,
                                    type : "get",
                                    toastr : true,
                                    blockUi : true,
                                    success : function(response, textStatus, jqXHR) {
                                        if (response.success) {
                                            var data = response.data;
                                            if (data != null && data.length > 0) {
                                                for (var i = 0; i < data.length; i++) {
                                                    var dc = data[i];
                                                    var commentDiv = '<div class="item"><div class="item-head"><div class="item-details">' + '<a href="" class="item-name primary-link">'
                                                            + dc.createrUsername + '</a> <span class="item-label">' + dc.createTime
                                                            + '</span></div><span class="item-status"><span class="badge badge-empty badge-success"></span>' + '</span></div><div class="item-body">'
                                                            + dc.comment + '</div></div>';
                                                    $commentListDiv.append(commentDiv);
                                                }
                                            }
                                        }
                                    }
                                })
                                $commentModal.modal("show");
                            });
//                            $td.append($btn);

                            $td.dtOperBtn("remove", function() {
                                // var $addModal =
                                // $("#system_dictionary_comment_modal");
                                // var $addForm =
                                // $("#system_dictionary_comment_form");
                                // cleanValidInfo(commentValidator, $addForm);
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
                var $addModal = $("#system_dictionary_add_modal");
                var $addForm = $("#system_dictionary_add_form");
                cleanValidInfo(addValidator, $addForm);
                $addModal.modal("show");
            }
        }, {
            extend : "search",
            action : function(e, dt, button, config) {
                var searchParams = $("#system_dictionary_search_form").serializeObject();
                searchParams.staffName = $.encodeURIComponent(searchParams.staffName);
                dt.settings()[0].ajax.data = searchParams;
                dt.ajax.reload();
            }
        } ];

        var $table = $("#system_user_list_table");
        dtTable = $table.customizeDataTables(tableConfig, buttons);
    }

    function addViewSetting() {

        var $addModal = $("#system_dictionary_add_modal");
        var $addForm = $("#system_dictionary_add_form");

        addValidator = $addForm.validate({
            rules : {
                name : {
                    required : true,
                    rangelength : [ 1, 20 ],
                    remote : {
                        url : contextPath + "/system/dictionary/api/usable-name",
                        type : "get"
                    }
                },
                content : {
                    required : true
                },

            },
            messages : {
                name : {
                    remote : "该名词已存在"
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

        var $editModal = $("#system_dictionary_info_modal");
        var $editForm = $("#system_dictionary_info_form");

        infoValidator = $editForm.validate({
            rules : {
                comment : {
                    required : true,
                    rangelength : [ 14, 140 ]
                }
            },
            messages : {
                comment : {
                    required : "评论不能为空！",
                }
            },
            errorElement : "em",
            errorPlacement : function(error, element) {
                // Add the `help-block` class to the error element
                error.addClass("help-block");

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents(".col-md-11").addClass("has-feedback");

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
                $(element).parents(".col-md-11").addClass("has-error").removeClass("has-success");
                $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
            },
            unhighlight : function(element, errorClass, validClass) {
                $(element).parents(".col-md-11").addClass("has-success").removeClass("has-error");
                $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
            },
            submitHandler : function(form) {
                var paramsObj = $(form).serializeObject();
                $.ajax({
                    url : baseApiUrl + "/comment",
                    type : "post",
                    toastr : true,
                    blockUi : true,
                    data : paramsObj,
                    success : function(response, textStatus, jqXHR) {
                        if (response.success) {
                            dtTable.ajax.reload();
                        } else {
                            var errorMessage = "";
                            var data = response.data;
                            for ( var field in data) {
                                errorMessage += data[field] + ",";
                            }
                            toastr.error(errorMessage, "增加评论失败！");
                        }
                    }
                });
                $editModal.modal("hide");

            }
        });

    }

    function editViewSetting() {
        var $editModal = $("#system_dictionary_edit_modal");
        var $editForm = $("#system_dictionary_edit_form");

        editValidator = $editForm.validate({
            rules : {
                content : {
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
                            toastr.error(errorMessage, "编辑字典失败！");
                        }
                    }
                });
                $editModal.modal("hide");

            }
        });
    }

    function addCommentSetting() {
        var $editModal = $("#system_dictionary_comment_modal");
        var $editForm = $("#system_dictionary_comment_form");

        commentValidator = $editForm.validate({
            rules : {
                comment : {
                    required : true,
                    rangelength : [ 14, 140 ]
                }
            },
            messages : {
                comment : {
                    required : "评论不能为空！",
                }
            },
            errorElement : "em",
            errorPlacement : function(error, element) {
                // Add the `help-block` class to the error element
                error.addClass("help-block");

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents(".col-md-11").addClass("has-feedback");

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
                $(element).parents(".col-md-11").addClass("has-error").removeClass("has-success");
                $(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
            },
            unhighlight : function(element, errorClass, validClass) {
                $(element).parents(".col-md-11").addClass("has-success").removeClass("has-error");
                $(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
            },
            submitHandler : function(form) {
                var paramsObj = $(form).serializeObject();
                $.ajax({
                    url : baseApiUrl + "/comment",
                    type : "post",
                    toastr : true,
                    blockUi : true,
                    data : paramsObj,
                    success : function(response, textStatus, jqXHR) {
                        if (response.success) {
                            dtTable.ajax.reload();
                        } else {
                            var errorMessage = "";
                            var data = response.data;
                            for ( var field in data) {
                                errorMessage += data[field] + ",";
                            }
                            toastr.error(errorMessage, "增加评论失败！");
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

    function cleanValidInfo11(validator, form) {
        validator.resetForm();
        var $fieldDiv = form.find("div.col-md-11.has-feedback");
        $fieldDiv.removeClass("has-error has-success");
        $fieldDiv.find("span.form-control-feedback").removeClass("glyphicon-remove glyphicon-ok");
        form[0].reset();
    }

    function cleanValidInfoForColMd7(validator, form) {
        validator.resetForm();
        var $fieldDiv = form.find("div.col-md-7");
        $fieldDiv.removeClass("has-error has-success");
        $fieldDiv.find("span.form-control-feedback").removeClass("glyphicon-remove glyphicon-ok");
        form[0].reset();
    }

    function formatTypeForDictionary(typeCode) {
        var desc = "未知";
        if (typeCode == "001") {
            desc = "类别一";
        } else if (typeCode == "002") {
            desc = "类别二";
        } else if (typeCode == "003") {
            desc = "类别三";
        }
        return desc;

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