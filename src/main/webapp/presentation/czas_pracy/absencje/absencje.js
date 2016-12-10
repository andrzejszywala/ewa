angular.module('openkp.absencje', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/absencje', {
		templateUrl : 'presentation/czas_pracy/absencje/absencje.html',
		controller : 'AbsencjeController'
	});
} ]).controller(
		'AbsencjeController',
		function($scope, $http, AbsencjaResource, PracownikResource, $compile, uiCalendarConfig, uiGridConstants, $interval, $log) {
			$scope.wybranaAbsencja = {};
			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();
			$scope.gridOptions = {
				enableRowSelection : true,
				enableRowHeaderSelection : false,
				multiSelect : false,
				noUnselect : true,
				modifierKeysToMultiSelect : false,
				plugins: [new ngGridFlexibleHeightPlugin()],
				onRegisterApi : function(gridApi) {
					$scope.gridApi = gridApi;

					gridApi.selection.on.rowSelectionChanged($scope, function(row) {
						$scope.wybranyPracownik = row.entity;
						while ($scope.events.length > 0) {
							$scope.events.pop();
						}
						AbsencjaResource.query({
							pracownikId : row.entity.id
						}).$promise.then(function(result) {
							result.forEach(function(row) {
								var kolor = 'blue';
								if (row.title == 'choroba') {
									kolor = 'orange';
								} else if (row.title == 'szpital') {
									kolor = 'red';
								} 
									
								$scope.events.push({
									id : row.id,
									start : row.start,
									end : row.end,
									dataOd : row.dataOd,
									dataDo : row.dataDo,
									title : row.title,
									dataOdOrg : row.dataOd,
									dataDoOrg : row.dataDo,
									titleOrg : row.title,
									version : row.version,
									allDay : row.allDay,
									color : kolor
								});
							});
						});

					});
				},
				columnDefs : [ {
					field : 'id',
				}, {
					field : 'action',
					displayName : 'Imię'
				}, {
					field : 'copyTraderName'
				}, {
					field : 'amount',
				}, {
					field : 'units',
				}, {
					field : 'openRate',
				}, {
					field : 'closeRate',
				}, {
					field : 'spread',
				}, {
					field : 'profit',
				}, {
					field : 'openDate',
				}, {
					field : 'takeProfitRate',
				}, {
					field : 'stopLossRate',
				}, {
					field : 'closeRate',
				}, {
					field : 'rolloverFeesAndDividends',
				} ]
			};

			$scope.typAbsencjiList = [ "bezpłatna", "urlop", "choroba", "szpital" ];

			$scope.dateOptions = {
				startingDay : 1
			};

			$scope.events = [];

		
			AbsencjaResource.queryAll().$promise.then(function(data) {
				$scope.gridOptions.data = data;
			});

			
		});