phrCtrl.controller("phrStartCtrl",function( $window, $scope, phrStartService, lookupData, tarminalService ){
    logger.debug("phrStartCtrl Start");

    $scope.takeOver = phrStartService.createTakeOver();
    $scope.rules = phrStartService.getRules();
    $scope.patient = phrStartService.getPatient();
    $scope.tarminalInfo = tarminalService.getTarminalInfo();
    
    // 画面表示に必要な情報を作成
    $scope.sexCodeMap = lookupData.sexCodeMap();
    $scope.prefectureList = lookupData.prefectureList();
    
    $scope.goToPhrStartPhridView = function (){
        phrStartService.createPatient($scope.patient);
    };
    
    $scope.goToPhrStartPhridConfirm = function (){
        // 郵便番号の整形
        phrStartService.setPatientZipCode($scope.patient);
        // 都道府県名の設定
        phrStartService.setPatientViewPrefecture( $scope.patient );
         // 生年月日の設定
        phrStartService.setBirthDate($scope.patient);     
        
        myNavigator.pushPage('templates/phrstart-phrid-confirm.html',{});
    };
    
    $scope.goToPhrIdEntry = function (){
        myNavigator.pushPage('templates/phrstart-phrid-entry.html',{});
    };
    
    $scope.goToMainMenu = function() {
        // ここは新規PHRID発行後なのでロケーションで飛ばす！
        window.location.href = "index.html"
        //myNavigator.resetToPage("templates/menu-list.html", {animation:"slide"});
    };
    
    var successAction = function (data) {
        logger.debug("取得結果");
        logger.debug(JSON.stringify(data));
       
        // PHRIDとkeyIDを書き込む
        window.localStorage.setItem("PHR_ID", data.phrId);
        //window.localStorage.setItem("LAST_APP_KEY",window.localStorage.getItem("APP_KEY") );
        window.localStorage.setItem("APP_KEY", data.keyId);
        myNavigator.pushPage("templates/phrstart-takeover-resultok.html" );
    };

    $scope.goToTakeOverResult = function() {
        phrStartService.getTakeOver( successAction ); 
    };

    $scope.goToRulesView = function() {
        myNavigator.pushPage("templates/phrstart-rules-view.html" );
    };

    $scope.goToRulesViewDynamic = function() {
        myNavigator.pushPage("templates/phrstart-rules-view-dynamic.html" );
    };

    $scope.goToTakeOverInput = function() {
        myNavigator.pushPage("templates/phrstart-takeover.html" );
    };

    $scope.backToTakeOverInput = function() {
        myNavigator.popPage( );
    };

    $scope.backToPhrIdEntry = function() {
        myNavigator.popPage( );
    };

    $scope.backToStart = function() {
        myNavigator.popPage( );
    };

    $scope.backToRulesView = function() {
        myNavigator.popPage( );
    };

    /**
     * 利用規約同意ボタン押下時
     **/
    $scope.clickToggle = function() {
        var toggle = document.getElementById("toggle");
        var target = document.getElementById("goToTakeOverInput");
        if (toggle.checked) {
            target.disabled = false;
        } else {
            target.disabled = true;
        }
    }
    
    logger.debug("phrStartCtrl End");
});
