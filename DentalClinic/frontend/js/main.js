const HOST = 'http://localhost:8001/back?';

const KEY_FULL_NAME = 'fullName';
const KEY_REGISTRATION_CARD_NUMBER = 'registrationCardNumber';
const KEY_TYPE_OF_WORK = 'typeOfWork';
const KEY_PAID = 'paid';
const KEY_DEBT_SUM = 'debtSum';

let JsonArr = [];

fetch(HOST + "show", {
    method: 'POST',
}).then(response =>
    response.json().then(data => ({
        data: data,
    })).then(res => {
        try {
            JsonArr = res.data;
            render(JsonArr);
        } catch {
            JsonArr = [];
        }
    }));

let $content_table = document.querySelector('.content-table')

function render(array) {
    $content_table.innerHTML = '<thead class="align-top">\n' +
        '<tr>\n' +

        '<th scope="col">ФИО пациента</th>\n' +
        '<th scope="col">Номер учетной карточки</th>\n' +
        '<th scope="col">Тип работы</th>\n' +
        '<th scope="col">Отметка об оплате</th>\n' +
        '<th scope="col">Сумма задолженности</th>\n' +

        '</tr>\n' +
        '</thead>';
    for (let i = 0; i < array.length; i++) {
        $content_table.innerHTML += '<tbody><tr><td>' +

            array[i][KEY_FULL_NAME] + '</td><td>' +
            array[i][KEY_REGISTRATION_CARD_NUMBER] + '</td><td>' +
            array[i][KEY_TYPE_OF_WORK] + '</td><td>' +
            array[i][KEY_PAID] + '</td><td>' +
            array[i][KEY_DEBT_SUM] +

            '</td></tr></tbody>';
    }
}

function filterShowIndebted() {
    let result = [];
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_PAID] === false && JsonArr[i][KEY_DEBT_SUM] > 0) {
            result.push(JsonArr[i]);
        }
    }
    return result;
}

document.querySelector('#show-indebted').addEventListener('change', function () {
    if (this.checked) {
        render(filterShowIndebted());
    }
});
document.querySelector('#show-all').addEventListener('change', function () {
    if (this.checked) {
        render(JsonArr);
    }
});

let $searchFullName = document.querySelector('#live-filter-full-name');
$searchFullName.addEventListener('input', function () {
    let result = [];
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_FULL_NAME].toLowerCase().includes($searchFullName.value.toLowerCase())) {
            result.push(JsonArr[i]);
        }
    }
    render(result);
})

document.querySelector('#modal-add-send').addEventListener('click', function () {

    let paidRadioButtons = document.getElementsByName('paid');
    let paid = false;
    if (paidRadioButtons[0].checked)
        paid = true;

    let result = {
        fullName: document.querySelector('#modal-input-full-name').value,
        registrationCardNumber: Number(document.querySelector('#modal-input-registration-card-number').value),
        typeOfWork: document.querySelector('#modal-input-type-of-work').value,
        paid: paid,
        debtSum: Number(document.querySelector('#modal-input-debt-sum').value),
    };

    fetch(HOST + "add", {
        method: 'POST',
        body: JSON.stringify(result),
    }).then(response =>
        response.json().then(data => ({
            data: data,
        })).then(res => {
            if (res.data["added_successfully"] === true) {

                JsonArr.push(result);
                render(JsonArr);
                alert("Пациент успешно добавлен");

                const addModalWrapper = document.querySelector('#add-modal');
                const addModal = bootstrap.Modal.getInstance(addModalWrapper);
                addModal.hide();

                document.querySelector('#modal-input-full-name').value = "";
                document.querySelector('#modal-input-registration-card-number').value = "";
                document.querySelector('#modal-input-type-of-work').value = "";
                document.querySelector('#modal-input-debt-sum').value = "";

            } else {
                alert("Введены неправильные данные")
            }
        }));
})
document.querySelector('#delete-paid').addEventListener('click', function () {
    let result = [];
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_PAID] === false) {
            result.push(JsonArr[i]);
        }
    }

    fetch(HOST + "delete", {
        method: 'POST',
        body: JSON.stringify(result),
    }).then(response =>
        response.json().then(data => ({
            data: data,
            status: response.status
        })).then(res => {
            if (res.data["deleted_successfully"] === true) {
                JsonArr = result;
                render(JsonArr);
                alert("Пациенты оплатившие лечение удалены");
            } else {
                alert("При удалении произошла ошибка")
            }
        }));
})

let editId = -1;
document.querySelector('#edit-element-button').addEventListener('click', function () {
    let patientFound = false;
    let patientEditRegistrationCardNumber = Number(document.querySelector('#edit-element').value);
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_REGISTRATION_CARD_NUMBER] === patientEditRegistrationCardNumber) {

            let editModalWrapper = document.querySelector('#edit-modal');
            let editModal = new bootstrap.Modal(editModalWrapper);
            editModal.show();

            editId = i;
            patientFound = true;

            document.querySelector('#modal-input-full-name-edit').value = JsonArr[i][KEY_FULL_NAME];
            document.querySelector('#modal-input-registration-card-number-edit').value = JsonArr[i][KEY_REGISTRATION_CARD_NUMBER];
            document.querySelector('#modal-input-type-of-work-edit').value = JsonArr[i][KEY_TYPE_OF_WORK];
            document.querySelector('#modal-input-debt-sum-edit').value = JsonArr[i][KEY_DEBT_SUM];

            break;
        }
    }

    if (patientFound === false) {
        alert("Пациент с таким номером карточки не найден");
    }
})

document.querySelector('#modal-edit-send').addEventListener('click', function () {
    let paidRadioButtons = document.getElementsByName('paid-edit');
    let paid = false;
    if (paidRadioButtons[0].checked)
        paid = true;

    let result = {
        fullName: document.querySelector('#modal-input-full-name-edit').value,
        registrationCardNumber: Number(document.querySelector('#modal-input-registration-card-number-edit').value),
        typeOfWork: document.querySelector('#modal-input-type-of-work-edit').value,
        paid: paid,
        debtSum: Number(document.querySelector('#modal-input-debt-sum-edit').value),
    };

    let editData = {
        id: editId,
        patient: result
    }

    console.log(editData);

    fetch(HOST + "edit", {
        method: 'POST',
        body: JSON.stringify(editData),
    }).then(response =>
        response.json().then(data => ({
            data: data,
        })).then(res => {
            if (JSON.stringify(res.data) === '{"edited_successfully":true}') {

                JsonArr[editId] = result;
                render(JsonArr);

                alert("Успешно отредактировано");

                const editModalWrapper = document.querySelector('#edit-modal');
                const editModal = bootstrap.Modal.getInstance(editModalWrapper);
                editModal.hide();

                document.querySelector('#modal-input-full-name').value = "";
                document.querySelector('#modal-input-registration-card-number').value = "";
                document.querySelector('#modal-input-type-of-work').value = "";
                document.querySelector('#modal-input-debt-sum').value = "";
            } else {
                alert("Введены неправильные данные при редактировании")
            }
        }));

})