package com.miracle.sorm.bean;

import java.util.List;
import java.util.Map;

/**
 * 表的结构信息（包含表的名字，所有的字段信息，主键信息）
 */
public class TableInfo {

    /**
     *  表的名称
     */
    private String tableName;
    /**
     * 所有的字段信息
     */
    private Map<String,ColumnInfo> columns;

    /**
     * 唯一主键
     */
    private ColumnInfo onlyPrikey;

    /**
     *  联合主键

     */
    private List<ColumnInfo> prikeys;
    public TableInfo(String tableName, Map<String, ColumnInfo> columns, ColumnInfo onlyPrikey) {
        this.tableName = tableName;
        this.columns = columns;
        this.onlyPrikey = onlyPrikey;
    }
    public TableInfo(String tableName, List<ColumnInfo> prikeys,Map<String, ColumnInfo> columns){
        this.tableName = tableName;
        this.prikeys = prikeys;
        this.columns=columns;
    }
    public TableInfo(){

    }
    public String getTableName() {

        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnInfo> columns) {
        this.columns = columns;
    }

    public ColumnInfo getOnlyPrikey() {
        return onlyPrikey;
    }

    public void setOnlyPrikey(ColumnInfo onlyPrikey) {
        this.onlyPrikey = onlyPrikey;
    }

    public List<ColumnInfo> getPrikeys() {
        return prikeys;
    }

    public void setPrikeys(List<ColumnInfo> prikeys) {
        this.prikeys = prikeys;
    }


}
