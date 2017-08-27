var Layout = function () {
    var e = "layouts/layout2/img/", a = "layouts/layout2/css/", i = App.getResponsiveBreakpoint("md"), t = [], s = [],
        o = function () {
            var e, a = $(".page-content"), t = $(".page-sidebar"), s = $("body");
            if (s.hasClass("page-footer-fixed") === !0 && s.hasClass("page-sidebar-fixed") === !1) {
                var o = App.getViewPort().height - $(".page-footer").outerHeight() - $(".page-header").outerHeight();
                a.height() < o && a.css("min-height", o)
            } else {
                if (s.hasClass("page-sidebar-fixed")) e = d(), s.hasClass("page-footer-fixed") === !1 && (e -= $(".page-footer").outerHeight()); else {
                    var n = $(".page-header").outerHeight(), r = $(".page-footer").outerHeight();
                    e = App.getViewPort().width < i ? App.getViewPort().height - n - r : t.height() + 20, e + n + r <= App.getViewPort().height && (e = App.getViewPort().height - n - r)
                }
                a.css("min-height", e)
            }
        }, n = function (e, a, t) {
            var s = location.hash.toLowerCase(), o = $(".page-sidebar-menu");
            if ("click" === e || "set" === e ? a = $(a) : "match" === e && o.find("li > a").each(function () {
                    var e = $(this).attr("ui-sref");
                    if (t && e) {
                        if (t.is(e)) return void(a = $(this))
                    } else {
                        var i = $(this).attr("href");
                        if (i && (i = i.toLowerCase(), i.length > 1 && s.substr(1, i.length - 1) == i.substr(1))) return void(a = $(this))
                    }
                }), a && 0 != a.size() && "javascript:;" != a.attr("href") && "javascript:;" != a.attr("ui-sref") && "#" != a.attr("href") && "#" != a.attr("ui-sref")) {
                parseInt(o.data("slide-speed")), o.data("keep-expanded");
                o.hasClass("page-sidebar-menu-hover-submenu") === !1 ? o.find("li.nav-item.open").each(function () {
                    var e = !1;
                    $(this).find("li").each(function () {
                        var i = $(this).attr("ui-sref");
                        if (t && i) {
                            if (t.is(i)) return void(e = !0)
                        } else if ($(this).find(" > a").attr("href") === a.attr("href")) return void(e = !0)
                    }), e !== !0 && ($(this).removeClass("open"), $(this).find("> a > .arrow.open").removeClass("open"), $(this).find("> .sub-menu").slideUp())
                }) : o.find("li.open").removeClass("open"), o.find("li.active").removeClass("active"), o.find("li > a > .selected").remove(), a.parents("li").each(function () {
                    $(this).addClass("active"), $(this).find("> a > span.arrow").addClass("open"), 1 === $(this).parent("ul.page-sidebar-menu").size() && $(this).find("> a").append('<span class="selected"></span>'), 1 === $(this).children("ul.sub-menu").size() && $(this).addClass("open")
                }), "click" === e && App.getViewPort().width < i && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click()
            }
        }, r = function () {
            $(".page-sidebar-menu").on("click", "li > a.nav-toggle, li > a > span.nav-toggle", function (e) {
                var a = $(this).closest(".nav-item").children(".nav-link");
                if (!(App.getViewPort().width >= i && !$(".page-sidebar-menu").attr("data-initialized") && $("body").hasClass("page-sidebar-closed") && 1 === a.parent("li").parent(".page-sidebar-menu").size())) {
                    var t = a.next().hasClass("sub-menu");
                    if (!(App.getViewPort().width >= i && 1 === a.parents(".page-sidebar-menu-hover-submenu").size())) {
                        if (t === !1) return void(App.getViewPort().width < i && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click());
                        var s = a.parent().parent(), n = a, r = $(".page-sidebar-menu"), l = a.next(),
                            d = r.data("auto-scroll"), p = parseInt(r.data("slide-speed")), c = r.data("keep-expanded");
                        c !== !0 && (s.children("li.open").children("a").children(".arrow").removeClass("open"), s.children("li.open").children(".sub-menu:not(.always-open)").slideUp(p), s.children("li.open").removeClass("open"));
                        var h = -200;
                        l.is(":visible") ? ($(".arrow", n).removeClass("open"), n.parent().removeClass("open"), l.slideUp(p, function () {
                            d === !0 && $("body").hasClass("page-sidebar-closed") === !1 && ($("body").hasClass("page-sidebar-fixed") ? r.slimScroll({scrollTo: n.position().top}) : App.scrollTo(n, h)), o()
                        })) : t && ($(".arrow", n).addClass("open"), n.parent().addClass("open"), l.slideDown(p, function () {
                            d === !0 && $("body").hasClass("page-sidebar-closed") === !1 && ($("body").hasClass("page-sidebar-fixed") ? r.slimScroll({scrollTo: n.position().top}) : App.scrollTo(n, h)), o()
                        })), e.preventDefault()
                    }
                }
            }), App.isAngularJsApp() && $(".page-sidebar-menu li > a").on("click", function (e) {
                App.getViewPort().width < i && $(this).next().hasClass("sub-menu") === !1 && $(".page-header .responsive-toggler").click()
            }), $(".page-sidebar").on("click", " li > a.ajaxify", function (e) {
                e.preventDefault(), App.scrollTop();
                var a = $(this).attr("href"), t = $(".page-sidebar ul");
                t.children("li.active").removeClass("active"), t.children("arrow.open").removeClass("open"), $(this).parents("li").each(function () {
                    $(this).addClass("active"), $(this).children("a > span.arrow").addClass("open")
                }), $(this).parents("li").addClass("active"), App.getViewPort().width < i && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click(), Layout.loadAjaxContent(a, $(this))
            }), $(".page-content").on("click", ".ajaxify", function (e) {
                e.preventDefault(), App.scrollTop();
                var a = $(this).attr("href");
                App.startPageLoading(), App.getViewPort().width < i && $(".page-sidebar").hasClass("in") && $(".page-header .responsive-toggler").click(), Layout.loadAjaxContent(a)
            }), $(document).on("click", ".page-header-fixed-mobile .page-header .responsive-toggler", function () {
                App.scrollTop()
            }), c(), $(".page-sidebar").on("click", ".sidebar-search .remove", function (e) {
                e.preventDefault(), $(".sidebar-search").removeClass("open")
            }), $(".page-sidebar .sidebar-search").on("keypress", "input.form-control", function (e) {
                if (13 == e.which) return $(".sidebar-search").submit(), !1
            }), $(".sidebar-search .submit").on("click", function (e) {
                e.preventDefault(), $("body").hasClass("page-sidebar-closed") && $(".sidebar-search").hasClass("open") === !1 ? (1 === $(".page-sidebar-fixed").size() && $(".page-sidebar .sidebar-toggler").click(), $(".sidebar-search").addClass("open")) : $(".sidebar-search").submit()
            }), 0 !== $(".sidebar-search").size() && ($(".sidebar-search .input-group").on("click", function (e) {
                e.stopPropagation()
            }), $("body").on("click", function () {
                $(".sidebar-search").hasClass("open") && $(".sidebar-search").removeClass("open")
            }))
        }, l = function () {
            $(".page-header").on("click", ".search-form", function (e) {
                $(this).addClass("open"), $(this).find(".form-control").focus(), $(".page-header .search-form .form-control").on("blur", function (e) {
                    $(this).closest(".search-form").removeClass("open"), $(this).unbind("blur")
                })
            }), $(".page-header").on("keypress", ".hor-menu .search-form .form-control", function (e) {
                if (13 == e.which) return $(this).closest(".search-form").submit(), !1
            }), $(".page-header").on("mousedown", ".search-form.open .submit", function (e) {
                e.preventDefault(), e.stopPropagation(), $(this).closest(".search-form").submit()
            })
        }, d = function () {
            var e = App.getViewPort().height - $(".page-header").outerHeight();
            return $("body").hasClass("page-footer-fixed") && (e -= $(".page-footer").outerHeight()), e
        }, p = function () {
            var e = $(".page-sidebar-menu");
            return App.destroySlimScroll(e), 0 === $(".page-sidebar-fixed").size() ? void o() : void(App.getViewPort().width >= i && (e.attr("data-height", d()), App.initSlimScroll(e), o()))
        }, c = function () {
            var e = $("body");
            e.hasClass("page-sidebar-fixed") && $(".page-sidebar").on("mouseenter", function () {
                e.hasClass("page-sidebar-closed") && $(this).find(".page-sidebar-menu").removeClass("page-sidebar-menu-closed")
            }).on("mouseleave", function () {
                e.hasClass("page-sidebar-closed") && $(this).find(".page-sidebar-menu").addClass("page-sidebar-menu-closed")
            })
        }, h = function () {
            var e = $("body");
            $("body").on("click", ".sidebar-toggler", function (a) {
                var i = $(".page-sidebar"), t = $(".page-sidebar-menu");
                $(".sidebar-search", i).removeClass("open"), e.hasClass("page-sidebar-closed") ? (e.removeClass("page-sidebar-closed"), t.removeClass("page-sidebar-menu-closed"), Cookies && Cookies.set("sidebar_closed", "0")) : (e.addClass("page-sidebar-closed"), t.addClass("page-sidebar-menu-closed"), e.hasClass("page-sidebar-fixed") && t.trigger("mouseleave"), Cookies && Cookies.set("sidebar_closed", "1")), $(window).trigger("resize")
            })
        }, u = function () {
            $("body").on("shown.bs.tab", 'a[data-toggle="tab"]', function () {
                o()
            })
        }, g = function () {
            var e = 300, a = 500;
            navigator.userAgent.match(/iPhone|iPad|iPod/i) ? $(window).bind("touchend touchcancel touchleave", function (i) {
                $(this).scrollTop() > e ? $(".scroll-to-top").fadeIn(a) : $(".scroll-to-top").fadeOut(a)
            }) : $(window).scroll(function () {
                $(this).scrollTop() > e ? $(".scroll-to-top").fadeIn(a) : $(".scroll-to-top").fadeOut(a)
            }), $(".scroll-to-top").click(function (e) {
                return e.preventDefault(), $("html, body").animate({scrollTop: 0}, a), !1
            })
        }, f = function () {
            $(".full-height-content").each(function () {
                var e, a = $(this);
                if (e = App.getViewPort().height - $(".page-header").outerHeight(!0) - $(".page-footer").outerHeight(!0) - $(".page-title").outerHeight(!0) - $(".page-bar").outerHeight(!0), a.hasClass("portlet")) {
                    var t = a.find(".portlet-body");
                    App.destroySlimScroll(t.find(".full-height-content-body")), e = e - a.find(".portlet-title").outerHeight(!0) - parseInt(a.find(".portlet-body").css("padding-top")) - parseInt(a.find(".portlet-body").css("padding-bottom")) - 2, App.getViewPort().width >= i && a.hasClass("full-height-content-scrollable") ? (e -= 35, t.find(".full-height-content-body").css("height", e), App.initSlimScroll(t.find(".full-height-content-body"))) : t.css("min-height", e)
                } else App.destroySlimScroll(a.find(".full-height-content-body")), App.getViewPort().width >= i && a.hasClass("full-height-content-scrollable") ? (e -= 35, a.find(".full-height-content-body").css("height", e), App.initSlimScroll(a.find(".full-height-content-body"))) : a.css("min-height", e)
            })
        };
    return {
        initHeader: function () {
            l()
        }, setSidebarMenuActiveLink: function (e, a) {
            n(e, a)
        }, setAngularJsSidebarMenuActiveLink: function (e, a, i) {
            n(e, a, i)
        }, initSidebar: function (e) {
            p(), r(), h(), App.isAngularJsApp() && n("match", null, e), App.addResizeHandler(p)
        }, initContent: function () {
            f(), u(), App.addResizeHandler(o), App.addResizeHandler(f)
        }, initFooter: function () {
            g()
        }, init: function () {
            this.initHeader(), this.initSidebar(), this.initContent(), this.initFooter()
        }, loadAjaxContent: function (e, a) {
            var i = $(".page-content .page-content-body");
            App.startPageLoading({animate: !0}), $.ajax({
                type: "GET",
                cache: !1,
                url: e,
                dataType: "html",
                success: function (e) {
                    App.stopPageLoading(), i.html(e);
                    for (var s = 0; s < t.length; s++) t[s].call(e);
                    a.size() > 0 && 0 === a.parents("li.open").size() && $(".page-sidebar-menu > li.open > a").click(), Layout.fixContentHeight(), App.initAjax()
                },
                error: function (e, a, t) {
                    App.stopPageLoading(), i.html("<h4>Could not load the requested content.</h4>");
                    for (var o = 0; o < s.length; o++) s[o].call(e)
                }
            })
        }, addAjaxContentSuccessCallback: function (e) {
            t.push(e)
        }, addAjaxContentErrorCallback: function (e) {
            s.push(e)
        }, fixContentHeight: function () {
            o()
        }, initFixedSidebarHoverEffect: function () {
            c()
        }, initFixedSidebar: function () {
            p()
        }, getLayoutImgPath: function () {
            return App.getAssetsPath() + e
        }, getLayoutCssPath: function () {
            return App.getAssetsPath() + a
        }
    }
}();
App.isAngularJsApp() === !1 && jQuery(document).ready(function () {
    Layout.init()
});