/**
 * グラフリスト画面のコントローラー
 * @param {type} param1
 * @param {type} param2
 */
phrCtrl.controller("frailSummary", function($scope, $window, $http, $q, patientManagementService,FrailSummaryService){
    $scope.patient = patientManagementService.getPatient();    
    $scope.isDevelopMode = isDevelopMode;
    
     /**
     * グラフリスト取得成功時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successAction = function (data) {
        if (data.status === "0") {
            $scope.data = data;
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };   
    /**
     * グラフ表示画面の初期化
     * @returns {undefined}
     */
    $scope.init = function () {
        FrailSummaryService.getFrailSummary(successAction);
    };
    
})