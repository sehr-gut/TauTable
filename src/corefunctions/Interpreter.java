package corefunctions;

import corefunctions.Expression.*;
import corefunctions.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Interpreter implements Expression.Visitor<Integer> {
    List<String> props;
    List<Integer> results;
    int n;

    private Integer evaluate(Expression expr) {
        int res = expr.accept(this) & ((1 << n) - 1);
        return res;
    } 

    public Integer interpret(Expression expr) {
        initialize();  
        results = new ArrayList<>(); 
        return evaluate(expr);
    }

    @Override
    public Integer visitGroupExpr(Expression.Group expr) {
        int res = evaluate(expr.expr);
       
        return res;
    }
    @Override
    public Integer visitLiteralExpr(Expression.Literal expr) {
        if (expr.literal.equals("T")) return ((1 << n) - 1);
        else if(expr.literal.equals("F")) return 0;
        int val = getTruthValue(n / (2 << (props.indexOf(expr.literal))));
        // System.out.println("proposition ["+ expr.literal +"]\nval: " + Integer.toBinaryString(val));
        results.add(val & ((1 << n) - 1));
        return val & ((1 << n) - 1);
    }
    @Override
    public Integer visitBinaryExpr(Expression.Binary expr) {
        int left = evaluate(expr.left);
        int right = evaluate(expr.right);        
        
        int res = 0;
        switch (expr.op.type) {
            
            case TokenType.AND:
                res = (left & right); 
                break;
            case TokenType.OR:
                res = (left | right);
                break;
            case TokenType.IF:
                res =(~left | right);
                break;
            case TokenType.IFF:
                res =(~left | right);
                break;
        }
        results.add(res);
        return res;
    }
    @Override
    public Integer visitUnaryExpr(Expression.Unary expr) {
        int res = ~evaluate(expr.right) & ((1 << n) - 1);
        results.add(res);
        return res;
    }

    public int getTruthValue(int k) {
        StringBuilder sb = new StringBuilder();
        boolean isOneGroup = true;
        while(sb.length() < n) {
            for(int i = 0; i < k; i++) {
                sb.append(isOneGroup ? "1" : "0");
            }
            isOneGroup = !isOneGroup;
        }
        int res =Integer.parseInt(sb.toString(), 2);
        return (res);
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