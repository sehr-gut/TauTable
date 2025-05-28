package corefunctions;
import java.util.ArrayList;
import java.util.List;


public class Lexer { // token scanner and creation
    private final String source; // the main program
    final List<Token> tokens = new ArrayList<>(); // list of tokens
    private boolean error; // chekx lexicographical errors
    private int posError = 0; // outputs the error position
    List<Integer> errors; // outputs a list of error
    
    
    private int count = 0, start = 0; // pointers for the token generation
    public Lexer(String source) {   
        this.source = source;
    }
    public void scan() { // this is the scanning method ( the heart of this
                          // class)
        error = false;
        errors = new ArrayList<>();
        while(!end()) {
            start = count; // start update
            lex(); // repeated scanning
        }
        tokens.add(new Token(null, TokenType.EOF, count)); // adds end of file
                                                            // token in the end
    }
    
    public void lex() {
        char currChar = advance(); // increments and returns current character
       
        switch (currChar) {
            case '.': addToken(TokenType.DOT);break; // . char
            case '(': addToken(TokenType.LPAREN);break;// ( char
            case ')': addToken(TokenType.RPAREN);break;// ) char
            case '^': addToken(TokenType.AND);break; // ^ char
            case 'v': addToken(TokenType.OR);break; // v char
            case '-': 
                if(peek() == '>') { // chechs -> char
                    advance();
                    addToken(TokenType.IF);
                } else {            // if it did not find one, add error
                    error = true;
                    errors.add(count);
                    return;
                }
                break;
            case '<':              // three character chech: first "<"
                if(peek() == '=') { // then "="
                    advance();
                    if(peek() == '>') { // last ">"
                        advance();
                        addToken(TokenType.IFF);
                    }   
                } else { // else add error
                    error = true;
                    errors.add(count);
                    return;
                }
                break;
            case '~': addToken(TokenType.NEG); break; // ~ char
            
            case ' ': 
            case '\n':
            case '\r':
            case '\t':
                break;// ski[ any spaces and new lines
            default:
                if(alpha(currChar))// if a character is found
                    prop(); // call this function
                else {
                    error = true; // add error otherwise
                    errors.add(count);
                    return;
                }
                    
        }

    }
    public boolean getError() { //returns error status
        return error;
    }
    public List<Token> getTokens() { // return token list
        return tokens;
    }
    public List<Integer> getErrorPosition() { // return all the error position
        return errors;
    }
    private boolean alpha(char a) { // checks if a character is alphabetic
        return a >= 'a' && a <= 'z' ||
            a >= 'A' && a <= 'Z';
    }
    private void prop() { // handles propositions
        while((alpha(peek()) || peek() == ' ')&& peek() != 'v' ) {
            advance(); // while you see a letter a space and not a 'v'
                        // continue looping
        }
        String a = source.substring(start, count);
        //make a substring of the source to get the proposition
        if(a.equals("T")) addToken(TokenType.TAUT); // if its a tautology
        else if(a.equals("F")) addToken(TokenType.CONT); // if contradiction
        else addToken(TokenType.PROP, a); // else just add it
    }
    private void addToken(TokenType type, String s) {
        s = s.replaceAll(" ", "");
        tokens.add(new Token(s, type, start));
    }
    // above is for propositions while below is for others
    private void addToken(TokenType type) {
        String lexeme = source.substring(start, count);
        addToken(type, lexeme);
    }
    private char peek() { // returns current character
        if(end()) return '\0';
        return source.charAt(count);
    }
    private char advance() { // returns current character and increments counter
        return source.charAt(count++);
    }
    private boolean end() { // checks if it's the end of the source
        return count >= source.length();
    }
}