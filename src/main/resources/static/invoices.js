
        var myApp = angular.module('myApp', []);
		myApp.controller('InvoicesController', ['$scope', '$http', function($scope, $http) {
		$scope.enterprises = ['E0','E1','E2','E3'];
		$scope.e0Countries = ['PT','SK','PL','IT','TR','HR','CZ','BG','NL','BE'];	
		$scope.e1Countries = ['ES','RU','FR','HU','RO','DK','GR','RS','MD','US'];
		$scope.e2Countries = ['IN','PK','JP','VN','CN','KZ'];
		$scope.e3Countries = ['DE'];
	 	$scope.invoiceList = null;
    	$scope.resMsg = null;
    	$scope.no_of_days="3";
    	$scope.childScope={};
    	$scope.countryReqFlag=false;
    	
    	$scope.showInvoiceList =function(){$http({
            method: 'GET',
            url: 'http://localhost:8080/invoices/allInvoices?enterprise='+$scope.enterprise+'&country='
            +$scope.childScope.Country+'&noOfDays='+$scope.no_of_days+'&store_num='+$scope.store_num+'&invc_num='+$scope.invc_num
         }).then(function (response){
        	 var obj=JSON.parse(JSON.stringify(response));
        	 $scope.invoiceList =obj.data;
         },function (error){
        	 $scope.resMsg = "No Data";
         });
 
		}
    	$scope.clear=function(){
    		$scope.enterprise=undefined;
    		$scope.childScope.Country=undefined;
    		$scope.no_of_days="3";
    		$scope.invc_num=undefined;
    		$scope.store_num=undefined;
    	};
	
    }]);
   