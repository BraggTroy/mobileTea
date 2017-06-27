package com.tea.dao;

import com.tea.bean.Classify;
import com.tea.bean.Kinds;
import com.tea.bean.RecommendClassify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class RecommendClassifyDao extends BaseDao {

    /**
     * < 已经预加载全部茶系 >
     * 修改 茶系推荐顺序 order (前3个将会推送到首页)
     */
    public int ChangeRecommendClassifyOrder(int classifyId,int orders){
        int val = 0;

        Connection con = null;
        PreparedStatement psmt = null;
        //ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("UPDATE recommendclassify set orders=? where classifyId=?");
            psmt.setInt(1,orders);
            psmt.setInt(2,classifyId);
            val=psmt.executeUpdate();

        }
        catch (Exception ex){
            System.out.print("修改 茶系推荐顺序 order (前3个将会推送到首页)失败，错误原因："+ex.getMessage());
        }
        finally {
            //super.closeAll(rs , psmt , con);
            super.closeAll(null,psmt,con);
        }

        return val;
    }
    /**
     * 获取前三个茶种类（全部茶品 ， 包括花茶 ， 通过筛选）
     */
    public ArrayList<RecommendClassify> getRecommendClassify(){
        ArrayList<RecommendClassify> recommendClassifies = new ArrayList<RecommendClassify>();

        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("SELECT re.id,re.classifyId,c.name,re.orders FROM  recommendclassify as re INNER JOIN classify as c ON re.classifyId=c.id ORDER BY re.orders limit 3");
            rs=psmt.executeQuery();
            while (rs.next()){

                RecommendClassify recommendClassify=new RecommendClassify();
                recommendClassify.setId(rs.getInt(1));
                Classify classify=new Classify();
                classify.setId(rs.getInt(2));
                classify.setName(rs.getString(3));
                recommendClassify.setClassify(classify);
                recommendClassify.setOrder(rs.getInt(4));
                recommendClassifies.add(recommendClassify);


            }
        }
        catch (Exception e){
            System.out.println("获取前三个茶种类,原因"+e.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return recommendClassifies;
    }
}
