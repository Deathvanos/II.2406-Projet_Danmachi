function showItemSell(inventoryCell, quantity){
    const divToShow = document.getElementById("sellItemPanel");
    divToShow.style.visibility = 'visible';
    const inventoryId = inventoryCell.id
        $.ajax({
            type: 'GET',
            url: '/inventory/' + inventoryId,
            success: function(response) {

                $('#item-name').text(item.name);
                $('#item-image').text(item.urlImage);
                $('#quantity').attr('max', quantity);

                $('#itemId').attr('value', item.id);
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