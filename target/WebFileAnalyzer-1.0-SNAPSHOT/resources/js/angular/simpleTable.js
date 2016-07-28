angular.module('simpleTable', [])
.controller('controller', function($scope, $http, $filter) {
  $scope.sortType     = 'fileName'; // set the default sort type
  $scope.sortReverse  = false;  // set the default sort order
  $scope.search   = '';     // set the default search/filter term
  $scope.files = [];
  $scope.linkName ="fileName";
  $scope.keys = [];
  $scope.filterCheck = false;
  $scope.moreThan = 10;
  $scope.changeCheck = function(){
      $scope.get();
  }
  $scope.get = function(){
    $http({method: "GET",
        url: !$scope.filterCheck ? "getFiles" : "getFiles?moreThen=" + $scope.moreThan}).
            success(function (data, status) {
                if (status === 200) {
                    $scope.files = data.files;
                    if($scope.files !== undefined
                            && $scope.files[0] !== undefined)
                        $scope.keys = Object.keys($scope.files[0]);
                }
            }).error(function (data, status) {
    });
  }
  $scope.get();
  $scope.format = function(key, value) {
        var formatedVal;
        if(key === 'procDate')
            formatedVal= $filter('date')(value[key], "yyyy-MM-dd HH:mm:ss");//(value[key] | date:'yyyy-MM-dd HH:mm:ss');
        else if(key === 'fileName'){
            formatedVal=('<a href="fileStatistic.jsp?id='+value['id']+'" target="_blank">'+value[key]+'</a>');
        }
          else if(key === 'id')
            formatedVal = value[key];
                return formatedVal;
   }
            

});