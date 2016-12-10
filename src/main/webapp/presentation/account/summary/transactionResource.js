angular.module('openkp').factory('TransactionResource', [ '$resource', function($resource) {
	var resource = $resource('resources/transaction', {
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		}
	});
	return resource;
} ]);