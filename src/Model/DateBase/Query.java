package Model.DateBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Query {

    public ArrayList<String> User;
    public ArrayList<String> Vocab;
    public ArrayList<String> VocabEng;
    public ArrayList<String> VocabThai;

    public static boolean CheakUserPass(String user, String pass) {             /*ทำการตรวจสอบ username และ password*/
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT username,password "
                + "FROM user "
                + "WHERE "
                + "username " + "LIKE " + "\'" + user + "\'"
                + " and " + "password " + "LIKE" + "\'" + pass + "\'")) {
            return rs.next() != false;
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
            return false;
        }
    }

    public int GetPoint(String username) {                                   /*ทำการดึง Point จาก Database*/
        int point = 0;
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT point FROM user WHERE username LIKE " + "\'" + username + "\'")) {
            while (rs.next()) {
                point = rs.getInt(1);
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return point;
    }

    public String User(String username) {                                  
        String user = "";
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT first_name,last_name,status,point FROM user WHERE username LIKE " + "\'" + username + "\'")) {
            while (rs.next()) {
                user = "ชื่อ: " + rs.getString(1) + "\n"
                        + "นามสกุล: " + rs.getString(2) + "\n"
                        + "สถานะ: " + rs.getString(3) + "\n"
                        + "คะแนน: " + rs.getInt(4);
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return user;
    }

    public ArrayList UserList() {                         /*แสดงข้อมูลของนักเรียนทั้งหมด*/
        this.User = new ArrayList();
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT first_name,last_name,status,point FROM user WHERE status LIKE 'Student' ")) {
            while (rs.next()) {
                this.User.add("ชื่อ: " + rs.getString(1) + "\n"
                        + "นามสกุล: " + rs.getString(2) + "\n"
                        + "สถานะ: " + rs.getString(3) + "\n"
                        + "คะแนน: " + rs.getInt(4));
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return this.User;
    }

    public void Vocabulary(String type) {     /*ทำการค้าหาคำศัพท์ตามชนิด*/
        String temp;
        if (!type.equals("all")) {
            temp = "select * from vocabulary where type like " + "'" + type + "'";
        } else {
            temp = "select * from vocabulary";
        }
        try (ResultSet rs = Connect.conn.createStatement().executeQuery(temp)) {
            this.Vocab = new ArrayList();
            this.VocabEng = new ArrayList();
            this.VocabThai = new ArrayList();
            while (rs.next()) {
                Vocab.add(rs.getString(1) + " " + rs.getString(2));
                VocabEng.add(rs.getString(1));
                VocabThai.add(rs.getString(2));
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
    }

    public ArrayList VocabularyType() {     /*ทำการค้าหาชนิดคำศัพท์*/
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT DISTINCT type FROM vocabulary")) {
            this.Vocab = new ArrayList();
            while (rs.next()) {
                Vocab.add(rs.getString(1));
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return Vocab;
    }

    public String FindWordTH(String str) {  /*คนหาคำศีพท์ภาษาอังกฤษ*/
        String temp = "empty";
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT eng FROM vocabulary WHERE vocabulary.thai LIKE" + " " + "\'" + str + "\'")) {
            while (rs.next()) {
                temp = rs.getString(1);
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return temp;
    }

    public String FindWordENG(String str) {   /*คนหาคำศีพท์ภาษาไทย*/
        String temp = "empty";
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT thai FROM vocabulary WHERE vocabulary.eng LIKE" + " " + "\'" + str + "\'")) {
            while (rs.next()) {
                temp = rs.getString(1);
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return temp;
    }

    public String CheckStatus(String username) {     /*ตรวจสอบสนานะ*/
        String temp = "";
        try (ResultSet rs = Connect.conn.createStatement().executeQuery("SELECT status FROM user WHERE username LIKE" + " " + "\'" + username + "\'")) {
            while (rs.next()) {
                temp = rs.getString(1);
            }
        } catch (SQLException e) {
            Connect.Error = e.getMessage();
        }
        return temp;
    }
}
