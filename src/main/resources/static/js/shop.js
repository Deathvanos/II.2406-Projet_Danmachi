document.addEventListener('DOMContentLoaded', (event) => {
    findItem();
});

function showItemBuy(shopCell){
    const divToShow = document.getElementById("buyItemPanel");
    divToShow.style.visibility = 'visible';
    const shopId = shopCell.id
        $.ajax({
            type: 'GET',
            url: '/shop/' + shopId,
            success: function(response) {
                $('#buyItemPanel').html(response);
            },
            error: function(xhr, status, error) {
                console.error('Error: ', error);
            }
        });
}

function findItem(){
    var name = $('#name').val();
    var minPrice = $('#minPrice').val();
    var maxPrice = $('#maxPrice').val();
    var elementCategory = document.getElementsByTagName('input');
    var category = null;
    for (i = 0; i < elementCategory.length ; i++) {
        if (elementCategory[i].type === "radio") {
            if (elementCategory[i].checked){
                category = elementCategory[i].value;
            }
        }
    }
    console.log(category);
    var formData = new FormData();

    formData.append("name", name);
    formData.append("category", category);
    formData.append("minPrice", minPrice);
    formData.append("maxPrice", maxPrice);
    $.ajax({
        type: 'POST',
        url: 'shop/findItem',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            $('#shopList').html(response);
        },
        error: function(xhr, status, error) {
            console.error('Error: ', error);
        }
    });
}

function cancel(){
    const divToHide = document.getElementById("buyItemPanel");
    divToHide.style.visibility = 'hidden';
}

function updateCost(price){
    var quantity = document.getElementById("quantity").value;
    console.log("price = " + price);
    console.log("Quantity = " + quantity);
    var newPrice = quantity * price
    console.log("newPrice = " + newPrice);
    document.getElementById("price").setAttribute("value", newPrice);
    }

function cantBuyAlert(){
    alert("Vous ne pouvez pas acherter cet objet il coûte trop cher pour vous.");
}

$(document).ready(function () {
    $('#findItemButton').click(function () {
        findItem();
    });
});