var TUR = {
    USD: [
        {date: "12.03.2013", rate:	30.7576},
        {date: "13.03.2013", rate:	30.7499},
        {date: "14.03.2013", rate:	30.7209},
        {date: "15.03.2013", rate:	30.7769},
        {date: "16.03.2013", rate:	30.7196},
        {date: "19.03.2013", rate:	30.8908},
        {date: "20.03.2013", rate:	30.8285},
        {date: "21.03.2013", rate:	30.9446},
        {date: "22.03.2013", rate:	30.8923},
        {date: "23.03.2013", rate:	30.9325},
        {date: "26.03.2013", rate:	30.7585},
        {date: "27.03.2013", rate:	30.8734},
        {date: "28.03.2013", rate:	30.863},
        {date: "29.03.2013", rate:	30.9962},
        {date: "30.03.2013", rate:	31.0834},
        {date: "02.04.2013", rate:	31.1093},
        {date: "03.04.2013", rate:	31.1178},
        {date: "04.04.2013", rate:	31.3918},
        {date: "05.04.2013", rate:	31.7203},
        {date: "06.04.2013", rate:	31.6207},
        {date: "09.04.2013", rate:	31.6144},
        {date: "10.04.2013", rate: 31.2086}
    ]
}
function CurrencyListCtrl($scope) {

    $scope.current = {
        currency: "RUB",
        amount: 37000};

    $scope.currencies = [{
        "name": "USD",
        "description": "United States Dollars",
        "code": 840,
        "rate": 31.10
      },{
        "name": "EUR",
        "description": "Euro",
        "code": 978,
        "rate": 40.6523
      },{
        "name": "RUB",
        "description": "Russia Rubles",
        "code": 840,
        "rate": 1
    }];

    var getRate = function(currency){
        for (var i in $scope.currencies){
            if (currency == $scope.currencies[i].name){
                return $scope.currencies[i].rate
            }
        }
    }

    $scope.convert = function(amount, from, to){
        var sourceRate = getRate(from);
        var targetRate = getRate(to);
        return Math.round( 100 * (amount * sourceRate) / targetRate ) / 100
    }

    $scope.same = function(n1,n2){
        return Math.abs(n1-n2) <0.01
    }

    $scope.$on('AmountChanged', function(ev, args) {
        var targetAmount = $scope.convert($scope.current.amount, $scope.current.currency, args.currency);
        if (!$scope.same(targetAmount,args.amount)){
            $scope.current = args;
            $scope.$broadcast("AmountChanged", args)
        }
    });

}

function CurrencyCtrl($scope) {
    var refresh = function(){
        $scope.rate = $scope.convert( 1, $scope.currency.name, $scope.current.currency)
        $scope.amount = $scope.convert( $scope.current.amount, $scope.current.currency, $scope.currency.name)
    }

    $scope.$watch('amount', function() {
        var expected = $scope.convert( $scope.current.amount, $scope.current.currency, $scope.currency.name)
        if (!$scope.same(expected, $scope.amount)){
           $scope.$emit('AmountChanged', {currency: $scope.currency.name, amount: $scope.amount })
        }
    })

    $scope.$on("AmountChanged", refresh)

    refresh()
}