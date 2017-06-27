package com.tea.mservlet;

import com.tea.bean.Users;
import com.tea.dao.UserDao;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/11/15.
 */
@WebServlet(name = "MLoginServlet" , urlPatterns = "/mLogin.do")
public class MLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
//        System.out.println(name);
//        System.out.println(password);

        UserDao userDao = new UserDao();
        Users user = userDao.getInformationByNameOrEmailOrPhoneNumber(name,password);

        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        JSONObject jsonObject = new JSONObject(user);
        String result = jsonObject.toString();
        out.print(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
