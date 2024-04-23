phrService.factory('clinicalTestResultService', function ($http, $q, patientManagementService) {

    return {
        /**
         * Patient情報を返却する
         * @returns {services_L22.patient|Object}
         */
        getList: function (targetDate, successAction) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetClinicalTestResult";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.targetDate = phrJs.formatDate(targetDate, "yyyy/MM/dd");

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
    };
})