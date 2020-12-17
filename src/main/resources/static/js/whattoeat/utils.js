function inputPattern(elem, pattern) {
	elem.on('keypress', function (event) {
	    var regex = new RegExp(pattern);
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});
}

function inputFilter(elem, filter) {
	elem.on('keypress', function (event) {
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!filter(key, elem.val())) {
	       event.preventDefault();
	       return false;
	    }
	});
}

// Restricts input for each element in the set of matched elements to the given inputFilter.
(function($) {
  $.fn.inputFilter = function(inputFilter) {
    return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
      if (inputFilter(this.value)) {
        this.oldValue = this.value;
        this.oldSelectionStart = this.selectionStart;
        this.oldSelectionEnd = this.selectionEnd;
      } else if (this.hasOwnProperty("oldValue")) {
        this.value = this.oldValue;
        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
      } else {
        this.value = "";
      }
    });
  };
}(jQuery));

function changeOptional(elem, selector) {
    var input = $(elem).parent().find(selector);
    if (input.val() == "false") {
        input.val("true");
    } else {
        input.val("false");
    }
}