$(document).ready(function () {
    let cropper;

    $('#imageModal').on('shown.bs.modal', function () {
        let image = document.getElementById('image');
        cropper = new Cropper(image, {
            aspectRatio: 4 / 4
        });
    });

    $('#cropButton').click(function () {
        let croppedImageData = cropper.getCroppedCanvas().toDataURL('image/jpeg');
        $('#croppedImageData').val(croppedImageData.split(",")[1]); // Prendre la deuxième partie de la chaîne Base64
        $('#imageModal').modal('hide');
    });

    $('#image-input').change(function (event) {
        let files = event.target.files;
        let reader = new FileReader();
        console.log(files)

        reader.onload = function (event) {
            $('#image').attr('src', event.target.result);
            $('#imageModal').modal('show');
        };

        reader.readAsDataURL(files[0]);
    });
});