package corefunctions;

import corefunctions.Expression.*;
import corefunctions.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Interpreter implements Expression.Visitor<String> {
    List<String> props;
    List<String> results;
    int n;

    private String evaluate(Expression expr) {
        return expr.accept(this);
    } 

    public String interpret(Expression expr) {
        initialize();  
        results = new ArrayList<>(); 
        return evaluate(expr);
    }

    @Override
    public String visitGroupExpr(Expression.Group expr) {
        return evaluate(expr.expr);
    }
    @Override
    public String visitLiteralExpr(Expression.Literal expr) {
        StringBuilder sb = new StringBuilder();
        if (expr.literal.equals("T")) {
            for(int i = 0; i < n; i++) {
                sb.append("1");
            }
            return sb.toString();
        }
        else if(expr.literal.equals("F")) {
            for(int i = 0; i < n; i++) {
                sb.append("0");
            }
            return sb.toString();
        }
        
        return getTruthValue(n / (2 << (props.indexOf(expr.literal))));
        // System.out.println("proposition ["+ expr.literal +"]\nval: " + Integer.toBinaryString(val));
    }
    @Override
    public String visitBinaryExpr(Expression.Binary expr) {
        String left = evaluate(expr.left);
        String right = evaluate(expr.right);        
        
        String res = "";
        switch (expr.op.type) {
            
            case TokenType.AND:
                res = calculateAnd(left, right); 
                break;
            case TokenType.OR:
                res = calculateOr(left, right);
                break;
            case TokenType.IF:
                res = calculateIf(left, right);
                break;
            case TokenType.IFF:
                res = calculateIff(left, right);
                break;
        }
        results.add(res);
        return res;
    }
    @Override
    public String visitUnaryExpr(Expression.Unary expr) {
        String res = calculateNot(evaluate(expr.right));
        results.add(res);
        return res;
    }
    private String calculateAnd(String p, String q) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(0) - '0';
            int b = q.charAt(0) - '0';
            sb.append(Integer.toBinaryString(a & b));
        }
        return sb.toString();
    }
    private String calculateNot(String p) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(i) - '0' & 1;
            res.append(Integer.toBinaryString(a));
        }
        return res.toString();
    }
    private String calculateOr(String p, String q) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(0) - '0';
            int b = q.charAt(0) - '0';
            sb.append(Integer.toBinaryString(a | b));
        }
        return sb.toString();
    }
    private String calculateIf(String p, String q) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int a = p.charAt(0) - '0';
            int b = q.charAt(0) - '0';
            sb.append(~a | b);
        }
        return sb.toString();
    }
    private String calculateIff(String p, String q) {
        StringBuilder sb = new StringBuilder();
        String suf = calculateIf(p, q);
        String nec = calculateIf(q, p);
        for(int i = 0; i < p.length(); i++) {
            int a = suf.charAt(i) - '0';
            int b = nec.charAt(i) - '0';
            sb.append(a & b);
        }
        return sb.toString();
    }
    public String getTruthValue(int k) {
        StringBuilder sb = new StringBuilder();
        boolean isOneGroup = true;
        while(sb.length() < n) {
            for(int i = 0; i < k; i++) {
                sb.append(isOneGroup ? "1" : "0");
            }
            isOneGroup = !isOneGroup;
        }
        return sb.toString();
    }
    
    public int getn() {
        return n;
    }
    public List<String> getProps() {
        return props;
    }
    private void initialize() { 
        props = Parser.propositions;
        Collections.sort(props);
        while(props.contains('T') || props.contains('F')) {
            props.remove('T');
            props.remove('F');
        }
        n = 1 << props.size();

    }
}