const MyPagination = function() {

    this.createDataContainer = function(paginationContainer) {
        $('<div class="my-pagination-data-container"></div>').insertBefore(paginationContainer);
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

                    if (config.beforeSend) {
                        config.beforeSend();
                        return ;
                    }

                    dataContainer.html('<div style="text-align: center;">Loading ...</div>');
                },
                data: config.data
            },
            callback: function(data, pagination) {

                if (config.handler) {
                    config.handler(data, pagination);
                    return ;
                }

                if (config.beforeVendorHandler) {
                    config.beforeVendorHandler();
                }

                let html = "";

                data.forEach(function(datum) {
                    html += config.datumVendorHandler(datum);
                });

                dataContainer.html(html);

                if (config.afterVendorHandler) {
                    config.afterVendorHandler();
                }
            },
            ...config.extra
        });
    };
};