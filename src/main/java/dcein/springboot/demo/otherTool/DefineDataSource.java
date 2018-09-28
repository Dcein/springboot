package dcein.springboot.demo.otherTool;

import dcein.springboot.demo.constants.SystemConstants;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @program: springboot
 * @description: 自定义数据连接池, 实现数据源接口
 * @author: DingCong
 * @create: 2018-09-28 09:14
 **/
@Slf4j
public class DefineDataSource implements DataSource {

    /**
     * 数据连接池对象:用于存放当前连接信息
     */
    private static LinkedList<Connection> dataSourceConnectionPool = new LinkedList<Connection>();

    /**
     * 连接配置信息文件名
     */
    private static final String PROPERTIES = "db.properties";

    /**
     * 初始化连接池大小
     */
    private static final String INIT_SIZE = "InitSize";


    /**
     * 初始化静态代码块,保证类加载的首要顺序
     */
    static {

        //step1.获取数据库连接信息(从配置文件获取)
        InputStream connectionMes = DefineDataSource.class.getClassLoader().getResourceAsStream(PROPERTIES);
        Properties properties = new Properties();

        //step2.加载配置文件
        try {
            properties.load(connectionMes);
            String driver = properties.getProperty(SystemConstants.DATA_SOURCE_DRIVER);
            String url = properties.getProperty(SystemConstants.DATA_SOURCE_URL);
            String user = properties.getProperty(SystemConstants.DATA_SOURCE_USERNAME);
            String password = properties.getProperty(SystemConstants.DATA_SOURCE_PASSWORD);

            //数据库连接池的初始化连接数的大小
            int initSize = Integer.parseInt(properties.getProperty(INIT_SIZE));

            //加载驱动
            Class.forName(driver);
            for (int i = 0; i < initSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                log.info("Initialize dataSource connection pool , create connector number is " + (i + 1) + ",  add this to connection pool");
                dataSourceConnectionPool.add(connection);
            }
        } catch (Exception e) {
            log.error("getConnection Exception" , e);
        }
    }


    /**
     * @auther: DingCong
     * @param: void
     * @description: <p>重写接口,获取连接器</p>
     * @return: Connection
     * @date: 2018/9/28 9:18
     */
    @Override
    public Connection getConnection() throws SQLException {

        //step1.从集合中获取连接
        if(dataSourceConnectionPool.size() > 0){
            //从集合中获取一个连接
            final Connection connection = dataSourceConnectionPool.removeFirst();
            //返回Connection的代理对象
            return (Connection) Proxy.newProxyInstance(DefineDataSource.class.getClassLoader(), connection.getClass().getInterfaces(), new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    if(!"close".equals(method.getName())){
                        return method.invoke(connection, args);
                    }else{
                        dataSourceConnectionPool.add(connection);
                        System.out.println("关闭连接，实际还给了连接池");
                        System.out.println("池中连接数为 " + dataSourceConnectionPool.size());
                        return null;
                    }
                }
            });
        }else{
            throw new RuntimeException("数据库繁忙，稍后再试");
        }
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
