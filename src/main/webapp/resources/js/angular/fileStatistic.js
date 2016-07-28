window.App = angular.module('myModule', ['ui.bootstrap',  'ngResource']);

App.controller('mainCtrl', ['$scope', '$http', function($scope, $http) {
  $scope.objects = [];

  $scope.totalItems = $scope.objects.length;
  $scope.currentPage = 1;
  $scope.numPerPage = 10;
  
  $scope.fileId = "";
 
  function getParameterByName(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        $scope.fileId = decodeURIComponent(results[2].replace(/\+/g, " "));
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }
    $scope.fileId = getParameterByName('fileId');

    $scope.get = function(){
    $http({method: "GET",
        url: 'getFileStat/'+$scope.fileId}).
            success(function (data, status) {
                if (status === 200) {
                    $scope.objects = data.fileStat;
                }
            }).error(function (data, status) {
    });
  }
    if ($scope.fileId !== '')
        $scope.get();
  $scope.paginate = function(value) {
    var begin, end, index;
    begin = ($scope.currentPage - 1) * $scope.numPerPage;
    end = begin + $scope.numPerPage;
    index = $scope.objects.indexOf(value);
    return (begin <= index && index < end);
  };
}]);