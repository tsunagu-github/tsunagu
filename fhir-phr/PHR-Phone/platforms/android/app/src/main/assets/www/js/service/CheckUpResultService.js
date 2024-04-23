/**
 * 健診情報取得
 * （inspectionType＝健診：3 問診：4 診察：5）
 * @param {type} param1
 * @param {type} param2
 */
phrService.factory('checkUpResultService', function($http, $q,patientManagementService){

    return {
        /**
         * 健診情報を返却する
         * @returns {services_L22.patient|Object}
         */
        getList: function (inspectionTypeList, successAction) {

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetCheckUpResult";
//            sendObject.action = "mock-checkUpResult";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.inspectionTypeList = inspectionTypeList;

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
        /**
         * 過去日・未来日の健診情報を返却する
         * @returns {services_L22.patient|Object}
         */
         getList: function (inspectionTypeList, successAction, targetDate, status) {

             var webAction = new WebAction();
             var sendObject = new Object();
             sendObject.action = "GetCheckUpResult";
             sendObject.phrId = patientManagementService.getPatient().phrId;
             sendObject.inspectionTypeList = inspectionTypeList;
             sendObject.targetDate = targetDate;
             sendObject.status = status;

             webAction.setSuccessAction(successAction);
             webAction.action($http, $q, sendObject);
        },
        /**
         * 表示されている健診結果を端末に保存する
         * @returns {services_L22.patient|Object}
         */
         save: function (inspectionTypeList, medicalCheckupId, inquiryId, examinationId, successAction, targetDate, status) {

             var webAction = new WebAction();
             var sendObject = new Object();
             sendObject.action = "GetCheckUpResult";
             sendObject.phrId = patientManagementService.getPatient().phrId;
             sendObject.inspectionTypeList = inspectionTypeList;
             sendObject.medicalCheckupId =medicalCheckupId;
             sendObject.inquiryId = inquiryId;
             sendObject.examinationId = examinationId;
             sendObject.targetDate = targetDate;
             sendObject.status = status;

             webAction.setSuccessAction(successAction);
             webAction.action($http, $q, sendObject);
         }
    };
})
