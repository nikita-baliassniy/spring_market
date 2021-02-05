angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';
    const apiPath = "/api/v1";
    $scope.authorized = false;
    $scope.orderNumber = -1;

    $scope.fillTable = function (pageIndex = 1) {
        $http({
            url: contextPath + apiPath + '/products',
            method: 'GET',
            params: {
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null,
                title: $scope.filter ? $scope.filter.title : null,
                p: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }
            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductsPage.totalPages) {
                maxPageIndex = $scope.ProductsPage.totalPages;
            }
            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex)
        });
    };

    $scope.showCart = function () {
        $http({
            url: contextPath + apiPath + '/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.Cart = response.data;
            console.log($scope.Cart);
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + apiPath + '/cart/add/' + productId)
            .then(function (response) {
                $scope.showCart();
            });
    }

    $scope.removeFromCart = function (productId) {
        $http.get(contextPath + apiPath + '/cart/remove/' + productId)
            .then(function (response) {
                $scope.showCart();
            });
    }

    $scope.removeFromCartTotally = function (productId) {
        $http.get(contextPath + apiPath + '/cart/removeTotal/' + productId)
            .then(function (response) {
                $scope.showCart();
            });
    }

    $scope.clearCart = function () {
        $http.get(contextPath + apiPath + '/cart/clear')
            .then(function (response) {
                $scope.showCart();
            });
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + apiPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (id) {
        $http.delete(contextPath + apiPath + '/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            })
    }

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.authorized = true;
                    $scope.client = $scope.user.username;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.fillTable();
                }
            }, function errorCallback(response) {
                window.alert("Error");
            });
    };

    $scope.createOrder = function () {
        $http.post(contextPath + apiPath + '/cart/create_order')
            .then(function (response) {
                $scope.clearCart();
                $scope.orderNumber = response.data;
            });
    }
})
;