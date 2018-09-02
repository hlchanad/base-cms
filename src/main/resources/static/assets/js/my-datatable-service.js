const MyDatatableService = function () {

    this.ajaxCall = function (dataTable, refreshDataTable, url, method) {
        $.ajax({
            url: url,
            method: method,
            success: function (response) {
                swal({
                    type: "success",
                    title: "Success!"
                });
                if (refreshDataTable) dataTable.refresh();
            },
            error: function () {
                swal({
                    type: "error",
                    title: "Failed, please try again later"
                });
            }
        });
    };

    this.ajaxCallWithConfirm = function (dataTable, dataTableTitle, action, refreshDataTable, url, method) {

        if (action == null) action = 'Don\'t forget to specify your action';

        swal({
            title: "Are you sure to " + action + " the " + dataTableTitle + " ?",
            type: "warning",
            showCancelButton: true
        })
            .then((result) => {
                if (!result.value) return;
                this.ajaxCall(dataTable, refreshDataTable, url, method);
            })
            .catch(function () {
            });
    };

    this.redirect = function (url) {
        window.location.replace(url);
    };

    this.getDefaultActionButtonRender = function () {
        return function (response) {
            // render function for "action"

            const actionButtons = JSON.parse(response);

            let actionButtonsHtml = "<div class='btn-group'>";

            for (let i = 0; i < actionButtons.length; i++) {
                const actionButton = actionButtons[i];

                switch (actionButton.type) {
                    case "REDIRECT":
                        actionButtonsHtml +=
                            "<a class='btn btn-" + actionButton.bootstrapColor + "' " +
                            "   href='" + actionButton.href + "'>" + actionButton.displayName + "</a>";
                        break;

                    case "DELETE":
                        actionButtonsHtml +=
                            "<a class='btn btn-" + actionButton.bootstrapColor + " action-delete' " +
                            "   href='#' data-href='" + actionButton.href + "'>" + actionButton.displayName + "</a>";
                        break;

                    default:
                        break;
                }
            }

            actionButtonsHtml += "</div>";

            return actionButtonsHtml;
        }
    }
};
