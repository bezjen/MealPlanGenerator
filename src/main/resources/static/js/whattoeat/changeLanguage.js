var languageCodes = {
	en: "en",
	ru: "ru"
}

var languageTexts = {
	en: "English",
	ru: "Русский"
}

function getButton(langCode, langText) {
	return '<button class="btn btn-outline-light border-light btn-sm dropdown-toggle px-2" type="button" data-toggle="dropdown">'
			+ '<img class="mr-2" width="20" src="/img/flags/' + langCode + '.png" alt="' + langText + '"/>' + langText + '</button>';
}

function getDropdown(langCode, langText, href) {
	return '<ul class="dropdown-menu"><li><a class="dropdown-item pb-1" href="' + href + '">' 
			+ '<img class="mr-2" width="20" src="/img/flags/' + langCode + '.png" alt="' + langText + '"/>' + langText + '</a></li></ul>'
}

$(document).ready(function () {
	var languages = $('#languages');
	var currentLocation = window.location.pathname;
	var langCode = currentLocation.split('/')[1];
	if ($.trim(langCode)) {
		if (languageCodes.en != langCode && languageCodes.ru != langCode) {
			langCode = '';
		} else {
			currentLocation = currentLocation.substr(3);
		}
	}
	if (!$.trim(langCode)) {
		langCode = Cookies.get('lang');
		if (languageCodes.en != langCode && languageCodes.ru != langCode) {
			langCode = languageCodes.en;
		}
	}
	if (languageCodes.en == langCode) {
		languages.html(getButton(languageCodes.en, languageTexts.en) 
				+ getDropdown(languageCodes.ru, languageTexts.ru, "/" + languageCodes.ru + currentLocation));
	} else {
		languages.html(getButton(languageCodes.ru, languageTexts.ru) 
				+ getDropdown(languageCodes.en, languageTexts.en, "/" + languageCodes.en + currentLocation));
	}
});
