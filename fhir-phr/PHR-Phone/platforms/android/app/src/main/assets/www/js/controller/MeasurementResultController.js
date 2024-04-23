phrCtrl.controller("measurementResultCtrl", function ($window, $scope, measurementResultService, lookupData) {
    var measurementResult = new Object();  // 自己管理セット
    var targetDate = new Date();
    $scope.viewDataList = new Object();
    $scope.dt = new Object();
    $scope.dt.targetDate = targetDate;
    $scope.isTableView = "none";
    $scope.isMsgView = "none";
    $scope.message = "";
    $scope.viewItem = null;
    $scope.status = null;

    measurementResult.resultList = new Object();
    $scope.measurementResult = measurementResult;

    /**
     * 検査結果取得Server接続処理成功時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successAction = function (data) {

        measurementResult = $scope.measurementResult;
        if (data.viewList != null) {
            $scope.viewDataList = data.viewList[0];
            if (data.title != null) {
                $scope.title = data.title;
            }
            if (data.backgroundColor != null) {
                $scope.backgroundColor = data.backgroundColor;
            }
            $scope.observationEventId = data.observationEventId;
            setShowResult($scope.viewDataList, $scope.title, $scope.backgroundColor, $scope.observationEventId) ;
            $scope.pastDate = data.pastDate;
            $scope.futureDate = data.futureDate;
        } else {
            errorView();
        }
    };

    function setShowResult(view, title, backgroundColor, observationEventId){
        measurementResult = new Object;
        measurementResult.viewList = view.resultList;
        measurementResult.title = title;
        measurementResult.backgroundColor = backgroundColor;
        measurementResult.observationEventId = observationEventId;
        viewControl(view);
        $scope.measurementResult = measurementResult;
    }

    /**
     * 未来日付移動アクション
     * @returns {undefined}
     */
    $scope.moveFutureDate = function () {
        var targetDate = new Date($scope.futureDate);
        $scope.dt.targetDate = targetDate;
        $scope.status = "future";
        var id = document.getElementById("obEvId").innerText;
        $scope.observationEventId = id;
        measurementResultService.getList($scope.dt.targetDate, $scope.status, successAction, $scope.observationEventId);
    };

    /**
     * 過去日付移動アクション
     * @returns {undefined}
     */
    $scope.movePastDate = function () {
        var targetDate = new Date($scope.pastDate);
        $scope.dt.targetDate = targetDate;
        $scope.status = "past";
        var id = document.getElementById("obEvId").innerText;
        $scope.observationEventId = id;
        measurementResultService.getList($scope.dt.targetDate, $scope.status, successAction, $scope.observationEventId);
    };

    $scope.ngRepeatFinished = function () {
        //if ($window.pageYOffset > 0) {
        //    $scope.fixed = "position: fixed;";
        //} else {
        //    $scope.fixed = "";
        //}
    };

    ons.ready(function () {
        measurementResultService.getList(targetDate, $scope.status, successAction);
    });

    function viewControl(list) {
        if (list.resultList.length == 0) {
            $scope.message = "表示データがありません";
            $scope.isTableView = "none";
            $scope.isMsgView = "block";
            measurementResult.title = null;
            measurementResult.backgroundColor = null;
        } else {
            $scope.message = "";
            $scope.isTableView = "block";
            $scope.isMsgView = "none";
        }
    };

    function errorView() {
        $scope.message = "表示データがありません";
        $scope.isTableView = "none";
        $scope.isMsgView = "block";
        measurementResult.title = null;
        measurementResult.backgroundColor = null;
    };
});