phrService.factory('measurementResultService', function ($http, $q, patientManagementService) {

    return {
        /**
         * Patient情報を返却する
         * @returns {services_L22.patient|Object}
         */
        getList: function (targetDate, status, successAction, observationEventId) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetMeasurementResult";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.targetDate = phrJs.formatDate(targetDate, "yyyy/MM/dd HH:mm:ss");
            sendObject.status = status;
            sendObject.observationEventId = observationEventId;

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
    };
})