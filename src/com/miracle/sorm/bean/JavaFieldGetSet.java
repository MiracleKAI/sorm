package com.miracle.sorm.bean;

/**
 * 表的对应的bean类的set,get方法信息
 */
public class JavaFieldGetSet {

    /**
     * 属性的源码信息如 private int userId;
     */
    private String fieldInfo;

    /**
     * 属性的set方法信息如 public void setUserId(int userId){this.userId = userId;};
     */
    private String seterInfo;


    /**
     * 属性的get方法信息如 public int getUserId(){return userId;}
     */
    private String geterInfo;
    public JavaFieldGetSet(){

    }
    public JavaFieldGetSet(String fieldInfo, String seterInfo, String geterInfo) {
        this.fieldInfo = fieldInfo;
        this.seterInfo = seterInfo;
        this.geterInfo = geterInfo;
    }

    public String getSeterInfo() {
        return seterInfo;
    }

    public void setSeterInfo(String seterInfo) {
        this.seterInfo = seterInfo;
    }

    public String getGeterInfo() {
        return geterInfo;
    }

    public void setGeterInfo(String geterInfo) {
        this.geterInfo = geterInfo;
    }

    public String getFieldInfo() {

        return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
}
