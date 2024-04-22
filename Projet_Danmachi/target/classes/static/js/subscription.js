document.getElementById("signup-form").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent default form submission

    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const passwordError = document.getElementById("passwordError");
    console.log(password);

    const email = document.getElementById("email").value;
    const emailError = document.getElementById("emailError");
    console.log(email)

    const firstName = document.getElementById('firstName').value;
    const firstNameError = document.getElementById("firstNameError");
    console.log(firstName)

    const lastName = document.getElementById('lastName').value;
    const lastNameError = document.getElementById("lastNameError");
    console.log(lastName)

    let validationError = false;

    // Reset error messages
    passwordError.textContent = "";
    emailError.textContent = "";
    firstNameError.textContent = "";
    lastNameError.textContent = "";

    if (password === null || password === "") {
        passwordError.textContent = "Le mot de passe ne peut pas être null";
        validationError = true;
    } else if (password !== confirmPassword) {
        passwordError.textContent = "Les mots de passe ne corresponds pas";
        validationError = true;
    }

    if (firstName === null || firstName === "") {
        firstNameError.textContent = "Le prénom ne peut pas être null";
        validationError = true;
    }

    if (lastName === null || lastName === "") {
        lastNameError.textContent = "Le nom ne peut pas être null";
        validationError = true;
    }

    if (email === null || email === "") {
        emailError.textContent = "L'email ne peut pas être null";
        validationError = true;
    } else if (!validateEmail(email)) {
        emailError.textContent = "Format email invalid";
        validationError = true;
    }

    //if error there don't do any calls to back - don't submit
    if (validationError) { return; }

    // Check email uniqueness
    $.ajax({
        url: "/checkUnique",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(email),
        success: function(response) {
            if (response) {
                emailError.textContent = "Email is already registered";
                return;
            }
            // If all validations pass, submit the form
            document.getElementById("signup-form").submit();
        },
        error: function(xhr, status, error) {
            console.error("Error checking uniqueness:", error);
            // Handle error if needed
        }
    });

});

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}