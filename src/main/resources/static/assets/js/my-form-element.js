const MyFormElement = function() {

    this.initFormElements = function() {

        $("input.my-datepicker").datepicker({
            format: 'yyyy-mm-dd',
        });
        //
        $("input.my-timepicker").timepicker();
        
        // $("div.my-datetimepicker").datetimepicker();

        $("div.my-html-editor").summernote({
            height: 200,
            onfocus: function (e) {
                $("body").addClass("overlay-disabled");
            },
            onblur: function (e) {
                $("body").removeClass("overlay-disabled");
            }
        });
    }
};