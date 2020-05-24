package Controller;

import static Model.DateBase.Insert.InsertVocab;  
import Model.DateBase.Query;
import Model.Game.VocabularyGame;
import java.util.ArrayList;
import java.util.Scanner;
 
public class VocabGameManager {

    static Query Q = new Query();
    static Scanner sc = new Scanner(System.in);
    static VocabularyGame VG = new VocabularyGame();
    static DatabaseManeger DM = new DatabaseManeger();

    public static void MenuVocab() {                                /*ทำการเรียกใช้ VocabularyType เพื่อดึงชนิดคำศัพท์มาแสดง*/
        int num = 0;
        Q.VocabularyType();
        System.out.println("SELECT TYPE VOCABULARY");
        for (int i = 0; i < Q.Vocab.size(); i++) {
            System.out.println(i + 1 + "." + Q.Vocab.get(i));
        }
        System.out.println("0.Exit (กลับไปหน้าที่แล้ว)");
        System.out.print("Enter your menu (เลือกหมายเลขที่ต้องการ): ");
        num = sc.nextInt();
        sc.nextLine();
        System.out.println("\n");
        
        ShowVocab(Q.Vocab.get(num - 1));
    }

    public static void ShowVocab(String type) {                             /*เรียกใช้ Q.Vocabulary เพื่อดึงคำศัพท์มาแสดง*/
        Q.Vocabulary(type);

        System.out.println("Page-1");
        System.out.println("========================");
        for (int i = 0; i < Q.Vocab.size(); i++) {
            if (Q.Vocab.get(i) != null) {
                System.out.println(i + 1 + "." + Q.Vocab.get(i));
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
                System.out.println("Page-" + ((i + 1) / 5 + 1));
                System.out.println("========================");
            }
        }
        System.out.println("========================");
        System.out.println("");
    }

    public static void Game() {                                                /*เรียกใช้ VG.Random เพื่อนำคำศัพท์ที่สุ่มแล้วมาแสดง และทำการส่งคำตอบไปที่  CheakAns*/
        VG.Random(5);
        ArrayList<String> ans = new ArrayList();
        for (int i = 0; i < VG.Vocab.size(); i++) {
            System.out.println("          " + (i + 1) + "      \n");
            System.out.println("      " + VG.Vocab.get(i) + "      \n");
            System.out.print("Enter Answer (ใส่คำตอบ): ");
            ans.add(sc.nextLine());
        }
        System.out.println("\n");
        System.out.println("      " + "EndGame" + "\n");
        System.out.println("      " + "Point = " + VG.CheakAns(ans) + "\n");

        if (VG.CheakAns(ans) != 0) {
            DM.AddPoint(VG.CheakAns(ans));
        }

        if (!VG.WrongAnswer.isEmpty()) {
            System.out.println("====WrongAnswer====" + "\n");
            for (int i = 0; i < VG.WrongAnswer.size(); i++) {
                System.out.println("      " + VG.WrongAnswer.get(i) + "\n");
            }
        }
    }

    public static void GameChallehge(){                                         /*ทำการจับเวลาและเรียกใช้ Challehge เพื่อเรียกคำศัพท์ และส่งคำตอบ CheakAns*/
        double startTime = System.nanoTime();
        double endTime;
        VG.Challehge();
        ArrayList<String> ans = new ArrayList();
        for (int i = 0; i < VG.Vocab.size(); i++) {
            System.out.println("          " + (i + 1) + "      \n");
            System.out.println("      " + VG.Vocab.get(i) + "      \n");
            System.out.print("Enter Answer (ใส่คำตอบ): ");
            ans.add(sc.nextLine());
        }
        System.out.println("\n");
        System.out.println("      " + "EndGame" + "\n");
        System.out.println("      " + "Point = " + VG.CheakAns(ans) + "\n");

        if (VG.CheakAns(ans) != 0) {
            DM.AddPoint(VG.CheakAns(ans));
        }

        if (!VG.WrongAnswer.isEmpty()) {
            System.out.println("====WrongAnswer====" + "\n");
            for (int i = 0; i < VG.WrongAnswer.size(); i++) {
                System.out.println("      " + VG.WrongAnswer.get(i) + "\n");
            }
        }
        endTime = System.nanoTime()-startTime;
        System.out.println("เวลาที่ใช้ " + ((int)endTime/1000000000) + " วินาที");
    }

    public static void MenuAddVocab() {                         /*รับข้อมูลและส่งไปที่ InsertVocab*/
        System.out.print("ใส่คำศัพท์ภาษาอังกฤษ: ");
        String eng = sc.next();
        System.out.print("ใส่คำศัพท์ภาษาไทย: ");
        String thai = sc.next();
        System.out.print("ใส่ชนิดของคำศัพท์ (ภาษาอังกฤษ): ");
        String type = sc.next();
        if (InsertVocab(eng, thai, type)) {
            System.out.println("เพิ่มคำศัพท์เรียบร้อยแล้ว");
        } else {
            System.out.println("ไม่สามารถเพิ่มคำศัพท์ได้ คำศัพท์นี้อาจจะมีอยู่ในระบบอยู่แล้ว");
            System.out.println("");
        }
    }
}
