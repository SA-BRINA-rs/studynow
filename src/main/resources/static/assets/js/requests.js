
var csrfToken = document.querySelector("[name='_csrf']").value;

var deleteRequest = function (btn, deleteElement=null) {
    var url = btn.getAttribute('data-url');
    $.ajax({
        type: "DELETE",
        url: url,
        beforeSend: function(xhr) {
            // Set the CSRF token in the request headers
            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
        },
        success: function(response) {
            // get the closest tr parent element and remove it
            if (deleteElement != null && deleteElement != undefined) {
                btn.closest(deleteElement).remove();
            }
            console.log(response.message);
            if(response.url != null && response.url != undefined)
                window.location.href = response.url;
        },
        error: function(xhr, status, error) {
            // Handle the error response, if needed
            console.error("Error deleting mode:", error);
        }
    });
};

var updateRequest = function (url, data, msg={success: 'updated successfully', error: 'error updating'}) {
    $.ajax({
        type: "PUT",
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
        },
        success: function(response) {
            console.log(response.message);
            if(response.url != null && response.url != undefined)
                window.location.href = response.url;
        },
        error: function(xhr, status, error) {
            console.error(msg.error, error);
        }
    });
};

var getFormBody = function(data) {
    var formBody = [];
    for (var property in data) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(data[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    return formBody.join("&");
}

var createRequest = function (url, data, contentType='application/json',
    msg={success: 'created successfully', error: 'error creating'}) {

    if(contentType == 'application/json'){
        data = JSON.stringify(data);
    } else if (contentType == 'application/x-www-form-urlencoded') {
        data = getFormBody(data);
    }

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: contentType,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
        },
        success: function(response) {
            console.log(response.message);
            if(response.url != null && response.url != undefined)
                window.location.href = response.url;
        },
        error: function(xhr, status, error) {
            console.error(msg.error, error);
        }
    });
}
