package com.miracle.sorm.Util;

public class StringUtils {
    /**
     * 将首字母变为大写
     * @param str 需修改的字符串
     * @return 修改后的字符串
     */
    public static String firstChar2UpperCase(String str){
        //获取首字母（已变为大写）
        String firstChar = str.toUpperCase().substring(0,1);
        //获取首字母后面的字符串
        String mainString = str.substring(1);
        //将字符串连接
        return firstChar+mainString;
    }
}
