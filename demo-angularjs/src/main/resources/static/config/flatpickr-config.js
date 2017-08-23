+function() {

    $.fn.customizeFlatpickr = function(customizeConfig) {

        var _defaultConfig = {

        }

        var config = $.extend(true, {}, _defaultConfig, customizeConfig);

        return $(this).flatpickr(config);

    }

}();