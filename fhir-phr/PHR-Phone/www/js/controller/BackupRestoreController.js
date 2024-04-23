//phrCtrl.controller("backupRestoreCtrl", function($scope, backupRestoreService, patientManagementService){
phrCtrl.controller("backupRestoreCtrl", function( $scope, backupRestoreService, patientManagementService ){
	logger.debug("backupRestoreCtrl Start");

	var backupRestoreData = null; 
	backupRestoreData = backupRestoreService.createBackupRestoreData();

	$scope.viewStatus = backupRestoreService.getViewStatus();
	console.log( 'VS['+ $scope.viewStatus + ']' );
        var errMessage = "";

	// 削除確認
	$scope.goRemove = function ( ) {
		ons.notification.confirm({
			title: '消去',
			message: '消去します。よろしいですか。',
			buttonLabels: ['はい', 'いいえ'],
			callback: function (idx) {
				switch (idx) {
				case 0://　はい→削除
                                    console.log( JSON.stringify($scope.backupRestoreData));
                                    backupRestoreService.entryOperation( successActionDelete , $scope.backupRestoreData, "D" );
                                    break;
				case 1://　いいえ
					break;
				}
			}
		});
        //$scope.backupRestoreData = "";
	};	  

	// 更新
	$scope.goReload = function ( ) {
            console.log( JSON.stringify($scope.backupRestoreData));
            backupRestoreService.entryOperation( successActionReload , $scope.backupRestoreData, "U" );
	};

	// バックアップ
	$scope.goBackup = function ( ) {
		ons.notification.confirm({
			title: 'バックアップ',
			message: 'バックアップします。よろしいですか。',
			buttonLabels: ['はい', 'いいえ'],
			callback: function (idx) {
				switch (idx) {
				case 0://　はい→バックアップ
                    console.log( JSON.stringify($scope.backupRestoreData));
					backupRestoreService.entryOperation( successActionBackup , $scope.backupRestoreData, "B" );
					break;
				case 1://　いいえ
					break;
				}
			}
		});
	};	  

	// リストア
	$scope.goRestore = function ( ) {
		ons.notification.confirm({
			title: 'リストア',
			message: 'リストアします。よろしいですか。',
			buttonLabels: ['はい', 'いいえ'],
			callback: function (idx) {
				switch (idx) {
				case 0://　はい→リストア
					backupRestoreService.entryOperation( successActionRestore , $scope.backupRestoreData, "R" );
					//　★成功したらサクセスアクション（リストア）で画面を更新する
					break;
				case 1://　いいえ
					break;
				}
			}
		});
	};	  

	//	タブ変更ふう
	$scope.tabChanged = function( selectMode ){
        $scope.viewStatus.modeSelected = selectMode;
        var currentStatus = backupRestoreService.getCurrentStatus();
        setView( currentStatus, selectMode, $scope.viewStatus.subMessage );
    };

    // バックアップ要求の結果
    var successActionBackup = function (data) {
        logger.debug('バックアップ要求結果='+JSON.stringify( data ));
        if( data.progressStatus === STS_BK_NOW ){
            ons.notification.confirm({
                title: 'おしらせ',
                message: 'バックアップ開始しました',
                buttonLabels: ['確認']
            });
            backupRestoreService.setCurrentStatus( data.progressStatus, "" );
            backupRestoreService.setIdInformations(data.phrAssociationId, data.projectId);
            var currentStatus = backupRestoreService.getCurrentStatus();
            setView( currentStatus, $scope.viewStatus.modeSelected, $scope.viewStatus.subMessage );
        }else {
            ons.notification.confirm({
                title: 'バックアップエラー',
                message: '更新をタップして最新状態を確認してください',
                buttonLabels: ['確認']
            });
        }
        var currentStatus = backupRestoreService.getCurrentStatus();
        setView( currentStatus, $scope.viewStatus.modeSelected, $scope.viewStatus.subMessage );
    };
    
    // リストア要求の結果
    var successActionRestore = function (data) {
        logger.debug('リストア要求結果='+JSON.stringify( data ));
        if( data.progressStatus === STS_RE_NOW ){
            ons.notification.confirm({
                title: 'おしらせ',
                message: 'リストア開始しました',
                buttonLabels: ['確認']
            });
            backupRestoreService.setCurrentStatus( data.progressStatus, "" );
            var currentStatus = backupRestoreService.getCurrentStatus();
            setView( currentStatus, $scope.viewStatus.modeSelected, $scope.viewStatus.subMessage );
        }else {
            ons.notification.confirm({
                title: 'リストアエラー',
                message: '更新をタップして最新状態を確認してください',
                buttonLabels: ['確認']
            });
        }
        var currentStatus = backupRestoreService.getCurrentStatus();
        setView( currentStatus, $scope.viewStatus.modeSelected, $scope.viewStatus.subMessage );
    };
    
    // 更新の結果
    var successActionReload = function (data) {
        logger.debug('更新結果='+JSON.stringify( data ));
        var oldStatus = backupRestoreService.getCurrentStatus();
        backupRestoreService.setCurrentStatus( data.progressStatus, data.backupDate );
        errMessage = data.errorText;
        var currentStatus = backupRestoreService.getCurrentStatus();
        var backupDate =  backupRestoreService.getCurrentSubStatus();
        logger.debug('直前のステータス：'+oldStatus + '；　現ステータス：'+currentStatus);
        if(oldStatus===STS_RE_NOW && currentStatus != oldStatus){
                ons.notification.confirm({
                    title: 'リストア完了',
                    message: 'リストアが完了しました。',
                    buttonLabels: ['確認']
                }); 
                backupRestoreData.oldPhrId="";
            //backupRestoreService.setIdInformations("", "");        
        }else if(currentStatus==STS_NO || currentStatus==STS_BK_ERROR){
            backupRestoreService.setIdInformations("", "");
        }else if(currentStatus===STS_RE_ERROR){
        }
        setView( currentStatus, $scope.viewStatus.modeSelected, backupDate );
    };

    // 消去結果
    var successActionDelete = function (data) {
        logger.debug('消去結果='+JSON.stringify( data ));
        if( data.progressStatus === STS_NO ){
            ons.notification.confirm({
                title: 'おしらせ',
                message: '消去しました。',
                buttonLabels: ['確認']
            });
            backupRestoreService.setCurrentStatus( STS_NO, "" );
            backupRestoreService.setIdInformations( "", ""  );
        }else {
            ons.notification.confirm({
                title: '消去エラー',
                message: 'パスワードを再確認してください。',
                buttonLabels: ['確認']
            });
        }
        var currentStatus = backupRestoreService.getCurrentStatus();
        $scope.backupRestoreData = "";
        setView( currentStatus, $scope.viewStatus.modeSelected, $scope.viewStatus.subMessage );
    };

    //  見た目制御
    var setView = function( currentStatus, selectMode, subMessage ){
        setViewStatus( currentStatus, selectMode );
        setViewMessage( currentStatus, selectMode, subMessage );
        setViewPasswordLine( currentStatus, selectMode );
        setViewPHRIDLine( currentStatus, selectMode );
        setViewButtons( currentStatus, selectMode );
        setTabButtonsColor( selectMode )
        setRestoreIdInfo();
        backupRestoreData.bkPassword = "";
        $scope.backupRestoreData=backupRestoreData;//"";
    };

    var setViewStatus = function( currentStatus ){
        if( currentStatus === STS_NO ){
            displayControlInnerHTML( "statusArea","バックアップ未登録" );
        }else if( currentStatus === STS_OK ){
                //　ここに日付処理
            displayControlInnerHTML( "statusArea","バックアップOK" );
        }else if( currentStatus === STS_BK_NOW ){
            displayControlInnerHTML( "statusArea","バックアップ中" );
        }else if( currentStatus === STS_RE_NOW ){
            displayControlInnerHTML( "statusArea","リストア中" );
        }else if( currentStatus===STS_BK_ERROR || currentStatus===STS_RE_ERROR){
            displayControlInnerHTML( "statusArea","エラーが発生しました。" );
        }else{    
            displayControlInnerHTML( "statusArea","＊＊＊＊＊" );
        }
    };

    // 案内メッセージ制御
    var setViewMessage = function( currentStatus, selectMode, subMessage ){
        displayControlType1( "messageArea1","block" );
        displayControlType1( "messageArea2","none" );
        if( selectMode ){
            if( currentStatus === STS_NO || currentStatus===STS_BK_ERROR){
                displayControlInnerHTML( "messageArea1","パスワードとバックアップ時の各IDを入力してください。" );
            }else if( currentStatus === STS_OK ){
                displayControlInnerHTML( "messageArea1","パスワードとバックアップ時の各IDを入力してください。" );
            }else if( currentStatus === STS_BK_NOW ){
                displayControlInnerHTML( "messageArea1","更新をタップしてバックアップ結果を確認してください。" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlInnerHTML( "messageArea1","リストア実行中です。完了の確認には更新をタップしてください。" );
            }else if(currentStatus===STS_RE_ERROR){
                displayControlInnerHTML( "messageArea1","リストア中にエラーが発生しました。" + errMessage + "再度リストアを実行してください。" );
            }else{
                displayControlInnerHTML( "messageArea1","バックアップできません。" );
            }
        }else{
            if( currentStatus === STS_NO || currentStatus===STS_RE_ERROR){
                displayControlInnerHTML( "messageArea1","バックアップしてください。または、更新をタップして端末の情報を最新にしてください。" );
            }else if( currentStatus === STS_OK ){
                displayControlInnerHTML( "messageArea1", subMessage+ "にバックアップされました。" +
                        "<br/>リストアには、以下3つのIDとパスワードが必要です。控えをお取りください。<br/>" +
                        "<br/>PHR-ID : " + patientManagementService.getPatient().phrId +
                        "<br/>事業者ID : " + backupRestoreService.getSavedAssociationId() + 
                        "<br/>プロジェクトID : " + backupRestoreService.getSavedProjectId());
            }else if( currentStatus === STS_BK_NOW ){
                displayControlInnerHTML( "messageArea1","更新をタップしてバックアップ結果を確認してください。" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlInnerHTML( "messageArea1","リストア実行中です。完了の確認には更新をタップしてください。");//更新をタップしてリストア結果を確認してください。" );
            }else if( currentStatus===STS_BK_ERROR){
                displayControlInnerHTML( "messageArea1","バックアップ中にエラーが発生しました。" + errMessage + "再度バックアップを実行してください。" );
            }else{
                displayControlInnerHTML( "messageArea1","バックアップできません。" );
            }
        }
    };

    // パスワード行の表示・非表示
    var setViewPasswordLine = function( currentStatus, selectMode ){
        if( selectMode ){
            if( currentStatus === STS_NO || currentStatus===STS_BK_ERROR || currentStatus===STS_RE_ERROR){
                displayControlType1( "passwordLine","block" );
            }else if( currentStatus === STS_OK ){
                displayControlType1( "passwordLine","block" );
            }else if( currentStatus === STS_BK_NOW ){
                displayControlType1( "passwordLine","none" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlType1( "passwordLine","none" );
            }else{
                displayControlType1( "passwordLine","none" );
            }
        }else{
            if( currentStatus === STS_NO || currentStatus===STS_BK_ERROR || currentStatus===STS_RE_ERROR){
                displayControlType1( "passwordLine","block" );
            }else if( currentStatus === STS_OK ){
                displayControlType1( "passwordLine","block" );
            }else if( currentStatus === STS_BK_NOW ){
                displayControlType1( "passwordLine","none" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlType1( "passwordLine","none" );
            }else{
                displayControlType1( "passwordLine","none" );
            }
        }
    };

    // 前PHR-ID
    var setViewPHRIDLine = function( currentStatus, selectMode ){
        if( selectMode ){
            if( currentStatus === STS_NO || currentStatus===STS_BK_ERROR || currentStatus===STS_RE_ERROR){
                displayControlType1( "oldPhrIdLine","block" );
            }else if( currentStatus === STS_OK ){
                displayControlType1( "oldPhrIdLine","block" );
            }else if( currentStatus === STS_BK_NOW ){
                displayControlType1( "oldPhrIdLine","none" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlType1( "oldPhrIdLine","none" );
            }else{
                displayControlType1( "oldPhrIdLine","none" );
            }
        }else{
            displayControlType1( "oldPhrIdLine","none" );
        }
    };

    // 下部のボタン表示
    var setViewButtons = function( currentStatus, selectMode ){
        if( selectMode ){
            if( currentStatus === STS_NO || currentStatus===STS_BK_ERROR || currentStatus===STS_RE_ERROR){
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","visible" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else if( currentStatus === STS_OK ){
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","visible" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else if( currentStatus === STS_BK_NOW ){
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else{
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","hidden" );
                displayControlType2( "removeButton","hidden" );
            }
        }else{
            if( currentStatus === STS_NO || currentStatus===STS_BK_ERROR || currentStatus===STS_RE_ERROR){
                displayControlType2( "backupButton","visible" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else if( currentStatus === STS_OK ){
                displayControlType2( "backupButton","visible" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","visible" );
            }else if( currentStatus === STS_BK_NOW ){
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else if( currentStatus === STS_RE_NOW ){
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","visible" );
                displayControlType2( "removeButton","hidden" );
            }else{
                displayControlType2( "backupButton","hidden" );
                displayControlType2( "restroreButton","hidden" );
                displayControlType2( "reloadButton","hidden" );
                displayControlType2( "removeButton","hidden" );
            }
        }
    };

    var setTabButtonsColor = function( selectMode ){
        if( selectMode ){
            displayControlBkColor( "tabBackupButton","white" );
            displayControlBkColor( "tabRestoreButton","gold" );
        }else{
            displayControlBkColor( "tabBackupButton","gold" );
            displayControlBkColor( "tabRestoreButton","white" );
        }
    };

    var displayControlType1 = function( parts, type ){
        var obj = document.getElementById( parts );
        if( obj !== null ){
            obj.style.display=type;
        }else{
            console.log( 'not found Object(T1)='+parts);
        }
    };

    var displayControlType2 = function( parts, type ){
        var obj = document.getElementById( parts );
        if( obj !== null ){
            obj.style.visibility=type;
        }else{
            console.log( 'not found Object(T2)='+parts);
        }
    };

    var displayControlInnerHTML = function( parts, html ){
        var obj = document.getElementById( parts );
        if( obj !== null ){
            obj.innerHTML = html;
        }
    };

    var displayControlColor = function( parts, color ){
        var obj = document.getElementById( parts );
        if( obj !== null ){
            obj.style.color = color;
        }
    };

    var displayControlBkColor = function( parts, color ){
        var obj = document.getElementById( parts );
        if( obj !== null ){
            obj.style.backgroundColor  = color;
        }
    };

    var setRestoreIdInfo=function(){
        if(backupRestoreService.getSavedAssociationId()==""){
            backupRestoreData.phrAssociationId = "";
            backupRestoreData.projectId = "";
        }else{
            backupRestoreData.phrAssociationId = backupRestoreService.getSavedAssociationId();
            backupRestoreData.projectId = backupRestoreService.getSavedProjectId();            
        }
    }
    
    //	起動時
	ons.ready( function() {
        $scope.viewStatus.modeSelected = false;
        var currentStatus = backupRestoreService.getCurrentStatus();
        setView( currentStatus, $scope.viewStatus.modeSelected, $scope.viewStatus.subMessage );
	});

	logger.debug("backupRestoreCtrl End");

});

// ステータス
var STS_NO = "0";
var STS_OK = "1";
var STS_BK_NOW = "2";
var STS_RE_NOW = "3";
var STS_BK_ERROR = "91";
var STS_RE_ERROR = "92";

