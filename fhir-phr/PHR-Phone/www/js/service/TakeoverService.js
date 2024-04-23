phrService.factory('takeOverService', function ($http, $q, patientManagementService) {
            
    var takeOver = null;
    
    return {
        createTakeOver: function(){
            if (takeOver === null) {
                takeOver = new Object();
                takeOver.phrId= "";
                takeOver.takeOverCode = "";
                takeOver.expirationDate = "";
                takeOver.takeOverPassword = "";
            }
            return takeOver;
        },
        clearTakeOver: function(){
            takeOver = null;
        },
	getTakeOver: function( successAction ){
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetTakeOverCode";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.takeOverPassword = takeOver.takeOverPassword;

            logger.debug( JSON.stringify( sendObject ));
            
            webAction.setSuccessAction( successAction );
            webAction.action($http, $q, sendObject);
	},
	setTakeOver: function( phrId, takeOverCode, takeOverPassword, expirationDate) {
            if( phrId !== null ){
                takeOver.phrId= phrId;
            }
            if( takeOverCode !== null ){
                takeOver.takeOverCode = takeOverCode;
            }
            if( takeOverPassword !== null ){
                takeOver.takeOverPassword = takeOverPassword;
            }
            if( expirationDate !== null ){
                takeOver.expirationDate = expirationDate;
            }
        }
    };
})
