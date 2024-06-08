document.addEventListener('DOMContentLoaded', (event) => {
    findItem();
});

function showItemSell(inventoryCell){
    const divToShow = document.getElementById("sellItemPanel");
    divToShow.style.visibility = 'visible';
    const inventoryId = inventoryCell.id
        $.ajax({
            type: 'GET',
            url: '/inventory/' + inventoryId,
            success: function(response) {
                $('#sellItemPanel').html(response);
            },
            error: function(xhr, status, error) {
                console.error('Error: ', error);
            }
        });
}

function findItem(){
    var str = $("#formToFindItem").serialize();
    $.ajax({
        type: 'POST',
        url: 'inventory/findItem',
        data: str,
        success: function(response) {
            $('#inventoryList').html(response);
        },
        error: function(xhr, status, error) {
            console.error('Error: ', error);
        }
    });
}

function cancel(){
    const divToHide = document.getElementById("sellItemPanel");
    divToHide.style.visibility = 'hidden';
}

$(document).ready(function () {
    $('#findItemButton').click(function () {
        findItem();
    });
});