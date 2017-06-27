package com.tea.mservlet;

import com.tea.util.GlobalSetting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/11/16.
 */
@WebServlet(name = "GetImageUrlServlet" , urlPatterns = "/getImageUrl.do")
public class GetImageUrlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String imageUrl = GlobalSetting.getImageRootUrl();

//        System.out.println(imageUrl);
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(imageUrl);
    }
}
