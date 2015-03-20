//Here we get the module we created in file one
angular.module('vcApp')

// We are adding a function called Ctrl1
// to the module we got in the line above
.controller('clusterController', clusterController);

// Inject my dependencies
clusterController.$inject = ['$scope','$http','vcService'];

// Now create our controller function with all necessary logic
function clusterController($scope,$http,vcService) {
	
	$scope.vcCount = null;
	$scope.totalServerItems = 0;
	vcService.count(function(vcService) {
		$scope.vcCount = vcService;
	});
	
	$scope.myData = [];
	
	$http.get('http://localhost:8080/service/rest/appData/cluster')
	.success(function(data) {
	  $scope.myData = data;
	  $scope.totalServerItems = $scope.myData.length;
	})
	.error(function(data, status) {
	  console.error('Repos error', status, data);
	})
	
	$scope.gridOptions = {
		data : 'myData',
		showFooter: true,
        totalServerItems: 'totalServerItems',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'totalCpu', displayName:'Cpu'},
		{field:'totalMemory', displayName:'Memory'},
		{field:'numHosts', displayName:'Hosts'},
		{field:'connectionState', displayName:'Connection State'}]
	};
	
	$scope.getTableStyle= function() {
            var marginHeight = 20; // optional
			var maxHeight = screen.height - 250;
           return {
                /* height: (10 * $scope.gridOptions.rowHeight + $scope.gridOptions.headerRowHeight + marginHeight ) + "px" */
				height : maxHeight
			};
        };
}

