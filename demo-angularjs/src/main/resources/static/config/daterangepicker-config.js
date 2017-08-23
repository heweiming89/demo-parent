+function() {

    $.fn.customizeDaterangepicker = function(customizeConfig, callback) {
        var _defaultConfig = {
            autoApply : true,
            startDate : moment().subtract(2, 'days'),
            endDate : moment(),
            locale : {
                direction : 'ltr',
                format : 'YYYY-MM-DD',
                separator : ' - ',
                applyLabel : '确认',
                cancelLabel : '取消',
                weekLabel : 'W',
                customRangeLabel : '自定义范围',
                daysOfWeek : moment.weekdaysMin(),
                monthNames : moment.monthsShort(),
                firstDay : moment.localeData().firstDayOfWeek()
            },
            single : true
        // ,
        // ranges : {
        // '今天' : [ moment(), moment() ],
        // '昨天' : [ moment().subtract(1, 'days'), moment().subtract(1, 'days')
        // ],
        // '近7天' : [ moment().subtract(6, 'days'), moment() ],
        // '最近30天' : [ moment().subtract(29, 'days'), moment() ],
        // '本月' : [ moment().startOf('month'), moment().endOf('month') ],
        // '上个月' : [ moment().subtract(1, 'month').startOf('month'),
        // moment().subtract(1, 'month').endOf('month') ]
        // }
        };
        var config = $.extend(true, {}, _defaultConfig, customizeConfig);
        $(this).daterangepicker(config, callback);

    }

}();
