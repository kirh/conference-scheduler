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

// function openEdit() {
//     var modal = document.getElementById("edit-conference");
//     modal.style.display = "block";
//     modal.addEventListener("click", function(event) {
//         if (event.target == modal) {
//             modal.style.display = "none";
//         }
//     })
// }

function calculateAge(birthday) {
    var ageDifMs = Date.now() - birthday.getTime();
    var ageDate = new Date(ageDifMs);
    return Math.abs(ageDate.getUTCFullYear() - 1970);
}

function validateBirthday(birthdayInput) {
    var birthdayString = birthdayInput.value;
    var birthday = new Date(birthdayString);
    var age = calculateAge(birthday);
    console.log('age = ' + age);
    if (age < 7 || age > 120) {
        birthdayInput.setCustomValidity("Age must be between 7 and 120 years")
    } else {
        birthdayInput.setCustomValidity("");
    }
}

function addAdittionalEmail() {
    var emailBlock = document.createElement("div");
    emailBlock.innerHTML = '<input class="form-control" type="email" name="email[]" placeholder="additional email"' +
        ' required><button class="btn btn-cancel">-</button>';

    var button =  emailBlock.getElementsByTagName('button')[0];
    button.addEventListener('click', removeAdditionalEmail);
    emailBlock.appendChild(button);
    document.getElementById('emails').appendChild(emailBlock);
}

function removeAdditionalEmail(event) {
    var emailBlock = event.target.parentNode;
    var emailsContainer = document.getElementById('emails');
    emailsContainer.removeChild(emailBlock);
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