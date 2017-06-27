package com.tea.mservlet;

import com.tea.bean.Goods;

import com.tea.dao.GoodsDao;

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
 * Created by Administrator on 2016/11/9.
 */
@WebServlet(name = "MGetGoodsListServlet" , urlPatterns = "/mGetGoodsList.do")
public class MGetGoodsListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        ArrayList<Goods> goodses = new GoodsDao().getGoodsList();
        JSONArray jsonArray = new JSONArray(goodses);
        String result = jsonArray.toString();

        out.print(result);

    }
}
