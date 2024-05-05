let playerId;

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
                $('#info-nom').text(response.lastName);
                $('#info-prenom').text(response.firstName);
                $('#info-race').text(response.race);
                $('#info-money').text(response.money);
                $('#info-level').text(response.level);
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

function showBootstrapAlert(type, message) {
    const alertElement = $('<div class="alert alert-' + type + ' alert-dismissible fade show" role="alert">' +
        message +
        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
        '</div>');

    $('#alertContainer').append(alertElement);
}
