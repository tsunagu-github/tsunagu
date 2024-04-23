phrService.factory('announcementsService', function ($http, $q, patientManagementService) {
    return {
        allUnreadCount: 0,
        setAllUnreadCount: function (value) {
          this.allUnreadCount = value;
        },
        getAllUnreadCount: function () {
            return this.allUnreadCount;
        },
        infoUnreadCount: 0,
        setInfoUnreadCount: function (value) {
          this.infoUnreadCount = value;
        },
        getInfoUnreadCount: function () {
            return this.infoUnreadCount;
        },
        msgUnreadCount: 0,
        setMsgUnreadCount: function (value) {
          this.msgUnreadCount = value;
        },
        getMsgUnreadCount: function () {
            return this.msgUnreadCount;
        },
        /**
         * おしらせ・メッセージ情報を返却する
         * @returns 
         */
        getList: function (successAction, errorAction) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetCommunicationList";
            //sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.phrId = phrJs.getTarminalInfo().phrId;

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
        /**
         * 指定されたおしらせ・メッセージの詳細を返却し、既読にする
         * @returns 
         */
        getDetail: function (communicationId, seq, onNotification, successAction) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetCommunicationDetail";
            sendObject.phrId = patientManagementService.getPatient().phrId;
            sendObject.id = communicationId;
            sendObject.seq = seq;
            sendObject.onNotification = onNotification;

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
        /**
         * 宛先のアドレスリストを取得する
         * @returns 
         */
        getAddress: function (successAction) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "GetCommunicationAddress";
            sendObject.phrId = patientManagementService.getPatient().phrId;

            webAction.setSuccessAction(successAction);
            webAction.action($http, $q, sendObject);
        },
        /**
         * メッセージを作成する
         * @returns
         */
        createMessage : function (successActionCreate, oneData) {
            var webAction = new WebAction();
            var sendObject = new Object();
            sendObject.action = "OperationCommunication";
            var patient = patientManagementService.getPatient();
            sendObject.phrId = patient.phrId;
            sendObject.senderName = patient.familyName + "　" + patient.givenName;
            sendObject.subject = oneData.content.subject;
            sendObject.bodyText = oneData.content.text;
            sendObject.receiver = [];
            var receiverObj = new Object();
            receiverObj.insurerNo = oneData.address.insurerNo;
            receiverObj.organizationCd = oneData.address.organizationCd;
            sendObject.receiver.push(receiverObj);

            webAction.setSuccessAction(successActionCreate);
            webAction.action($http, $q, sendObject);
        },
        /**
         * 編集を破棄して一覧に戻るAction関数
        */
        goToListAction : function(animationStr) {
            myNavigator.resetToPage("templates/announcements.html", {animation:animationStr}).then( function (){
                myNavigator.insertPage( 0, "templates/menu-list.html" );
            });
        }
    };
})
