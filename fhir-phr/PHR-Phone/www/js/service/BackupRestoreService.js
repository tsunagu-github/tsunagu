//phrService.factory('backupRestoreService', function ($http, $q, patientManagementService) {
phrService.factory('backupRestoreService', function( $http, $q, patientManagementService ){

    var viewStatus = null;
    var backupRestoreData = null;
    return {
        createBackupRestoreData: function () {
            if (backupRestoreData === null) {
                backupRestoreData = new Object();
            }
            return backupRestoreData;
        },
        entryOperation: function( successAction, bkRedata, mode ) {
            logger.debug("bkRedata:" + JSON.stringify( bkRedata ));
            var webAction = new WebAction();
            var sendObject = new Object();
            
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "OperationBackupRestore";
            sendObject.operationMode = mode;
            sendObject.restorePassword = (mode==="U")?"":bkRedata.bkPassword;
            sendObject.oldPhrId = bkRedata.oldPhrId;
            sendObject.phrAssociationId = bkRedata.phrAssociationId;
            sendObject.projectId = bkRedata.projectId;
            //sendObject.backupRestoreEventId = getBackupRestoreEventId();

            logger.debug( "端末⇒サーバ");
            logger.debug( JSON.stringify( sendObject ));
            console.log( JSON.stringify( sendObject ));
            
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
        },
        getViewStatus: function () {
            if (viewStatus === null) {
                viewStatus = new Object();
            }
            var currentStatus = getCurrentStatus();
            viewStatus.message = currentStatus;
            var currentSubStatus = getCurrentSubStatus();
            if( currentSubStatus.length === null ){
                currentSubStatus = "";
            }
            viewStatus.subMessage = currentSubStatus;
            viewStatus.modeSelected = true;
            return viewStatus;
        },
        getCurrentStatus: function () {
            console.log( 'getCurrentStatus in');
            return getCurrentStatus();
        },
        setCurrentStatus: function( newStatus, newSubStatus ){
            console.log( 'setCurrentStatus in');
            setCurrentStatus( newStatus, newSubStatus );
        },
        setIdInformations: function(  phrAssociationId, projectId ){
            logger.debug( 'setIdInformations in');
            setIdInformations(  phrAssociationId, projectId );
        },        
        getCurrentSubStatus: function () {
            return getCurrentSubStatus();
        },
        setPassword: function( passwordString ){
            backupRestoreData.bkPassword = passwordString;
        },
        getSavedAssociationId: function () {
            return getSavedAssociationId();
        },
        getSavedProjectId: function () {
            return getSavedProjectId();
        }
        
    };
});

// ステータス
var STS_NO = "0";
var STS_OK = "1";
var STS_BK_NOW = "2";
var STS_RE_NOW = "3";
var STS_BK_ERROR = "91";
var STS_RE_ERROR = "92";

// 現在のステータスを取得
var getCurrentStatus = function( ){
    console.log( '状態取得中');
    var cs = window.localStorage.getItem("PHR-BR-S");
    if( cs === null || cs.length===0 ){
        console.log( '状態を取得できません。[バックアップなし]にします。');
        cs = STS_NO;
        setCurrentStatus( cs, " " );
    }else{
        console.log( '状態取得できました='+cs );
    }
    return cs;
};

var getCurrentSubStatus = function( ){
    console.log( 'サブ状態取得中');
    var cs_s = window.localStorage.getItem("PHR-BR-SS");
    if( cs_s === null || cs_s.length===0 ){
        console.log( 'サブ状態を取得できません。[空]にします。');
        cs_s = "";
    }else{
        console.log( 'サブ状態取得できました='+cs_s );
    }
    return cs_s;
};

var setCurrentStatus = function( newStatus, newSubStatus ){
    console.log( '状態設定開始-['+newStatus+']['+newSubStatus+']');
    window.localStorage.setItem( "PHR-BR-S", newStatus );
    window.localStorage.setItem( "PHR-BR-SS", newSubStatus );
    console.log( '状態設定終了');
};

var getSavedAssociationId = function( ){
    console.log( '事業者ID取得中');
    var asid = window.localStorage.getItem("PHR-BR-ASID");
    if( asid === null || asid.length===0 ){
        console.log( '事業者IDを取得できません。[空]にします。');
        asid = "";
    }else{
        console.log( '事業者ID取得できました='+asid );
    }
    return asid;
};
var getSavedProjectId = function( ){
    console.log( 'プロジェクトID取得中');
    var proid = window.localStorage.getItem("PHR-BR-PROID");
    if( proid === null || proid.length===0 ){
        console.log( 'プロジェクトIDを取得できません。[空]にします。');
        proid = "";
    }else{
        console.log( 'プロジェクトID取得できました='+proid );
    }
    return proid;
};
//var getBackupRestoreEventId = function( ){
//    console.log( 'バックアップリストアイベントID取得中');
//    var breid = window.localStorage.getItem("PHR-BR-BREID");
//    if( breid === null || breid.length===0 ){
//        console.log( 'バックアップリストアイベントIDを取得できません。[空]にします。');
//        breid = "";
//    }else{
//        console.log( 'バックアップリストアイベントID取得できました='+breid );
//    }
//    return breid;
//};
var setIdInformations = function( phrAssociationId, projectId){
    logger.debug( 'ID保存開始-['+phrAssociationId+']['+projectId+']');
    window.localStorage.setItem( "PHR-BR-ASID", phrAssociationId );
    window.localStorage.setItem( "PHR-BR-PROID", projectId );
    //window.localStorage.setItem( "PHR-BR-BREID", backupRestoreEventId );
};
