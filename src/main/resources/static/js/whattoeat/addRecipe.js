var emptyIngredient = $('.recipe-ingredient').clone().append('<td><i class="h5 text-body czi-close"></i></td>');
var emptyStep = $('.recipe-step').clone();
var stepsCounter = 1;
var toTasteVal = emptyIngredient.find('.ingredient-measure option:contains(' + toTasteText + ')').val();
var imageMaxSize = 3 * 1024 * 1024;
var imagesMaxSize = 16 * imageMaxSize;

function initGeneral() {
    $('#recipeName').on('input', function() {
        validateRequiredInput($(this));
    });
    var recipeCategory = $('#recipeCategory');
    recipeCategory.selectpicker();
    recipeCategory.change(function() {
        validateRequiredInputWithParent($(this));
    });
    $('#recipeImage').change(function() {
        validateRequiredInputWithParent($(this));
        if (this.files[0].size > imageMaxSize) {
            $(this).parent().addClass('is-invalid');
        } else {
            $(this).parent().removeClass('is-invalid');
        }
        validateImagesSize();
    });
    $('#prepTime').on('input', function() {
        validateRequiredInput($(this));
        validateTime($(this));
    });
    inputFilter($("#prepTime"), function(key, value) {
	    var newValue = value + key;
        return (key == '0' || parseInt(key)) && newValue >= 1 && newValue <= 90;
    });
    $('#cookingTime').on('input', function() {
        validateRequiredInput($(this));
        validateTime($(this));
    });
    inputFilter($("#cookingTime"), function(key, value) {
        var newValue = value + key;
        return (key == '0' || parseInt(key)) && newValue >= 1 && newValue <= 240;
    });
    var primaryCookingMethod = $('#primaryCookingMethod');
    primaryCookingMethod.selectpicker();
    primaryCookingMethod.change(function() {
        validateRequiredInputWithParent($(this));
    });
	$('#secondaryCookingMethods').selectpicker();
	$('#servingsNumber').on('input', function() {
        validateRequiredInput($(this));
    });
    inputFilter($("#servingsNumber"), function(key, value) {
        var newValue = value + key;
        return (key == '0' || parseInt(key)) && newValue >= 1 && newValue < 100;
    });
}

function insertIngredient() {
	var newIngredient = emptyIngredient.clone();
	newIngredient.insertAfter($(this).parent().parent());
	initIngredient(newIngredient);
}

function removeIngredient() {
	$(this).parent().parent().remove();
}

function initIngredient(ingredient) {
    ingredient.find('.czi-add').click(insertIngredient);
    ingredient.find('.czi-close').click(removeIngredient);
    ingredient.find('.ingredient-product').selectpicker();
    var product = ingredient.find('.ingredient-product select');
    product.change(function() {
        validateRequiredInputWithParent($(this));
        validateFoodProducts();
    });
    ingredient.find('.ingredient-measure').selectpicker();
    var measure = ingredient.find('.ingredient-measure select');
    measure.change(function() {
        var measureAmount = measure.parent().parent().parent().find('.ingredient-amount');
        if (measure.val() == toTasteVal) {
            measureAmount.val('');
            measureAmount.prop('readonly', true);
            measureAmount.removeClass('is-invalid');
        } else {
            measureAmount.prop('readonly', false);
        }
        validateRequiredInputWithParent($(this));
    });
    var amount = ingredient.find('.ingredient-amount');
    amount.on('input', function() {
        if ($(this).parent().parent().find('.ingredient-measure select').val() != toTasteVal) {
            if (amount.val().trim().length > 0 && /^(([1-9]\d{0,2}([\.\,]\d{1,2})?)|([0][\.\,]\d{1,2}))$/.test(amount.val())) {
                amount.removeClass('is-invalid');
            } else {
                amount.addClass('is-invalid');
            }
        }
    });
    amount.on('keypress', function (event) {
        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
        var newValue = amount.val() + key;
        if (/^\d*[\.\,]?\d{0,2}$/.test(newValue) && parseInt(newValue) >= 0 && parseInt(newValue) < 1000) {
            if (key == ',') {
                amount.val(amount.val() + '.')
                event.preventDefault();
            }
        } else {
            event.preventDefault();
        }
    });
}

function initDirections() {
    $('#directions').find('.czi-add').click(insertStep);
    $('#directions').find('.czi-close').click(removeStep);
    $('#directions').find('.step-text').each(function(){
        $(this).on('input', function() {
            validateRequiredInput($(this));
        });
    });
    $('#directions').find('.step-image').change(function() {
        if (this.files[0].size > imageMaxSize) {
            $(this).parent().addClass('is-invalid');
        } else {
            $(this).parent().removeClass('is-invalid');
        }
        validateImagesSize();
    });
}

function insertStep() {
	var newStep = emptyStep.clone();
	stepsCounter++;
	var newStepId = 'step-image-' + stepsCounter;
	newStep.find('.custom-file-input').attr('id', newStepId);
	newStep.find('.custom-file-label').attr('for', newStepId);
	newStep.insertAfter($(this).parent().parent());
	newStep.find('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').html(fileName.replace('C:\\fakepath\\', " "));
    });
	newStep.find('.czi-add').click(insertStep);
	newStep.find('.czi-close').click(removeStep);
	newStep.find('.step-text').on('input', function() {
        validateRequiredInput($(this));
    });
    newStep.find('.step-image').change(function() {
        if (this.files[0].size > imageMaxSize) {
            $(this).parent().addClass('is-invalid');
        } else {
            $(this).parent().removeClass('is-invalid');
        }
        validateImagesSize();
    });
}

function removeStep() {
    $(this).parent().parent().remove();
}

function initAdditionalParameters() {
    $('#dietaryOptions').selectpicker();
}

function initAddRecipeButton() {
	$('#addRecipe').click(function () {
	    var addRecipeButton = $(this);
	    addRecipeButton.prop("disabled", true);
	    addRecipeButton.append('<span class="spinner-border spinner-border-sm ml-2" role="status" aria-hidden="true"></span>');
	    if (validateRecipe()) {
	        var recipe = collectRecipeData();
            $.ajax({
                type: "POST",
                traditional: true,
                cache: false,
                contentType: false,
                processData: false,
                url: window.location.origin + '/recipes/add',
                data: recipe
            })
            .done(function (data) {
                if ($(data).find('#error-panel').length) {
                    $('#messages').replaceWith(data);
                    enableButton(addRecipeButton);
                } else {
                    window.location.replace(window.location.origin + '/recipes');
                }
            });
	    } else {
	        enableButton(addRecipeButton);
	    }
    });
}

function enableButton(button) {
    button.prop("disabled", false);
	button.find('.spinner-border').remove();
}

function collectRecipeData() {
    var recipe = new FormData();
    recipe.append('recipeName', $('#recipeName').val());
    recipe.append('recipeCategory', $('#recipeCategory').val());
	recipe.append('recipeImage', $('#recipeImage').prop('files')[0]);
	recipe.append('recipeDescription', $('#recipeDescription').val());
	recipe.append('servingsNumber', $('#servingsNumber').val());
	recipe.append('prepTime', $('#prepTime').val());
	recipe.append('cookingTime', $('#cookingTime').val());
	recipe.append('primaryCookingMethod', $('#primaryCookingMethod').val());
	recipe.append('snack', $('#isSnack').is(":checked"));
	recipe.append('breakfastOrBrunch', $('#isBreakfastOrBrunch').is(":checked"));
	$('#secondaryCookingMethods').find("option:selected").each(function(i) {
		recipe.append('secondaryCookingMethods[' + i + ']', this.value);
    });
	$('#recipeIngredients').find("select.ingredient-product").each(function(i) {
		recipe.append('foodProducts[' + i + ']', $(this).find("option:selected").val());
    });
	$('#recipeIngredients').find("select.ingredient-measure").each(function(i) {
		recipe.append('measures[' + i + ']', $(this).find("option:selected").val());
    });
	$('#recipeIngredients').find(".ingredient-amount").each(function(i) {
		recipe.append('amounts[' + i + ']', this.value);
    });
	$('#directions').find(".step-text").each(function(i) {
		recipe.append('steps[' + i + ']', this.value);
    });
	$('#directions').find("input").each(function(i) {
		var stepImage = $(this);
		if (stepImage.val()) {
			recipe.append('stepsImages[' + i + ']', stepImage.prop('files')[0]);
		}
    });
    $('#dietaryOptions').find("option:selected").each(function(i) {
        recipe.append('dietaryOptions[' + i + ']', this.value);
    });
    return recipe;
}

function validateFoodProducts() {
    var foodProducts = $('#recipeIngredients').find('.ingredient-product select');
    var foodProductsSet = new Set();
    var duplicatesSet = new Set();
    $('#recipeIngredients').find('.ingredient-product select').each(function() {
        var foodProduct = $(this).val();
        if (foodProduct && foodProductsSet.has(foodProduct)) {
            duplicatesSet.add(foodProduct);
        } else {
            foodProductsSet.add(foodProduct);
        }
    });
    $('#recipeIngredients').find('.ingredient-product select').each(function() {
        var foodProduct = $(this);
        if (foodProduct.val()) {
            if (duplicatesSet.has(foodProduct.val())) {
                foodProduct.parent().addClass('is-invalid');
            } else {
                foodProduct.parent().removeClass('is-invalid');
            }
        }
    });
    return duplicatesSet.size == 0;
}

function validateImagesSize() {
    var imagesSize = 0;
    var recipeImage = $('#recipeImage').get(0);
    if (recipeImage.files.length > 0) {
        imagesSize += recipeImage.files[0].size;
    }
    $('#directions').find('.step-image').each(function() {
        if (this.files.length > 0) {
            imagesSize += this.files[0].size;
        }
    });
    var isSizeValid = imagesSize < imagesMaxSize;
    if (!isSizeValid) {
        alert(tooManyImagesText);
    }
    return isSizeValid;
}

function validateRecipe() {
    var isValid = true;

    isValid = validateRequiredInput($('#recipeName')) && isValid;
    isValid = validateRequiredInputWithParent($('#recipeCategory')) && isValid;
    isValid = validateRequiredInputWithParent($('#recipeImage')) && isValid;
    isValid = validateRequiredInput($('#prepTime')) && isValid;
    isValid = validateRequiredInput($('#cookingTime')) && isValid;
    isValid = validateRequiredInputWithParent($('#primaryCookingMethod')) && isValid;
    isValid = validateRequiredInput($('#servingsNumber')) && isValid;

    $('#recipeIngredients').find('.recipe-ingredient').each(function() {
        var recipeIngredient = $(this);
        isValid = validateRequiredInputWithParent(recipeIngredient.find('.ingredient-product select')) && isValid;
        isValid = validateRequiredInputWithParent(recipeIngredient.find('.ingredient-measure select')) && isValid;
        isValid = validateFoodProducts() && isValid;
        if (recipeIngredient.find('.ingredient-measure select').val() != toTasteVal) {
            isValid = validateRequiredInput(recipeIngredient.find('.ingredient-amount')) && isValid;
        }
    });

    var steps = $('#directions');
    steps.find('.step-text').each(function() {
        isValid = validateRequiredInput($(this)) && isValid;
    });
    steps.find('.step-image').each(function() {
        if (this.files.length > 0 && this.files[0].size > imageMaxSize) {
            isValid = false;
            $(this).parent().addClass('is-invalid');
        } else {
            $(this).parent().removeClass('is-invalid');
        }
    });
    isValid = validateImagesSize() && isValid;
    return isValid;
}

function validateTime(e) {
    var prepTime = $('#prepTime');
    var cookingTime = $('#cookingTime');
    if (prepTime.val() && cookingTime.val()) {
        if (parseInt(prepTime.val()) > parseInt(cookingTime.val())) {
            console.log('invalid');
            e.addClass('is-invalid');
        } else {
            prepTime.removeClass('is-invalid');
            cookingTime.removeClass('is-invalid');
        }
    }
}

function validateRequiredInput(element) {
    if (element.val().trim().length > 0) {
        element.removeClass('is-invalid');
    } else {
        element.addClass('is-invalid');
        return false;
    }
    return true;
}

function validateRequiredInputWithParent(element) {
    if (element.val().trim().length > 0) {
        element.parent().removeClass('is-invalid');
    } else {
        element.parent().addClass('is-invalid');
        return false;
    }
    return true;
}

$(document).ready(function () {
	emptyStep.find('td:last').remove();
	emptyStep.append('<td class="col-1"><i class="h5 text-body czi-close"></i></td>');
    initGeneral();
    initIngredient($('#recipeIngredients').find('.recipe-ingredient'));
    initDirections();
    var addIngredientButton = $('#recipeIngredients').find('.czi-add');
    for (i = 0; i < 4; i++) {
        addIngredientButton.click();
    }
    initAdditionalParameters();
    initAddRecipeButton();
});