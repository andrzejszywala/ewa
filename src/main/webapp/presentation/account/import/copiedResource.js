angular.module('openkp').factory('CopiedResource', ['$resource', function($resource) {
	var resource = $resource('resources/copied', {
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		}
	});
	return resource;
}]);