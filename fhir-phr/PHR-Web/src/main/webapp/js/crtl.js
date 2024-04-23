function ControlJS() {
    var inputObjects = new Array();
    var minCtrl = null;
    var maxCtrl = null;
    this.isNull = function(obj) {
        if ((typeof (obj)) == "undefined") {
            return true;
        } else if (obj == null) {
            return true;
        }
        return false;
    }
    this.isArray = function(obj) {
        if ((typeof (obj[0])) != "undefined") {
            return true;
        }
        return false;
    }
    this.isSingleChar = function(str) {
        var i = 0;
        var inrRet = 0;
        for (i = 0; i < str.length; i++) {
            if (escape(str.charAt(i)).length >= 4) {
                return false;
            }
        }
        return true;
    }
    this.isAlphabet = function(str) {
        if (self.isNull(str.match(/([^A-Za-z])/))) {
            return true;
        }
        return false;
    }
    this.isNumeric = function(str) {
        if (!isNaN(str)) {
            return true;
        }
        return false;
    }
    this.length = function(str) {
        var i = 0;
        var intRet = 0;
        for (i = 0; i < str.length; i++) {
            if (escape(str.charAt(i)).length >= 4) {
                intRet += 2;
            } else {
                intRet++;
            }
        }
        return intRet;
    }
    this.padding = function(len, char1) {
        var i;
        var val = "";
        for (i = 0; i < len; i++) {
            val += char1;
        }
        return val;
    }
    this.trim = function(str) {
        var newStr = str.replace(/^\s+|\s+$/g, "");
        return newStr;
    }
    // Submitタイミングを制御する
    var timerId;
    var isSubmit = true
    var submitTime = 0;
    this.checkSubmit = function() {
        var date = new Date();
        var time = date.getTime();
        if (isSubmit) {
            isSubmit = false;
            timerId = setTimeout(function() { alert("AA"); clearTimeout(timerId); isSubmit = true; submitTime = 0; }, 1000);
            submitTime = submitTime;
            return true;
        }
        return false;
    }
    this.addControl = function(id, type, tabIndex, tabStop, imeMode, maxLength, separator, inputFormat, decimal, useAutoPostBack) {
        var obj = document.getElementById(id);
        var inputObject = null;
        var minObj = null;
        if (obj != null) {
            inputObject = new InputObject();
            inputObject.id = id;
            inputObject.type = type;
            inputObject.tabIndex = tabIndex;
            inputObject.tabStop = tabStop;
            inputObject.imeMode = imeMode;
            inputObject.maxLength = maxLength;
            inputObject.separator = separator;
            inputObject.inputFormat = inputFormat;
            inputObject.decimal = decimal;
            inputObject.useAutoPostBack = useAutoPostBack;
            inputObject.control = obj;
            inputObjects[id] = inputObject;

            if (type == "num") {
                obj.onfocus = self.numFocus;
                obj.onblur = self.numBlur;
                obj.onkeydown = self.numKeyDown;
                obj.onkeyup = self.numKeyUp;
            }
            if (!inputObject.tabStop) {
                return true;
            }
            for (var i in inputObjects) {
                var inputObject = document.getElementById(inputObjects[i].id);
                if (self.isNull(minCtrl) && inputObjects[i].tabStop && !inputObject.disabled) {
                    minCtrl = inputObject;
                }
                if (!self.isNull(minCtrl) &&
	            		inputObject.tabIndex < minCtrl.tabIndex &&
	            		inputObject.tabIndex != -1 &&
	            		inputObjects[i].tabStop &&
	            		!inputObject.disabled) {
                    minCtrl = inputObject;
                }
                if (self.isNull(maxCtrl) && inputObjects[i].tabStop && !inputObject.disabled) {
                    maxCtrl = inputObject;
                }
                if (!self.isNull(maxCtrl) &&
	            		inputObject.tabIndex > maxCtrl.tabIndex &&
	            		inputObject.tabIndex != -1 &&
	            		inputObjects[i].tabStop &&
	            		!inputObject.disabled) {
                    maxCtrl = inputObject;
                }
            }
            if (minCtrl != null) {
                minObj = document.getElementById(minCtrl.id);
            }
            if (minObj != null) {
                //minObj.focus();
            }
        }
    }
    function InputObject() {
        this.id = "";
        this.type = null;
        this.tabIndex = -1;
        this.tabStop = false;
        this.imeMode = null;
        this.maxLength = 0;
        this.separator = null;
        this.inputFormat = null;
        this.decimal = null;
        this.control = null;
        this.useAutoPostBack = false;
        this.toString = function() {
            var ret = "id=" + this.id + ",";
            ret += "type=" + this.type + ",";
            ret += "tabIndex=" + this.tabIndex + ",";
            ret += "tabStop=" + this.tabStop + ",";
            ret += "imeMode=" + this.imeMode + ",";
            ret += "maxLength=" + this.maxLength + ",";
            ret += "separator=" + this.separator + ",";
            ret += "inputFormat=" + this.inputFormat + ",";
            ret += "decimal=" + this.decimal + ",";
            ret += "control=" + this.control + ",";
            ret += "useAutoPostBack=" + this.useAutoPostBack;
            return (ret);
        }
    }
    this.numFocus = function(e) {
        var srcElement = self.getSrcElement(e);
        var id = srcElement.getAttribute("id");
        var inputObject = inputObjects[id];

        var num = String(srcElement.value);
        var old = num.length;
        //var maxlength = srcElement.getAttribute("maxLength");
        //if (!self.isNull(maxlength)) {
        //	maxlength = new Number(maxlength);
        //}
        var maxlength = inputObject.maxLength;
        num = num.replace(/,/g, "");

        //if (maxlength > 0) {
        //	srcElement.setAttribute("maxLength",maxlength-(old - num.length));
        //}
        srcElement.style.imeMode = "" + inputObject.imeMode;
        srcElement.value = num;
        numText = srcElement.value;
        srcElement.select();
    }
    this.numBlur = function(e) {
        var srcElement = self.getSrcElement(e);
        var id = srcElement.getAttribute("id");
        var inputObject = inputObjects[id];
        var separator = inputObject.separator;
        //var maxlength = srcElement.getAttribute("maxLength");
        var maxlength = inputObject.maxLength;
        //if (!self.isNull(maxlength)) {
        //	maxlength = new Number(maxlength);
        //}
        var num = srcElement.value;
        if (!self.isNumeric(num)) {
            num = "";
        }
        if (num != "") {
        	num = num.replace(/(^\s+)|(\s+$)/g, "");
            //num = String(new Number(num));
       }
        var f = num.substring(0,1);
        if (f == "-") {
        	num = num.substring(1);
        }
        var old = num.length;
        if (separator == "true") {
            while (num != (num = num.replace(/^(\d+)(\d{3})/, "$1,$2"))) {
            }
        }
        //if (maxlength > 0) {
        //    srcElement.setAttribute("maxLength", maxlength + (num.length-old));
        //}
        if (f == "-") {
        	num = "-" + num;
        }
        srcElement.value = num;
    }
    this.numKeyDown = function(e) {
        var srcElement = self.getSrcElement(e);
        var event = self.getEvent(e);
        var k = event.keyCode;
        var s = event.shiftKey;
        var c = event.ctrlKey;
        if (k > 0) {
            // 1-0(keyboard)
            if ((s == false && c == false) && (k >= 48 && k <= 57)) {
                if (!self.numTextBoxCheck(e)) {
                    return false;
                }
                numText = srcElement.value;
                return true;
            }
            // 0-9(ten-key) 
            if (k >= 96 && k <= 105) {
                if (!self.numTextBoxCheck(e)) {
                    return false;
                }
                numText = srcElement.value;
                return true;
            }
            // BS,DEL
            if (k == 8 || k == 46) {
                numText = srcElement.value;
                return true;
            }
            // +,-,.
            if (k == 107 || k == 109 || k == 110 || k == 190) {
                //if (k==107 || k==109 || k==110) {
                if (!self.numTextBoxCheck(e)) {
                    return false;
                }
                numText = srcElement.value;
                return true;
            }
            // +,-,.[Shift]
            if ((s && k == 187) || (k == 189) || (s && k == 190)) {
                if (!self.numTextBoxCheck(e)) {
                    return false;
                }
                numText = srcElement.value;
                return true;
            }
            // <-, ->
            if (k == 37 || k == 39) {
                if (!self.numTextBoxCheck(e)) {
                    return false;
                }
                numText = srcElement.value;
                return true;
            }
            // [Tab]
            if (k == 9) {
                if (!self.numTextBoxCheck(e)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    this.numTextBoxCheck = function(e) {
        var srcElement = self.getSrcElement(e);
        var id = srcElement.getAttribute("id");
        var inputObject = inputObjects[id];
        var value = srcElement.value;
        var values = value.split(".");
        if (values.length > 2) {
            return false;
        }
        if (inputObject.maxLength > 0) {
            if (values[0].length > inputObject.maxLength) {
                return false;
            }
        }
        if (values.length == 2 && values[1].length > inputObject.decimal) {
            return false;
        }
        return true;

    }
    this.numTextBoxDelCheck = function(e) {
        var srcElement = self.getSrcElement(e);
        var id = srcElement.getAttribute("id");
        var inputObject = inputObjects[id];
        var value = srcElement.value;
        var values = value.split(".");
        if (values.length > 2) {
            return false;
        }
        if (inputObject.maxLength > 0) {
            if (values[0].length > inputObject.maxLength) {
                return false;
            }
        }
        if (values.length == 2 && values[1].length > inputObject.decimal) {
            return false;
        }
        return true;

    }

    this.numKeyUp = function(e) {
        var srcElement = self.getSrcElement(e);
        var id = srcElement.getAttribute("id");
        var inputObject = inputObjects[id];
        var value = srcElement.value;
        var values = value.split(".");

	    if (value.length == 1 && value.substring(0,1).match(/\+|\-/)) {
            return true;
        }

        if (values.length > 2) {
            srcElement.value = numText;
        }

        if (inputObject.maxLength > 0) {
            if (values[0].length > inputObject.maxLength) {
                srcElement.value = numText;
            }
        }
        
        if (values.length == 2 && values[1].length > inputObject.decimal) {
            srcElement.value = numText;
        }

        if (!self.isNumeric(srcElement.value)) {
            if (self.isNumeric(numText)) {
                srcElement.value = numText;
            }
        }
        return true;
    }
    this.getSrcElement = function(e) {
        if (document.all) {
            return event.srcElement;
        }
        else {
            return e.target;
        }
    }
    this.getEvent = function(e) {
        if (document.all) {
            return event;
        }
        else {
            return e;
        }
    }
    var self = this;

}
control = new ControlJS();
