document.getElementById("chat-form").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent default form submission

    const content = document.getElementById("content").value;
    console.log(content)

    let validationError = false;

    // Reset error messages
    emptyContent.textContent = "";

    if (password === null || password === "") {
        passwordError.textContent = "Le message est vide.";
        validationError = true;
    }

    //if error there don't do any calls to back - don't submit
    if (validationError) { return; }

});