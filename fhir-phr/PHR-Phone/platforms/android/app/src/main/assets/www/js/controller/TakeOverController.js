phrCtrl.controller("takeOverCtrl",function( $scope,takeOverService, patientManagementService ){
    logger.debug("takeOverCtrl Start");

    $scope.takeOver = takeOverService.createTakeOver();

    /**
     * テイクオーバーコードを取得できたとき
     * @param {type} data
     * @returns {undefined}
     */
    var successAction = function (data) {
        logger.debug(JSON.stringify(data));
        takeOverService.setTakeOver( patientManagementService.getPatient().phrId, data.takeOverCode, null, data.expirationDate );
        myNavigator.pushPage( "templates/takeover-confirm.html" );
    };

    $scope.goToTakeoverConfirm = function() {
        takeOverService.getTakeOver( successAction ); 
    };

    $scope.goToMainMenu = function() {
        takeOverService.setTakeOver( null, "", "", null );
        myNavigator.resetToPage("templates/menu-list.html", {animation:"slide"});
    };

    logger.debug("takeOverCtrl End");

});
