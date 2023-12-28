package com.Users.beefood.utils;

import com.Users.beefood.model.GioHang;
import com.Users.beefood.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
//    public static final String BASE_URL="http://192.168.1.191:8686/banhang/";
public static final String BASE_URL = "http://192.168.1.17:8686/banhang1/";
//public static final String BASE_URL="http://172.21.0.24:8686/banhang/";

    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current= new User();
}
