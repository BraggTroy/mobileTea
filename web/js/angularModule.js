/**
 * Created by Administrator on 2016/11/9.
 */

/********************** 全部商品栏目 *************************/
/**
 * cbj-tea-GoodsList 全部商品
 * cbj-tea-KindsList 全部种类
 *
 */

var imageURL = "";
(function(){
    $.ajax({
        url:'getImageUrl.do',
        type:'get',
        async:false,
        success:function(result){
            imageURL = result;
        }
    });
})();
console.log(imageURL);

/**
 * angularJS 获取商品
 */
(function(){
    var app = angular.module('app',['ngRoute']);

        /******* 配置 *******/
        app.config(['$routeProvider',function($routeProvider){
            $routeProvider
                .when('/',{templateUrl:'view/home.html' , controller:'homeController'})
                .when('/goodsPage',{templateUrl:'view/goods.html',controller:'goodsController'})
                .when('/cartPage',{templateUrl:'view/cart.html',controller:'cartController'})
                .when('/minePage',{templateUrl:'view/mine.html',controller:'mineController'})
                .when('/detail/:goodsId',{templateUrl:'view/detail.html',controller:'detailController'})
                .when('/login',{templateUrl:'view/login.html',controller:'loginController'})
                .when('/register',{templateUrl:'view/register.html',controller:'registerController'})
                .when('/kinds',{templateUrl:'view/kinds.html',controller:'kindsController'})/*种类*/
                .when('/kindsGoods/:kindId',{templateUrl:'view/kindsGoods.html',controller:'kindsGoodsController'})/*各种类商品*/
                .when('/culture',{templateUrl:'view/culture.html',controller:'cultureController'})
                .when('/article/:cultureId',{templateUrl:'view/article.html',controller:'articleController'})


                .otherwise({redirectTo:'/'})
        }]);




        /********************** 控制器 **********************/
        /**
         * 登录
         */
        app.controller('loginController',['$scope','$http',function($scope,$http){
            //$scope.userName="" ;
            //$scope.password="" ;
            var $w_login = $("#w_login");

            $scope.user = {name:"" , password:""};

                $scope.processForm = function () {
                    //console.log($scope.user)
                    if($scope.user.name.length ==0 || $scope.user.password.length ==0){
                        $w_login.text("用户名或密码不能为空");
                        $w_login.css('visibility','visible');
                        return;
                    }else {
                        $http({
                            method: 'POST',
                            url: 'mLogin.do',
                            data: $.param($scope.user),
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                        })
                            .success(function (result) {
                                //console.log(result)
                                if (result.name == null) {
                                    //alert("用户名或密码不正确")
                                    $w_login.text("用户名或密码不正确");
                                    $w_login.css('visibility', 'visible');
                                } else {
                                    sessionStorage.setItem('cbj-tea-user', JSON.stringify(result));
                                    window.location.href = '#/minePage';
                                }
                            })
                    }
                };

        }]);
        /**
         * 注册
         */
        app.controller('registerController',['$scope','$http',function($scope,$http){

        }]);
    /**
     * index
     */
    app.controller('indexController' , ['$scope',function($scope){
        $scope.imageURL = imageURL;

    }]);

        /**
         * 首页
         */
        app.controller('homeController',['$scope' , '$http' , function($scope,$http){


            $scope.allGoods = []; /*首页预加载全部商品*/
            $http
                .get('mGetGoodsList.do')
                .success(function(result){
                    $scope.allGoods = result;
                    localStorage.setItem('cbj-tea-GoodsList',JSON.stringify(result));
                });

        }]);
        /**
         * 全部商品
         */
        app.controller('goodsController' , ['$scope' , '$http' , function($scope , $http){

            $scope.imageURL = imageURL;
            $scope.data = [];
            var list = localStorage.getItem('cbj-tea-GoodsList');
            if(null != list){
                $scope.data = JSON.parse(list);
            }else {
                $http
                    .get('mGetGoodsList.do')
                    .success(function (result) {
                        $scope.data = result;
                        localStorage.setItem('cbj-tea-GoodsList', JSON.stringify(result));
                    });
            }
        }]);
        /**
         * 购物车
         */
        app.controller('cartController',['$scope' , '$http' , function($scope,$http){
            $scope.user = JSON.parse(sessionStorage.getItem('cbj-tea-user'));
            $scope.imageURL = imageURL;
            $scope.data = "";

            var cart = JSON.parse(localStorage.getItem('cbj-tea-Cart'));

                if (null != cart) {
                    $scope.data = cart;
                } else {
                    if($scope.user != null || $scope.user != undefined) {
                        $http
                            .get('mGetMyCart.do', {params: {'id': $scope.user.id}})
                            .success(function (result) {
                                $scope.data = result;
                                //console.log(result)
                                localStorage.setItem('cbj-tea-Cart', JSON.stringify(result));
                            });
                    }
                }

        }]);
        /**个人中心**/
        app.controller('mineController',['$scope' , '$http' , function($scope,$http){
            $scope.user = JSON.parse(sessionStorage.getItem('cbj-tea-user'));


        }]);
        /**商品详情**/
        app.controller('detailController',['$scope' , '$routeParams' , function($scope,$routeParams){
            $scope.user = JSON.parse(sessionStorage.getItem('cbj-tea-user'));
            $scope.imageURL = imageURL;
            $scope.count = 1;

            /* 商品数量 */
            $scope.countdelete = function () {
                $scope.count -= 1;
                if($scope.count <= 0){
                    console.log($scope.count);
                    $scope.count = 1;
                }
            };


            var id = $routeParams.goodsId;
            var list = JSON.parse(localStorage.getItem('cbj-tea-GoodsList'));
            for(var i=0;i<list.length;i++){
                if(id == list[i].id){
                    $scope.data = list[i];
                    $scope.images = $scope.data.image.split(",");
                    //console.log(list[i]);
                }
            }

            /** 加入购物车 **/



        }]);

    /**商品种类**/
    app.controller('kindsController',['$scope','$http',function($scope,$http){
        $scope.data = [];
        $scope.flower = []; /*花茶*/
        $scope.main = []; /*经典茶系*/

        var kindslist = localStorage.getItem('cbj-tea-KindsList');
        if(null != kindslist){
            $scope.data = JSON.parse(kindslist);
            for (var i = 0; i < $scope.data.length; i++) {
                if ($scope.data[i].classify.id == 1) {
                    $scope.flower.push($scope.data[i]);
                }
                else {
                    $scope.main.push($scope.data[i]);
                }
            }
        }else {
            $http
                .get('mGetKinds.do')
                .success(function (result) {
                    $scope.data = result;
                    localStorage.setItem('cbj-tea-KindsList' , JSON.stringify(result));
                    /*在全部种类中分离出花茶*/
                    for (var i = 0; i < $scope.data.length; i++) {
                        if ($scope.data[i].classify.id == 1) {
                            $scope.flower.push($scope.data[i]);
                        }
                        else {
                            $scope.main.push($scope.data[i]);
                        }
                    }
                });
            }
    }]);
    /**各种类商品**/
    app.controller('kindsGoodsController' , ['$scope' , '$routeParams', function($scope , $routeParams){
        $scope.data = [];
        $scope.kindsName = "";
        var kindId = $routeParams.kindId;

        var goodses = JSON.parse(localStorage.getItem('cbj-tea-GoodsList'));

        for(var i =0 ; i<goodses.length; i++){
            if(kindId == goodses[i].kindId){
                $scope.data.push(goodses[i]);
                //console.log($scope.data);
            }
        }
        var kindses = JSON.parse(localStorage.getItem('cbj-tea-KindsList'));

        //console.log(kindses[2]);
        for(var j=0; j<kindses.length; j++){
            if(kindId == kindses[j].id){
                $scope.kindsName = kindses[j].name;
            }
            //console.log($scope.kindsName);
        }

    }]);
    /** 推荐商品 - 首页 （二次请求）**/
    app.controller('recommendController' , ['$scope' , '$http' , function($scope , $http){
        //$scope.data = [];  /*全部推荐商品*/
        $scope.goods = [];
        $scope.homeData = [];  /*四件随机商品至首页显示*/

        var recommend = JSON.parse(localStorage.getItem('cbj-tea-recommend'));
        if(null != recommend){
            $scope.goods = recommend;

            /*随机抽取4件商品放到首页*/
            for(var k=0 ;k<4; k++){
                var index = Math.floor(Math.random() * $scope.goods.length);
                //console.log(index);
                $scope.homeData.push($scope.goods.splice(index,1)[0]);
            }
            //console.log($scope.homeData);
        }
        else {
            $http
                .get('getRecommend.do')
                .success(function (result) {

                    for (var i = 0; i < result.length; i++) {
                        var recommendClassify = JSON.parse(result[i]);
                        for(var j=0; j<recommendClassify.length; j++){
                            $scope.goods.push(recommendClassify[j]);
                        }
                    }
                    localStorage.setItem('cbj-tea-recommend' , JSON.stringify($scope.goods));

                    /*随机抽取4件商品放到首页*/
                    for(var k=0 ;k<4; k++){
                        var index = Math.floor(Math.random() * $scope.goods.length);
                        console.log(index);
                        $scope.homeData.push($scope.goods.splice(index,1)[0]);
                    }
                    //console.log($scope.homeData);
                });
        }
    }]);
    /** 茶文化 - 首页 （三次请求）**/
    app.controller('cultureController',['$scope' , '$http' , function($scope , $http){
        $scope.data = [];
        $scope.homeCulture = [];
        $scope.homeCultureFirst = "";

        var culture = JSON.parse(localStorage.getItem('cbj-tea-culture'));
        if(null != culture){
            $scope.data = culture;

            for(var i=1; i<4; i++){
                $scope.homeCultureFirst = $scope.data[0];
                $scope.homeCulture.push($scope.data[i]);
            }
        }
        else{
            $http
                .get('getAllCulture.do')
                .success(function(result){
                    $scope.data = result;
                    localStorage.setItem('cbj-tea-culture' , JSON.stringify(result));
                    //console.log(result);
                    $scope.homeCultureFirst = result[0];
                    for(var i=1; i<4; i++){
                        $scope.homeCulture.push(result[i]);
                    }
                })
        }
    }]);
    /** 茶文化 - 详情页 **/
    app.controller('articleController' , ['$scope' , '$routeParams' , function($scope , $routeParams){
        $scope.article = "";
        $scope.text = [];
        var id = $routeParams.cultureId;
        //console.log(id);
        var culture = JSON.parse(localStorage.getItem('cbj-tea-culture'));
        for(var i=0; i<culture.length; i++){
            if(id == culture[i].id){
                $scope.article = culture[i];
                console.log($scope.article.txt)

                var texts = $scope.article.txt.split("\r\n\r\n");
                //console.log(texts.length);
                for(var j=0; j<texts.length; j++){
                    $scope.text.push(texts[j]);
                }
                console.log($scope.text);
                console.log($scope.text.length);

                return;
            }
        }
        //console.log($scope.text);

    }])


})();


/**
 * 点击退出 - 个人中心
 */
function userExit(){
    sessionStorage.removeItem('cbj-tea-user');
    localStorage.removeItem('cbj-tea-Cart');
    window.location.href='#/';
}



//作业 HTML03 - 二、3
//(function (){
//    var url = "http://item.taobao.com/item.htm?a=1&b=2&c=&d=xxx&e=1";
//    var url2 = window.location.href;
//    var jsonUrl = {};
//    //url2 = url2.split("?")[1];
//    var arr = (url2.split("?")[1]).split("&");
//    for(var i=0 ;i<arr.length; i++){
//        jsonUrl[arr[i].split("=")[0]] = arr[i].split("=")[1];
//    }
//
//    console.log(jsonUrl);
//})();



















