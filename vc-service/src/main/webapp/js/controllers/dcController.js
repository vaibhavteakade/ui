// Here we get the module we created in file one
angular.module('vcApp')

// We are adding a function called Ctrl1
// to the module we got in the line above
.controller('dcController', dcController).directive('hboTabs', function() {
    return {
        restrict: 'A',
        link: function(scope, elm, attrs) {
            var jqueryElm = $(elm[0]);
            $(jqueryElm).tabs()
        }
    };
});

// Inject my dependencies
dcController.$inject = ['$scope','$location','$http','vcService'];

// Now create our controller function with all necessary logic
function dcController($scope,$location,$http,vcService) {
	
	
	$scope.totalServerItems = 0;
	
	$scope.vcCount = null;

	vcService.count(function(vcService) {
		$scope.vcCount = vcService;
	});
	
	$scope.myData = [];
	$scope.selectedI = [];
	
	$http.get('http://localhost:8080/service/rest/appData/dc')
	.success(function(data) {
		var dcData =  new Array();
		for (i = 0; i < data.length; i++) {
			var dc={};
		     dc.name = data[i].name;
		     dc.type = data[i].type;
		     dc.moid = data[i].moid;
		     dc.taskDeatils = data[i].taskDeatils;
		     dc.clusterList = data[i].clusterList;
		     dc.hostList = data[i].hostList;
		     dc.dsList = data[i].dsList;
		     dc.clusterSize = data[i].clusterList.length;
		     dc.hostSize = data[i].hostList.length;
		     dc.dsSize = data[i].dsList.length;
			 dcData.push(dc);
		     
		}
	  $scope.myData = dcData;
	  $scope.totalServerItems = data.length;
	})
	.error(function(data, status) {
	  console.error('Repos error', status, data);
	})
	
	$scope.gridOptions = {
		data : 'myData',
		showFooter: true,
        totalServerItems: 'totalServerItems',
		selectedItems: $scope.selectedI,
	     multiSelect: false,
		rowTemplate: '<div ng-dblclick="onDblClickRow(row)" ng-style="{ \’cursor\’: row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>',
		columnDefs: [{field:'name', displayName:'Name'},
		{field:'clusterSize', displayName:'Clusters'},
		{field:'hostSize', displayName:'Hosts'},
		{field:'dsSize', displayName:'Datastores'}]
	};
	
	 $scope.onDblClickRow = function(rowItem) {
		 //alert($scope.selectedI[0].moid);
		  $location.path( '/dc/:' + $scope.selectedI[0].moid );
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