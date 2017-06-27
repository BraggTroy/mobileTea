package com.tea.dao;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.tea.bean.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2016/10/31.
 */
public class UserDao extends BaseDao {

    /**
     * 注册
     * @param users
     * @return
     */
    public int register(Users users){
        int val=0;
        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=getConnection();
            psmt=con.prepareStatement("INSERT INTO users(name,sex,password,email,phoneNumber) VALUES (?,?,?,?,?)");
            psmt.setString(1,users.getName());
            psmt.setString(2,users.getSex());
            psmt.setString(3,users.getPassword());
            psmt.setString(4,users.getEmail());
            psmt.setString(5,users.getPhoneNumber());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("注册失败，错误原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }


    /**
     * 查看用户资料
     */
    public Users getMyInformation(String userName){
        Users users = new Users();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("SELECT id,name,sex,password,image,email,phoneNumber,address from Users WHERE name=?");
            //赋值
            psmt.setString(1, userName);
            rs = psmt.executeQuery();
            if(rs.next()){
                //获取
                users.setId(rs.getInt(1));
                users.setName(rs.getString(2));
                users.setSex(rs.getString(3));
                users.setPassword(rs.getString(4));
                users.setImage(rs.getString(5));
                users.setEmail(rs.getString(6));
                users.setPhoneNumber(rs.getString(7));
                users.setAddress(rs.getString(8));
            }
        }
        catch (Exception ex){
            System.out.println("查看用户资料失败，失败原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return users;
    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    public Users getUserById(int id){
        Users users = new Users();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("SELECT id,name,sex,password,image,email,phoneNumber,address from Users WHERE id=?");
            //赋值
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            if(rs.next()){
                //获取
                users.setId(rs.getInt(1));
                users.setName(rs.getString(2));
                users.setSex(rs.getString(3));
                users.setPassword(rs.getString(4));
                users.setImage(rs.getString(5));
                users.setEmail(rs.getString(6));
                users.setPhoneNumber(rs.getString(7));
                users.setAddress(rs.getString(8));
            }
        }
        catch (Exception ex){
            System.out.println("根据id查找用户失败，失败原因："+ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return users;
    }


    /**
     * 修改个人资料
     */
    public int changeMyInformation(Users users){
        int val = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("UPDATE users SET name=?,sex=?,password=?,image=?,email=?,phoneNumber=?,address=? WHERE id=?");
            psmt.setString(1,users.getName());
            psmt.setString(2,users.getSex());
            psmt.setString(3,users.getPassword());
            psmt.setString(4,users.getImage());
            psmt.setString(5,users.getEmail());
            psmt.setString(6,users.getPhoneNumber());
            psmt.setString(7,users.getAddress());
            psmt.setInt(8, users.getId());
            val=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("修改个人资料失败，失败原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return  val;
    }

    /**
     * 统计用户量
     */
    public int getUsersAmount(){
        int amount = 0;
        Connection con=null;
        PreparedStatement psmt=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("SELECT COUNT(*) from users");
            amount=psmt.executeUpdate();
        }
        catch (Exception ex){
            System.out.println("统计用户量失败，失败原因："+ex.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return amount;
    }

    /**
     * 通过会员名查找会员信息
     */
    public Users getUsersByCurrentUser(String currentUser){

        Users users = new Users();

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = super.getConnection();
            psmt = con.prepareStatement("SELECT id,name,email,phoneNumber,password,sex FROM users\n" +
                    "WHERE name = ? OR email = ? OR phoneNumber=?");
            psmt.setString(1,currentUser);
            psmt.setString(2,currentUser);
            psmt.setString(3,currentUser);
            rs = psmt.executeQuery();

            if(rs.last()){
                users.setId(rs.getInt(1));
                users.setName(rs.getString(2));
                users.setEmail(rs.getString(3));
                users.setPhoneNumber(rs.getString(4));
                users.setPassword(rs.getString(5));
                users.setSex(rs.getString(6));
                return users;
            }
        }
        catch (Exception ex){
            System.out.print("查找用户异常："+ex.getMessage());
        }
        finally {
            super.closeAll(rs , psmt , con);
        }

        return null;
    }
    /**
     * 根据用户名或邮箱或手机登录
     * @param name
     * @return
     */
    public Users getInformationByNameOrEmailOrPhoneNumber(String name,String password){
        Users users = new Users();
        Connection con=null;
        PreparedStatement psmt=null;
        ResultSet rs=null;
        try{
            con=getConnection();
            psmt=con.prepareStatement("SELECT name,password,sex,email,phoneNumber,id from Users WHERE  password=? AND name=? OR email=? OR phoneNumber=? ");
            //赋值
            psmt.setString(1,password);
            psmt.setString(2, name);
            psmt.setString(3, name);
            psmt.setString(4, name);

            rs = psmt.executeQuery();
            if(rs.next()){
                //获取
                users.setName(rs.getString(1));
                users.setPassword(rs.getString(2));
                users.setSex(rs.getString(3));
                users.setEmail(rs.getString(4));
                users.setPhoneNumber(rs.getString(5));
                users.setId(rs.getInt(6));

            }
        }
        catch (Exception ex){
            System.out.println("根据用户名或邮箱或手机登录失败，失败原因：" + ex.getMessage());
        }
        finally {
            super.closeAll(rs,psmt,con);
        }
        return users;
    }

    /**
            * 忘记密码,通过手机号进行修改密码
    * @param pwd 密码
    * @param phone 电话
    * @return
            */
    public int updatePwdByPhone(String pwd,String phone){
        int val=0;
        Connection con=null;
        PreparedStatement psmt=null;
        try {
            con=super.getConnection();
            psmt=con.prepareStatement("UPDATE users SET password=? WHERE phoneNumber=?");
            psmt.setString(1,pwd);
            psmt.setString(2,phone);
            val=psmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("修改密码失败,原因:"+e.getMessage());
        }
        finally {
            super.closeAll(null,psmt,con);
        }
        return val;
    }

}

