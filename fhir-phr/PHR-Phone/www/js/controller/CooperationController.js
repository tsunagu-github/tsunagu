//  患者連携設定コントローラ
phrCtrl.controller( "cooperationListCtrl", function( $scope, cooperationListService ){

    logger.debug("cooperationListCtrl Start");

    var cooperationList = [];
    cooperationList = cooperationListService.createMedicalList();

    var operation = cooperationListService.getOperation();

    var oneCooperationData = cooperationListService.getoneCooperationData();

    var successAction = function (data) {
        logger.debug(JSON.stringify( data ));
        $scope.cooperationList = data.cooperationList;
        if (data.cooperationList == null || data.cooperationList.length == 0) {
            $scope.isTableView = "none";
            $scope.isMsgView = "block";
        } else {
            $scope.isTableView = "block";
            $scope.isMsgView = "none";
        }
    };

    //  詳細ページへの遷移
    $scope.goToShowDetail = function(item) {
        cooperationListService.setOperation( true );
        myNavigator.pushPage("templates/cooperation-detail-ud.html", {item: item});
    };
    
    //  新規登録ページへの遷移
    $scope.goToEntryDetail = function(item) {
        cooperationListService.setOperation( false );
        cooperationListService.setCooperationList( $scope.cooperationList );
        myNavigator.pushPage("templates/cooperation-detail-c.html", {item: item});
    };

    ons.ready(function() {
        cooperationListService.getMedicalList( successAction );
    });

    logger.debug("cooperationListCtrl End");
});

//  詳細閲覧コントローラ
phrCtrl.controller("cooperationDetailCtrl", function( $scope,cooperationListService){

    logger.debug("cooperationDetailCtrl Start");
    
    var cooperationList = [];
    var operationResult;
    var oneCooperationData;

    var successAction = function (data) {
        logger.debug(JSON.stringify( data ));
        $scope.cooperationList = data.cooperationList;
        ons.notification.confirm({
            title: 'おしらせ',
            message: '完了しました。',
            buttonLabels: ['確認']
        });
        cooperationList = null;
        myNavigator.resetToPage("templates/cooperation-list.html", {animation:"slide"}).then( function (){
            myNavigator.insertPage( 0, "templates/menu-list.html" );
        })
    };

    var successAction2 = function (data) {
        logger.debug(JSON.stringify( data ));
        $scope.operationResult = data.status;
        cooperationListService.getMedicalList( successAction );
    };

    ons.ready(function() {
        $scope.oneCooperationData = myNavigator.topPage.pushedOptions.item;
    });

    //  患者連携設定削除
    $scope.deleteConfirm = function ( value ) {
        //var mod = material ? 'material' : undefined;
        ons.notification.confirm({
            title: '削除',
            message: '削除します。よろしいですか。',
            buttonLabels: ['はい', 'いいえ'],
            //modifier: mod,
            callback: function (idx) {
                switch (idx) {
                case 0://　はい→削除
                    cooperationListService.entryOperation( successAction2 , $scope.oneCooperationData, "D" );
                    break;
                case 1://　いいえ
                    break;
                }
            }
        });
    };    

    //  患者連携設定（更新・登録）
    $scope.entryConfirm = function ( ) {
 
        var operation = cooperationListService.getOperation();

        if( operation.mode ){
            logger.debug('update');
            cooperationListService.entryOperation( successAction2 , $scope.oneCooperationData, (operation.mode)?"U":"C" );
        }else{
            logger.debug('create');
            var resultObj = cooperationListService.checkEntryData( $scope.oneCooperationData, cooperationListService.getCooperationList() );
            if( resultObj.result ){
                cooperationListService.entryOperation( successAction2 , $scope.oneCooperationData, (operation.mode)?"U":"C" );
            }else{
                ons.notification.confirm({
                    title: 'エラー',
                    message: resultObj.message,
                    buttonLabels: ['確認']
                });
            }
        }

    };    

    logger.debug("cooperationDetailCtrl End");

} );
    