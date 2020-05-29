$(document).ready(function () {
    $("#flyPlane").click(flyPlane);
    reloadPlanes();
    fillAirportSelect();
});

function flyPlane() {
    $.ajax({
        url: 'api/airplanes/move/' + $("#airplaneSelect").val() + '/' + $("#destinationSelect").val(),
        type: 'PUT',
        success: reloadPlanes,
        error: function (error) {
            console.log(error);
            showError(error && error.responseJSON ? error.responseJSON.message : 'Something went wrong. Please try again!');
        }
    });
}

function reloadPlanes() {
    $.get('api/airplanes', airplanes => {
        if (!airplanes || !airplanes.length) {
            showErrorHtml('We don\'t have an airplane yet. Create one <a href="airplanes.html">here</a> first!');
        }
        $('#airplaneSelect').empty();
        $.each(airplanes, (_, plane) => $('#airplaneSelect').append('<option value="' + plane.id + '">' + plane.name + ' - ' + plane.airport.name + '</option>'));
    });
}

function showError(message) {
    showErrorHtml(message);
}

function showErrorHtml(html) {
    $('#errorModal .modal-body p').html(html);
    $('#errorModal').modal('show');
}

function fillAirportSelect() {
    $.get('api/airports', function (airports) {
        $.each(airports, function (_index, airport) {
            $('#destinationSelect').append(`<option value="${airport.id}">${airport.name}</option>`);
        });
    });
}