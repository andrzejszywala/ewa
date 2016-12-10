angular.module('openkp.positions', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/positions', {
		templateUrl : 'presentation/positions/positions.html',
		controller : 'PositionsController'
	});
} ]).controller(
		'PositionsController',
		function($scope, $http, $compile, $log, uiGridConstants, PositionsResource) {
			$scope.position = {};
			
			$scope.gridOptions = {					
				showGridFooter: true,
				showColumnFooter: true,
				enableFiltering : true,
				enableRowSelection : true,
				enableRowHeaderSelection : false,
				multiSelect : false,
				noUnselect : true,
				modifierKeysToMultiSelect : false,
				onRegisterApi : function(gridApi) {
					$scope.gridApi = gridApi;	
					gridApi.selection.on.rowSelectionChanged($scope, function(row) {
						$scope.position = PositionsResource.query({id : row.entity.id});
					});
				},
				columnDefs : [ {
					field : 'closeDate',
					width : 120,
					type : 'date',
					cellFilter: 'date : \'short\'',
					cellClass : 'grid-column-date'
				}, {
					field : 'action',
					displayName : 'Action'
				}, {
					field : 'copyTraderName',
					displayName: 'Copied Name'
				}, {
					field : 'amount',
					width : 70,
					type : 'number',
					cellFilter: 'number:2',
					cellClass : 'grid-column-number',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:2 }}</div>'
				}, {
					field : 'units',
					cellClass : 'grid-column-number',
					type : 'number',
					cellFilter: 'number:2',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:2 }}</div>'
				}, {
					field : 'openRate',
					type : 'number',
					displayName : 'Open rate',
					cellFilter: 'number:4',
					cellClass : 'grid-column-number',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:4 }}</div>'
				}, {
					field : 'closeRate',
					type : 'number',
					displayName : 'Close rate',
					cellFilter: 'number:4',
					cellClass : 'grid-column-number',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:4 }}</div>'
				}, {
					field : 'spread',
					type : 'number',
					cellFilter: 'number:2',
					cellClass : 'grid-column-number',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:2 }}</div>'
				}, {
					field : 'profit',
					type : 'number',
					cellFilter: 'number:2',
					cellClass : 'grid-column-number',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:2 }}</div>'
				}, {
					field : 'rolloverFeesAndDividends',
					type : 'number',
					cellFilter: 'number:2',
					cellClass : 'grid-column-number',
					aggregationType: uiGridConstants.aggregationTypes.sum,
					footerCellTemplate: '<div class="ui-grid-cell-contents grid-column-number">{{col.getAggregationValue() | number:2 }}</div>'
				} ]
			};			
		
			PositionsResource.queryAll().$promise.then(function(data) {
				$scope.gridOptions.data = data;
			});

			
		});