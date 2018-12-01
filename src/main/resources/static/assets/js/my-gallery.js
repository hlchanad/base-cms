new (function() {

    this.init = function() {
        new MyPagination().paginate({
            url: "image",
            paginationContainerSelector: ".gallery-pagination",
            dataContainerSelector: ".gallery",
            dataLocator: "data.images",
            totalLocator: "data.total",
            datumVendorHandler: this.createImageItem,
            beforeVendorHandler: this.destroyIsotope,
            afterVendorHandler: this.applyIsotope,
            extra: {
                pageSize: 8
            }
        });

        this.applyIsotope();
    };

    this.applyIsotope = function() {

        let gallery = $(".gallery");

        gallery.imagesLoaded(function() {
            gallery.isotope({
                itemSelector: '.gallery-item',
                masonry: {
                    columnWidth: 280,
                    gutter: 10,
                    isFitWidth: true
                }
            });
        });
    };

    this.destroyIsotope = function() {
        let gallery = $(".gallery");
        if (gallery.data("isotope")) {
            gallery.isotope("destroy");
        }
    };

    this.createImageItem = function(image){

        console.log("image: ", image);



        return `
<div class="gallery-item" data-width="1" data-height="1">
    <!-- START PREVIEW -->
    <img src="${image.url}" alt="" class="image-responsive-height">
    <!-- END PREVIEW -->
    <!-- START ITEM OVERLAY DESCRIPTION -->
    <div class="overlayer bottom-left full-width">
        <div class="overlayer-wrapper item-info ">
            <div class="gradient-grey p-l-20 p-r-20 p-t-20 p-b-5">
                <div class="">
                    <p class="pull-left bold text-white fs-14 p-t-10">${image.originalFileName}</p>
                    <div class="clearfix"></div>
                </div>
                <div class="m-t-10">
                    <div class="inline">
                        <p class="no-margin text-white fs-12">${image.width} &times; ${image.height}, ${image.fileSize} Bytes</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- END PRODUCT OVERLAY DESCRIPTION -->
</div>
                        `;
    };
})().init();