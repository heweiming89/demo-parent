var WebApplication = function() {

    var $contentWrapper = $("div.page-content-wrapper");

    var blockUiType = {
        pageLoading : "pageLoading"
    }

    function viewInitSetting() {
        // menuSetting();
        logoutSetting();

        // $("a.app-menu-a").on("click", function(e) {
        // e.preventDefault();
        // var $contentWrapper = $("#index_content_wrapper");
        // var $menu = $(".page-sidebar-menu.page-sidebar-menu-hover-submenu");
        // $menu.find(".active").removeClass("active");
        // // $(this).addClass("active");
        // $(this).closest("li.nav-item").addClass("active");
        //
        // var loadUrl = this.href;
        // $contentWrapper.load(loadUrl, function(responseText, textStatus,
        // jqXHR) {
        // if (textStatus == "success" || textStatus == "notmodified") {
        // var html = '<h1>' + $(e.target).data("freedom-page-title") +
        // '&nbsp;&nbsp;<small>' + moment().format('YYYY-MM-DD HH:mm:ss') +
        // '</small></h1>';
        // $("#index-page-title").html(html);
        // } else {
        //
        // }
        // }, "html");

        // });
    }

    function menuSetting() {
        $(document).on("click", "#home_link,.sub-menu .nav-item .nav-link:not(.nav-toggle)", function(event) {
            event.preventDefault();
            var loadUrl = this.href;

            $.ajax({
                url : loadUrl,
                type : "get",
                data : {
                    date : +new Date()
                },
                blockUi : true,
                blockUiType : blockUiType.pageLoading,
                success : function(data, textStatus, jqXHR) {
                    if (textStatus == "success" || textStatus == "notmodified") {
                        successHandler.call(event.target);
                    }
                    $contentWrapper.html(data);
                },
                error : function(jqXHR, textStatus, errorThrown) {

                }

            });

            // $contentWrapper.load(loadUrl, function(responseText, textStatus,
            // jqXHR) {
            // if (textStatus == "success" || textStatus == "notmodified") {
            // successHandler.call(event.target);
            // }
            // }, "html");
        });

        function successHandler() {
            $ele = $(this);
            if (!$ele.is("a")) {
                $ele = $ele.closest("a");
            }
            var active = "active";
            var open = "open";
            var $menu = $(".page-sidebar-menu");

            $menu.find(".selected").remove();
            $menu.find(".nav-item").removeClass(active + " " + open).end().find(".arrow").removeClass("open");

            var selected = '<span class="selected"></span>';
            $ele.append(selected);//

            $ele.closest(".nav-item").addClass(active).addClass(open)//
            .parent().prev().append(selected)//
            .closest(".nav-item").addClass(active).addClass(open);
        }

    }

    function logoutSetting() {
        $("#logout_link").on("click", function(event) {
            event.preventDefault();
            $("<form />", {
                action : contextPath + "/logout",
                method : "get"
            }).appendTo(document.body)[0].submit();
        });
    }

    function ajaxSetting() {
        // ajax blockUi 提示
        $(document).ajaxSend(function(evt, request, settings) {
            if (settings.blockUi) {
                if (settings.blockUiType && settings.blockUiType == blockUiType.pageLoading) {
                    App.startPageLoading({
                        animate : true
                    });
                } else {
                    App.blockUI({
                        boxed : true,
                        message : settings.blockUiMsg ? settings.blockUiMsg : "数据加载中..."
                    });
                }
            }
        }).ajaxComplete(function(event, response, settings) {
            if (settings.blockUi) {
                if (settings.blockUiType && settings.blockUiType == blockUiType.pageLoading) {
                    App.stopPageLoading();
                } else {
                    App.unblockUI();
                }
            }

            if (settings.toastr) {
                toastr.clear();
                if (response.responseJSON && response.responseJSON.success) {
                    toastr["success"]('<span class="chinese-character">操作成功</span>', '<span class="chinese-character">提示消息</span>');
                } else {
                    toastr["error"]('<span class="chinese-character">操作失败</span>', '<span class="chinese-character">提示消息</span>');
                }
            }
        });
    }

    return {
        init : function() {
            viewInitSetting();
            ajaxSetting();
            // TODO
        }
    }

}();

jQuery(document).ready(function() {
    WebApplication.init();
    
    try {
        if (jQuery().fullCalendar) {
            var e = new Date, t = e.getDate(), a = e.getMonth(), i = e.getFullYear(), l = {};
            $("#calendar").width() <= 400 ? ($("#calendar").addClass("mobile"), l = {
                left: "title, prev, next",
                center: "",
                right: "today,month,agendaWeek,agendaDay"
            }) : ($("#calendar").removeClass("mobile"), l = App.isRTL() ? {
                right: "title",
                center: "",
                left: "prev,next,today,month,agendaWeek,agendaDay"
            } : {
                left: "title",
                center: "",
                right: "prev,next,today,month,agendaWeek,agendaDay"
            }), $("#calendar").fullCalendar("destroy"), $("#calendar").fullCalendar({
                disableDragging: !1,
                header: l,
                editable: !0,
                events: [{
                    title: "All Day",
                    start: new Date(i, a, 1),
                    backgroundColor: App.getBrandColor("yellow")
                }, {
                    title: "Long Event",
                    start: new Date(i, a, t - 5),
                    end: new Date(i, a, t - 2),
                    backgroundColor: App.getBrandColor("blue")
                }, {
                    title: "Repeating Event",
                    start: new Date(i, a, t - 3, 16, 0),
                    allDay: !1,
                    backgroundColor: App.getBrandColor("red")
                }, {
                    title: "Repeating Event",
                    start: new Date(i, a, t + 6, 16, 0),
                    allDay: !1,
                    backgroundColor: App.getBrandColor("green")
                }, {title: "Meeting", start: new Date(i, a, t + 9, 10, 30), allDay: !1}, {
                    title: "Lunch",
                    start: new Date(i, a, t, 14, 0),
                    end: new Date(i, a, t, 14, 0),
                    backgroundColor: App.getBrandColor("grey"),
                    allDay: !1
                }, {
                    title: "Birthday",
                    start: new Date(i, a, t + 1, 19, 0),
                    end: new Date(i, a, t + 1, 22, 30),
                    backgroundColor: App.getBrandColor("purple"),
                    allDay: !1
                }, {
                    title: "Click for Google",
                    start: new Date(i, a, 28),
                    end: new Date(i, a, 29),
                    backgroundColor: App.getBrandColor("yellow"),
                    url: "http://google.com/"
                }]
            })
        }
    } catch (e) {
       console.info(e);
    }

});