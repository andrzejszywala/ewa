angular.module('openkp').factory('AbsencjaResource', [ '$resource', function($resource) {
	var resource = $resource('resources/position/:PracownikId', {
		PracownikId : '@id'
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
} ]);