package corefunctions;


public abstract class Expression {
    // The visitor class where every concrete visitor extends
    interface Visitor<R> { // interface for the concrete visitor 
        R visitBinaryExpr(Binary expr);
        R visitUnaryExpr(Unary expr);
        R visitGroupExpr(Group expr);
        R visitLiteralExpr(Literal expr);
    }
    public static class Binary extends Expression {
        // concrete element of the expression
        Binary(Expression left, Token op, Expression right) {
            this.left = left;
            this.op = op;
            this.right = right;
        }
        // since it is an oparation in pairs, the structure
        // needs the left and right hand and an operator
        final Expression left;
        final Token op;
        final Expression right;

        @Override
        <R> R accept(Visitor<R> v) {
            return v.visitBinaryExpr(this);
        }
    }
    public static class Unary extends Expression {
        // concrete element for single operations
        Unary(Token op, Expression right) {
            this.op = op;
            this.right = right;
        }
        // since it is only for one proposition
        // the only needed things here are the
        // operation and the righthand side
        final Token op;
        final Expression right;

        @Override
        <R> R accept(Visitor<R> v){
            return v.visitUnaryExpr(this);
        }
    }
    public static class Literal extends Expression{
        // concrete element for literals (i.e. propositions)
        Literal (String literal) {
            this.literal = literal;
        }
        final String literal;

        @Override
        <R> R accept(Visitor<R> v){
            return v.visitLiteralExpr(this);
        }
    }
    public static class Group extends Expression{
        Group (Expression expr) {
            this.expr = expr;
        }
        // this is for groupings
        final Expression expr;

        @Override
        <R> R accept(Visitor<R> v){
            return v.visitGroupExpr(this);
        }
    }

   abstract <R> R accept(Visitor<R> v);
}