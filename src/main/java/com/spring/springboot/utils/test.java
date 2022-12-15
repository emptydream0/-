package com.spring.springboot.utils;

public class test {
    public static void main(String[] args) {
          byte[] bytes =  Base64Util.imageTobyte("E:\\Learning\\手机应用开发\\suxingShop\\src\\main\\files\\9cf0326e10d1488f93347439b7eb02fc.jpg");
        String base64 = Base64Util.encode(bytes);
        System.out.println(base64);

        Base64Util.decode(base64,"123.jpg","E:\\Learning\\手机应用开发\\suxingShop\\src\\main\\resources\\static\\");

    }
}
