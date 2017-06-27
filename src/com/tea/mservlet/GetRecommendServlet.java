package com.tea.mservlet;

import com.tea.bean.Goods;
import com.tea.bean.Recommend;
import com.tea.bean.RecommendClassify;
import com.tea.dao.RecommendClassifyDao;
import com.tea.dao.RecommendDao;
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
@WebServlet(name = "GetRecommendServlet" , urlPatterns = "/getRecommend.do")
public class GetRecommendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        ArrayList allgoods = new ArrayList();

        ArrayList<RecommendClassify> recommendClassifies = new RecommendClassifyDao().getRecommendClassify();
        for(int i=0; i<recommendClassifies.size(); i++){
            ArrayList<Goods> goodses = new RecommendDao().getRecommendGoodsByClassifyId(recommendClassifies.get(i).getClassify().getId() , 6);
            JSONArray jsonArray = new JSONArray(goodses);
            String gd = jsonArray.toString();
            allgoods.add(gd);
        }

        JSONArray jsonArray = new JSONArray(allgoods);
        String result = jsonArray.toString();

        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        out.print(result);


    }
}
