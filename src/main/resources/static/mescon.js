 var myApp = angular.module('myApp', []);
		myApp.controller('MesconCustomerController', ['$scope', '$http', function($scope, $http) {
		//$scope.enterprises = ['E0','E1','E2','E3'];
		$scope.e0Countries = ['PT','SK','PL','IT','TR','HR','CZ','BG','NL','BE'];	
		$scope.e1Countries = ['ES','RU','FR','HU','RO','DK','GR','RS','MD','US'];
		$scope.e2Countries = ['IN','PK','JP','VN','CN','KZ'];
		$scope.e3Countries = ['DE'];
		$scope.allCountries= ['PT','SK','PL','IT','TR','HR','CZ','BG','NL','BE','ES',
		'RU','FR','HU','RO','DK','GR','RS','MD','US','IN','PK','JP','VN','CN','KZ','DE']
	 	$scope.mesconList = null;
    	$scope.resMsg = null;
    	$scope.showMesconCustList =function(){$http({
            method: 'GET',
            url: 'http://localhost:8080/mescon/allMesconCust'
         }).then(function (response){
        	 var obj=JSON.parse(JSON.stringify(response));
        	 $scope.mesconList =obj.data;
         },function (error){
        	 $scope.resMsg = "No Data";
         });
		}
	
    }]);
   