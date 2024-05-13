$(document).ready(function () {
    let cropper;

    $('#imageModal').on('shown.bs.modal', function () {
        let image = document.getElementById('image');
        cropper = new Cropper(image, {
            aspectRatio: 3 / 4
        });
    });

    $('#cropButton').click(function () {
        let croppedImageData = cropper.getCroppedCanvas().toDataURL('image/jpeg');
        $('#croppedImageData').val(croppedImageData);
        $('#imageModal').modal('hide');
    });

    $('#image-input').change(function (event) {
        let files = event.target.files;
        let reader = new FileReader();

        reader.onload = function (event) {
            $('#image').attr('src', event.target.result);
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
        let playerId = button.id;
        sessionStorage.setItem("personnageId", playerId);

        $.ajax({
            type: 'GET',
            url: '/personnage/' + playerId,
            success: function(response) {
                $('#info-nom').text(response.lastName);
                $('#info-prenom').text(response.firstName);
                $('#info-race').text(response.raceString);
                $('#info-money').text(response.money);
                $('#info-level').text(response.level);
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

$(document).ready(function() {
    $("#deleteForm").submit(function(event) {
        event.preventDefault();
        let personnageId = $("#personnageId").val();

        $.ajax({
            url: "/personnage/" + personnageId,
            type: "DELETE",
            success: function(response) {
                alert("Personnage supprimé avec succès!");
                location.reload();
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
                alert("Erreur lors de la suppression du personnage.");
            }
        });
    });
});

document.getElementById("modify-user-info-form").addEventListener("submit", function(event) {
    event.preventDefault();

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
            aspectRatio: 1,
            viewMode: 1
        });
    };

    reader.readAsDataURL(file);
});

function validateForm() {
    let fileInput = document.getElementById('image-input');
    let file = fileInput.files[0];

    if (!file) {
        alert("Veuillez sélectionner une image.");
        return false;
    }
    return true;
}