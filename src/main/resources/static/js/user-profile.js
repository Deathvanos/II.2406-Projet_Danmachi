let playerId;

$(document).ready(function () {
    let cropper;

    // Initialize Cropper.js when modal is shown
    $('#imageModal').on('shown.bs.modal', function () {
        var image = document.getElementById('image');
        cropper = new Cropper(image, {
            aspectRatio: 3 / 4,
            crop: function (e) {
                // Output the crop coordinates if needed
                console.log(e.detail.x);
                console.log(e.detail.y);
                console.log(e.detail.width);
                console.log(e.detail.height);
            },
        });
    });

    // Crop image when crop button is clicked
    $('#cropButton').click(function () {
        // Get cropped image data
        var croppedImageData = cropper.getCroppedCanvas().toDataURL('image/jpeg');
        // Set cropped image data to hidden input field
        $('#croppedImageData').val(croppedImageData);
        // Close modal
        $('#imageModal').modal('hide');
    });

    // Open modal when file input changes
    $('#image-input').change(function (event) {
        var files = event.target.files;
        var reader = new FileReader();

        reader.onload = function (event) {
            $('#image').attr('src', event.target.result);
            // Show modal
            $('#imageModal').modal('show');
        };

        reader.readAsDataURL(files[0]);
    });
});


function showDiv(divId, button) {
    const divsToHide = document.querySelectorAll('.div-info');
    Array.prototype.forEach.call(divsToHide, function(div) {
        div.style.display = 'none';
    });

    const divToShow = document.getElementById(divId);
    divToShow.style.display = 'block';

    if (divId === 'player-information') {
        playerId = button.id;

        $.ajax({
            type: 'GET',
            url: '/personnage/' + playerId,
            success: function(response) {
                $('#info-nom').text(response.personnage.lastName);
                $('#info-prenom').text(response.personnage.firstName);
                $('#info-race').text(response.raceString);
                $('#info-money').text(response.personnage.money);
                $('#info-level').text(response.personnage.level);
                $('#button-race').text('Fiche '+response.raceString);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }
}


function clearSelection() {
    document.getElementById('race-select').selectedIndex = 0;
}

function deletePersonnage() {
    $.ajax({
        type: 'DELETE',
        url: '/personnage/' + playerId,
        success: function(response) {
            location.reload();
            showBootstrapAlert("success", response);
        },
        error: function(xhr, status) {
            showBootstrapAlert("danger", "Erreur "+ status +": Veuillez réessayer plus tard.")
        }
    });
}

document.getElementById("modify-user-info-form").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent default form submission

    const email = document.getElementById("email").value;
    const emailError = document.getElementById("emailError");

    const username = document.getElementById('username').value;
    const usernameError = document.getElementById('usernameError');

    emailError.textContent = "";
    usernameError.textContent = "";

    // Check email uniqueness
    $.ajax({
        url: "/checkUnique",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({"email": email, "username": username}),
        success: function(response) {
            let uniqueError = false;
            if (response.existingEmail) {
                emailError.textContent = "Cette adresse mail est déjà utilisée";
                uniqueError = true;
            }

            if (response.existingUsername) {
                usernameError.textContent = "Ce pseudonym est déjà utilisé";
                uniqueError = true;
            }

            if (uniqueError) {return;}

            document.getElementById("signup-form").submit();
        },
        error: function(xhr, status, error) {
            console.error("Error checking uniqueness:", error);
            // Handle error if needed
        }
    });

});

function showBootstrapAlert(type, message) {
    const alertElement = $('<div class="alert alert-' + type + ' alert-dismissible fade show" role="alert">' +
        message +
        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
        '</div>');

    $('#alertContainer').append(alertElement);
}

document.getElementById('image-input').addEventListener('change', function (e) {
    const file = e.target.files[0];
    const reader = new FileReader();
    const imagePreview = document.getElementById('image-preview');
    let cropper;

    reader.onload = function (event) {
        const imageUrl = event.target.result;
        imagePreview.src = imageUrl;

        cropper = new Cropper(imagePreview, {
            aspectRatio: 1, // Set aspect ratio as needed
            viewMode: 1, // Set view mode as needed
        });
    };

    reader.readAsDataURL(file);
});