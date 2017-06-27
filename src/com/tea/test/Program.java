package com.tea.test;

import com.tea.bean.*;
import com.tea.dao.ClassifyDao;
import com.tea.dao.GoodsDao;
import com.tea.dao.OrdersDao;
import com.tea.dao.UserDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/30.
 */
public class Program {
    public static void main(String[] args){
        /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time= df.format(new Date()).split(" ")[0];
        System.out.println(time);// new Date()为获取当前系统时间*/
        RecordSet<Goods> recordSet=new GoodsDao().getFlowerTeaByKindIdAndSoldNumDesc("茉莉花茶",0,12);
        for(Goods g:recordSet.getRecordSet()){
            System.out.println(g.getSoldNumber());
        }
    }
}
