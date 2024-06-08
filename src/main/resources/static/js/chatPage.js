document.getElementById("sent-message-form").addEventListener("submit", function(event) {
    const content = document.getElementById("content").value;

    if (content == "") {
        event.preventDefault();
        const errorMessage = document.getElementById("error-empty-message");
        errorMessage.textContent = "Veuillez rentrer un message dans le champ avant de l'envoyer.";
    }
});