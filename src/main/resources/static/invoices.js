
        var myApp = angular.module('myApp', []);
		myApp.controller('InvoicesController', ['$scope', '$http', function($scope, $http) {
		$scope.enterprises = ['E0','E1','E2','E3'];
		$scope.e0Countries = ['PT','SK','PL','IT','TR','HR','CZ','BG','NL','BE'];	
		$scope.e1Countries = ['ES','RU','FR','HU','RO','DK','GR','RS','MD','US'];
		$scope.e2Countries = ['IN','PK','JP','VN','CN','KZ'];
		$scope.e3Countries = ['DE'];
		$scope.invoiceStatus=['SendingFailed','Submitted','In-Process','Cancelled','Credited','Rejected','Voided','Pending','Closed'];
		$scope.childScope={};
		$scope.enterprise="";
		$scope.childScope.Country="";
		$scope.no_of_days="3";
		$scope.invc_num="";
		$scope.store_num="";
		$scope.order_num="";
	 	$scope.invoiceList ="";
	 	$scope.status="";
	 	$scope.resMsg = null;
	 	/*var c;
	 	for( c of $scope.status){
	 		console.log(c);
	 	}*/
	 	console.log($scope.status);
	 	console.log($scope.enterprise);
    	var showInvoiceList =function(){$http({
            method: 'GET',
            url: 'http://localhost:8080/invoices/allInvoices?enterprise='+$scope.enterprise+'&country='
            +$scope.childScope.Country+'&noOfDays='+$scope.no_of_days+'&store_num='+$scope.store_num
            +'&invc_num='+$scope.invc_num+'&order_num='+$scope.order_num+'&status='+$scope.status
         }).then(function (response){
        	 var obj=JSON.parse(JSON.stringify(response));
        	 $scope.invoiceList =obj.data;
        	 $scope.getArray =obj.data;
        	 console.log($scope.status);
        	 $scope.count=$scope.invoiceList.length;
        	 $scope.loading = 0;
        	 $scope.showRecords =5;
         },function (error){
        	 $scope.resMsg = "No Data";
        	 $scope.loading = 0;
        	 $scope.showRecords =5;
         });
		}
    	$scope.clear=function(){
    		$scope.enterprise="";
    		$scope.childScope.Country="";
    		$scope.no_of_days="3";
    		$scope.invc_num="";
    		$scope.store_num="";
    		$scope.order_num="";
    		$scope.invoiceList ="";
    		$scope.status=[];
    		$scope.showRecords = 0;
    		};
    	$scope.loader=function(){
    		$scope.loading = 5;
    		console.log($scope.status)
    		showInvoiceList();
    		
    		};
    	
    }]);
		
	