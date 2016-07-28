<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список файлов</title>
        <link rel="stylesheet" href="resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/css/style.css">
        
        <script src="resources/js/angular/angular.js"></script>
        <script src="resources/js/angular/simpleTable.js"></script>
        <script src="resources/js/angular/angular-sanitize.min.js"></script>
        
    </head>
    <body>
        <div class="container" ng-app="simpleTable" ng-controller="controller">

            <div class="alert alert-info container">
                <p>Тип сортировки: {{ sortType }}</p>
                <p>От большего к меньшему: {{ sortReverse === true? 'вкл.':'выкл' }}</p>
                <p>Поисковой запрос: {{ search }}</p>
                <div class="pull-right">
                    <a class="btn btn-secondary" href="sendingLines.jsp" target="_blank" role="button">Отправить на статистику</a>
                </div>
                
            </div>
            
            <div class="form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="checkbox" value=""  ng-change="changeCheck()" ng-model="filterCheck">
                    файлы более чем 10 строк
                </label>
            </div>
            
            <form>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon"><i class="fa fa-search"></i></div>
                        <input type="text" class="form-control" placeholder="Искать файл" ng-model="search">
                    </div>      
                </div>
            </form>

            <table class="table table-bordered table-striped">

                <thead>
                    <tr>
                        <td>
                            <a href="#" ng-click="sortType = 'id'; sortReverse = !sortReverse">
                                Id
                                <span ng-show="sortType == 'id' && !sortReverse" class="fa fa-caret-down"></span>
                                <span ng-show="sortType == 'id' && sortReverse" class="fa fa-caret-up"></span>
                            </a>
                        </td>
                        <td>
                            <a href="#" ng-click="sortType = 'fileName'; sortReverse = !sortReverse">
                                Имя файла
                                <span ng-show="sortType == 'fileName' && !sortReverse" class="fa fa-caret-down"></span>
                                <span ng-show="sortType == 'fileName' && sortReverse" class="fa fa-caret-up"></span>
                            </a>
                        </td>
                        <td>
                            <a href="#" ng-click="sortType = 'procDate'; sortReverse = !sortReverse">
                                Дата обработки
                                <span ng-show="sortType == 'procDate' && !sortReverse" class="fa fa-caret-down"></span>
                                <span ng-show="sortType == 'procDate' && sortReverse" class="fa fa-caret-up"></span>
                            </a>
                        </td>
                </thead>

                <tbody>
                    <tr ng-repeat="file in files| orderBy:sortType:sortReverse | filter:search">
                        <td>{{ file.id }}</td>
                        <td><a href="fileStatistic.jsp?fileId={{file.id}}" target="_blank">{{ file.fileName }}</a></td>
                        <td>{{ file.procDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                    </tr>
                </tbody>

            </table>
        </div>
    </body>
</html>
