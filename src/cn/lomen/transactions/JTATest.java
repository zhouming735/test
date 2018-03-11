/**
 * Project: test Package Name:transactions
 * 
 * File Created at 2018年3月6日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd All Rights Reserved.
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd. Copying or
 * reproduction without prior written approval is prohibited.
 * 
 */
package cn.lomen.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * ClassName:JTATest <br/>
 * Date: 2018年3月6日 下午11:33:58 <br/>
 * 
 * @author <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version 注意：分布式事务，JTA需要用到 J2EE中的javaee.jar包，实现了XA协议的数据库连接jar包 Mysql只有innodb引擎支持XA分布式事务
 *          两阶段提交中的第二阶段, 协调者需要等待所有参与者发出yes请求, 或者一个参与者发出no请求后, 才能执行提交或者中断操作. 这会造成长时间同时锁住多个资源, 造成性能瓶颈,
 *          如果参与者有一个耗时长的操作, 性能损耗会更明显. 实现复杂, 不利于系统的扩展, 不推荐.
 */
public class JTATest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        XADataSource xaDs1 = getDataSource(
                "jdbc:mysql://127.0.0.1:3306/sampledb?useUnicode=true&characterEncoding=UTF-8",
                "root", "1234");
        XAConnection xaCon1 = null;
        XAResource xaRes1 = null;
        Connection conn1 = null;
        PreparedStatement stmt1 = null;

        XADataSource xaDs2 = getDataSource(
                "jdbc:mysql://127.0.0.1:3306/study?useUnicode=true&characterEncoding=UTF-8", "root",
                "1234");
        XAConnection xaCon2 = null;
        XAResource xaRes2 = null;
        Connection conn2 = null;
        PreparedStatement stmt2 = null;

        // UserTransaction userTx=null;

        int ret1 = 0;
        int ret2 = 0;

        Xid xid1 = new MyXid(100, new byte[] {0x01}, new byte[] {0x02});
        Xid xid2 = new MyXid(100, new byte[] {0x01}, new byte[] {0x03});

        try {
            // 获得 Transaction 管理对象
            // userTx = (UserTransaction)getContext().lookup("java:comp/UserTransaction");

            xaCon1 = JTATest.getXAConnetion(xaDs1);
            conn1 = JTATest.getConnection(xaCon1);
            String sql1 = "INSERT INTO t_user(user_name,password,credits) VALUES(?,?,?);";
            stmt1 = conn1.prepareStatement(sql1);
            stmt1.setString(1, "test1");
            stmt1.setString(2, "1234");
            stmt1.setInt(3, 11);
            // 获取库1的RM
            xaRes1 = xaCon1.getXAResource();

            xaCon2 = JTATest.getXAConnetion(xaDs2);
            conn2 = JTATest.getConnection(xaCon2);
            String sql2 = "INSERT INTO test(name,cdatestamp,id) VALUES(?,?,?);";
            stmt2 = conn2.prepareStatement(sql2);
            stmt2.setString(1, "test1");
            stmt2.setString(2, "2018-03-07");
            stmt2.setInt(3, 6);
            // 获取库2的RM
            xaRes2 = xaCon2.getXAResource();

            xaRes1.start(xid1, XAResource.TMNOFLAGS);
            stmt1.execute();
            xaRes1.end(xid1, XAResource.TMSUCCESS);

            // 判断下是否来自于同一个数据库
            if (xaRes2.isSameRM(xaRes1)) {
                xaRes2.start(xid1, XAResource.TMNOFLAGS);
                stmt2.execute();
                xaRes2.end(xid1, XAResource.TMSUCCESS);
            } else {
                xaRes2.start(xid2, XAResource.TMNOFLAGS);
                stmt2.execute();
                xaRes2.end(xid2, XAResource.TMSUCCESS);
                ret1 = xaRes2.prepare(xid2);
            }
            ret2 = xaRes1.prepare(xid1);
            // prepare都准备好后，才能进行commit，否则什么都不做
            if (ret1 == XAResource.XA_OK && ret2 == XAResource.XA_OK) {
                try {
                    xaRes1.commit(xid1, false);
                } catch (Exception e) {
                    xaRes1.rollback(xid1);
                }
                try {
                    if (xaRes2.isSameRM(xaRes1)) {
                        xaRes2.commit(xid1, false);
                    } else {
                        xaRes2.commit(xid2, false);
                    }
                } catch (Exception e) {
                    System.out.println("异常回滚11");
                    xaRes1.rollback(xid1);
                    if (xaRes2.isSameRM(xaRes1)) {
                        xaRes2.rollback(xid1);
                    } else {
                        xaRes2.rollback(xid2);
                    }
                    System.out.println("异常回滚22");
                    
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            //最后关闭数据库连接
            closeConnection(conn1); 
            closeConnection(conn2);
        }

    }

    private static XADataSource getDataSource(String url, String user, String password) {
        // TODO Auto-generated method stub
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    public static XAConnection getXAConnetion(XADataSource dataSource) {
        XAConnection XAConn = null;
        try {
            XAConn = dataSource.getXAConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return XAConn;
    }

    public static Connection getConnection(XAConnection XAConn) {
        Connection conn = null;
        try {
            conn = XAConn.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("连接关闭失败");
        }
    }
}
