package Model.Game;

import Model.DateBase.Query;
import java.util.ArrayList;

public class VocabularyGame implements GameInterface {

    static Query Q = new Query();
    public ArrayList<String> Vocab;
    public ArrayList<String> WrongAnswer;

    @Override
    public int CheakAns(ArrayList<String> ans) {   /*ทำการรับคำตอบมาและตรวจสอบคำตอบ*/
        int point = 0;
        this.WrongAnswer = new ArrayList();
        for (int i = 0; i < ans.size(); i++) {
            if (Vocab.get(i).equals(Q.FindWordTH(ans.get(i))) || Vocab.get(i).equals(Q.FindWordENG(ans.get(i)))) {
                point++;
            } else {
                if(!Q.FindWordENG(Vocab.get(i)).equals("empty")){
                    this.WrongAnswer.add(Vocab.get(i) + "  |  " + Q.FindWordENG(Vocab.get(i)));
                }else{
                    this.WrongAnswer.add(Vocab.get(i) + "  |  " + Q.FindWordTH(Vocab.get(i)));
                }
            }
        }
        return point;
    }

    @Override                               /*ทำการสุ่มคำศัพท์ที่เป็นภาษาอังกฤษ*/
    public void Random(int size) {
        Q.Vocabulary("all");
        int num;
        this.Vocab = new ArrayList();
        for (int i = 0; i < size; i++) {
            num = (int) (Math.random() * Q.Vocab.size());
            Vocab.add(Q.VocabEng.get(num));
        }
    }

    @Override                                   /*ทำการสุ่มคำศัพท์ ภาษาอังกฤษและภาษาไทย*/
    public ArrayList Challehge() {
        Q.Vocabulary("all");
        this.Vocab = new ArrayList();
        int num;
        int num2;
        for (int i = 0; i < 10; i++) {
            num = (int) (Math.random() * 2);
            num2 = (int) (Math.random() * Q.Vocab.size());
            if (num == 1) {
                Vocab.add(Q.VocabEng.get(num2));
            } else {
                Vocab.add(Q.VocabThai.get(num2));
            }
        }
        return this.Vocab;
    }
}
