package com.miracle.sorm.core;

import com.miracle.sorm.Util.JDBCUtils;
import com.miracle.sorm.Util.ReflectUtils;
import com.miracle.sorm.bean.ColumnInfo;
import com.miracle.sorm.bean.TableInfo;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlQuery implements Query{


    @Override
    public int executeDML(String sql, Object[] params) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int count = 0;
        try {
            connection = DBManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            JDBCUtils.processParams(params,preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(preparedStatement);
            DBManager.closeConnection(connection);
        }

        return count;
    }

    @Override //object 主键对象
    public void delete(Object object) {
        Class c = object.getClass();

        TableInfo tableInfo = TableContext.poClassTablesMap.get(c);
        //获得唯一主建
        ColumnInfo onlyPriKey = tableInfo.getOnlyPrikey();

        Object onlyPriKeyVaule = ReflectUtils.invokeGeter(onlyPriKey.getColumnName(),object);
        delete(c,onlyPriKeyVaule);

    }

    @Override
    public void delete(Class clazz, Object id) {
        //获得class 对象对应的表信息
        TableInfo tableInfo = TableContext.poClassTablesMap.get(clazz);
        //获得唯一主建
        ColumnInfo onlyPriKey = tableInfo.getOnlyPrikey();

        String sql = "delete from "+tableInfo.getTableName()+" where "+ onlyPriKey.getColumnName() +" = "+id;
        executeDML(sql,new Object[]{id});
    }

    @Override//insert into 表名 (不为空的字段) value (?,?)
    public void insert(Object object) {
        Class c = object.getClass();

        TableInfo tableInfo =TableContext.poClassTablesMap.get(c);

        StringBuilder result = new StringBuilder();
        result.append("insert into " + tableInfo.getTableName()+" (");
        Field[] fields = c.getDeclaredFields();
        List<Object> params = new ArrayList<>();
        int notNUllFieldCount = 0;
        for(Field field : fields){
            String fieldName = field.getName();
            Object fieldValue = ReflectUtils.invokeGeter(fieldName,object);

            if(null != fieldValue){
                notNUllFieldCount++;
                result.append(fieldName+",");
                params.add(fieldValue);
            }
        }
        result.replace(result.length()-1,result.length(),") value (");
       // System.out.println(result.toString());
        for(int i = 0; i < notNUllFieldCount; i++){
            result.append("?,");
        }
        result.replace(result.length()-1,result.length(),")");
        System.out.println(result.toString());
        executeDML(result.toString(),params.toArray());
    }

    @Override//update 表名 set 字段 = 修改值 where id = ?
    public int update(Object object, String[] fieldNames) {
        Class c = object.getClass();
        TableInfo tableInfo = TableContext.poClassTablesMap.get(c);
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("update "+tableInfo.getTableName()+" set ");

        for(String fieldName : fieldNames){
            Object fieldValue = ReflectUtils.invokeGeter(fieldName,object);
            sql.append(fieldName +"=?,");
            params.add(fieldValue);
        }
        sql.setCharAt(sql.length()-1,' ');
        ColumnInfo onlyPrikey = tableInfo.getOnlyPrikey();
        String onlyPrikeyName = onlyPrikey.getColumnName();
        sql.append("where " +onlyPrikeyName + " = ?");
        System.out.println(sql.toString());
        params.add(ReflectUtils.invokeGeter(onlyPrikeyName,object));
        return executeDML(sql.toString(),params.toArray());
    }

    @Override//select username from user where id>?
    public List<Object> queryRows(String sql, Class clazz, Object[] params) {
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List list = null;
        try {

            preparedStatement = connection.prepareStatement(sql);
            JDBCUtils.processParams(params,preparedStatement);
            resultSet= preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            if(resultSetMetaData.getColumnCount()>0){
                list = new ArrayList<>();
            }else {
                return null;
            }
            //多行
            while(resultSet.next()){
                // 调用bean的无参构造器
                Object obj = clazz.newInstance();
                //多列
                for(int i = 0; i < resultSetMetaData.getColumnCount(); i++){
                    //获取列的标签（别名或列名）
                    String columnsName = resultSetMetaData.getColumnLabel(i+1);
                    //获取查到的值
                    Object columnsValue = resultSet.getObject(i+1);
                    //调用对应的bean的对应set方法
                    ReflectUtils.invokeSeter(columnsName,columnsValue,obj);
                }

                list.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(preparedStatement,resultSet);
            DBManager.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Object queryUniqueRows(String sql, Class clazz, Object[] params) {
        List list = queryRows(sql,clazz,params);
        return (list == null || list.size() <= 0) ? "查询为空" : list.get(0);
    }

    @Override
    public List<Object> queryValue(String sql, Object[] params) {
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Object> columnsValue = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            JDBCUtils.processParams(params,preparedStatement);
            System.out.println(sql);
            resultSet = preparedStatement.executeQuery();
         //   ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while(resultSet.next()){
                //获取查到的值
               columnsValue.add(resultSet.getObject(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(preparedStatement,resultSet);
            DBManager.closeConnection(connection);
        }

        return columnsValue;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        Object o = queryValue(sql,params);
        return (o instanceof Number) ? (Number)o : null;
    }
}
