phrService
.factory('announcementsService', function($http, $q) {
  var deferred = $q.defer();
  //テスト用モックJSON＆トークン
  $http.get('mock-data/mock-announcements.json', {
        headers: {
            "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaHJpZCI6IjEyMzQ1Njc4OTAiLCJyb2xlIjoiUGF0aWVudCJ9.cclNxlhSSI_2KMs2gP1EBirU3DTAi-7kmlkML1P0Gw0"
        }
    }).then(function(data)
  {
     deferred.resolve(data);
  });

  return {
    getList: function() {
        return deferred.promise;
    }
  };
})
;
