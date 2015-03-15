//Here we get the module we created in file one
angular.module('vcApp')

// We are adding a function called Ctrl1
// to the module we got in the line above
.controller('dsController', dsController);

// Inject my dependencies
dsController.$inject = ['$scope','$http','vcService'];

// Now create our controller function with all necessary logic
function dsController($scope,$http,vcService) {
	
	$scope.vcCount = null;

	vcService.count(function(vcService) {
		$scope.vcCount = vcService;
	});
	
	$scope.myData = [];
	
	$http.get('http://localhost:8080/service/rest/appData/ds')
	.success(function(data) {
	  $scope.myData = data;
	})
	.error(function(data, status) {
	  console.error('Repos error', status, data);
	})
	
	$scope.gridOptions = {
		data : 'myData',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'accessible', displayName:'Accessible'},
		{field:'ds_type', displayName:'Type'},
		{field:'capacity', displayName:'Capacity'},
		{field:'freeSpace', displayName:'Free Space'}]
	};
}

