
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

public class Ballot extends JPanel {

    private int _ballotSize = 0;
    private String _id = "";
    private String _title = "";
    private String[] _buttonNames;
    private int[] _ballots;
    private JButton[] _buttons;

    public Ballot() {

    }

    public void setButtons(JButton[] buttons) {
        _buttons = buttons;
    }

    public JButton[] getButtons() {
        return _buttons;
    }

    public void setSize(int ballotSize) {
        _ballotSize = ballotSize;
    }

    public int getBallotSize() {
        return _ballotSize;
    }

    public void setId(String ID) {
        _id = ID;
    }

    public String getId() {
        return _id;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getTitle() {
        return _title;
    }

    public void setNames(String[] buttonNames) {
        _buttonNames = buttonNames;
        _ballots = new int[_buttonNames.length];
    }

    public String[] getNames() {
        return _buttonNames;
    }

    public void saveFile(String fileName) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        int index = 0;
        for (int i = 0; i < _buttons.length; i++) {
            if (_buttons[i].getForeground().equals(Color.red)) {
                index = i;
            } else {
                //do nothing
            }
        }
        _ballots[index]++;
        for (int i = 0; i < _buttonNames.length; i++) {
            out.println(_buttonNames[i] + " " + _ballots[i]);
        }
        out.close();
    }

    public boolean safeSaveFile(String fileToWrite, String backupFile) {

        try {
            PrintWriter out = new PrintWriter(backupFile);
            for (int i = 0; i < _buttonNames.length; i++) {
                out.println(_buttonNames[i] + " " + _ballots[i]);
            }
            out.close();
            File a = new File(fileToWrite);
            File b = new File(backupFile);
            Files.copy(b.toPath(), a.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(b.toPath());
            return true;
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
            return false;
        }

    }
}
