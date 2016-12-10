angular.module('openkp.summary', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/summary', {
		templateUrl : 'presentation/account/summary/summary.html',
		controller : 'SummaryController'
	});
} ]).controller('SummaryController', function($scope, $http, TransactionResource, AccountResource) {
	
	$scope.transactions = TransactionResource.queryAll();
	$scope.statement = AccountResource.query();
	
	
	$scope.sumUsd = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	        total += $scope.transactions[i].usd;
	    }
	    return total;
	};
		
	$scope.sumPln = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	        total += $scope.transactions[i].pln;
	    }
	    return total;
	};
	
	$scope.totalNumberOfTrades = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	        total++;
	    }
	    return total;
	};
	
	$scope.numberOfProfitableTrades = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].usd >= 0) {
	    		total++;
	    	}
	    }
	    return total;
	};
	
	$scope.numberOfLossTrades = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].usd < 0) {
	    		total++;
	    	}
	    }
	    return total;
	};
	
	$scope.profitableTradesUSD = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].usd > 0 && $scope.transactions[i].type == 'Profit/Loss of Trade') {
	    		total += $scope.transactions[i].usd;
	    	}
	    }
	    return total;
	};
	
	$scope.lossTradesUSD = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].usd < 0 && $scope.transactions[i].type == 'Profit/Loss of Trade') {
	    		total += $scope.transactions[i].usd;
	    	}
	    }
	    return total;
	};
	
	$scope.incomeUSD = function () {
		return $scope.profitableTradesUSD();
	};
	
	$scope.costsUSD = function () {
		return $scope.lossTradesUSD() + $scope.rolloverFeesUSD();
	};
	
	$scope.rolloverFeesUSD = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].type == 'Rollover Fee') {
	    		total += $scope.transactions[i].usd;
	    	}
	    }
	    return total;
	};
	
	$scope.profitOrLossUSD = function () {
		return $scope.incomeUSD() + $scope.costsUSD();
	};
	
	
	$scope.profitableTradesPLN = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].pln > 0 && $scope.transactions[i].type == 'Profit/Loss of Trade') {
	    		total += $scope.transactions[i].pln;
	    	}
	    }
	    return total;
	};
	
	$scope.lossTradesPLN = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].pln < 0 && $scope.transactions[i].type == 'Profit/Loss of Trade') {
	    		total += $scope.transactions[i].pln;
	    	}
	    }
	    return total;
	};
	
	$scope.incomePLN = function () {
		return $scope.profitableTradesPLN();
	};
	
	$scope.costsPLN = function () {
		return $scope.lossTradesPLN() + $scope.rolloverFeesPLN();
	};
	
	$scope.rolloverFeesPLN = function () {
		var total = 0;
	    for(var i = 0; i < $scope.transactions.length; i++){
	    	if ($scope.transactions[i].type == 'Rollover Fee') {
	    		total += $scope.transactions[i].pln;
	    	}
	    }
	    return total;
	};
	
	$scope.profitOrLossPLN = function () {
		return $scope.incomePLN() + $scope.costsPLN();
	};
	

});