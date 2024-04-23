/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
phrService.factory('chartViewService', function ($http, $q, $window, lookupData, patientManagementService) {

    // インスタンスに保存する
    var editChart = null;
    var viewChartData = null;
    var colorList = null;
    
    /**
     * グラフ修正画面遷移の成功Action
     * @param {type} data
     * @returns {undefined}
     */
    var successEditChartPage = function (data) {
        if (data.status === "0") {
            editChart = data.targetChartDefinition;
            editChart.chartDefinitionId = data.chartDefinitionId;
            colorList = data.colorList;
            myNavigator.pushPage( 'templates/chart-edit.html');
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * グラフ登録の成功Action
     * @param {type} data
     * @returns {undefined}
     */
    var successEntryChart = function (data) {
        if (data.status === "0") {
            myNavigator.resetToPage('templates/chart-menu.html', { animation: "none" }).then(function() {
                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" });
            });
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * グラフ削除の成功Action
     * @param {type} data
     * @returns {undefined}
     */
    var successDeleteChart = function (data) {
        if (data.status === "0") {
            myNavigator.resetToPage('templates/chart-menu.html', { animation: "none" }).then(function() {
                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" });
            });
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };
    /**
     * グラフ表示の成功Action
     * @param {type} data
     * @returns {undefined}
     */
    var successViewChar = function (data) {
        if (data.status === "0") {
            viewChartData = data;
            myNavigator.pushPage("templates/chart-view.html");     
        } else {
            errorMsg = data.message;
            myNavigator.pushPage("templates/error.html");
        }        
    };   

    return {
        
        getEditChart: function() {
            return editChart;
        },
        
        getColorList: function() {
            return colorList;
        },
        
        getViewChartData: function() {
            return viewChartData;
        },
        
        /**
         * グラフ一覧を取得する
         * @returns {data}
         */
        getChartList: function(successAction) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "ChartListAction";
            sendObject.charttDefinitionAction = "LIST"
            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
        
        /**
         * 表示するグラフを取得する
         * @param {type} patientDto
         * @returns {undefined}
         */
        viewChart: function (no, viewCount, offSet, targetDate, successFunction) {
            var webAction = new WebAction();
            var sendObject = new Object();
            if (isDevelopMode === 3) {
                sendObject.action = "ChartViewAction" + no + "-" + offSet;
            } else {
                sendObject.action = "ChartViewAction";
            }
             
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.chartDefinitionId = no;
            sendObject.charttDefinitionAction = "VIEW"
            sendObject.viewCount = viewCount;
            sendObject.offSet = offSet;
            if (targetDate !== null) {
                var currDate = new Date(targetDate);
                sendObject.targetDate =  phrJs.formatDate(currDate, "yyyy/MM/dd");
            }
            if (successFunction === null) {
                webAction.setSuccessAction(successViewChar);
            } else {
                webAction.setSuccessAction(successFunction);
            }
            webAction.action($http, $q, sendObject);
        },
        /**
         * グラフを新規作成する
         * @param {type} patientDto
         * @returns {undefined}
         */
        newChart: function (chartDefinitionId, inputTypeCd, successAction, chartDefinitionDto) {
            var webAction = new WebAction();
            var sendObject = new Object();
            if (isDevelopMode === 3) {
                sendObject.action = "ChartNewAction" + chartDefinitionId + "-" + inputTypeCd;
            } else {
                sendObject.action = "ChartNewAction";
            }
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.chartDefinitionId = chartDefinitionId;
            sendObject.dataInputType = inputTypeCd;
            
            if (chartDefinitionId === null || chartDefinitionId === 0) {
                sendObject.charttDefinitionAction = "NEW";
                if (chartDefinitionDto != null) {
                    chartDefinitionDto.chartObservationList = [];
                    sendObject.entryChartDefinitionDto = chartDefinitionDto;
                }
            } else {
                sendObject.charttDefinitionAction = "EDIT";
            }
            if (successAction === null) {
                webAction.setSuccessAction(successEditChartPage);
            } else {
                webAction.setSuccessAction(successAction);
            }
            webAction.action($http, $q, sendObject);
        },
        /**
         * 表示項目を変更する
         * @param checkbox
         * @param chartDefinitionDto
         * @param inputTypeCd
         * @returns {undefined}
         */
        changeChart: function (checkbox, chartObservationDto, inputTypeCd, process) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.dataInputType = inputTypeCd;
            if (process == "1") {
                sendObject.charttDefinitionAction = "CHANGE";
            } else if (process == "2") {
                sendObject.charttDefinitionAction = "CHANGE2";
            }
            sendObject.action = "ChartNewAction";
            sendObject.chartDefinitionId = chartObservationDto.chartDefinitionId;
            sendObject.entryChartDefinitionDto = chartObservationDto;
            sendObject.checkboxFlg = checkbox;
            webAction.setSuccessAction(successEditChartPage);
            webAction.action($http, $q, sendObject);
        },
        /**
         * グラフを登録する
         * @param {type} chartDefinitionDto
         * @returns {undefined}
         */
        entryChart: function (chartDefinitionDto) {
            var webAction = new WebAction();
            var sendObject = new Object();
            
            sendObject.action = "ChartEntryAction";
            sendObject.charttDefinitionAction = "ENTRY";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.entryChartDefinitionDto = chartDefinitionDto;
            sendObject.dataInputType = chartDefinitionDto.dataInputType;
            sendObject.chartDefinitionId = chartDefinitionDto.chartDefinitionId;
            webAction.setSuccessAction(successEntryChart);
            webAction.action($http, $q, sendObject);
        },
        /**
         * グラフ削除
         * @param {type} chartDefinitionDto
         * @returns {undefined}
         */
        deleteChart: function (chartDefinitionDto) {
            var webAction = new WebAction();
            var sendObject = new Object();
            
            sendObject.action = "ChartDeleteAction";
            sendObject.charttDefinitionAction = "DEL";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.chartDefinitionId = chartDefinitionDto.chartDefinitionId;
            webAction.setSuccessAction(successDeleteChart);
            webAction.action($http, $q, sendObject);
        }
    }
})