var ajaxIgnoredKeys = {
    9: "tab",
    16: "shift",
    17: "ctrl",
    18: "alt",
    19: "pause",
    20: "caps",
    27: "esc",
    33: "pagedown",
    34: "pageup",
    35: "home",
    36: "end",
    37: "left",
    38: "up",
    39: "right",
    40: "down",
    45: "insert",
    91: "meta",
    93: "rightclick",
    112: "f1",
    113: "f2",
    114: "f3",
    115: "f4",
    116: "f5",
    117: "f6",
    118: "f7",
    119: "f8",
    120: "f9",
    121: "f10",
    122: "f11",
    123: "f12",
    144: "num",
    145: "scroll",
}

var selectPickerPaths = {
	foodProduct : '/selectpicker/searchProducts',
}

function initAjaxSelectPicker(element, path) {
    element.selectpicker().ajaxSelectPicker({
        ajax: {
            type: "POST",
            url: window.location.origin + path,
            data: { name:'{{{q}}}' }
        },
        langCode: Cookies.get('lang'),
        preprocessData:function (data) {
            var i, l = data.length, array = [];
            if (l) {
                for (i = 0; i < l; i++) {
                    array.push($.extend(true, data[i], {
                        text : data[i].value,
                        value: data[i].id
                    }));
                }
            }
            return array;
        }
    });
}