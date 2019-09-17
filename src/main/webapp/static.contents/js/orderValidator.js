let firstNameChecked = false;
let zoneChecked = false;
let phoneChecked = false;
let emailChecked = false;

let peek = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_\s></%&*]+/;
let letter = /[a-zA-Z]+/;
let emailRegex = /\S+@\S+\\.\S+/;
let phoneRegex = /\((17|29|33|44)\)[0-9]{8}/;
let digit = /[0-9]+/;


let maxZoneLength = 2;
let notFoundIndex = -1;

let firstName = document.getElementById("first_name");
let phone = document.getElementById("phone");
let email = document.getElementById("email");
let zone = document.getElementById("zone");

let submit = document.getElementById("submit");

let submitChange = function () {
    if (firstNameChecked && zoneChecked && phoneChecked && emailChecked) {
        submit.disabled = false;
        submit.classList.add("active");
    } else {
        submit.disabled = true;
        submit.classList.remove("active");
    }
};

let setBackgroundFieldGreen = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
};

let setBackgroundFieldRed = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
};

let checkName = function () {
    if (notValidField(firstName)) {
        setBackgroundFieldRed(firstName);
        firstNameChecked = false;
    } else {
        setBackgroundFieldGreen(firstName);
        firstNameChecked = true;
    }
    submitChange();
};

let checkEmail = function () {
    if (email.value.search(emailRegex) > notFoundIndex ||
        email.value.length <= 5) {
        setBackgroundFieldRed(email);
        emailChecked = false;
    } else {
        setBackgroundFieldGreen(email);
        emailChecked = true;
    }
    submitChange();
};

function checkZOne() {
    if (zone.value.search(peek) > notFoundIndex ||
        zone.value.search(letter) > notFoundIndex ||
        zone.value.length > maxZoneLength ||
        zone.value > 22 ) {
        setBackgroundFieldRed(zone);
        zoneChecked = false;
    } else {
        setBackgroundFieldGreen(zone);
        zoneChecked = true;
    }
    submitChange();
}

function checkPhone() {
    if (phone.value.search(phoneRegex) > notFoundIndex ||
        phone.value.length < 8) {
        setBackgroundFieldRed(phone);
        phoneChecked = false;
    } else {
        setBackgroundFieldGreen(phone);
        phoneChecked = true;
    }
    submitChange();
}


function notValidField(name) {
    return (name.value.search(peek) > notFoundIndex ||
        name.value.search(digit) > notFoundIndex ||
        name.value.length < 1);
}

