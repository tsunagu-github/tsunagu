phrService.factory('patientManagementService', function ($http, $q, $window, lookupData) {

    // インスタンス変数として保持するようにする
    var patient = null;
    var patientBk = null;

    /**
     * 利用者更新Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successActionUpdate = function (data) {
        if (data.status === "0") {
            myNavigator.resetToPage( 'templates/menu-list.html', { animation: "slide" });
            //$window.location.href = "index.html";
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * 利用者作成Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successActionCreate = function (data) {
        if (data.status === "0") {
            window.localStorage.setItem("PHR_ID", data.phrId);
            window.localStorage.setItem("APP_KEY", data.keyId);
            myNavigator.pushPage('templates/phrstart-phrid-view.html');
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }
    };
    /**
     * 退会処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successActionWithDraw = function (data) {
        if (data.status === "0") {
            alert("ダイナミックコンセントの利用を停止いたしました。");
//            myNavigator.pushPage('templates/menu-list.html');
            window.location.href = "index.html"
        } else {
            alert("ダイナミックコンセントの利用停止に失敗しました。");
        }
    };
    return {
        /**
         * Patient情報を返却する
         * @returns {services_L22.patient|Object}
         */
        getPatient: function () {
            if (patient == null) {
                patient = new Object();
                patientBk = angular.copy(patient);
            }
            return patient;
        },
        /**
         * Patient情報を返却する
         * @returns {services_L22.patient|Object}
         */
        getViewPatient: function () {
            patient = this.getPatient();
            patient.zipCode1 = null;
            patient.zipCode2 = null;
            patient.viewBirthDate = null;
            if (patient.zipCode != null) {
                patient.zipCode1 = patient.zipCode.substring(0, 3);
                patient.zipCode2 = patient.zipCode.substring(3);
            }
            if (patient.birthDate != null) {
                patient.viewBirthDate = new Date(patient.birthDate);
            }
            return patient;
        },
        /**
         * Patient情報を設定する
         * @param {type} value
         * @returns {undefined}
         */
        setPatient: function (value) {
            patientBk = angular.copy(value);
            if (patientBk.zipCode1 != null) {
                delete patientBk.zipCode1;
            }
            if (patientBk.zipCode2 != null) {
                delete patientBk.zipCode2;
            }
            if (patientBk.prefectureName != null) {
                delete patientBk.prefectureName;
            }
            if (patientBk.diseaseManagement === "true") {
                patientBk.diseaseManagement = true;
            } else if (patientBk.diseaseManagement === "false" ||
                    patientBk.diseaseManagement == null || patientBk.diseaseManagement.length > 0) {
                patientBk.diseaseManagement = false;
            }
            if (patientBk.viewBirthDate != null) {
                patientBk.birthDate = phrJs.formatDate(patientBk.viewBirthDate, "yyyy/MM/dd");
            }
            patient = angular.copy(patientBk);
        },
        /**
         * 都道府県名を設定する
         * @param {type} patient
         * @returns {undefined}
         */
        setPrefectureName: function (patient) {
            var prefectureList = lookupData.prefectureList();
            patient.prefectureName = "不明";
            if (patient.prefectureCd != null && patient.prefectureCd.length > 0) {
                for (var i = 0; i < prefectureList.length; i++) {
                    if (prefectureList[i].cd == patient.prefectureCd) {
                        patient.prefectureName = prefectureList[i].name;
                        break;
                    }
                }
            }
        },
        /**
         * 郵便番号を設定する
         * @param {type} patient
         * @returns {undefined}
         */
        setZipCode: function (patient) {
            // 郵便番号の整形
            if (patient.zipCode1 != null && patient.zipCode2 &&
                    patient.zipCode1.length == 3 &&
                    patient.zipCode2.length == 4) {
                patient.zipCode = patient.zipCode1 + patient.zipCode2;
            } else {
                patient.zipCode = null;
            }
        },
        /**
         * 生年月日を設定する
         * @param {type} patient
         * @returns {undefined}
         */
        setBirthDate: function (patient) {
            if (patient.viewBirthDate != null) {
                patient.birthDate = phrJs.formatDate(patient.viewBirthDate, "yyyy/MM/dd");
            }
        },
        /**
         * Patient情報を設定する
         * @param {type} value
         * @returns {undefined}
         */
        rollbackPatient: function () {
            patient = angular.copy(patientBk);
        },
        /**
         * Patient情報を更新する
         * @param {type} patientDto
         * @returns {undefined}
         */
        updatePatient: function () {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "UpdatePatienAction";
            sendObject.phrId = patient.phrId;
            sendObject.patientDto = patient;

            webAction.setSuccessAction(successActionUpdate);
            webAction.action($http, $q, sendObject);
        },
        /**
         * Patient情報を作成する
         * @param {type} patientDto
         * @returns {undefined}
         */
        createPatient: function () {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "CreatePatienAction";
            sendObject.phrId = patient.phrId;
            sendObject.patientDto = patient;

            webAction.setSuccessAction(successActionCreate);
            webAction.action($http, $q, sendObject);
        },
        /**
         * 退会処理を実行する
         * @param {type} patientDto
         * @returns {undefined}
         */
        withDraw: function () {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "withDrawPatienAction";
            sendObject.phrId = patient.phrId;
            sendObject.patientDto = patient;

            webAction.setSuccessAction(successActionWithDraw);
            webAction.action($http, $q, sendObject);
        }
    };
})
