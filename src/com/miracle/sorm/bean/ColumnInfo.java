package com.miracle.sorm.bean;

/**
 * 封装表中每一个字段的信息（如字段类型，主键，字段名）
 */
public class ColumnInfo {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 是否为主键 0为否，1为是
     */
    private int keyType;

    public ColumnInfo(String columnName, String columnType, int keyType) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.keyType = keyType;
    }

    public ColumnInfo(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public ColumnInfo(){
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        keyType = keyType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }


}
