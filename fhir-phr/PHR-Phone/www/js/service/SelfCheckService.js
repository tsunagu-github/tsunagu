//　自己測定
phrService.factory('selfCheckService', function($http, $q, patientManagementService ){
    var selfCheckList = null;
    var operation = null;
    var oneSelfCheckData = null;
    var checkTypeList = null;
    var saved = null;
//    var operationMode ="";
    var getOfBeforeAfterHours = function(dateObj, number) {
        var result = false;
        if (dateObj && dateObj.getTime && number && String(number).match(/^-?[0-9]+$/)) {
            result = new Date(dateObj.getTime() + Number(number) * 60 * 60 * 1000);
        }
        return result;
    };
    
    var formatDate = function (date, format) {
        if (!format) format = 'YYYY-MM-DD hh:mm:ss.SSS';
        format = format.replace(/YYYY/g, date.getFullYear());
        format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
        format = format.replace(/DD/g, ('0' + date.getDate()).slice(-2));
        format = format.replace(/hh/g, ('0' + date.getHours()).slice(-2));
        format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
        format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
        if (format.match(/S/g)) {
            var milliSeconds = ('00' + date.getMilliseconds()).slice(-3);
            var length = format.match(/S/g).length;
            for (var i = 0; i < length; i++) format = format.replace(/S/, milliSeconds.substring(i, i + 1));
        }
        return format;
    };

    return {
        createCheckTypeList: function() {
            if(checkTypeList === null) {
                var makeList = [];
                var item = new Object();
                item.checked = true;
                logger.debug( JSON.stringify(item) );
                makeList[0] = item;
                var item = new Object();
                item.checked = true;
                logger.debug( JSON.stringify(item) );
                makeList[1] = item;
                var item = new Object();
                item.checked = true;
                logger.debug( JSON.stringify(item) );
                makeList[2] = item;
            }
            logger.debug( JSON.stringify(makeList) );
            return makeList;
        },
        createSelfCheckList: function () {
            if (selfCheckList === null) {
                selfCheckList = new Object();
            }
            return selfCheckList;
        },
        getSelfCheckList: function( successAction, agoDays ){
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "GetSelfCheckList";
            sendObject.agoDays = agoDays;
            logger.debug( JSON.stringify( sendObject ));
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
            return;
        },
        getOperation: function(){
            if( operation === null ){
                operation = new Object();
                operation.mode = "";
            }
            return operation;
        },
        setOperation: function( value ) {
            if( operation === null ){
                operation = new Object();
            }
            operation.mode = value;
        },
        getSaved: function(){
            if( saved === null ){
                saved = new Object();
                saved.mode = false;
                saved.data = null;
            }
            return saved;
        },
        setSaved: function( value, data ) {
            if( saved === null ){
                saved = new Object();
            }
            saved.mode = value;
            saved.data = data;
        },
        getOneSelfCheckData: function() {
            if( oneSelfCheckData=== null ){
                oneSelfCheckData = new Object();
                oneSelfCheckData.seqno="";
                oneSelfCheckData.checkDateView="";
                oneSelfCheckData.bloodPressureView="";
                oneSelfCheckData.systolicBP="";
                oneSelfCheckData.diastolicBP="";
                oneSelfCheckData.weightView="";
                oneSelfCheckData.weight="";
                oneSelfCheckData.bloodGlucoseView="";
                oneSelfCheckData.bloodGlucose="";
                oneSelfCheckData.observationEventId="";
                var dt = new Date();
                var hosei = getOfBeforeAfterHours( dt, -9 );
                logger.debug( formatDate(hosei,"YYYY-MM-DDThh:mm") );
                oneSelfCheckData.checkDate= new Date(formatDate(hosei,"YYYY-MM-DDThh:mm"));
                logger.debug( JSON.stringify( oneSelfCheckData.checkDate ));
            }
            return oneSelfCheckData;
        },
        resetOneSelfCheckData: function( ) {
            oneSelfCheckData= null;
        },
        setOnselfCheckDataCheckDate : function( value ) {
            if( oneSelfCheckData !== null ){
                oneSelfCheckData.checkDate = value;
            }
        },
        entryOperation: function( successAction, oneData, mode ) {
            logger.debug( JSON.stringify( oneData ));
            var webAction = new WebAction();
            var sendObject = new Object();
            
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.action = "OperationSelfCheck";
            sendObject.checkDate = oneData.checkDate;
            sendObject.systolicBp = oneData.systolicBP;
            sendObject.diastolicBp = oneData.diastolicBP;
            sendObject.weight = oneData.weight;
            sendObject.bloodGlucose = oneData.bloodGlucose;
            sendObject.operationMode = mode;
            sendObject.observationEventId = oneData.observationEventId;
            
            logger.debug( "端末⇒サーバ");
            logger.debug( JSON.stringify( sendObject ));
            
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
            
        },
        checkEntryData: function( oneData ) {
            var resultObj = new Object();
            resultObj.result = true;
            resultObj.message = "";
            if( oneData.systolicBP === "" &&
                oneData.diastolicBP === "" &&
                oneData.weight.length === "" &&
                oneData.bloodGlucose === "" ){
                resultObj.result = false;
            }
            if( resultObj.result === true ){
                resultObj.message = "OK";
            }else{
                resultObj.message = "全項目未入力です";
            }
            return resultObj;
        }
    };
});
