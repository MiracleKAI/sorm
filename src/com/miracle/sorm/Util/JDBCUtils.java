package com.miracle.sorm.Util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtils {
    /**
     * 将属性信息填充到sql语句中
     * @param params 参数
     * @param preparedStatement preparedStatement 对象
     */
    public static void processParams(Object[] params, PreparedStatement preparedStatement){
        for(int i=0; i< params.length;i++){
            try {
                preparedStatement.setObject(i+1,params[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}
