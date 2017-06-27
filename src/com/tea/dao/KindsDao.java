package com.tea.dao;

import com.tea.bean.Classify;
import com.tea.bean.Kinds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class KindsDao extends BaseDao {

    /**
     * 获取全部茶种类（全部茶品 ， 包括花茶 ， 通过筛选）
     */
    public ArrayList<Kinds> getAllKinds(){
        ArrayList<Kinds> kinds = new ArrayList<Kinds>();

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT k.id,k.name,k.classifyId,c.name as classifyName,k.image FROM kinds k INNER JOIN classify c ON k.classifyId=c.id");
            rs=psmt.executeQuery();
            while (rs.next()){
                Kinds kind=new Kinds();
                kind.setId(rs.getInt(1));
                kind.setName(rs.getString(2));
                Classify classify=new Classify(rs.getInt(3),rs.getString(4));
                kind.setClassify(classify);
                kind.setImage(rs.getString(5));
                kinds.add(kind);
            }
        }
        catch (Exception e){
            System.out.println("获取全部种类失败,原因"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return kinds;
    }

    /**
     *  添加新的经典茶品
     */
    public int addNewKinds(Kinds kinds){
        int val = 0;

        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("INSERT INTO  kinds(name,classifyId,image) VALUES(?,?,?)");
            psmt.setString(1,kinds.getName());
            psmt.setInt(2,kinds.getClassify().getId());
            psmt.setString(3,kinds.getImage());
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("添加新的经典茶品失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return  val;
    }

    /**
     * 编辑茶品（种类）信息，名称、所属茶系、图片
     */
    public int changeKinds(Kinds kinds){
        int val = 0;

        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE kinds SET name=?,classifyId=?,image=? WHERE id=?");
            psmt.setString(1,kinds.getName());
            psmt.setInt(2,kinds.getClassify().getId());
            psmt.setString(3,kinds.getImage());
            psmt.setInt(4,kinds.getId());
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("编辑茶品失败,原因"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return  val;
    }

    /**
     * 获取该种类下商品数量（from goods）
     */
    public int getGoodsNumByKindsId(int kindsId){
        int amount = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT COUNT(1) FROM kinds k INNER JOIN goods g ON k.id=g.KindId WHERE k.id=?");
            psmt.setInt(1,kindsId);
            rs=psmt.executeQuery();
            if(rs.last()){
                amount=rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("获取该种类下商品数量失败,原因"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return amount;
    }


    /**
     * 通过id获取kinds
     */
    public Kinds getKindsById(int id){

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT * FROM kinds WHERE id = ?");
            psmt.setInt(1,id);

            rs = psmt.executeQuery();

            if(rs.last()){
                Kinds kinds = new Kinds(rs.getInt(1),rs.getString(2),new ClassifyDao().getClassifyById(rs.getInt(3)),rs.getString(4));

                return kinds;
            }

        }
        catch (Exception e){
            System.out.println("获取该种类下商品数量失败,原因"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return null;
    }


    /**
     * 通过name获取kinds
     * @param kindName
     * @return
     */
    public Kinds getKindsByName(String kindName){

        Kinds kinds=new Kinds();

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT k.id,k.name,c.name,k.image FROM kinds k INNER JOIN classify c ON k.classifyId=c.id WHERE k.name=?");
            psmt.setString(1,kindName);
            rs=psmt.executeQuery();
            if(rs.next()){
                kinds.setId(rs.getInt(1));
                kinds.setName(rs.getString(2));
                Classify classify=new Classify();
                classify.setName(rs.getString(3));
                kinds.setClassify(classify);
                kinds.setImage(rs.getString(4));

                return kinds;
            }

        }
        catch (Exception ex){
            System.out.println("由KindName获取该茶系失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return null;
    }


/**
 * 验证种种类是否存在 并验证Id
 *
 * 新增
 */
    public int checkKindsName(String name){

        int kindsId = 0;

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT id from kinds WHERE name = ?");
            psmt.setString(1,name);

            rs = psmt.executeQuery();
            if (rs.last()){
                kindsId = rs.getInt(1);
            }


        }
        catch (Exception ex){
            System.out.println("由KindName获取该茶系失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return kindsId;

    }




}
