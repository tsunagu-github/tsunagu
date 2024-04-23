phrCtrl.controller("errorCtrl", function ($scope, $window) {
    $scope.errorMsg = errorMsg;
    
    /**
     * スタートページにリダイレクトします
     * @returns {undefined}
     */
    $scope.redirectStartPage = function () {
        $window.location.href = "index.html";
    };
    /**
     * ヒストリーばｃｋ
     * @returns {undefined}
     */
    $scope.backAction = function () {
        //myNavigator.resetToPage("templates/index.html");
        //myNavigator.popPage();
    };
})
