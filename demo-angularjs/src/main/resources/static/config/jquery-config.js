+function() {

    jQuery.extend({
        numberFormat : function(number, option) {
            if (typeof number !== 'number' && typeof number !== 'string') {
                return "0.00";
            }
            var _defaultConfig = {
                thousands : ",",
                decimal : ".",
                precision : 2,
                prefix : "",
                suffix : ""
            }
            var thousands, decimal, precision, prefix, suffix;
            var config = $.extend({}, _defaultConfig, option);

            thousands = config.thousands;
            decimal = config.decimal;
            precision = config.precision;
            prefix = config.prefix;
            suffix = config.suffix;

            var negative = d < 0 ? '-' : '';
            var flo = parseFloat(number);

            if (isNaN(flo)) {
                return "";
            }

            flo = flo.toFixed(precision);
            number = Math.abs(flo);

            var intPart = parseInt(d, 10);
            var floatPart = precision ? decimal + (number - intPart).toFixed(precision).substring(2) : '';

            return negative + (prefix || '') + intPart.toString().replace(/\B(?=(\d{3})+(?!\d))/g, thousands) + floatPart + (suffix || '');
        },

        encodeURI : function(uri) {
            return encodeURI(encodeURI(uri));
        },

        encodeURIComponent : function(component) {
            return encodeURIComponent(encodeURIComponent(component))
        }

    });

    jQuery.fn.extend({
        serializeObject : function() {
            var o = {};
            var a = this.serializeArray();
            jQuery.each(a, function() {
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [ o[this.name] ];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        },
        loadForm : function(data) {
            var $self = $(this);
            var form = $self;
            for ( var name in data) {
                var val = data[name];
                form.find('input[name="' + name + '"]').val(val);
                form.find('textarea[name="' + name + '"]').val(val);
                // console.info(typeof val + " " + val);
                // form.find('select[name="' + name + '"]').attr("value", val);
                form.find('select[name="' + name + '"]').val(val);
            }
        }
    });

    // $.fn.serializeObject = function() {
    // var o = {};
    // var a = this.serializeArray();
    // $.each(a, function() {
    // if (o[this.name] !== undefined) {
    // if (!o[this.name].push) {
    // o[this.name] = [ o[this.name] ];
    // }
    // o[this.name].push(this.value || '');
    // } else {
    // o[this.name] = this.value || '';
    // }
    // });
    // return o;
    // };

}();