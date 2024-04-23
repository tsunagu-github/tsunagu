phrService.factory('FrailSummaryService', function ($http, $q, $window, lookupData, patientManagementService) {

    
    
    return {
        
        getFrailSummary: function(successAction) {
            
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "FrailSummaryAction";
            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);

        }
    }
      /**
         * グラフ一覧を取得する
         * @returns {data}
         */
       
        
})
