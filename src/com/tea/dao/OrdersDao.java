package com.tea.dao;

import com.tea.bean.Goods;
import com.tea.bean.Orders;
import com.tea.bean.RecordSet;
import com.tea.bean.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/31.
 */
public class OrdersDao extends BaseDao {

    /**
     * 获取对应用户的订单
     */
    public RecordSet<Orders> getMyOrders(String name,int start,int count){
        RecordSet<Orders> recordSet = new RecordSet<Orders>();
        recordSet.setPageSize(count);
        ArrayList<Orders> orderses =new ArrayList<Orders>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=getConnection();
            psmt=con.prepareStatement("SELECT o.name,o.goodsNum,o.state,o.time,g.id,g.name," +
                    "g.currentPrice,g.image,u.name FROM orders as o INNER JOIN goods as g ON " +
                    "o.goodsId=g.id INNER JOIN users as u ON o.userId=u.id WHERE u.name=? " +
                    "ORDER BY o.time DESC limit ?,?");
            psmt.setString(1,name);
            psmt.setInt(2,start);
            psmt.setInt(3,count);
            rs=psmt.executeQuery();
            while (rs.next()){
                String orderName=rs.getString(1);
                int goodsNum=rs.getInt(2);
                int state=rs.getInt(3);
                String time=rs.getString(4);
                Goods goods=new Goods();
                goods.setId(rs.getInt(5));
                goods.setName(rs.getString(6));
                goods.setCurrentPrice(rs.getDouble(7));
                goods.setImage(rs.getString(8));
                Users users=new Users();
                users.setName(rs.getString(9));
                Orders o=new Orders(0,orderName,goods,goodsNum,users,time,state);
                orderses .add(o);
            }
            recordSet.setRecordSet(orderses);

            rs = psmt.executeQuery("select count(1) from orders WHERE u.name=?");
            psmt.setString(1,name);
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.println("获取对应用户的订单失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return  recordSet;
    }


    /**
     * 确定下单，生成订单
     */
    public int addNewOrders(Orders orders){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time= df.format(new Date()).split(" ")[0];
        try {
            con=getConnection();
            psmt=con.prepareStatement("INSERT INTO orders(name,goodsId,goodsNum,userId,time,state) VALUES (?,?,?,?,?,?)");
            psmt.setString(1,orders.getName());
            psmt.setInt(2,orders.getGoods().getId());
            psmt.setInt(3,orders.getGoodsNum());
            psmt.setInt(4,orders.getUsers().getId());
            psmt.setString(5,time);
            psmt.setInt(6,orders.getState());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("生成订单失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return  val;
    }

    /**
     * 根据订单号 查询订单
     */
    public Orders getOrdersByOrderName(String orderName){
        Orders orders=new Orders();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=getConnection();
            psmt=con.prepareStatement("SELECT o.name,o.goodsNum,o.state,o.time,g.name,g.currentPrice,g.image,u.name FROM orders as o INNER JOIN goods as g ON o.goodsId=g.id INNER JOIN users as u ON o.userId=u.id WHERE o.name=?");
            psmt.setString(1,orderName);
            rs=psmt.executeQuery();
            if(rs.next()){
                String  name=rs.getString(1);
                int goodsNum=rs.getInt(2);
                int state=rs.getInt(3);
                String time=rs.getString(4);
                Goods goods=new Goods();
                goods.setName(rs.getString(5));
                goods.setCurrentPrice(rs.getDouble(6));
                goods.setImage(rs.getString(7));
                Users users=new Users();
                users.setName(rs.getString(8));
                orders=new Orders(0,name,goods,goodsNum,users,time,state);

            }
        }
        catch (Exception ex){
            System.out.println("根据订单号 查询订单失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return orders;
    }

    /**
     * 显示所有未处理的订单 （所有state == 0 的订单）
     */
    public ArrayList<Orders> getOrdersUntreated(){
        ArrayList<Orders> orderses = new ArrayList<Orders>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=getConnection();
            psmt=con.prepareStatement("SELECT o.name,o.goodsNum,o.state,o.time,g.name,g.currentPrice,g.image,u.name FROM orders as o INNER JOIN goods as g ON o.goodsId=g.id INNER JOIN users as u ON o.userId=u.id WHERE o.state=0");
            rs=psmt.executeQuery();
            while (rs.next()){
                String orderName=rs.getString(1);
                int goodsNum=rs.getInt(2);
                int state=rs.getInt(3);
                String time=rs.getString(4);
                Goods goods=new Goods();
                goods.setName(rs.getString(5));
                goods.setCurrentPrice(rs.getDouble(6));
                goods.setImage(rs.getString(7));
                Users users=new Users();
                users.setName(rs.getString(8));
                Orders o=new Orders(0,orderName,goods,goodsNum,users,time,state);
                orderses .add(o);
            }

        }
        catch (Exception ex){
            System.out.println("显示所有未处理的订单失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return orderses;
    }


    /**
     * 商家处理订单，接单  （对state进行操作，将0 -> 1）
     */
    public int treatOrders(int orderId){
        /*更新操作，将状态state变为1*/
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=getConnection();
            psmt=con.prepareStatement("UPDATE orders SET state=1 WHERE id=?");
            psmt.setInt(1,orderId);
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("商家处理订单，接单 失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }

    /**
     * 根据时间显示订单
     */
    public ArrayList<Orders> getOrdersByTime(String orderTime){
        ArrayList<Orders> orderses=new ArrayList<Orders>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=getConnection();
            psmt=con.prepareStatement("SELECT o.name,o.goodsNum,o.state,o.time,g.name,g.currentPrice,g.image,u.name FROM orders as o INNER JOIN goods as g ON o.goodsId=g.id INNER JOIN users as u ON o.userId=u.id WHERE o.time=?");
            psmt.setString(1,orderTime);
            rs=psmt.executeQuery();
            while (rs.next()){
                String orderName=rs.getString(1);
                int goodsNum=rs.getInt(2);
                int state=rs.getInt(3);
                String time=rs.getString(4);
                Goods goods=new Goods();
                goods.setName(rs.getString(5));
                goods.setCurrentPrice(rs.getDouble(6));
                goods.setImage(rs.getString(7));
                Users users=new Users();
                users.setName(rs.getString(8));
                Orders o=new Orders(0,orderName,goods,goodsNum,users,time,state);
                orderses .add(o);
            }
        }
        catch (Exception ex){
            System.out.println("根据时间显示订单失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return orderses;
    }


}

