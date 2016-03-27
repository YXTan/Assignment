//necessary import
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/*
 YongxinTan Assignment3 Quiz
 */

public class Quiz {

    public static void main(String[] args) throws FileNotFoundException {

		String fileName = "";
		if (args.length > 0) {
	    	fileName = args[0];
		} else {
	    	System.out.println("No args given!");
		}//read the file name from the command line and stop the programm if there is no args
		
        System.out.println("Welcome to the Quiz Program! Good Luck!");

		//get variables that I need
        File file = new File(fileName);
        Scanner scanFile = new Scanner(file);
        Scanner scan = new Scanner(System.in);
        ArrayList<Question> question = new ArrayList<>();//arraylist of objects
        ArrayList<String> rightAnswer = new ArrayList<>();//array list of right answers
        ArrayList<String> userAnswer = new ArrayList<>();//array list of users' answers
        
        //create ArrayList with Question
        while (scanFile.hasNext()) {
            Question q;
            String temp = scanFile.nextLine();
            if (temp.endsWith("?")) {
                int size = Integer.parseInt(scanFile.nextLine());
                q = new Question(size);//confirm the size of the object and create the object
                //use an OO way to set up all variables in the object after reading them from the file
                q.setSize(size);
                q.setQuestion(temp);

                String[] choice = new String[size];
                for (int i = 0; i < size; i++) {
                    choice[i] = scanFile.nextLine();
                }
                q.setChoice(choice);

                String right = scanFile.nextLine();
                q.setRightAnswer(Integer.parseInt(right));
                rightAnswer.add(right);

                int tryTime = Integer.parseInt(scanFile.nextLine());
                q.setTriedTimes(tryTime);

                int rightTime = Integer.parseInt(scanFile.nextLine());
                q.setRightTimes(rightTime);

                question.add(q);//add it back to the array list
            }
        }
        //print out questions and get answers
        String ques = "";
        String[] choice;
        for (int i = 0; i < question.size(); i++) {
            System.out.println("Question" + (i + 1) + ":");
            Question q = question.get(i);
            System.out.println(ques);
            choice = q.getChoice();
            for (int j = 0; j < choice.length; j++) {
                System.out.println(j + ": " + choice[j]);
            }
            System.out.println();
            System.out.println("Your answer? (enter a number): ");
            boolean valid = false;
            //get users' answer and check its validity
            while (!valid) {
                if (scan.hasNextInt()) {
                    int temp = scan.nextInt();
                    if (temp>=0&&temp<=(choice.length-1)){
                        userAnswer.add(temp + "");
                        valid = true;
                    }else{
                        System.out.println("Enter the right number!");
                        valid = false;
                    }
                } else {
                    scan.next();
                }
            }
        }
        System.out.println("Thank you for your answers!");
        System.out.println("Here are your results:");
        System.out.println();
        //compare users' answer with the right answer using two array list made before
        int right = 0;
        int wrong = 0;
        for (int i = 0; i < question.size(); i++) {
        	Question q = question.get(i);//get the object which is stored in an array list
        	int rightTime = q.getRightTimes();
        	int time = q.getTriedTimes();
        	System.out.println(time);
            System.out.println("Question: " + q.getQuestion());
            System.out.println("Answer: " + rightAnswer.get(i));
            System.out.println("Player Guess: " + userAnswer.get(i));
            if (rightAnswer.get(i).equals(userAnswer.get(i))){
                System.out.println("	Result: CORRECT! Great Work!");
                rightTime++;
                right ++;
                q.setRightTimes(rightTime);//
            }else {
                System.out.println("	Result: INCORRECT! Remember the answer for next time!");
                wrong ++;
                q.setRightTimes(rightTime);
            }    
            time ++;
            q.setTriedTimes(time);
            System.out.println(time);
        }
        //overall performance
        System.out.println("Your overall performance was:");
        System.out.println("	Right: " + right);
        System.out.println("	Wrong: " + wrong);
        double k = (double)right/(right + wrong);
        System.out.println("	Pct: " + k);
        //cumulative statistics
        System.out.println("Here are some cumulative statistics: ");
        double[] pctList = new double[question.size()];
        for (int i = 0; i < question.size(); i++) {
            Question q = question.get(i);
            double pct = 0.0;
            System.out.println("Question: " + q.getQuestion());
            System.out.println("	Times Tried: " + q.getTriedTimes());
            System.out.println("	Times Correct: " + q.getRightTimes());
            pct = (double)q.getRightTimes()/q.getTriedTimes();
            pctList[i] = pct;
            System.out.printf("	Percent Correct: %.1f%% \n", pct*100);
        }
        //find easiest and hardest question by comparing the percentage
        double easist = pctList[0];
        int indexE = 0;
        for (int i=0; i<pctList.length; i++){
            if (pctList[i] > easist){
                easist = pctList[i];
                indexE = i;
            }
        }
        
        double hardest = pctList[pctList.length-1];
        int indexH = 0;
        for (int i=0; i<pctList.length; i++){
            if (pctList[i] < hardest){
                hardest= pctList[i];
                indexH = i;
            }
        }
        //print out hardest and easiest question
        System.out.println("Easist Question:");
        Question easistQuestion = question.get(indexE);
        System.out.println("Question: " + easistQuestion.getQuestion());
        System.out.println("	Times Tried: " + easistQuestion.getTriedTimes());
        System.out.println("	Times Correct: " + easistQuestion.getRightTimes());
        System.out.printf("	Percent Correct: %.1f%% \n", easist*100);
        
        System.out.println("Hardest Question:");
        Question hardestQuestion = question.get(indexH);
        System.out.println("Question: " + hardestQuestion.getQuestion());
        System.out.println("	Times Tried: " + hardestQuestion.getTriedTimes());
        System.out.println("	Times Correct: " + hardestQuestion.getRightTimes());
        System.out.printf("	Percent Correct: %.1f%% \n", hardest*100);   
        //re-write the whole file
        PrintWriter writeFile = new PrintWriter(args[0]);
        writeFile.print("");
        for (int i=0; i<question.size();i++){
            Question que = question.get(i);
            que.writeFile(writeFile, que, args[0]);//use method of "question"
        }
        writeFile.close();//close the file
    }
}
