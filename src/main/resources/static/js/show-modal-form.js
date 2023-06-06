$(document).ready(function () {
    $('#deleteModal').on('show.bs.modal', function (event) {
        let buttonClicked = $(event.relatedTarget);
        let $userData = $(buttonClicked).closest('tr').children('td').slice(0, 6);
        $('#idModalDelete').val($userData[0].innerHTML);
        $('#firstNameModalDelete').val($userData[1].innerHTML);
        $('#surnameModalDelete').val($userData[2].innerHTML);
        $('#ageModalDelete').val($userData[3].innerHTML);
        $('#loginModalDelete').val($userData[4].innerHTML);
        if (($userData[5].innerHTML).includes('ADMIN')) {
            $('#selectedRoleDelete option[value="one"]').prop("selected", true);
        }
        if (($userData[5].innerHTML).includes('USER')) {
            $('#selectedRoleDelete option[value="two"]').prop("selected", true);
        }
        let urlForDeleteId = '/admin/' + $('#idModalDelete').val();
        $('#modalDelete').attr('action', urlForDeleteId);
    });

    $('#deleteModal').on('hide.bs.modal', function (event) {
        $('#idModalDelete').val('');
        $('#firstNameModalDelete').val('');
        $('#surnameModalDelete').val('');
        $('#ageModalDelete').val('');
        $('#loginModalDelete').val('');
        if ($('#selectedRoleDelete option[value="one"]').prop("selected") === true) {
            $('#selectedRoleDelete option[value="one"]').prop("selected", false);
        }
        if ($('#selectedRoleDelete option[value="two"]').prop("selected") === true) {
            $('#selectedRoleDelete option[value="two"]').prop("selected", false);
        }
    });


    $('#editModal').on('show.bs.modal', function (event) {
        let buttonClicked = $(event.relatedTarget);
        let $userData = $(buttonClicked).closest('tr').children('td').slice(0, 6);
        $('#idModalEdit').val($userData[0].innerHTML);
        $('#firstNameModalEdit').val($userData[1].innerHTML);
        $('#surnameModalEdit').val($userData[2].innerHTML);
        $('#ageModalEdit').val($userData[3].innerHTML);
        $('#loginModalEdit').val($userData[4].innerHTML);
        if (($userData[5].innerHTML).includes('ADMIN')) {
            $('#selectedRoleEdit option[value="1"]').prop("selected", true);
        }
        if (($userData[5].innerHTML).includes('USER')) {
            $('#selectedRoleEdit option[value="2"]').prop("selected", true);
        }
        let urlForEditId = '/admin/' + $('#idModalEdit').val();
        $('#modalEdit').attr('action', urlForEditId);
    });

    $('#editModal').on('hide.bs.modal', function (event) {
        $('#idModalEdit').val('');
        $('#firstNameModalEdit').val('');
        $('#surnameModalEdit').val('');
        $('#ageModalEdit').val('');
        $('#loginModalEdit').val('');
        if ($('#selectedRoleEdit option[value="1"]').prop("selected") === true) {
            $('#selectedRoleEdit option[value="1"]').prop("selected", false);
        }
        if ($('#selectedRoleEdit option[value="2"]').prop("selected") === true) {
            $('#selectedRoleEdit option[value="2"]').prop("selected", false);
        }
    });

});




