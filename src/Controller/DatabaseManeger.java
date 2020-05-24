package Controller;

import static Controller.VocabGameManager.sc;
import Model.DateBase.Connect;
import Model.DateBase.Insert;
import static Model.DateBase.Insert.UpdatePoint;
import static Model.DateBase.Insert.UpdateUsername;
import static Model.DateBase.Insert.UpdateVocab;
import Model.DateBase.Query;
import Model.Member.Person;
import Model.Member.Status;
import Model.Member.Student;
import Model.Member.Teacher;
import java.util.Base64;

public class DatabaseManeger {

    private Student student;
    private Teacher teacher;
    private static String username;
    public static String status;
    static Query Q = new Query();

    public static boolean ConnectToDataBase() {                     /*ใช้เรียก Method ConnectDB() เพื่อทำการเชื่อมต่อกับฐานข้อมูล*/
        if (Connect.ConnectDB() != null) {
            return true;
        } else {
            System.out.println("ไม่สามารถเชื่อมต่อกับฐานข้อมูลได้ : \n"
                    + Connect.getError());
        }
        return false;
    }

    public static void DisconnectDatabase() {                       /*ใช้ยกเลิกการเชื่อมต่อกับฐานข้อมูล*/
        if (Connect.DisconnectDB()) {
            System.out.println("ยกเลิกการเชื่อมต่อฐานข้อมูล");
        } else {
            System.out.println(Connect.getError());
        }
    }

    public static boolean CheckLogin(String user, String password) {        /*ใช้ตรวจสอบ Username และ Password*/
        String pass = Encode(password);
        if (Query.CheakUserPass(user, pass) == true) {
            username = user;
            status = Q.CheckStatus(username);
            System.out.println("เข้าสู่ระบบเรียบร้อย");
            System.out.println();
            return true;
        } else if (Query.CheakUserPass(user, pass) == false) {
            System.out.println("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง!!");
        } else {
            System.out.println(Connect.getError());
            return false;
        }
        return false;
    }

    public boolean Register(String firstname, String lastname, String username, String password, Status status) {
        String pass = Encode(password);
        Person person = new Person(firstname, lastname);                                   /*ใช้ส่งข้อมูลไปยัง Method InsertUser*/
        if (Query.CheakUserPass(username, "%") == true) {
            System.out.println("ชื่อผู้ใช้นี้ถูกใช้ไปแล้ว");
            return false;
        } else {
            if (status.equals(Status.Student)) {
                this.student = new Student(username, pass, person);
                if (Insert.InsertUser(student) == true) {
                    System.out.println("ลงทะเบียนสำเร็จ");
                    return true;
                } else {
                    System.out.println("ไม่สามารถลงทะเบียนได้");
                    return false;
                }
            } else if (status.equals(Status.Teacher)) {
                this.teacher = new Teacher(username, pass, person);
                if (Insert.InsertUser(teacher) == true) {
                    System.out.println("ลงทะเบียนสำเร็จ");
                    return true;
                } else {
                    System.out.println("ไม่สามารถลงทะเบียนได้");
                    return false;
                }
            }
        }
        System.out.println(Connect.getError());
        return false;
    }

    public static void ShowUser() {                                 /*ใช้แสดงข้อมูลของนักเรียน*/
        Q.UserList();
        System.out.println("หน้าที่ 1");
        System.out.println("========================");
        for (int i = 0; i < Q.User.size(); i++) {
            if (Q.User.get(i) != null) {
                System.out.println(Q.User.get(i));
                System.out.println("");
            } else {
                System.out.println("========================");
                System.out.println("");
                break;
            }
            if ((i + 1) % 5 == 0) {
                System.out.println("========================");
                System.out.print("กด Enter เพื่อไปหน้าต่อไปหรือพิมพ์ exit เพื่อออก");
                String pressKey = sc.nextLine();
                if (pressKey.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("\n");
                System.out.println("หน้าที่ " + ((i + 1) / 5 + 1));
                System.out.println("========================");
            }
        }
        System.out.println("========================");
        System.out.println("");
    }

    public static void EditVocab(String word) {                                  /*ใช้รับข้อมูลและทำการส่งขอมูลไปยัง Method UpdateVocab*/
        System.out.print("ใส่คำศัพท์ภาษาอังกฤษ: ");
        String eng = sc.next();
        System.out.print("ใส่คำศัพท์ภาษาไทย: ");
        String thai = sc.next();
        System.out.print("ใส่ชนิดของคำศัพท์ (ภาษาอังกฤษ): ");
        String type = sc.next();

        if (UpdateVocab(word, eng, thai, type)) {
            System.out.println("แก้ไขเรียบร้อยแล้ว");
        } else {
            System.out.println("ไม่สามรถแก้ไขได้");
        }
    }

    public boolean ChangeUsername(String f_name, String l_name) {               /*รับข้อมูล fname lname เพื่อส่งให้ Method UpdateUsername*/
        if (UpdateUsername(f_name, l_name, username)) {
            return true;
        }
        return false;
    }

    public boolean ShowUserStutas() {                                           /*ใช้แสดงสถานะผู้ใช้*/
        System.out.println(Q.User(username));
        return true;
    }

    public boolean AddPoint(int point) {                                        /*รับข้อมูลมาและส่งไปยัง Method AddPoint*/
        UpdatePoint(username, point + Q.GetPoint(username));
        return true;
    }

    public static String Encode(String str) {                                           /*ทำการรับรหัสมาและแปลงให้เป็น Base64*/
        String encodedString = Base64.getEncoder().encodeToString(str.getBytes());
        return encodedString;
    }
}
