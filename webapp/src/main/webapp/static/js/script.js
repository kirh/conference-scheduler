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


function confirmAndRemove(id, url) {
    var element = document.getElementById(id);
    var nameCell = element.getElementsByClassName('name')[0];
    var sure = confirm("Are you sure want to remove " + nameCell.innerText + "?");
    if (sure) {
        remove(element, url);
    }
}

function remove(element, url) {
    var xHttp = new XMLHttpRequest();
    xHttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var parent = element.parentNode;
            parent.removeChild(element);
        } else if (this.readyState == 4) {
            alert(this.statusText);
        }
    }
    try {
        xHttp.open('POST', url, true);
        xHttp.send();
    } catch (exception) {
        console.log(exception);
    }
}