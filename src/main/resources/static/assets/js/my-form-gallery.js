const MyFormGallery = function () {

    this.init = function () {
        this.setOnClickListenerOnSelectButton();
    };

    this.setOnClickListenerOnSelectButton = function () {
        $("body").on("click", ".select-image-button", function () {
            console.log("helloworld");
        });
    }
};

const myFormGallery = new MyFormGallery();
myFormGallery.init();