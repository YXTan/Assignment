
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Question {

    String _question;
    String[] _qArray;
    int _rightAnswer = 0;
    int _triedTimes = 0;
    int _correctTimes = 0;
    String[] _choice;
    int _size = 0;

    public Question() {

    }
	//constructor
    public Question(int size) {
        _qArray = new String[size];
    }
	//setters and getters
    public void setQuestion(String question) {
        _question = question;
    }

    public String getQuestion() {
        return _question;
    }

    public void setSize(int size) {
        _size = size;
    }

    public int getSize() {
        return _size;
    }

    public void setChoice(String[] choice) {
        _choice = choice;
    }

    public String[] getChoice() {
        return _choice;
    }

    public void setRightAnswer(int rightAnswer) {
        _rightAnswer = rightAnswer;
    }

    public int getRightAnswer() {
        return _rightAnswer;
    }

    public void setTriedTimes(int triedTimes) {
        _triedTimes = triedTimes;
    }

    public int getTriedTimes() {
        return _triedTimes;
    }

    public void setRightTimes(int correctTimes) {
        _correctTimes = correctTimes;
    }

    public int getRightTimes() {
        return _correctTimes;
    }
    
    public void writeFile(PrintWriter writeFile, Question q,String name) throws FileNotFoundException {
        writeFile.println(q.getQuestion());
        writeFile.println(q.getSize());
        for (int j = 0; j < q.getChoice().length; j++) {
            String[] temp = q.getChoice();
            writeFile.println(temp[j]);
        }
        writeFile.println(q.getRightAnswer());
        writeFile.println(q.getTriedTimes());
        writeFile.println(q.getRightTimes());
    }

}
