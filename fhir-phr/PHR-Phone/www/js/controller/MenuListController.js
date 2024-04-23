phrCtrl.controller("menuListCtrl", function($scope, $window, $http, $q, patientManagementService){
    $scope.patient = patientManagementService.getPatient();
    $scope.isDevelopMode = isDevelopMode;
    app.slidingMenu.setSwipeable(false);
    $scope.service = phrJs;
     
    /**
     * 画面の初期化
     * @returns {undefined}
     */
    $scope.initPage = function () {

    };
    /**
     * 画面の初期化
     * @returns {undefined}
     */
    $scope.getUnReadCount = function () {
        try {
            return $scope.service.getUnReadCount();
        } catch (e) {
            
        }
    };
        
    /**
     * 指定したページに遷移する
     * @param {type} page
     * @returns {undefined}
     */
    $scope.setPage = function(page) {
        logger.debug('setPage ' + page);
        // リセットページで該当ページに遷移した後、スタックの最初にメニューを入れる

        myNavigator.resetToPage(page, { animation: "none" }).then(function() {
            myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" });
        });

//        myNavigator.resetToPage(page, { animation: "slide" }).then(function() {
//            // メニューの場合は無視する
//            if (page != "templates/menu-list.html") {
//                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" });
//            }
//        });

        app.slidingMenu.closeMenu();
    };

    /**
     * MySOS起動ボタン時のアクション
     * @returns {undefined}
     */
    $scope.mySOSAction = function () {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "EncryptIdAction";
            sendObject.phrId = $scope.patient.phrId;
            sendObject.patientDto = $scope.patient;

            webAction.setSuccessAction(successAction, "3");
            webAction.action($http, $q, sendObject);
            
    };
    /**
     * MySOS起動ボタン時のアクション
     * @returns {undefined}
     */
    $scope.mircaAction = function () {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "EncryptIdAction";
            sendObject.phrId = $scope.patient.phrId;
            sendObject.patientDto = $scope.patient;

            webAction.setSuccessAction(successAction, "1");
            webAction.action($http, $q, sendObject);
            
    };
    /**
     * 利用者更新Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successAction = function (data, status) {
        
        if (data.status === "0") {
            var pid = escape(data.encId);
            $window.handleOpenURL = function(url) {
                
            }
            launchAndroidApp(pid, status);
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    
    function launchAndroidApp(pid, status) {
    window.plugins.webintent.startActivity({
        action: window.plugins.webintent.ACTION_VIEW,
        url: location.href = "net.allm.MySOS://Medis?p1=" + pid + "&status=" + status
        },
        function() {},
        function() {
            console.log('Failed to open App');
            launchAndroidStore(appinfo);
            }
    );
}

})