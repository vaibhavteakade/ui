// Here we get the module we created in file one
angular.module('vcApp')

// We are adding a function called Ctrl1
// to the module we got in the line above
.controller('DashBoardController', DashBoardController);

// Inject my dependencies
DashBoardController.$inject = [ '$scope', '$http', 'vcService' ];

// Now create our controller function with all necessary logic
function DashBoardController($scope, $http, vcService) {
	$scope.vcCount = null;

	vcService.count(function(vcService) {
		$scope.vcCount = vcService;
	});

	// $http.get('http://localhost:8080/service/rest/appData/count')
	// .success(function(data) {
	//	 
	// })
	// .error(function(data, status) {
	// console.error('Repos error', status, data);
	// })
}