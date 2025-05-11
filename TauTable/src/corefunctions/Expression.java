package corefunctions;


abstract class Expression {
    interface Visitor<R> {
        R visitBinaryExpr(Binary expr);
        R visitUnaryExpr(Unary expr);
        R visitGroupExpr(Group expr);
        R visitLiteralExpr(Literal expr);
    }
    static class Binary extends Expression {
        Binary(Expression left, Token op, Expression right) {
            this.left = left;
            this.op = op;
            this.right = right;
        }
        final Expression left;
        final Token op;
        final Expression right;

        @Override
        <R> R accept(Visitor<R> v) {
            return v.visitBinaryExpr(this);
        }
    }
    static class Unary extends Expression {
        Unary(Token op, Expression right) {
            this.op = op;
            this.right = right;
        }
        final Token op;
        final Expression right;

        @Override
        <R> R accept(Visitor<R> v){
            return v.visitUnaryExpr(this);
        }
    }
    static class Literal extends Expression{
        Literal (String literal) {
            this.literal = literal;
        }
        final String literal;

        @Override
        <R> R accept(Visitor<R> v){
            return v.visitLiteralExpr(this);
        }
    }
    static class Group extends Expression{
        Group (Expression expr) {
            this.expr = expr;
        }
        final Expression expr;

        @Override
        <R> R accept(Visitor<R> v){
            return v.visitGroupExpr(this);
        }
    }

   abstract <R> R accept(Visitor<R> v);
}