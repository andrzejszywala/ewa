angular.module('openkp').factory('AccountResource', [ '$resource', function($resource) {
	var resource = $resource('resources/account', {
	}, {
		'query' : {
			method : 'GET',
			isArray : false
		}
	});
	return resource;
} ]);