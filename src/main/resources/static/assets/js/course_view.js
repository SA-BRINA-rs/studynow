
var favoriteButton = document.getElementById("favorite");

var rateComment = document.getElementById("comment");
var ratingList = document.getElementById("rating_list");

var addToFavorites = function(id) {
    var url = "/view/favorites";
    var data = id;
    createRequest(url, data);
}

favoriteButton.addEventListener("click", function (event) {
    var courseId = favoriteButton.getAttribute("data-id");
    // check if favorite has class active
    if (!favoriteButton.classList.contains("active")) {
        // remove active class
        favoriteButton.classList.remove("active");
        // remove from favorites
        deleteRequest(favoriteButton);
    } else {
        // add active class
        favoriteButton.classList.add("active");
        // add to favorites
        addToFavorites(courseId);
    }
});

var postRate = function(btn){

    var url = btn.getAttribute('data-url');
    var courseId = btn.getAttribute('data-courseId');
    var rate = 0;
    // use ratingList to get all li elements. For each li element get the svg element and check if it has the class star-active. And count how many stars are star-active
    console.log(ratingList.querySelectorAll('li').length)
    console.log(ratingList.children)
    Array.from(ratingList.children).forEach(function (el) {
        if (el.tagName == 'LI') {
            var svg = el.children[0];
            if (svg && svg.classList.contains('star-yellow')) {
                rate++;
            }
        }
    });
    rate = rate = 0 ? 1 : rate;

    var data = {
        "id": "-1",
        "user.id": "-1",
        "course.id": courseId,
        "rate": rate,
        "comment": rateComment.value
    };

    let contentType = 'application/x-www-form-urlencoded';
    createRequest(url, data, contentType);
};