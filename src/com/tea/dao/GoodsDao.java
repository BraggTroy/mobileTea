package com.tea.dao;

import com.tea.bean.Classify;
import com.tea.bean.Goods;
import com.tea.bean.Kinds;
import com.tea.bean.RecordSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class GoodsDao extends BaseDao {

    /**
     * 加载全部商品 - Mobile
     */
    public ArrayList<Goods> getGoodsList(){
        ArrayList<Goods> goodses = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt " +
                    "FROM goods INNER JOIN classify INNER JOIN kinds " +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id ");
            rs = psmt.executeQuery();

            while(rs.next()){
                Goods good = createGoods(rs);
                goodses.add(good);
            }

        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return goodses;
    }
    /**
     * 按照销量排序  -  Mobile
     */
    public ArrayList<Goods> getGoodsBySoldNum(){
        ArrayList<Goods> goodses = new ArrayList<Goods>();
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt " +
                    "FROM goods INNER JOIN classify INNER JOIN kinds  " +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id " +
                    "ORDER BY soldNumber DESC ");
            rs = psmt.executeQuery();
            while (rs.next()){
                Goods good = createGoods(rs);
                goodses.add(good);
            }

        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return goodses;
    }


    /**
     * 加载经典茶系全部商品
     */
    public RecordSet<Goods> getAllGoods(int start , int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt " +
                    "FROM goods INNER JOIN classify INNER JOIN kinds " +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id " +
                    "WHERE classify.id != 1 " +
                    "LIMIT ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变

            psmt.setInt(1,start);
            psmt.setInt(2,count);

            rs = psmt.executeQuery();

            while(rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }




    /**
     * 经典茶系按照销量排序
     * @return
     */
    public RecordSet<Goods> getGoodsOrderByBySoldNum(int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds  \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id  \n" +
                    "WHERE classify.id != 1 \n" +
                    "ORDER BY soldNumber DESC \n" +
                    "limit ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变

            psmt.setInt(1,start);
            psmt.setInt(2,count);

            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }


    /**
     * 通过Id 查询商品信息
     */
    public Goods getGoodsById(int id){

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt \n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id \n" +
                    "WHERE goods.id = ?");
            psmt.setInt(1,id);
            rs=psmt.executeQuery();
            if(rs.last()){
                Goods goods = createGoods(rs);
//                System.out.println(goods.getName());
                return goods;
            }

        }
        catch (Exception ex){
            System.out.println("通过Id 查询商品信息失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
    return null;

    }

    /**
     * 通过 商品名 模糊匹配 查询相关商品信息
     */
    public ArrayList<Goods> getGoodsByNameLike(String nameLike){
        ArrayList<Goods> goodsArrayList = new ArrayList<Goods>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt FROM goods INNER JOIN classify INNER JOIN kinds ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id WHERE goods.name LIKE ?");
            psmt.setString(1,"%"+nameLike+"%");
            rs=psmt.executeQuery();
            while (rs.next()){
                Goods good = createGoods(rs);
                goodsArrayList.add(good);
            }

        }
        catch (Exception ex){
            System.out.println("通过 商品名 模糊匹配 查询相关商品信息失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return goodsArrayList;
    }


    /**
     * 根据kindid获取商品
     * @param start
     * @param count
     * @param kindId
     * @return
     */
    public RecordSet<Goods> getFlowerTeaByNameAndSort(int kindId,int sort,int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            if(sort==1){
                con = super.getConnection();
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id =1 AND goods.KindId=? ORDER BY goods.id LIMIT ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
                psmt.setInt(1,kindId);
                psmt.setInt(2,start);
                psmt.setInt(3,count);
                rs=psmt.executeQuery();
            }
            else if (sort==2){
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id =1 AND goods.KindId=? ORDER BY goods.soldNumber DESC LIMIT ?,?");
                            psmt.setInt(1,kindId);
                            psmt.setInt(2,start);
                            psmt.setInt(3,count);
                            rs=psmt.executeQuery();

            }
            else if (sort==3){
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id =1 AND goods.KindId=? ORDER BY goods.currentPrice DESC LIMIT ?,?");
                        psmt.setInt(1,kindId);
                        psmt.setInt(2,start);
                        psmt.setInt(3,count);
                        rs=psmt.executeQuery();
            }
            else {
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id =1 AND goods.KindId=? ORDER BY goods.currentPrice  LIMIT ?,?");
                        psmt.setInt(1,kindId);
                        psmt.setInt(2,start);
                        psmt.setInt(3,count);
                        rs=psmt.executeQuery();
            }

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            while (rs.next()){
                Goods good=createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 经典茶系按照价格从高到低排序
     * @return
     */
    public RecordSet<Goods> getGoodsOrderByPriceDesc(int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds  \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id  \n" +
                    "WHERE classify.id != 1 \n" +
                    "ORDER BY currentPrice DESC \n" +
                    "limit ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变

            psmt.setInt(1,start);
            psmt.setInt(2,count);

            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 经典茶系按照价格从低到高
     * @return
     */
    public RecordSet<Goods> getGoodsOrderByPrice(int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds  \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id  \n" +
                    "WHERE classify.id != 1 \n" +
                    "ORDER BY currentPrice \n" +
                    "limit ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变

            psmt.setInt(1,start);
            psmt.setInt(2,count);

            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 花茶系列按照价格从高到低排序
     * @param start
     * @param count
     * @return
     */
    public RecordSet<Goods> getFlowerTeaOrderByPriceDesc(int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds  \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id  \n" +
                    "WHERE classify.id = 1 \n" +
                    "ORDER BY currentPrice DESC \n" +
                    "limit ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变

            psmt.setInt(1,start);
            psmt.setInt(2,count);

            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 花茶系列按照价格从低到高排序
     * @return
     */
    public RecordSet<Goods> getFlowerTeaOrderByPrice(int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds  \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id  \n" +
                    "WHERE classify.id = 1 \n" +
                    "ORDER BY currentPrice \n" +
                    "limit ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变

            psmt.setInt(1,start);
            psmt.setInt(2,count);

            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }



    public RecordSet<Goods> getClassifyByNameAndSort(int kindId,int sort,int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            if(sort==1){
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id !=1 AND goods.KindId=? ORDER BY goods.id LIMIT ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
                psmt.setInt(1,kindId);
                psmt.setInt(2,start);
                psmt.setInt(3,count);
                rs=psmt.executeQuery();

            }
            else if(sort==2){
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id !=1 AND goods.KindId=? ORDER BY goods.soldNumber DESC LIMIT ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
                psmt.setInt(1,kindId);
                psmt.setInt(2,start);
                psmt.setInt(3,count);
                rs=psmt.executeQuery();
            }
            else if(sort==3){
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id !=1 AND goods.KindId=? ORDER BY goods.currentPrice DESC LIMIT ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
                psmt.setInt(1,kindId);
                psmt.setInt(2,start);
                psmt.setInt(3,count);
                rs=psmt.executeQuery();
            }
            else {
                psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                        "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                        "FROM goods INNER JOIN classify INNER JOIN kinds\n" +
                        "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                        "WHERE classify.id !=1 AND goods.KindId=? ORDER BY goods.currentPrice LIMIT ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
                psmt.setInt(1,kindId);
                psmt.setInt(2,start);
                psmt.setInt(3,count);
                rs=psmt.executeQuery();

            }

            while (rs.next()){
                Goods good=createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);

            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("根据品种查询商品失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }


    /**
     * kindId先筛选,价格由高到低后筛选
     * @param kindName
     * @return
     */
    public RecordSet<Goods> getGoodsByKindIdAndPriceDesc(String kindName,int start,int count){

        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods=new ArrayList<Goods>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id \n" +
                    "WHERE classify.id != 1 and kinds.name=?\n" +
                    "ORDER BY currentPrice DESC limit ?,?");

//          id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            psmt.setString(1, kindName);
            psmt.setInt(2,start);
            psmt.setInt(3,count);
            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }
            recordSet.setRecordSet(goods);
            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");

            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("二重筛选失败,原因:"+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }
    /**
     * 经典茶系先根据kindId筛选,再通过价格由低到高进行筛选
     * @param kindName
     * @return
     */
    public RecordSet<Goods> getGoodsByKindIdAndPrice(String kindName,int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods=new ArrayList<Goods>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id \n" +
                    "WHERE classify.id != 1 and kinds.name=?\n" +
                    "ORDER BY currentPrice limit ?,?");

//          id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            psmt.setString(1, kindName);
            psmt.setInt(2,start);
            psmt.setInt(3,count);
            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }
            recordSet.setRecordSet(goods);
            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");

            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("二重筛选失败,原因:"+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }
    /**
     * 经典茶系先根据kindId筛选,再通过销量由高到低进行筛选
     * @param kindName
     * @return
     */
    public RecordSet<Goods> getGoodsByKindIdAndSoldNumDesc(String kindName,int start,int count){

        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods=new ArrayList<Goods>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id \n" +
                    "WHERE classify.id != 1 and kinds.name=?\n" +
                    "ORDER BY soldNumber DESC limit ?,?");

//          id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            psmt.setString(1, kindName);
            psmt.setInt(2,start);
            psmt.setInt(3,count);
            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }
            recordSet.setRecordSet(goods);
            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId!=1");

            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("二重筛选失败,原因:"+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 时尚花茶先根据kindId筛选,再通过价格由高到低进行筛选
     * @param kindName
     * @return
     */
    public RecordSet<Goods> getFlowerTeaByKindIdAndPriceDesc(String kindName,int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods = new ArrayList<Goods>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id\n" +
                    "WHERE classify.id = 1 and kinds.name=?\n" +
                    "ORDER BY currentPrice DESC limit ?,?");

//            id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            psmt.setString(1, kindName);
           psmt.setInt(2,start);
            psmt.setInt(3,count);

            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }

            recordSet.setRecordSet(goods);
            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1");
            //rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1 AND kindId=?" +
            //        "ORDER BY currentPrice DESC");
           // psmt.setInt(1,kindId);
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("时尚花茶kindId,价格由高到低筛选失败,原因:"+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 时尚花茶先根据kindId筛选,再通过价格由低到高进行筛选
     * @param kindName
     * @return
     */
    public RecordSet<Goods> getFlowerTeaByKindIdAndPrice(String kindName,int start,int count){
         RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);
        ArrayList<Goods> goods=new ArrayList<Goods>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id \n" +
                    "WHERE classify.id = 1 and kinds.name=?\n" +
                    "ORDER BY currentPrice limit ?,?");

//          id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            psmt.setString(1, kindName);
            psmt.setInt(2,start);
            psmt.setInt(3,count);
            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }
            recordSet.setRecordSet(goods);
            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1 ");

            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("二重筛选失败,原因:"+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }

    /**
     * 时尚花茶先根据kindId筛选,再通过销量由高到低进行筛选
     * @param kindName
     * @return
     */
    public RecordSet<Goods> getFlowerTeaByKindIdAndSoldNumDesc(String kindName,int start,int count){
        RecordSet<Goods> recordSet = new RecordSet<Goods>();
        recordSet.setPageSize(count);

        ArrayList<Goods> goods=new ArrayList<Goods>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT goods.id,goods.name,goods.currentPrice,goods.originalPrice,goods.field,goods.effect,goods.unit,\n" +
                    "goods.image,goods.number,goods.soldNumber,classify.name,kinds.name,goods.goodsTxt\n" +
                    "FROM goods INNER JOIN classify INNER JOIN kinds \n" +
                    "ON goods.ClassifyId = classify.id AND goods.KindId = kinds.id \n" +
                    "WHERE classify.id = 1 and kinds.name=?\n" +
                    "ORDER BY soldNumber DESC limit ?,?");

//          id != 1 表示非花茶，数据库要保证花茶的id为1，且不改变
            psmt.setString(1, kindName);
            psmt.setInt(2,start);
            psmt.setInt(3,count);
            rs = psmt.executeQuery();

            while (rs.next()){
                Goods good = createGoods(rs);
                goods.add(good);
            }
            recordSet.setRecordSet(goods);
            rs = psmt.executeQuery("select count(1) from goods WHERE ClassifyId=1 ");
            if(rs.next()){
                recordSet.setRowCount(rs.getInt(1));
            }
        }
        catch (Exception ex){
            System.out.print("二重筛选失败,原因:"+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }
        return recordSet;
    }
    /////////////////////根据结果集中的一条结果，创建一个Goods对象
    public Goods createGoods(ResultSet rs){
        try {
            int id = rs.getInt(1);
            String goodsName = rs.getString(2);
            Double currentPrice = rs.getDouble(3);
            Double originalPrice = rs.getDouble(4);
            String field = rs.getString(5);
            String effect = rs.getString(6);
            String unit = rs.getString(7);
            String image = rs.getString(8);

            int number = rs.getInt(9);
            int soldNumber = rs.getInt(10);
            String ClassifyName=rs.getString(11);
            String KindName=rs.getString(12);
            String goodsTxt = rs.getString(13);

            Goods g = new Goods();
            g.setId(id);
            g.setName(goodsName);
            g.setCurrentPrice(currentPrice);
            g.setOriginalPrice(originalPrice);
            g.setField(field);
            g.setEffect(effect);
            g.setUnit(unit);
            g.setImage(image);
            g.setNumber(number);
            g.setSoldNumber(soldNumber);
            //
            Classify classify=new ClassifyDao().getClassifyByName(ClassifyName);
            g.setClassifyId(classify.getId());
            //
            Kinds kinds=new KindsDao().getKindsByName(KindName);
            g.setKindId(kinds.getId());

            g.setGoodsTxt(goodsTxt);
            return g;
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }

        return null;
    }
    /**
     * 修改商品数量
     * @param number
     * @param soldNum
     * @param id
     * @return
     */
    public int updateGoodsNum(int number,int soldNum,int id){
        int val=0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE goods SET number=?,soldNumber=? WHERE id=?");
            psmt.setInt(1,number);
            psmt.setInt(2,soldNum);
            psmt.setInt(3,id);
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("修改数量失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }

}
