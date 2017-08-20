/**
 *
 */
+function($) {

    /*
     * 
     * 
     * 
       <div class="col-md-4">
            <div class="form-group">
                <label class="control-label col-md-4">呼叫时间</label>
                <div class="col-md-8">
                    <div class="input-group date default-datepicker3">
                        <input name="" type="text" class="form-control" readonly="readonly">
                        <div class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-3">处理时间</label>
                <div class="col-md-9">
                    <div class="input-group input-daterange default-datepicker3-daterange">
                        <input type="text" class="form-control">
                        <!--  -->
                        <span class="input-group-addon">至</span>
                        <!--  -->
                        <input type="text" class="form-control">
                    </div>
                </div>
            </div>
        </div>


     */
    $.fn.datepicker.defaults.language = "zh-CN";
    $.fn.datepicker.defaults.format = "yyyy-mm-dd";
    $.fn.datepicker.defaults.autoclose = true;
    $.fn.datepicker.defaults.clearBtn = true;
    /*
    var _defaultConfig = {

        // daysOfWeekHighlighted : [ 0, 6 ],
//        endDate : "0d",
        format : "yyyy-mm-dd",
        language : "zh-CN",
        autoclose : true,
        clearBtn : true
    }
    */

    $(".default-datepicker3").datepicker();

    $(".default-datepicker3-daterange input").each(function() {
        $(this).datepicker();
        // $(this).datepicker("clearDates");
    });

}(jQuery);