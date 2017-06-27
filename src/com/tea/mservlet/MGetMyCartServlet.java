package com.tea.mservlet;

import com.tea.bean.Carts;
import com.tea.dao.CartsDao;
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
 * Created by Administrator on 2016/11/18.
 */
@WebServlet(name = "MGetMyCartServlet" , urlPatterns = "/mGetMyCart.do")
public class MGetMyCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
//        System.out.println(id);

        ArrayList<Carts> cartses = new CartsDao().getCartsByUserId(id);

        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        JSONArray jsonArray = new JSONArray(cartses);
        String result = jsonArray.toString();

        out.print(result);
    }
}
