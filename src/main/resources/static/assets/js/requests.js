
var csrfToken = document.querySelector("[name='_csrf']").value;

const modeId = document.getElementById('modeId');
const modeName = document.getElementById('modeName');
const modeDescription = document.getElementById('modeDescription');
const modeInstitution = document.getElementById('institutionId');

const courseId = document.getElementById('courseId');
const courseName = document.getElementById('courseName');
const courseSubject = document.getElementById('courseSubject');
const courseDescription = document.getElementById('courseDescription');
const courseStartDate = document.getElementById('courseStartDate');
const courseEndDate = document.getElementById('courseEndDate');
const coursePrice = document.getElementById('coursePrice');
const courseTags = document.getElementById('courseTags');
const courseModeOption = document.getElementById('courseModeOption');
const courseInstitution = document.getElementById('courseInstitution');

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
        data: JSON.stringify(data),
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

var createMode = function(btn){
    var url = "/institution/mode";
    var data = {mode: {
            name: modeName.value,
            description: modeDescription.value,
            institution: modeInstitution.value
    }};
    createRequest(url, data);
}

var updateMode = function(){
    var url = "/institution/mode/" + modeId.value;
    var data = {mode: {
            id: modeId.value,
            name: modeName.value,
            description: modeDescription.value,
            institution: modeInstitution.value
    }};
    updateRequest(url, mode);
}

var editMode = function(btn) {
    console.log("Editing mode");
    var mode = {
        id: btn.getAttribute('data-id'),
        name: btn.getAttribute('data-name'),
        description: btn.getAttribute('data-description'),
        institution: { id: btn.getAttribute('data-institutionId') }
    };
    console.log(mode);
    // Now you can access the properties of the mode object and set them in the form
    modeId.value = mode.id;
    modeName.value = mode.name;
    modeDescription.value = mode.description;
    institutionId.value = mode.institution.id;
};

var editCourse = function(btn) {
    console.log("Editing course");
    var course = {
        id: btn.getAttribute('data-id'),
        name: btn.getAttribute('data-name'),
        subject: btn.getAttribute('data-subject'),
        description: btn.getAttribute('data-description'),
        startDate: btn.getAttribute('data-startDate'),
        endDate: btn.getAttribute('data-endDate'),
        price: btn.getAttribute('data-price'),
        tags: btn.getAttribute('data-tags'),
        mode: { id: btn.getAttribute('data-modeId') },
        institution: { id: btn.getAttribute('data-institutionId') }
    };
    console.log(course);
    // Now you can access the properties of the course object and set them in the form
    courseId.value = course.id;
    courseName.value = course.name;
    courseSubject.value = course.subject;
    courseDescription.value = course.description;
    courseStartDate.value = course.startDate;
    courseEndDate.value = course.endDate;
    coursePrice.value = course.price;
    courseTags.value = course.tags.slice(1, -1);
    courseModeOption.value = course.mode.id;
    institutionId.value = course.institution.id;
}

var updateCourse = function(){
    var url = "/institution/course";
    var data = {course: {
            id: courseId.value,
            name: courseName.value,
            subject: courseSubject.value,
            description: courseDescription.value,
            startDate: courseStartDate.value,
            endDate: courseEndDate.value,
            price: coursePrice.value,
            tags: courseTags.value,
            mode: { id: courseModeOption.value },
            institution: { id: institutionId.value }
    }};
    updateRequest(url, data);
}