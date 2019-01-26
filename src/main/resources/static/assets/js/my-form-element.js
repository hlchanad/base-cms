const MyFormElement = function() {

    this.initFormElements = function() {

        $("input.my-datepicker").daterangepicker({
            singleDatePicker: true,
            autoApply: true,
            locale: {
                format: "YYYY-MM-DD",
            },
        });

        $("input.my-timepicker").timepicker({
            showMeridian: false,
            minuteStep: 1,
        });

        $("input.my-datetimepicker").daterangepicker({
            singleDatePicker: true,
            autoApply: true,
            timePicker: true,
            timePicker24Hour: true,
            locale: {
                format: "YYYY-MM-DD HH:mm:ss",
            },
        });

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