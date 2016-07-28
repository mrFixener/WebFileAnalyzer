
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/css/style.css">
        <script src="resources/js/angular/angular.js"></script>
        <script src="resources/js/angular/angular-resource.js"></script>
        <script src="resources/js/bootstrap/ui-bootstrap-tpls-0.11.0.js"></script>
        <script src="resources/js/angular/fileStatistic.js"></script>
        
        <title>Статистика по файлу</title>
    </head>
    <body ng-app="myModule">
        <div class="container" ng-controller="mainCtrl">
            <div class="alert alert-info container">
                <div class="pull-right">
                    <a class="btn btn-secondary" href="sendingLines.jsp" target="_blank" role="button">Отправить на статистику</a>
                </div>

            </div>
            <div ng-controller="mainCtrl">

                <table class="table table-striped table-hover table-bordered">
                    <thead>
                        <tr>
                            <td>Ид записи</td>
                            <td>Ид файла в базе</td>
                            <td>Строка</td>
                            <td>Самое длинное слово<br>(символы между 2 пробелами)</td>
                            <td>Кратчайшее слово</td>
                            <td>Длина строки</td>
                            <td>Средняя длина слова</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="obj in objects | filter : paginate">
                            <td>{{obj.internalId}}</td>
                            <td>{{obj.externalId}}</td>
                            <td>{{obj.line}}</td>
                            <td>{{obj.maxWord}}</td>
                            <td>{{obj.minWord}}</td>
                            <td>{{obj.lengthLine}}</td>
                            <td>{{obj.avgWord}}</td>
                        </tr>
                    </tbody>
                </table>
                <pagination total-items="totalItems" ng-model="currentPage"
                            max-size="100" boundary-links="true"
                            items-per-page="numPerPage" class="pagination-sm">
                </pagination>
            </div> 
        </div>
    </body>
</html>
