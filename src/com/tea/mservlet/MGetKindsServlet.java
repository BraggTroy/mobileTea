package com.tea.mservlet;

import com.tea.bean.Kinds;
import com.tea.dao.KindsDao;
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
 * Created by Administrator on 2016/11/13.
 */
@WebServlet(name = "MGetKindsServlet" , urlPatterns = "/mGetKinds.do")
public class MGetKindsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        ArrayList<Kinds> kindses = new KindsDao().getAllKinds();

        JSONArray jsonArray = new JSONArray(kindses);
        String result = jsonArray.toString();

        PrintWriter out = response.getWriter();
        out.print(result);
    }
}
