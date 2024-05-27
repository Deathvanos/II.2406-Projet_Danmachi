$(document).ready(function() {
    $('#personnages-table').DataTable({
        "paging": false,
        "searching": false,
        "language": {
            "info": ""
        }
    });
});

document.getElementById('race-filter').addEventListener('change', function() {
    const selectedRace = this.value;
    const tableRows = document.querySelectorAll('#personnages-table tbody tr');

    tableRows.forEach(function(row) {
        const raceCell = row.querySelector('td:nth-child(4)').textContent;

        const shouldBeVisible = (selectedRace === '' || raceCell === selectedRace);

        const rowOffset = getRowIndex(row);

        row.style.display = shouldBeVisible ? '' : 'none';
    });
});

function getRowIndex(row) {
    const rows = Array.from(row.parentNode.children);
    return rows.indexOf(row);
}
