package dcein.springboot.demo.otherTool;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.st;

/**
 * @program: springboot
 * @description: 自定义数据源测试类
 * @author: DingCong
 * @create: 2018-09-28 10:06
 **/
public class TestDefineDataSource {

    //数据库连接池
    private static DefineDataSource  dataPool = new DefineDataSource();

    /**
     * @auther: DingCong
     * @param:
     * @description: <p>测试类</p>
     * @return:
     * @date: 2018/9/28 10:08
     */
    @Test
    public void jdbcTestUtil() throws SQLException {
        try {
            Connection conn = getConnection();
            if(conn != null){
                System.out.println(conn.getCatalog());
            }
            CloseConnection(conn, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从池中获取一个连接
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return dataPool.getConnection();
    }

    /**
     * 关闭连接
     * @param conn
     * @param st
     * @param rs
     * @throws SQLException
     */
    public static void CloseConnection(Connection conn, Statement st, ResultSet rs) throws SQLException{

        // 关闭存储查询结果的ResultSet对象
        if(rs != null){
            rs.close();
        }

        //关闭Statement对象
        if(st != null){
            st.close();
        }

        //关闭连接
        if(conn != null){
            conn.close();
        }
    }
}
