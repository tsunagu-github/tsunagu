phrService.factory('dynamicConsentService', function ($http, $q, $window, lookupData, patientManagementService) {

    return {
         /**
          * 画面に表示する活用同意一覧を取得する
          */
         getConsentList: function (successAction) {
             var webAction = new WebAction();
             var sendObject = new Object();
             sendObject.action = "DynamicConsent";
             sendObject.type = "getList";
             sendObject.phrId = patientManagementService.getPatient().phrId;

             webAction.setSuccessAction(successAction);
             webAction.action($http, $q, sendObject);
         }
    };

})
