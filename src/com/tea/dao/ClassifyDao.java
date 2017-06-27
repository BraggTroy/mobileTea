package com.tea.dao;

import com.tea.bean.Classify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ClassifyDao extends  BaseDao {

    /**
     * 获取全部茶系
     */
    public ArrayList<Classify> getAllClassify(){
        ArrayList<Classify> classifies =  new ArrayList<Classify>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT id,name from classify WHERE id!=1");
            rs=psmt.executeQuery();
            while (rs.next()){
                Classify c=new Classify();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                classifies.add(c);
            }
        }
        catch (Exception ex){
            System.out.println("获取全部经典茶系失败，错误原因：" + ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return classifies;
    }


    /**
     * 添加新的茶系
     */
    public int addNewClassify(String name){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("INSERT INTO classify (NAME) VALUES (?)");
            psmt.setString(1,name);
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("添加新的茶系失败，错误原因：" + ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }

    /**
     * 编辑该茶系
     */
    public int changeClassify(Classify classify){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE classify set NAME =? where id=?");
            psmt.setString(1,classify.getName());
            psmt.setInt(2,classify.getId());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("编辑茶系失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return  val;
    }

    /**
     * 通过classifyId获取该该茶系下的品类数量（from kinds）
     */
    public int getKindsNumByClassifyId(int classifyId){
        int amount = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("select count(*)from kinds WHERE classifyId=?");
            psmt.setInt(1,classifyId);
            amount=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("通过classifyId获取该该茶系下的品类数量失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return  amount;
    }



    /**
     * 由ClassifyId获取该茶系
     */
    public Classify getClassifyById(int classifyId){
        Classify classify = new Classify();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("select id,name from classify WHERE id=?");
            psmt.setInt(1,classifyId);
            rs=psmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                classify=new Classify(id,name);
            }

        }
        catch (Exception ex){
            System.out.println("由ClassifyId获取该茶系失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return classify;
    }

    /**
     * 由ClassifyName(茶系名，例如：红茶，花茶)获取该茶系
     */
    public Classify getClassifyByName(String name){
        Classify classify = new Classify();

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("select id,name from classify WHERE name =?");
            psmt.setString(1,name);
            rs=psmt.executeQuery();
            if(rs.next()){
                int id=rs.getInt(1);
                String classifyName=rs.getString(2);
                classify=new Classify(id,classifyName);
                return classify;
            }

        }
        catch (Exception ex){
            System.out.println("由ClassifyName获取该茶系失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return null;
    }


}
