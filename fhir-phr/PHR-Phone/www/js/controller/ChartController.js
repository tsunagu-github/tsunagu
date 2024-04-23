/**
 * グラフリスト画面のコントローラー
 * @param {type} param1
 * @param {type} param2
 */
phrCtrl.controller("chartMenu", function($scope, $window, $http, $q, patientManagementService, chartViewService){
    $scope.patient = patientManagementService.getPatient();    
    $scope.isDevelopMode = isDevelopMode;
    
    /**
     * グラフリスト取得成功時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successChartListAction = function (data) {
        if (data.status === "0") {
            $scope.charDef = data;
            var length = $scope.charDef.chartDefinitionList.length;
            for(var i = 0; i < length; i++) {
                if ($scope.charDef.chartDefinitionList[i].commonFlg === 0) {
                    $scope.charDef.chartDefinitionList[i].color = "#000000";
                } else {
                    $scope.charDef.chartDefinitionList[i].color = "#20b2aa";
                }
            }
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * グラフ定義修正画面遷移成功時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successNewChart = function (data) {
        if (data.status === "0") {
            $scope.editChart = data.targetChartDefinition;
            if (data.targetChartDefinition.chartDefinitionId ===0) {
                $scope.showDelBt = "none";
            }           
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * グラフ表示画面の初期化
     * @returns {undefined}
     */
    $scope.startChartList = function () {
        chartViewService.getChartList(successChartListAction);
    };
    /**
     * グラフ選択時のアクション
     * @param {type} viewId
     * @param {type} viewCount
     * @param {type} offSet
     * @returns {undefined}
     */
    $scope.viewChart = function (viewId, viewCount, offSet) {
        chartViewService.viewChart(viewId, viewCount, offSet, null, null);  
    };
    /**
     * 定義作成ボタン時のアクション
     * @param {type} no
     * @param {type} isPageChange
     * @returns {undefined}
     */
    $scope.createDefinition = function (chartDefinitionId, inputTypeCd, isPageChange) {
        if (inputTypeCd !== null) {
            $scope.inputTypeCd = inputTypeCd;
        }
        
        if (isPageChange) {
            chartViewService.newChart(chartDefinitionId, inputTypeCd, null, $scope.editChart);
        } else {
            chartViewService.newChart(chartDefinitionId, inputTypeCd, successNewChart, $scope.editChart);
        }
    };
});

/**
 * グラフ編集画面のコントローラー
 */
phrCtrl.controller("chartEdit", function($scope, $window, $http, $q, patientManagementService, chartViewService){
    $scope.patient = patientManagementService.getPatient();    
    $scope.isDevelopMode = isDevelopMode;
    $scope.showDelBt = "flex";
    $scope.showEditBt = "flex";
    $scope.backText = "グラフ管理へ";
    $scope.colorList;
    $scope.typeColor1 = "background-color:#00008b;color:#ffffff";
    $scope.typeColor2 = "background-color:#00008b;color:#ffffff";
    $scope.typeColor3 = "background-color:#00008b;color:#ffffff";
    
    /**
     * グラフ編集の初期化
     * @returns {undefined}
     */
    $scope.startChartEdit = function () {
        $scope.editChart = chartViewService.getEditChart();
        $scope.colorList = chartViewService.getColorList();
        
        if ($scope.editChart.viewCount == 0) {
            $scope.editChart.viewCount = null;
        }
        
        $scope.inputTypeCd = $scope.editChart.dataInputType;
        if ($scope.editChart.chartDefinitionId === 0) {
            $scope.showDelBt = "none";
        } else {
            $scope.backText = "グラフ表示へ"
        }
        if ($scope.editChart.commonFlg === 1) {
            $scope.showDelBt = "none";
            $scope.showEditBt = "none";
            $scope.backText = "グラフ表示へ"
        }
        $scope.typeColor1 = "";
        $scope.typeColor2 = "";
        $scope.typeColor3 = "";
        if ($scope.inputTypeCd === 1) {
            $scope.typeColor1 = "background-color:#00008b;color:#ffffff";
        } else if ($scope.inputTypeCd === 2) {
            $scope.typeColor2 = "background-color:#00008b;color:#ffffff";
        } else if ($scope.inputTypeCd === 3) {
            $scope.typeColor3 = "background-color:#00008b;color:#ffffff";
        }
    };
    /**
     * グラフ定義修正画面遷移成功時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successNewChart = function (data) {
        if (data.status === "0") {
            $scope.editChart = data.targetChartDefinition;
            if ($scope.editChart.viewCount == 0) {
                $scope.editChart.viewCount = "";
            }
            $scope.colorList = data.colorList;
            if (data.targetChartDefinition.chartDefinitionId ===0) {
                $scope.showDelBt = "none";
            }
            $scope.typeColor1 = "";
            $scope.typeColor2 = "";
            $scope.typeColor3 = "";
            if ($scope.inputTypeCd === 1) {
                $scope.typeColor1 = "background-color:#00008b;color:#ffffff";
            } else if ($scope.inputTypeCd === 2) {
                $scope.typeColor2 = "background-color:#00008b;color:#ffffff";
            } else if ($scope.inputTypeCd === 3) {
                $scope.typeColor3 = "background-color:#00008b;color:#ffffff";
            }

            
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * 定義作成ボタン時のアクション
     * @param {type} no
     * @param {type} isPageChange
     * @returns {undefined}
     */
    $scope.createDefinition = function (chartDefinitionId, inputTypeCd, isPageChange) {
        if (inputTypeCd !== null) {
            $scope.inputTypeCd = inputTypeCd;
        }
        
        if (isPageChange) {
            chartViewService.newChart(chartDefinitionId, inputTypeCd, null, $scope.editChart);
        } else {
            chartViewService.newChart(chartDefinitionId, inputTypeCd, successNewChart, $scope.editChart);
        }
    };
    /**
     * 登録ボタン時のアクション
     * @returns {undefined}
     */
    $scope.entryChart = function () {
        var length = $scope.editChart.chartObservationList.length;
        var isEntry = false;

        // 線色が未選択の場合
        var list = $scope.editChart.chartObservationList;
        for (var j = 0; j < length; j++) {
            if (list[j].selected) {
                if (typeof list[j].lineColor === 'undefined') {
                    ons.notification.confirm({
                         title: '入力チェック',
                         message: '線色を選択してください',
                         buttonLabels: ['確認']
                    });
                    return;
                }
            }
        }
        
        if(isNaN($scope.editChart.viewCount)){
            ons.notification.confirm({
                title: '入力チェック',
                message: '表示回数には数値を入力してください',
                buttonLabels: ['確認']
            });
            return;
        }

        
        for (var i = 0; i < length; i++) {
            var def = $scope.editChart.chartObservationList[i];
            if (def.selected && def.lineColor != "" && def.lineColor != null && def.lineColor != "undefined") {
                isEntry = true;
                break;
            }
        }
        if (!isEntry) {
            ons.notification.confirm({
                title: '入力チェック',
                message: '1つ以上の項目を選択してください',
                buttonLabels: ['確認']
            });
            return;
        }
        $scope.editChart.dataInputType = $scope.inputTypeCd;
        chartViewService.entryChart($scope.editChart);
    };
    /**
     * 削除ボタンのAction
     * @returns {undefined}
     */
    $scope.deleteChart = function () {
        ons.notification.confirm({
            title: '削除',
            message: '削除します。よろしいですか。',
            buttonLabels: ['はい', 'いいえ'],
            callback: function (idx) {
                switch (idx) {
                case 0://　はい→削除
                    chartViewService.deleteChart($scope.editChart);  
                    break;
                case 1://　いいえ
                    break;
                }
            }
        });
    };

    /**
     * グラフ定義画面のチェックボックス選択時の処理
     */
    $scope.checkAction = function (inputTypeCd) {
        var e = e || window.event;
        var elem = e.target || e.srcElement;
        var elemId = elem.id;
        var chartDefinitionDto = $scope.editChart;
        var checkFlg = chartDefinitionDto.checkFlg;

        // 遷移元の画面（1:登録画面、2:修正画面）
        var process = "1";
        // チェックボックスの状態
        var checkbox = false;

        // 修正画面の場合
        if (typeof checkFlg === 'undefined') {
            process = "2"
            checkbox = true;
        } else {
            // 登録画面の場合
            if (elemId == 2) {
                if (!checkFlg[0]) {
                    checkbox = true;
                }
            } else if (elemId == 1) {
                if (!checkFlg[1]) {
                    checkbox = true;
                }
            } else if (elemId == 3) {
                if (!checkFlg[2]) {
                    checkbox = true;
                }
            } else if (elemId == 8) {
                if (!checkFlg[3]) {
                    checkbox = true;
                }
            }
        }
        chartViewService.changeChart(checkbox, chartDefinitionDto, inputTypeCd, process);
    }
});

/**
 * グラフ表示画面のコントローラー
 */
phrCtrl.controller("chartCtrl", function($scope, $window, $http, $q, patientManagementService, chartViewService){
    $scope.patient = patientManagementService.getPatient();
    $scope.isDevelopMode = isDevelopMode;
    $scope.targetDate = new Date();
    $scope.oldTargetDate = new Date();
    $scope.viewCount = 7;
    $scope.oldViewCount = 7;
    $scope.chartDefinitionId = 0;
    $scope.myLineChart;
    $scope.inputTypeCd = 2;
    $scope.maxOffSet = 0;
    $scope.offSet = 0;
    $scope.showEditBt = "flex";
    /**
     * グラフ表示時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successViewChar = function (data) {
        if (data.status === "0") {
            $scope.chartDefinitionId = data.chartDefinitionId;
            $scope.targetDate = new Date($scope.oldTargetDate);        
            $scope.viewCount = data.viewCount;
            $scope.oldViewCount = data.viewCount;
            $scope.maxOffSet = data.maxOffSet;
            if ($scope.myLineChart !== null) { $scope.myLineChart.destroy(); }
            var ctx = document.getElementById("selfChart").getContext('2d');
            $scope.myLineChart = new Chart(ctx, data.chartData);
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * グラフ定義修正画面遷移成功時のAction
     * @param {type} data
     * @returns {undefined}
     */
    var successNewChart = function (data) {
        if (data.status === "0") {
            $scope.editChart = data.targetChartDefinition;
            if (data.targetChartDefinition.chartDefinitionId ===0) {
                $scope.showDelBt = "none";
            }           
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * 左にスワイプ(未来日)した際のAction
     * @param {type} event
     * @returns {undefined}
     */
    $scope.swipeLeft = function (event) {
       if ($scope.offSet > 0) {
           $scope.offSet = $scope.offSet - 1;
           chartViewService.viewChart($scope.chartDefinitionId, $scope.viewCount, $scope.offSet, $scope.targetDate, successViewChar);
       }
    };
    /**
     * 右にスワイプ(過去日)した際のAction
     * @param {type} event
     * @returns {undefined}
     */
    $scope.swipeRight = function (event) {
        if ($scope.offSet < $scope.maxOffSet) {
            $scope.offSet = $scope.offSet + 1;
            chartViewService.viewChart($scope.chartDefinitionId, $scope.viewCount, $scope.offSet, $scope.targetDate, successViewChar);
        }
    };
    
    /**
     * 定義作成ボタン時のアクション
     * @param {type} no
     * @param {type} isPageChange
     * @returns {undefined}
     */
    $scope.createDefinition = function (chartDefinitionId, inputTypeCd, isPageChange) {
        if (inputTypeCd !== null) {
            $scope.inputTypeCd = inputTypeCd;
        }
        
        if (isPageChange) {
            chartViewService.newChart(chartDefinitionId, inputTypeCd, null, $scope.editChart);
        } else {
            chartViewService.newChart(chartDefinitionId, inputTypeCd, successNewChart, $scope.editChart);
        }
    };
    
    /**
     * グラフ表示画面の初期化
     * @returns {undefined}
     */
    $scope.startViewChart = function () {
        var ctx = document.getElementById("selfChart").getContext('2d');
        var data = chartViewService.getViewChartData();
        $scope.chartDefinitionId = data.chartDefinitionId;
        $scope.targetDate = new Date(data.targetDate);        
        $scope.viewCount = data.viewCount;
        $scope.oldViewCount = data.viewCount;
        $scope.oldTargetDate = new Date(data.targetDate);      
        $scope.myLineChart = new Chart(ctx, data.chartData);
        $scope.maxOffSet = data.maxOffSet;
        if(data.commonFlg === 1 ) {
            $scope.showEditBt = "none";
        }
                 
        //Hammer.jsオブジェクトの読み込み
        var exampleElm = document.getElementById("selfChart");
        $scope.chart = new Hammer(exampleElm);
        $scope.chart.get("pan").set({ enable: true });
        $scope.chart.get("pinch").set({ enable: true });
        $scope.chart.get("tap").set({ enable: true });
        $scope.chart.get("swipe").set({ enable: true });
        $scope.chart.on("swipeleft",$scope.swipeLeft);        
        $scope.chart.on("swiperight", $scope.swipeRight);

    };


    /**
     * グラフ表示画面の日付変更時
     * @returns {undefined}
     */
    $scope.changeData = function () {
        var currDate = new Date($scope.targetDate);
        var oldDate = new Date($scope.oldTargetDate);
        if ( phrJs.formatDate(currDate, "yyyy/MM/dd") ===  phrJs.formatDate(oldDate, "yyyy/MM/dd") && 
            $scope.viewCount === $scope.oldViewCount) {
            return;
        }
        $scope.offSet = 0;
        $scope.oldTargetDate =  phrJs.formatDate(currDate, "yyyy/MM/dd");
        chartViewService.viewChart($scope.chartDefinitionId, $scope.viewCount, 0, $scope.targetDate, successViewChar);  
    };

})
