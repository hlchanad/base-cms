const MyPagination = function() {

    this.createDataContainer = function(paginationContainer) {
        $("<div class='my-pagination-data-container'></div>").insertBefore(paginationContainer);
        return paginationContainer.prev();
    };

    this.getDataContainer = function(paginationContainer, dataContainerSelector) {
        return dataContainerSelector ? $(dataContainerSelector) : this.createDataContainer(paginationContainer);
    };

    this.paginate = function(config) {
        const paginationContainer = $(config.paginationContainerSelector);

        const dataContainer = this.getDataContainer(paginationContainer, config.dataContainerSelector);

        paginationContainer.pagination({
            dataSource: config.url,
            locator: config.dataLocator,
            totalNumberLocator: function(response) {
                let props = config.totalLocator.split(".");
                let result = response;
                for (let i = 0; i < props.length; i++) {
                    result = result[props[i]];
                }
                return result;
            },
            alias: {
                pageNumber: "page",
                pageSize: "limit"
            },
            prevText: "<i class=\"pg-arrow_left\"></i>",
            nextText: "<i class=\"pg-arrow_right\"></i>",
            ajax: {
                beforeSend: function() {
                    dataContainer.html('Loading ...');
                }
            },
            callback: function(data, pagination) {
                let html = "";

                data.forEach(function(datum) {
                    html += config.handler(datum);
                });

                dataContainer.html(html);
            },
            ...config.extra
        });
    };
};