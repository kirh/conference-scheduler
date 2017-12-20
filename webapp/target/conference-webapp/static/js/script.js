function checkpassword() {
    var password = document.getElementById("password-input");
    var password_repeat = document.getElementById("password-repeat-input");
    if (password.value !== password_repeat.value) {
        password_repeat.setCustomValidity("Password doesn't match");
    } else {
        password_repeat.setCustomValidity("");
    }
}

function removeConference() {
    var sure = confirm("Are you sure want to remove this conference?");
}

function openEdit() {
    var modal = document.getElementById("edit-conference");
    modal.style.display = "block";
    modal.addEventListener("click", function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    })
}