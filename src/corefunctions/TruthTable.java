package corefunctions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TruthTable {
    HashMap<String, String> truthTable ;
    int n;
    public TruthTable() {
        truthTable = new HashMap<>();
    }
    public TruthTable(int n) {
        this.n = n;
        truthTable = new HashMap<>();
    }
    public void setn(int n) {
        this.n = n;
    }
    public HashMap<String, String> getTruthTable() {
        return truthTable;
    }
    public void addEntry(String key, String value) {
        truthTable.put(key, value);
    }
    public String getEntry(String key) {
        return truthTable.get(key);
    }
    public void generateTable(List<String> s, List<String> v) {

        for(int i = 0; i < s.size(); i++) {
            String key = s.get(i);
            String value = v.get(i);
            truthTable.put(key, value);
        }
    }

    @Override
    public String toString() {
        String res = "";
        List<String> keys = new ArrayList<>(truthTable.keySet());
        keys.sort(Comparator.comparingInt(String::length));
        for(int i = 0; i < truthTable.size(); i++) {
            res +=  keys.get(i) + " - " + truthTable.get(keys.get(i))+ "\n";
        }
        return res;
    }
    
}