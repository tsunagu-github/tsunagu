//  患者連携設定
phrService.factory('cooperationListService', function($http, $q, patientManagementService ){
    var cooperationList = null;
    var operation = null;
    var oneCooperationData = null;
    
    return {
        createMedicalList: function () {
            if (cooperationList === null) {
                cooperationList = new Object();
            }
            return cooperationList;
        },
        getMedicalList: function( successAction ){
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "GetCooperationList";

            logger.debug( JSON.stringify( sendObject ));
            
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
        },
        getCooperationList: function() {
            return cooperationList;
        },
        setCooperationList: function( value ) {
            cooperationList = value;
        },
        getOperation: function( ){
            if( operation === null ){
                operation = new Object();
                operation.mode = "";
            }
            return operation;
        },
        setOperation: function( value ) {
            operation.mode = value;
        },
        getoneCooperationData: function( ){
            if( oneCooperationData === null ){
                oneCooperationData = new Object();
                oneCooperationData.patientId = "";
                oneCooperationData.medicalAgencyCode = "";
            }
            return oneCooperationData;
        },
        entryOperation: function( successAction, oneData, mode ) {
            logger.debug( JSON.stringify( oneData ));
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "OperationCooperation";
            sendObject.medicalCd = oneData.medicalAgencyCode;
            sendObject.patientId = oneData.patientId;
            sendObject.operationMode = mode;
            sendObject.agreesToShare = oneData.agreesToShare;

            logger.debug( JSON.stringify( sendObject ));
            
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
        },
        checkEntryData: function( oneData, cooperationList ) {
            var resultObj = new Object();
            resultObj.result = true;
            resultObj.message = "";
            for( var cnt=0;cnt < cooperationList.length; cnt++ ){
                if( cooperationList[cnt].medicalAgencyCode == oneData.medicalAgencyCode ){
                    resultObj.result = false;
                }
            }
            if( resultObj.result === true ){
                resultObj.message = "OK";
            }else{
                resultObj.message = "この医療機関は登録済です。";
            }
            return resultObj;
        }

    };

   
});
