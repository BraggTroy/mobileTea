package com.tea.dao;

import com.tea.bean.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RegionDao extends BaseDao {
    /**
     * 获取全部 省、市
     */
    public ArrayList<Region> getRegions(){
        ArrayList<Region> regions = new ArrayList<Region>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT RegionCode,FullName,ShortName,ParentCode\n" +
                    "FROM regions\n" +
                    "WHERE LENGTH(RegionCode)<=4");
            rs = psmt.executeQuery();

            while (rs.next()) {
                Region region = new Region(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                regions.add(region);
            }
        }
        catch (Exception ex){
            System.out.println("获取全部省市，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return regions;
    }

    /**
     * 根据省名，获取该省的RegionCode
     */
    public String getRegionCodeByProvince(String provinceName){

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT RegionCode FROM regions WHERE FullName = ? AND LENGTH(RegionCode)=2");

            psmt.setString(1,provinceName);
            rs = psmt.executeQuery();
            if (rs.last()){

                String regionCode = rs.getString(1);
                return regionCode;
            }

        }
        catch (Exception ex){
            System.out.println("获取全部省市，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return null;
    }


    /**
     * 根据省，获取对应的市
     */
    public ArrayList getCityByProvince(String parentCode){
        ArrayList cityArrayList = new ArrayList();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT FullName\n" +
                    "FROM regions\n" +
                    "WHERE LENGTH(RegionCode)=4 AND parentCode=?");
            psmt.setString(1,parentCode);

            rs = psmt.executeQuery();
            while (rs.next()){

                cityArrayList.add(rs.getString(1));
            }

        }
        catch (Exception ex){
            System.out.println("根据省code获取对应市，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return cityArrayList;
    }

    /**
     * 获取省
     * @return
     */
    public ArrayList<Region> getProvince(){
        ArrayList<Region> regions = new ArrayList<Region>();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT RegionCode,FullName,ShortName,ParentCode\n" +
                    "FROM regions\n" +
                    "WHERE LENGTH(RegionCode)<=2");
            rs = psmt.executeQuery();

            while (rs.next()) {
                Region region = new Region(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                regions.add(region);
            }
        }
        catch (Exception ex){
            System.out.println("获取全部省，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return regions;
    }

}
