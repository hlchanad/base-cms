const MyLogout = function() {

    this.init = function() {
        this.setupLogoutButtonListener();
    };

    this.setupLogoutButtonListener = function () {
        $("a#logout-a-tag").click(function(event) {
            event.preventDefault();
            $("form#logout-form").submit();
        })
    };
};

const myLogout = new MyLogout();
myLogout.init();