package com.miracle.sorm.pool;

import com.miracle.sorm.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接池
 */
public class DBConnectPool {
    /**
     *
     *     连接池对象
     */
    private List<Connection> connectionPool = null;
    /**
     *  最大连接数
     */
    private static final int MAX_CONNECT_COUNT = Integer.parseInt(DBManager.getConfiguration().getPoolMaxSize());
    /**
     *   最小连接数
     */
    private static final int MIN_CONNECT_COUNT = Integer.parseInt(DBManager.getConfiguration().getPoolMinSize());

    private static int count = 0;
    public DBConnectPool(){
        initPool();
    }
    /**
     * 初始化连接池
     */
    public void initPool(){
        if(connectionPool == null){
            connectionPool = new ArrayList<>();
        }
        while(connectionPool.size() < MIN_CONNECT_COUNT){
            connectionPool.add(DBManager.creatConnection()) ;
            System.out.println("初始化连接池,目前连接池拥有连接个数: "+ ++count);
        }
    }

    /**
     * 从连接池中取连接
     * @return connection对象
     */
    public synchronized Connection getConnection(){
        Connection connection = connectionPool.get(connectionPool.size()-1);
        connectionPool.remove(connectionPool.size()-1);
        System.out.println("目前连接池剩余连接个数为: "+ --count);
        if(connectionPool.size()<MIN_CONNECT_COUNT){
            connectionPool.add(DBManager.creatConnection());
            System.out.println("目前连接池小于最少连接数，加1，剩余连接个数为: "+ connectionPool.size());
        }
        return connection;
    }

    /**
     * 关闭连接
     * @param connection 需要关闭的连接
     */
    public synchronized  void close(Connection connection){
        if(connectionPool.size()>=MAX_CONNECT_COUNT){
            try {
                if(null != connection){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            connectionPool.add(connection);
            System.out.println("目前连接池剩余连接个数为: "+ ++count);
        }

    }

}
