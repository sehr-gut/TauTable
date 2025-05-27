
package corefunctions;

import java.util.ArrayList;
import java.util.List;




public class ASTPrinter implements Expression.Visitor<String> {
    // uses visitor pattern, being the concrete visitor class
    // generating AST of the language
    List<String> propositions = new ArrayList<>(); // stores all the propossitions
                                                   // statements
    public String print(Expression head) {
        return head.accept(this); // this starts the recursive descent algorithm
    }

    @Override
    public String visitGroupExpr(Expression.Group expr) { 
        String s = "(" + expr.expr.accept(this) + ")"; // handles group expression
        return s;
    }
    @Override
    public String visitBinaryExpr(Expression.Binary expr) {
        // handles operations with two statements
        String s = expr.left.accept(this)+ " " + expr.op.lexeme +
                                        " " + expr.right.accept(this);
                                        
        propositions.add(s);    
        return s;
    }
    @Override
    public String visitUnaryExpr(Expression.Unary expr) {
        // handles one statement propositions
        String r = expr.right.accept(this);
        String s = (r.length() >= 2)? expr.op.lexeme +
                                        "(" + r + ")" : expr.op.lexeme + r;
        propositions.add(s);
        return s;
    }
    @Override
    public String visitLiteralExpr(Expression.Literal expr) {
        // the most fundamental part of this AST
        propositions.add(expr.literal);
        return expr.literal;
    }
    public List<String> getPropositions() {
        // getter for the propositions
        return propositions;
    }
   
    
}