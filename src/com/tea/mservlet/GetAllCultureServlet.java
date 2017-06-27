package com.tea.mservlet;

import com.tea.bean.Culture;
import com.tea.dao.CultureDao;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/14.
 */
@WebServlet(name = "GetAllCultureServlet" , urlPatterns = "/getAllCulture.do")
public class GetAllCultureServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        ArrayList<Culture> cultures = new CultureDao().getAllCulture();
        JSONArray jsonArray = new JSONArray(cultures);
        String result = jsonArray.toString();

        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(result);

    }
}
