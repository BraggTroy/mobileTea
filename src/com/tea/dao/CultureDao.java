package com.tea.dao;

import com.tea.bean.Culture;
import com.tea.bean.Kinds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/30.
 */
public class CultureDao extends BaseDao {

    /**
     * 获取全部茶文化
     */
    public ArrayList<Culture> getAllCulture(){
        ArrayList<Culture> cultures = new ArrayList<Culture>();

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT c.id,c.name,c.txt,c.image,k.id,k.name,c.time FROM culture c INNER JOIN kinds k ON c.kindId=k.id");
            rs=psmt.executeQuery();
            while (rs.next()){
                Culture culture=new Culture();
                culture.setId(rs.getInt(1));
                culture.setName(rs.getString(2));
                culture.setTxt(rs.getString(3));
                culture.setImage(rs.getString(4));

                Kinds kind=new Kinds();
                kind.setId(rs.getInt(5));
                kind.setName(rs.getString(6));

                culture.setKinds(kind);
                culture.setTime(rs.getString(7));

                cultures.add(culture);
            }
        }
        catch (Exception e){
            System.out.println("获取全部茶文化失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return  cultures;
    }

    /**
     * 添加 新的茶文化 文章
     */
    public int addNewCulture(Culture culture){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time= df.format(new Date()).split(" ")[0];
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("INSERT INTO culture(name,txt,image,kindId,time)VALUES(?,?,?,?,?)");
            psmt.setString(1,culture.getName());
            psmt.setString(2,culture.getTxt());
            psmt.setString(3,culture.getImage());
            psmt.setInt(4,culture.getKinds().getId());
            psmt.setString(5, time);
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("添加新的茶文化文章失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return val;
    }

    /**
     * 编辑茶文化
     */
    public int changeCulture(Culture culture){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time= df.format(new Date()).split(" ")[0];
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE culture set name=?,txt=?,image=?,kindId=?,time=? WHERE id=?");
            psmt.setString(1,culture.getName());
            psmt.setString(2,culture.getTxt());
            psmt.setString(3,culture.getImage());
            psmt.setInt(4,culture.getKinds().getId());
            psmt.setString(5,time);
            psmt.setInt(6,culture.getId());
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("编辑茶文化失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }

    /**
     * 通过id获取茶文化
     */
    public Culture getCultureById(int id){

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT c.id,c.name,c.txt,c.image,k.id,c.time \n" +
                    "FROM culture c INNER JOIN kinds k ON c.kindId=k.id\n" +
                    "WHERE c.id = ?");

            psmt.setInt(1,id);

            rs = psmt.executeQuery();

            if (rs.last()){

                Culture culture = new Culture(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),(new KindsDao().getKindsById(rs.getInt(5))),rs.getString(6));
                return culture;
            }

        }
        catch (Exception e){
            System.out.println("编辑茶文化失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return null;
    }

    /**
     * 通过kindid 查找文化
     * @param kindId
     * @return
     */
    public Culture getCultureByKindId(int kindId){
        Culture culture=new Culture();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT c.id,c.name,c.txt,c.image,k.id,k.name,c.time \n" +
                    "FROM culture c INNER JOIN kinds k ON c.kindId=k.id\n" +
                    "WHERE c.kindId=?");
            psmt.setInt(1,kindId);
            rs=psmt.executeQuery();
            while (rs.next()){
                culture.setId(rs.getInt(1));
                culture.setName(rs.getString(2));
                culture.setTxt(rs.getString(3));
                culture.setImage(rs.getString(4));
                Kinds kind=new Kinds();
                kind.setId(rs.getInt(5));
                kind.setName(rs.getString(6));
                culture.setKinds(kind);
                culture.setTime(rs.getString(7));
            }
        }
        catch (Exception e){
            System.out.println("获取全部茶文化失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return culture;
    }

    /**
     * 经典茶系推荐
     * @return
     */
    public Culture recommendClassicsCulture(){
        Culture culture=new Culture();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT c.id,c.name,c.txt,c.image,k.id,k.name,c.time FROM culture c \n" +
                    "INNER JOIN kinds k ON c.kindId=k.id WHERE k.classifyId !=1 LIMIT 1");
            rs=psmt.executeQuery();
            while (rs.next()){
                culture.setId(rs.getInt(1));
                culture.setName(rs.getString(2));
                culture.setTxt(rs.getString(3));
                culture.setImage(rs.getString(4));
                Kinds kind=new Kinds();
                kind.setId(rs.getInt(5));
                kind.setName(rs.getString(6));
                culture.setKinds(kind);
                culture.setTime(rs.getString(7));
            }
        }
        catch (Exception e){
            System.out.println("获取推荐茶文化失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return culture;
    }

    /**
     * 时尚茶系推荐
     * @return
     */
    public Culture recommendFlowerTeaCulture(){
        Culture culture=new Culture();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT c.id,c.name,c.txt,c.image,k.id,k.name,c.time FROM culture c \n" +
                    "INNER JOIN kinds k ON c.kindId=k.id WHERE k.classifyId =1 LIMIT 1");
            rs=psmt.executeQuery();
            while (rs.next()){
                culture.setId(rs.getInt(1));
                culture.setName(rs.getString(2));
                culture.setTxt(rs.getString(3));
                culture.setImage(rs.getString(4));
                Kinds kind=new Kinds();
                kind.setId(rs.getInt(5));
                kind.setName(rs.getString(6));
                culture.setKinds(kind);
                culture.setTime(rs.getString(7));
            }
        }
        catch (Exception e){
            System.out.println("获取推荐茶文化失败，原因:"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return culture;
    }
}
