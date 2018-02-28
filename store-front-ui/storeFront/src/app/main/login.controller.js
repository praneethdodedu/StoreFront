(function() {
  'use strict';

  var app = angular
    .module('storeFront')
    .controller('LoginController', LoginController);

  /** @ngInject */
  function LoginController($scope, $rootScope, $stateParams, $state, $http, LoginService, MainService, $uibModal, storeFront_Constants) {
    $rootScope.title = "StoreFront Login";

    $scope.loginUrl = function () {
      window.location.href = storeFront_Constants.REST_API_BASE+'/login';
    }

    $scope.organizationList = function() {
        $scope.promise = LoginService.getAccountLists()
              .then(function (response){
                $scope.getAllAccounts = response.data;
                console.log("account list", response.data);
              }, function (error) {
                  console.log("error in getting accounts lists..");
              });
    }
    $scope.organizationList();

    $scope.user = {};
    $scope.registerLogUp = function (mode) {
    //  if (mode==2) {
        //console.log("inside mode 2");
        console.log("organization name selected", $scope.user);
        $scope.payload =
        {
          "userName": $scope.user.username,
          "emailId": $scope.user.email,
          "accountId": $scope.user.accountId
        }
        console.log("payload", $scope.payload);
        $scope.promise=LoginService.signUpUser($scope.payload)
              .then(function (response) {
                  console.log("user registered", response.data);
                  $scope.registerUser = response.data;
                  var uibInstance = $uibModal.open({
                     animation: 'true',
                     templateUrl: 'app/main/signupEmailMsg.html',
                     scope: $scope,
                     size: 'sm'
                   })
             }, function (error) {
                 console.log("error in registering user..");
                 $scope.errorRegisterUser = error.data;
                 console.log("errorRegisterUser", $scope.errorRegisterUser);
                 var uibInstance = $uibModal.open({
                    animation: 'true',
                    templateUrl: 'app/main/signupEmailMsg.html',
                    scope: $scope,
                    size: 'sm'
                  })
             });

      //}
    }
    console.log("$rootScope.userId", $rootScope.userId);

  }


  // app.factory('LoginService', function() {
  //    var admin = 'chandrasekhar003@gmail.com';
  //    var pass = 'Test@2017';
  //    var isAuthenticated = false;
  //
  //    return {
  //      login : function(username, password) {
  //        isAuthenticated = username === admin && password === pass;
  //        return isAuthenticated;
  //      },
  //      isAuthenticated : function() {
  //        return isAuthenticated;
  //      }
  //    };
  //
  //  });
})();
