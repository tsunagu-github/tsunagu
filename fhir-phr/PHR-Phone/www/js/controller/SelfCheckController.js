//  自己測定結果コントローラ
phrCtrl.controller("selfCheckListCtrl", function($scope, selfCheckService){

    logger.debug("selfCheckListCtrl Start");

    var selfCheckList = []; 
    selfCheckList = selfCheckService.createSelfCheckList();
    var operation = selfCheckService.getOperation();
    var oneSelfCheckData = selfCheckService.getOneSelfCheckData();
    var checkTypeList = [];
    var saved = selfCheckService.getSaved();

    //  一覧データ取得成功時
    var successAction = function (data) {
        var oneData;
        logger.debug('データ件数='+data.selfCheckList.length );
    
        logger.debug( JSON.stringify(data) );
        
        if( data.browserType !== null ){
            if( browserType === "" ){
                browserType = data.browserType;
            }
        }
        logger.debug( browserType );
        
        for( var lpct=0; lpct < data.selfCheckList.length; lpct++ ){
            oneData = data.selfCheckList[lpct];
            oneData.systolicBPView = ( oneData.systolicBP !== "" )?oneData.systolicBP + " ㎜Hg":"";
            oneData.diastolicBPView = ( oneData.diastolicBP !== "" )?oneData.diastolicBP + " ㎜Hg":"";
            oneData.weightView = ( oneData.weight !== "" )?oneData.weight + " ㎏":"";
            oneData.bloodGlucoseView =( oneData.bloodGlucose !== "" )?oneData.bloodGlucose + " ㎎/dL":"";
        }
        $scope.selfCheckList = data.selfCheckList;
        if( $scope.selfCheckList.length === 0 ){
            $scope.isNullmlist = true;
            selfCheckService.setSaved( false, [] );
        }else{
            $scope.isNullmlist = false;
            selfCheckService.setSaved( true, data.selfCheckList );
        }
        var hantei = selfCheckService.getSaved();
        changeViewMode( hantei.mode );
        var date_obj = new Date();
        logger.debug(date_obj.toString());        
        logger.debug( '件数='+data.selfCheckList.length);

        viewLocationControl();
    };

    //  一覧データ全取得
    $scope.getAllSelfCheckList = function () {
        selfCheckService.getSelfCheckList( successAction, 10000 );
    };
    
    //  詳細ページへの遷移
    $scope.goToShowDetail = function(item) {
        selfCheckService.setOperation( true );
        logger.debug( JSON.stringify(item));
        if (item == null){
            myNavigator.pushPage("templates/selfcheck-detail-ud.html");
        }else{
            myNavigator.pushPage("templates/selfcheck-detail-ud.html", {item: item});
        }
    };
    
    //  詳細ページへの遷移
    $scope.goToEntryDetail = function() {
        selfCheckService.setOperation( false );
        myNavigator.pushPage("templates/selfcheck-detail-c.html");
    };

    //  一覧を開いたとき
    ons.ready(function() {
        var date_obj = new Date();
        logger.debug(date_obj.toString());        
        var hantei = selfCheckService.getSaved();
        if( hantei.mode === true ){
            var loadData = new Object();
            loadData.selfCheckList = hantei.data;
            successAction( loadData );
        }else{
            logger.debug( '保存データなし - 60日分取得します');
            $scope.checkTypeList = selfCheckService.createCheckTypeList();
            selfCheckService.getSelfCheckList( successAction, 60 );
        }
    });

    logger.debug("selfCheckListCtrll End");

});

//  詳細閲覧コントローラ
phrCtrl.controller("selfCheckDetailCtrl", function( $scope,selfCheckService){
    logger.debug("selfCheckCtrl Start");
    
    var operationResult;
    var oneSelfCheckData;
    var operationType = "";
    
    //　追加・削除・更新をしたあと
    var successAction2 = function (data) {
        logger.debug(JSON.stringify( data ));
        $scope.operationResult = data.status;
    
        logger.debug( '今回のミッション='+ operationType );
        logger.debug( JSON.stringify( $scope.oneSelfCheckData ));

        if( operationType === "D" ){
            deleteData();
        }
        if( operationType === "C" ){
            addData( data.addData );
        }
        
        ons.notification.confirm({
            title: 'おしらせ',
            message: '完了しました。',
            buttonLabels: ['確認'],
            callback: function() {                
                selfCheckList = null;
                selfCheckService.resetOneSelfCheckData();
                myNavigator.resetToPage("templates/selfcheck-list.html", {animation:"slide"}).then( function (){
                    var hantei = selfCheckService.getSaved();
                    changeViewMode( hantei.mode );
                    viewLocationControl();
                    myNavigator.insertPage( 0, "templates/menu-list.html" );
                });
            }
        });
    };

    //  新規入力したときの追加
    var addData = function( addData ){
        var allData = selfCheckService.getSaved();
        if( allData !== null){
            allData.data.push( addData );
        }else{
            allData.data[0] = addData;
        }
        selfCheckService.setSaved( true, allData.data );
        logger.debug( '登録したデータ=' + JSON.stringify( addData ) );
        logger.debug( '登録後の全体行数='+allData.data.length);
        
    };

    //  削除したときの詰め
    var deleteData = function( ){
        var allData = selfCheckService.getSaved();
        var target = $scope.oneSelfCheckData;
        var oneData = null;
        logger.debug( '削除前の全体行数='+allData.data.length);        
        logger.debug( JSON.stringify(target));
        logger.debug( '削除したデータ=' + JSON.stringify(target) );
        for( var lpct=0; lpct < allData.data.length; lpct++ ){
            oneData = allData.data[ lpct ];
            if( oneData.observationEventId === target.observationEventId ){
                logger.debug( lpct + ':[' + oneData.observationEventId + '][' + target.observationEventId + ']');
                allData.data.splice( lpct,1);
                logger.debug( '削除後行数='+allData.data.length);        
                selfCheckService.setSaved( true, allData.data );
                break;
            }
        }
    };

    //  詳細画面開いたとき
    ons.ready(function() {
        if( !selfCheckService.getOperation().mode ){
            logger.debug('新規の時');
            $scope.oneSelfCheckData =selfCheckService.getOneSelfCheckData();
        }
        else{
            logger.debug('選択したとき');
            $scope.oneSelfCheckData = myNavigator.topPage.pushedOptions.item;
        }
    });

    //  自己測定削除
    $scope.deleteConfirm = function ( value ) {
        ons.notification.confirm({
            title: '削除',
            message: '削除します。よろしいですか。',
            buttonLabels: ['はい', 'いいえ'],
            callback: function (idx) {
                switch (idx) {
                case 0://　はい→削除
                    operationType = "D";
                    selfCheckService.entryOperation( successAction2 , $scope.oneSelfCheckData, "D" );
                    break;
                case 1://　いいえ
                    break;
                }
            }
        });
    };    

    //  自己測定設定（更新・登録）
    $scope.entryConfirm = function () {
        var operation = selfCheckService.getOperation();
        var resultObj = selfCheckService.checkEntryData( $scope.oneSelfCheckData );
        operationType = (operation.mode)?"U":"C";
        if( resultObj.result ){
            selfCheckService.entryOperation( successAction2 , $scope.oneSelfCheckData, (operation.mode)?"U":"C" );
        }else{
            ons.notification.confirm({
                title: 'エラー',
                message: resultObj.message,
                buttonLabels: ['確認']
            });
        }
    };    
    logger.debug("selfCheckCtrl End");
} );

// 表示モード変更
var changeViewMode = function( mode ){
    var block;
    block = document.getElementById("noList");
    if( mode ){
        if( block !== null ){
            block.style.display = "none";
            block.innerHTML = "";
        }
//        document.getElementById("listHeader").style.display = "block";
    }else{
        if( block !== null ){
            block.style.display = "block";
            block.innerHTML = "未登録です";
        }
//        document.getElementById("listHeader").style.display = "none";
    }
};
var browserType="";

var viewLocationControl = function( ){
    var block;
    block = document.getElementById("existList");

    logger.debug( 'ブラウザ='+browserType );
    innerWidth = window.innerWidth;
    logger.debug( '幅='+ innerWidth );
    
    if( browserType === "Windows" ){
        if( block !== null ){
            block.style.marginTop = "35px";
        }
    }else if( browserType === "Android" ){
        if( block !== null ){
            block.style.paddingTop ="20px";
        }
    }else if( browserType === "iPhone" ){
        if( block !== null ){
            block.style.paddingTop ="60px";
        }
    }
    
    // タイトル
    // r1c2
    block = document.getElementById("r1c1");
    if( block !== null ){
        block.style.width = (innerWidth * 20 /100 )+ "px"; ;
    }
    block = document.getElementById("r1c2");
    if( block !== null ){
        block.style.width = (innerWidth * 62 /100 )+ "px"; ;
    }
    block = document.getElementById("r1c3");
    if( block !== null ){
        block.style.width = (innerWidth * 18 /100 )+ "px"; ;
    }
    // 見出し
    block = document.getElementById("r2c1");
    if( block !== null ){
        block.style.width = (innerWidth * 20 /100 )+ "px"; ;
    }
    block = document.getElementById("r2c2");
    if( block !== null ){
        block.style.width = (innerWidth * 28 /100) + "px";
    }
    block = document.getElementById("r2c3");
    if( block !== null ){
        block.style.width = (innerWidth * 34 /100) + "px";
    }
    block = document.getElementById("r2c4");
    if( block !== null ){
        block.style.width = (innerWidth * 18 /100) + "px";
    }
    
};
