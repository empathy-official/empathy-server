package com.server.empathy.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtil {

    public static String stampFormatSimple(Timestamp timestamp){
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String result = format.format(date);
        return result;
    }

    public static String stampFormatDetail(Timestamp timestamp){
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
        String result = format.format(date);
        return result;
    }
}
