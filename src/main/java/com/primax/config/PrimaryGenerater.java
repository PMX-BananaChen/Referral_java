package com.primax.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrimaryGenerater {

    private static final Logger logger = LoggerFactory.getLogger(PrimaryGenerater.class);

    private static String SERIAL_NUMBER = "001" ;
    private static PrimaryGenerater primaryGenerater = null;

    private PrimaryGenerater(){

    }

    /**
     * 取得PrimaryGenerater的单例实现
     *
     * @return
     */
    public static PrimaryGenerater getInstance() {
        if (primaryGenerater == null) {
            synchronized (PrimaryGenerater.class) {
                if (primaryGenerater == null) {
                    primaryGenerater = new PrimaryGenerater();
                }
            }
        }
        return primaryGenerater;
    }


    /**
     * 年月日+001
     * 20200202001
     * 20200224001
     * 每月从001开始计数
     * */
    public static synchronized String getnumber(String thisCode){

        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String thisData = thisCode.substring(2, 10);

        //这个判断就是判断你数据取出来的最后一个业务单号是不是当月的
        if(!thisData.equals(formatter.format(date))){

            thisData = formatter.format(date);
            //如果是新的一月的就直接变成0001
            id ="TJ" + thisData + "001";
        }else{
            DecimalFormat df = new DecimalFormat("000");
            //不是新的一月就累加
            id ="TJ"+thisData
                    + df.format(1 + Integer.parseInt(thisCode.substring(10, 13)));
        }
        logger.info("idddddd================"+id);
        return id;
    }
}
