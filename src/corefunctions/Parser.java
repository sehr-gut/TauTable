package corefunctions;

import corefunctions.Expression.*;
import corefunctions.TokenType;
import corefunctions.Token;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    private class ParseError extends RuntimeException{};
    // this is for error detection
    static List<String> propositions; // list of propositions
    final List<Token> tokens; // tokens to be parsed
    public int count; // counter
    
    boolean hasError = false; // statud
    public Parser(List<Token> tokens) { 
        this.tokens = tokens;
        count = 0;
    }
    
    public Expression parse() {// heart of the program
                                // what runs the parser
        try {
            propositions = new ArrayList<>(); //initialization
            return parseExp(); // start of the parser based on the grammar
                                // <expr>
        } catch(ParseError e) {
            hasError = true; //error catching
            return null;
        }
    }    

    private Expression parseExp() {
        // based on the grammar <expr> -> <iff>
        return parseIff();
    }    
    private Expression parseIff() {
        // based on the grammar <if> "<=>" <if> | <if>;
        Expression left = parseIf();
        while(match(TokenType.IFF)) { // matches <=> until exhausted
            Token op = previous();
            Expression right = parseIf();
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }
    private Expression parseIf() {
        //based on the grammar <or>  "->" <or> | <or>;
        Expression left = parseOr(); // matches -> until exhausted
        while(match(TokenType.IF)) {
            Token op = previous();
            Expression right = parseOr();            
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }
    private Expression parseOr() {
        //based on the grammar <and>  "^" <and> | <and>;
        Expression left = parseAnd();
        while(match(TokenType.OR)) {// matches v until exhausted
            Token op = previous();
            Expression right = parseAnd();
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }
 

    private Expression parseAnd() {
        //based on the grammar <neg> "^" <ner> | <neg>;
        Expression left = parseNeg(); 
        while(match(TokenType.AND)) { // matches ^ until exhausted
            Token op = previous();
            Expression right = parseNeg();
            left = new Expression.Binary(left, op, right);
        }
        return left;
    }


    private Expression parseNeg() {
        //based on the grammar "~" <prop> | <prop>;
        if(match(TokenType.NEG)) {
            Token op = previous();
            Expression right = parseNeg();
            return new Expression.Unary(op, right);
        }
        return parseProp();  
    }
    private Expression parseProp() {
        //based on the grammar <group> | LITERAL;
        if(match(TokenType.LPAREN)) { // this is for groups
            Expression in = parseExp(); // recursive call to parseExpr (top most)
            if(!match(TokenType.RPAREN)) {
                hasError = true;
            }

            return new Expression.Group(in);
        }
        if(match(TokenType.PROP, TokenType.TAUT, TokenType.CONT)) { // this is for literal
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
    public boolean getErrorStatus() { // returns error
        return hasError;
    }
    private void consume(TokenType type, String message) {
        if(check(type)) advance(); // this is for error detection
        throw error(peek(), message);
    }
    private ParseError error(Token t, String m) { // throws parse error
                                                   // if something unexpected 
                                                   // happens
        InterpreterHandler.error(t, m);
        return new ParseError();
    }
    private boolean check(TokenType type) { // checks if current Token is the same
        if (end()) return false;
        return peek().type == type;
    }
    private Token peek() { // returns current char
        return tokens.get(count);
    }
    private boolean end() { // checks EOF token to halt operations
        return peek().type == TokenType.EOF;
    }
    private Token previous() { // returns last token
        return tokens.get(count - 1);
    }
    private Token advance() { // returns current char and increments counetr
        if(!end()) count++;
        return previous();
    }
}