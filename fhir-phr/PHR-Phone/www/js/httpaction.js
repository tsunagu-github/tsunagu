/**
 * AMED-PHR Webアクションスクリプト
 */
function WebAction() {
    /**
     * 成功時アクション
     */
    this.successAction = null;
    this.setSuccessAction = function (action) {
        this.successAction = action;
    };
    this.errorAuthAction = function (data) {
        var msg1 = data.message;
        errorMsg = msg1;
        window.location = "auth-error.html?msg1=" + encodeURI(msg1);
/*
        myNavigator.pushPage("templates/error.html").catch(
            function (data){
                window.location = "error.html?msg1=" + encodeURI(msg1);
            }
        );
*/
    };
    this.errorAction = function (data) {
        var msg1 = encodeURI("サーバ処理でエラーが発生しました");
        var msg2 = data.message;
        errorMsg = msg2;
        myNavigator.pushPage("templates/error.html").catch(
            function (data){
                window.location = "error.html?msg1=" + msg1 + "&msg2=" + encodeURI(msg2);
            }
        );
    };
    /**
     * 致命的なエラー時アクション
     */
    this.fatalAction = function (data) {
        var msg1 = "サーバとの接続に失敗しました";
        var msg2 = data.message;
        errorMsg = msg2;
        myNavigator.pushPage("templates/error.html").catch(
            function (data){
                window.location = "error.html?msg1=" + encodeURI(msg1);
            }
        );
    };
    this.setFatalActionon = function (action) {
        this.fatalAction = action;
    };

    this.action = function ($http, $q, sendObject) {
        var deferred = $q.defer();
        var successAction = this.successAction;
        var errorAction = this.errorAction;
        var errorAuthAction = this.errorAuthAction;
        var fatalAction = this.fatalAction;
        var url = "";
        //
        var modal = document.getElementById('modal');
        if (modal != null && modal != "undefaind") {
            modal.show();
        }
        
        // 認証、PHRID発行、データ引継ぎ以外は認証情報を付加する
        if (sendObject.action != "TariminalAuthAction" 
                && sendObject.action != "CreatePatienAction"
                && sendObject.action != "EntryTakeOverCode") {
            var authInfo = phrJs.createAuthenticationInfo();
            sendObject.timestamp = authInfo.timestamp;
            sendObject.keyValue = authInfo.keyValue;
        }
        if (sendObject.version == null || sendObject.version == "undefaind") {
            sendObject.version = appIFVersion;
        }
        logger.debug(sendObject);
        if (isDevelopMode === 3) {
            url = serverUrl + sendObject.action + ".json";
            $http.get(url, {}).then(
                /**
                 * 成功時の処理
                 * @param {type} data
                 * @returns {undefined}
                 */
                function (data)
                {
                    deferred.resolve(data);
                },
                /**
                 * 失敗時の処理
                 * @param {type} data
                 * @returns {undefined}
                 */
                function (data)
                {
                    deferred.resolve(data);
                }
            );
        } else {
            url = serverUrl;
            //$http.post(url, "DATA=" + JSON.stringify(sendObject, null, "  ")
            $http(
                {
                method: 'POST',
                url: url,
                headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                timeout: 100000,
                data: sendObject
                }
            ).then(
                    /**
                     * 成功時の処理
                     * @param {type} data
                     * @returns {undefined}
                     */
                    function (data)
                    {
                        deferred.resolve(data);
                    },
                    /**
                     * 失敗時の処理
                     * @param {type} data
                     * @returns {undefined}
                     */
                    function (data)
                    {
                        deferred.resolve(data);
                    }
            );
        }
     
        var promise = deferred.promise;
        promise.then(function (data)
        {
            if (modal != null && modal != "undefaind") {
                modal.hide();
            }
            if (data.status == 200) {
                var resData = data.data;
                logger.warn(JSON.stringify(resData, null, "  "));
                logger.debug(resData);
                if (resData.status == "8") {
                    errorAuthAction(resData);
                } else if (resData.status == "0") {
                    if (resData.unreadMessageCount != null && resData.unreadMessageCount != "undefined") {
                        try {
                            phrJs.setUnReadCount(resData.unreadMessageCount);
                        } catch(e) {
                            logger.debug(JSON.stringify(e, null, "  "));
                        }
                    }
                    successAction(resData);
                } else {
                    errorAction(resData);
                }
            } else {
                logger.error(data);
                fatalAction(data);
            }

        });
    };
}



