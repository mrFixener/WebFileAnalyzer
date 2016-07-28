window.App = angular.module('myModule', []);

App.controller('mainCtrl', ['$scope', "$window", "$http", function($scope, $window, $http) {
  $scope.fileName = "";
  $scope.lines    = "";
  $scope.serverPath = "/WebFileAnalyzer";
    $scope.send = function(){
      if($scope.fileName === undefined
            && $scope.fileName === ''){
            $scope.errFileName = "Поле имя файла не может быть пустое!"; 
            return;
        }
      if($scope.lines === undefined
            && $scope.lines === ''){
            $scope.errLines = "Поле имя файла не может быть пустое!"; 
            return;
    }
        $scope.post();
    }
    $scope.post=function(){
        $http({method: "POST", url: $scope.serverPath+"/procStat",
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}, data: 'fileName='+$scope.fileName+'&lines='+$scope.lines}).
                success(function(data, status) {
                    if(status === 200){
                        $window.location.href = $scope.serverPath+"/fileStatistic.jsp?fileId="+data;
                    }
        }).
                error(function(data, status) {
                console.log("error --> " + status);
        });
    }
}]);

