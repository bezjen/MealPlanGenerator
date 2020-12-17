var home = '<li class="breadcrumb-item text-nowrap"><a href="/"><i class="czi-home"></i>Главная</a></li>';

function findParentLink(currentPath) {
	var currentPageLink = $('.navbar-nav');
	var result = $();
	var i = 5;
	while ($.trim(currentPath)) {
		currentPath = currentPath.substr(0, currentPath.lastIndexOf('/'));
		var result = $('.navbar-nav').find('a[href="'+ currentPath + '"]');
		if (result.length) {
			break;
		}
		i--;
		if(i < 0) {
        	break;
        }
	}
	return result;
}

function getBreadCrumbElement(path, name) {
	return '<li class="breadcrumb-item text-nowrap" aria-current="page"><a href="' + path + '">' + name + '</a></li>';
}

$(document).ready(function () {
	var breadcrumb = $('#breadcrumb');
	var breadCrumbHTML = '';
	var currentPageLink = $('.navbar-nav').find('a[href="'+ window.location.pathname + '"]');
	if (currentPageLink.length) {
		currentPageLink = currentPageLink.parent();
	} else {
		currentPageLink = findParentLink(window.location.pathname);
	}
	if (currentPageLink.length) {
		currentPageLink.parents('li').each(function() {
		  	var a = $(this).children('a');
		  	breadCrumbHTML = getBreadCrumbElement(a.attr('href'), a.text()) + breadCrumbHTML;
		});
	}
	breadCrumbHTML = home + breadCrumbHTML;
	var currentPageTitle = document.title.substring(0, document.title.indexOf('|') - 1);
	breadCrumbHTML += '<li class="breadcrumb-item text-nowrap active" aria-current="page">' + currentPageTitle + '</li>';
	breadcrumb.html(breadCrumbHTML);
	breadcrumb.parent().parent().parent().find('h1').text(currentPageTitle);

});