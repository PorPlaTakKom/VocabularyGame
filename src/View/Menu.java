package View;

import Controller.DatabaseManeger;
import static Controller.DatabaseManeger.CheckLogin;
import static Controller.DatabaseManeger.ConnectToDataBase;
import static Controller.DatabaseManeger.DisconnectDatabase;
import static Controller.DatabaseManeger.ShowUser;
import static Controller.VocabGameManager.Game;
import static Controller.VocabGameManager.GameChallehge;
import static Controller.VocabGameManager.MenuAddVocab;
import static Controller.VocabGameManager.MenuVocab;
import Model.DateBase.Connect;
import Model.DateBase.Query;
import Model.Member.Status;
import java.util.InputMismatchException;

import java.util.Scanner;

public class Menu {

    static Scanner sc = new Scanner(System.in);
    static DatabaseManeger DM = new DatabaseManeger();
    static Query Q = new Query();

    public static void main(String[] args) {
        if (ConnectToDataBase() != false) {
            try {
                Menu();
            } catch (InputMismatchException e) {
                System.out.println("\n");
                System.out.println("ใส่ข้อมูลไม่ถูกต้องกรุณาลองใหม่อีกครั้ง");
            }
        }
    }

    private static void Menu() {
        int menuId;
        do {
            System.out.println("<<<<-|VOCABULARY GAME|->>>>");                  /*แสดงเมนูให้เลือกของโปรแกรมละทำการพาไปยังการทำการต่อไปที่เราได้เลือก*/
            System.out.println("1. Login (เข้าสู่ระบบ)");
            System.out.println("2. Register (ลงทะเบียน)");
            System.out.println("3. Exit (ออกจะระบบ)");
            System.out.print("Enter your menu (เลือกหมายเลขที่ต้องการ) [1-3]: ");
            menuId = sc.nextInt();
            System.out.println("");

            switch (menuId) {
                case 1:
                    if (LoginMenu() == true) {
                        if ("Student".equals(DM.status)) {
                            StudentMenu();
                        } else if ("Teacher".equals(DM.status)) {
                            TeacherMenu();
                        }
                    }
                    break;
                case 2:
                    RegisterMenu();
                    break;
                case 3:
                    DisconnectDatabase();
                    break;
            }
        } while (menuId != 3);
    }

    private static boolean LoginMenu() {                                        /*แสดงเมนูของการเข้าสู่ระบบและส่งขอมูลไปให้ Class CheckLogin ตรวจสอบ*/
        System.out.println("<<<<-|Login|->>>>");
        System.out.print("Username: ");
        String user = sc.next();
        System.out.print("Password: ");
        String pass = sc.next();
        System.out.println("");

        if (CheckLogin(user, pass) == true) {
            return true;
        } else {
            return false;
        }
    }

    private static void RegisterMenu() {                                        /*จะทำการเก็บข้อมูลผู้ใช้และทำการส่งไปยัง Class Register เพื่อบันทึกเข้าฐานข้อมูล*/
        int num = 0;
        String fname, lname, username, password;
        System.out.println("1. RegisterForStudent");
        System.out.println("2. RegisterForTeacher");
        System.out.print("Enter your menu [1-2]: ");
        num = sc.nextInt();
        System.out.print("Enter your FirstName: ");
        fname = sc.next();
        System.out.print("Enter your LasrName: ");
        lname = sc.next();
        System.out.print("Enter your Username: ");
        username = sc.next();
        System.out.print("Enter your Password: ");
        password = sc.next();
        System.out.println("");

        switch (num) {
            case 1:
                DM.Register(fname, lname, username, password, Status.Student);
                break;
            case 2:
                DM.Register(fname, lname, username, password, Status.Teacher);
                break;
        }
    }

    private static void StudentMenu() {                                         /*แสดงเมนูของผู้ใช้ที่เป็นนักเรียน*/
        int menuId;
        do {
            System.out.println("<<<<-|VOCABULARY GAME|->>>>");                   
            System.out.println("1. VOCABULARY (ดูคำศัพท์)");
            System.out.println("2. VOCABULARY GAME (เกมส์คำศัพท์)");
            System.out.println("3. VOCABULARY CHALLENGE GAME (เกมส์คำศัพท์)");
            System.out.println("4. USER STATUS สถานะผู้ใช้)");
            System.out.println("0. Exit (ออกจากระบบ)");
            System.out.print("Enter your menu (เลือกหมายเลขที่ต้องการ)[1-4]: ");
            menuId = sc.nextInt();
            System.out.println("");

            switch (menuId) {
                case 1:
                    MenuVocab();
                    break;
                case 2:
                    Game();
                    break;
                case 3:
                    GameChallehge();
                    break;
                case 4:
                    UserStatus();
                    break;
            }
        } while (menuId != 0);
    }

    private static void TeacherMenu() {                                         /*แสดงเมนูของผู้ใช้ที่เป็นคุณครู*/
        int menuId;
        do {
            
            System.out.println("<<<<-|VOCABULARY GAME|->>>>");
            System.out.println("1. ADD VOCABULARY (เพิ่มคำศัพท์)");
            System.out.println("2. STUDENT LIST (รายชื่อนักเรียน)");
            System.out.println("3. EDIT VOCABULARY (แก้ไขคำศัพท์)");
            System.out.println("4. USER STATUS (สถานะผู้ใช้)");
            System.out.println("0. Exit (ออกจากระบบ)");
            System.out.print("Enter your menu (เลือกหมายเลขที่ต้องการ)[1-4]: ");
            menuId = sc.nextInt();
            System.out.println("");

            switch (menuId) {
                case 1:
                    MenuAddVocab();
                    break;
                case 2:
                    ShowUser();
                    break;
                case 3:
                    System.out.print("ใส่คำศัพท์ภาษาอังกฤษที่จะแก้: ");
                    String word = sc.next();
                    DM.EditVocab(word);
                    break;
                case 4:
                    UserStatus();
                    break;
            }
        } while (menuId != 0);
    }

    private static void UserStatus() {                                          /*แสดงข้อมูลต่างๆของผู้ใช้*/
        int menuId;
        System.out.println("<<<<-|User Status|->>>>");
        System.out.println("\n");
        if (DM.ShowUserStutas()) {
            System.out.println("");
            System.out.println("1.แก้ไข้ ชื่อ-นามสกุล");
            System.out.println("0.กลับไปหน้าที่แล้ว");
            System.out.print("ใส่ตัวเลขที่ต้องการ: ");
            menuId = sc.nextInt();

            if (menuId == 1) {
                System.out.print("ใส่ชื่อใหม่ของคุณ: ");
                String f_name = sc.next();
                System.out.print("ใส่นามสกุลใหม่ของคุณ: ");
                String l_name = sc.next();
                if (DM.ChangeUsername(f_name, l_name)) {
                    System.out.println("ทำการเปลี่ยนชื่อ-นามสกุลเรียบร้อยแล้ว");
                    System.out.println("");
                } else {
                    System.out.println("ไม่สามารภเปลี่ยนชื่อ-นามสกุลได้" + "|" + Connect.Error);
                    System.out.println("");
                }
            }
        } else {
            System.out.println("มีบ้างอย่างผิดพลาด" + "|" + Connect.Error);
            System.out.println("");
        }
    }
}
