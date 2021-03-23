angular.module('app').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market';

    $scope.clearCart = function () {
        $localStorage.marketCart.clear();
    }

    $scope.createOrder = function () {
        $http.get(contextPath + '/api/v1/orders/create')
            .then(function (response) {
                $scope.showMyOrders();
                $scope.showCart();
            });
    }

    $scope.goToOrderSubmit = function () {
        $location.path('/order_confirmation');
    }

    $scope.checkCart = function () {
        $http.post(contextPath + '/api/v1/orders/js', $localStorage.marketCart)
            .then(function (response) {
                console.log('good');
            });
    }

    $scope.cartView = $localStorage.marketCart;
});