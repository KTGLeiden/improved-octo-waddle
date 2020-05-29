var airportTable;

$(document).ready(function () {
    airportTable = $("#airportTable").DataTable({
        ajax: {
            url: 'api/airports',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'name' },
            {
                data: null, render: data =>
                    '<td><button airportId="' + data.id + '" class="delete">Delete</button></td>'
            },
        ],
        select: {
            style: 'os',
            selector: 'td:first-child'
        },
    });

    $("#saveAirport").click(submitAirportForm);
    $('#airportTable').on('click', 'button.delete', onDeleteAirportClick);
});


function reloadAirports() {
    airportTable.ajax.reload();
}

function submitAirportForm() {
    var airport = {
        name: $('#nameInput').val()
    };
    saveAirport(airport);
}

function saveAirport(airport) {
    $.ajax({
        url: 'api/airports',
        type: 'POST',
        data: JSON.stringify(airport),
        contentType: 'application/json',
        success: reloadAirports,
        error: showError
    });
}

function onDeleteAirportClick() {
    deleteAirport($(this).attr('airportId'));
}

function deleteAirport(id) {
    $.ajax({
        url: 'api/airports/' + id,
        type: 'DELETE',
        success: reloadAirports,
        error: showError
    });
}

function showError() {
    alert('something went wrong');
}