function initFilterSelectPickers() {
    initAjaxSelectPicker($('#includeIngredient'), selectPickerPaths.foodProduct);
    initAjaxSelectPicker($('#excludeIngredient'), selectPickerPaths.foodProduct);
}

function collectFiltersData() {
    var filter = new Object();
    filter.includeIngredientIds = [];
    $('#includeIngredient').find("option:selected").each(function(i) {
    	filter.includeIngredientIds[i] = this.value;
    });
    filter.excludeIngredientIds = [];
    $('#excludeIngredient').find("option:selected").each(function(i) {
    	filter.excludeIngredientIds[i] = this.value;
    });
    filter.minPrepTime = $('#min-prep-time').val();
    filter.maxPrepTime = $('#max-prep-time').val();
    filter.minCookingTime = $('#min-cooking-time').val();
    filter.maxCookingTime = $('#max-cooking-time').val();
    filter.cookingMethodIds = [];
    $('#cooking-methods').find("input:checkbox:checked").each(function(i) {
    	filter.cookingMethodIds[i] = this.id;
    });
    filter.dietIds = [];
    $('#diets').find("input:checkbox:checked").each(function(i) {
        filter.dietIds[i] = this.id;
    });
    filter.firstMealBreakfast = $('#first-meal-breakfast').is(":checked");
    return filter;
}

function initGenerateButton() {
	$('#generatorFilterButton').click(function () {
        var filter = collectFiltersData();
        filter.mealsNumber = $("#mealsNumber option:selected").val();
        filter.daysAmount = $("#daysAmount option:selected").val();
        $.ajax({
            type: "POST",
            traditional: true,
            url: window.location.origin + '/generator/generate',
            data: filter
        })
        .done(function (data) {
        	if ($(data).find('.is-invalid').length) {
        		$('#generatorFilter').replaceWith(data);
        		$('#generatorResult').empty();
        		initGenerateButton();
        	} else {
        		$('#mealsNumber').removeClass('is-invalid');
        		$('#generatorResult').replaceWith(data);
        		replaceMeal();
        		shoppingList();
        	}
        });
    });
}

function replaceMeal() {
	$('.ml-2.mb-1.close').click(function() {
	    var button = $(this);
	    var meal = button.parent().parent();
        var spinner = button.parent().find('.spinner-border');
        var filter = collectFiltersData();
        filter.index = meal.index();
        filter.mealsIds = [];
        $('#generatorResult').find('a').each(function(i) {
        	filter.mealsIds[i] = this.id;
        });
        button.addClass('d-none');
        spinner.removeClass('d-none');
	    $.ajax({
            type: "POST",
            traditional: true,
            url: window.location.origin + '/generator/replace',
            data: filter
        })
        .done(function (data) {
     	   meal.replaceWith(data);
     	   replaceMeal();
     	   $('#shoppingList').empty();
        })
        .fail(function() {
            button.removeClass('d-none');
            spinner.addClass('d-none');
        });
	});
}

function shoppingList() {
	$('#shoppingListButton').click(function() {
		var meals = $('#generatorResult');
        var model = new Object();
        model.mealsIds = [];
        $('#generatorResult').find('a').each(function(i) {
        	model.mealsIds[i] = this.id;
        });
	    $.ajax({
            type: "POST",
            traditional: true,
            url: window.location.origin + '/generator/shoppingList',
            data: model
        })
        .done(function (data) {
     	   $('#shoppingList').replaceWith(data);
        });
	});
}

$(document).ready(function () {
	initFilterSelectPickers();
	initGenerateButton();
});