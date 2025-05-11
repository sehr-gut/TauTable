package corefunctions;
import java.util.ArrayList;
import java.util.List;


public class Lexer {
    private final String source;
    final List<Token> tokens = new ArrayList<>();

    public List<Token> getTokens() {
        return tokens;
    }
    private int count = 0, start = 0;
    public Lexer(String source) {   
        this.source = source;
    }
    public void scan() {
        while(!end()) {
            start = count;
            lex(); 
        }
        tokens.add(new Token(null, TokenType.EOF));
    }
    public void lex() {
        char currChar = advance();
       
        switch (currChar) {
            case '.': addToken(TokenType.DOT);break;
            case '(': addToken(TokenType.LPAREN);break;
            case ')': addToken(TokenType.RPAREN);break;
            case '^': addToken(TokenType.AND);break;
            case 'v': addToken(TokenType.OR);break;
            case '-': 
                if(peek() == '>') {
                    advance();
                    addToken(TokenType.IF);
                } else {
                    System.err.println("unknown operator");
                    System.exit(1);
                }
                break;
            case '<': 
                if(peek() == '=') {
                    advance();
                    if(peek() == '>') {
                        advance();
                        addToken(TokenType.IFF);
                    }   
                } else {
                    System.err.println("unknown operator");
                    System.exit(1);
                }
                break;
            case '~': addToken(TokenType.NEG); break;
            case 'x': addToken(TokenType.XOR); break;
            case ' ': break;
            case '\n':
            case '\r':
            case '\t':
                break;
            default:
                prop();
        }

    }
    private boolean alpha(char a) {
        return a >= 'a' && a <= 'z' ||
            a >= 'A' && a <= 'Z';
    }
    private void prop() {
        while(alpha(peek()) && peek() != 'v') {
            advance();
        }
        String a = source.substring(start, count);
        if(a.equals("T")) addToken(TokenType.TAUT);
        else if(a.equals("F")) addToken(TokenType.CONT);
        else addToken(TokenType.PROP, a);
    }
    private void addToken(TokenType type, String s) {
        tokens.add(new Token(s, type));
    }
    private void addToken(TokenType type) {
        String lexeme = source.substring(start, count);
        tokens.add(new Token(lexeme, type));
    }
    private char peek() {
        if(end()) return '\0';
        return source.charAt(count);
    }
    private char advance() {
        return source.charAt(count++);
    }
    private boolean end() {
        return count >= source.length();
    }
}