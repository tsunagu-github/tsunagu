phrCtrl.controller("clinicalTestResultCtrl", function ($window, $scope, clinicalTestResultService, lookupData) {
    var clinicalTestResult = new Object();  // 自己管理セット
    var targetDate = new Date();
    $scope.viewDataList = new Object();    
    $scope.dt = new Object();
    $scope.dt.targetDate = targetDate;
    $scope.vColorMap = lookupData.clinicalTesValueColorMap();
    $scope.dColorMap = lookupData.clinicalTesDateColorMap();
    $scope.isTableView = "none";
    $scope.isMsgView = "none";
    $scope.message = "";
    $scope.viewItem = null;
    
    clinicalTestResult.resultList = new Object();
    $scope.clinicalTestResult = clinicalTestResult;

    /**
     * 検査結果取得Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successAction = function (data) {

        clinicalTestResult = $scope.clinicalTestResult;
        $scope.viewDataList = data.viewList;
        logger.debug($scope.viewDataList);
        if($scope.viewItem === null || data.viewList.length === 1){
            $scope.viewItem =  data.viewList[0].viewId;
        }
        setselectViewList();
        setShowResult($scope.viewItem);
        
//        clinicalTestResult.diseaseDefinitionMap = data.diseaseDefinitionMap;
//        clinicalTestResult.resultList = data.resultList;
//        if (clinicalTestResult.diseaseTypeList == null ||
//                clinicalTestResult.diseaseTypeList == "undefined") {
//            clinicalTestResult.diseaseTypeList = data.diseaseTypeList;
//            clinicalTestResult.diseaseTypeList2 = [];
//            for (var i = 1; i < data.diseaseTypeList.length; i++) {
//                clinicalTestResult.diseaseTypeList2[clinicalTestResult.diseaseTypeList2.length] = data.diseaseTypeList[i];
//            }
//        }
//        var list = createViewList(clinicalTestResult, $scope);
//        clinicalTestResult.viewList = list;
//        viewControl(list);
//        $scope.clinicalTestResult = clinicalTestResult;
        $scope.pastDate = data.pastDate;
        $scope.futureDate = data.futureDate;

    };

    function setShowResult(viewId){
        clinicalTestResult = new Object;
        for(var i=0;i<$scope.viewDataList.length;i++){
            if(viewId === $scope.viewDataList[i].viewId){
                clinicalTestResult.diseaseDefinitionMap = $scope.viewDataList[i].diseaseDefinitionMap;       
                clinicalTestResult.resultList = $scope.viewDataList[i].resultList;
                if (clinicalTestResult.diseaseTypeList == null ||
                        clinicalTestResult.diseaseTypeList == "undefined") {
                    clinicalTestResult.diseaseTypeList = $scope.viewDataList[i].diseaseTypeList;
                    clinicalTestResult.diseaseTypeList2 = [];
                    clinicalTestResult.diseaseTypeList3 = [];
                    clinicalTestResult.diseaseTypeList4 = [];
                    for (var k = 1; k < $scope.viewDataList[i].diseaseTypeList.length; k++) {
                        for (var k = 1; k < 4; k++) {
                            clinicalTestResult.diseaseTypeList2[clinicalTestResult.diseaseTypeList2.length] = $scope.viewDataList[i].diseaseTypeList[k];
                        }
                        for (var k = 4; k < 7; k++) {
                            clinicalTestResult.diseaseTypeList3[k-4] = $scope.viewDataList[i].diseaseTypeList[k];
                        }
                    }
                    var list = createViewList(clinicalTestResult, $scope);
                    clinicalTestResult.viewList = list;
                    viewControl(list);
                    clinicalTestResult.button = "次の項目へ";
                    $scope.clinicalTestResult = clinicalTestResult;
                }
            }
        }
    }

    /**
     * 次の項目ボタンを押下した時のアクション
     */
    $scope.change = function () {
        var clinicalTestResult = $scope.clinicalTestResult;
        if (clinicalTestResult.button == "次の項目へ") {
            clinicalTestResult.button = "前の項目へ";
            clinicalTestResult.diseaseTypeList4 = clinicalTestResult.diseaseTypeList2;
            clinicalTestResult.diseaseTypeList2 = clinicalTestResult.diseaseTypeList3;
        } else if (clinicalTestResult.button == "前の項目へ") {
            clinicalTestResult.button = "次の項目へ";
            clinicalTestResult.diseaseTypeList3 = clinicalTestResult.diseaseTypeList2;
            clinicalTestResult.diseaseTypeList2 = clinicalTestResult.diseaseTypeList4;
        }
        $scope.clinicalTestResult = clinicalTestResult;
    }

    /**
     * 疾病チェック時のアクション
     * @returns {undefined}
     */
    $scope.checkedAction = function () {
        var clinicalTestResult = $scope.clinicalTestResult;
        var list = createViewList(clinicalTestResult, $scope);
        clinicalTestResult.viewList = list;
        viewControl(list);
        $scope.clinicalTestResult = clinicalTestResult;
    };

    /**
     * 対象日変更時のアクション
     * @returns {undefined}
     */
    $scope.changeTargetDate = function () {
        clinicalTestResultService.getList($scope.dt.targetDate, successAction);
    };

    /**
     * 過去日付移動アクション
     * @returns {undefined}
     */
    $scope.movePastDate = function () {
        var targetDate = new Date($scope.pastDate);
        $scope.dt.targetDate = targetDate;
        clinicalTestResultService.getList($scope.dt.targetDate, successAction);
    };
    /**
     * 未来日付移動アクション
     * @returns {undefined}
     */
    $scope.moveFutureDate = function () {
        var targetDate = new Date($scope.futureDate);
        $scope.dt.targetDate = targetDate;
        clinicalTestResultService.getList($scope.dt.targetDate, successAction);
    };
    $scope.ngRepeatFinished = function () {
        //if ($window.pageYOffset > 0) {
        //    $scope.fixed = "position: fixed;";
        //} else {
        //    $scope.fixed = "";
        //}
    };

    ons.ready(function () {
        clinicalTestResultService.getList(targetDate, successAction);
    });

    /**
     * 検査結果をチェック項目に合わせて変更する
     * @param {type} clinicalTestResult
     * @returns {Array}
     */
    function createViewList(clinicalTestResult, $scope) {
        var list = [];
        for (var i = 0; i < clinicalTestResult.diseaseTypeList.length; i++) {
            var dto = clinicalTestResult.diseaseTypeList[i];
            if (dto.checked) {
                list[list.length] = dto.key;
            }
        }
        if (list.length == 0) {
            list[list.length] = "0";
        }
        var definitionList = [];
        for (var i = 0; i < list.length; i++) {
            var idList = clinicalTestResult.diseaseDefinitionMap[list[i]];
            if (idList != null) {
                for (var j = 0; j < idList.length; j++) {
                    if (definitionList.indexOf(idList[j]) == -1) {
                        definitionList[definitionList.length] = idList[j];
                    }
                }
            }
        }

        var resultList = [];
        if (clinicalTestResult.resultList != null) {
            for (var i = 0; i < clinicalTestResult.resultList.length; i++) {
                if (definitionList.indexOf(clinicalTestResult.resultList[i].id) >= 0) {
                    resultList[resultList.length] = clinicalTestResult.resultList[i];
                }
            }
        }
        return resultList;
    }
    ;

    function viewControl(list) {
        if (list.length == 0) {
            $scope.message = "表示データがありません";
            $scope.isTableView = "none";
            $scope.isMsgView = "block";
        } else {
            $scope.message = "";
            $scope.isTableView = "block";
            $scope.isMsgView = "none";
        }
    }
    ;
    $scope.changeView = function(item) {
        if(item==null){
        }else{
          //-> 選択されたアイテムの値
          $scope.viewItem = item;
          setShowResult(item);
        }
     } ;    

     function setselectViewList(){
         $scope.viewItemList = [];
         var setid = 0;
         for(var i=0;i<$scope.viewDataList.length;i++){
            $scope.viewItemList.push({id:$scope.viewDataList[i].viewId, name:$scope.viewDataList[i].viewName});
            if($scope.viewItem === $scope.viewItemList[i].id){
                setid = i;
            }
        }
        $scope.viewItem = $scope.viewItemList[setid].id;
     };  
});