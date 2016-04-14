
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Voter {
    
    private PrintWriter _writeFile;
    private String _ID;
    private String _name;
    private boolean _status;
    private ArrayList<String> _lines = new ArrayList<>();
    
    public Voter() {
        
    }
    
    public Voter(String ID) {
        _ID = ID;
        System.out.println("Voter created!");
    }
    
    public void setName(String name) {
        _name = name;
    }
    
    public String getName() {
        return _name;
    }
    
    public void setID(String ID) {
        _ID = ID;
    } 
    
    public String getID() {
        return _ID;
    }
    
    public void setStatus(boolean status) {
        _status = status;
    }
    
    public boolean getStatus() {
        return _status;
    }
    
    public void loadFile(File file) throws FileNotFoundException {
        _lines.clear();
        Scanner scanFile = new Scanner(file);
        while (scanFile.hasNext()){
            String temp = scanFile.nextLine();
            _lines.add(temp);
        }
        scanFile.close();
        System.out.println("Load finish");
        for (int i=0; i<_lines.size(); i++){
            System.out.println(_lines.get(i));
        }
    }
    
    public ArrayList<String> getFile() {
        return _lines;
    }
    
    @Override
    public String toString() {
        String str = "" + _ID + ":" + _name + ":" + _status;
        return str;
    }
}
