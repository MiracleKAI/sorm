package com.miracle.sorm.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

    /**
     * 获得object的fieldName对应的get方法的值
     * @param fieldName get方法对应的属性名
     * @param object 对应类的对象
     * @return object的fieldName对应的get方法的值
     */
    public static Object invokeGeter( String fieldName, Object object){
        try {
            Method m = object.getClass().getMethod("get"+ StringUtils.firstChar2UpperCase(fieldName),null);
            return m.invoke(object,null);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
            return null;
    }

    /**
     * 调用object的对应field的set方法，并将参数设为fieldValue
     * @param fieldName setfield方法
     * @param object    对应类的对象
     * @param fieldsValue setfield的参数
     */
    public static void invokeSeter(String fieldName,Object fieldsValue, Object object){
       try{

           if(fieldsValue != null) {
               Method method = object.getClass().getDeclaredMethod("set" + StringUtils.firstChar2UpperCase(fieldName), fieldsValue.getClass());
               method.invoke(object, fieldsValue);
           }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
