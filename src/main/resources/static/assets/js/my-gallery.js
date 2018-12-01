new (function() {

    this.init = function() {
        const _this = this;

        new MyPagination().paginate({
            url: "image",
            dataContainerSelector: ".gallery",
            paginationContainerSelector: ".gallery-pagination",
            dataLocator: "data.images",
            totalLocator: "data.total",
            handler: function(data, pagination) {

                _this.destroyIsotopeOnGrid();

                let imageGrid = "";
                data.forEach(function(datum) {
                    imageGrid += _this.createImageItem(datum);
                });
                $(".gallery").html(imageGrid);

                let imageDetails = "";
                data.forEach(function(datum) {
                    imageDetails += _this.createImageDetail(datum);
                });
                $(".gallery-item-details").html(imageDetails);

                _this.applyIsotopeOnGrid();
                _this.makeItemDetailImageUseCssBg();

            },
            extra: {
                pageSize: 8
            }
        });

        this.setOnClickListenerOnImageItem();
        this.setOnClickListenerOnImageItemDeleteButton();
    };

    this.applyIsotopeOnGrid = function () {

        const gallery = $(".gallery");

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

    this.destroyIsotopeOnGrid = function() {
        let gallery = $(".gallery");
        if (gallery.data("isotope")) {
            gallery.isotope("destroy");
        }
    };

    this.setOnClickListenerOnImageItem = function () {
        $("body").on("click", ".gallery-item", function() {

            const fileName = $(this).data("filename");
            const itemDetail = $(`#item-detail-${fileName}`);

            const dialog = new DialogFx(itemDetail.get(0));
            dialog.toggle();
        });
    };

    this.setOnClickListenerOnImageItemDeleteButton = function () {
        $("body").on("click", ".delete-image", function() {
            const fileName = $(this).data("filename");
            console.log("TODO: delete image ", fileName);
        });
    };

    this.makeItemDetailImageUseCssBg = function () {
        $('.item-slideshow > div').each(function() {
            const imageUrl = $(this).data('image');
            $(this).css({
                'background-image': 'url(' + imageUrl + ')'
            })
        });
    };

    this.getFileSize = function (fileSize) {

        const fileSizeUnit = [ "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" ];

        let fileSizeLeft = fileSize;

        for (let i = 0; i < fileSizeUnit.length; i++) {
            if (fileSizeLeft / 1024 < 1) {
                return fileSizeLeft.toFixed(2) + " " + fileSizeUnit[i];
            }

            fileSizeLeft = fileSizeLeft / 1024;
        }

        return fileSize + " Bytes";
    };

    this.createImageItem = function (image) {

        const fileSize = this.getFileSize(image.fileSize);

        return `
            <div id="image-item-${image.fileName}" class="gallery-item" 
                data-filename="${image.fileName}" data-width="1" data-height="1">
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
                                    <p class="no-margin text-white fs-12">${image.width} &times; ${image.height}, ${fileSize}</p>
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

    this.createImageDetail = function (image) {

        const fileSize = this.getFileSize(image.fileSize);

        return `
            <div id="item-detail-${image.fileName}" class="dialog item-details" data-filename="${image.fileName}">
                <div class="dialog__overlay"></div>
                <div class="dialog__content">
                    <div class="container-fluid">
                        <div class="row dialog__overview">
                            <div class="col-md-7 no-padding item-slideshow-wrapper full-height">
                                <div class="item-slideshow full-height">
                                    <div class="slide" data-image="${image.url}"></div>
                                </div>
                            </div>
                            <div class="col-md-5 p-r-35 p-t-35 p-l-35 full-height item-description">
                                <h2 class="semi-bold font-montserrat m-t-0 m-b-20">${image.originalFileName}</h2>
                                <!--<p class="fs-13">-->
                                    <!--Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sed aliquam nunc. -->
                                    <!--Nulla convallis massa eget imperdiet aliquam. Proin eu ultricies justo. Nam id leo -->
                                    <!--vel quam bibendum ornare id et mi.-->
                                <!--</p>-->
                                <div class="row">
                                    <div class="col-md-5">Dimension :</div>
                                    <div class="col-md-7">${image.width} &times; ${image.height}</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">File Size :</div>
                                    <div class="col-md-7">${fileSize}</div>
                                </div>
                                <button class="btn btn-danger delete-image" data-filename="${image.fileName}">Delete</button>
                            </div>
                        </div>
                    </div>
                    <button class="close action top-right" data-dialog-close><i class="pg-close fs-14"></i>
                    </button>
                </div>
            </div>
        `;
    }

})().init();