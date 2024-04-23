phrCtrl.controller("oneTimePasswordCtrl",function( $scope,oneTimePasswordService, patientManagementService ){
    logger.debug("oneTimePassword Start");

    $scope.oneTimePassword = oneTimePasswordService.createOneTimePassword();

    /**
     * ワンタイムパスワードを取得できたとき
     * @param {type} data
     * @returns {undefined}
     */
    var successAction = function (data) {
        oneTimePasswordService.setOneTimePassword( patientManagementService.getPatient().phrId, data.oneTimePassword, data.expirationDate );
    };

    /**
     * ワンタイムパスワードを送信できたとき
     * @param {type} data
     * @returns {undefined}
     */
    var successActionSent = function () {
	document.getElementById("sendOneTimePassword").style.display = "none";
        document.getElementById("sentOneTimePassword").style.display = "block";
    };

    $scope.goToOneTimePasswordConfirm = function() {
        // memo
        // 処理成功時にコールバックでsuccessActionを実行する
        oneTimePasswordService.getOneTimePassword( successAction ); 
        myNavigator.pushPage( "templates/onetimepassword-confirm.html" );
    };

    $scope.goToMainMenu = function() {
        myNavigator.resetToPage("templates/menu-list.html", {animation:"slide"});
    };

    $scope.sendOneTimePassword = function() {
        ons.notification.confirm({
            title: '確認',
            message: '保険者（窓口）へパスワード情報を通知します。\n\
よろしいですか。',
            buttonLabels: ['はい', 'いいえ'],
            //modifier: mod,
            callback: function (idx) {
                switch (idx) {
                case 0://　はい→送信
                    oneTimePasswordService.sendOneTimePassword( successActionSent ); 
                    break;
                case 1://　いいえ
                    break;
                }
            }
        });
    };
    
    ons.ready(function() {
        var target = document.getElementById("sentOneTimePassword");
        if( target !== null ){
            target.style.display = "none";
        }
    });

    logger.debug("oneTimePassword End");
    
});
