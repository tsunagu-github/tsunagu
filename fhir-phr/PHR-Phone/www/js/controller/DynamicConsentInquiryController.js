phrCtrl.controller("dynamicConsentInquiryCtrl", function ($window, $scope, dynamicConsentInquiryService, lookupData) {
    var dynamicConsentInquiryResult = new Object();  // ダイナミックコンセント（保留時の問い合わせ）
    $scope.dt = new Object();

    $scope.dynamicConsentInquiryResult = dynamicConsentInquiryResult;

    /**
     * 検査結果取得Server接続処理成功時のAction
     * @returns {undefined}
     **/
    var successAction = function (data) {
        logger.debug("Start");
        if (data.remarks == null) {
            // 正常時
            var textBox = document.getElementById("patientName");
            textBox.value = data.patientName;
        } else if (data.remarks == "Error") {
            // エラー時
            logger.debug("エラーが発生しました。");
            alert("エラーが発生しました。");
        }
        logger.debug("End");
    }

    /**
     * 初期表示時
     **/
    ons.ready(function () {
        dynamicConsentInquiryService.getPatient(successAction);
    });

});