let toChecked = false;


let peek = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_></%&*]+/;

let maxZoneLength = 25;
let notFoundIndex = -1;

let to = document.getElementById("to");

let submit = document.getElementById("submit");

let submitChange = function () {
    if (toChecked) {
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

let checkTo = function () {
    if (notValidField(to)) {
        setBackgroundFieldRed(to);
        toChecked = false;
    } else {
        setBackgroundFieldGreen(to);
        toChecked = true;
    }
    submitChange();
};


function notValidField(name) {
    return (name.value.search(peek) > notFoundIndex ||
        name.value.length < 1 ||
        name.value.length>maxZoneLength);
}

