/**
 * Created by Administrator on 2016/11/7.
 */

/************ 头部 **************/
$(function(){
    var $search = $("#searchText");
    /**
     * 搜索表单 获取焦点
     */
    $search.bind("focus",function(){
        $(this).css({"background-color":"#ffffff","color":"#333"}).val("");
        $(this).parent().css("background-color","#ffffff");
        $("#searchBtn").css("color","#333");
        $("#hotSearch").css("display","block");
    });
    /**
     * 搜索表单 失去焦点
     */
    $search.bind("blur",function(){
        $(this).css({"background-color":"#4a9400","color":"#fff"}).val("搜索商品……");
        $(this).parent().css("background-color","#4a9400");
        $("#searchBtn").css("color","#fff");
        $("#hotSearch").css("display","none");
    });

    /** 主体div对象 **/
    var $pageIndex = $("#pageIndex");
    var $pageGoods = $("#pageGoods");
    var $pageCart = $("#pageCart");
    var $pageMine = $("#pageMine");
    /**
     * nav 点击事件绑定
     */
    $("#index").bind("click",function(){  navClick(this); });
    $("#goods").bind("click",function(){ navClick(this); });
    $("#cart").bind("click",function(){ navClick(this); });
    $("#mine").bind("click",function(){ navClick(this); });


});

/**
 * function() nav点击事件
 */
function navClick(nav){
    $(nav).parent().parent().find('div').removeClass('active');
    $(nav).addClass('active');

}

/**
 * cart_edit 购物车页面 点击编辑
 */
function cart_edit(source){
    $(source).css('display','none');
    $(source).next().css('display','inline-block');
    $(source).parent().parent().next().find('.edit_changeNum').css('display','block');

}
/** 点击完成 **/
function cart_save(source){
    $(source).css('display','none');
    $(source).prev().css('display','inline-block');
    $(source).parent().parent().next().find('.edit_changeNum').css('display','none');
}
/**
 * 点击删除
 */
function cart_delete(){
    $("#confirm_deleteBg").css('display','block');
}
/**
 * 取消删除
 */
function cart_cancel(){
    $("#confirm_deleteBg").css('display','none');
}
/**
 * 确定删除
 */
function cartdelete_confirm(){

}


/**
 * 商品详情 未登录点击加入购物车
 */
function noUser_click(){
    nouserbtn.style.display = 'block';
}
/**
 * 商品详情 点击取消登录
 */
function detail_cancel(){
    $("#nouserbtn").css('display','none');
}


/**
 * 全部商品页面 - 点击筛选
 */
function showKinds(){
    goods_chooseClick.style.display = 'block';
    $("#goods_kinds").css({'transition': 'width 2s', 'width': 'auto'})
}
/** **/
function hideKinds(){
    $("#goods_kinds").css({'transition': 'width 2s', 'width': '0'});
    goods_chooseClick.style.display = 'none';
}




