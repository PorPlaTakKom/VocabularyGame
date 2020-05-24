 package Model.DateBase;

import static Model.DateBase.Connect.conn;
import Model.Member.Account;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Insert {

    public static boolean InsertUser(Account user) {                            /*ทำการเพิ่มข้อมูลผู้ใช้เข้า Database*/
        String username = user.getUsername();
        String pass = user.getPassword();
        String fname = user.getFirstname();
        String lname = user.getLastname();
        String status = user.getStatus().toString();
        LocalDateTime regis_date = LocalDateTime.now();

        DateTimeFormatter FormatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (PreparedStatement pstmt = Connect.conn.prepareStatement("INSERT INTO user (username, password, first_name, last_name, point, status, regis_datetime) VALUES (?,?,?,?,?,?,?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, pass);
            pstmt.setString(3, fname.toString());
            pstmt.setString(4, lname);
            pstmt.setInt(5, 0);
            pstmt.setString(6, status);
            pstmt.setString(7, regis_date.format(FormatTime));
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
            return false;
        }
    }

    public static boolean UpdatePoint(String user, int point) {                 /*ทำการ Update คะแนนของ นักเรียน*/
        String sql = "UPDATE user SET point = ? WHERE username LIKE ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, point);
            stm.setString(2, user);
            stm.executeUpdate();
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return false;
    }

    public static boolean UpdateUsername(String f_name, String l_name, String username) {   /*ทำการ Update ชื่อ นามสกุล*/
        String sql1 = "UPDATE user SET first_name = ? WHERE username LIKE ?";
        String sql2 = "UPDATE user SET last_name = ? WHERE username LIKE ?";
        try (PreparedStatement stm = conn.prepareStatement(sql1);
                PreparedStatement stm2 = conn.prepareStatement(sql2)) {

            stm.setString(1, f_name);
            stm.setString(2, username);

            stm2.setString(1, l_name);
            stm2.setString(2, username);

            stm.executeUpdate();
            stm2.executeUpdate();

            return true;
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return false;
    }

    public static boolean InsertVocab(String eng, String thai, String type) {                /*ทำการเพิ่มคำศัพท์เข้าไป Database*/
        try (PreparedStatement pstmt = Connect.conn.prepareStatement("INSERT INTO vocabulary (eng, thai, type) VALUES (?,?,?)")) {
            pstmt.setString(1, eng);
            pstmt.setString(2, thai);
            pstmt.setString(3, type);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
            return false;
        }
    }

    public static boolean UpdateVocab(String old,String eng, String thai, String type) {      /*ทำการ Updata คำศัพท์ใน Database*/
        String sql1 = "UPDATE vocabulary SET eng = ?, thai = ?, type = ? WHERE eng LIKE ?";
        try (PreparedStatement stm = conn.prepareStatement(sql1)) {

            stm.setString(1, eng);
            stm.setString(2, thai);
            stm.setString(3, type);
            stm.setString(4, old);
            stm.executeUpdate();

            return true;
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return false;
    }
}
