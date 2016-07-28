<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/css/style.css">
        <script src="resources/js/angular/angular.js"></script>
        <script src="resources/js/angular/sendingLines.js"></script>
        <title>Отправить строки на подсчет статистики</title>
    </head>
    <body ng-app="myModule">
        <div class="container" ng-controller="mainCtrl">
            <div class="alert alert-info container">
            </div>
            <div class="bd-example" data-example-id="">    
            <form name="form" ng-submit="send()">
              <div class="form-group" ng-class="{ 'has-error': form.fileName.$dirty && form.fileName.$error.required }">
                <label for="inputName1">Имя файла</label>
                <input type="text" ng-model="fileName" required class="form-control" id="inputName1" aria-describedby="emailHelp" placeholder="Введите имя файла">
                <small id="emailHelp" class="form-text text-muted">Придумайте название файла</small>
                <span ng-show="form.fileName.$dirty && form.fileName.$error.required" class="help-block">{{errFileName}}</span>
              </div>
              <div class="form-group" ng-class="{ 'has-error': form.lines.$dirty && form.lines.$error.required }">
                <label for="textarea">Строки для подсчета статистики</label>
                <textarea class="form-control" ng-model="lines" required id="textarea" rows="14"></textarea>
                <span ng-show="form.lines.$dirty && form.lines.$error.required" class="help-block">{{errLines}}</span>
              </div>
              <div class="form-actions">
                <button type="submit" ng-disabled="form.$invalid || user.dataLoading" class="btn btn-primary btn-block">Отправить</button>
              </div>  
             </form>
            </div>
        </div>
    </body>
</html>
