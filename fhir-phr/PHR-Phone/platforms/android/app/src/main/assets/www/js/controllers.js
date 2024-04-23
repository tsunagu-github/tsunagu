phrCtrl
.controller("SideMenuCtrl", function($scope){
    
    /**
     * 指定したページに遷移する
     * @param {type} page
     * @returns {undefined}
     */
    $scope.setPage = function(page) {
        logger.debug('setPage ' + page);
        // リセットページで該当ページに遷移した後、スタックの最初にメニューを入れる

        myNavigator.resetToPage(page, { animation: "none" }).then(function() {
            myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" });
        });
        /*    
        myNavigator.resetToPage(page, { animation: "slide" }).then(function() {
            // メニューの場合は無視する
            if (page != "templates/menu-list.html") {
                myNavigator.insertPage(0, 'templates/menu-list.html', { animation: "slide" });
            }
        });
        */
        app.slidingMenu.closeMenu();
    };
})

.controller("HomeCtrl", ["$http", function(){
    
    GoToMainMenu = function(){
        logger.debug('connect');
        indexNavigator.
        indexNavigator.pushPage('templates/main-menu.html',{});
    }

}]);
