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