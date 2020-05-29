var planeTable;

$(document).ready(function () {
    planeTable = $("#airplaneTable").DataTable({
        ajax: {
            url: 'api/airplanes',
            dataSrc: ''
        },
        columns: [
            { data: 'id' },
            { data: 'name' },
            { data: 'tonsOfFuel' },
            { data: 'airport.name' },
            {
                data: null, render: data =>
                    '<td><button planeId="' + data.id + '" class="refuel">Refuel</button><button planeId="' + data.id + '" class="delete">Delete</button></td>'
            },
        ],
        select: {
            style: 'os',
            selector: 'td:first-child'
        },
    });

    $("#savePlane").click(submitAirplaneForm);
    $('#airplaneTable').on('click', 'button.refuel', onRefuelPlaneClick);
    $('#airplaneTable').on('click', 'button.delete', onDeletePlaneClick);
    // Load airport data
    fillAirportSelect();
});

function fillAirportSelect() {
    $.get('api/airports', function (airports) {
        $.each(airports, function (_index, airport) {
            $('#airportInput').append(`<option value="${airport.id}">${airport.name}</option>`);
        });
    });
}

function reloadPlanes() {
    planeTable.ajax.reload();
}

function submitAirplaneForm() {
    var airplane = {
        name: $('#nameInput').val(),
        airport: {
            id: $('#airportInput').val()
        }
    };
    savePlane(airplane);
}

function savePlane(plane) {
    $.ajax({
        url: 'api/airplanes',
        type: 'POST',
        data: JSON.stringify(plane),
        contentType: 'application/json',
        success: reloadPlanes,
        error: showError
    });
}

function onRefuelPlaneClick() {
    refuelPlane($(this).attr('planeId'));
}

function refuelPlane(id) {
    $.ajax({
        url: 'api/airplanes/refuel/' + id,
        type: 'PUT',
        success: reloadPlanes,
        error: showError
    });
}

function onDeletePlaneClick() {
    deletePlane($(this).attr('planeId'));
}

function deletePlane(id) {
    $.ajax({
        url: 'api/airplanes/' + id,
        type: 'DELETE',
        success: reloadPlanes,
        error: showError
    });
}

function showError() {
    alert('something went wrong');
}