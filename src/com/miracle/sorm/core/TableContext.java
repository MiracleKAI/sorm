package com.miracle.sorm.core;

import com.miracle.sorm.Util.JavaFileUtils;
import com.miracle.sorm.Util.StringUtils;
import com.miracle.sorm.bean.ColumnInfo;
import com.miracle.sorm.bean.TableInfo;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责获取管理数据库所有表结构和类结构信息，并通过表结构生成类结构
 */
public class TableContext {
    /**
     * key为表名，value 为表信息对象，将表名与表信息对应起来
     * */
    public static Map<String, TableInfo> tables = new HashMap<>();
    /**
     * 将po包中的bean类与对应的表对应起来
     */
    public static Map<Class,TableInfo> poClassTablesMap = new HashMap<>();

    private TableContext(){}


    static{

        Connection conn = null;
        ResultSet tableSet = null;
        ResultSet primaryKeysSet = null;
        try {
            // 获取连接对象
            conn = DBManager.getConnection();
            // 获取数据库源信息
            DatabaseMetaData data = conn.getMetaData();
            // 获取所有表名的结果集
            tableSet = data.getTables("fyoung", "%", "%", new String[] { "TABLE" });
            while (tableSet.next()) {
                String tableName = (String) tableSet.getObject("TABLE_NAME");
                TableInfo tableInfo = new TableInfo(tableName,new ArrayList<>(),new HashMap<>());
                // 把创建的表放到一个map集合中去
                tables.put(tableName, tableInfo);
                // 根据表名查询所有数据库表中的所有字段的结果集
                ResultSet columnsSet = data.getColumns("fyoung", "%", tableName, "%");
                // 遍历获取所有字段信息封装到相应表中
                while (columnsSet.next()) {
                    // 构造一个字段，数据库中的字段应该包括字段名，字段类型，以及是否是主键、外键等因素
                    ColumnInfo columnInfo = new ColumnInfo(columnsSet.getString("COLUMN_NAME"),
                            columnsSet.getString("TYPE_NAME"), 0);
                    tableInfo.getColumns().put(columnsSet.getString("COLUMN_NAME"), columnInfo);
                }

                // 对应表的所有主键集合
                primaryKeysSet = data.getPrimaryKeys("fyoung", "%", tableName);
                while (primaryKeysSet.next()) {
                    // 从所有字段中取主键，添加到所有主键列
                    ColumnInfo primaryKey = tableInfo.getColumns().get(primaryKeysSet.getObject("COLUMN_NAME"));
                    // 我们规定类型主键的标志类型
                    primaryKey.setKeyType(1);
                    // 把主键放到表信息的集合中去
                    tableInfo.getPrikeys().add(primaryKey);
                }
                // 若存在主键，则获取第一个作为主键，不考虑联合主键的情况
                if (tableInfo.getPrikeys().size() > 0) {
                    // 获取第一个主键
                    tableInfo.setOnlyPrikey((tableInfo.getPrikeys().get(0)));
                }
                System.out.println("成功填充tables,将表"+tableName+"与表信息对应");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(tableSet,primaryKeysSet);
            DBManager.closeConnection(conn);
        }
        //根据表结构信息生成对应的javabean
        updateJavaPoFile();

        //加载PO包下的类
        loadPOTables();

    }

    /**
     * 根据表结构信息生成对应的javabean，实现了表到类的转化
     */
    public static void updateJavaPoFile(){
        Map<String, TableInfo> map = TableContext.tables;
        for(TableInfo tableInfo : map.values()){
            JavaFileUtils.createJavaClassFile(tableInfo,new TypeConvertor());
            System.out.println("成功生成"+tableInfo.getTableName()+"类");
        }
    }

    /**
     * 加载PO包下的类
     */
    public static void loadPOTables(){
        Map<Class,TableInfo> map = TableContext.poClassTablesMap;
        Map<String, TableInfo> tables = TableContext.tables;
        for(TableInfo tableInfo : tables.values()){
            try {
                Class c = Class.forName(DBManager.getConfiguration().getPoPackage()+"."+ StringUtils.firstChar2UpperCase(tableInfo.getTableName()));
                map.put(c,tableInfo);
            } catch (ClassNotFoundException e) {
              continue;
            }
            System.out.println("成功加载"+tableInfo.getTableName()+"类到poClassTablesMap");
        }

    }


}
