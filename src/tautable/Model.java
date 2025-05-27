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
    // this is the M in MVC design pattern
    final static InterpreterHandler ih = new InterpreterHandler(); 
    // Uses the API provided by the interpreter handler 
    static List<String> previousInputs = new ArrayList<>(); 
    // old inputs
    static List<TruthTable> prevTables = new ArrayList<>();
    // the old tables to be displayed
    static TruthTable tt; // current truth table
    private String input;
    
    public void interpret(String propositions){ // api provided by interpreter handler
        input = propositions;
        tt = ih.interpret(propositions);
    }
    public TruthTable readFromCsv(){ // reading from csv for old propositions
        try {
            previousInputs.clear(); // clears the propositions array
            prevTables.clear(); // clears the tables array
            BufferedReader b = new BufferedReader(new FileReader(
                                ".\\src\\Saved Tables\\tables.csv")); // reader
            String s  = "";
            while((s = b.readLine()) != null) {
                String [] sl = s.split(","); // splits the csv
                
                previousInputs.add(sl[0]); // the previous inputs are added
                String[] ps = sl[1].split("\\|"); // the propositions
                String[] vs = sl[2].split("\\|"); // the results
                List<String> props =  Arrays.asList(ps); 
                List<String> value =  Arrays.asList(vs);
                prevTables.add(new TruthTable(props, value));   
                    //add new Table to the list 
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
            // standard file writing
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
