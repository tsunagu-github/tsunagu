phrService.factory('dynamicConsentInquiryService', function ($http, $q, $window, lookupData, patientManagementService) {

    return {
         /**
          * 患者名を取得する
          */
         getPatient: function (successAction) {
             var webAction = new WebAction();
             var sendObject = new Object();
             sendObject.action = "DynamicConsent";
             sendObject.type = "getName";
             sendObject.phrId = patientManagementService.getPatient().phrId;

             webAction.setSuccessAction(successAction);
             webAction.action($http, $q, sendObject);
         }
    };

})
