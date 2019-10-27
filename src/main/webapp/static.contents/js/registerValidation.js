let passChecked = false;
let firstNameChecked = false;
let lastNameChecked = false;
let loginChecked = false;
let phoneChecked = false;

let phoneRegex = /\((17|29|33|44)\)[0-9]{8}/;
let logRegEx = /\W/;
let passwordRegEx = /\d\W/;
let peek = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_\s></%&*]+/;
let emailRegex = /\S+@\S+\\.\S+/;
let digit = /[0-9]+/;

let minLoginLength = 5;
let minPasswordLength = 4;
let notFoundIndex = -1;

let phone = document.getElementById("phone");
let password = document.getElementById("password");
let confirmPassword = document.getElementById("confirm_password");
let submit = document.getElementById("submit");
let firstName = document.getElementById("first_name");
let lastName = document.getElementById("last_name");
let login = document.getElementById("login");
let email = document.getElementById("email");

let submitChange = function () {
    if (firstNameChecked && lastNameChecked && passChecked && loginChecked && phoneChecked) {
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

let checkPassword = function () {
    if (password.value.search(passwordRegEx) > notFoundIndex ||
        password.value.length < minPasswordLength) {
        setBackgroundFieldRed(password);
    } else {
        setBackgroundFieldGreen(password);
    }
    if (password.value === confirmPassword.value) {
        setBackgroundFieldGreen(confirmPassword);
        passChecked = true;
    } else {
        setBackgroundFieldRed(confirmPassword);
        passChecked = false;
    }
    submitChange();
};

let checkName = function () {
    if (notValidField(firstName)) {
        setBackgroundFieldRed(firstName);
        firstNameChecked = false;
    } else {
        setBackgroundFieldGreen(firstName);
        firstNameChecked = true;
    }
    if (notValidField(lastName)) {
        setBackgroundFieldRed(lastName);
        lastNameChecked = false;
    } else {
        setBackgroundFieldGreen(lastName);
        lastNameChecked = true;
    }
    submitChange();
};

let checkEmail = function () {
    if (email.value.search(emailRegex) > notFoundIndex ||
        email.value.length <= minLoginLength) {
        setBackgroundFieldRed(email);
        loginChecked = false;
    } else {
        setBackgroundFieldGreen(email);
        loginChecked = true;
    }
    submitChange();
};


function notValidField(name) {
    return (name.value.search(peek) > notFoundIndex ||
        name.value.search(digit) > notFoundIndex ||
        name.value.length < 1);
}

let checkLogin = function () {
    if (login.value.search(logRegEx) > notFoundIndex ||
        login.value.length <= minLoginLength) {
        setBackgroundFieldRed(login);
        loginChecked = false;
    } else {
        setBackgroundFieldGreen(login);
        loginChecked = true;
    }
    submitChange();
};

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


