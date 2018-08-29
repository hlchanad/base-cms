const MyDatatable = function(dataTableService, tableId, tableName, ajaxUrl, columns, extraConfigs) {

    this.dataTableService = dataTableService;

    this.tableName = tableName;
    this.tableId = tableId;
    this.ajaxUrl = ajaxUrl;
    this.columns = columns;
    this.extraConfigs = extraConfigs;

    this.dataTable = null;

    this.init = function() {
        this.initDataTable();
        this.setupDataTableActionButton();
        this.setupDataTableFilter();
    };

    this.setupDataTableFilter = function() {
        let _this = this;
        $("#" + this.tableId + "_search_box").keyup(function() {
            _this.dataTable.search($(this).val()).draw();
        })
    };

    this.setupDataTableActionButton = function() {
        let _this = this;

        $("table#" + this.tableId).on("click", "a.action-delete", function(event) {
            event.preventDefault();

            let href = $(this).attr("data-href");
            _this.dataTableService.ajaxCallWithConfirm(_this, _this.tableName, "delete", true, href, "DELETE");
        });
    };

    this.initDataTable = function() {
        let _this = this;

        this.dataTable = $("table#" + this.tableId).DataTable({
            ajax: {
                url: _this.ajaxUrl,
                data: function(data) {
                    Object.assign(data, _this.extraConfigs);
                    return data;
                }
            },
            type: "POST",
            processing: true,
            serverSide: true,
            columns: _this.columns,
            dom: 'rtip'
        });
    };

    this.refresh =  function() {
        this.dataTable.ajax.reload();
    };

    this.updateExtraConfigs = function(props) {
        this.extraConfigs = {
            ...this.extraConfigs,
            ...props
        };
        this.refresh();
    }
};
