//Here we get the module we created in file one
angular.module('vcApp')

// We are adding a function called Ctrl1
// to the module we got in the line above
.controller('hostController', hostController);

// Inject my dependencies
hostController.$inject = ['$scope','$http','vcService'];

// Now create our controller function with all necessary logic
function hostController($scope,$http,vcService) {
	
	$scope.vcCount = null;
	 $scope.totalServerItems = 0;
	vcService.count(function(vcService) {
		$scope.vcCount = vcService;
	});
	
	$scope.myData = [];
	
	$http.get('http://localhost:8080/service/rest/appData/host')
	.success(function(data) {
	  $scope.myData = data;
	  $scope.totalServerItems = data.length;
	})
	.error(function(data, status) {
	  console.error('Repos error', status, data);
	})
	
	$scope.gridOptions = {
		data : 'myData',
		showFooter: true,
        totalServerItems: 'totalServerItems',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'iscsiSupported', displayName:'Iscsi Supported'},
		{field:'bootTime', displayName:'Boot Time'},
		{field:'powerState', displayName:'Power State'},
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

