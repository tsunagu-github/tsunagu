//健診結果コントローラ

phrCtrl.controller("checkUpResultsCtrl", function($scope,checkUpResultService, lookupData){
    logger.debug("checkUpResultsCtrl Start");
    var checkUpResult = new Object();
    checkUpResult.hospitalName = new Object();
    checkUpResult.date = new Object();
    checkUpResult.title = new Object();

    // 健診（健診）結果
    var valuelist = new Object();
    valuelist.yearList = new Object();
    valuelist.dataList = new Object();
    valuelist.dataList.results = new Object();

    // 健診（問診）結果
    var inquirylist = new Object();
    inquirylist.yearList = new Object();
    inquirylist.dataList = new Object();
    inquirylist.dataList.results = new Object();

    // 健診（診察）結果
    var examlist = new Object();
    examlist.yearList = new Object();
    examlist.dataList = new Object();
    examlist.dataList.results = new Object();

    $scope.valueCounter=0;
    $scope.inqCounter=0;
    $scope.examCounter=0;

    $scope.valuelist = valuelist;
    $scope.inquirylist = inquirylist;
    $scope.examlist = examlist;
    
    var typeList = lookupData.inspectionTypeCDList();
    var inspectionTypeList=[];
    for(var j=0;j<typeList.length;j++){
        inspectionTypeList.push(typeList[j].cd);
    }
    logger.debug(inspectionTypeList);
    /**
     * 検査結果取得Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successAction = function (data) {
        // 表示タイトル
        checkUpResult.hospitalName = data.hospitalName;
        checkUpResult.date = data.date;
        checkUpResult.medicalCheckupId = data.medicalCheckupId;
        checkUpResult.inquiryId = data.inquiryId;
        checkUpResult.examinationId = data.examinationId;
        checkUpResult.title = checkUpResult.hospitalName + "（" +checkUpResult.date + "）";
	checkUpResult.file = data.file;
        $scope.checkUpResult = checkUpResult;

        // 過去日・未来日の矢印
        $scope.beforeFlg = data.beforeFlg;
        $scope.futureFlg = data.futureFlg;

        var dataList = data.dataList;
        if (dataList.length == 0) {
            $scope.isNullVlist = true;
            $scope.isNullIlist = true;
            $scope.isNullElist = true;
	    $scope.isSaveButton = false;
        } else {
            for(var i=0;i<dataList.length;i++){
                var forselectType = dataList[i].inspectionType;
                if(forselectType=="3"){
                     logger.debug("健診");
                     valuelist = $scope.valuelist;
                     valuelist = dataList[i];
            //       var jsonText = JSON.stringify(valuelist, null, "  ");
            //       logger.debug(jsonText);
                     $scope.valuelist = valuelist;

                     var vlistlength = $scope.valuelist.dataList.length;
                     if (vlistlength == 0) {
                         $scope.isNullVlist = true;
			 $scope.isSaveButton = false;
                     } else {
                         $scope.isNullVlist = false;
			 $scope.isSaveButton = true;
                     }
            //       var vlistlength =$scope.valuelist.yearList.length;
            //       logger.debug($scope.valuelist.yearList);
            //       logger.debug(vlistlength);
            //       if(vlistlength==0){
            //           $scope.isNullVlist = true;
            //           $scope.maxValueCounter=0;
            //       }else{
            //           $scope.maxValueCounter=vlistlength-3;
            //           $scope.isNullVlist = false;
            //       }
            //       logger.debug($scope.isNullVlist);
                }else if(forselectType=="4"){
                    logger.debug("問診");
                    inquirylist = $scope.inquirylist;
                    inquirylist = dataList[i];
            //      var jsonText = JSON.stringify(inquirylist, null, "  ");
            //      logger.debug(jsonText);
                    $scope.inquirylist = inquirylist;

                    var ilistlength =$scope.inquirylist.dataList.length;
//                    if (vlistlength == 0) {
                    if (ilistlength == 0) {
                        $scope.isNullIlist = true;
			$scope.isSaveButton = false;
                    } else {
                        $scope.isNullIlist = false;
			$scope.isSaveButton = true;
                    }
            //      var ilistlength =$scope.inquirylist.yearList.length;
            //      logger.debug(ilistlength);
            //      if(ilistlength==0){
            //          $scope.isNullIlist = true;
            //          $scope.maxInqCounter=0;
            //      }else{
            //          $scope.maxInqCounter=ilistlength-3;
            //          $scope.isNullIlist = false;
            //      }
            //      logger.debug($scope.isNullIlist);
                }else{
                     logger.debug("診察");
                     examlist = $scope.examlist;
                     examlist = dataList[i];
            //       var jsonText = JSON.stringify(examlist, null, "  ");
            //       logger.debug(jsonText);
                     $scope.examlist = examlist;

                     var elistlength = $scope.examlist.dataList.length;
//                     if (vlistlength == 0) {
                    if (elistlength == 0) {
                         $scope.isNullElist = true;
			 $scope.isSaveButton = false;
                     } else {
                         $scope.isNullElist = false;
			 $scope.isSaveButton = true;
                     }
            //       var elistlength =$scope.examlist.yearList.length;
            //       logger.debug(elistlength);
            //       if(elistlength==0){
            //           $scope.isNullElist = true;
            //           $scope.maxExamCounter=0;
            //       }else{
            //           $scope.maxExamCounter=elistlength-3;
            //           $scope.isNullElist = false;
            //       }
            //           logger.debug($scope.isNullElist);
               }
            }
        }

        // ファイル保存処理
        if (data.file.length != 0) {
            var path = cordova.file.externalRootDirectory + "Download/";
            window.resolveLocalFileSystemURL(path, function(fileSystem) {
                fileSystem.getFile("特定健診XML_エクスポート.xml", { create: true, exclusive: false }, function(fileEntry) {
                    writeFile(fileEntry, null);
                });
            });
            function writeFile(fileEntry, dataObj) {
                fileEntry.createWriter(function(fileWriter) {
                    fileWriter.write(checkUpResult.file);
                });
            };
            window.setTimeout(function() {
                alert("特定健診情報XMLファイルを保存しました。");
            }, 5000);
        }
    };
    
    checkUpResultService.getList(inspectionTypeList, successAction);
    
    /**
     * ページめくりカウントアップアクション
     * @param {type} resultType
     * @returns {undefined}
     */
    $scope.countUp = function (resultType){
        var targetDate = document.getElementById("targetDate").innerText;
        var status = "future";
        checkUpResultService.getList(inspectionTypeList, successAction, targetDate, status);
//        var num = 0;
//        if(resultType=="v"){
//            num= $scope.valueCounter;
//            $scope.valueCounter = num + 1;
//        }else if(resultType=="i"){
//            num= $scope.inqCounter;
//            $scope.inqCounter = num + 1;
//        }else{
//            num= $scope.examCounter;
//            $scope.examCounter = num + 1;
//        }
    };
    /**
     * ページもどりカウントダウンアクション
     * @param {type} resultType
     * @returns {undefined}
     */
    $scope.countDown = function (resultType){
        var targetDate = document.getElementById("targetDate").innerText;
        var status = "before";
        checkUpResultService.getList(inspectionTypeList, successAction, targetDate, status);
//        var num = 0;
//        if(resultType=="v"){
//            num= $scope.valueCounter;
//            $scope.valueCounter = num - 1;
//        }else if(resultType=="i"){
//            num= $scope.inqCounter;
//            $scope.inqCounter = num - 1;
//        }else{
//            num= $scope.examCounter;
//            $scope.examCounter = num - 1;
//        }
    };    

    /**
     * 保存ボタン押下時
     * @returns {undefined}
     */
    $scope.save = function (){
        var targetDate = document.getElementById("targetDate").innerText;
        var medicalCheckupId = document.getElementById("medicalCheckupId").innerText;
        var inquiryId = document.getElementById("inquiryId").innerText;
        var examinationId = document.getElementById("examinationId").innerText;
        var status = "save";
        checkUpResultService.save(inspectionTypeList, medicalCheckupId, inquiryId, examinationId, successAction, targetDate, status);
    }

    logger.debug("checkUpResultsCtrl End");
})

