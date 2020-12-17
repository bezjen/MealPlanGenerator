function getCurrentLocationPrefix() {
    var currentLocation = window.location.pathname;
    if (currentLocation == "/en" || currentLocation.startsWith("/en/")) {
        return "/en";
    } else if (currentLocation == "/ru" || currentLocation.startsWith("/ru/")) {
        return "/ru";
    } else {
        return "";
    }
}

$(document).ready(function () {
    $('.navbar-nav a').each(function() {
        var link = $(this);
        link.attr("href", getCurrentLocationPrefix() + link.attr("href"));
    });
    var brand = $('.navbar-brand');
    brand.attr("href", getCurrentLocationPrefix() + brand.attr("href"));
});
