package com.plf.learn.insert;

import cn.beecp.BeeDataSource;
import cn.beecp.BeeDataSourceConfig;
import com.google.common.base.Stopwatch;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * @author panlf
 * @date 2021/8/14
 */
public class InsertBatch {


    public static void main(String[] args) {
        try {
            //testInsert();
            testInsertRealBatch();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //615
    public static void testInsert() throws Exception{
        Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2b8");
        StringBuffer stringBuffer = new StringBuffer("insert into user_copy(sfzhm) values ");
        for(int i=1;i<=20000;i++){
            if(i==20000){
                stringBuffer.append("('33068119981298561"+i+"');");
            }else {
                stringBuffer.append("('33068119981298561" + i + "'),");
            }
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        Statement statement = connection.createStatement();
        statement.addBatch(stringBuffer.toString());
        statement.executeBatch();
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        if(connection!=null){
            connection.close();
        }

        if(statement != null){
            statement.close();
        }
    }

    //197
    public static void testInsertRealBatch() throws Exception{
        Connection connection = getConnection("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2b8&rewriteBatchedStatements=true");
        StringBuffer stringBuffer = new StringBuffer("insert into user_copy(sfzhm) values ");
        for(int i=1;i<=2000;i++){
            if(i==2000){
                stringBuffer.append("('33068119981298561"+i+"');");
            }else {
                stringBuffer.append("('33068119981298561" + i + "'),");
            }
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        Statement statement = connection.createStatement();
        statement.addBatch(stringBuffer.toString());
        statement.executeBatch();
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        if(connection!=null){
            connection.close();
        }

        if(statement != null){
            statement.close();
        }
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String url) throws SQLException {
        BeeDataSourceConfig config = new BeeDataSourceConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(url);
        config.setUsername("root");
        config.setPassword("root");
        config.setMaxActive(10);
        config.setInitialSize(0);
        config.setMaxWait(8000);//ms
        //DataSource ds=new BeeDataSource(config);
        BeeDataSource ds=new BeeDataSource(config);
        Connection conn=ds.getConnection();
        return conn;
    }
}
