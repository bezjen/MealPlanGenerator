function initFilterSelectPickers() {
    initAjaxSelectPicker($('#includeIngredient'), selectPickerPaths.foodProduct);
}

function collectFiltersData() {
    var model = new Object();
    model.includeIngredientIds = [];
    $('#includeIngredient').find("option:selected").each(function(i) {
        model.includeIngredientIds[i] = this.value;
    });
    model.minPrepTime = $('#min-prep-time').val();
    model.maxPrepTime = $('#max-prep-time').val();
    model.minCookingTime = $('#min-cooking-time').val();
    model.maxCookingTime = $('#max-cooking-time').val();
    model.cookingMethodIds = [];
    $('#cooking-methods').find("input:checkbox:checked").each(function(i) {
        model.cookingMethodIds[i] = this.id;
    });
    model.dietIds = [];
    $('#diets').find("input:checkbox:checked").each(function(i) {
        model.dietIds[i] = this.id;
    });
    model.breakfast = $('#is-breakfast').find('input[name="is-breakfast-radio"]:checked').val();
    model.snack = $('#is-snack').find('input[name="is-snack-radio"]:checked').val();
    return model;
}

function applyFilters(model) {
    $.ajax({
        type: "POST",
        traditional: true,
        url: window.location.origin + '/recipes/applyFilters',
        data: model
    })
    .done(function (data) {
       $('#recipesSection').replaceWith(data);
       history.pushState(data, document.title, '/recipes/' + model.page);
       initPagination();
       $('.product-card :first').focus();
    });
}

function initRecipesButton(elem, page) {
    elem.click(function () {
        var model = collectFiltersData();
        model.page = page;
        applyFilters(model);
    });
}

function initFilters() {
    initRecipesButton($('#recipes-filter-button'), 1);
}

function initPagination() {
    var currentPage = +($('#current-page').text());
    initRecipesButton($('#prev-page'), currentPage - 1);
    initRecipesButton($('#next-page'), currentPage + 1);
    $('#pagination-pages').find('button').each(function() {
        var pageButton = $(this);
        initRecipesButton(pageButton, pageButton.text());
    });
}

$(document).ready(function () {
    initFilterSelectPickers();
    initFilters();
    initPagination();
});