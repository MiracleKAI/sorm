package com.miracle.sorm.bean;

/**
 * 配置信息
 */
public class Configuration {
    /**
     * 数据库驱动
     */
    private String driver;
    /**
     * 连接数据库的url
     */
    private String url;
    /**
     * 使用数据库的用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 使用的数据库
     */
    private String usingDB;
    /**
     * 项目的源码路径
     */
    private String srcPath;

    /**
     * 放置生成的javabean类的包（po persistence object 持久化对象）
     */
    private String poPackage;

    /**
     * 打算用的query类，如MysqlQuery
     */
    private String queryClass;
    /**
     * 连接池中最小的连接数
     */
    private String poolMinSize;


    /**
     *连接池中最大的连接数
     */
    private String poolMaxSize;
    public Configuration(){

    }
    public Configuration(String driver, String url, String username, String password, String usingDB, String srcPath, String poPackage,String queryClass){
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
        this.queryClass = queryClass;
    }

    public String getDriver() {

        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }
    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }
    public String getPoolMinSize() {
        return poolMinSize;
    }

    public void setPoolMinSize(String poolMinSize) {
        this.poolMinSize = poolMinSize;
    }

    public String getPoolMaxSize() {
        return poolMaxSize;
    }

    public void setPoolMaxSize(String poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }

}
