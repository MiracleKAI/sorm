package com.miracle.sorm.core;

import com.miracle.sorm.bean.Configuration;
import com.miracle.sorm.pool.DBConnectPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * 根据配置信息，维持数据库的连接对象的管理
 */
public class DBManager {
    /**
     * 配置信息
     */
    private static Configuration configuration;
    /**
     * 连接池对象
     */
    private static DBConnectPool dbConnectPool;
    static{
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration = new Configuration();

        configuration.setDriver(properties.getProperty("driver"));
        configuration.setUsername(properties.getProperty("username"));
        configuration.setPassword(properties.getProperty("password"));
        configuration.setUrl(properties.getProperty("url"));
        configuration.setUsingDB(properties.getProperty("usingDB"));
        configuration.setSrcPath(properties.getProperty("srcPath"));
        configuration.setPoPackage(properties.getProperty("poPackage"));
        configuration.setQueryClass(properties.getProperty("queryClass"));
        configuration.setPoolMinSize(properties.getProperty("poolMinSize"));
        configuration.setPoolMaxSize(properties.getProperty("poolMaxSize"));



        System.out.println("加载DBManager成功");
    }

    /**
     * 获取配置
     * @return 获取配置对象
     */
    public static Configuration getConfiguration(){
        if(dbConnectPool == null){
            dbConnectPool = new DBConnectPool();
        }
        return configuration;
    }

    /**
     * 获取连接
     * @return connection对象
     */
    public static Connection getConnection(){
      return  dbConnectPool.getConnection();
    }
    /**
     * 创建connection对象
     * @return connection connection对象
     */
    public synchronized static Connection creatConnection(){
        Connection connection = null;
        try {
            Class.forName(configuration.getDriver());
            connection = DriverManager.getConnection(configuration.getUrl(),configuration.getUsername(),configuration.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭除connection外的各种连接
     * @param autos 需要关闭的流或连接
     */
    public static void close(AutoCloseable... autos){
        for(AutoCloseable auto : autos){
            try {
                if(auto!=null) {
                    auto.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭connection连接
     * @param connection 需要关闭的连接
     */
    public static void closeConnection(Connection connection){
        dbConnectPool.close(connection);
    }

}
