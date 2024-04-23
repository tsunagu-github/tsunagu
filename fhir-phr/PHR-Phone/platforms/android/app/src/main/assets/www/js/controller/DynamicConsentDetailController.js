phrCtrl.controller("dynamicConsentDetailCtrl", function ($window, $scope, dynamicConsentDetailService, lookupData) {
    var dynamicConsentDetailResult = new Object();  // ダイナミックコンセント詳細
    $scope.dt = new Object();
    $scope.item = new Object();
    $scope.isView = "none";
    $scope.isNoView = "none";

    $scope.dynamicConsentDetailResult = dynamicConsentDetailResult;

    /**
     * 更新処理成功時のAction
     * @returns {undefined}
     **/
    var successAction = function (data) {
        logger.debug("Start");
        if (data.remarks == "UpdateError") {
            // 更新処理失敗
            logger.debug("更新処理に失敗しました。");
            alert("回答状況の更新に失敗しました。");
            for (var i = myNavigator.pages.length-1; i > 0; i--) {
                myNavigator.pages[i].remove();
            }
            myNavigator.pushPage("templates/dynamicConsent-menu.html");
        } else {
            // 更新処理成功
            logger.debug("更新処理に成功しました。");
            alert("回答状況の更新が完了しました。");
            for (var i = myNavigator.pages.length-1; i > 0; i--) {
                myNavigator.pages[i].remove();
            }
            myNavigator.pushPage("templates/dynamicConsent-menu.html");
        }
        logger.debug("End");
    }

    /**
     * 初期表示時
     **/
    ons.ready(function () {
        logger.debug("Start");
        // 一覧画面から該当のカードの情報を取得して画面にセット
        $scope.item = myNavigator.topPage.pushedOptions.item;
        dynamicConsentDetailResult.studyId = $scope.item.studyId;
        dynamicConsentDetailResult.subjectId = $scope.item.subjectId;
        dynamicConsentDetailResult.studyName = $scope.item.studyName;
        dynamicConsentDetailResult.explanation = $scope.item.explanation;
        dynamicConsentDetailResult.consentType = $scope.item.consentType;
        dynamicConsentDetailResult.responseStatus = $scope.item.responseStatus;
        dynamicConsentDetailResult.fontColor = $scope.item.fontColor;
        dynamicConsentDetailResult.notificationDate = $scope.item.notificationDate;
        dynamicConsentDetailResult.isResponseDateView = $scope.item.isResponseDateView;
        dynamicConsentDetailResult.responseDate = $scope.item.responseDate;
        dynamicConsentDetailResult.checkList = $scope.item.checkList;
        dynamicConsentDetailResult.url = $scope.item.url;

        // 同意種別によって処理を分岐
        if (dynamicConsentDetailResult.consentType == "オプトイン方式同意") {
            // 回答状況によってボタンの表示を変更する
            if (dynamicConsentDetailResult.responseStatus == "同意") {
                document.getElementById("button_1").style.display = "none";
                document.getElementById("button_2").style.display = "none";
                document.getElementById("button_3").style.display = "none";
                document.getElementById("button_4").style.display = "done";
            } else if (dynamicConsentDetailResult.responseStatus == "非同意") {
                document.getElementById("button_1").style.display = "none";
                document.getElementById("button_2").style.display = "done";
                document.getElementById("button_3").style.display = "none";
                document.getElementById("button_4").style.display = "none";
            } else if (dynamicConsentDetailResult.responseStatus == "未回答") {
                document.getElementById("button_1").style.display = "done";
                document.getElementById("button_2").style.display = "none";
                document.getElementById("button_3").style.display = "none";
                document.getElementById("button_4").style.display = "none";
            } else if (dynamicConsentDetailResult.responseStatus == "保留") {
                document.getElementById("button_1").style.display = "none";
                document.getElementById("button_2").style.display = "none";
                document.getElementById("button_3").style.display = "done";
                document.getElementById("button_4").style.display = "none";
            }

        } else if (dynamicConsentDetailResult.consentType == "オプトアウト方式同意") {
            if (dynamicConsentDetailResult.responseStatus == "同意" || dynamicConsentDetailResult.responseStatus == "未回答") {
                document.getElementById("button_1").style.display = "none";
                document.getElementById("button_2").style.display = "done";
            } else if (dynamicConsentDetailResult.responseStatus == "非同意") {
                document.getElementById("button_1").style.display = "done";
                document.getElementById("button_2").style.display = "none";
            }
        }

        $scope.dynamicConsentDetailResult = dynamicConsentDetailResult;
        logger.debug("End");
    });

    /**
     * チェックボックス押下時（オプトイン）
     **/
     $scope.allCheck = function() {
        if (document.getElementById("check").checked) {
            let target = document.getElementsByName("check");
            for (var i = 0; i < target.length; i++) {
                target[i].checked = true;
            }
        } else {
            let target = document.getElementsByName("check");
            for (var i = 0; i < target.length; i++) {
                target[i].checked = false;
            }
        }
     }

    /**
     * 保留時の問い合わせボタン押下時（オプトイン）
     **/
     $scope.openMail = function() {
        myNavigator.pushPage("templates/dynamicConsent-inquiry.html");
     }

    /**
     * 詳細説明ボタン押下時
     **/
    $scope.openExpla = function(url) {
        dynamicConsentDetailService.openExpla(url);
    }

    /**
     * 更新ボタン押下時（オプトアウト）
     **/
    $scope.update = function(data) {
        logger.debug("Start");

        var studyId = data.studyId;
        var subjectId = data.subjectId;
        var responseStatus = "";
        // 回答ステータスを取得
        if (document.getElementById("button_1").style.display == "") {
            responseStatus = "非同意";
        } else if (document.getElementById("button_2").style.display == "") {
            responseStatus = "同意";
        }
        dynamicConsentDetailService.updateOptOut(successAction, studyId, subjectId, responseStatus, "オプトアウト");

        logger.debug("End");
    }

    /**
     * 更新ボタン押下時（オプトイン）
     **/
    $scope.updateOptIn = function(data) {
        logger.debug("Start");

        // 確認項目の選択状態を取得
        var checks = [];
        var dto = document.form1.toggle;
        for (var i = 0; i < dto.length; i++) {
            if (dto[i].checked) {
                // 選択時は1をセット
                $scope.checks = checks.push("1");
            } else {
                // 未選択時は0をセット
                $scope.checks = checks.push("0");
            }
        }

//        var dynamicConsentDetailResult = $scope.dynamicConsentDetailResult;
//        for (var i = 0; i < dynamicConsentDetailResult.checkList.length; i++) {
//            var dto = dynamicConsentDetailResult.checkList[i];
//            if (dto.checked) {
//                // 選択時は1をセット
//                $scope.checks = checks.push("1");
//            } else {
//                // 未選択時は0をセット
//                $scope.checks = checks.push("0");
//            }
//        }

        var studyId = data.studyId;
        var subjectId = data.subjectId;
        var responseStatus = "";
        // 回答ステータスを取得
        if (document.getElementById("button_2").style.display == "") {
            responseStatus = "非同意";
        } else if (document.getElementById("button_3").style.display == "") {
            responseStatus = "保留";
        } else if (document.getElementById("button_4").style.display == "") {
            responseStatus = "同意";
        }
        dynamicConsentDetailService.update(successAction, studyId, subjectId, responseStatus, checks);

        logger.debug("End");
    }

    /**
     * 非同意ボタン押下時（オプトアウト画面）
     **/
    $scope.clickN = function() {
        document.getElementById("button_2").style.display = "none";
        document.getElementById("button_1").style.display = "";
    }

    /**
     * 同意ボタン押下時（オプトアウト画面）
     **/
    $scope.clickO = function() {
        document.getElementById("button_2").style.display = "";
        document.getElementById("button_1").style.display = "none";
    }

    /**
     * 非同意ボタン押下時（オプトイン画面）
     **/
    $scope.click1 = function() {
        document.getElementById("button_1").style.display = "none";
        document.getElementById("button_2").style.display = "";
        document.getElementById("button_3").style.display = "none";
        document.getElementById("button_4").style.display = "none";
    }

    /**
     * 保留ボタン押下時（オプトイン画面）
     **/
    $scope.click2 = function() {
        document.getElementById("button_1").style.display = "none";
        document.getElementById("button_2").style.display = "none";
        document.getElementById("button_3").style.display = "";
        document.getElementById("button_4").style.display = "none";
    }

    /**
     * 同意ボタン押下時（オプトイン画面）
     * 確認項目がすべて✓済の場合のみ選択できるようにする
     **/
    $scope.click3 = function() {
        let sum = 0;
        let target = document.getElementsByName("check");
            for (var i = 0; i < target.length; i++) {
                if (target[i].checked) {
                    sum++;
                }
            }
        if (sum == target.length) {
            // 全て✓済
            document.getElementById("button_1").style.display = "none";
            document.getElementById("button_2").style.display = "none";
            document.getElementById("button_3").style.display = "none";
            document.getElementById("button_4").style.display = "";
        } else {
            alert("同意するためにはすべての確認項目を選択してください。");
        }
    }

});