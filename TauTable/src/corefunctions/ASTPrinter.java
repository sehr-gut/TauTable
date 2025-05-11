
package corefunctions;

import java.util.ArrayList;
import java.util.List;




public class ASTPrinter implements Expression.Visitor<String> {
    List<String> propositions = new ArrayList<>(); 
    String print(Expression head) {
        return head.accept(this);
    }

    @Override
    public String visitGroupExpr(Expression.Group expr) {
        String s = expr.expr.accept(this);
        return s;
    }
    @Override
    public String visitBinaryExpr(Expression.Binary expr) {
        String s = expr.left.accept(this)+ " " + expr.op.lexeme + " " + expr.right.accept(this);
        propositions.add(s);    
        return s;
    }
    @Override
    public String visitUnaryExpr(Expression.Unary expr) {
        String r = expr.right.accept(this);
        String s = (r.length() >= 2)? expr.op.lexeme + "(" + r + ")" : expr.op.lexeme + r;
        propositions.add(s);
        return s;
    }
    @Override
    public String visitLiteralExpr(Expression.Literal expr) {
        propositions.add(expr.literal);
        return expr.literal;
    }
    public List<String> getPropositions() {
        return propositions;
    }
   
    
}