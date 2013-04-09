
function CurrencyListCtrl($scope) {

    $scope.current = {
        currency: "RUB",
        amount: 31.10}

    $scope.currencies = [{
        "img" : "img/usd.jpg",
        "name": "USD",
        "description": "United States Dollars",
        "code": 840,
        "rate": 31.10
      },{
        "img" : "img/eur.jpg",
        "name": "EUR",
        "description": "Euro",
        "code": 978,
        "rate": 40.6523
      },{
        "img" : "img/rub.jpg",
        "name": "RUB",
        "description": "Russia Rubles",
        "code": 840,
        "rate": 1
    }];

    $scope.$on('AmountChanged', function(ev, args) {
        $scope.current.amount = args.amount
    });
}


function CurrencyCtrl($scope) {
    $scope.zho = $scope.currency.name
    $scope.yo = $scope.current.amount / $scope.currency.rate

    $scope.$watch('yo', function() {
        // $scope.$emit('AmountChanged', {amount:  $scope.yo * $scope.currency.rate})
    });
}