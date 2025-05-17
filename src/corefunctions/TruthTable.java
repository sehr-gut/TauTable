package corefunctions;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class TruthTable extends DefaultTableModel {
    int n;
    List<String> k;
    List<String> v;
    public TruthTable(int n) {
        this.n = n;
    }
    public void setn(int n) {
        this.n = n;
    }
    public int getn(){
        return n;
    }
    public void generateTable(List<String> s, List<String> v) {
        k = s;
        this.v = v;
        setColumnIdentifiers(new Vector<>(s));
        
        for(int j = 0; j < n; j++) {
            Object[] m = new Object[v.size()];
            for(int i = 0; i < v.size(); i++) {
                String curr = v.get(i);
                m[i] = curr.charAt(j) == '1'? "T":"F";
                
            }
            insertRow(getRowCount(), m);
        }
    }
    public String CSVForm() {
        StringBuilder sb = new StringBuilder();
        sb.append(k.get(k.size() - 1));
        sb.append(",");
        for (int i = 0; i < v.size(); i++) {
            sb.append(v.get(i));
            sb.append("|");
        }
        sb.append("\n");
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