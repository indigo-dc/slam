var app = angular.module('configurationManager');

app.controller('ApplicationController', ['$scope', '$routeParams','$route', 'applicationService',
function ($scope, $routeParams, $route, applicationService) {
    $scope.isLoading = true;
    $scope.title = "tytul";

    $scope.init = function () {


        loadApplications();
    }


    function loadApplications() {

        applicationService.getApplications().then(
            function (result) {
                alert('zaladowano');

                if (result['status'] == 'SUCCESS') {
                    _applyRemoteData(result['type']);

                } else {
                    var message = "Przepraszamy, wystąpił błąd";
                    if (result['message'] != null) {
                        message = result['message'];
                    }
                    window.alert(message);
                }
            }
        );

    }


    $scope.newApplicationName = "";
    $scope.saveButtonDisabled = true;

    $scope.createApplication = function () {

        var postData = {
            name: $scope.newApplicationName,
        };

        applicationService.createApplication(postData).then(
            function (result) {
                alert('zapisanoe');


                loadApplications();
                resetForm();
            }
        );


    }

    function resetForm() {
        $scope.newApplicationName = "";
        $scope.saveButtonDisabled = true;
    }



    $scope.changeInput = function() {
        if ($scope.newApplicationName.trim() == '') {
            $scope.saveButtonDisabled = true;
        }
        else {
            $scope.saveButtonDisabled = false;
        }
    }


  }]);