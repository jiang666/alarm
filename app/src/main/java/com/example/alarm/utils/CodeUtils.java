package com.example.alarm.utils;

/**
 * @desc: 身份信息加密工具类
 * @author: shy
 * @date: 2020/12/21 9:45
 */
public class CodeUtils {

    /**
     * 用户姓名的打码隐藏加星号加*
     * 名字是两位时: 张三--> *三
     * 名字是三位及以上时: 燕双鹰 --> 燕*鹰
     *
     * @return 处理完成的姓名
     */
    public static String nameMask(String name){
        String res = "";
        if (name.length() == 2){
            res = name.replaceAll("^.", "*");
        }else if (name.length() > 2){
            res = name.replaceAll("(?<=.).(?=.)", "*");
        }
        return res;
    }
    /**
     * 用户身份证号码的打码隐藏加星号加*
     *
     * @return 处理完成的身份证
     */
    public static String idCardMask(String idCardNum) {
        String res = "";
        if (!StringUtils.isEmpty(idCardNum)) {
            StringBuilder stringBuilder = new StringBuilder(idCardNum);
            res = stringBuilder.replace(6, 14, "********").toString();
        }
        return res;
    }
    /**
     * 用户电话号码的打码隐藏加星号加*
     *
     * @return 处理完成电话号码
     */
    public static String phoneMask(String phone) {
        String res = "";
        if (!StringUtils.isEmpty(phone)) {
            StringBuilder stringBuilder = new StringBuilder(phone);
            res = stringBuilder.replace(3, 7, "****").toString();
        }
        return res;
    }
}
