phrCtrl.controller("dynamicConsentCtrl", function ($window, $scope, dynamicConsentService, lookupData) {
    var dynamicConsentResult = new Object();  // ダイナミックコンセント
    $scope.dt = new Object();
    $scope.isView = "none";
    $scope.isNoView = "none";

    $scope.dynamicConsentResult = dynamicConsentResult;

    /**
     * 検査結果取得Server接続処理成功時のAction
     * @returns {undefined}
     **/
    var successAction = function (data) {
        logger.debug("Start");
        if (data.remarks == null) {
            // 同意一覧が1件以上存在するとき
            $scope.isView = "block";
            // 研究ID順
            dynamicConsentResult.viewList = data.viewList;
            dynamicConsentResult.viewList2 = data.viewList2;
            // 通知順
            dynamicConsentResult.viewList3 = data.viewList3;
            dynamicConsentResult.viewList4 = data.viewList4;
            // 更新順
            dynamicConsentResult.viewList5 = data.viewList5;
            dynamicConsentResult.viewList6 = data.viewList6;
            // 初期表示
            dynamicConsentResult.view = data.viewList;
            dynamicConsentResult.color = "#0000cd";
            dynamicConsentResult.color2 = "black";
            dynamicConsentResult.color3 = "black";
            $scope.dynamicConsentResult = dynamicConsentResult;
        } else if (data.remarks == "NoData") {
            // 表示する同意一覧が存在しないとき
            $scope.isNoView = "block";
            logger.debug("活用同意一覧が存在しません。");
        }
        logger.debug("End");
    }

    /**
     * 「研究ID順」タップ時
     **/
    $scope.change = function (data) {
        // 文字色変更
        dynamicConsentResult.color = "#0000cd"
        dynamicConsentResult.color2 = "black";
        dynamicConsentResult.color3 = "black";
        // 表示リストの切り替え
        var check = document.getElementById("idList").checked;
        if (check) {
            dynamicConsentResult.view = data.viewList;
        } else {
            dynamicConsentResult.view = data.viewList2;
        }
        $scope.dynamicConsentResult = dynamicConsentResult;
        // 他のチェックを外す
        var check2 = document.getElementById("notificationList").checked;
        var check3 = document.getElementById("updateList").checked;
        if (check2) {
            check2 = false;
        }
        if (check3) {
            check3 = false;
        }
    }

    /**
     * 「通知順」タップ時
     **/
    $scope.change2 = function (data) {
    　　// 文字色変更
        dynamicConsentResult.color = "black"
        dynamicConsentResult.color2 = "#0000cd";
        dynamicConsentResult.color3 = "black";
        // 表示リストの切り替え
        var check2 = document.getElementById("notificationList").checked;
        if (check2) {
            dynamicConsentResult.view = data.viewList3;
        } else {
            dynamicConsentResult.view = data.viewList4;
        }
        $scope.dynamicConsentResult = dynamicConsentResult;
        // 他のチェックを外す
        var check = document.getElementById("idList").checked;
        var check3 = document.getElementById("updateList").checked;
        if (check) {
            check = false;
        }
        if (check3) {
            check3 = false;
        }
    }

    /**
     * 「更新日順」タップ時
     **/
    $scope.change3 = function (data) {
    　　// 文字色変更
        dynamicConsentResult.color = "black"
        dynamicConsentResult.color2 = "black";
        dynamicConsentResult.color3 = "#0000cd";
        // 表示リストの切り替え
        var check3 = document.getElementById("updateList").checked;
        if (check3) {
            dynamicConsentResult.view = data.viewList5;
        } else {
            dynamicConsentResult.view = data.viewList6;
        }
        $scope.dynamicConsentResult = dynamicConsentResult;
        // 他のチェックを外す
        var check = document.getElementById("idList").checked;
        var check2 = document.getElementById("notificationList").checked;
        if (check) {
            check = false;
        }
        if (check2) {
            check2 = false;
        }
    }

//    /**
//     * タブクリック時
//     **/
//    document.addEventListener('postchange', function(event) {
//        // タブの色を変更する
//        var index = tabbar.getActiveTabIndex();
//        document.getElementById(index).style.color = "#0000cd";
//        for (var i = 0; i < 3; i++) {
//            if (i != index) {
//                document.getElementById(i).style.color = "#000000";
//            }
//        }
//    });

    /**
     * 初期表示時
     **/
    ons.ready(function () {
        dynamicConsentService.getConsentList(successAction);
    });

    /**
     * 詳細ボタンクリック時
     **/
    $scope.click = function(dto) {
        logger.debug("Start");
        var item = {
            studyId: dto.studyId,
            subjectId: dto.subjectId,
            studyName: dto.studyName,
            explanation: dto.explanation,
            consentType: dto.consentType,
            responseStatus: dto.responseStatus,
            fontColor: dto.fontColor,
            notificationDate: dto.notificationDate,
            responseDate: dto.responseDate,
            isResponseDateView: dto.isResponseDateView,
            checkList: dto.checkList,
            url: dto.url
        };
        // 回答種別によって処理を分岐
        if (dto.consentType == "オプトイン方式同意") {
            myNavigator.pushPage("templates/dynamicConsent-optin.html", {item: item});
        } else if (dto.consentType == "オプトアウト方式同意") {
            myNavigator.pushPage("templates/dynamicConsent-optout.html", {item: item});
        }
        logger.debug("End");
    };

});