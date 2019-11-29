/* var myApp = angular.module('myApp', []);
		myApp.controller('UnprocessedInvoiceController', ['$scope', '$http', function($scope, $http) {
	 	$scope.invoiceList = null;
    	$scope.resMsg = null;
		$scope.showInvoiceList =function(){$http({
            method: 'GET',
            url: 'http://localhost:8080/invoices/allInvoices'
         }).then(function (response){
        	 var obj=JSON.parse(JSON.stringify(response));
        	 $scope.invoiceList =obj.data;
         },function (error){
        	 $scope.resMsg = "No Data";
         });
		}
		   $scope.showInvoiceList();
    }]);
   	 


*/