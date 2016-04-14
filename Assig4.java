
/**
 * YongxinTan Assignment4
 */
//necessary import
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Assig4 {
    
    //create private static variables and wirte their getters
    private static JButton loginButton = new JButton("Login to vote");
    private static JButton castButton = new JButton("Cast your vote");
    private static JFrame mainWindow = new JFrame("E-Vote");
    private static ArrayList<JButton[]> buttons = new ArrayList<>();
    private static ArrayList<Ballot> ballots = new ArrayList<>();
    private static Voter voter = new Voter();

    public static ArrayList<Ballot> getBallots() {
        return ballots;
    }

    public static Voter getVoter() {
        return voter;
    }

    public static JButton getLogin() {
        return loginButton;
    }

    public static JButton getCast() {
        return castButton;
    }

    public static JFrame getMainWindow() {
        return mainWindow;
    }

    public static ArrayList<JButton[]> getNormalButtons() {
        return buttons;
    }
    //Listener checker
    public static void checkListener() {
        System.out.println("KKK");
    }

    public static void main(String[] args) throws Exception {
        //read file name from the command line
        String fileName = "";
        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("No args given!");
        }
        //set up main window
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new FlowLayout());
        File openFile = new File(fileName);
        Scanner scanFile = new Scanner(openFile);
        System.out.println("scanner finished");
        
        //start reading the "ballots" file
        int ballotSize = Integer.parseInt(scanFile.nextLine());
        for (int i = 0; i < ballotSize; i++) {//use a for loop to get all necessary information
            Ballot ballot = new Ballot();
            ballot.setSize(ballotSize);

            String temp = scanFile.nextLine();
            //System.out.println(temp);
            String[] split = temp.split(":");
            
            //set up the ballot object which extends JPanel
            String id = split[0];
            ballot.setId(id);
            ballot.setTitle(split[1]);
            ballot.setLayout(new BoxLayout(ballot, BoxLayout.Y_AXIS));

            //add JLabel to the panel
            JLabel name = new JLabel();
            name.setFont(new Font("CourierNew", Font.PLAIN, 13));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setText(split[1]);
            ballot.add(name);
            
            //add buttons to the panel and store them in an ArrayList
            String[] _buttonName = split[2].split(",");
            ballot.setNames(_buttonName);
            JButton[] normalButtons = new JButton[_buttonName.length];
            for (int j = 0; j < normalButtons.length; j++) {
                normalButtons[j] = new JButton();
                //System.out.println(_buttonName[j]);
                normalButtons[j].setText(_buttonName[j]);
                normalButtons[j].setAlignmentX(Component.CENTER_ALIGNMENT);
                normalButtons[j].setEnabled(false);
                ActionListener normal = new NormalButtonListener();
                normalButtons[j].addActionListener(normal);
                ballot.add(normalButtons[j]);
            }
            ballot.setButtons(normalButtons);
            //safe save the ballot information
            ballot.safeSaveFile(id, "backUp.txt");
            System.out.println("Safe Saved!");
            //add things to the arraylist and to the mainwindow
            buttons.add(normalButtons);
            mainWindow.add(ballot);
            ballots.add(ballot);
        }
        
        //create login panel and set buttons
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());
        castButton.setEnabled(false);
        loginButton.setEnabled(true);
        ActionListener loginListener = new LoginButtonListener();
        loginButton.addActionListener(loginListener);
        loginPanel.add(loginButton);
        mainWindow.add(loginPanel);

        //create cast panel and set buttons
        JPanel castPanel = new JPanel();
        castPanel.setLayout(new FlowLayout());
        ActionListener castListener = new CastButtonListener();
        castButton.addActionListener(castListener);
        castPanel.add(castButton);
        mainWindow.add(castPanel);

        //finish setting up
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}
//buttonListener for normal buttons
class NormalButtonListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Assignment4GUI.checkListener();
        int index = 0;
        JButton source = (JButton) e.getSource();
        ArrayList<JButton[]> temp2 = Assignment4GUI.getNormalButtons();
        //get the index of the source button
        for (int i = 0; i < temp2.size(); i++) {
            for (int j = 0; j < temp2.get(i).length; j++) {
                if (temp2.get(i)[j].equals(source)) {
                    index = i;
                }
            }
        }
        //set buttons in the source panel to black
        for (int i = 0; i < temp2.get(index).length; i++) {
            temp2.get(index)[i].setForeground(Color.black);
        }
        //set the source button to red
        source.setForeground(Color.red);
    }
}
//buttonListener for login button
class LoginButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        Voter voter = Assignment4GUI.getVoter();
        String ID = "";
        boolean valid = false;
        Assignment4GUI.checkListener();
        //let the user enter the name and check it
        ID = JOptionPane.showInputDialog("Please enter your voter ID ");
        
        //load the voter file, get necessary information
        try {
            File file = new File("voters.txt");
            voter.loadFile(file);
            Scanner scanFile = new Scanner(file);
            while (scanFile.hasNext()) {
                String temp = scanFile.nextLine();
                if (temp.startsWith(ID)) {
                    String[] infor = temp.split(":");
                    //check if the voter is valid
                    if (infor[2].equals("false")) {
                        valid = true;
                        voter.setID(ID);
                        JOptionPane.showMessageDialog(null, infor[1] + ", please make your choices.");
                        voter.setName(infor[1]);
                        //reset buttons
                        JButton source = (JButton) e.getSource();
                        source.setEnabled(false);
                        Assignment4GUI.getCast().setEnabled(true);
                        ArrayList<JButton[]> temp2 = Assignment4GUI.getNormalButtons();
                        for (int i = 0; i < temp2.size(); i++) {
                            for (int j = 0; j < temp2.get(i).length; j++) {
                                temp2.get(i)[j].setEnabled(true);
                                temp2.get(i)[j].setForeground(Color.black);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have already voted!");
                    }
                } else {
                    //do nothing
                }  
            }
            scanFile.close();
            if (valid == false){
                JOptionPane.showMessageDialog(null, "Invalid Voter!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
//buttonListener for cast button
class CastButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Assignment4GUI.checkListener();
        Voter voter = Assignment4GUI.getVoter();
        Object[] options = {"Cancel", "No", "Yes"};
        int choice = JOptionPane.showOptionDialog(null, "Confirm Vote"
                + "?", "Select an Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        //2 yes 1 no 0 cancel
        if (choice == 2) {
            voter.setStatus(true);
            //first we need to update the voter file
            String IDtemp = voter.getID();
            String nameTemp = voter.getName();
            boolean statusTemp = voter.getStatus();
            ArrayList<String> lines = voter.getFile();
            ArrayList<String> newLines = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(IDtemp)) {
                    newLines.add("" + IDtemp + ":" + nameTemp + ":" + statusTemp);
                } else {
                    newLines.add(lines.get(i));
                }
            }
            System.out.println("Change completed!");
            for (int i = 0; i < newLines.size(); i++) {
                System.out.println(newLines.get(i));
            }
            //update the voter file
            try {
                PrintWriter out = new PrintWriter("voters.txt");
                for (int i = 0; i < newLines.size(); i++) {
                    out.println(newLines.get(i));
                }
                out.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            //reset buttons
            JButton temp = (JButton) e.getSource();
            temp.setEnabled(false);
            Assignment4GUI.getLogin().setEnabled(true);
            ArrayList<JButton[]> temp2 = Assignment4GUI.getNormalButtons();
            for (int i = 0; i < temp2.size(); i++) {
                for (int j = 0; j < temp2.get(i).length; j++) {
                    temp2.get(i)[j].setEnabled(false);
                }
            }
            
            //save ballots information
            ArrayList<Ballot> ballots = Assignment4GUI.getBallots();
            for (int i = 0; i < ballots.size(); i++) {
                try {
                    ballots.get(i).saveFile(ballots.get(i).getId());
                    System.out.println("File saved!");
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            JOptionPane.showMessageDialog(null, "Thank you for your vote!");
        }
    }
}
