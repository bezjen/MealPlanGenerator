function generateNewRandomRecipe() {
	$('#randomRecipe').find('.close').click(function() {
	    var randomRecipe = $('#randomRecipe');
	    var button = $(this);
	    var spinner = button.parent().find('.spinner-border');
        button.addClass('d-none');
        spinner.removeClass('d-none');
	    $.ajax({
            type: "POST",
            traditional: true,
            url: window.location.origin + '/randomRecipe'
        })
        .done(function (data) {
     	   randomRecipe.replaceWith(data);
     	   generateNewRandomRecipe();
        })
        .fail(function() {
            button.removeClass('d-none');
            spinner.addClass('d-none');
        });;
	});
}

$(document).ready(function () {
	generateNewRandomRecipe();
});