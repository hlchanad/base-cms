const MyPage = function (errorPopup) {

    this.init = function () {
        this.setupCsrfHeader();
        this.setupLogoutButtonListener();
        this.showErrorPopup(errorPopup);
    };

    this.setupLogoutButtonListener = function () {
        $("a#logout-a-tag").click(function (event) {
            event.preventDefault();
            $("form#logout-form").submit();
        })
    };

    this.setupCsrfHeader = function () {
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        });
    };

    this.showErrorPopup = function (errorPopup) {
        if (errorPopup == null) return;

        swal({
            type: errorPopup.type.toLowerCase(),
            title: errorPopup.title,
            text: errorPopup.text
        });
    }
};