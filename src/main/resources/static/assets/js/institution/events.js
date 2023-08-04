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

var updateMode = function(self){
    event.preventDefault();
    var url = "/institution/mode/" + modeId.value;
    var data = {
            id: modeId.value,
            name: modeName.value,
            description: modeDescription.value,
            institution: { id: modeInstitution.value }
    };
    updateRequest(url, data);
}

var updateCourse = function(self){
    event.preventDefault();
    var url = "/institution/course";
    var data = {
            id: courseId.value,
            name: courseName.value,
            subject: courseSubject.value,
            description: courseDescription.value,
            startDate: courseStartDate.value,
            endDate: courseEndDate.value,
            price: coursePrice.value,
//            tags: courseTags.value,
            mode: { id: courseModeOption.value },
            institution: { id: institutionId.value }
    };
    updateRequest(url, data);
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
