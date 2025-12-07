package com.example.cs.fourWeek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2TableCreate {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // 메모리 DB, 연결 종료 후에도 유지
        String user = "sa";
        String password = "";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement()) {

            // 테이블 생성 SQL
            String sql = "CREATE TABLE tx_test (id INT PRIMARY KEY, val VARCHAR(255))";
            stmt.executeUpdate(sql);
            System.out.println("테이블 'users'가 성공적으로 생성되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
