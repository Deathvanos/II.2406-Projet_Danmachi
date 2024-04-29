function showDiv(divId) {
    var divsToHide = document.querySelectorAll('.div-info');
    Array.prototype.forEach.call(divsToHide, function(div) {
        div.style.display = 'none';
    });
    var divToShow = document.getElementById(divId);
    divToShow.style.display = 'block';
}

function clearSelection() {
    document.getElementById('race-select').selectedIndex = 0;
}
