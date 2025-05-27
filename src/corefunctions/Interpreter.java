package corefunctions;

import corefunctions.Expression.*;
import corefunctions.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Interpreter implements Expression.Visitor<String> {
    // conrete visitor class for the numeric output of the 
    // proposition generator.
    // this is an Abstract Syntax Tree reader
    List<String> props;
    List<String> results;
    int n;

    private String evaluate(Expression expr) { // 
        return expr.accept(this);
    } 

    public String interpret(Expression expr) {
        initialize();  // calls the initialize to clear all variables
        results = new ArrayList<>(); 
        return evaluate(expr);
    }

    @Override
    public String visitGroupExpr(Expression.Group expr) {
        return evaluate(expr.expr); // visits inner expression
    }
    @Override
    public String visitLiteralExpr(Expression.Literal expr) {
        StringBuilder sb = new StringBuilder(); // gets the literal value
        if (expr.literal.equals("T")) { // for all true statements
            for(int i = 0; i < n; i++) {
                sb.append("1");
            }
            return sb.toString();
        }
        else if(expr.literal.equals("F")) { // for all false statements
            for(int i = 0; i < n; i++) {
                sb.append("0");
            }
            return sb.toString();
        }
        
        // this is the else part
        String val = getTruthValue(n / (2 << (props.indexOf(expr.literal))));
            // we get the position of the proposition by dividing n by 2 
            // bitshifted by the index
            // example
            // getTruthValue(8 / (2 << 1))
            // would yield getTruthValue(2) thats (8 /4)
            // so the pattern that would alternate is 11001100
            // which is correct if you see truth tables
        results.add(val); // add to the result
        return val;
        // System.out.println("proposition ["+ expr.literal +"]\nval: " + Integer.toBinaryString(val));
    }
    @Override
    public String visitBinaryExpr(Expression.Binary expr) {
        String left = evaluate(expr.left);
        String right = evaluate(expr.right);        
        // for operations thet requiers two 
        String res = "";
        switch (expr.op.type) {
            case TokenType.AND: // for p ^ q
                res = calculateAnd(left, right); 
                break;
            case TokenType.OR:// for p v q
                res = calculateOr(left, right);
                break;
            case TokenType.IF: // for p -> q
                res = calculateIf(left, right);
                break;
            case TokenType.IFF: // for p <=> q
                res = calculateIff(left, right);
                break;
        }
        results.add(res); // adds the result to the results array
        return res;
    }
    @Override
    public String visitUnaryExpr(Expression.Unary expr) {
        // single proposition calculations
        String res = calculateNot(evaluate(expr.right));
        results.add(res);
        return res;
    }
    
    // the reason for & 1 is that we only want the last integer but the 
    // operations outputs 32 bits. to get the last bit, we use "&" with 1
    private String calculateAnd(String p, String q) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(i) - '0';
            int b = q.charAt(i) - '0';// convertion from char to binary
            sb.append(Integer.toBinaryString(a & b)); // bitwise and
        }
        return sb.toString();
    }
    private String calculateNot(String p) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = ~(p.charAt(i) - '0') & 1; // bitwise not
            res.append(Integer.toBinaryString(a));
        }
        return res.toString();
    }
    private String calculateOr(String p, String q) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(i) - '0' & 1;
            int b = q.charAt(i) - '0' & 1;// convertion from char to binary
            sb.append(Integer.toBinaryString(a | b)); // bitwise or
        }
        return sb.toString();
    }
    private String calculateIf(String p, String q) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(i) - '0' & 1;
            int b = q.charAt(i) - '0' & 1; // convertion from char to binary
            sb.append(Integer.toBinaryString((~a & 1) | b)); // ~p v q definition
        }                                                   // of if-then
        return sb.toString();
    }
    private String calculateIff(String p, String q) {
        StringBuilder sb = new StringBuilder();
        String suf = calculateIf(p, q);
        String nec = calculateIf(q, p);
        for(int i = 0; i < p.length(); i++) {
            int a = suf.charAt(i) - '0';
            int b = nec.charAt(i) - '0';
            sb.append(Integer.toBinaryString(a & b));
        }
        return sb.toString();
    }
    public String getTruthValue(int k) { // gets the boolean value of the 
                                         // variable
        StringBuilder sb = new StringBuilder();// output
        boolean isOneGroup = true;
        while(sb.length() < n) {        // all this does is to alternate
                                         // 1s and 0s k times
                                          // like if k = 2 then 11001100
            for(int i = 0; i < k; i++) {
                sb.append(isOneGroup ? "1" : "0"); 
            }
            isOneGroup = !isOneGroup;
        }
                                        // most important for truth table
        return sb.toString();
    }
    
    public int getn() {
        return n;
    }
    public List<String> getProps() {
        return props;
    }
    private void initialize() { 
        props = Parser.propositions; // gets the propositions from the parser
        Collections.sort(props); // sorts it so that proper placement of 
                                 // propositions are achieved
                                 // cases like p->q and q->p where the 
                                  // aphabetical order matters
                                  // without sorting they would have the same
                                  // output
        while(props.contains('T') || props.contains('F')) {
            props.remove('T');  // removes Tautology from the list (All true)
            props.remove('F');  // removes Contradictions from the list (All False)
        }
        n = 1 << props.size(); // returns the needed length of the output
                                // the same as 2 ^ n

    }
}