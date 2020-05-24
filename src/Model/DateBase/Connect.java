package Model.DateBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static final String url = "jdbc:mysql://192.168.200.135:3306/";
    private static final String db_name = "INT103"+"?useUnicode=yes&characterEncoding=UTF-8";
    private static final String db_user = "project";
    private static final String db_pass = "Project103";

    public static Connection conn = null;
    public static String Error;

    public static Connection ConnectDB() {
        try {
            conn = DriverManager.getConnection(url + db_name, db_user, db_pass);  /*ทำการเชื่อมต่อฐานข้อมูล*/
            return conn;
        } catch (SQLException e) {
            Error = e.getMessage();
        }
        return null;
    }

    public static boolean DisconnectDB() {                                       /*ทำการยกเลิกเชื่อมต่อฐานข้อมูล*/
        try {
            if (conn != null) {
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            Error = e.getMessage();
            return false;
        }
    }
    
    public static String getError() {                                           /*ทำการส่งต่า Error ออกไป*/
        return Error;
    }
}
