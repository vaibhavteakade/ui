var app = angular.module('vcApp', [ 'ngRoute', 'ngGrid','ngTable','ngCookies' ]);
app.config(function($routeProvider,$locationProvider) {
	$routeProvider.when("/dashboard", {
		templateUrl : "partials/dashboard.html",
		controller : "DashBoardController"
	}).when("/virtualmachine", {
		templateUrl : "partials/virtualmachines.html",
		controller : "vimController"
	}).when("/datacenters", {
		templateUrl : "partials/datacenters.html",
		controller : "dcController"
	}).when("/clusters", {
		templateUrl : "partials/clusters.html",
		controller : "clusterController"
	}).when("/hosts", {
		templateUrl : "partials/hosts.html",
		controller : "hostController"
	}).when("/datastores", {
		templateUrl : "partials/datastores.html",
		controller : "dsController"
	}).when("/dc/:id", {
		templateUrl : "partials/details/dcd.html",
		controller : "dcdController"
	})
	.when("/login", {
		templateUrl : "partials/login.html",
		controller : "loginController"
	})
	.otherwise({
		redirectTo : "/login"
	});
})

.run(function($rootScope, $location) {

    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
      if ($rootScope.loggedInUser == null) {
        // no logged user, redirect to /login
        if ( next.templateUrl === "partials/login.html") {
        } else {
          $location.path("/login");
        }
      }
    });
  });

app.factory('vcService', function($http) {
	return {
		count : function(callback) {
			$http.get('http://localhost:8080/service/rest/appData/count')
					.success(callback);
		}
	};
});

app.factory('userService', function($rootScope){
  var service = {};
  service.vcUrl = "";
  service.user = "";
  

  service.updatevcUrl = function(value){
    this.vcUrl = value;
    $rootScope.$broadcast("valuesUpdated");
  }

  service.updateuser = function(value){
    this.user = value;
    $rootScope.$broadcast("valuesUpdated");
  }

  return service;
});