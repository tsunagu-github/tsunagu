/**
 * 
 * 各種コントローラー、グローバル変数を定義したファイル
 */

/**
 * 開発モード
 * 0：本番環境実行モード
 * 1：検証環境実行モード
 * 2：IDE開発モード(IDE Web利用)
 * 3：IDE開発モード(Mock利用)
 */
var isDevelopMode = 2;

/**
 * アプリケーションI/Fバージョン
 */
var appIFVersion = "1.1";

/**
 * ServerURL
 * @type String
 */
var serverUrl = "";
if (isDevelopMode === 3) {
    serverUrl = "mock-data/";
} else if (isDevelopMode === 2) {
    serverUrl = "http://localhost:8080/PHR-Web/phoneWebInterface";
    //serverUrl = "http://10.0.2.2:8084/PHR-Web/phoneWebInterface";/* androidエミュレータからlocalhostへの接続 */
} else if (isDevelopMode === 1) {
    serverUrl = "https://test.j-phr.jp/PHR-Web/phoneWebInterface";
} else if (isDevelopMode === 0) {
    serverUrl = "https://www.j-phr.jp/PHR-Web/phoneWebInterface";
}

/**
 * アプリケーション変数
 */
var errorMsg;
var app = angular.module('phrApp', ['onsen', 'phr.controllers', 'phr.services'])
    /**
     * 共通エラーハンドリング
     * @param {type} $provide
     * @returns {undefined}
     */
    .config(function ($provide) {
        $provide.decorator('$exceptionHandler', ['$log', '$delegate',
            function ($log, $delegate) {
                return function (exception, cause) {
                    logger.error(exception);
                    errorMsg = exception.message;
                    myNavigator.pushPage("templates/error.html");
                };
            }
        ]);
    })
    .run(function(){
       ons.ready(function(){
         ons.createPopover('templates/notification-popover.html')
            .then(function(notifpopover) {
                //グローバルな通知Popoverの作成
                notifPop = notifpopover;
         });
         //Push通知での端末トークンをFCMから取得
         if(ons.platform.isWebView()) {
            var localtoken = localStorage.getItem("TOKEN_ID");
            //LocalStorageのトークンが空の場合（DB登録済みの場合registerd、サーバー側で削除があった場合null）
            if(!localtoken) {
              //FCMPlugin.getToken( successCallback(token), errorCallback(err) );
              //Keep in mind the function will return null if the token has not been established yet.
              FCMPlugin.getToken(
                function (token) {
                    if(token) {
                        localStorage.setItem("TOKEN_ID", token);
                        //alert('tokenが更新されました: ' + token);
                    }
                },
                function (err) {
                    logger.error('error retrieving token: ' + err);
                }
              );
            }
            //Push通知届いた時の処理を登録
            //FCMPlugin.onNotification( onNotificationCallback(data), successCallback(msg), errorCallback(err) )
            //Here you define your application behaviour based on the notification data.
            FCMPlugin.onNotification(
                function(data){
                    if(data.wasTapped){
                        //Notification was received on device tray and tapped by the user.
                        //alert( JSON.stringify(data) );
                        if(data.communication_id) {
                            var item = {
                              id: data.communication_id,
                              onNotification: true
                            };
                            myNavigator.pushPage("templates/announcements-details.html", {item: item});
                        }
                    }else{
                        //アプリがforegroundの場合、popoverを表示
                        if(data.communication_id) {
                            notifPop.show(".navigation-bar__left");
                        }
                    }
                },
                function(msg){
                    logger.debug('onNotification callback successfully registered: ' + msg);
                },
                function(err){
                    logger.error('Error registering onNotification callback: ' + err);
                }
            );
            //新しいトークンが発行された時の処理を登録
            //FCMPlugin.onTokenRefresh( onTokenRefreshCallback(token) );
            //Note that this callback will be fired everytime a new token is generated, including the first time.
            FCMPlugin.onTokenRefresh(function(token){
                localStorage.setItem("TOKEN_ID", token);
            });
         }
       });
    });


/**
 * コントローラー変数
 */
var phrCtrl = angular.module('phr.controllers', []);
/**
 * サービス変数
 */
var phrService = angular.module('phr.services', []);

/**
 * アプリケーションコントローラー
 */
app.controller('appCtrl', function ($scope, $http, $window, tarminalService) {
        var tarminalInfo = tarminalService.getTarminalInfo();

        if (tarminalInfo == null || tarminalInfo.phrId == null) {
            $window.location.href = "start.html";
        } else {
            tarminalService.authenticationTarminal();
		}
});
/**
 * アプリケーションコントローラー
 */
phrCtrl.controller('manuCtrl', function ($scope, $window, $http, tarminalService) {
    $scope.isDevelopMode = isDevelopMode;
});
/**
 * アプリケーションコントローラー
 */
app.controller('startCtrl', function ($scope, $window, $http, tarminalService) {

});
