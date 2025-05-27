package corefunctions;
import java.util.ArrayList;
import java.util.List;


public class Lexer {
    private final String source;
    final List<Token> tokens = new ArrayList<>();
    private boolean error;
    private int posError = 0;
    List<Integer> errors;
    
    
    private int count = 0, start = 0;
    public Lexer(String source) {   
        this.source = source;
    }
    public void scan() {
        error = false;
        errors = new ArrayList<>();
        while(!end()) {
            start = count;
            lex(); 
        }
        tokens.add(new Token(null, TokenType.EOF, count));
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
                    error = true;
                    errors.add(count);
                    return;
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
                    error = true;
                    errors.add(count);
                    return;
                }
                break;
            case '~': addToken(TokenType.NEG); break;
            case 'x': addToken(TokenType.XOR); break;
            
            case ' ': 
            case '\n':
            case '\r':
            case '\t':
                break;
            default:
                if(alpha(currChar))
                    prop();
                else {
                    error = true;
                    errors.add(count);
                    return;
                }
                    
        }

    }
    public boolean getError() {
        return error;
    }
    public List<Token> getTokens() {
        return tokens;
    }
    public List<Integer> getErrorPosition() {
        return errors;
    }
    private boolean alpha(char a) {
        return a >= 'a' && a <= 'z' ||
            a >= 'A' && a <= 'Z';
    }
    private void prop() {
        while((alpha(peek()) || peek() == ' ')&& peek() != 'v' ) {
            advance();
        }
        String a = source.substring(start, count).replaceAll(" ", "");
        if(a.equals("T")) addToken(TokenType.TAUT);
        else if(a.equals("F")) addToken(TokenType.CONT);
        else addToken(TokenType.PROP, a);
    }
    private void addToken(TokenType type, String s) {
        tokens.add(new Token(s, type, start));
    }
    private void addToken(TokenType type) {
        String lexeme = source.substring(start, count);
        addToken(type, lexeme);
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