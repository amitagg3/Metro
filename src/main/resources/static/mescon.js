 var myApp = angular.module('myApp', []);
		myApp.controller('MesconCustomerController', ['$scope', '$http', function($scope, $http) {
		$scope.allEntityType=['Mescon_Customer','Mescon_Article','Mescon_CI'];
		$scope.allCountries= ['PT','SK','PL','IT','TR','HR','CZ','BG','NL','BE','ES',
		'RU','FR','HU','RO','DK','GR','RS','MD','UA','IN','PK','JP','VN','CN','KZ','DE'];
		$scope.processingcd=['0 - Unprocessed','1 - Successful','2 - Unprocessed'];
		$scope.allCustomerOperations=['updatePromChannel','updatePaymentAllowance','updateObligeePayAllow','updateLicense','deleteObligeeAddress',
		'deleteObligee','updateCustConsultant','deleteCardVersion','updateAddress','updateObligee','updateObligeeAddress','deleteCustomer',
		'deleteCustConsultant','updateCustomer','updateCardVersion','deleteLicense'];
		$scope.allArticleOperations=['InsertArticle','InsertArticlePrice','InsertArticleStore','insertArticleStore','InsertArticleMaxPrice','insertArticlePrice','insertArticleTraceability'];
		$scope.allCiOperations=['ImportBettyOrder','ImportEntitlement'];
		
	 	$scope.mesconList = null;
	 	$scope.childScope={};
	 	$scope.EntityType="";
    	$scope.resMsg = null;
    	$scope.country="";
    	$scope.ProcessingCd="";
    	$scope.store_num="";
		$scope.payload="";
		$scope.key1="";
		$scope.key4="";    
		$scope.no_of_days="1";
    	$scope.childScope.operation="";
    	
    	var showMesconCustList =function(){$http({
            method: 'GET',       
            url: 'http://localhost:8080/mescon/mesconCustomer?entityType='+$scope.EntityType+'&country='+$scope.country
            +'&ProcessingCd='+$scope.ProcessingCd+'&store='+$scope.store_num+'&payload='+$scope.payload
            +'&key1='+$scope.key1+'&key4='+$scope.key4
            +'&operation='+$scope.childScope.operation+'&noOfDays='+$scope.no_of_days
         }).then(function (response){
        	 var obj=JSON.parse(JSON.stringify(response));
        	 $scope.mesconList =obj.data;
        	 $scope.count=$scope.mesconList.length;
        	 $scope.showRecords =5;
        	 $scope.loading = 0;
         	

        	 
         },function (error){
        	 $scope.resMsg = "No Data";
        	 $scope.loading = 0;
        	 $scope.showRecords =5;
         });
		}
		 $scope.orderByMe = function(x) {
		 	console.log("Orderbyfunction");
    	        $scope.myOrderBy = x;
    	    }
		$scope.clear=function(){
    		$scope.EntityType="";
    		$scope.resMsg = null;
    		$scope.country="";
    		$scope.ProcessingCd="";
    		$scope.store_num="";
			$scope.payload="";
			$scope.key1="";
			$scope.key4="";    	
    		$scope.childScope.operation="";
    		$scope.no_of_days=1;
    		$scope.showRecords = 0;
    		}
		$scope.clear_operation=function(){
			$scope.childScope.operation="";
		}
    		
    	$scope.loader=function(){
        		$scope.loading = 5;
        		showMesconCustList();
        		};
		
	
    }]);
   