package corefunctions;

import corefunctions.Expression.*;
import corefunctions.TokenType;
import corefunctions.Token;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    private class ParseError extends RuntimeException{};
    static List<String> propositions;
    final List<Token> tokens;
    public int count;
    
    boolean hasError = false;
    public Parser(List<Token> tokens) {
        
        this.tokens = tokens;
        count = 0;
    }
    
    public Expression parse() {
        try {
            propositions = new ArrayList<>();
            return parseExp();
        } catch(ParseError e) {
            hasError = true;
            return null;
        }
    }    

    private Expression parseExp() {
        return parseIff();
    }    
    private Expression parseIff() {
        Expression left = parseIf();
        while(match(TokenType.IFF)) {
            Token op = previous();
            Expression right = parseIf();
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }
    private Expression parseIf() {
        Expression left = parseOr();
        while(match(TokenType.IF)) {
            Token op = previous();
            Expression right = parseOr();            
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }
    private Expression parseOr() {
        Expression left = parseAnd();
        while(match(TokenType.OR)) {
            Token op = previous();
            Expression right = parseAnd();
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }
 

    private Expression parseAnd() {
        Expression left = parseNeg();
        while(match(TokenType.AND)) {
            Token op = previous();
            Expression right = parseNeg();
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }


    private Expression parseNeg() {
        if(match(TokenType.NEG)) {
            Token op = previous();
            Expression right = parseNeg();
            return new Expression.Unary(op, right);
        }
        return parseProp();  
    }
    private Expression parseProp() {
        if(match(TokenType.LPAREN)) {
            Expression in = parseExp();
            if(!match(TokenType.RPAREN)) {
                hasError = true;
            }

            return new Expression.Group(in);
        }
        if(match(TokenType.PROP, TokenType.TAUT, TokenType.CONT)) {
            if(!propositions.contains(previous().lexeme)) 
                propositions.add(previous().lexeme);
            
            return new Expression.Literal(previous().lexeme);
        }
            
        throw error(peek(), "Expected a proposition here");
    }
    private boolean match(TokenType... types) {
        for(TokenType type: types) {
            if(peek().type == type)  {
                advance();
                return true;
            }
        }
        return false;
    }
    public boolean getErrorStatus() {
        return hasError;
    }
    private void consume(TokenType type, String message) {
        if(check(type)) advance();
        throw error(peek(), message);
    }
    private ParseError error(Token t, String m) {
        InterpreterHandler.error(t, m);
        return new ParseError();
    }
    private boolean check(TokenType type) {
        if (end()) return false;
        return peek().type == type;
    }
    private Token peek() {
        return tokens.get(count);
    }
    private boolean end() {
        return peek().type == TokenType.EOF;
    }
    private Token previous() {
        return tokens.get(count - 1);
    }
    private Token advance() {
        if(!end()) count++;
        return previous();
    }
}