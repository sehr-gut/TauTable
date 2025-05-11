package corefunctions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TruthTable {
    HashMap<String, Integer> truthTable ;
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
    public HashMap<String, Integer> getTruthTable() {
        return truthTable;
    }
    public void addEntry(String key, Integer value) {
        truthTable.put(key, value);
    }
    public Integer getEntry(String key) {
        return truthTable.get(key);
    }
    public void generateTable(List<String> s, List<Integer> v) {
        for(int i = 0; i < s.size(); i++) {
            String key = s.get(i);
            Integer value = v.get(i);
            truthTable.put(key, value);
        }
    }
    public String toBytes(int m) {
        String res = "";
        for(int i = n - 1; i >= 0; i--) {
            res += ((m >> i) & 1);
        }
        return res;
    }
    @Override
    public String toString() {
        String res = "";
        List<String> keys = new ArrayList<>(truthTable.keySet());
        keys.sort(Comparator.comparingInt(String::length));
        for(int i = 0; i < truthTable.size(); i++) {
            res +=  keys.get(i) + " - " + toBytes(truthTable.get(keys.get(i)) )+ "\n";
        }
        return res;
    }
    
}