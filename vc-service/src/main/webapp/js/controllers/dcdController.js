//Here we get the module we created in file one
angular.module('vcApp')

// We are adding a function called Ctrl1
// to the module we got in the line above
.controller('dcdController', dcdController);

// Inject my dependencies
dcdController.$inject = ['$scope','$http','$routeParams','vcService'];

// Now create our controller function with all necessary logic
function dcdController($scope,$http,$routeParams,vcService) {
	
	$scope.clusters = [];
	$scope.hosts = [];
	$scope.ds = [];
	$scope.vim = [];
	
	$scope.bytesToSize = function (bytes) {
   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
   if (bytes == 0) return '0 Byte';
   var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
   return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
};
	
	$http.get('http://localhost:8080/service/rest/appData/dc/' + $routeParams.id)
	.success(function(data) {
	  $scope.clusters = data.clusterList;
	  $scope.hosts = data.hostList;
	  $scope.ds = data.dsList;
	  $scope.vim = data.vmList;
	})
	.error(function(data, status) {
	  console.error('Repos error', status, data);
	})
	
	//cluster grid
	$scope.clusterOptions = {
		data : 'clusters',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'totalCpu', displayName:'Cpu'},
	{field:'totalMemory', displayName:'Memory',cellTemplate: '<div class="ngCellText">{{bytesToSize(row.entity.totalMemory)}}</div>'},
		{field:'numHosts', displayName:'Hosts'},
		{field:'connectionState', displayName:'Connection State'}]
	};
	
	//host
	$scope.hostOptions = {
		data : 'hosts',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'iscsiSupported', displayName:'Iscsi Supported'},
		{field:'bootTime', displayName:'Boot Time'},
		{field:'powerState', displayName:'Power State'},
		{field:'connectionState', displayName:'Connection State'}]
	};
	
	//ds
	$scope.dsOptions = {
		data : 'ds',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'accessible', displayName:'Accessible'},
		{field:'ds_type', displayName:'Type'},
		{field:'capacity', displayName:'Capacity'},
		{field:'freeSpace', displayName:'Free Space'}]
	};
	
	//vim
		$scope.vimOptions = {
		data : 'vim',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'appHeartbeatStatus', displayName:'HeartbeatStatus'},
		{field:'memoryMB', displayName:'Memory MB'},
		{field:'numCPU', displayName:'CPU'},
		{field:'connectionState', displayName:'Connection State'}]
	};
	

	
}