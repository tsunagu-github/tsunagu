function Logger() {
    /*----------------------
     * ログレベル定数
     ----------------------*/
    var ERROR_LEVEL = 1;
    var WARN_LEVEL = 2;
    var INFO_LEVEL = 3;
    var DEBUG_LEVEL = 4;
    var LOG_LEVEL = 1;  // 現在の出力レベル
    /*----------------------
     * ログ出力
    ----------------------*/
    this.error = function (obj) {if (LOG_LEVEL < ERROR_LEVEL) {return;}if (window.console == "undefined") {return;}try {var func = {};Error.captureStackTrace( func, this.error );var date = this.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.fff");var msg = date + " [ERROR] " + func.stack.fileName + "#" + func.stack.functionName + "(" + func.stack.lineNumber + ") - " + obj;window.console.error(msg);} catch( ex ){window.console.error(obj);}};
    this.warn = function (obj) {if (LOG_LEVEL < WARN_LEVEL) {return;}if (window.console == "undefined") {return;}try {var func = {};Error.captureStackTrace( func, this.warn );var date = this.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.fff");var msg = date + " [WARN] " + func.stack.fileName + "#" + func.stack.functionName + "(" + func.stack.lineNumber + ") - " + obj;window.console.warn(msg);} catch( ex ){window.console.warn(obj);}};
    this.info = function (obj) {if (LOG_LEVEL < INFO_LEVEL) {return;}if (window.console == "undefined") {return;}try {var func = {};Error.captureStackTrace( func, this.info );var date = this.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.fff");var msg = date + " [INFO] " + func.stack.fileName + "#" + func.stack.functionName + "(" + func.stack.lineNumber + ") - " + obj;window.console.info(msg);} catch( ex ){window.console.info(obj);}};
    this.debug = function (obj) {if (LOG_LEVEL < DEBUG_LEVEL) {return;}if (window.console == "undefined") {return;}try {var func = {};Error.captureStackTrace( func, this.debug );var date = this.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.fff");var msg = date + " [DEBUG] " + func.stack.fileName + "#" + func.stack.functionName + "(" + func.stack.lineNumber + ") - " + obj;window.console.log(msg);} catch( ex ){window.console.log(obj);}};
    Error.prepareStackTrace = function( e, st ) {try {var funcName = st[0].getFunctionName();var lineNumb = st[0].getLineNumber();var fileName = st[0].getFileName();var fileNames = fileName.split("/");fileName = fileNames[fileNames.length-1];return {functionName: funcName,lineNumber: lineNumb,fileName: fileName,};} catch( ex ){}};
}
var logger = new Logger();