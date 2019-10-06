package com.miracle.sorm.core;

import java.util.List;

/**
 * 对外提供服务的一个类，客户若要使用该框架，则需从该类开始
 */
public interface Query {
    /**
     *执行DML语句，
     * @param sql SQL语句
     * @param params 替换占位符的参数
     * @return 执行的sql语句后，影响记录的行数
     */
    int executeDML(String sql,Object[] params);

    /**
     * 删除对象载数据库中对应的记录，对象所在的类对应到表，对象的主键值（如id）对应到记录
     * @param object 主键
     */
    void delete(Object object);

    /**
     * 删除clazz表示类对应表中   的记录，根据表结构生成的bean类的class对象
     * @param clazz 表对应表的Class类
     * @param id 记录的id
     */
    void delete(Class clazz, Object id);
    /**
     * 将一个对象存储到数据库中（如TableInfo）
     * @param object 想要插入的数据对象
     */
    void insert(Object object);

    /**
     * 更新指定对象的值，并且只更新指定字段的值
     * @param object 想要更新的数据对象
     * @param fieldNames 要更新的字段
     */
    int update(Object object, String[] fieldNames);

    /**
     * 将通过sql语句读出来的数据存入指定的class对应的javabean类中
     * 查询返回多行记录，并将每行记录封装到clazz指定的类对象中
     * @param sql 查询语句
     * @param clazz 封装数据的javabean类的Class对象
     * @param params 查询参数  select * from tables where id = ?
     * @return 查询返回的多行记录
     */
    List queryRows(String sql,Class clazz, Object[] params);

    /**
     * 查询返回一行记录，并将该记录封装到clazz指定的类对象中
     * @param sql 查询的语句
     * @param params  sql的参数
     * @param clazz    封装数据的javabean类的Class对象
     * @return 查询的结果
     */
    Object queryUniqueRows(String sql,Class clazz, Object[] params);

    /**
     * 查询返回一个值（一行一列），并将该值返回
     * @param sql 查询的语句
     * @param params  sql的参数
     * @return 查询的结果
     */
    Object queryValue(String sql,Object[] params);

    /**
     * 查询返回一个数字（一行一列），并将该值返回
     * @param sql 查询的语句
     * @param params  sql的参数
     * @return 查询的结果
     */
    Number queryNumber(String sql,Object[] params);

}
