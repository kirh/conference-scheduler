function changeToLanguage(language) {
    location.replace("/change-locale?lang=" + language);
}

function checkpassword() {
    var password = document.getElementById("password-input");
    var password_repeat = document.getElementById("password-repeat-input");
    if (password.value !== password_repeat.value) {
        password_repeat.setCustomValidity("Password doesn't match");
    } else {
        password_repeat.setCustomValidity("");
    }
}

function validateConferenceDate(input) {
    var dateString = input.value;
    var date = Date.parse(dateString);
    if (date <= Date.now()) {
        input.setCustomValidity("Should be date in future")
    } else {
        input.setCustomValidity("");
    }
}

function confirmAndRedirect(uri) {
    var sure = confirm("Are you sure want to remove this conference?");
    if (sure) {
        location.replace(uri);
    }
}

function goToPreviousPage() {
    history.back()
}