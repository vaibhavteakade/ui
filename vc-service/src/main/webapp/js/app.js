var app = angular.module('vcApp', [ 'ngRoute', 'ngGrid' ]);
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
	.otherwise({
		redirectTo : "/dashboard"
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