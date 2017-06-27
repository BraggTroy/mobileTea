package com.tea.dao;

import com.tea.bean.Information;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/30.
 */
public class InformationDao extends BaseDao {
    Connection con=null;
    PreparedStatement psmt=null;
    ResultSet rs=null;
    /**
     * 加载所有资讯
     */
    public ArrayList<Information> getAllInformation(){
        ArrayList<Information> informations = new ArrayList<Information>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            String sql="select id,name,txt,image,time from information";
            psmt=con.prepareStatement(sql);
            rs=psmt.executeQuery();
            while (rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String txt=rs.getString(3);
                String image=rs.getString(4);
                String time=rs.getString(5);
                Information info=new Information(id,name,txt,image,time);
                informations.add(info);
            }
        }
        catch (Exception ex){
            System.out.println("加载所有资讯失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }


        return informations;
    }


    /**
     * 添加新资讯
     */
    public int addNewInformation(Information infor){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time=df.format(new Date()).split(" ")[0];// new Date()为获取当前系统时间
        try{
            con=getConnection();
            psmt=con.prepareStatement("INSERT INTO information(name,txt,image,time) VALUES (?,?,?,?)");
            psmt.setString(1,infor.getName());
            psmt.setString(2,infor.getTxt());
            psmt.setString(3,infor.getImage());
            psmt.setString(4,time);
            val=psmt.executeUpdate();

        }
        catch (Exception ex){
            System.out.println("添加新资讯失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return  val;
    }

    /**
     * 编辑
     */
    public int changeInformation(Information infor){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time=df.format(new Date()).split(" ")[0];// new Date()为获取当前系统时间
        try{
           con=getConnection();
            psmt=con.prepareStatement("UPDATE information SET name=?,txt=?,image=?,time=? WHERE id=?");
            psmt.setString(1,infor.getName());
            psmt.setString(2,infor.getTxt());
            psmt.setString(3,infor.getImage());
            psmt.setString(4,time);
            psmt.setInt(5,infor.getId());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("编辑资讯失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return  val;
    }

    public Information getInformationById(int id){
        Information information=new Information();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("SELECT id,name,txt,image,time FROM information where id=?");
            psmt.setInt(1,id);
            rs=psmt.executeQuery();
            if (rs.next()){
                information.setId(rs.getInt(1));
                information.setName(rs.getString(2));
                information.setTxt(rs.getString(3));
                information.setImage(rs.getString(4));
                information.setTime(rs.getString(5));
            }
        }
        catch (Exception ex){
            System.out.println("加载所有资讯失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return information;
    }



}
