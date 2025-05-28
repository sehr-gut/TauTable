package corefunctions;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class TruthTable extends DefaultTableModel {
    // truth table handler
    int n;
    private List<String> k; // all the keys 
    private List<String> v; // all the values
    // kinda like hashmaps
    public TruthTable(List<String> s, List<String> v) { // with params 
        this.n = s.size();
        generateTable(s, v);
    }
    public TruthTable(int n) { // patams
        this.n = n;
    }
    public void setn(int n) { // setting table row numbers
        this.n = n;
    }
    public int getn(){ 
        return n;
    }
    public void generateTable(List<String> s, List<String> v) { // table generation
        k = s;
        this.v = v;
        setColumnIdentifiers(new Vector<>(s));
        
        for(int j = 0; j < n; j++) {
            Object[] m = new Object[v.size()];
            for(int i = 0; i < v.size(); i++) {
                String curr = v.get(i);
                m[i] = curr.charAt(j) == '1'? "T":"F"; // convertion
                                        // from 1 and 0 to T and F
                
            }
            insertRow(getRowCount(), m);
        }
    }
    public String CSVForm() { // to Csv
        StringBuilder sb = new StringBuilder();
        sb.append(k.get(k.size() - 1)); // gets the input
        sb.append(",");
        for (int i = 0; i < v.size(); i++) { // next is the propositions
                                              // generated
            sb.append(k.get(i));
            sb.append("|");
        }
        sb.append(",");
        for (int i = 0; i < v.size(); i++) { // next is the results
                                               // generated
            sb.append(v.get(i));
            sb.append("|");
        }
        sb.append("\n");
        // the output of this is (proposition input), prop1|prop2,out1|out2,
        return sb.toString();
    }
    public String toString() {
        String res = "";
        for(int i = 0;i < v.size(); i++) {
            res += (k.get(i) + " | " + v.get(i));
        }

        return res;
    }


    
}