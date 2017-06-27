package com.tea.dao;

import com.tea.bean.Carts;
import com.tea.bean.Goods;
import com.tea.bean.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/1.
 */
public class CartsDao extends BaseDao {
    /**
     * 显示购物车的商品
     * @return
     */
    public ArrayList<Carts> getGoodsFromCarts(int id){
        ArrayList<Carts> cartses=new ArrayList<Carts>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT c.id,g.id,g.name,g.image,g.currentPrice,c.goodsNum,u.name FROM carts c INNER JOIN goods g  \n" +
                    "INNER JOIN users u ON c.goodsId=g.id AND c.userId=u.id WHERE userId=?");
            psmt.setInt(1,id);
            rs=psmt.executeQuery();
            while (rs.next()){
                Carts cart=new Carts();
                cart.setId(rs.getInt(1));
                Goods goods=new Goods();
                goods.setId(rs.getInt(2));
                goods.setName(rs.getString(3));
                goods.setImage(rs.getString(4));
                goods.setCurrentPrice(rs.getDouble(5));
                cart.setGoods(goods);
                cart.setGoodsNum(rs.getInt(6));
                Users user=new Users();
                user.setName(rs.getString(7));
                cart.setUsers(user);
                cartses.add(cart);
            }
        }
        catch (Exception e){
            System.out.println("显示失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return cartses;
    }
    /**
     * 购物车添加商品
     * @return
     */
    public int insetGoods(int goodsId,int goodsNum,int userId){
        int val=0;
        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("INSERT INTO carts(goodsId,goodsNum,userId) VALUES(?,?,?)");
            psmt.setInt(1,goodsId);
            psmt.setInt(2,goodsNum);
            psmt.setInt(3,userId);
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("添加失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return  val;
    }

    /**
     * 根据id删除商品
     * @param catrsId
     * @return
     */
    public int deleteGoodsFromCarts(int catrsId){
        int val=0;
        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("DELETE FROM carts WHERE id=?");
            psmt.setInt(1,catrsId);
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("删除失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return  val;
    }

    /**
     *
     * @param num
     * @param catrsId
     * @return
     */
    public int updateNum(int num,int catrsId){
        int val=0;
        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE carts SET goodsNum=? WHERE id=?");
            psmt.setInt(1,num);
            psmt.setInt(2,catrsId);
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("删除失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }

    /**
     * 通过用户userId获取购物车信息
     * @param userId
     * @return
     */
    public ArrayList<Carts> getCartsByUserId(int userId){
//        System.out.println("userId:+:"+userId);
        ArrayList<Carts> cartses=new ArrayList<Carts>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT id,goodsId,goodsNum FROM carts WHERE userId = ?");
            psmt.setInt(1,userId);
            rs=psmt.executeQuery();
            while (rs.next()){

                int id = rs.getInt(1);
//                System.out.println("ra.getId(2):" + rs.getInt(2));
                Goods goods = new GoodsDao().getGoodsById(rs.getInt(2));
//                System.out.println("cart:goods?:"+goods.getName());
                int goodsNum = rs.getInt(3);
                Users user=new UserDao().getUserById(userId);

                Carts cart = new Carts(id , goodsNum , goods , user);
//                System.out.println("cartDaozhong:"+cart.getGoods().getName());

                cartses.add(cart);
            }
        }
        catch (Exception e){
            System.out.println("显示失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return cartses;
    }
}
