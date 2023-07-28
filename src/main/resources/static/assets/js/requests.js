var deleteRequest = function (btn) {
    var csrfToken = document.querySelector("[name='_csrf']").value;
    console.log(btn);
    var url = btn.getAttribute('data-url');

    $.ajax({
        type: "DELETE",
        url: url,
        beforeSend: function(xhr) {
            // Set the CSRF token in the request headers
            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
        },
        success: function(data) {
            // Handle the success response, if needed
            console.log("Mode deleted successfully");
        },
        error: function(xhr, status, error) {
            // Handle the error response, if needed
            console.error("Error deleting mode:", error);
        }
    });
};