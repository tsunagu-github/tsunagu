phrService.factory('tarminalService', function ($http, $q, $window, patientManagementService) {
    var tarminalInfo = null;

    /**
     * Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successAction = function (data) {
        if (data.status === "0") {
            var patient = patientManagementService.getPatient();
            patient.phrId = data.phrId;
            patient.familyName = data.familyName;
            patient.givenName = data.givenName;
            patient.familyKana = data.familyKana;
            patient.givenKana = data.givenKana;
            patient.birthDate = data.birthDate;
            patient.sexCd = data.sexCd;
            patient.zipCode = data.zipCode;
            patient.prefectureCd = data.prefectureCd;
            patient.addressLine = data.addressLine;
            patient.buildingName = data.buildingName;
            patient.telNo = data.telNo;
            patient.otherContactNo = data.otherContactNo;
            patient.emailAddress = data.emailAddress;
            patient.insurerNo = data.insurerNo;
            patient.diseaseManagement = data.diseaseManagement;
            patient.dynamicConsentFlg = data.dynamicConsentFlg;
            patientManagementService.setPatient(patient);

            if(data.tokenRegistered) {
                if(tarminalInfo.tokenId && tarminalInfo.tokenId !== 'registerd') {
                    localStorage.setItem("TOKEN_ID", 'registerd');
                    tarminalInfo.tokenId = 'registerd';
                }
            } else {
                if(tarminalInfo.tokenId) {
                    localStorage.removeItem("TOKEN_ID");
                    tarminalInfo.tokenId = null;
                }
            }
            //myNavigator.resetToPage('templates/menu-list.html');
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }


    };
    /**
     * Server接続処理失敗時のアクション
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var fatalAction = function (data) {
        $window.location.href = "fatal.html";
    };
    return {
        /**
         * 端末の情報を取得する
         * @returns {patientManagementService_L1.tarminalInfo|Object}
         */
        getTarminalInfo: function () {

            tarminalInfo = phrJs.getTarminalInfo();
            return tarminalInfo;
        },
        /**
         * 端末の認証を行います
         * @returns {undefined}
         */
        authenticationTarminal: function () {
            var webAction = new WebAction();

            if (tarminalInfo === null) {
                getTarminalInfo();
            }

            var sendObject = new Object();
            var now = new Date();
            sendObject.action = "TariminalAuthAction";
            sendObject.phrId = tarminalInfo.phrId;
            sendObject.timestamp = now.getTime() + "";
            var shaObj = new jsSHA(sendObject.timestamp + tarminalInfo.appKey, "TEXT");
            var hash = shaObj.getHash("SHA-512", "HEX");
            sendObject.keyValue = hash;
            if(tarminalInfo.tokenId && tarminalInfo.tokenId !== 'registerd') {
                sendObject.tokenId = tarminalInfo.tokenId;
            }

            webAction.setSuccessAction(successAction);
            webAction.setFatalActionon(fatalAction);
            webAction.action($http, $q, sendObject);
        }

    };
})