phrService.factory('oneTimePasswordService', function ($http, $q, patientManagementService) {
            
    var oneTimePassword = null;
    
    return {
        createOneTimePassword: function(){
            if (oneTimePassword === null) {
                oneTimePassword = new Object();
                oneTimePassword.phrId= "";
                oneTimePassword.oneTimePassword = "";
                oneTimePassword.expirationDate = "";
            }
            return oneTimePassword;
        },
	getOneTimePassword: function(successAction){

            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetOneTimePassword";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
	},
	setOneTimePassword: function( phrId, password, expirationDate ) {
            if( phrId !== null ){
                oneTimePassword.phrId= phrId;
            }
            if( password !== null ){
                oneTimePassword.password = password;
            }
            if( expirationDate !== null ){
                oneTimePassword.expirationDate = expirationDate;
            }
            logger.debug("サービスの中");
            logger.debug(JSON.stringify(oneTimePassword));
        },
        sendOneTimePassword: function( successActionSent ) {
            //  保険者へワンタイムパスワードを通知する
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "SendOneTimePassword";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.password = oneTimePassword.password;
            sendObject.expirationDate = oneTimePassword.expirationDate;
            webAction.setSuccessAction( successActionSent );
            logger.debug( JSON.stringify(sendObject) );
            webAction.action($http, $q, sendObject);
        }
    };
});
