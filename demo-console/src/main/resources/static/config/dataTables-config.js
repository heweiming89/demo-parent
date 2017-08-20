+function() {

    // <!-- 审核 -->
    var checkBtn = '<a class="btn yellow btn-outline" href="javascript:;" title="审核"> <i class="fa fa-check-square-o"></i></a>';
    // <!-- 详情 -->
    var infoBtn = '<a class="btn blue btn-outline" href="javascript:;" title="详情"> <i class="fa fa-bars"></i></a>';
    // <!-- 编辑 -->
    var editBtn = '<a class="btn purple btn-outline" href="javascript:;" title="编辑"> <i class="fa fa-pencil"></i></a>';
    // <!-- 删除 -->
    var removeBtn = '<a class="btn red btn-outline" href="javascript:;" title="删除"> <i class="fa fa-trash-o"></i></a>';

    window.dtOptionalData = null;

    // Use the event as a custom error handler:
    $.fn.dataTable.ext.errMode = 'none';

    $.fn.customizeDataTables = function(customizeConfig, buttons) {
        var table = null;
        var _defaultTableConfig = {
            language : {
                sProcessing : "数据加载中...",
                sLengthMenu : "显示 _MENU_ 项结果",
                sZeroRecords : "没有匹配结果",
                sInfo : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                sInfoEmpty : "显示第 0 至 0 项结果，共 0 项",
                sInfoFiltered : "(由 _MAX_ 项结果过滤)",
                sInfoPostFix : "",
                sSearch : "搜索:",
                sUrl : "",
                sEmptyTable : "表中数据为空",
                sLoadingRecords : "载入中...",
                sInfoThousands : ",",
                oPaginate : {
                    sFirst : "首页",
                    sPrevious : "上页",
                    sNext : "下页",
                    sLast : "末页"
                },
                oAria : {
                    sSortAscending : ": 以升序排列此列",
                    sSortDescending : ": 以降序排列此列"
                },
                buttons : {
                    copyTitle : "复制到剪切板",
                    copyKeys : "Appuyez sur <i>ctrl</i> ou <i>\u2318</i> + <i>C</i> pour copier les données du tableau à votre presse-papiers. <br><br>Pour annuler, cliquez sur ce message ou appuyez sur Echap.",
                    copySuccess : {
                        "_" : "已复制   %d 条记录",
                        "1" : "已复制   1 条记录"
                    }
                },
                select : {
                    rows : {
                        "_" : "已选择  %d 条记录",
                        "0" : "",
                        "1" : "选中了 1 条记录"
                    }
                }
            },
            ordering : false,
            searching : false,
            pagingType : "full_numbers",
            processing : true,
            serverSide : true,
            deferRender : true
        // ,
        // select : {
        // style : 'single',
        // blurable : true
        // }
        };
        var tableConfig = $.extend(true, {}, _defaultTableConfig, customizeConfig);
        table = $(this)
        // ajax事件-当datatable发送ajax请求完成时
        .on('xhr.dt', function(e, settings, json, xhr) {
            // 获取自定义数据
            if (json) {
                dtOptionalData = json.optionalData;
            }

        }) // 错误事件-当在加载数据发生错误时
        .on('error.dt', function(e, settings, techNote, message) {
            var errMsg = message.substr(message.indexOf('-') + 2);
            if (errMsg.indexOf("Invalid JSON response") != -1) {
//                window.location.href = contextPath + "/login";
            }
            toastr.error(errMsg, "操作失败");
        }).DataTable(tableConfig);
        if (buttons) {
            new $.fn.dataTable.Buttons(table, {
                buttons : buttons
            });
            var $div = $('div.row:first', table.table().container());
            var $subDiv = $("div.col-sm-6", $div);
            if ($subDiv.size() == 2) {
                // 克隆 选这页面记录数 按钮和时间
                var $cloneSubDiv0 = $($subDiv[0]).clone(true);
                // $(".dataTables_length",
                // $cloneSubDiv0).addClass("pull-right");
                $div.empty();
                table.buttons().container().removeClass("btn-group");
                // .addClass("pull-left");
                var $btn = $(".btn", table.buttons().container());
                $btn.css("margin-right", "5px").removeClass("btn-default");
                $($subDiv[1]).append(table.buttons().container());
                $div.append($cloneSubDiv0).append($subDiv[1]);
            }
            // table.buttons().container().appendTo($('.col-sm-6:eq(1)',
            // table.table().container()));
        }
        return table;
    };

    $.fn.dataTable.ext.buttons.search = {
        text : '<i class="fa fa-search" aria-hidden="true"></i> <span class="chinese-character">查询</span>',
        className : 'yellow btn-outline'
    }

    $.fn.dataTable.ext.buttons.create = {
        text : '<i class="fa fa-user-plus" aria-hidden="true"></i> <span class="chinese-character">新建</span>',
        className : 'green btn-outline',
    };

    $.fn.dataTable.ext.buttons.update = {
        className : 'btn btn-primary table-update',
    };

    $.fn.dataTable.ext.buttons.remove = {
        className : 'btn btn-danger table-delete',
    };

    $.fn.dtOperBtn = function(type, fun) {
        var $self = $(this);
        var $btn = $("<button>按钮</button");
        var callBack = fun;
        if (type === "check") {
            $btn = $(checkBtn);
        } else if (type === "edit") {
            $btn = $(editBtn);
        } else if (type === "info") {
            $btn = $(infoBtn);
        } else if (type === "remove") {
            callBack = function() {
                swal({
                    title : '确认删除',
                    type : 'warning',
                    showCancelButton : true,
                    confirmButtonText : '<span class="chinese-character">确认</span>',
                    cancelButtonText : '<span class="chinese-character">取消</span>'
                }).then(function() {
                    fun();
                });
            }
            $btn = $(removeBtn);
        }
        $btn.on("click", callBack);
        $self.append($btn);
    }

}();