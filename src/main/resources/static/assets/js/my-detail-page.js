const MyDetailPage = function() {

    this.setupDetailButtons = function() {

        $(".detail-buttons .detail-delete-button").click(function(event) {
            event.preventDefault();

            const deleteUrl   = $(this).attr("data-href");
            const redirectUrl = $(this).attr("data-redirect");

            swal({
                title: "Are you sure to DELETE this record ?",
                type: "warning",
                showCancelButton: true
            })
                .then((result) => {
                    if (!result.value) return;

                    $.ajax({
                        url: deleteUrl,
                        method: "delete",
                        success: function (response) {
                            swal({
                                type: "success",
                                title: "Success!",
                                timer: 3000
                            })
                                .then(function() {
                                    window.location.replace(redirectUrl);
                                });
                        },
                        error: function () {
                            swal({
                                type: "error",
                                title: "Failed, please try again later"
                            });
                        }
                    });
                })
                .catch(function () {
                });
        });
    }
};