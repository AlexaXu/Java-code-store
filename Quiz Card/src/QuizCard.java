/**
 * Created by Better on 2017/7/17.
 */
import java.io.*;

public class QuizCard {
    private String question;
    private String answer;

    public QuizCard(){

    }

    public QuizCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
