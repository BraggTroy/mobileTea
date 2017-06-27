package com.tea.dao;

import com.tea.bean.AdImages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class AdImagesDao extends BaseDao {

    /**
     * 获取全部广告图片
     */
    public ArrayList<AdImages> getAllAdImages(){
        ArrayList<AdImages> adImageses = new ArrayList<AdImages>();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT id,image,orders FROM adimages");
            rs=psmt.executeQuery();
            while (rs.next()){
                AdImages adImages=new AdImages();
                adImages.setId(rs.getInt(1));
                adImages.setImageName(rs.getString(2));
                adImages.setOrder(rs.getInt(3));
                adImageses.add(adImages);
            }

        }
        catch (Exception ex){
            System.out.println("获取全部广告图片失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }

        return adImageses;
    }

    /**
     * 添加新的广告图
     */
    public int addNewAdImages(AdImages adImages){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("INSERT INTO adimages (image,orders) VALUES (?,?)");
            psmt.setString(1,adImages.getImageName());
            psmt.setInt(2,adImages.getOrder());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("添加新的广告图失败，错误原因：" + ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return val;
    }

    /**
     * 编辑  图片、顺序
     */
    public int changeAdImages(AdImages adImages){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE adimages SET image=?,orders=? WHERE id=?");
            psmt.setString(1,adImages.getImageName());
            psmt.setInt(2,adImages.getOrder());
            psmt.setInt(3,adImages.getId());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("编辑图片、顺序失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }

        return val;
    }

}
