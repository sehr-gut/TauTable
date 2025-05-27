/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;
import corefunctions.InterpreterHandler;
import corefunctions.TruthTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Franklin Xam
 */
public class Model {
    final static InterpreterHandler ih = new InterpreterHandler();
    static List<String> previousInputs = new ArrayList<>(); 
    static List<TruthTable> prevTables = new ArrayList<>();

    static TruthTable tt;
    private String input;
    
    public void interpret(String propositions){
        input = propositions;
        tt = ih.interpret(propositions);
    }
    public TruthTable readFromCsv(){
        try {
            previousInputs.clear();
            prevTables.clear();
            BufferedReader b = new BufferedReader(new FileReader(".\\src\\Saved Tables\\tables.csv"));
            String s  = "";
            while((s = b.readLine()) != null) {
                String [] sl = s.split(",");
                
                previousInputs.add(sl[0]);
                String[] ps = sl[1].split("\\|");
                String[] vs = sl[2].split("\\|");
                List<String> props =  Arrays.asList(ps);
                List<String> value =  Arrays.asList(vs);
                prevTables.add(new TruthTable(props, value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveToCsv() {
        try {
            FileWriter fw = new FileWriter(".\\src\\Saved Tables\\tables.csv",true);
            File f = new File(".\\src\\Saved Tables\\tables.csv");
            if(!f.exists()) {
                f.createNewFile();
            }       
            fw.append(tt.CSVForm());
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void printCSV() {
        System.out.println(tt.CSVForm());
    }
} 
