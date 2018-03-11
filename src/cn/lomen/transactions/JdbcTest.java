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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName:JdbcTest <br/>
 * 本地事务
 * JDBC 事务的一个缺点是事务的范围局限于一个数据库连接。一个 JDBC 事务不能跨越多个数据库
 * @version
 */
public class JdbcTest {
    public static final String url = "jdbc:mysql://127.0.0.1:3306/sampledb?"
            + "user=root&password=1234&useUnicode=true&characterEncoding=UTF-8";
    public static final String driverClass = "com.mysql.jdbc.Driver";


    static Connection conn = null;
    static PreparedStatement stmt = null;

    public static void main(String[] args) {

        try {
            // 1、加载数据库驱动
            Class.forName(driverClass);
            // 2、获取数据库连接
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false); // 关闭自动提交事务
            String sql = "INSERT INTO t_user(user_name,password,credits) VALUES(?,?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "test1");
            stmt.setString(2, "1234");
            stmt.setInt(3, 11);
            stmt.executeUpdate();

            sql = "INSERT INTO user(id,name) VALUES(?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 100);
            stmt.setString(2, "test1");
            stmt.executeUpdate();
            conn.commit();
            System.out.println("提交数据库");

        } catch (Exception e) {
            System.out.println("异常："+e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {
            System.out.println("操作数据库结束");
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
