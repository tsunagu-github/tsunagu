//  おしらせ・メッセージ コントローラ
phrCtrl.controller("announcementsCtrl", function($scope, $timeout, $interval, announcementsService){
  $scope.announcements = [];
  $scope.responseArray = [];
  $scope.isLoading = false;
  $scope.reachEnd = false;
  $scope.loadedIndex = 0;
  $scope.loadedMsg = 0;
  $scope.loadedInfo = 0;
  $scope.loadSize = 10;

    var loadMore = function() {
        var loaded = 0;
        for (var i = $scope.loadedIndex; i < $scope.loadedIndex + $scope.loadSize; i++) {
            if (i >= $scope.responseArray.length) {
                $scope.reachEnd = true;
                break;
            }
            $scope.announcements.push($scope.responseArray[i]);
            loaded++;
            if ($scope.responseArray[i].type === 2) {
                $scope.loadedMsg++;
            } else {
                $scope.loadedInfo++;
            }
        }
        //$scope.isLoading = false;
        $scope.loadedIndex += loaded;
    };

    $scope.loadMoreOnce = function() {
        return $timeout(function() {
            loadMore();
        }, 1000);
    };

    var stop;
    $scope.loadMoreTen = function() {
        return stop = $interval(function() {
            if (!$scope.reachEnd && (
                    ($scope.segment === "announce" && $scope.loadedInfo < $scope.loadSize) || 
                    ($scope.segment === "message" && $scope.loadedMsg < $scope.loadSize)) ) {
                loadMore();
            } else {
                $scope.stopLoad();
            }
        }, 1000);
    };

    $scope.stopLoad = function() {
        if (angular.isDefined(stop)) {
            $interval.cancel(stop);
            stop = undefined;
            $scope.isLoading = false;
        }
    };

    $scope.onInfiniteScroll = function(done) {
        if($scope.isLoading) return;

        $scope.loadMoreOnce().then( function() {
                $scope.isLoading = false;
                done();
            });

        $timeout(function() {
           $scope.isLoading = true;  
        });
    };

    $scope.refresh = function() {
        announcementsService.goToListAction("none");
    };

    var doNothing = function() {
    };

    $scope.loadMoreIfSegmentBlank = function() {
        if (angular.isDefined(stop) || $scope.isLoading) {
            return;
        }

        $scope.loadMoreTen();

        $timeout(function() {
           $scope.isLoading = true;  
        });
    };

    /**
     * コミュニケーション情報取得Server接続処理成功時のAction
     */
    var successAction = function (data) {
        $scope.responseArray = data.msgInfoList;
        announcementsService.setAllUnreadCount(data.allUnreadCount);
        announcementsService.setInfoUnreadCount(data.infoUnreadCount);
        announcementsService.setMsgUnreadCount(data.msgUnreadCount);

        if($scope.segment === "all") {
            $scope.onInfiniteScroll(doNothing);
        } else {
            $scope.loadMoreIfSegmentBlank();
        }
    };

    ons.ready(function() {
        $scope.service = announcementsService;
        $scope.segment = "all";
        announcementsService.getList(successAction);
        if(ons.platform.isAndroidTablet() || ons.platform.isIPad()) {
            //画面が大きい場合は1回に20件ロードする
            $scope.loadSize = 20;
        }
        logger.debug("一回のロード件数：" + $scope.loadSize);
    });

    $scope.showDetail = function(item) {
        myNavigator.pushPage("templates/announcements-details.html", {item: item});
    };
    
    $scope.createMessage = function() {
        myNavigator.pushPage("templates/announcements-entry.html");
    };
})

phrCtrl.controller('announceDetailCtrl', function($scope, announcementsService) {
    /**
     * コミュニケーション詳細取得Server接続処理成功時のAction
     */
    var successAction = function (data) {
        var msgInfoList = data.msgInfoList;
        if(msgInfoList && msgInfoList.length > 0) {
            $scope.announce.bodyText = msgInfoList[0].bodyText;
            $scope.announce.insurerNo = msgInfoList[0].insurerNo;
            $scope.announce.organizationCd = msgInfoList[0].organizationCd;
            if($scope.announce.onNotification){
                $scope.announce.senderName = msgInfoList[0].senderName;
                $scope.announce.type = msgInfoList[0].type;
                $scope.announce.date = msgInfoList[0].date;
                $scope.announce.subject = msgInfoList[0].subject;
            }else if(!$scope.announce.readFlg && !$scope.announce.self) {
                $scope.announce.readFlg = true;
                var alltmp = announcementsService.getAllUnreadCount();
                announcementsService.setAllUnreadCount(alltmp-1);
                if($scope.announce.type===2) {
                    var msgtmp = announcementsService.getMsgUnreadCount();
                    announcementsService.setMsgUnreadCount(msgtmp-1);
                } else {
                    var infotmp = announcementsService.getInfoUnreadCount();
                    announcementsService.setInfoUnreadCount(infotmp-1);
                }
            }
        }
    };

    ons.ready(function() {
        $scope.announce = myNavigator.topPage.pushedOptions.item;
        announcementsService.getDetail($scope.announce.id, $scope.announce.seq, $scope.announce.onNotification, successAction);
    });
    
    $scope.replyTo = function(item) {
        myNavigator.pushPage("templates/announcements-entry.html", {item: item});
    };
})

phrCtrl.controller('announceEntryCtrl', function($scope, announcementsService) {
    $scope.message = {};
    $scope.isReply = false;
    $scope.addresslist = [];
 
    /**
     * 宛先アドレスリスト取得Server接続処理成功時のAction
     */
    var successAction = function (data) {
        $scope.addresslist = data.addressList;
    };

    ons.ready(function() {
        $scope.service = announcementsService;
        $scope.message.received = myNavigator.topPage.pushedOptions.item;
        if($scope.message.received) {
            $scope.isReply = true;
            $scope.message.send = {};
            $scope.message.send.subject = "Re: " + $scope.message.received.subject;
        } else {
            $scope.service.getAddress(successAction);
        }
    });
    
    $scope.showConfirm = function() {
        if($scope.messageForm.$valid) {
            myNavigator.pushPage("templates/announcements-confirm.html", {message: $scope.message});
        }
    };
    
    $scope.selectAddress = function(item) {
        $scope.message.address = item;
        $scope.message.address.type = 2;
    };
})

phrCtrl.controller('announceConfirmCtrl', function($scope, $timeout, announcementsService) {
    $scope.isReply = false;

    ons.ready(function() {
        $scope.service = announcementsService;
        $scope.sendMessage = {};
        $scope.sendMessage.received = myNavigator.topPage.pushedOptions.message.received;
        if($scope.sendMessage.received) {
            $scope.isReply = true;
            $scope.sendMessage.address = {};
            $scope.sendMessage.address.name = $scope.sendMessage.received.senderName;
            $scope.sendMessage.address.type = $scope.sendMessage.received.type;
            $scope.sendMessage.address.insurerNo = $scope.sendMessage.received.insurerNo;
            $scope.sendMessage.address.organizationCd = $scope.sendMessage.received.organizationCd;
        } else {
            $scope.sendMessage.address = myNavigator.topPage.pushedOptions.message.address;
        }
        $scope.sendMessage.content = myNavigator.topPage.pushedOptions.message.send;
        ons.createPopover('templates/announce-send-popover.html')
           .then(function(popover) {
                $scope.popover = popover;
           });
    });
    
    /**
     * コミュニケーション作成Server接続処理成功時のAction
     */
    var successAction = function (data) {
        if (data.status === "0") {
           $scope.popover.show("#submit");
           announcementsService.goToListAction("simpleslide");
        
           $timeout(function() {
               $scope.popover.hide();
           }, 700);
       }
    };
    $scope.onSubmit = function() {
        announcementsService.createMessage(successAction, $scope.sendMessage);
    };
    
})
