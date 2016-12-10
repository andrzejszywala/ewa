angular.module('openkp').factory('EquityResource', [ '$resource', function($resource) {
	var resource = $resource('resources/equity', {
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		}
	});
	return resource;
} ]);