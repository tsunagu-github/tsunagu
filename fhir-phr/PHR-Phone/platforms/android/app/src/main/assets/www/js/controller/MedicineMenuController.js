
//お薬メインメニューコントローラ
phrCtrl.controller("medicineMenuCtrl", function($scope){
    logger.debug("medicineMenuCtrl Start");
    
    logger.debug("medicineMenuCtrl End");
})


//お薬編集コントローラ
phrCtrl.controller("medicineEditCtrl", function($scope,editMedicineSettingService, lookupData){
    logger.debug("medicineEditCtrl Start"); 
    ons.ready(function() {
        var cmditem = myNavigator.topPage.pushedOptions.item;
        logger.debug("コマンド：" + cmditem); 
        if(cmditem=="new"){
            $scope.editMedicine = editMedicineSettingService.geteditMedicine(cmditem);
        }else {
            logger.debug("セット開始");
            //$scope.editMedicine = editMedicineSettingService.setEditMedicine(cmditem);
            $scope.editMedicine = editMedicineSettingService.setEditMedicine_new(cmditem);
            var medicineInfo = $scope.editMedicine.medicines;
            if(medicineInfo.length > 0){
                logger.debug("tab setting");
                var mytab = myNavigator.topPage.querySelector("ons-tabbar");
                mytab.setActiveTab(1);
            }
        }
        logger.debug("get editItem");
    });

 //未来日制限用操作日付取得
    var today = new Date();
    var thisDay = today.getDate();
    today.setDate(thisDay + 1);
    $scope.today = today.toISOString();
    
//セレクトボックスのアイテム取得    
    $scope.dosageFormCDList = lookupData.dosageFormCDList();
    $scope.mUnitCDList = lookupData.mUnitCDList();
  
    //剤型ごとに表示項目変更　（dosageQuantity:内服・浸煎・湯薬→数量（日数）表示/頓服→「投与回数」/その他は非表示（「1」を持たせる））・
   //                       （dosage.amount:内服・浸煎・湯薬→1日量/頓服→1回量/その他→全量）
    $scope.selectItem = function (index) {
        logger.debug("selectdosage");
        var selectdosage = $scope.editMedicine.medicines[index].dosageForm;
        if(selectdosage == '1'){//内服
               $scope.editMedicine.medicines[index].quantityTitle='数量（日数）';
               $scope.editMedicine.medicines[index].dosageUnit='日分';
               $scope.editMedicine.medicines[index].dosageForm_v=true;
               $scope.editMedicine.medicines[index].amountTitle='１日量（用量）';
           }else if(selectdosage == '2'){//内滴
               $scope.editMedicine.medicines[index].dosageQuantity=1;
               $scope.editMedicine.medicines[index].dosageUnit='調剤';
               $scope.editMedicine.medicines[index].dosageForm_v=false;
               $scope.editMedicine.medicines[index].amountTitle='全量（用量）';
            }else if(selectdosage == '3'){//頓服
               $scope.editMedicine.medicines[index].quantityTitle='投与回数';
               $scope.editMedicine.medicines[index].dosageUnit='回分';
               $scope.editMedicine.medicines[index].dosageForm_v=true;
               $scope.editMedicine.medicines[index].amountTitle='１回量（用量）';
            }else if(selectdosage == '4'){//注射
               $scope.editMedicine.medicines[index].dosageQuantity=1;
              $scope.editMedicine.medicines[index].dosageUnit='調剤';
                $scope.editMedicine.medicines[index].dosageForm_v=false;
               $scope.editMedicine.medicines[index].amountTitle='全量（用量）';
            }else if(selectdosage == '5'){//外用
               $scope.editMedicine.medicines[index].dosageQuantity=1;
              $scope.editMedicine.medicines[index].dosageUnit='調剤';
                $scope.editMedicine.medicines[index].dosageForm_v=false;
               $scope.editMedicine.medicines[index].amountTitle='全量（用量）';
            }else if(selectdosage == '6'){//浸煎
               $scope.editMedicine.medicines[index].quantityTitle='数量（日数）';
               $scope.editMedicine.medicines[index].dosageUnit='日分';
               $scope.editMedicine.medicines[index].dosageForm_v=true;
               $scope.editMedicine.medicines[index].amountTitle='１日量（用量）';
            }else if(selectdosage == '7'){//湯薬
               $scope.editMedicine.medicines[index].quantityTitle='数量（日数）';
               $scope.editMedicine.medicines[index].dosageUnit='日分';
               $scope.editMedicine.medicines[index].dosageForm_v=true;
               $scope.editMedicine.medicines[index].amountTitle='１日量（用量）';
            }else if(selectdosage == '9'){//材料
               $scope.editMedicine.medicines[index].dosageQuantity=1;
              $scope.editMedicine.medicines[index].dosageUnit='調剤';
                $scope.editMedicine.medicines[index].dosageForm_v=false;
               $scope.editMedicine.medicines[index].amountTitle='全量（用量）';
           }else if(selectdosage == '10'){//その他
               $scope.editMedicine.medicines[index].dosageQuantity=1;
              $scope.editMedicine.medicines[index].dosageUnit='調剤';
                $scope.editMedicine.medicines[index].dosageForm_v=false;
               $scope.editMedicine.medicines[index].amountTitle='全量（用量）';
        };
        //$scope.$apply();
    };
   
    //用法の選択肢表示
    $scope.selectUsageName = function(indexnum){
        logger.debug("select start");
        
        var usageStr = $scope.editMedicine.medicines[indexnum].usageName;
        if(usageStr==null||usageStr==""){
        
            ons.notification.confirm({
                title: '用法テンプレート選択',
                messageHTML: '選択してください。<br>文言は次で自由に編集できます。',
                buttonLabels: ['1日3回 毎食後', '1日2回 朝夕食後', '1日1回 朝食後', '1日1回 夕食後', '1日1回 就寝前'],
                animation: 'none',
                cancelable: true,
                callback: function(index) {                
                    switch (index) {
                    case 0:
                        usageStr='1日3回 毎食後';
                        break;
                    case 1:
                        usageStr='1日2回 朝夕食後';
                        break;
                   case 2:
                       usageStr='1日1回 朝食後';
                        break;
                    case 3:
                        usageStr='1日1回 夕食後';
                        break;
                    case 4:
                        usageStr='1日1回 就寝前';
                        break;
                    }
                    logger.debug(usageStr);
                     ons.notification.prompt({
                        title:'用法',
                        messageHTML: '自由に編集してください。<br>そのまま登録もできます。',
                        buttonLabel: '登録',
                        cancelable: true,
                        defaultValue:usageStr,
                        callback:function(str){
                            $scope.editMedicine.medicines[indexnum].usageName=str;
                            $scope.$apply();
                        }
                     });
                }
            });
        }
    };

    
    //薬剤削除
    $scope.deleteConfirm = function (indexnum,medType) {
        logger.debug($scope.editMedicine);
        //var mod = material ? 'material' : undefined;
        ons.notification.confirm({
            title: '確認',
            message: '薬剤を削除します。よろしいですか？',
            buttonLabels: ['はい', 'いいえ'],
            //modifier: mod,
            callback: function (idx) {
                switch (idx) {
                case 0://はい→削除
                    if($scope.editMedicine.isNew){
                        logger.debug("new del");
                        //1件もなくなってしまったら空白の薬剤ボックスを追加
                        
                        //新規作成の場合
                        if(medType=="nonpre"){
                            $scope.editMedicine.nonpre.splice(indexnum,1);
                            logger.debug($scope.editMedicine.nonpre);
                            if($scope.editMedicine.nonpre.length==0){
                                $scope.addMedicine("nonpre");
                            }
                        }else{
                            $scope.editMedicine.medicines.splice(indexnum,1);
                            if($scope.editMedicine.medicines.length==0){
                                $scope.addMedicine("presc");
                            }                            
                        }
                    }else{
                        logger.debug("med del");
                        //既存更新の場合
                        if(medType=="nonpre"){
                            $scope.editMedicine.nonpre[indexnum].isdelete=true;
                            var delnum = 0
                            for(var i= 0;i<$scope.editMedicine.nonpre.length;i++){
                                if($scope.editMedicine.nonpre[i].isdelete){
                                    delnum++;
                                }
                            }
                            if(delnum == $scope.editMedicine.nonpre.length){
                                $scope.addMedicine("nonpre")
                            }
                        }else{
                            $scope.editMedicine.medicines[indexnum].isdelete=true;
                            var delnum = 0
                            for(var i= 0;i<$scope.editMedicine.medicines.length;i++){
                                if($scope.editMedicine.medicines[i].isdelete){
                                    delnum++;
                                }
                            }
                            if(delnum == $scope.editMedicine.medicines.length){
                                $scope.addMedicine("presc")
                            }                            
                        }
                    }
                    $scope.$apply();
                    logger.debug($scope.editMedicine);
                    break;
                case 1://いいえ→何もせずに戻る
                    break;
                }
            }
        });
    };    
    
    //薬剤追加
    $scope.addMedicine = function (medType) {
        var newmedicine;
        logger.debug(medType);
        if(medType=="nonpre"){
            newmedicine = editMedicineSettingService.addNewNonpre();
            $scope.editMedicine.nonpre.push(newmedicine);            
        }else {
            newmedicine = editMedicineSettingService.addNewMedicine();
            $scope.editMedicine.medicines.push(newmedicine);
        }
        //$scope.$apply();
    };
    
    //確認
    $scope.confirmMedicine = function (medType,entryItem){
        if(medType=="nonpre"){
            $scope.editMedicine.medicines=null;
            $scope.editMedicine.medType="nonpre";
        }else{
            $scope.editMedicine.nonpre=null;
            $scope.editMedicine.medType="presc";
        }
        
        myNavigator.pushPage("templates/medicine-confirm.html", {item: entryItem});
    };
    
    logger.debug("medicineEditCtrl End");
})

//お薬登録コントローラ,entryMedicineService
phrCtrl.controller("medicineEntryCtrl", function($scope,medicineUpdateService,lookupData){
    logger.debug("medicineEntryCtrl Start");
    
//剤型コード取得    
    $scope.dosageFormCDList = lookupData.dosageFormCDList();
    
    ons.ready(function() {
        $scope.entryMedicine = myNavigator.topPage.pushedOptions.item;
        
        var mType = $scope.entryMedicine.medType;
        if(mType=="nonpre"){
            logger.debug("日付小細工");
            var medlist =  $scope.entryMedicine.nonpre;
            for(var i=0;i<medlist.length;i++){
                var startdate = $scope.entryMedicine.nonpre[i].startDate;
                var enddate = $scope.entryMedicine.nonpre[i].endDate;
                logger.debug(startdate);logger.debug(enddate);
                if(startdate!=null||startdate!=""){
                    var startNew = startdate.toString().replace("00:00:00","09:00:00");
                    //startNew = startNew.subString(0,33);
                    logger.debug(startNew);
                    $scope.entryMedicine.nonpre[i].startDate = new Date(startNew);
                    logger.debug($scope.entryMedicine.nonpre[i].startDate);
                }
                if(enddate!=null||enddate!=""){
                    var endNew = enddate.toString().replace("00:00:00","09:00:00");
                    logger.debug(endNew);
                    $scope.entryMedicine.nonpre[i].endDate= new Date(endNew);
                    logger.debug($scope.entryMedicine.nonpre[i].endDate);
                }
                //}
            }
        }
        
        
    });
    
    //登録
     $scope.entryItem = function (entryItem){
        logger.debug("entry page");
        var successAction = function (data) {
            ons.notification.confirm({
                title: 'おしらせ',
                message: '登録が完了しました。',
                buttonLabels: ['確認']
            });            
            myNavigator.resetToPage('templates/medicine-menu.html', { animation: "none" }).then(function() {
                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" }).then(function(){
                        myNavigator.pushPage("templates/medicine-list.html");                                
                    });
            });
        }
        medicineUpdateService.doUpdate(successAction,entryItem);
    };   
    logger.debug("medicineEntryCtrl End");
})

//お薬一覧コントローラ
phrCtrl.controller("medicineListCtrl", function($scope, medicineListService){
    logger.debug("medicineListCtrl Start");

    var mlist = new Object();  // 検査結果

//    mlist.medicineList = new Object();
//    mlist.medicineList.medicines = new Object();
//    $scope.mlist = mlist;
    
    //変更後
    mlist.dosageAttention = new Object();
    mlist.dosageRemark = new Object();
    mlist.PatientInput = new Object();
    mlist.dosageDataList = new Object();
    mlist.dosageDataList.recipeList = new Object();
    mlist.dosageDataList.recipeList.dosageRecipeAttention = new Object();
    mlist.dosageDataList.recipeList.dosageRecipeAddition = new Object();
    mlist.dosageDataList.recipeList.medicines = new Object();
    $scope.mlist = mlist;
    //
    
    /**
     * 検査結果取得Server接続処理成功時のAction
     * @param {type} data
     * @param {type} status
     * @param {type} headers
     * @param {type} config
     * @returns {undefined}
     */
    var successAction = function (data) {
        
        mlist = $scope.mlist;
        mlist = data.dosageList;        
        var jsonText = JSON.stringify(mlist, null, "  ");
        logger.debug(jsonText);
        $scope.mlist = mlist;
        
        var mlistlength =$scope.mlist.length;
        if(mlistlength==0){
            $scope.isNullmlist = true;
        }

    };
    
    //基準日取得
    var baseDate = null;
    baseDate = new Date();
    medicineListService.getList(successAction,baseDate);
    
    
    //詳細ページへの遷移
    $scope.showDetail = function(item) {
        myNavigator.pushPage("templates/medicine-detail.html", {item: item});
    };
    
    logger.debug("medicineListCtrl End");
})

//詳細閲覧コントローラ
phrCtrl.controller("medicineDetailCtrl", function($scope, lookupData, $filter, medicineDeleteService,medicinePatientInputService){
    logger.debug("medicineDetailCtrl Start");
    $scope.patientInputTextNew="";
    ons.ready(function() {
        $scope.dosageMedicine = myNavigator.topPage.pushedOptions.item;
        logger.debug($scope.dosageMedicine);
        $scope.dosageFormCDList = lookupData.dosageFormCDList();
        
    });

    //グーグル検索への遷移
    $scope.getSearch = function(searchitem){
        logger.debug(searchitem);
        var pass = "https://www.google.co.jp/#q=" + searchitem;
        //var pass = "//www.google.co.jp/#q=" + searchitem;

        //window.open(pass,"");
        window.open(pass,'_blank');
        //window.href="intent:pass#Intent;scheme=http;action=android.intent.action.VIEW;end";
    };
    
    //削除
    $scope.deleteConfirm = function (dosageId) {
        ons.notification.confirm({
            title: '確認',
            message: 'おくすりを削除します。よろしいですか？',
            buttonLabels: ['はい', 'いいえ'],
            callback: function (index) {
                switch (index) {
                case 0:
                     //削除処理                   
                    var successAction = function (data) {
                        ons.notification.confirm({
                            title: 'おしらせ',
                            message: '削除が完了しました。',
                            buttonLabels: ['確認']
                        });
                        
                        myNavigator.resetToPage('templates/medicine-menu.html', { animation: "none" }).then(function() {
                                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" }).then(function(){
                                    myNavigator.pushPage("templates/medicine-list.html");                                
                                });
                        });                        
                    }
                    medicineDeleteService.doDelete(successAction,dosageId);

                    break;
                case 1:
                    break;
                }
            }
        });
    };
    
    //患者記入情報登録
    $scope.entryMessage = function (dosageId) {

        var newTxt = $scope.patientInputTextNew;
        var patientInput = $scope.dosageMedicine.patientInput;
        logger.debug(newTxt + "" + patientInput);

        if(newTxt.length>0){
            var newPatInput = {
                dosageId:"",
                seq:0,
                inputSeq:0,
                inputDate:new Date(),
                inputText:newTxt
            };
            if(patientInput==null){
                patientInput = [];
            }
            patientInput.push(newPatInput);
        }
        
        var successAction = function () {
            $scope.patientInputTextNew="";
            $scope.dosageMedicine.patientInput = patientInput;
        }
        if( patientInput==null){
            
        }else{
            medicinePatientInputService.doPatientInput(successAction,dosageId,patientInput);
        }
    };    

    //編集画面への遷移
    $scope.openEditor=function(editItem){
        myNavigator.pushPage("templates/medicine-entry-nonpre.html", {item: editItem});
    };
    logger.debug("medicineDetailCtrl End");
})

//お薬一覧カレンダーコントローラ
phrCtrl.controller("medicineCalendarCtrl", function($scope, medicineListService, calendarService, $timeout){
    logger.debug("medicineCalendarCtrl Start");
    //Hammer.gestures.Swipe.defaults.swipeVelocityX = 0.1;
    var mlist = new Object();  

    //基準日取得
    var baseDate = null;
    baseDate = new Date();
    $scope.showYear = baseDate.getFullYear();
    $scope.showMonth = baseDate.getMonth() + 1;
    logger.debug(baseDate);
    
    //カレンダー表示
    $scope.header = calendarService.getheader();
    var calendarList = calendarService.getcalendar(baseDate);
    $scope.calendar = calendarList;
    $scope.isNewestMonth=true;
    //$scope.showListItem = false;
    
//    mlist.medicineList = new Object();
//    mlist.medicineList.medicines = new Object();
//    $scope.mlist = mlist;
    

    mlist.dosageAttention = new Object();
    mlist.dosageRemark = new Object();
    mlist.PatientInput = new Object();
    mlist.dosageDataList = new Object();
    mlist.dosageDataList.recipeList = new Object();
    mlist.dosageDataList.recipeList.dosageRecipeAttention = new Object();
    mlist.dosageDataList.recipeList.dosageRecipeAddition = new Object();
    mlist.dosageDataList.recipeList.medicines = new Object();
    $scope.mlist = mlist;
    //    
    
    var successAction = function (data) {
        
        mlist = $scope.mlist;
        mlist = data.dosageList;        
        var jsonText = JSON.stringify(mlist, null, "  ");
        logger.debug(jsonText);
        $scope.mlist = mlist;
        
        var mlistlength =$scope.mlist.length;
        if(mlistlength==0){
            $scope.isNullmlist = true;
        }else{
            var calendarListsuccess = calendarService.getcalendar(new Date());
            $scope.calendar = calendarService.setMedicine(calendarListsuccess,mlist,new Date());
        }
        logger.debug("取得完了");

    };
    baseDate = null;
    baseDate = new Date();
    //サーバ接続
    medicineListService.getList(successAction,baseDate);
    
    
    //翌月の取得
    $scope.nextMonth = function(year,month) {
        logger.debug("nextMonth start");
        var realToday =new Date();
        var today = new Date(realToday.getFullYear(), realToday.getMonth(),1);
//        var wantMonth = new Date(year,month-1,today.getDate());
        var wantMonth = new Date(year,month-1,1);
        logger.debug(today);
        logger.debug(wantMonth);
        if(today.valueOf()==wantMonth.valueOf()){
            logger.debug("何もしない");
        }else{
            wantMonth.setMonth(wantMonth.getMonth() + 1);
            $scope.showYear = wantMonth.getFullYear();
            $scope.showMonth = wantMonth.getMonth()+1;        
            $scope.Daymlist = null;
            if(today.getFullYear()==$scope.showYear && today.getMonth()==$scope.showMonth-1){
                $scope.isNewestMonth=true;
            }else{
                $scope.isNewestMonth=false;    
            }        
            var nextCalendarList  = calendarService.getcalendar(wantMonth);
            $scope.calendar  = calendarService.setMedicine(nextCalendarList,mlist,new Date(year,month,1));
    }
    };    
    
    //先月の取得
    $scope.prevMonth = function(year,month) {
        logger.debug("preMonth start");
        var today =new Date();
//        var wantMonth = new Date(year,month-1,today.getDate());
        logger.debug(month);
        var wantMonth = new Date(year,month-1,1);
        wantMonth.setMonth(wantMonth.getMonth() - 1);
        $scope.showYear = wantMonth.getFullYear();
        $scope.showMonth = wantMonth.getMonth() + 1;
        $scope.Daymlist = null;
        var prevCalendarList  = calendarService.getcalendar(wantMonth);
        $scope.calendar  = calendarService.setMedicine(prevCalendarList,mlist,new Date(year,month-2,1));
        $scope.isNewestMonth=false;
    };
    
    //カルーセル
    $timeout(function(){
         carousel.on('overscroll', function (event) {
//             logger.debug(event.carousel);
  
//                logger.debug(event.direction);
                if (event.direction == "right") {
                    $scope.nextMonth($scope.showYear,$scope.showMonth);
                } else if (event.direction == "left"){
                    $scope.prevMonth($scope.showYear,$scope.showMonth);
                }
                $timeout(function() {
                    carousel.refresh();
                });
        });   
    });
 
    
    //選択日のおくすり表示
    $scope.showList = function(item,dd) {
        logger.debug("リスト表示");
         $scope.showListItem = true;
         $scope.Daymlist = item;
         $scope.showDD = dd;
    };
    //詳細ページへの遷移
    $scope.showDetail = function(item) {
            myNavigator.pushPage("templates/medicine-detail.html", {item: item});
    };
    
    logger.debug("medicineCalendarCtrl End");
})

//お薬QR読込みコントローラ
phrCtrl.controller("medicineQRReaderCtrl", function($scope,medicineImportService){
    logger.debug("medicineQRReaderCtrl Start");
    var scanner = cordova.plugins.barcodeScanner;
    //var scanner = cordova.plugins.qrreader;
    var importText = [];
    var separateId=null;
    var sepSize=0;
    var promptSentence="";
    
    $scope.scanOn=false;
    
    var successAction = function (data) {
        
        var msg1 = data.message;
        if(msg1==null || msg1.length==0){        
             ons.notification.confirm({
                title: 'おしらせ',
                message: '撮影データの取り込みが完了しました。',
                buttonLabels: ['確認']
            });            
            myNavigator.resetToPage('templates/medicine-menu.html', { animation: "none" }).then(function() {
                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "none" }).then(function(){
                        myNavigator.pushPage("templates/medicine-list.html");                                
                    });
            });           
        }else{                   
            ons.notification.confirm({
                title: 'おしらせ',
                message: msg1,
                buttonLabels: ['確認']
            });  
        }
    }
       
   $scope.scan = function(){
       logger.debug("SCAN機能");
        $scope.scanOn=true;
        scanner.scan(
          function (result) {
            if (result.cancelled) {
                importText.length=0;
                separateId=null;
                sepSize=0;
                promptSentence="";
                $scope.scanOn=false;
                return;
            }
            var format = result.format;
            logger.debug("format:" +format);
            if(format!="QR_CODE"){
                ons.notification.confirm({
                    title: 'コードエラー',
                    message: 'QRコードと認識できませんでした。もう一度撮影をお願いいたします。',
                    buttonLabels: ['カメラ起動'],
                    callback: function (index) {
                        switch (index) {
                        case 0:
                             //SCAN
                            $scope.scan();
                            break;   
                        }
                    }
                });                 
            }else{
                var resultText =result.text;
                logger.debug(resultText);
                var sendServerFlg = false;
                var notDupFlg=true;
                var separatedError = false;
                for(var i=0;i<importText.length;i++){
                    if(importText[i].importText==resultText){
                        notDupFlg=false;
                        logger.debug("重複");
                        break;
                    }
                }                      
                if(notDupFlg==true){
                    //ヘッダーによる分割の確認
                    var searated = result.separate;
                    logger.debug("isSeparatd:" + searated);
                    if(searated==true){
                        var qrsepId=result.parity;
                        var qrsepNo = result.current;
                        if(separateId==null){
                            separateId = result.parity;
                        }
                        if(sepSize==0){
                            sepSize = result.total;
                        }
                        if(separateId==qrsepId){
                            var resultSet = {
                                importText:resultText,
                                shootingOrder:qrsepNo
                            };
                            importText.push(resultSet); 
                            if(importText.length==sepSize){
                                sendServerFlg=true;
                            }else{
                                var readno =[];
                                var unreadno=[];
                                
                                for(var k=0;k<sepSize;k++){
                                    var doneSetting=false;
                                    for(var l=0;l<importText.length;l++){
                                        if(importText[l].shootingOrder==(k+1)){
                                            doneSetting=true;
                                            readno.push(k+1);
                                            break;
                                        }
                                    }
                                    if(doneSetting==false){
                                        unreadno.push(k+1)
                                    }
                                }
                                
                                promptSentence="未読：" + unreadno +"  読込済み：" + readno;
                            //    ons.notification.confirm({
                            //        title: '連続コード',
                            //        message:'全' +  sepSize + '枚のQRコードです。次のQRコードを読み込んでください',
                            //        buttonLabels: ['カメラ起動'],
                            //        callback: function (index) {
                            //            switch (index) {
                            //            case 0:
                                             //SCAN
                                            $scope.scan();
                            //                break;
                            //            }
                            //        }
                            //    });                            
                            }
                        }else{
                            //パリティ値が不一致でした
                            separatedError = true;
                        }
                    }else{
                        if(separateId==null){
                            var resultSet = {
                                importText:resultText,
                                shootingOrder:1
                            };
                            importText.push(resultSet);
                            sendServerFlg=true;
                        }else{
                            separatedError = true;
                        }
                    }
                    if(separatedError==true){
                        ons.notification.confirm({
                            title: '対象外エラー',
                            message: '連結対象外のコードです。確認のうえ、もう一度読込みをお願いいたします。',
                            buttonLabels: ['カメラ起動'],
                            callback: function (index) {
                                switch (index) {
                                case 0:
                                     //SCAN
                                    $scope.scan();
                                    break;
                                }
                            }
                        });                     
                    }
                    if(sendServerFlg==true){
                        var importText2 = importText.slice(0, importText.length)
                        medicineImportService.doUpdate(successAction,importText2);
                        importText.length=0;
                        separateId=null;
                        sepSize=0;
                        sendServerFlg = false;
                        promptSentence="";
                        $scope.scanOn=false;
                    }
                }else{
                    ons.notification.confirm({
                        title: '重複エラー',
                        message: '撮影済みのQRコードでした。次のコードの読込みをお願いいたします。',
                        buttonLabels: ['カメラ起動'],
                        callback: function (index) {
                            switch (index) {
                            case 0:
                                 //SCAN
                                $scope.scan();
                                break;
                            }
                        }
                    });            
                }
            }  
          }, 
          function (error) {
              alert("読込みに失敗しました。もう一度お試しください。: " + error);
          },
          {"prompt" :promptSentence}
       );
   }; 
   
   
   $scope.refresh = function(){
       logger.debug("リフレッシュ機能");
        importText.length=0;
        separateId=null;
        sepSize=0;
        promptSentence="";
        $scope.scanOn=false;
    };
    logger.debug("medicineQRReaderCtrl End");
})

//お薬QR読込みコントローラ_OLD
//phrCtrl.controller("medicineQRReaderCtrl", function($scope,medicineImportService){
//    logger.debug("medicineQRReaderCtrl Start");
//    var scanner = cordova.plugins.barcodeScanner;
//    //var importText = null;
//    var importText = [];
//    var shotNo = 0
//    
//    
//    var successAction = function (data) {
//        ons.notification.confirm({
//            title: 'おしらせ',
//            message: '撮影データの取り込みが完了しました。',
//            buttonLabels: ['確認']
//        });            
//        myNavigator.resetToPage('templates/medicine-menu.html', { animation: "none" }).then(function() {
//            myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "none" }).then(function(){
//                    myNavigator.pushPage("templates/medicine-list.html");                                
//                });
//        });
//    }
//     
//   $scope.scan = function(){
//       logger.debug("SCAN機能");
//        scanner.scan(
//          function (result) {
//            if (result.cancelled) {return;}            
//            var resultText =result.text;
//            logger.debug(resultText);
//            var notDupFlg=true;
//            for(var i=0;i<importText.length;i++){
//                logger.debug("ループ中");
//                if(importText[i]==resultText){
//                    notDupFlg=false;
//                    logger.debug("重複");
//                    ons.notification.confirm({
//                        title: '再撮影',
//                        message: '撮影済みのQRコードでした。' + (importText.length + 1) + 'つ目のQRコードをカメラにかざしてください。',
//                        buttonLabels: ['はい'],
//                        callback: function (index) {
//                            switch (index) {
//                            case 0:
//                                 //SCAN
//                                $scope.scan();
//                                break;
//                            }
//                        }
//                    });                    
//                    
//                    break;
//                }
//            }
//            if(notDupFlg){
//                shotNo = shotNo + 1;
//                var resultSet = {
//                    importText:resultText,
//                    shootingOrder:shotNo
//                };
//                importText.push(resultSet);
//                //importText.push(resultText);
//            }       
//            ons.notification.confirm({
//                title: '連続撮影',
//                message: '次のQRコードを読み込みますか?',
//                buttonLabels: ['はい', 'いいえ'],
//                callback: function (index) {
//                    switch (index) {
//                    case 0:
//                         //SCAN
//                        $scope.scan();
//                        break;
//                    case 1:
//                        var importText2 = importText.slice(0, importText.length)
//                        medicineImportService.doUpdate(successAction,importText2);
//                        importText.length=0;
//                        break;
//                    }
//                }
//            });                
//          }, 
//          function (error) {
//              alert("読込みに失敗しました。もう一度お試しください。: " + error);
//          }//,
//          //{"prompt" : importText.length + "件のコードを取込み済みです"}
//       );
//   };   
//   
//    
//    logger.debug("medicineQRReaderCtrl End");
//})