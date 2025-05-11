package corefunctions;

public class Token {
    public Token(String le, TokenType t) {
        lexeme = le;
        type = t;
    }

    final String lexeme;
    final TokenType type;

    public String toString() {
        return String.format("Token\nLexeme: %s\nType: %s\n", 
                           lexeme, type);
    }
}