package com.Users.beefood.utils;

import com.Users.beefood.model.GioHang;
import com.Users.beefood.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
//    public static final String BASE_URL="http://192.168.1.191:8686/banhang/";as
public static final String BASE_URL = "http://192.168.1.191:8686/banhang1/";
//public static final String BASE_URL="http://172.21.0.24:8686/banhang/";

    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";


    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current= new User();
    public static String statusOrder(int status){
        String result="";
        switch (status){
            case 0 :
                result = "Đơn hàng đang được xử lí";
                break;
            case 1 :
                result = "Đơn hàng đã được chấp nhận";
                break;
            case 2 :
                result = "Đơn hàng đã được giao đi";
                break;
            case 3 :
                result = "Giao thành công";
                break;
            case 4 :
                result = "Đơn hàng đã hủy";
                break;
            default:
                result = "...";
        }


        return result;
    }

}
