package javatutorial;

import java.sql.*;

public class CConnection {
    public Connection cn;

    private String url = "jdbc:mysql://localhost:3306/karyawan_db"; // kalo ngikutin tutorial ga usah di ubah
    private String user = "karyawan_user";    // Sesuaikan sama username MySQL Anda
    private String password = "password123"; // Sesuaikan sama password MySQL Anda

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

   public void openConnection() {
    try {
        Class.forName(JDBC_DRIVER);
        cn = DriverManager.getConnection(url, user, password);
        System.out.println("✅ Database Connected!");
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    public void closeConnection() {
        try {
            if (cn != null && !cn.isClosed()) cn.close();
        } catch (Exception ex) {}
    }
}