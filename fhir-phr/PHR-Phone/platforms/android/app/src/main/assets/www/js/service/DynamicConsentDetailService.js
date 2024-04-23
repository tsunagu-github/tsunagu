phrService.factory('dynamicConsentDetailService', function ($http, $q, $window, lookupData, patientManagementService) {

    return {
        /**
         * 詳細説明の外部サイトを表示する
         */
        openExpla: function (url) {
            $window.cordova.InAppBrowser.open(url, "_system");
        },
        /**
         * 回答ステータスを更新する（オプトイン）
         */
        update: function (successAction, studyId, subjectId, responseStatus, checks) {
             var webAction = new WebAction();
             var sendObject = new Object();
             sendObject.action = "DynamicConsent";
             sendObject.phrId = patientManagementService.getPatient().phrId;
             sendObject.type = "update";
             sendObject.studyId = studyId;
             sendObject.subjectId = subjectId;
             sendObject.responseStatus = responseStatus;
             sendObject.checks = checks;

             webAction.setSuccessAction(successAction);
             webAction.action($http, $q, sendObject);
        },
        /**
         * 回答ステータスを更新する（オプトアウト）
         */
        updateOptOut: function (successAction, studyId, subjectId, responseStatus, consentType) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "DynamicConsent";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.type = "update";
            sendObject.studyId = studyId;
            sendObject.subjectId = subjectId;
            sendObject.responseStatus = responseStatus;
            sendObject.consentType = consentType;

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
            }
    };

})
