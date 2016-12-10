angular.module('openkp').factory('ExchangeResource', ['$resource', function($resource) {
	var resource = $resource('resources/exchange', {
		yearFrom : '@yearFrom',
		yearTo : '@yearTo'
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		},
		'query' : {
			method : 'GET',
			isArray : false
		},
		'update' : {
			method : 'PUT'
		}
	});
	return resource;
}]);