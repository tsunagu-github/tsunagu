function PhrCommon() {
    
    this.unReadCount = 0;

    /*----------------------
     * 日付フォーマット
     ----------------------*/
    this.formatDate = function (date, format) {if (!format) format = 'yyyy-MM-dd HH:mm:ss.fff'; format = format.replace(/yyyy/g, date.getFullYear()); format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice( - 2)); format = format.replace(/dd/g, ('0' + date.getDate()).slice( - 2)); format = format.replace(/HH/g, ('0' + date.getHours()).slice( - 2)); format = format.replace(/mm/g, ('0' + date.getMinutes()).slice( - 2)); format = format.replace(/ss/g, ('0' + date.getSeconds()).slice( - 2)); if (format.match(/f/g)) {var milliSeconds = ('00' + date.getMilliseconds()).slice( - 3); var length = format.match(/f/g).length; for (var i = 0; i < length; i++) format = format.replace(/f/, milliSeconds.substring(i, i + 1)); }return format; };

    /**
     * 端末の情報を取得する
     * @returns 端末情報
     */
    this.getTarminalInfo =  function () {
        var tarminalInfo = new Object();
        tarminalInfo.phrId = window.localStorage.getItem("PHR_ID");
        tarminalInfo.appKey = window.localStorage.getItem("APP_KEY");
        tarminalInfo.tokenId = window.localStorage.getItem("TOKEN_ID");
        return tarminalInfo;
    };
    /**
     * 認証情報を作成する
     * @returns 認証情報
     */
    this.createAuthenticationInfo =  function () {
        var tarminalInfo = this.getTarminalInfo();
        var authInfo = new Object();
        var now = new Date();
        
        authInfo.phrId = tarminalInfo.phrId;
        authInfo.timestamp = now.getTime() + "";
        var shaObj = new jsSHA(authInfo.timestamp + tarminalInfo.appKey, "TEXT");
        var hash = shaObj.getHash("SHA-512", "HEX");
        authInfo.keyValue = hash;
        return authInfo;
    };
    /**
     * 未読件数を設定する
     * @param {type} count
     * @returns {undefined}
     */
    this.setUnReadCount = function (count) {
        unReadCount = count;
    };
    /**
     * 未読件数を取得する
     * @returns {Number|count}
     */
    this.getUnReadCount = function () {
        return unReadCount;
    };
}
var phrJs = new PhrCommon();