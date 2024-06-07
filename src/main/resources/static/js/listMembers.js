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

        row.style.display = shouldBeVisible ? '' : 'none';
    });
});
