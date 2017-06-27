package com.tea.dao;

import com.tea.bean.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class RecommendDao extends BaseDao {

    /**
     * 按茶系Id分别显示推荐商品
     */
    public ArrayList<Goods> getRecommendGoodsByClassifyId(int classifyId,int limit){
        ArrayList<Goods> goodsArrayList = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT g.id,g.name,g.currentPrice,g.originalPrice,g.field,g.effect,g.unit,g.image,g.number,g.soldNumber,g.classifyId,g.kindId,g.goodstxt FROM goods as g INNER JOIN recommend as re on re.goodsId=g.id WHERE re.classifyId=? limit ?");
            psmt.setInt(1,classifyId);
            psmt.setInt(2,limit);
            rs=psmt.executeQuery();
            while (rs.next()){
                int id=rs.getInt(1);
                String goodsName=rs.getString(2);
                double goodsCurrentPrice=rs.getDouble(3);
                double goodsOriginalPrice=rs.getDouble(4);
                String field=rs.getString(5);
                String effect=rs.getString(6);
                String unit=rs.getString(7);
                String goodsImage=rs.getString(8);
                int number=rs.getInt(9);
                int soldNumber=rs.getInt(10);
                int goodsClassifyId=rs.getInt(11);
                int goodsKindId=rs.getInt(12);
                String goodsTxt=rs.getString(13);

                Goods g=new Goods(id,goodsName,goodsCurrentPrice,goodsOriginalPrice,field,effect,unit,goodsImage,number,soldNumber,goodsClassifyId,goodsKindId,goodsTxt);
                goodsArrayList.add(g);
            }

        }
        catch (Exception ex){
            System.out.println("按茶系Id分别显示推荐商品失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return goodsArrayList;
    }


    public ArrayList<Goods> getRecommendGoodsBySoldNum(int limit){
        ArrayList<Goods> goodsArrayList = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT g.id,g.name,g.currentPrice,g.originalPrice,g.field,g.effect," +
                    "g.unit,g.image,g.number,g.soldNumber,g.classifyId,g.kindId,g.goodstxt " +
                    "FROM goods as g INNER JOIN recommend as re on re.goodsId=g.id ORDER BY g.soldNumber DESC limit ?");
            psmt.setInt(1,limit);
            rs=psmt.executeQuery();
            while (rs.next()){
                int id=rs.getInt(1);
                String goodsName=rs.getString(2);
                double goodsCurrentPrice=rs.getDouble(3);
                double goodsOriginalPrice=rs.getDouble(4);
                String field=rs.getString(5);
                String effect=rs.getString(6);
                String unit=rs.getString(7);
                String goodsImage=rs.getString(8);
                int number=rs.getInt(9);
                int soldNumber=rs.getInt(10);
                int goodsClassifyId=rs.getInt(11);
                int goodsKindId=rs.getInt(12);
                String goodsTxt=rs.getString(13);

                Goods g=new Goods(id,goodsName,goodsCurrentPrice,goodsOriginalPrice,field,effect,unit,goodsImage,number,soldNumber,goodsClassifyId,goodsKindId,goodsTxt);
                goodsArrayList.add(g);
            }

        }
        catch (Exception ex){
            System.out.println("高端茶礼失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return goodsArrayList;
    }
}
