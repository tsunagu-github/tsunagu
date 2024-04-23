phrCtrl.controller("parientCtrl", function ($scope, patientManagementService, lookupData) {
    logger.debug("parientCtrl Start");
    
    // サービスより利用者情報を取得し画面に必要な情報を設定する
    $scope.patient = patientManagementService.getViewPatient();

    // 画面表示に必要な情報を作成
    $scope.sexCodeMap = lookupData.sexCodeMap();
    $scope.prefectureList = lookupData.prefectureList();
    /**
     * 確認ボタン時のアクション
     * @returns {undefined}
     */
    $scope.confirmAction = function () {

        // 郵便番号の整形
        patientManagementService.setZipCode($scope.patient);
        // 都道府県名の設定
        patientManagementService.setPrefectureName($scope.patient);
        // 生年月日の設定
        patientManagementService.setBirthDate($scope.patient);

        myNavigator.pushPage("templates/patient-info-confirm.html");
    };
    
    /**
     * 修正ボタン時のアクション
     * @returns {undefined}
     */
    $scope.updateAction = function () {
        patientManagementService.setPatient($scope.patient);
        patientManagementService.updatePatient();
        ons.notification.confirm({
            title: 'おしらせ',
            message: '完了しました。',
            buttonLabels: ['確認']
        });

    };
    /**
     * 戻るボタン時のアクション
     * @returns {undefined}
     */
    $scope.backMenuAction = function () {
        myNavigator.popPage();
    };    
    /**
     * 戻るボタン時のアクション
     * @returns {undefined}
     */
    $scope.backAction = function () {
        patientManagementService.rollbackPatient();
    };

    /**
     * 退会ボタン押下時のアクション
     * @return {undefined}
     */
    $scope.withDraw = function (data) {
        // ダイナミックコンセント利用フラグがtrueの時
        if (data.dynamicConsentFlg) {
            myNavigator.pushPage("templates/patient-info-withdraw.html");
        } else {
            alert("すでにダイナミックコンセントの利用は停止されています。");
        }
//        patientManagementService.withDraw();
    }

    /**
     * 退会実行ボタン押下時
     */
    $scope.deleteAction = function() {
        patientManagementService.withDraw();
    }

    /**
     * 退会キャンセルボタン押下時
     */
    $scope.deleteCancel = function(id) {
        myNavigator.popPage();
    }
    
    logger.debug("parientCtrl End");
})