package com.miracle.sorm.core;

/**
 * 创建指定的Query类
 */
public class QueryFactory {

    private static Class clazz = null;
    private QueryFactory(){//私有构造器
    }
    static{
        try {
            clazz = Class.forName(DBManager.getConfiguration().getQueryClass());//加载指定的Query类
            Class.forName("com.miracle.sorm.core.DBManager");
            Class.forName("com.miracle.sorm.core.TableContext");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得Query对象
     * @return Query对象
     */
    public static Query creatQuery(){
        try {
            return (Query)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
