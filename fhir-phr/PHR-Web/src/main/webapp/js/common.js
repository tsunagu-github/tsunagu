function submitForm(command) {
	var from = document.forms[0];
	from.target="_self";
	if (from.command != null) {
		from.command.value = command;
	}
	from.submit();
}
function submitForm(command, param1) {
	var from = document.forms[0];
	 from.target="_self";
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.param1 != null) {
		from.param1.value = param1;
	}
	from.submit();
}
function submitForm(command, param1, param2) {
	var from = document.forms[0];
	from.target="_self";
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.param1 != null) {
		from.param1.value = param1;
	}
	if (from.param2 != null) {
		from.param2.value = param2;
	}
	from.submit();
}
function submitForm(command, param1, param2, param3) {
	var from = document.forms[0];
	from.target="_self";
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.param1 != null) {
		from.param1.value = param1;
	}
	if (from.param2 != null) {
		from.param2.value = param2;
	}
	if (from.param3 != null) {
		from.param3.value = param3;
	}
	from.submit();
}
function submitFormBlank(command) {
	var from = document.forms[0];
	from.target="_blank";
	if (from.command != null) {
		from.command.value = command;
	}
	from.submit();
}
function submitFormBlank(command, param1) {
	var from = document.forms[0];
	from.target="_blank";
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.param1 != null) {
		from.param1.value = param1;
	}
	from.submit();
}
function submitFormBlank(command, param1, param2) {
	var from = document.forms[0];
	from.target="_blank";
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.param1 != null) {
		from.param1.value = param1;
	}
	if (from.param2 != null) {
		from.param2.value = param2;
	}
	from.submit();
}
function submitFormBlank(command, param1, param2, param3) {
	var from = document.forms[0];
	from.target="_blank";
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.param1 != null) {
		from.param1.value = param1;
	}
	if (from.param2 != null) {
		from.param2.value = param2;
	}
	if (from.param3 != null) {
		from.param3.value = param3;
	}
	from.submit();
}
function clearFormAll() {
			for (var i=0; i<document.forms.length; ++i) {
		clearForm(document.forms[i]);
			}
};

function submitFormPaging(command, clickedPage) {
	var from = document.forms[0];
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.clickedPage!= null) {
		from.clickedPage.value = clickedPage
	}
	from.submit();
}
function submitFormIndex(command, index) {
	var from = document.forms[0];
	if (from.command != null) {
		from.command.value = command;
	}
	if (from.index!= null) {
		from.index.value = index
	}
	from.submit();
}

function clearForm(form) {
		for(var i=0; i<form.elements.length; ++i) {
		clearElement(form.elements[i]);
			}
};
function clearElement(element) {
	switch(element.type) {
		case "hidden":
		case "submit":
		case "reset":
		case "button":
		case "image":
			return;
		case "file":
			return;
		case "text":
		case "password":
		case "textarea":
			element.value = "";
			return;
		case "checkbox":
		case "radio":
			element.checked = false;
			return;
		case "select-one":
		case "select-multiple":
			element.selectedIndex = 0;
			return;
		default:
	}
};
function setCookie(name, value, domain, path, expires) {
	if (!name) return;

	var str = name + "=" + escape(value);
	if (domain) {
		if (domain == 1) domain = location.hostname.replace(/^[^\.]*/, "");
		str += "; domain=" + domain;
	}
	if (path) {
		if (path == 1) path = location.pathname;
		str += "; path=" + path;
	}
	if (expires) {
		var nowtime = new Date().getTime();
		expires = new Date(nowtime + (60 * 60 * 24 * 1000 * expires));
		expires = expires.toGMTString();
		str += "; expires=" + expires;
	}
	if (location.protocol == "https:") {
		str += "; secure";
	}

	document.cookie = str;
}
function getCookie(name) {
	var value = "";
	if (document.cookie) {
		var cookies = document.cookie.split("; ");
		for (var i = 0; i < cookies.length; i++) {
			var str = cookies[i].split("=");
			if (name == str[0]) {
				return unescape(str[1]);
			}
		}
	}
	return value;
}