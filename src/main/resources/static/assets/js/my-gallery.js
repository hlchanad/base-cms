new (function() {

    this.init = function() {
        new MyPagination().paginate({
            url: "image",
            paginationContainerSelector: "div#image-gallery-pagination",
            dataContainerSelector: "div#image-gallery",
            dataLocator: "data.images",
            totalLocator: "data.total",
            handler: this.createImageItem
        });
    };

    this.createImageItem = function(image){
        return `
<div>
     <img src="${image.url}"  alt="image"/>
</div>
                        `;
    };
})().init();