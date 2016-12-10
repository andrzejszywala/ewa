angular.module('openkp').factory('PositionsResource', [ '$resource', function($resource) {
	var resource = $resource('resources/position/:id', {
		id : '@id'
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		},
		'query' : {
			method : 'GET',
			isArray : false
		}
	});
	return resource;
} ]);